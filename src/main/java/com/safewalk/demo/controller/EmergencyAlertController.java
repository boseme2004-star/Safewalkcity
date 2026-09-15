package com.safewalk.demo.controller;

import com.safewalk.demo.model.EmergencyAlert;
import com.safewalk.demo.service.EmergencyAlertService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emergency")
public class EmergencyAlertController {

    private final EmergencyAlertService emergencyAlertService;

    public EmergencyAlertController(
            EmergencyAlertService emergencyAlertService) {

        this.emergencyAlertService = emergencyAlertService;
    }

    @PostMapping("/sos/{sessionId}")
    public ResponseEntity<EmergencyAlert> triggerSOS(
            @PathVariable Long sessionId,
            @RequestParam(required = false) String message) {

        if (message == null || message.isBlank()) {
            message = "Emergency SOS triggered";
        }

        return ResponseEntity.ok(
                emergencyAlertService.triggerSOS(
                        sessionId,
                        message
                )
        );
    }

    @PostMapping("/resolve/{alertId}")
    public ResponseEntity<EmergencyAlert> resolveAlert(
            @PathVariable Long alertId) {

        return ResponseEntity.ok(
                emergencyAlertService.resolveAlert(alertId)
        );
    }
}