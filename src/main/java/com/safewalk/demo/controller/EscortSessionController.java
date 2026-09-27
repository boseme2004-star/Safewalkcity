package com.safewalk.demo.controller;

import com.safewalk.demo.model.EscortSession;
import com.safewalk.demo.service.EscortSessionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/escort-sessions")
public class EscortSessionController {

    private final EscortSessionService sessionService;

    public EscortSessionController(EscortSessionService sessionService) {
        this.sessionService = sessionService;
    }

    // Create an escort session
    @PostMapping("/create/{assignmentId}")
    public ResponseEntity<EscortSession> createSession(
            @PathVariable Long assignmentId) {

        EscortSession session =
                sessionService.createSession(assignmentId);

        return ResponseEntity.ok(session);
    }

    // Start the escort session
    @PostMapping("/{sessionId}/start")
    public ResponseEntity<EscortSession> startSession(
            @PathVariable Long sessionId) {

        EscortSession session =
                sessionService.startSession(sessionId);

        return ResponseEntity.ok(session);
    }


    @PostMapping("/{sessionId}/arrive")
    public ResponseEntity<EscortSession> confirmArrival(
        @PathVariable Long sessionId) {

    EscortSession session =
            sessionService.confirmArrival(sessionId);

    return ResponseEntity.ok(session);
}
 
@PostMapping("/{sessionId}/complete")
public ResponseEntity<EscortSession> completeSession(
        @PathVariable Long sessionId) {

    EscortSession session =
            sessionService.completeSession(sessionId);

    return ResponseEntity.ok(session);
}

@GetMapping("/{sessionId}/timer")
public ResponseEntity<Long> getJourneyTimer(
        @PathVariable Long sessionId) {

    long duration =
            sessionService.getJourneyDuration(sessionId);

    return ResponseEntity.ok(duration);
}

 } 

 