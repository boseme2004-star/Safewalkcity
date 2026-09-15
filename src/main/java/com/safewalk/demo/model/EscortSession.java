package com.safewalk.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "escort_sessions")
public class EscortSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The assignment connected to this journey
    @OneToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private EscortAssignment assignment;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessionStatus status = SessionStatus.NOT_STARTED;

    public enum SessionStatus {
        NOT_STARTED,
        ACTIVE,
        ARRIVED,
        COMPLETED,
        EMERGENCY
    }

    public EscortSession() {
    }

    public Long getId() {
        return id;
    }

    public EscortAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(EscortAssignment assignment) {
        this.assignment = assignment;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }
}