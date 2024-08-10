package com.thesis.camundaintegration.domain;

public class StartProcessJson implements CamundaJson {
    private String processId;
    public String getprocessId() {
        return processId;
    }

    public void setprocessId(String processId) {
        this.processId = processId;
    }
}
