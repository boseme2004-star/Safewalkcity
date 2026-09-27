package com.safewalk.demo.service;

import com.safewalk.demo.dto.EscortRequestDTO;
import com.safewalk.demo.model.EscortRequest;
import com.safewalk.demo.model.User;
import com.safewalk.demo.repository.EscortRequestRepository;
import com.safewalk.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EscortRequestService {

    private final EscortRequestRepository escortRequestRepository;
    private final UserRepository userRepository;

    public EscortRequestService(
            EscortRequestRepository escortRequestRepository,
            UserRepository userRepository) {

        this.escortRequestRepository = escortRequestRepository;
        this.userRepository = userRepository;
    }

    public EscortRequest createRequest(Long userId,
                                       EscortRequestDTO requestDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        EscortRequest request = new EscortRequest();

        request.setUser(user);
        request.setPickupLocation(requestDTO.getPickupLocation());
        request.setDestination(requestDTO.getDestination());
        request.setDate(requestDTO.getDate());
        request.setTime(requestDTO.getTime());
        request.setNotes(requestDTO.getNotes());

        return escortRequestRepository.save(request);
    }

    public List<EscortRequest> getUserRequests(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return escortRequestRepository.findByUser(user);
    }

    public EscortRequest cancelRequest(Long requestId) {

        EscortRequest request = escortRequestRepository.findById(requestId)
                .orElseThrow(() ->
                        new RuntimeException("Request not found"));

        request.setStatus(EscortRequest.Status.CANCELLED);

        return escortRequestRepository.save(request);
    }
}