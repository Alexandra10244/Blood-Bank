package com.bpa.bloodbank.models.dtos;

import com.bpa.bloodbank.models.enums.DonationStatus;
import com.bpa.bloodbank.models.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationDTO {

    private Long id;
    private LocalDate bloodCollectionDate;
    private LocalDate expirationDate;
    @NotBlank(message = "This field must not be empty!")
    private ProductType productType;
    @NotBlank(message = "This field must not be empty!")
    private int amount;
    @NotBlank(message = "This field must not be empty!")
    private DonationStatus donationStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String reasonForRefusal;
    private String donorName;
    private String centerName;
}
