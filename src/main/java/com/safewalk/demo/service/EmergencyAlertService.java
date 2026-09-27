package com.safewalk.demo.service;

import com.safewalk.demo.model.EmergencyAlert;
import com.safewalk.demo.model.EscortSession;
import com.safewalk.demo.repository.EmergencyAlertRepository;
import com.safewalk.demo.repository.EscortSessionRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmergencyAlertService {

    private final EmergencyAlertRepository alertRepository;
    private final EscortSessionRepository sessionRepository;

    public EmergencyAlertService(
            EmergencyAlertRepository alertRepository,
            EscortSessionRepository sessionRepository) {

        this.alertRepository = alertRepository;
        this.sessionRepository = sessionRepository;
    }

    public EmergencyAlert triggerSOS(
            Long sessionId,
            String message) {

        EscortSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new RuntimeException("Escort session not found"));

        if (session.getStatus() != EscortSession.SessionStatus.ACTIVE) {
            throw new RuntimeException(
                    "SOS can only be triggered during an active session");
        }

        EmergencyAlert alert = new EmergencyAlert();

        alert.setSession(session);
        alert.setUser(
                session.getAssignment()
                        .getEscortRequest()
                        .getUser()
        );
        alert.setTriggeredAt(LocalDateTime.now());
        alert.setStatus(EmergencyAlert.AlertStatus.ACTIVE);
        alert.setMessage(message);

        session.setStatus(EscortSession.SessionStatus.EMERGENCY);

        sessionRepository.save(session);

        return alertRepository.save(alert);
    }

    public EmergencyAlert resolveAlert(Long alertId) {

        EmergencyAlert alert = alertRepository.findById(alertId)
                .orElseThrow(() ->
                        new RuntimeException("Emergency alert not found"));

        alert.setStatus(EmergencyAlert.AlertStatus.RESOLVED);

        return alertRepository.save(alert);
    }
}