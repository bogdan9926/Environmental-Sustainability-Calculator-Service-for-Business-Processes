package com.thesis.camundaintegration.domain;

public class DeployDiagramJson implements CamundaJson {
        private String fileLocation;

        private String processId;


    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getprocessId() {
        return processId;
    }

    public void setprocessId(String processId) {
        this.processId = processId;
    }
}
