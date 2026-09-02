package com.safewalk.demo.service;

import com.safewalk.demo.model.EscortAssignment;
import com.safewalk.demo.model.EscortRequest;
import com.safewalk.demo.model.User;
import com.safewalk.demo.repository.EscortAssignmentRepository;
import com.safewalk.demo.repository.EscortRequestRepository;
import com.safewalk.demo.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EscortAssignmentService {

    private final EscortAssignmentRepository assignmentRepository;
    private final EscortRequestRepository escortRequestRepository;
    private final UserRepository userRepository;

    public EscortAssignmentService(
            EscortAssignmentRepository assignmentRepository,
            EscortRequestRepository escortRequestRepository,
            UserRepository userRepository) {

        this.assignmentRepository = assignmentRepository;
        this.escortRequestRepository = escortRequestRepository;
        this.userRepository = userRepository;
    }

    // Accept an escort request
    public EscortAssignment acceptRequest(Long requestId, Long escortId) {

        EscortRequest request = escortRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Escort request not found"));

        User escort = userRepository.findById(escortId)
                .orElseThrow(() -> new RuntimeException("Escort not found"));

        if (escort.getRole() != User.Role.VOLUNTEER) {
            throw new RuntimeException("User is not a volunteer");
        }

        if (request.getStatus() != EscortRequest.Status.PENDING) {
            throw new RuntimeException("Request is not pending");
        }

        EscortAssignment assignment = new EscortAssignment();

        assignment.setEscortRequest(request);
        assignment.setEscort(escort);
        assignment.setAssignedAt(LocalDateTime.now());
        assignment.setStatus(EscortAssignment.AssignmentStatus.ASSIGNED);

        request.setStatus(EscortRequest.Status.ACCEPTED);
        escortRequestRepository.save(request);

        return assignmentRepository.save(assignment);
    }

    // Start an escort
    public EscortAssignment startEscort(Long assignmentId) {

        EscortAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        if (assignment.getStatus() != EscortAssignment.AssignmentStatus.ASSIGNED) {
            throw new RuntimeException("Escort cannot be started");
        }

        assignment.setStatus(EscortAssignment.AssignmentStatus.STARTED);

        EscortRequest request = assignment.getEscortRequest();
        request.setStatus(EscortRequest.Status.IN_PROGRESS);

        escortRequestRepository.save(request);

        return assignmentRepository.save(assignment);
    }

    // Complete an escort
    public EscortAssignment completeEscort(Long assignmentId) {

        EscortAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        if (assignment.getStatus() != EscortAssignment.AssignmentStatus.STARTED) {
            throw new RuntimeException("Escort has not been started");
        }

        assignment.setStatus(EscortAssignment.AssignmentStatus.COMPLETED);

        EscortRequest request = assignment.getEscortRequest();
        request.setStatus(EscortRequest.Status.COMPLETED);

        escortRequestRepository.save(request);

        return assignmentRepository.save(assignment);
    }

    // Cancel an assignment
    public EscortAssignment cancelAssignment(Long assignmentId) {

        EscortAssignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setStatus(EscortAssignment.AssignmentStatus.CANCELLED);

        EscortRequest request = assignment.getEscortRequest();
        request.setStatus(EscortRequest.Status.CANCELLED);

        escortRequestRepository.save(request);

        return assignmentRepository.save(assignment);
    }
}