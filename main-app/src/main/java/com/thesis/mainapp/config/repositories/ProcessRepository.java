package com.thesis.mainapp.config.repositories;

import com.thesis.mainapp.domain.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {
    Process findByProcessNameAndProcessKey(String processName, String processKey);
}
