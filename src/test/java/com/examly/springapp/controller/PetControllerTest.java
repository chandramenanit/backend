package com.examly.springapp.controller;

import com.examly.springapp.model.Pet;
import com.examly.springapp.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    private Pet pet1, pet2;

    @BeforeEach
    public void setup() {
        pet1 = new Pet();
        pet1.setId(1L);
        pet1.setName("Buddy");
        pet1.setSpecies("Dog");
        pet1.setBreed("Golden Retriever");
        pet1.setAge(24);
        pet1.setDescription("Friendly and energetic");
        pet1.setImageUrl("https://8080-baeeebbfeeeff331360945adaaadfdebeafour.premiumproject.examly.io/buddy.jpg");
        pet1.setAdoptionStatus("Available");

        pet2 = new Pet();
        pet2.setId(2L);
        pet2.setName("Whiskers");
        pet2.setSpecies("Cat");
        pet2.setBreed("Siamese");
        pet2.setAge(12);
        pet2.setDescription("Calm and affectionate");
        pet2.setImageUrl("https://8080-baeeebbfeeeff331360945adaaadfdebeafour.premiumproject.examly.io/whiskers.jpg");
        pet2.setAdoptionStatus("Pending");
    }

    @Test
    public void testGetAllPets() throws Exception {
        given(petService.getAllPets()).willReturn(Arrays.asList(pet1, pet2));
        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Buddy"))
                .andExpect(jsonPath("$[1].adoptionStatus").value("Pending"));
    }

    @Test
    public void testGetPetById_found() throws Exception {
        given(petService.getPetById(1L)).willReturn(Optional.of(pet1));
        mockMvc.perform(get("/api/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.species").value("Dog"));
    }

    @Test
    public void testGetPetById_notFound() throws Exception {
        given(petService.getPetById(999L)).willReturn(Optional.empty());
        mockMvc.perform(get("/api/pets/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Pet with ID 999 not found"));
    }

    @Test
    public void testCreatePet_valid() throws Exception {
        Pet newPet = new Pet();
        newPet.setName("Max");
        newPet.setSpecies("Dog");
        newPet.setBreed("Beagle");
        newPet.setAge(18);
        newPet.setDescription("Loyal and smart");
        newPet.setImageUrl(null);
        newPet.setAdoptionStatus("Available");

        Pet savedPet = new Pet();
        savedPet.setId(3L);
        savedPet.setName("Max");
        savedPet.setSpecies("Dog");
        savedPet.setBreed("Beagle");
        savedPet.setAge(18);
        savedPet.setDescription("Loyal and smart");
        savedPet.setImageUrl(null);
        savedPet.setAdoptionStatus("Available");

        given(petService.createPet(any(Pet.class))).willReturn(savedPet);

        mockMvc.perform(post("/api/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(newPet)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(3L))
            .andExpect(jsonPath("$.name").value("Max"));
    }

    @Test
    public void testCreatePet_validationError() throws Exception {
        Pet badPet = new Pet();
        badPet.setName("");  // Should fail, name is required
        badPet.setSpecies("");  // Required
        badPet.setBreed(null); // Required
        badPet.setAge(-1);  // Negative age
        badPet.setAdoptionStatus("NotValid"); // Invalid status
        mockMvc.perform(post("/api/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(badPet)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Validation failed"))
            .andExpect(jsonPath("$.errors").isArray());
    }
}
