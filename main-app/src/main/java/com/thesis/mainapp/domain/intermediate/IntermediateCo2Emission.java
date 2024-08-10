package com.thesis.mainapp.domain.intermediate;

import com.thesis.mainapp.domain.Process;
import jakarta.persistence.*;


@Entity
@Table(name = "intermediate_co2_emission")
public class IntermediateCo2Emission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "process_id", nullable = false)
    private Process process;

    @Column(name = "task")
    private String task;

    @Column(name = "value")
    private Double value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
