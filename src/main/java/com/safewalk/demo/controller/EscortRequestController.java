package com.safewalk.demo.controller;

import com.safewalk.demo.dto.EscortRequestDTO;
import com.safewalk.demo.model.EscortRequest;
import com.safewalk.demo.service.EscortRequestService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/escort-requests")
public class EscortRequestController {

    private final EscortRequestService escortRequestService;

    public EscortRequestController(EscortRequestService escortRequestService) {
        this.escortRequestService = escortRequestService;
    }

    @PostMapping("/{userId}")
    public EscortRequest createRequest(
            @PathVariable Long userId,
            @Valid @RequestBody EscortRequestDTO requestDTO) {

        return escortRequestService.createRequest(userId, requestDTO);
    }

    @GetMapping("/{userId}")
    public List<EscortRequest> getUserRequests(
            @PathVariable Long userId) {

        return escortRequestService.getUserRequests(userId);
    }

    @PutMapping("/{requestId}/cancel")
    public EscortRequest cancelRequest(
            @PathVariable Long requestId) {

        return escortRequestService.cancelRequest(requestId);
    }
}