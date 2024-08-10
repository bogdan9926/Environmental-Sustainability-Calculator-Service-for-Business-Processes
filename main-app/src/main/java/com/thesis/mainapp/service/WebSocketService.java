package com.thesis.mainapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thesis.mainapp.config.repositories.AnnotationJsonRepository;
import com.thesis.mainapp.config.repositories.FrontendDataRepository;
import com.thesis.mainapp.config.repositories.FuelEmissionFactorRepository;
import com.thesis.mainapp.domain.FrontendData;
import com.thesis.mainapp.domain.FuelEmissionFactor;
import com.thesis.mainapp.domain.annotations.AnnotationJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private FrontendDataRepository frontendDataRepository;
    @Autowired
    private AnnotationJsonRepository annotationJsonRepository;
    @Autowired
    private FuelEmissionFactorRepository fuelEmissionFactorRepository;

    public void sendMessage(FrontendData message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
    }

    public void sendAnnotations(List<AnnotationJson> message) {
        messagingTemplate.convertAndSend("/topic/annotations", message);
    }
    public void sendFuelEmissions(List<FuelEmissionFactor> message) {
        messagingTemplate.convertAndSend("/topic/fuelEmissions", message);
    }
    @Transactional
    public void sendPayloadToFrontend(Map payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        FrontendData frontendData = objectMapper.readValue(objectMapper.writeValueAsString(payload), FrontendData.class);
        frontendDataRepository.save(frontendData);
        sendMessage(frontendData);
    }
    public void sendAllAnnotationJsonToFrontend() {
        List<AnnotationJson> annotationJsonList=annotationJsonRepository.findAll();
        sendAnnotations(annotationJsonList);
    }
    public void sendAllFuelEmissionFactorToFrontend() {
        List<FuelEmissionFactor> fuelEmissionFactors = fuelEmissionFactorRepository.findAll();
        sendFuelEmissions(fuelEmissionFactors);
    }
}
