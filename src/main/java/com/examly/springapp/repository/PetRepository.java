package com.examly.springapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examly.springapp.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet , Long> {
    Page<Pet> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Pet> findBySpeciesContainingIgnoreCase(String species, Pageable pageable);

    Page<Pet> findByNameContainingIgnoreCaseAndSpeciesContainingIgnoreCase(String name, String species, Pageable pageable);
}
