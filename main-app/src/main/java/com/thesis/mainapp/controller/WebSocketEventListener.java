package com.thesis.mainapp.controller;

import com.thesis.mainapp.config.repositories.AnnotationJsonRepository;
import com.thesis.mainapp.config.repositories.FrontendDataRepository;
import com.thesis.mainapp.config.repositories.FuelEmissionFactorRepository;
import com.thesis.mainapp.domain.FrontendData;
import com.thesis.mainapp.domain.FuelEmissionFactor;
import com.thesis.mainapp.domain.annotations.AnnotationJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;

@Component
public class WebSocketEventListener implements ApplicationListener<SessionSubscribeEvent> {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private FrontendDataRepository frontendDataRepository;
    @Autowired
    private AnnotationJsonRepository annotationJsonRepository;
    @Autowired
    private FuelEmissionFactorRepository fuelEmissionFactorRepository;

    @Override
    public void onApplicationEvent(SessionSubscribeEvent  event) {
        List<FrontendData> frontendDataList = frontendDataRepository.findAll();
        List<AnnotationJson> annotationJsonList = annotationJsonRepository.findAll();
        List<FuelEmissionFactor> fuelEmissionFactorList = fuelEmissionFactorRepository.findAll();
        messagingTemplate.convertAndSend("/topic/emissions", frontendDataList);
        messagingTemplate.convertAndSend("/topic/annotations", annotationJsonList);
        messagingTemplate.convertAndSend("/topic/emissionFactors", fuelEmissionFactorList);
    }
}
