package com.thesis.jbpmintegration.service;


import com.thesis.jbpmintegration.domain.ContainerCreationRequest;
import com.thesis.jbpmintegration.domain.JbpmJson;
import com.thesis.jbpmintegration.domain.ProcessStartRequest;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.task.TaskService;
import org.kie.server.api.model.definition.UserTaskDefinition;
import org.kie.server.api.model.definition.UserTaskDefinitionList;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Base64;

@Service
public class JBPMService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KieServicesClient kieServicesClient;

    public void createContainer(JbpmJson containerCreationRequest) {
        String url = "http://localhost:8080/kie-server/services/rest/server/containers/"+ containerCreationRequest.getContainerName();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString("wbadmin:wbadmin".getBytes()));

        String requestJson = String.format(
                "{\"container-id\" : \"%s\",\"release-id\" : { \"group-id\" : \"%s\",   \"artifact-id\" : \"%s\", \"version\" : \"%s\"}}",
                containerCreationRequest.getContainerName(),
                containerCreationRequest.getGroupId(),
                containerCreationRequest.getArtifactId(),
                containerCreationRequest.getVersion()
        );
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        restTemplate.put(url, entity);
    }

//    public void startProcess(ProcessStartRequest processStartRequest) {
//        String url = "http://localhost:8080/kie-server/services/rest/server/containers/" + processStartRequest.getContainerId() + "/processes/" + processStartRequest.getProcessId() + "/instances";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString("wbadmin:wbadmin".getBytes()));
//
////        String requestJson = "{\"container-id\" : \"coi2\", \"release-id\" : { \"group-id\" : \"com.company\", \"artifact-id\" : \"business-application-kjar\", \"version\" : \"1.0-SNAPSHOT\" }}";
//
//        HttpEntity<String> entity = new HttpEntity<>(null, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
//        System.out.println(response.getStatusCode());
//
//    }

    public Long startProcess(String containerId, String processId) {
        ProcessServicesClient processServicesClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);

        return processServicesClient.startProcess(containerId, processId);
    }

    public Long startProcess(ProcessStartRequest processStartRequest) {

        ProcessServicesClient processServicesClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
        UserTaskDefinitionList userTaskDefinitionList = processServicesClient.getUserTaskDefinitions(processStartRequest.getContainerId(),processStartRequest.getProcessId());
        UserTaskDefinition[] userTaskDefinitions = userTaskDefinitionList.getTasks();
        return processServicesClient.startProcess(processStartRequest.getContainerId(), processStartRequest.getProcessId());
    }
}
