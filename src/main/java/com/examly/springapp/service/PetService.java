package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Pet;
import com.examly.springapp.repository.PetRepository;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public Pet createPet(Pet pet){
        return petRepository.save(pet);
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }


    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }

    public Page<Pet> getPets(String name, String species, Pageable pageable) {
        if (!name.isEmpty() && !species.isEmpty()) {
            return petRepository.findByNameContainingIgnoreCaseAndSpeciesContainingIgnoreCase(name, species, pageable);
        } else if (!name.isEmpty()) {
            return petRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (!species.isEmpty()) {
            return petRepository.findBySpeciesContainingIgnoreCase(species, pageable);
        } else {
            return petRepository.findAll(pageable);
        }
    }

}
