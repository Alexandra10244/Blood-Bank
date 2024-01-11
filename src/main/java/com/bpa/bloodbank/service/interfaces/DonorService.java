package com.bpa.bloodbank.service.interfaces;

import com.bpa.bloodbank.models.dtos.DonorDTO;
import com.bpa.bloodbank.models.dtos.EditedDonorDTO;

import java.util.List;

public interface DonorService {

    DonorDTO createDonor(DonorDTO donorDTO);
    List<DonorDTO> getAllDonors();
    DonorDTO updateDonor(Long id, EditedDonorDTO donorDTO);
    String deleteDonor(Long id);
    DonorDTO findDonorByID(Long id);
    DonorDTO findDonorByEmail(String email);
}
