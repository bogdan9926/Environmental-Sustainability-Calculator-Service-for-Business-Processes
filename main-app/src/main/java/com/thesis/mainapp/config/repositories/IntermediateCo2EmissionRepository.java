package com.thesis.mainapp.config.repositories;

import com.thesis.mainapp.domain.Process;
import com.thesis.mainapp.domain.intermediate.IntermediateCo2Emission;
import com.thesis.mainapp.domain.intermediate.IntermediateProcessConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntermediateCo2EmissionRepository extends JpaRepository<IntermediateCo2Emission, Long> {
    List<IntermediateCo2Emission> findAllByProcess(Process process);
    // You can define custom database queries here if necessary
}
