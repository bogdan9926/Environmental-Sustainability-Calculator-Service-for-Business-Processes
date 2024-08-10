package com.thesis.mainapp.config.repositories;

import com.thesis.mainapp.domain.intermediate.IntermediateProcessConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntermediateProcessConsumptionRepository extends JpaRepository<IntermediateProcessConsumption, Long> {

    // You can define custom database queries here if necessary
}
