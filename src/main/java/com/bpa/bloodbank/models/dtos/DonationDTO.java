package com.bpa.bloodbank.models.dtos;

import com.bpa.bloodbank.models.enums.ProductType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DonationDTO {

    private Long id;
    @NotBlank(message = "This field must not be empty!")
    private LocalDate bloodCollection;
    @NotBlank(message = "This field must not be empty!")
    private LocalDate expirationDate;
    @NotBlank(message = "This field must not be empty!")
    private ProductType productType;
    @NotBlank(message = "This field must not be empty!")
    private int amount;
    @NotBlank(message = "This field must not be empty!")
    private Boolean donationStatus;
}
