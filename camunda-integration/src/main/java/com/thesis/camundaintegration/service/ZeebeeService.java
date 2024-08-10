//package com.thesis.camundaintegration.service;
//
//import io.camunda.zeebe.client.ZeebeClient;
//import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
//import io.camunda.zeebe.client.api.worker.JobWorker;
//import io.camunda.zeebe.client.api.response.DeploymentEvent;
//
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class ZeebeeService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private RabbitService rabbitService;
//
//    @Autowired
//    private ZeebeClient zeebeClient;
//    public byte[] getDiagram (String fileLocation) throws IOException {
//        Path path = Paths.get(fileLocation);
//        return Files.readAllBytes(path);
//    }
//    public void deployDiagram(byte[] bytes, String processId) throws IOException {
//        DeploymentEvent deploymentEvent = zeebeClient.newDeployResourceCommand()
//                .addResourceBytes(bytes, processId)// Replace with your actual process ID
//                .send()
//                .join(); // Use join to wait synchronously for the result
//
//        System.out.println("Deployment successful. Versions: " + deploymentEvent.getProcesses());
//    }
//
//    public void startProcess(String processId) throws IOException {
//        ProcessInstanceEvent processInstanceEvent = zeebeClient.newCreateInstanceCommand()
//                .bpmnProcessId(processId)
//                .latestVersion()
//                .send()
//                .join();// Use join to wait synchronously for the result
//
//        System.out.println("Process instance started. Instance key: " + processInstanceEvent.getProcessInstanceKey());
//
//
//    }
//
//}
