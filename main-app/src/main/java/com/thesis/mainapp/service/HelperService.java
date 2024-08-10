package com.thesis.mainapp.service;

import com.thesis.mainapp.config.repositories.AnnotationJsonRepository;
import com.thesis.mainapp.config.repositories.ProcessAnnotationRepository;
import com.thesis.mainapp.config.repositories.ResourceUsageRepository;
import com.thesis.mainapp.domain.annotations.AnnotationJson;
import com.thesis.mainapp.domain.annotations.ProcessAnnotation;
import com.thesis.mainapp.domain.annotations.ResourceUsage;
import com.thesis.mainapp.domain.annotations.TaskAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class HelperService {
    @Autowired
    private ProcessAnnotationRepository processAnnotationRepository;
    @Autowired
    private ResourceUsageRepository resourceUsageRepository;
    @Autowired
    private AnnotationJsonRepository annotationJsonRepository;

    /**
     * Function used to save the annotation body or update an existing one
     * @param processAnnotation
     * @return saved/modified annotation
     */
    @Transactional
    public ProcessAnnotation saveOrUpdate(ProcessAnnotation processAnnotation) {
        Optional<ProcessAnnotation> existingProcess = processAnnotationRepository.findByName(processAnnotation.getName());

        if (existingProcess.isPresent()) {
            ProcessAnnotation existing = existingProcess.get();
            existing.setFrequencyPerMonth(processAnnotation.getFrequencyPerMonth());
            existing.setResources(processAnnotation.getResources());
            existing.setName(processAnnotation.getName());
            for (TaskAnnotation taskAnnotation : existing.getTasks()) {
                for (ResourceUsage resourceUsage: taskAnnotation.getResourcesUsed()) {
                    resourceUsageRepository.deleteById(resourceUsage.getId());
                }
            }
            existing.setTasks(processAnnotation.getTasks());
            // other fields to update
            return processAnnotationRepository.save(existing);
        } else {
            return processAnnotationRepository.save(processAnnotation);
        }
    }
    @Transactional
    public void saveAnnotationBody(String json, String processName) {
        AnnotationJson annotationJson = new AnnotationJson();
        annotationJson.setData(json);
        annotationJson.setProcessName(processName);
        annotationJsonRepository.save(annotationJson);
    }

    /**
     * Parses the timestamps to LocalDateTime
     * @param map payload from the engine
     * @param timeType start/endTime
     * @return
     */
    public Map parseTime(Map map, String timeType) {
        LocalDateTime time;
        if(map.get("platform").toString().equals("camunda")) {
            ArrayList<Integer> startArray = (ArrayList) map.get(timeType);
            time = LocalDateTime.of(startArray.get(0), startArray.get(1), startArray.get(2), startArray.get(3), startArray.get(4), startArray.get(5), startArray.get(6));
            map.put(timeType, time);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
            time = LocalDateTime.parse(map.get(timeType).toString(), formatter);
            map.put(timeType, time);
        }
        return map;
    }
}
