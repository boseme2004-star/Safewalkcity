package com.safewalk.demo.dto;

import com.safewalk.demo.model.EscortAssignment;
import com.safewalk.demo.model.EscortRequest;

import java.time.LocalDate;
import java.time.LocalTime;

public class EscortAssignmentResponseDTO {

    private Long id;
    private Long requestId;
    private Long userId;
    private String pickupLocation;
    private String destination;
    private LocalDate date;
    private LocalTime time;
    private EscortRequest.Status requestStatus;
    private String notes;

    private UserResponseDTO escort;

    private String assignedAt;
    private EscortAssignment.AssignmentStatus status;

    public EscortAssignmentResponseDTO(EscortAssignment assignment) {

        this.id = assignment.getId();

        EscortRequest request = assignment.getEscortRequest();

        this.requestId = request.getId();
        this.userId = request.getUser().getId();
        this.pickupLocation = request.getPickupLocation();
        this.destination = request.getDestination();
        this.date = request.getDate();
        this.time = request.getTime();
        this.requestStatus = request.getStatus();
        this.notes = request.getNotes();

        this.escort = new UserResponseDTO(assignment.getEscort());

        this.assignedAt = assignment.getAssignedAt() != null
                ? assignment.getAssignedAt().toString()
                : null;

        this.status = assignment.getStatus();
    }

    public Long getId() {
        return id;
    }

    public Long getRequestId() {
        return requestId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public EscortRequest.Status getRequestStatus() {
        return requestStatus;
    }

    public String getNotes() {
        return notes;
    }

    public UserResponseDTO getEscort() {
        return escort;
    }

    public String getAssignedAt() {
        return assignedAt;
    }

    public EscortAssignment.AssignmentStatus getStatus() {
        return status;
    }
}