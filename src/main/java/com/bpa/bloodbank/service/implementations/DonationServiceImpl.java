package com.bpa.bloodbank.service.implementations;

import com.bpa.bloodbank.exceptions.CenterNotFoundException;
import com.bpa.bloodbank.exceptions.DonationNotFoundException;
import com.bpa.bloodbank.exceptions.DonorNotFoundException;
import com.bpa.bloodbank.exceptions.InvalidDateException;
import com.bpa.bloodbank.models.dtos.DonationDTO;
import com.bpa.bloodbank.models.dtos.DonationProjectionDTO;
import com.bpa.bloodbank.models.entities.Center;
import com.bpa.bloodbank.models.entities.Donation;
import com.bpa.bloodbank.models.entities.Donor;
import com.bpa.bloodbank.models.enums.DonationStatus;
import com.bpa.bloodbank.repositories.CenterRepository;
import com.bpa.bloodbank.repositories.DonationRepository;
import com.bpa.bloodbank.repositories.DonorRepository;
import com.bpa.bloodbank.repositories.projections.DonationsProjection;
import com.bpa.bloodbank.service.interfaces.DonationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {
    private final DonationRepository donationRepository;
    private final DonorRepository donorRepository;
    private final CenterRepository centerRepository;
    private final ObjectMapper objectMapper;

    @Override
    public DonationDTO createDonation(Long donorId, Long centerId, DonationDTO donationDTO) {
        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new DonorNotFoundException("Donor does not exist!"));

        Center center = centerRepository.findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException("Center does not exist!"));

        Donation donation = Donation.builder()
                .expirationDate(LocalDate.now().plusDays(7))
                .productType(donationDTO.getProductType())
                .amount(donationDTO.getAmount())
                .donationStatus(donationDTO.getDonationStatus())
                .reasonForRefusal(donationDTO.getReasonForRefusal())
                .donor(donor)
                .center(center)
                .build();
        Donation donationResponse = donationRepository.save(donation);

        DonationDTO donationDTOResponse = DonationDTO.builder()
                .id(donationResponse.getId())
                .bloodCollectionDate(donationResponse.getBloodCollectionDate())
                .expirationDate(donationResponse.getExpirationDate())
                .productType(donationResponse.getProductType())
                .amount(donationResponse.getAmount())
                .donationStatus(donationResponse.getDonationStatus())
                .reasonForRefusal(donationResponse.getReasonForRefusal())
                .donorName(donationResponse.getDonor().getFirstName() + " " + donationResponse.getDonor().getLastName())
                .centerName(donationResponse.getCenter().getCenterName())
                .build();

        return donationDTOResponse;
    }

    @Override
    public List<DonationProjectionDTO> getDonationsDailyMonthlyYearly(Integer day, Integer month, Integer year) {
        if (day != null && month != null && year != null) {
            LocalDate startDate = LocalDate.of(year, month, day);
            LocalDate endDate = LocalDate.of(year, month, day);
            return donationsConverter(donationRepository.findDonationsByDate(startDate, endDate));
        } else if(month != null && year != null){
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);
            return donationsConverter(donationRepository.findDonationsByDate(startDate, endDate));
        }else if(year != null){
            LocalDate startDate = LocalDate.of(year, 1, 1);
            LocalDate endDate = startDate.plusYears(1).minusDays(1);
            return donationsConverter(donationRepository.findDonationsByDate(startDate, endDate));
        }
        else{
            throw new InvalidDateException("Invalid date!");
        }

    }

    private List<DonationProjectionDTO> donationsConverter(List<DonationsProjection> projections) {
        List<DonationProjectionDTO> donationProjectionDTOs = new ArrayList<>();
        for (DonationsProjection projection : projections) {
            DonationProjectionDTO donationProjectionDTO = DonationProjectionDTO.builder()
                    .donorFirstName(projection.getFirst_name())
                    .donorLastname(projection.getLast_name())
                    .bloodAmountDonated(projection.getAmount())
                    .bloodType(projection.getBlood_type())
                    .rh(projection.getRh())
                    .productType(projection.getProduct_type().toString())
                    .centerName(projection.getCenter_name())
                    .build();
            donationProjectionDTOs.add(donationProjectionDTO);
        }
        return donationProjectionDTOs;
    }

    @Override
    public String donationStatusChange(Long donationId, DonationStatus statusChange){
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new DonationNotFoundException("Donation not found!"));
        donation.setDonationStatus(statusChange);
        donationRepository.save(donation);
        return "Donation with id " + donation.getId() + " modified status to " + donation.getDonationStatus().toString();
    }

}
