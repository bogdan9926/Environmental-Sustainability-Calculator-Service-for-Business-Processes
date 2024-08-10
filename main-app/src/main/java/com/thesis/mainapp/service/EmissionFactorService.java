package com.thesis.mainapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.mainapp.config.repositories.*;
import com.thesis.mainapp.config.TimeUnitConverter;
import com.thesis.mainapp.domain.FrontendData;
import com.thesis.mainapp.domain.FuelEmissionFactor;
import com.thesis.mainapp.domain.Task;
import com.thesis.mainapp.domain.annotations.ResourceAnnotation;
import com.thesis.mainapp.domain.intermediate.IntermediateCo2Emission;
import com.thesis.mainapp.domain.intermediate.IntermediateProcessConsumption;
import com.thesis.mainapp.domain.Process;
import com.thesis.mainapp.domain.annotations.ResourceUsage;
import com.thesis.mainapp.domain.annotations.TaskAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmissionFactorService {
    @Autowired
    private ProcessRepository processRepository;
    @Autowired
    private ResourceAnnotationRepository resourceAnnotationRepository;
    @Autowired
    private IntermediateProcessConsumptionRepository intermediateProcessConsumptionRepository;
    @Autowired
    private FuelEmissionFactorRepository fuelEmissionFactorRepository;
    @Autowired
    private IntermediateCo2EmissionRepository intermediateCo2EmissionRepository;
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private TaskRepository taskRepository;

    /**
     * Function to calculate fuel consumption for a task
     * @param map payload from the engine
     * @param taskAnnotation annotation for the specific task
     * @return a list of fuel amounts
     */
    @Transactional
    public List<IntermediateProcessConsumption> calculateConsumption(Map map, TaskAnnotation taskAnnotation) {
        List<IntermediateProcessConsumption> intermediateProcessConsumptionList = new ArrayList<>();
        List<ResourceUsage> resourceUsagesList = taskAnnotation.getResourcesUsed();
        for (ResourceUsage resourceUsage : resourceUsagesList) {
            ResourceAnnotation resource = resourceAnnotationRepository.findByNameAndProcess(resourceUsage.getResourceName(), taskAnnotation.getProcess());
            if (resource.getType().equals(ResourceAnnotation.ATOMIC)) {
                Process process = processRepository.findByProcessNameAndProcessKey(map.get("processName").toString(), map.get("processKey").toString());

                String fromUnit = resourceUsage.getUnit();
                String toUnit = resource.getTimeUnit();
                double resourceUsageValue;
                //if the resource usage annotations has a fixed amount of time
                if (resourceUsage.getTimeUsed() != null)
                    resourceUsageValue = Double.parseDouble(resourceUsage.getTimeUsed());
                else {
                //the resource is used as long as the task runs
                    Task task = taskRepository.findByNameAndProcessAndEndTime(map.get("taskName").toString(),process,null);
                    LocalDateTime startTime = task.getStartTime();
                    LocalDateTime endTime = (LocalDateTime) map.get("endTime");
                    Duration duration = Duration.between(startTime, endTime);
                    fromUnit = "seconds";
                    resourceUsageValue = Double.parseDouble(String.valueOf(duration.toSeconds()));
                }
                //amount of fuel = amount of fuel per time unit * time used
                double resourceConsumptionValue = Double.parseDouble(resource.getFuelPerUse());
                resourceUsageValue = TimeUnitConverter.convert(fromUnit, toUnit, resourceUsageValue);
                double fuelAmountConsumed = resourceUsageValue * resourceConsumptionValue;

                //store amount of fuel
                IntermediateProcessConsumption intermediateProcessConsumption = new IntermediateProcessConsumption();
                intermediateProcessConsumption.setTask(map.get("taskName").toString());
                intermediateProcessConsumption.setProcess(process);
                intermediateProcessConsumption.setUnit(resource.getFuelUnit());
                intermediateProcessConsumption.setValue(fuelAmountConsumed);
                intermediateProcessConsumption.setFuelType(resource.getFuelType());
                intermediateProcessConsumptionRepository.save(intermediateProcessConsumption);
                intermediateProcessConsumptionList.add(intermediateProcessConsumption);
            }

        }
        return intermediateProcessConsumptionList;

    }

    /**
     * Calculate co2 emission amount for a task
     * @param list list of fuel amounts
     * @return co2 emission amount
     */
    @Transactional
    public double calculateEmissions(List<IntermediateProcessConsumption> list) {
        double co2EmissionAmount = 0.0;
        for (IntermediateProcessConsumption consumption : list) {
            FuelEmissionFactor factor = fuelEmissionFactorRepository.findByFuelType(consumption.getFuelType());
            co2EmissionAmount += factor.calculateEmissionAmount(consumption.getValue(), consumption.getUnit());
        }
        //precision
        double scale = Math.pow(10, 4);
        co2EmissionAmount = Math.round(co2EmissionAmount * scale) / scale;

        //store co2 emission amount
        IntermediateCo2Emission intermediateCo2Emission = new IntermediateCo2Emission();
        intermediateCo2Emission.setProcess(list.get(0).getProcess());
        intermediateCo2Emission.setValue(co2EmissionAmount);
        intermediateCo2Emission.setTask(list.get(0).getTask());
        intermediateCo2EmissionRepository.save(intermediateCo2Emission);
        return co2EmissionAmount;

    }

    /**
     * Add co2 emissions of all tasks in the process
     * @param process
     * @return co2 emission amount
     */
    @Transactional
    public double calculateTotalEmissions(Process process) {
        List<IntermediateCo2Emission> intermediateCo2EmissionList = intermediateCo2EmissionRepository.findAllByProcess(process);
        double sumCo2Emissions = 0.0;
        for (IntermediateCo2Emission i : intermediateCo2EmissionList) {
            sumCo2Emissions += i.getValue();
        }
        //precision
        double scale = Math.pow(10, 4);
        sumCo2Emissions = Math.round(sumCo2Emissions * scale) / scale;
        //store total co2 emissions
        IntermediateCo2Emission intermediateCo2Emission = new IntermediateCo2Emission();
        intermediateCo2Emission.setProcess(process);
        intermediateCo2Emission.setValue(sumCo2Emissions);
        intermediateCo2Emission.setTask("total");
        intermediateCo2EmissionRepository.save(intermediateCo2Emission);
        //send payload to frontend
        Map payload = new HashMap();
        payload.put("processName", process.getProcessName());
        payload.put("processInstance", process.getProcessKey());
        payload.put("taskName", "Total");
        payload.put("startTime", process.getStartTime());
        payload.put("endTime", process.getEndTime());
        payload.put("co2EmissionValue", sumCo2Emissions);
        payload.put("diagramXML", process.getDiagramXML());
        try {
            webSocketService.sendPayloadToFrontend(payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sumCo2Emissions;


    }

}
