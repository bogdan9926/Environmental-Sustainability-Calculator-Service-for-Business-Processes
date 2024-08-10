package com.example.workflow.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Random;

public class ServiceTaskExecutionWithDelay implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("delegate");
        Random random = new Random();
        int waitTime = 1 + random.nextInt(4);
        System.out.println("Waiting for " + waitTime + " seconds...");
        Thread.sleep(waitTime * 1000);

    }
}