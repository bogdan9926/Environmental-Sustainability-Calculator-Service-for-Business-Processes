package com.thesis.mainapp.domain;

import java.time.LocalDateTime;
import jakarta.persistence.*;


@Entity
@Table(name = "process")
public class Process {
    public static final String PLATFORM_JBPM ="jbpm";
    public static final String PLATFORM_CAMUNDA ="camunda";
    public static final String PLATFORM_ACTIVITI ="activiti";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "platform")
    private String platform;

    @Column(name = "process_name")
    private String processName;

    @Column(name = "process_key")
    private String processKey;

    @Column(name = "start_time")
    private LocalDateTime startTime;


    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status")
    private String status;
    @Column(name = "diagram_xml")
    private String diagramXML;
    // Getters and Setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getDiagramXML() {
        return diagramXML;
    }

    public void setDiagramXML(String diagramXML) {
        this.diagramXML = diagramXML;
    }
}

