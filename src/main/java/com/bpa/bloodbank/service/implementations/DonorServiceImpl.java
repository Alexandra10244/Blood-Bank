package com.bpa.bloodbank.service.implementations;

import com.bpa.bloodbank.exceptions.DonorNotFoundException;
import com.bpa.bloodbank.models.dtos.DonorDTO;
import com.bpa.bloodbank.models.dtos.DonorProjectionDTO;
import com.bpa.bloodbank.models.dtos.EditedDonorDTO;
import com.bpa.bloodbank.models.entities.Address;
import com.bpa.bloodbank.models.entities.Donor;
import com.bpa.bloodbank.repositories.DonorRepository;
import com.bpa.bloodbank.repositories.projections.DonorProjection;
import com.bpa.bloodbank.service.interfaces.DonorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorService {
    private final DonorRepository donorRepository;
    private final ObjectMapper objectMapper;

    @Override
    public DonorDTO createDonor(DonorDTO donorDTO) {
        Donor donor = Donor.builder()
                .firstName(donorDTO.getFirstName())
                .lastName(donorDTO.getLastName())
                .email(donorDTO.getEmail())
                .phoneNumber(donorDTO.getPhoneNumber())
                .bloodType(donorDTO.getBloodType())
                .rh(donorDTO.getRh())
                .address(objectMapper.convertValue(donorDTO.getAddress(), Address.class))
                .build();
        return objectMapper.convertValue(donorRepository.save(donor), DonorDTO.class);
//        Donor donor = donorRepository.save(objectMapper.convertValue(donorDTO, Donor.class));
//        return objectMapper.convertValue(donor, DonorDTO.class);
    }

    @Override
    public List<DonorDTO> getAllDonors() {
        List<Donor> donors = donorRepository.findAll();

        return donors.stream()
                .map(donor -> objectMapper.convertValue(donor, DonorDTO.class))
                .toList();
    }

    @Override
    public DonorDTO updateDonor(Long id, EditedDonorDTO donorDTO) {
        Donor donor = donorRepository.findById(id).orElseThrow(() -> new DonorNotFoundException("Donor not found"));

        if (donorDTO.getFirstName() != null && !donorDTO.getFirstName().isEmpty()) {
            donor.setFirstName(donorDTO.getFirstName());
        }
        if (donorDTO.getLastName() != null && !donorDTO.getLastName().isEmpty()) {
            donor.setLastName(donorDTO.getLastName());
        }
        if (donorDTO.getEmail() != null && !donorDTO.getEmail().isEmpty()) {
            donor.setEmail(donorDTO.getEmail());
        }
        if (donorDTO.getPhoneNumber() != null && !donorDTO.getPhoneNumber().isEmpty()) {
            donor.setPhoneNumber(donorDTO.getPhoneNumber());
        }
        if (donorDTO.getBloodType() != null && !donorDTO.getBloodType().isEmpty()) {
            donor.setBloodType(donorDTO.getBloodType());
        }
        if (donorDTO.getRh() != null) {
            donor.setRh(donorDTO.getRh());
        }
        if (donorDTO.getComorbidities() != null && !donorDTO.getComorbidities().isEmpty()) {
            donor.setComorbidities(donorDTO.getComorbidities());
        }

        Donor updateDonor = donorRepository.save(donor);

        return objectMapper.convertValue(updateDonor, DonorDTO.class);

    }

    @Override
    public String deleteDonor(Long id) {
        if (donorRepository.existsById(id)) {
            donorRepository.deleteById(id);

            return "Donor " + id + " successfully deleted!";
        } else {
            throw new DonorNotFoundException("Donor not found!");
        }
    }

    @Override
    public DonorDTO findDonorByID(Long id) {
        Donor donor = donorRepository
                .findById(id)
                .orElseThrow(() -> new DonorNotFoundException("Donor not found!"));

        return objectMapper.convertValue(donor, DonorDTO.class);
    }

    @Override
    public DonorDTO findDonorByEmail(String email) {
        Donor donor = donorRepository
                .findDonorByEmail(email)
                .orElseThrow(() -> new DonorNotFoundException("Donor not found!"));

        return objectMapper.convertValue(donor, DonorDTO.class);
    }

    @Override
    public List<DonorProjectionDTO> findDonorByBloodTypeAndRh(String bloodType, String rh) {
        List<DonorProjection> donorProjections = donorRepository.findDonorByBloodTypeAndRh(bloodType, rh);
        List<DonorProjectionDTO> donorProjectionDTOs = new ArrayList<>();

        for (DonorProjection donorProjection : donorProjections) {
            DonorProjectionDTO donorProjectionDTO = new DonorProjectionDTO();
            donorProjectionDTO.setFirstName(donorProjection.getFirstName());
            donorProjectionDTO.setLastname(donorProjection.getLastName());
            donorProjectionDTO.setBloodType(donorProjection.getBloodType());
            donorProjectionDTO.setRh(donorProjection.getRh());
            donorProjectionDTOs.add(donorProjectionDTO);
        }
        return donorProjectionDTOs;
    }
}
