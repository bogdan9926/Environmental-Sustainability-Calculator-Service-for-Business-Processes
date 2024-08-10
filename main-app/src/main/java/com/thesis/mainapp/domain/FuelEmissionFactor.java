package com.thesis.mainapp.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "fuel_emission_factor")
public class FuelEmissionFactor {
    public static final String CUBICMETERS = "m3";
    public static final String TONNES = "t";
    public static final String KILOGRAMS = "kg";
    public static final String LITRES = "l";
    public static final String KWH = "kwh";
    public static final String WH = "wh";

    @Id
    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "unit")
    private String unit;

    @Column(name = "factor")
    private Double factor;

    // Constructors, getters, and setters

    public FuelEmissionFactor() {
    }

    public FuelEmissionFactor(String fuelType, String unit, double factor) {
        this.fuelType = fuelType;
        this.unit = unit;
        this.factor = factor;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    public Double calculateEmissionAmount(Double amount, String unit) {
        if(Objects.equals(this.unit, unit))
            return factor*amount;
        if(Objects.equals(this.unit, TONNES) && Objects.equals(unit, KILOGRAMS))
            return factor*amount/1000;
        if(Objects.equals(this.unit, KWH) && Objects.equals(unit, WH))
            return factor*amount/1000;
        if(Objects.equals(unit, TONNES) && Objects.equals(this.unit, KILOGRAMS))
            return factor*amount*1000;
        if(Objects.equals(unit, KWH) && Objects.equals(this.unit, WH))
            return factor*amount*1000;
        throw new RuntimeException("Invalid unit");
    }
}

