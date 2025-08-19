package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.AdoptionRequest;
@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest,Long> {
    
}
