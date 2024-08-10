package com.thesis.mainapp.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.thesis.mainapp.config.repositories.*;
import com.thesis.mainapp.domain.FuelEmissionFactor;
import com.thesis.mainapp.domain.Process;
import com.thesis.mainapp.domain.annotations.ProcessAnnotation;
import com.thesis.mainapp.domain.annotations.ResourceAnnotation;
import com.thesis.mainapp.domain.annotations.ResourceUsage;
import com.thesis.mainapp.domain.annotations.TaskAnnotation;
import com.thesis.mainapp.domain.intermediate.IntermediateCo2Emission;
import com.thesis.mainapp.domain.intermediate.IntermediateProcessConsumption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmissionFactorServiceTest {

    @Mock
    private FuelEmissionFactorRepository fuelEmissionFactorRepository;
    @Mock
    private ProcessRepository processRepository;

    @Mock
    private ResourceAnnotationRepository resourceAnnotationRepository;

    @Mock
    private IntermediateProcessConsumptionRepository intermediateProcessConsumptionRepository;


    @Mock
    private IntermediateCo2EmissionRepository intermediateCo2EmissionRepository;

    @Mock
    private WebSocketService webSocketService;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private EmissionFactorService emissionFactorService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllEmissionFactors() {
        FuelEmissionFactor factor1 = new FuelEmissionFactor("Diesel", "l", 2.70553);
        FuelEmissionFactor factor2 = new FuelEmissionFactor("Petrol", "l", 2.32055);
        when(fuelEmissionFactorRepository.findAll()).thenReturn(Arrays.asList(factor1, factor2));

        List<FuelEmissionFactor> factors = fuelEmissionFactorRepository.findAll();

        assertNotNull(factors);
        assertEquals(2, factors.size());
        assertEquals("Diesel", factors.get(0).getFuelType());
        assertEquals("Petrol", factors.get(1).getFuelType());
    }

    @Test
    public void testSaveEmissionFactor() {
        FuelEmissionFactor factor = new FuelEmissionFactor("Natural Gas", "m3", 1.89876);
        when(fuelEmissionFactorRepository.save(any(FuelEmissionFactor.class))).thenReturn(factor);

        FuelEmissionFactor savedFactor = fuelEmissionFactorRepository.save(factor);

        assertNotNull(savedFactor);
        assertEquals("Natural Gas", savedFactor.getFuelType());
        assertEquals("m3", savedFactor.getUnit());
        assertEquals(1.89876, savedFactor.getFactor());
    }

    @Test
    public void testCalculateTotalEmissions() throws JsonProcessingException {
        Process process = new Process();
        process.setProcessName("Test Process");
        process.setProcessKey("test_key");
        process.setDiagramXML("<diagram></diagram>");

        IntermediateCo2Emission emission1 = new IntermediateCo2Emission();
        emission1.setValue(10.0);
        IntermediateCo2Emission emission2 = new IntermediateCo2Emission();
        emission2.setValue(15.0);

        when(intermediateCo2EmissionRepository.findAllByProcess(any(Process.class)))
                .thenReturn(Arrays.asList(emission1, emission2));

        double result = emissionFactorService.calculateTotalEmissions(process);
        assertEquals(25.0, result);
        verify(intermediateCo2EmissionRepository, times(1)).save(any(IntermediateCo2Emission.class));
        verify(webSocketService, times(1)).sendPayloadToFrontend(anyMap());
    }
    @Test
    public void testCalculateConsumption() {
        TaskAnnotation taskAnnotation = new TaskAnnotation();
        taskAnnotation.setName("Test Task");
        taskAnnotation.setDuration("1");
        taskAnnotation.setTimeUnit("hours");

        Process process = new Process();
        process.setProcessName("Test Process");
        process.setProcessKey("test_key");
        process.setDiagramXML("<diagram></diagram>");

        ProcessAnnotation processAnnotation = new ProcessAnnotation();
        taskAnnotation.setProcess(processAnnotation);

        ResourceAnnotation resourceAnnotation = new ResourceAnnotation();
        resourceAnnotation.setName("Resource1");
        resourceAnnotation.setType(ResourceAnnotation.ATOMIC);
        resourceAnnotation.setFuelType("Diesel");
        resourceAnnotation.setFuelPerUse("10");
        resourceAnnotation.setFuelUnit("l");
        resourceAnnotation.setTimeUnit("hours");
        resourceAnnotation.setProcess(processAnnotation);

        ResourceUsage resourceUsage = new ResourceUsage();
        resourceUsage.setResourceName("Test Resource");
        resourceUsage.setTimeUsed("10.0");
        resourceUsage.setUnit("minutes");
        resourceUsage.setResourceName("Resource1");

        taskAnnotation.setResourcesUsed(Arrays.asList(resourceUsage));
        when(resourceAnnotationRepository.findByNameAndProcess(anyString(), any(ProcessAnnotation.class)))
                .thenReturn(resourceAnnotation);
        when(processRepository.findByProcessNameAndProcessKey(anyString(), anyString()))
                .thenReturn(process);
        Map<String, Object> map = new HashMap<>();
        map.put("jobId", "testJob");
        map.put("processName", "Test Process");
        map.put("processKey", "test_key");
        map.put("taskName", "Test Task");
        map.put("startTime", LocalDateTime.now());
        map.put("endTime", LocalDateTime.now().plusHours(1));
        map.put("platform", "camunda");

        IntermediateProcessConsumption consumption1 = new IntermediateProcessConsumption();
        consumption1.setFuelType("Diesel");
        consumption1.setValue(100.0);
        consumption1.setUnit("l");

        IntermediateProcessConsumption consumption2 = new IntermediateProcessConsumption();
        consumption2.setFuelType("Petrol");
        consumption2.setValue(200.0);
        consumption2.setUnit("l");

        when(intermediateProcessConsumptionRepository.saveAll(anyList()))
                .thenReturn(Arrays.asList(consumption1, consumption2));

        List<IntermediateProcessConsumption> result = emissionFactorService.calculateConsumption(map, taskAnnotation);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testCalculateCo2Emission() {
        List<IntermediateProcessConsumption> consumptions = new ArrayList<>();
        IntermediateProcessConsumption consumption1 = new IntermediateProcessConsumption();
        consumption1.setFuelType("Diesel");
        consumption1.setValue(100.0);
        consumption1.setUnit("l");
        consumptions.add(consumption1);

        IntermediateProcessConsumption consumption2 = new IntermediateProcessConsumption();
        consumption2.setFuelType("Petrol");
        consumption2.setValue(200.0);
        consumption2.setUnit("t");
        consumptions.add(consumption2);

        FuelEmissionFactor dieselFactor = new FuelEmissionFactor();
        dieselFactor.setFuelType("Diesel");
        dieselFactor.setUnit("l");
        dieselFactor.setFactor(2.70553);

        FuelEmissionFactor petrolFactor = new FuelEmissionFactor();
        petrolFactor.setFuelType("Petrol");
        petrolFactor.setUnit("t");
        petrolFactor.setFactor(2.32055);

        when(fuelEmissionFactorRepository.findByFuelType("Petrol")).thenReturn(petrolFactor);
        when(fuelEmissionFactorRepository.findByFuelType("Diesel")).thenReturn(dieselFactor);
        double result = emissionFactorService.calculateEmissions(consumptions);

        assertEquals((100.0 * 2.70553) + (200.0 * 2.32055), result);
    }
}

