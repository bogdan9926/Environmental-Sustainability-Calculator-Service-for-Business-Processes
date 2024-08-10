package com.thesis.mainapp.config.repositories;

import com.thesis.mainapp.domain.annotations.ProcessAnnotation;
import com.thesis.mainapp.domain.annotations.ResourceAnnotation;
import com.thesis.mainapp.domain.annotations.ResourceUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceUsageRepository extends JpaRepository<ResourceUsage, Long> {
    void deleteById(long id);
    // You can define custom database queries here if necessary
}

