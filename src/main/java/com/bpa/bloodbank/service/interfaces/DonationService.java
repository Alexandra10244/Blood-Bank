package com.bpa.bloodbank.service.interfaces;


import com.bpa.bloodbank.models.dtos.DonationDTO;
import com.bpa.bloodbank.models.dtos.DonationProjectionDTO;
import com.bpa.bloodbank.models.enums.DonationStatus;

import java.util.List;

public interface DonationService {


    DonationDTO createDonation(Long donorId, Long centerId, DonationDTO donationDTO);

    List<DonationProjectionDTO> getDonationsDailyMonthlyYearly(Integer day, Integer month, Integer year);

    String donationStatusChange (Long donationId, DonationStatus statusChange);
}
