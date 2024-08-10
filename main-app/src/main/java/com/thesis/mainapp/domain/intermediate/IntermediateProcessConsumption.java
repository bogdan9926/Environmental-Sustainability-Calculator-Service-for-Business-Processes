package com.thesis.mainapp.domain.intermediate;

import com.thesis.mainapp.domain.Process;
import jakarta.persistence.*;


@Entity
@Table(name = "intermediate_process_consumption")
public class IntermediateProcessConsumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "process_id", nullable = false)
    private Process process;
    @Column(name = "task")
    private String task;
    @Column(name = "unit")
    private String unit;
    @Column(name = "value")
    private Double value;
    @Column(name = "fuel_type")
    private String fuelType;

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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
