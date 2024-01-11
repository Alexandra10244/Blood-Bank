package com.bpa.bloodbank.controllers;

import com.bpa.bloodbank.models.dtos.DonorDTO;
import com.bpa.bloodbank.models.dtos.EditedDonorDTO;
import com.bpa.bloodbank.service.interfaces.DonorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorService donorService;

    @PostMapping
    public ResponseEntity<DonorDTO> createDonor(@Validated @RequestBody DonorDTO donorDTO){
        return ResponseEntity.ok(donorService.createDonor(donorDTO));
    }

    @GetMapping
    public ResponseEntity<List<DonorDTO>> getAllDonors(){
        return ResponseEntity.ok(donorService.getAllDonors());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDonor(@PathVariable Long id){
        return ResponseEntity.ok(donorService.deleteDonor(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DonorDTO> updateDonor(@PathVariable Long id, @Valid @RequestBody EditedDonorDTO donorDTO){
        return  ResponseEntity.ok(donorService.updateDonor(id,donorDTO));
    }

}
