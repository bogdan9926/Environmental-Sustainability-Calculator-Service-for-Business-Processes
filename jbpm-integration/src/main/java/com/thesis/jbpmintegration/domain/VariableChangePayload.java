package com.thesis.jbpmintegration.domain;

public class VariableChangePayload {
    private String variableName;
    private String variableValue;

    public VariableChangePayload(String variableName, String variableValue) {
        this.variableName = variableName;
        this.variableValue = variableValue;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }
}
