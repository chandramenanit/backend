package com.examly.springapp.controller;
import com.examly.springapp.model.AdoptionRequest;
import com.examly.springapp.model.Pet;
import com.examly.springapp.service.AdoptionRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdoptionRequestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AdoptionRequestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdoptionRequestService adoptionRequestService;

    private Pet pet;
    private AdoptionRequest req1;

    @BeforeEach
    public void setup() {
        pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");
        pet.setSpecies("Dog");
        pet.setBreed("Golden Retriever");
        pet.setAge(24);
        pet.setDescription("Friendly");
        pet.setImageUrl(null);
        pet.setAdoptionStatus("Available");

        req1 = new AdoptionRequest();
        req1.setId(1L);
        req1.setPetId(1L);
        req1.setApplicantName("Alice");
        req1.setApplicantEmail("alice@example.com");
        req1.setApplicantPhone("1234567890");
        req1.setStatus("Pending");
        req1.setSubmissionDate(LocalDateTime.now());
    }

    @Test
    public void testCreateAdoptionRequest_success() throws Exception {
        given(adoptionRequestService.createAdoptionRequest(any(AdoptionRequest.class))).willAnswer(invocation -> {
            AdoptionRequest ar = invocation.getArgument(0);
            ar.setId(10L);
            ar.setStatus("Pending");
            ar.setSubmissionDate(LocalDateTime.now());
            return ar;
        });
        AdoptionRequest incomingReq = new AdoptionRequest();
        incomingReq.setPetId(1L);
        incomingReq.setApplicantName("Bob");
        incomingReq.setApplicantEmail("bob@example.com");
        incomingReq.setApplicantPhone("9876543210");

        mockMvc.perform(post("/api/adoption-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(incomingReq)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(10L))
            .andExpect(jsonPath("$.status").value("Pending"));
    }

    @Test
    public void testCreateAdoptionRequest_invalidPet() throws Exception {
        given(adoptionRequestService.createAdoptionRequest(any(AdoptionRequest.class)))
            .willThrow(new IllegalArgumentException("Pet is not available for adoption"));
        AdoptionRequest incomingReq = new AdoptionRequest();
        incomingReq.setPetId(999L);
        incomingReq.setApplicantName("Charlie");
        incomingReq.setApplicantEmail("charlie@example.com");
        incomingReq.setApplicantPhone("7002223000");
        mockMvc.perform(post("/api/adoption-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(incomingReq)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Pet is not available for adoption"));
    }

    @Test
    public void testCreateAdoptionRequest_petNotAvailable() throws Exception {
        given(adoptionRequestService.createAdoptionRequest(any(AdoptionRequest.class)))
            .willThrow(new IllegalArgumentException("Pet is not available for adoption"));
        AdoptionRequest r = new AdoptionRequest();
        r.setPetId(2L);
        r.setApplicantName("Dana");
        r.setApplicantEmail("dana@example.com");
        r.setApplicantPhone("1231112222");
        mockMvc.perform(post("/api/adoption-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(r)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Pet is not available for adoption"));
    }

    @Test
    public void testCreateAdoptionRequest_validationFail() throws Exception {
        AdoptionRequest invalidReq = new AdoptionRequest();
        invalidReq.setPetId(null); // Required
        invalidReq.setApplicantName("A"); // Too short
        invalidReq.setApplicantEmail("bademail"); // Invalid
        invalidReq.setApplicantPhone(""); // Required
        mockMvc.perform(post("/api/adoption-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(invalidReq)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Validation failed"))
            .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    public void testGetAllAdoptionRequests() throws Exception {
        given(adoptionRequestService.getAllAdoptionRequests()).willReturn(Arrays.asList(req1));
        mockMvc.perform(get("/api/adoption-requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].applicantName").value("Alice"));
    }
}
