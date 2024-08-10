package com.thesis.mainapp.domain.id;

import java.io.Serializable;
import java.util.Objects;

public class ResourceAnnotationId implements Serializable {
    private Long process; // This will refer to process_id
    private String name;

    // Default constructor
    public ResourceAnnotationId() {}

    // Constructor with fields
    public ResourceAnnotationId(Long process, String name) {
        this.process = process;
        this.name = name;
    }

    // Getters and setters
    public Long getProcess() {
        return process;
    }

    public void setProcess(Long process) {
        this.process = process;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceAnnotationId that = (ResourceAnnotationId) o;
        return Objects.equals(process, that.process) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(process, name);
    }
}

