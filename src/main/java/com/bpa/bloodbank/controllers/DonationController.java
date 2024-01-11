package com.bpa.bloodbank.controllers;

import com.bpa.bloodbank.models.dtos.DonationDTO;
import com.bpa.bloodbank.models.dtos.DonationProjectionDTO;
import com.bpa.bloodbank.models.enums.DonationStatus;
import com.bpa.bloodbank.service.interfaces.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/donations")
@RestController
public class DonationController {

    private final DonationService donationService;

    @PostMapping
    public ResponseEntity<DonationDTO> createDonation (@RequestParam Long donorId,
                                                       @RequestParam Long centerId,
                                                       @RequestBody DonationDTO donationDTO){
        return ResponseEntity.ok(donationService.createDonation(donorId,centerId,donationDTO));
    }

    @GetMapping ResponseEntity<List<DonationProjectionDTO>> getDonationsByDate(@RequestParam(required = false) Integer day,
                                                                              @RequestParam(required = false) Integer month,
                                                                              @RequestParam(required = true) Integer year){
        return ResponseEntity.ok(donationService.getDonationsDailyMonthlyYearly(day,month,year));
    }

    @PatchMapping ResponseEntity<String> changeDonationStatus(@RequestParam Long donationId,
                                                              @RequestParam DonationStatus donationStatus){
        return ResponseEntity.ok(donationService.donationStatusChange(donationId, donationStatus));
    }
}
