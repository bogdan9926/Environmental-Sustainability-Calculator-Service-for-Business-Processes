package com.thesis.jbpmintegration.domain;

public class ContainerCreationRequest {

    private String containerName;
    private String containerId;
    private String groupId;
    private String artifactId;
    private String version;

    // Constructors, getters, and setters

    public ContainerCreationRequest() {
    }

    // Getters
    public String getContainerName() {
        return containerName;
    }

    public String getContainerId() {
        return containerId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    // Setters
    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
