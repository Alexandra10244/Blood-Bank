package com.bpa.bloodbank.service.interfaces;


import com.bpa.bloodbank.models.dtos.DonationDTO;
import com.bpa.bloodbank.models.dtos.DonationProjectionDTO;
import com.bpa.bloodbank.models.dtos.DonorDTO;
import com.bpa.bloodbank.models.dtos.RejectionDTO;
import com.bpa.bloodbank.models.enums.DonationStatus;

import java.util.List;

public interface DonationService {


    DonationDTO createDonation(Long donorId, Long centerId, DonationDTO donationDTO);

    List<DonationProjectionDTO> getDonationsDailyMonthlyYearly(Integer day, Integer month, Integer year);

    String donationStatusChange (Long donationId, DonationStatus statusChange);

    String filterStockByBloodTypeRhProductTypeAndCenter(String bloodType, String rh, String productType);

    List<RejectionDTO> getDonationsRejections(Integer day, Integer month, Integer year);

    List<DonorDTO> findDonorsWhoDonatedMoreThan3Times(Integer year);

    String getLowStock(String bloodType, String rh, String productType, int stockLimit);
}
