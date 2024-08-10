package com.thesis.mainapp.config.repositories;

import com.thesis.mainapp.domain.annotations.ProcessAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessAnnotationRepository extends JpaRepository<ProcessAnnotation, Long> {
    Optional<ProcessAnnotation> findByName(String name);

//    ProcessAnnotation findByName(String name);

    // You can define custom database queries here if necessary
}
