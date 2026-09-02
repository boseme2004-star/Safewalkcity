package com.safewalk.demo.controller;

import com.safewalk.demo.model.EscortAssignment;
import com.safewalk.demo.service.EscortAssignmentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/escort-assignments")
public class EscortAssignmentController {

    private final EscortAssignmentService assignmentService;

    public EscortAssignmentController(EscortAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    // Accept an escort request
    @PostMapping("/accept/{requestId}/{escortId}")
    public ResponseEntity<EscortAssignment> acceptRequest(
            @PathVariable Long requestId,
            @PathVariable Long escortId) {

        EscortAssignment assignment =
                assignmentService.acceptRequest(requestId, escortId);

        return ResponseEntity.ok(assignment);
    }

    // Start an escort
    @PostMapping("/{assignmentId}/start")
    public ResponseEntity<EscortAssignment> startEscort(
            @PathVariable Long assignmentId) {

        EscortAssignment assignment =
                assignmentService.startEscort(assignmentId);

        return ResponseEntity.ok(assignment);
    }

    // Complete an escort
    @PostMapping("/{assignmentId}/complete")
    public ResponseEntity<EscortAssignment> completeEscort(
            @PathVariable Long assignmentId) {

        EscortAssignment assignment =
                assignmentService.completeEscort(assignmentId);

        return ResponseEntity.ok(assignment);
    }

    // Cancel an assignment
    @PostMapping("/{assignmentId}/cancel")
    public ResponseEntity<EscortAssignment> cancelAssignment(
            @PathVariable Long assignmentId) {

        EscortAssignment assignment =
                assignmentService.cancelAssignment(assignmentId);

        return ResponseEntity.ok(assignment);
    }
}