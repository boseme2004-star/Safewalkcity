package com.safewalk.demo.repository;

import com.safewalk.demo.model.EmergencyAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyAlertRepository
        extends JpaRepository<EmergencyAlert, Long> {
}