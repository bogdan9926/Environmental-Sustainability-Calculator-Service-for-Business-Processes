package com.thesis.mainapp.deprecated.domain.payloads;

import org.springframework.web.multipart.MultipartFile;

public class DiagramUploadRequest {
    private String platform;
    private MultipartFile diagram;

    private String processId;

    public DiagramUploadRequest() {
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }




    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public MultipartFile getDiagram() {
        return diagram;
    }

    public void setDiagram(MultipartFile diagram) {
        this.diagram = diagram;
    }
}
