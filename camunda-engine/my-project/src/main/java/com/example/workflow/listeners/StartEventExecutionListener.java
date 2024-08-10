package com.example.workflow.listeners;

import com.example.workflow.ApiService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.util.IoUtil;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@Component
public class StartEventExecutionListener implements ExecutionListener {
    @Autowired
    private ApiService apiService;
    @Value("${startUrl}")
    private String url;    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        System.out.println("start");
        RepositoryService repositoryService = delegateExecution.getProcessEngineServices().getRepositoryService();
        ProcessDefinition process = repositoryService.getProcessDefinition(delegateExecution.getProcessDefinitionId());
        String processName = process.getKey();
        String taskName = delegateExecution.getCurrentActivityName();
        String processKey = delegateExecution.getProcessInstanceId();
        LocalDateTime startTime = LocalDateTime.now();
        Map<String, Object> payload = new HashMap<>();
        payload.put("status", "1");
        payload.put("processKey", processKey);
        payload.put("startTime", startTime);
        payload.put("processName", processName);
        payload.put("platform", "camunda");

        String deploymentId = process.getDeploymentId();
        String diagramResourceName = process.getResourceName();
        String processDefinitionId = delegateExecution.getProcessDefinitionId();

        InputStream bpmnXmlStream = repositoryService.getProcessModel(processDefinitionId);

        if (bpmnXmlStream != null) {
            String bpmnXml = new String(IoUtil.readInputStream(bpmnXmlStream, "BPMN XML"), "UTF-8");
            // Do something with the BPMN XML
            System.out.println("BPMN XML: " + bpmnXml);
            payload.put("diagramXML", bpmnXml);

            String response = apiService.sendPostRequest(url, payload);
            System.out.println("Response: " + response);
        } else {
            throw new RuntimeException("BPMN model not found for process definition id: " + processDefinitionId);
        }



    }
}
