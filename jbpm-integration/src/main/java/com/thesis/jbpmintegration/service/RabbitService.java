package com.thesis.jbpmintegration.service;

import com.thesis.jbpmintegration.domain.JbpmJson;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.task.TaskService;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.kie.services.client.api.RemoteRestRuntimeFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RabbitService {
    private final String serverUrl = "http://localhost:8080/kie-server/services/rest/server";
    String username = "wbadmin";
    String password = "wbadmin";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private KieServicesClient kieServicesClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void createContainer(JbpmJson containerCreationRequest) {
        String url = "http://jbpm-server:8080/kie-server/services/rest/server/containers/"+ containerCreationRequest.getContainerName();

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

        try {
            restTemplate.put(url, entity);
        } catch (RestClientException e) {
            System.err.println("Error occurred while creating the container: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
    public Long startProcess(JbpmJson processStartRequest) {
        ProcessServicesClient processServicesClient = kieServicesClient.getServicesClient(ProcessServicesClient.class);
        return processServicesClient.startProcess(processStartRequest.getContainerId(), processStartRequest.getProcessName());
    }

    public void sendMessage(String channel, String message) {
        rabbitTemplate.convertAndSend(channel, message);
    }


}
