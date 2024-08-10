package com.thesis.mainapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.mainapp.config.repositories.FuelEmissionFactorRepository;
import com.thesis.mainapp.domain.FuelEmissionFactor;
import com.thesis.mainapp.domain.Process;
import com.thesis.mainapp.domain.annotations.ProcessAnnotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppServiceTest {

    @Mock
    private RabbitService rabbitService;

    @Mock
    private EmissionFactorService emissionFactorService;

    @Mock
    private FuelEmissionFactorRepository fuelEmissionFactorRepository;

    @Mock
    private WebSocketService webSocketService;

    @Mock
    private HelperService helperService;

    @InjectMocks
    private AppService appService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCalculateTotalEmissions() {
        Process process = new Process();
        process.setProcessName("Test Process");
        process.setProcessKey("test_key");

        when(emissionFactorService.calculateTotalEmissions(any(Process.class)))
                .thenReturn(100.0);

        emissionFactorService.calculateTotalEmissions(process);

        verify(emissionFactorService, times(1)).calculateTotalEmissions(process);
    }

    @Test
    public void testSaveAnnotations() throws JsonProcessingException {
        String json = "{ \"name\": \"Process_1y80xt6\", \"frequencyPerMonth\": 5, \"tasks\": [ { \"name\": \"activity3\", \"duration\": \"30\", \"timeUnit\": \"minutes\", \"resourcesUsed\": [ {\"resourceName\": \"Welder\", \"timeUsed\": \"30\", \"unit\": \"minutes\"} ] }, { \"name\": \"plmda\", \"duration\": \"45\", \"timeUnit\": \"minutes\", \"resourcesUsed\": [ {\"resourceName\": \"Paint Sprayer\", \"timeUsed\": \"30\", \"unit\": \"minutes\"} ] } ], \"resources\": [ {\"name\": \"Welder\", \"type\": \"atomic\", \"fuelPerUse\": \"500\", \"fuelType\": \"Diesel\", \"fuelUnit\": \"Wh\", \"timeUnit\": \"hour\"}, {\"name\": \"Paint Sprayer\", \"type\": \"atomic\", \"fuelPerUse\": \"300\", \"fuelType\": \"Diesel\", \"fuelUnit\": \"Wh\", \"timeUnit\": \"hour\"} ] }";

        ObjectMapper mapper = new ObjectMapper();
        ProcessAnnotation processAnnotation = mapper.readValue(json, ProcessAnnotation.class);

        when(helperService.saveOrUpdate(any(ProcessAnnotation.class))).thenReturn(processAnnotation);
        doNothing().when(helperService).saveAnnotationBody(anyString(), anyString());
        doNothing().when(webSocketService).sendAllAnnotationJsonToFrontend();

        appService.saveAnnotations(json);

        verify(helperService, times(1)).saveOrUpdate(any(ProcessAnnotation.class));
        verify(helperService, times(1)).saveAnnotationBody(anyString(), eq(processAnnotation.getName()));
        verify(webSocketService, times(1)).sendAllAnnotationJsonToFrontend();
    }

    @Test
    public void testSaveFactors() throws JsonProcessingException {
        String json = "[ { \"fuelType\": \"Diesel\", \"unit\": \"l\", \"factor\": 2.70553 } ]";

        ObjectMapper mapper = new ObjectMapper();
        List<FuelEmissionFactor> fuelEmissionFactors = mapper.readValue(json, new TypeReference<List<FuelEmissionFactor>>() {});

        doNothing().when(webSocketService).sendAllFuelEmissionFactorToFrontend();

        appService.saveFactors(json);

        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(fuelEmissionFactorRepository, times(1)).saveAll(argumentCaptor.capture());

        List<FuelEmissionFactor> capturedArgument = argumentCaptor.getValue();
        assertEquals(fuelEmissionFactors.size(), capturedArgument.size());
        for (int i = 0; i < fuelEmissionFactors.size(); i++) {
            assertEquals(fuelEmissionFactors.get(i).getFuelType(), capturedArgument.get(i).getFuelType());
            assertEquals(fuelEmissionFactors.get(i).getUnit(), capturedArgument.get(i).getUnit());
            assertEquals(fuelEmissionFactors.get(i).getFactor(), capturedArgument.get(i).getFactor(), 0.00001);
        }

        verify(webSocketService, times(1)).sendAllFuelEmissionFactorToFrontend();
    }
}
