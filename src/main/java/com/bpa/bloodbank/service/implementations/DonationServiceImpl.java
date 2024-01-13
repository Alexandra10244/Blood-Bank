package com.bpa.bloodbank.service.implementations;

import com.bpa.bloodbank.exceptions.*;
import com.bpa.bloodbank.models.dtos.*;
import com.bpa.bloodbank.models.entities.Center;
import com.bpa.bloodbank.models.entities.Donation;
import com.bpa.bloodbank.models.entities.Donor;
import com.bpa.bloodbank.models.enums.DonationStatus;
import com.bpa.bloodbank.repositories.CenterRepository;
import com.bpa.bloodbank.repositories.DonationRepository;
import com.bpa.bloodbank.repositories.DonorRepository;
import com.bpa.bloodbank.repositories.projections.DonationsProjection;
import com.bpa.bloodbank.repositories.projections.RejectionProjection;
import com.bpa.bloodbank.service.interfaces.DonationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        } else if (month != null && year != null) {
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);
            return donationsConverter(donationRepository.findDonationsByDate(startDate, endDate));
        } else if (year != null) {
            LocalDate startDate = LocalDate.of(year, 1, 1);
            LocalDate endDate = startDate.plusYears(1).minusDays(1);
            return donationsConverter(donationRepository.findDonationsByDate(startDate, endDate));
        } else {
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
    public String donationStatusChange(Long donationId, DonationStatus statusChange) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new DonationNotFoundException("Donation not found!"));
        donation.setDonationStatus(statusChange);
        donationRepository.save(donation);
        return "Donation with id " + donation.getId() + " modified status to " + donation.getDonationStatus().toString();
    }

    @Override
    public String filterStockByBloodTypeRhProductTypeAndCenter(String bloodType, String rh, String productType) {
        try {
            List<DonationsProjection> filteredList = donationRepository.findStockByBloodType_Rh_ProductType(bloodType, rh, productType);
            List<String> centerList = new ArrayList<>();
            List<Center> allCenters = centerRepository.findAll();
            for (DonationsProjection projection : filteredList) {
                centerList.add(projection.getCenter_name());
            }
            Map<String, Long> centerCounts = centerList.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            for (Center center : allCenters) {
                if (!centerCounts.containsKey(center.getCenterName())) {
                    centerCounts.put(center.getCenterName(), 0L);
                }
            }
            String result = centerCounts.entrySet().stream()
                    .map(entry -> String.format("%s: %d", entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining("\n"));
            return "Stock of " + bloodType + " " + rh + " " + productType + " per center:\n" + result;
        } catch (Exception exception) {
            throw new IncorrectFilterParametersException("Incorrect filters applied!");
        }
    }

    @Override
    public List<RejectionDTO> getDonationsRejections(Integer day, Integer month, Integer year) {
        if (day != null && month != null && year != null) {
            LocalDate date = LocalDate.of(year, month, day);
            List<RejectionProjection> rejectionProjections = donationRepository.findDonationsRejectionsByDate(date);
            List<RejectionDTO> rejections = new ArrayList<>();
            for (RejectionProjection projection : rejectionProjections) {
                RejectionDTO rejectionDTO = RejectionDTO.builder()
                        .firstName(projection.getFirst_name())
                        .lastName(projection.getLast_name())
                        .donationStatus(projection.getDonation_status())
                        .reasonForRefusal(projection.getReason_for_refusal())
                        .centerName(projection.getCenter_name())
                        .build();
                rejections.add(rejectionDTO);
            }
            return rejections;
        }
        throw new InvalidDateException("Invalid date!");
    }

    @Override
    public List<DonorDTO> findDonorsWhoDonatedMoreThan3Times(Integer year) {
        if (year != null) {
            LocalDate startDate = LocalDate.of(year, 1, 1);
            LocalDate endDate = startDate.plusYears(1).minusDays(1);
            List<Long> donorIds = donationRepository.findDonorsIdThatDonatedMoreThan3Times(startDate, endDate);
            List<DonorDTO> donorDTOS = new ArrayList<>();
            List<Donor> donorsEntity = new ArrayList<>();
            for (Long id : donorIds) {
                donorsEntity.add(donorRepository.findById(id).get());
            }
            for (Donor donorEntity : donorsEntity) {
                donorDTOS.add(DonorDTO.builder()
                        .id(donorEntity.getId())
                        .firstName(donorEntity.getFirstName())
                        .lastName(donorEntity.getLastName())
                        .email(donorEntity.getEmail())
                        .phoneNumber(donorEntity.getPhoneNumber())
                        .bloodType(donorEntity.getBloodType())
                        .rh(donorEntity.getRh())
                        .comorbidities(donorEntity.getComorbidities())
                        .address(objectMapper.convertValue(donorEntity.getAddress(), AddressDTO.class))
                        .build());
            }
            return donorDTOS;
        }
        throw new InvalidDateException("Invalid Year!");
    }

    @Override
    public String getLowStock(String bloodType, String rh, String productType, int stockLimit) {
        try {
            List<DonationsProjection> filteredList = donationRepository.findStockByBloodType_Rh_ProductType(bloodType, rh, productType);
            List<String> centerList = new ArrayList<>();
            List<Center> allCenters = centerRepository.findAll();
            for (DonationsProjection projection : filteredList) {
                centerList.add(projection.getCenter_name());
            }

            Map<String, Long> centerCounts = centerList.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            for (Center center : allCenters) {
                if (!centerCounts.containsKey(center.getCenterName())) {
                    centerCounts.put(center.getCenterName(), 0L);
                }
            }
            Map<String, Long> filteredMap = centerCounts.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() <= stockLimit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            String result = filteredMap.entrySet().stream()
                    .map(entry -> String.format("%s: %d", entry.getKey(), entry.getValue()))
                    .collect(Collectors.joining("\n"));
            return "Stock of " + bloodType + " " + rh + " " + productType + " per center:\n" + result;
        } catch (Exception exception) {
            throw new IncorrectFilterParametersException("Incorrect filters applied!");
        }
    }
}
