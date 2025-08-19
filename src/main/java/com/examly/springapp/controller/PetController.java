package com.examly.springapp.controller;
 
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
 
import com.examly.springapp.model.Pet;
import com.examly.springapp.service.PetService;


@RestController
@CrossOrigin(origins = "*")
public class PetController {
    @Autowired
    PetService ps;
 
    @PostMapping("/api/pets")
    public ResponseEntity<Pet> insertPet(@RequestBody Pet pet){
        Pet createdpet=ps.createPet(pet);
        URI location=URI.create("/api/pets/"+createdpet.getId());
        return ResponseEntity.created(location).body(createdpet);
    }
 
   
     @GetMapping("/api/pets")
     public List<Pet> getData(){
        return ps.getAllPets();
     }
   
     @GetMapping("/api/pets/{id}")
     public ResponseEntity<?> getDataById(@PathVariable Long id){
        Optional<Pet> pet=ps.getPetById(id);
        if(pet.isPresent()){
            return ResponseEntity.ok(pet.get());
        }
        else{
            Map<String,String> err=new HashMap<>();
            err.put("message","Pet with ID "+id+" not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }
     }
     @PutMapping("/update/{id}")
     public Pet UpdateData(@PathVariable Long id,@RequestBody Pet pet){
        Optional<Pet> existing=ps.getPetById(id);
        if(existing.isPresent()){
            Pet existingPet=existing.get();
            existingPet.setAge(pet.getAge());
            ps.createPet(existingPet);
            return existingPet;
        }
        else{
            return null;
        }
     }

     @GetMapping("/api/pets/paginated")
    public Page<Pet> getPetsPaginated(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String species,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                                                    : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ps.getPets(name, species, pageable);
    }
}