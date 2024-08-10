package com.thesis.jbpmintegration.domain;

public class ProcessStartRequest {

    private String containerId;
    private String processId;

    // Constructors, getters, and setters

    public ProcessStartRequest() {
    }

    // Getters
    public String getProcessId() {
        return processId;
    }

    public String getContainerId() {
        return containerId;
    }


    // Setters
    public void setProcessId(String containerName) {
        this.processId = containerName;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
}