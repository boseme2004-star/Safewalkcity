package com.safewalk.demo.service;

import com.safewalk.demo.model.EscortAssignment;
import com.safewalk.demo.model.EscortSession;
import com.safewalk.demo.repository.EscortAssignmentRepository;
import com.safewalk.demo.repository.EscortSessionRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EscortSessionService {

    private final EscortSessionRepository sessionRepository;
    private final EscortAssignmentRepository assignmentRepository;

    public EscortSessionService(
            EscortSessionRepository sessionRepository,
            EscortAssignmentRepository assignmentRepository) {

        this.sessionRepository = sessionRepository;
        this.assignmentRepository = assignmentRepository;
    }

    // Create a new escort session
    public EscortSession createSession(Long assignmentId) {

        EscortAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() ->
                        new RuntimeException("Escort assignment not found"));

        EscortSession session = new EscortSession();

        session.setAssignment(assignment);
        session.setStatus(EscortSession.SessionStatus.NOT_STARTED);

        return sessionRepository.save(session);
    }

    // Start the journey
    public EscortSession startSession(Long sessionId) {

        EscortSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new RuntimeException("Escort session not found"));

        if (session.getStatus() != EscortSession.SessionStatus.NOT_STARTED) {
            throw new RuntimeException("Session cannot be started");
        }

        session.setStartTime(LocalDateTime.now());
        session.setStatus(EscortSession.SessionStatus.ACTIVE);

        return sessionRepository.save(session);
    }


    public EscortSession confirmArrival(Long sessionId) {

    EscortSession session = sessionRepository.findById(sessionId)
            .orElseThrow(() ->
                    new RuntimeException("Escort session not found"));

    if (session.getStatus() != EscortSession.SessionStatus.ACTIVE) {
        throw new RuntimeException("Arrival can only be confirmed for an active session");
    }

    session.setEndTime(LocalDateTime.now());
    session.setStatus(EscortSession.SessionStatus.ARRIVED);

    return sessionRepository.save(session);
}

  public EscortSession completeSession(Long sessionId) {

    EscortSession session = sessionRepository.findById(sessionId)
            .orElseThrow(() ->
                    new RuntimeException("Escort session not found"));

    if (session.getStatus() != EscortSession.SessionStatus.ARRIVED) {
        throw new RuntimeException(
                "Session can only be completed after arrival");
    }

    session.setStatus(EscortSession.SessionStatus.COMPLETED);

    return sessionRepository.save(session);
}

public long getJourneyDuration(Long sessionId) {

    EscortSession session = sessionRepository.findById(sessionId)
            .orElseThrow(() ->
                    new RuntimeException("Escort session not found"));

    if (session.getStartTime() == null) {
        throw new RuntimeException("Journey has not started");
    }

    LocalDateTime endTime = session.getEndTime() != null
            ? session.getEndTime()
            : LocalDateTime.now();

    return java.time.Duration.between(
            session.getStartTime(),
            endTime
    ).getSeconds();
}

 }