package com.thesis.mainapp.config.repositories;

import com.thesis.mainapp.domain.FuelEmissionFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelEmissionFactorRepository extends JpaRepository<FuelEmissionFactor, Long> {
    FuelEmissionFactor findByUnit(String unit);
    FuelEmissionFactor findByFuelType(String fuelType);
    // You can define custom database queries here if necessary
}
