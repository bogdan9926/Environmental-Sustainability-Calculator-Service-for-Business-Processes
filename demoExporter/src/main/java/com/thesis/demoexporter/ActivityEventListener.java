package com.thesis.demoexporter;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ActivityEventListener implements ExecutionListener {


    @Override
    public void notify(DelegateExecution execution) {
        String eventName = execution.getEventName();
        String activityName = execution.getCurrentActivityName();
        String processInstanceId = execution.getProcessInstanceId();


    }
}
