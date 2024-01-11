package com.bpa.bloodbank.service.implementations;

import com.bpa.bloodbank.repositories.DonationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl {
    private final DonationRepository donationRepository;
    private final ObjectMapper objectMapper;

}
