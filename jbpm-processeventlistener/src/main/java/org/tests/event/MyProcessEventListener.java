package org.tests.event;

import org.jbpm.bpmn2.xml.XmlBPMNProcessDumper;
import org.kie.api.KieBase;
import org.kie.api.definition.process.Process;
import org.kie.api.definition.process.WorkflowProcess;
import org.kie.api.event.process.*;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.process.WorkflowProcessInstance;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class MyProcessEventListener extends DefaultProcessEventListener {
    private String nodeNotificationEndpoint = "http://jbpm-integration:5002/api/jbpm/notifyNodeChange";
    private String processNotificationEndpoint = "http://jbpm-integration:5002/api/jbpm/notifyProcessStart";
    private Set set = new HashSet<>();


    //    @Override
//    public void beforeVariableChanged(ProcessVariableChangedEvent event) {
//        System.out.println("beforeVariableChanged");
//        System.out.println(event.getVariableId());
//        System.out.println(event.getOldValue());
//        System.out.println(event.getNewValue());
//        String process = event.getProcessInstance().getProcessId();
//        Long processId = event.getProcessInstance().getId();
//        String variableName = event.getVariableId();
//        Object oldValue = event.getOldValue();
//        String newValue = event.getNewValue().toString();
//        if (newValue != null && !newValue.isEmpty()) {
//            try {
//                String payload = String.format("{\"process\": \"%s\", \"processId\": \"%s\",\"variableName\": \"%s\", \"newValue\": \"%s\"}",process,processId, variableName, newValue);
//                sendPostRequest(variableNotificationEndpoint, payload);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
    @Override
    public void beforeNodeLeft(ProcessNodeLeftEvent event) {
        System.out.println("beforeNodeLeft");
        String process = event.getProcessInstance().getProcessId();
        Long processId = event.getProcessInstance().getId();
        String taskName = event.getNodeInstance().getNode().getName();
        LocalDateTime time = LocalDateTime.now();
        try {
            String payload = String.format("{\"processName\": \"%s\", \"processKey\": \"%s\", \"taskName\": \"%s\", \"endTime\": \"%s\",\"platform\": \"jbpm\"}", process, processId, taskName,time);
            sendPostRequest(nodeNotificationEndpoint, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
        System.out.println("beforeNodeTriggered");
        String process = event.getProcessInstance().getProcessId();
        Long processId = event.getProcessInstance().getId();
        String taskName = event.getNodeInstance().getNode().getName();
        LocalDateTime time = LocalDateTime.now();

        try {
            String payload = String.format("{\"processName\": \"%s\", \"processKey\": \"%s\", \"taskName\": \"%s\", \"startTime\": \"%s\",\"platform\": \"jbpm\"}", process, processId, taskName,time);
            sendPostRequest(nodeNotificationEndpoint, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeProcessStarted(ProcessStartedEvent event) {

        String process = event.getProcessInstance().getProcessId();
        Long processId = event.getProcessInstance().getId();
        Integer status = 1;
        System.out.println("beforeProcessStarted");
        ProcessInstance processInstance = event.getProcessInstance();
        Process process2 = processInstance.getProcess();
        System.out.println(process2);
        String processXml = XmlBPMNProcessDumper.INSTANCE.dump((org.jbpm.workflow.core.WorkflowProcess) process2);
        processXml = processXml.replace("\"", "\\\"");
        processXml = processXml.replace("\n", "\\n");
        LocalDateTime time = LocalDateTime.now();


        System.out.println(processXml);

        try {
            String payload = String.format("{\"processName\": \"%s\", \"processKey\": \"%s\", \"status\": \"%s\",\"diagramXML\": \"%s\",\"startTime\": \"%s\",\"platform\": \"jbpm\"}", process, processId, status,processXml, time);
            sendPostRequest(processNotificationEndpoint, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        event.getProcessInstance().
//        sendPostRequest(notificationEndpoint, payload);
    }

    @Override
    public void afterProcessCompleted(ProcessCompletedEvent event) {

        String process = event.getProcessInstance().getProcessId();
        Long processId = event.getProcessInstance().getId();
        Integer status = event.getProcessInstance().getState();
        LocalDateTime time = LocalDateTime.now();

        System.out.println("afterProcessCompleted");
//        System.out.println(event.getProcessInstance().getProcessId());
//        System.out.println(event.getProcessInstance().getId());
//        System.out.println(event.getProcessInstance().getState());

        try {
            String payload = String.format("{\"processName\": \"%s\", \"processKey\": \"%s\", \"status\": \"%s\",\"endTime\": \"%s\",\"platform\": \"jbpm\"}", process, processId, status, time);
            sendPostRequest(processNotificationEndpoint, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPostRequest(String urlString, String payload) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = payload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Read the response if necessary
        try (java.io.BufferedReader br = new java.io.BufferedReader(
                new java.io.InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("Response: " + response.toString());
        }

        con.disconnect();
    }
}
