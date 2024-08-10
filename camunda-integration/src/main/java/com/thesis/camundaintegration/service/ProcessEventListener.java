//package com.thesis.camundaintegration.service;
//
//import com.google.type.DateTime;
//import io.camunda.zeebe.client.ZeebeClient;
//import io.camunda.zeebe.client.api.response.ActivatedJob;
//import io.camunda.zeebe.client.api.worker.JobClient;
//import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class ProcessEventListener {
//
//    private final ZeebeClient zeebeClient;
//
//    @Autowired
//    private RabbitService rabbitService;
//
//    @Value("camundaReturnChannel")
//    private String returnChannel;
//
//    public ProcessEventListener(ZeebeClient zeebeClient) {
//        this.zeebeClient = zeebeClient;
////        startListening();
////        startListeningUserTask();
//    }
//
//    @ZeebeWorker(type = "io.camunda.zeebe:userTask")
//    public void userTask(final JobClient client, final ActivatedJob job) {
//        // Assume form data is passed as variables and process them here
//        Map<String, Object> variables = new HashMap<>();
//        String taskName = job.getElementId();
//        String processId = job.getBpmnProcessId();
//        Long processInstanceKey = job.getProcessInstanceKey();
//        variables.put("taskName", taskName);
//        variables.put("processName", processId);
//        variables.put("processKey", processInstanceKey);
//
//        LocalDateTime timestamp = LocalDateTime.now();
//        variables.put("timestamp",timestamp);
//        JSONObject jsonObject = new JSONObject(variables);
//
//        System.out.println("Processing data: " + variables);
//        rabbitService.sendMessage(returnChannel, jsonObject.toString());
//    }
//
//    @ZeebeWorker(type = "task", autoComplete = true)
//    public void serviceTask(final JobClient client, final ActivatedJob job) {
//
//        Map<String, Object> variables = new HashMap<>();
//        String taskName = job.getElementId();
//        String processId = job.getBpmnProcessId();
//        Long processInstanceKey = job.getProcessInstanceKey();
//        variables.put("taskName", taskName);
//        variables.put("processName", processId);
//        variables.put("processKey", processInstanceKey);
//        LocalDateTime timestamp = LocalDateTime.now();
//        variables.put("timestamp",timestamp);
//        JSONObject jsonObject = new JSONObject(variables);
//        // Assume form data is passed as variables and process them here
//        System.out.println("Activity started: " + job.getElementId());
//        rabbitService.sendMessage(returnChannel, jsonObject.toString());
//        // Example: Further business logic goes here
//    }
//
//    @ZeebeWorker(type = "end-event", autoComplete = true)
//    public void handleMessageEndEvent(final JobClient client, final ActivatedJob job) {
//        // Your code here to handle the message end event
//        Map<String, Object> variables = new HashMap<>();
//        String processId = job.getBpmnProcessId();
//        Long processInstanceKey = job.getProcessInstanceKey();
//        variables.put("processName", processId);
//        variables.put("processKey", processInstanceKey);
//        variables.put("status", "2");
//
//        JSONObject jsonObject = new JSONObject(variables);
//        // Assume form data is passed as variables and process them here
//        System.out.println("Process ended: " + job.getProcessInstanceKey());
//        rabbitService.sendMessage(returnChannel, jsonObject.toString());
//    }
//    @ZeebeWorker(type = "start-event", autoComplete = true)
//    public void handleMessageStartEvent(final JobClient client, final ActivatedJob job) {
//        // Your code here to handle the message end event
//        Map<String, Object> variables = new HashMap<>();
//        String processId = job.getBpmnProcessId();
//        Long processInstanceKey = job.getProcessInstanceKey();
//        variables.put("processName", processId);
//        variables.put("processKey", processInstanceKey);
//        variables.put("status", "1");
//
//        JSONObject jsonObject = new JSONObject(variables);
//        // Assume form data is passed as variables and process them here
//        System.out.println("Process started: " + job.getProcessInstanceKey());
//        rabbitService.sendMessage(returnChannel, jsonObject.toString());
//    }
//
//
//
//
//}
//
