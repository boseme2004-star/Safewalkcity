package com.safewalk.demo.repository;

import com.safewalk.demo.model.EscortRequest;
import com.safewalk.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscortRequestRepository
        extends JpaRepository<EscortRequest, Long> {

    List<EscortRequest> findByUser(User user);
}