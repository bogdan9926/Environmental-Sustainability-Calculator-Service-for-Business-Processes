package com.thesis.mainapp.domain.deprecated;

public class ProcessStartRequest {
    private String platform;
    private  String containerId;
    private String processId;

    public ProcessStartRequest() {
    }


    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
