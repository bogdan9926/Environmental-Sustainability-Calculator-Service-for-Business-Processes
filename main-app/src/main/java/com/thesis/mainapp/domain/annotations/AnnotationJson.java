package com.thesis.mainapp.domain.annotations;

import jakarta.persistence.*;

@Entity
@Table(name = "annotation_json")
public class AnnotationJson {
    @Id
    @Column(name = "process_name")
    private String processName;

    @Column(name = "data")
    private String data;

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
