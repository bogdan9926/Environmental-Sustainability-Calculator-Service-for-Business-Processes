package com.thesis.mainapp.config.repositories;

import com.thesis.mainapp.domain.FrontendData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrontendDataRepository extends JpaRepository<FrontendData, Long> {
}
