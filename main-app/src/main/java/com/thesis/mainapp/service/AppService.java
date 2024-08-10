package com.thesis.mainapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.mainapp.config.repositories.*;
import com.thesis.mainapp.domain.FuelEmissionFactor;
import com.thesis.mainapp.domain.Process;
import com.thesis.mainapp.domain.Task;
import com.thesis.mainapp.domain.annotations.ProcessAnnotation;
import com.thesis.mainapp.domain.annotations.TaskAnnotation;
import com.thesis.mainapp.domain.intermediate.IntermediateProcessConsumption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AppService {

    @Autowired
    private EmissionFactorService emissionFactorService;
    @Autowired
    private ProcessRepository processRepository;
    @Autowired
    private ProcessAnnotationRepository processAnnotationRepository;
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private HelperService helperService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private FuelEmissionFactorRepository fuelEmissionFactorRepository;

    // message should contain processName, processKey, taskName/status, startTime/endTime, platform
    @Transactional
    public void parseReceivedMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map map = mapper.readValue(message, Map.class);

            //if message is from a task event (null check for some jbpm null tasks that appear)
            if (map.containsKey("taskName") && !Objects.equals(map.get("taskName").toString(), "null")) {
                //task start event
                if(map.containsKey("startTime")) {
                    map = helperService.parseTime(map, "startTime");
                    Task task = new Task();
                    task.setName(map.get("taskName").toString());
                    task.setStartTime((LocalDateTime) map.get("startTime"));
                    task.setProcess(processRepository.findByProcessNameAndProcessKey(map.get("processName").toString(), map.get("processKey").toString()));
                    taskRepository.save(task);
                //task end event
                } else if (map.containsKey("endTime")) {
                    map = helperService.parseTime(map, "endTime");
                    calculateTaskConsumption(map);
                    Task task = taskRepository.findByNameAndProcessAndEndTime(map.get("taskName").toString(),processRepository.findByProcessNameAndProcessKey(map.get("processName").toString(), map.get("processKey").toString()), null);
                    task.setEndTime((LocalDateTime) map.get("endTime"));
                    taskRepository.save(task);
                }
            //process start event
            } else if ((map.containsKey("status") && map.get("status").toString().equals("1"))) {
                map = helperService.parseTime(map, "startTime");
                Process process = new Process();
                process.setStartTime((LocalDateTime) map.get("startTime"));
                process.setProcessName(map.get("processName").toString());
                process.setProcessKey(map.get("processKey").toString());
                process.setStatus(map.get("status").toString());
                process.setDiagramXML(map.get("diagramXML").toString());
                processRepository.save(process);
            //process end event
            } else if (map.containsKey("status") && map.get("status").toString().equals("2")) {

                map = helperService.parseTime(map, "endTime");
                Process process = processRepository.findByProcessNameAndProcessKey(map.get("processName").toString(), map.get("processKey").toString());
                process.setEndTime((LocalDateTime) map.get("endTime"));
                process.setStatus(map.get("status").toString());
                processRepository.save(process);
                emissionFactorService.calculateTotalEmissions(process);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to calculate the co2 consumption of a specific task
     * @param map payload from the engine
     * @throws JsonProcessingException
     */
    public void calculateTaskConsumption(Map map) throws JsonProcessingException {
        //extra check for when the annotations were not imported
        Optional<ProcessAnnotation> optionalProcessAnnotation = processAnnotationRepository.findByName(map.get("processName").toString());
        if(optionalProcessAnnotation.isPresent()) {
            ProcessAnnotation processAnnotation = optionalProcessAnnotation.get();
            Optional<TaskAnnotation> optionalTaskAnnotation = processAnnotation.getTask(map.get("taskName").toString());
            if (optionalTaskAnnotation.isPresent()) {
                TaskAnnotation taskAnnotation = optionalTaskAnnotation.get();
                List<IntermediateProcessConsumption> intermediateProcessConsumptionList = emissionFactorService.calculateConsumption(map, taskAnnotation);
                double co2EmissionValue = emissionFactorService.calculateEmissions(intermediateProcessConsumptionList);
                Task task = taskRepository.findByNameAndProcessAndEndTime(taskAnnotation.getName(),intermediateProcessConsumptionList.get(0).getProcess(),null);

                //send co2 emission for task to frontend
                Map payload = new HashMap();
                payload.put("processName", intermediateProcessConsumptionList.get(0).getProcess().getProcessName());
                payload.put("processInstance",intermediateProcessConsumptionList.get(0).getProcess().getProcessKey());
                payload.put("taskName",taskAnnotation.getName());
                payload.put("co2EmissionValue", co2EmissionValue);
                payload.put("startTime", task.getStartTime());
                payload.put("endTime", map.get("endTime").toString());
                payload.put("diagramXML", intermediateProcessConsumptionList.get(0).getProcess().getDiagramXML());
                webSocketService.sendPayloadToFrontend(payload);
            }
        }


    }

    @Transactional
        public void saveAnnotations(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ProcessAnnotation processAnnotation = mapper.readValue(json, ProcessAnnotation.class);
        String processName = processAnnotation.getName();
        helperService.saveOrUpdate(processAnnotation);
        helperService.saveAnnotationBody(json, processName);
        webSocketService.sendAllAnnotationJsonToFrontend();
    }

    @Transactional
    public void saveFactors(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<FuelEmissionFactor> fuelEmissionFactors = mapper.readValue(json, new TypeReference<List<FuelEmissionFactor>>() { });
        fuelEmissionFactorRepository.saveAll(fuelEmissionFactors);
        webSocketService.sendAllFuelEmissionFactorToFrontend();
    }

}
