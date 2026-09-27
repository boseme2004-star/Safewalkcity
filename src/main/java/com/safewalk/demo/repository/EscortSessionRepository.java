package com.safewalk.demo.repository;

import com.safewalk.demo.model.EscortSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscortSessionRepository extends JpaRepository<EscortSession, Long> {
}