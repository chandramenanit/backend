package com.examly.springapp.service;

import com.examly.springapp.model.AdoptionRequest;
import com.examly.springapp.repository.AdoptionRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptionRequestService {

    private final AdoptionRequestRepository adoptionRequestRepository;

    public AdoptionRequestService(AdoptionRequestRepository adoptionRequestRepository) {
        this.adoptionRequestRepository = adoptionRequestRepository;
    }

    public AdoptionRequest createAdoptionRequest(AdoptionRequest request) {
        // Save the adoption request into MySQL
        return adoptionRequestRepository.save(request);
    }

    public List<AdoptionRequest> getAllAdoptionRequests() {
        return adoptionRequestRepository.findAll();
    }
}
