package com.thesis.mainapp.domain.annotations;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
@Table(name = "resource_usage")
public class ResourceUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumns({
            @JoinColumn(name = "process_id", referencedColumnName = "process_id"),
            @JoinColumn(name = "task_name", referencedColumnName = "name")
    })
    @JsonBackReference
    private TaskAnnotation task;
    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "time_used")
    private String timeUsed;

    @Column(name = "unit")
    private String unit;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskAnnotation getTask() {
        return task;
    }

    public void setTask(TaskAnnotation task) {
        this.task = task;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(String timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
