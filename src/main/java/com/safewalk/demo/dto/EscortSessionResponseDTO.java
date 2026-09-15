package com.safewalk.demo.dto;

import com.safewalk.demo.model.EscortSession;

import java.time.LocalDateTime;

public class EscortSessionResponseDTO {

    private Long id;
    private Long assignmentId;
    private Long requestId;
    private Long userId;
    private UserResponseDTO escort;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EscortSession.SessionStatus status;

    public EscortSessionResponseDTO(EscortSession session) {

        this.id = session.getId();

        this.assignmentId = session.getAssignment().getId();

        this.requestId =
                session.getAssignment()
                        .getEscortRequest()
                        .getId();

        this.userId =
                session.getAssignment()
                        .getEscortRequest()
                        .getUser()
                        .getId();

        this.escort =
                new UserResponseDTO(
                        session.getAssignment().getEscort()
                );

        this.startTime = session.getStartTime();
        this.endTime = session.getEndTime();
        this.status = session.getStatus();
    }

    public Long getId() {
        return id;
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public Long getUserId() {
        return userId;
    }

    public UserResponseDTO getEscort() {
        return escort;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public EscortSession.SessionStatus getStatus() {
        return status;
    }
}