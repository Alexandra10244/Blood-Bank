package com.bpa.bloodbank.controllers;

import com.bpa.bloodbank.models.dtos.CenterDTO;
import com.bpa.bloodbank.models.dtos.EditedCenterDTO;
import com.bpa.bloodbank.service.interfaces.CenterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/centers")
public class CenterController {

    private final CenterService centerService;

    @PostMapping
    public ResponseEntity<CenterDTO> createCenter(@Validated @RequestBody CenterDTO centerDTO){
        return ResponseEntity.ok(centerService.createCenter(centerDTO));
    }

    @GetMapping
    public ResponseEntity<List<CenterDTO>> getAllCenters(){
        return ResponseEntity.ok(centerService.getAllCenters());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCenter(@PathVariable Long id){
        return ResponseEntity.ok(centerService.deleteCenter(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CenterDTO> updateCenter(@PathVariable Long id, @Valid @RequestBody EditedCenterDTO centerDTO){
        return ResponseEntity.ok(centerService.updateCenter(id, centerDTO));
    }
}
