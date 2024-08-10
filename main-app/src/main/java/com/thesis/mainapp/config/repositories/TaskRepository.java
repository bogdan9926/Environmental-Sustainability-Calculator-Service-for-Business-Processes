package com.thesis.mainapp.config.repositories;

import com.thesis.mainapp.domain.Process;
import com.thesis.mainapp.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TaskRepository extends JpaRepository<Task, Long> {
    public Task findByNameAndProcessAndEndTime(String name, Process process, LocalDateTime endTime);
}
