package com.thesis.mainapp.domain.annotations;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.thesis.mainapp.domain.id.TaskAnnotationId;
import jakarta.persistence.*;


@Entity
@IdClass(TaskAnnotationId.class)
@Table(name = "task_annotation")
public class TaskAnnotation {
    @Id
    @ManyToOne
    @JoinColumn(name = "process_id")
    @JsonBackReference
    private ProcessAnnotation process;

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "time_unit", nullable = false)
    private String timeUnit;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "task", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ResourceUsage> resourcesUsed;

    // getters and setters

    public ProcessAnnotation getProcess() {
        return process;
    }

    public void setProcess(ProcessAnnotation process) {
        this.process = process;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public List<ResourceUsage> getResourcesUsed() {
        return resourcesUsed;
    }

    public void setResourcesUsed(List<ResourceUsage> resourcesUsed) {
        this.resourcesUsed = resourcesUsed;
    }
}
