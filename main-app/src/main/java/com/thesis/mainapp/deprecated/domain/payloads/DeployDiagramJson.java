package com.thesis.mainapp.deprecated.domain.payloads;

public class DeployDiagramJson {
        private String fileLocation;

        private String processId;


    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }
}
