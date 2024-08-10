package com.thesis.mainapp.config.repositories;

import com.thesis.mainapp.domain.annotations.ProcessAnnotation;
import com.thesis.mainapp.domain.annotations.ResourceAnnotation;
import com.thesis.mainapp.domain.annotations.TaskAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceAnnotationRepository extends JpaRepository<ResourceAnnotation, Long> {
    ResourceAnnotation findByNameAndProcess(String name, ProcessAnnotation processAnnotation);

    // You can define custom database queries here if necessary
}
