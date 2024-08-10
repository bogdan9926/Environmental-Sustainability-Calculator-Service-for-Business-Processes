package com.thesis.mainapp.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.mainapp.config.repositories.AnnotationJsonRepository;
import com.thesis.mainapp.config.repositories.FrontendDataRepository;
import com.thesis.mainapp.config.repositories.FuelEmissionFactorRepository;
import com.thesis.mainapp.domain.FrontendData;
import com.thesis.mainapp.domain.FuelEmissionFactor;
import com.thesis.mainapp.domain.annotations.AnnotationJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class WebSocketServiceTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private FrontendDataRepository frontendDataRepository;

    @Mock
    private AnnotationJsonRepository annotationJsonRepository;

    @Mock
    private FuelEmissionFactorRepository fuelEmissionFactorRepository;

    @InjectMocks
    private WebSocketService webSocketService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendMessage() {
        FrontendData message = new FrontendData();
        message.setCo2EmissionValue("11");

        webSocketService.sendMessage(message);

        verify(messagingTemplate, times(1)).convertAndSend("/topic/messages", message);
    }

    @Test
    public void testSendAnnotations() {
        AnnotationJson annotationJson = new AnnotationJson();
        List<AnnotationJson> annotationJsonList = Arrays.asList(annotationJson);

        webSocketService.sendAnnotations(annotationJsonList);

        verify(messagingTemplate, times(1)).convertAndSend("/topic/annotations", annotationJsonList);
    }

    @Test
    public void testSendFuelEmissions() {
        FuelEmissionFactor fuelEmissionFactor = new FuelEmissionFactor();
        List<FuelEmissionFactor> fuelEmissionFactorList = Arrays.asList(fuelEmissionFactor);

        webSocketService.sendFuelEmissions(fuelEmissionFactorList);

        verify(messagingTemplate, times(1)).convertAndSend("/topic/fuelEmissions", fuelEmissionFactorList);
    }

    @Test
    public void testSendPayloadToFrontend() throws JsonProcessingException {
        // Create a payload that matches the FrontendData structure
        Map<String, Object> payload = Map.of(
                "processName", "Test Process",
                "processInstance", "test_instance",
                "taskName", "Test Task",
                "co2EmissionValue", 123.45,
                "diagramXML", "<diagram></diagram>"
        );
        ObjectMapper objectMapper = new ObjectMapper();
        FrontendData frontendData = new FrontendData();
        frontendData.setProcessName("Test Process");
        frontendData.setProcessInstance("test_instance");
        frontendData.setTaskName("Test Task");
        frontendData.setCo2EmissionValue("123.45");
        frontendData.setDiagramXML("<diagram></diagram>");

        when(frontendDataRepository.save(any(FrontendData.class))).thenReturn(frontendData);

        webSocketService.sendPayloadToFrontend(payload);

        verify(frontendDataRepository, times(1)).save(any(FrontendData.class));
    }

    @Test
    public void testSendAllAnnotationJsonToFrontend() {
        AnnotationJson annotationJson = new AnnotationJson();
        List<AnnotationJson> annotationJsonList = Arrays.asList(annotationJson);

        when(annotationJsonRepository.findAll()).thenReturn(annotationJsonList);

        webSocketService.sendAllAnnotationJsonToFrontend();

        verify(annotationJsonRepository, times(1)).findAll();
        verify(messagingTemplate, times(1)).convertAndSend("/topic/annotations", annotationJsonList);
    }

    @Test
    public void testSendAllFuelEmissionFactorToFrontend() {
        FuelEmissionFactor fuelEmissionFactor = new FuelEmissionFactor();
        List<FuelEmissionFactor> fuelEmissionFactorList = Arrays.asList(fuelEmissionFactor);

        when(fuelEmissionFactorRepository.findAll()).thenReturn(fuelEmissionFactorList);

        webSocketService.sendAllFuelEmissionFactorToFrontend();

        verify(fuelEmissionFactorRepository, times(1)).findAll();
        verify(messagingTemplate, times(1)).convertAndSend("/topic/fuelEmissions", fuelEmissionFactorList);
    }
}
