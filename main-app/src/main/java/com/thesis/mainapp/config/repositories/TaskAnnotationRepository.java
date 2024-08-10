package com.thesis.mainapp.config.repositories;

import com.thesis.mainapp.domain.annotations.ProcessAnnotation;
import com.thesis.mainapp.domain.annotations.TaskAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskAnnotationRepository extends JpaRepository<TaskAnnotation, Long> {
    TaskAnnotation findByName(String name);
    TaskAnnotation findByNameAndProcess(String name, ProcessAnnotation process);

    // You can define custom database queries here if necessary
}
