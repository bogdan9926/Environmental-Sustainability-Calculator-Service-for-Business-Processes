package com.thesis.mainapp.domain.annotations;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.thesis.mainapp.domain.id.ResourceAnnotationId;
import com.thesis.mainapp.domain.id.TaskAnnotationId;
import jakarta.persistence.*;


@Entity
@IdClass(ResourceAnnotationId.class)

@Table(name = "resource_annotation")
public class ResourceAnnotation {
    public final static String ATOMIC="atomic";
    public final static String SHARED="shared";
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "fuel_per_use")
    private String fuelPerUse;

    @Column(name = "fuel_unit")
    private String fuelUnit;

    @Column(name = "time_unit")
    private String timeUnit;

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "process_id")
    @JsonBackReference
    private ProcessAnnotation process;

    // getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getFuelPerUse() {
        return fuelPerUse;
    }

    public void setFuelPerUse(String fuelPerUse) {
        this.fuelPerUse = fuelPerUse;
    }

    public String getFuelUnit() {
        return fuelUnit;
    }

    public void setFuelUnit(String unit) {
        this.fuelUnit = unit;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public ProcessAnnotation getProcess() {
        return process;
    }

    public void setProcess(ProcessAnnotation process) {
        this.process = process;
    }
}
