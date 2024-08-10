package com.thesis.mainapp.config.repositories;

import com.thesis.mainapp.domain.annotations.AnnotationJson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnotationJsonRepository extends JpaRepository<AnnotationJson, Long> {
}
