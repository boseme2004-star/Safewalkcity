package com.safewalk.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "escort_assignments")
public class EscortAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The escort request being assigned
    @OneToOne
    @JoinColumn(name = "escort_request_id", nullable = false)
    private EscortRequest escortRequest;

    // The volunteer/escort assigned to the request
    @ManyToOne
    @JoinColumn(name = "escort_id", nullable = false)
    private User escort;

    private LocalDateTime assignedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignmentStatus status = AssignmentStatus.ASSIGNED;

    public enum AssignmentStatus {
        ASSIGNED,
        STARTED,
        COMPLETED,
        CANCELLED
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public EscortRequest getEscortRequest() {
        return escortRequest;
    }

    public void setEscortRequest(EscortRequest escortRequest) {
        this.escortRequest = escortRequest;
    }

    public User getEscort() {
        return escort;
    }

    public void setEscort(User escort) {
        this.escort = escort;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }
}