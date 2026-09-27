package com.safewalk.demo.repository;

import com.safewalk.demo.model.EscortAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscortAssignmentRepository extends JpaRepository<EscortAssignment, Long> {
}