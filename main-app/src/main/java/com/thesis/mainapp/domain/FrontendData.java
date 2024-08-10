package com.thesis.mainapp.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "frontend_data")
public class FrontendData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "process_name")
    private String processName;

    @Column(name = "process_instance")
    private String processInstance;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "co2_emission_value")
    private String co2EmissionValue;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "diagram_xml")
    private String  diagramXML;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(String processInstance) {
        this.processInstance = processInstance;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCo2EmissionValue() {
        return co2EmissionValue;
    }

    public void setCo2EmissionValue(String co2EmissionValue) {
        this.co2EmissionValue = co2EmissionValue;
    }

    public String getDiagramXML() {
        return diagramXML;
    }

    public void setDiagramXML(String diagramXML) {
        this.diagramXML = diagramXML;
    }
}
