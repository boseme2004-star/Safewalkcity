package com.safewalk.demo.controller;

import com.safewalk.demo.dto.EscortAssignmentResponseDTO;
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
    public ResponseEntity<EscortAssignmentResponseDTO> acceptRequest(
            @PathVariable Long requestId,
            @PathVariable Long escortId) {

        EscortAssignment assignment =
                assignmentService.acceptRequest(requestId, escortId);

        return ResponseEntity.ok(
                new EscortAssignmentResponseDTO(assignment)
        );
    }

    // Start an escort
    @PostMapping("/{assignmentId}/start")
    public ResponseEntity<EscortAssignmentResponseDTO> startEscort(
            @PathVariable Long assignmentId) {

        EscortAssignment assignment =
                assignmentService.startEscort(assignmentId);

        return ResponseEntity.ok(
                new EscortAssignmentResponseDTO(assignment)
        );
    }

    // Complete an escort
    @PostMapping("/{assignmentId}/complete")
    public ResponseEntity<EscortAssignmentResponseDTO> completeEscort(
            @PathVariable Long assignmentId) {

        EscortAssignment assignment =
                assignmentService.completeEscort(assignmentId);

        return ResponseEntity.ok(
                new EscortAssignmentResponseDTO(assignment)
        );
    }

    // Cancel an assignment
    @PostMapping("/{assignmentId}/cancel")
    public ResponseEntity<EscortAssignmentResponseDTO> cancelAssignment(
            @PathVariable Long assignmentId) {

        EscortAssignment assignment =
                assignmentService.cancelAssignment(assignmentId);

        return ResponseEntity.ok(
                new EscortAssignmentResponseDTO(assignment)
        );
    }
}