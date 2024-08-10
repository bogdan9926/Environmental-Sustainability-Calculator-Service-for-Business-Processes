package com.thesis.mainapp.domain.annotations;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


@Entity
@Table(name = "process_annotation")
public class ProcessAnnotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "frequency_per_month")
    private Integer frequencyPerMonth;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "process", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TaskAnnotation> tasks;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "process", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ResourceAnnotation> resources;

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFrequencyPerMonth() {
        return frequencyPerMonth;
    }

    public void setFrequencyPerMonth(Integer frequencyPerMonth) {
        this.frequencyPerMonth = frequencyPerMonth;
    }

    public List<TaskAnnotation> getTasks() {
        return tasks;
    }

    public Optional<TaskAnnotation> getTask(String taskName) {
        return tasks.stream()
                .filter(task -> taskName.equals(task.getName()))
                .findFirst();
    }

    public void setTasks(List<TaskAnnotation> tasks) {
        this.tasks = tasks;
        if (tasks != null) {
            for (TaskAnnotation task : tasks) {
                task.setProcess(this);
            }
        }
    }

    public List<ResourceAnnotation> getResources() {
        return resources;
    }

    public Optional<ResourceAnnotation> getResource(String resourceName) {
        return resources.stream()
                .filter(resource -> name.equals(resource.getName()))
                .findFirst();
    }

    public void setResources(List<ResourceAnnotation> resources) {
        this.resources = resources;
        if (resources != null) {
            for (ResourceAnnotation resource : resources) {
                resource.setProcess(this);
            }
        }
    }
}