package com.safewalk.demo.service;

import com.safewalk.demo.model.EmergencyAlert;
import com.safewalk.demo.model.EscortSession;
import com.safewalk.demo.repository.EmergencyAlertRepository;
import com.safewalk.demo.repository.EscortSessionRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AutomaticAlertService {

    private final EscortSessionRepository sessionRepository;
    private final EmergencyAlertRepository alertRepository;

    public AutomaticAlertService(
            EscortSessionRepository sessionRepository,
            EmergencyAlertRepository alertRepository) {

        this.sessionRepository = sessionRepository;
        this.alertRepository = alertRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void checkActiveSessions() {

        List<EscortSession> sessions =
                sessionRepository.findAll();

        for (EscortSession session : sessions) {

            if (session.getStatus()
                    == EscortSession.SessionStatus.ACTIVE
                    && session.getStartTime() != null) {

                LocalDateTime alertTime =
                        session.getStartTime().plusMinutes(1);

                if (LocalDateTime.now().isAfter(alertTime)) {

                    EmergencyAlert alert =
                            new EmergencyAlert();

                    alert.setSession(session);

                    alert.setUser(
                            session.getAssignment()
                                    .getEscortRequest()
                                    .getUser()
                    );

                    alert.setTriggeredAt(LocalDateTime.now());

                    alert.setStatus(
                            EmergencyAlert.AlertStatus.ACTIVE
                    );

                    alert.setMessage(
                            "Automatic safety alert: " +
                            "arrival has not been confirmed."
                    );

                    session.setStatus(
                            EscortSession.SessionStatus.EMERGENCY
                    );

                    sessionRepository.save(session);
                    alertRepository.save(alert);
                }
            }
        }
    }
}