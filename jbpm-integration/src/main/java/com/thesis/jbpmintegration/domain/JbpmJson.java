package com.thesis.jbpmintegration.domain;

public class JbpmJson {
        private String fileLocation;

        private String processName;
        private String containerName;
        private String containerId;
        private String groupId;
        private String artifactId;
        private String version;

    public String getFileLocation() {
        return fileLocation;
    }
    public String getProcessName() {
        return processName;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
