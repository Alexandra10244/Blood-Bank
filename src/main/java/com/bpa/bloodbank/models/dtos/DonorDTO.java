package com.bpa.bloodbank.models.dtos;

import com.bpa.bloodbank.models.enums.BloodType;
import com.bpa.bloodbank.models.enums.Rh;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonorDTO {

    private Long id;
    @NotBlank(message = "This field must not be empty!")
    private String firstName;
    @NotBlank(message = "This field must not be empty!")
    private String lastName;
    @Email
    @NotBlank(message = "This field must not be empty!")
    private String email;
    @NotBlank(message = "This field must not be empty!")
    private String phoneNumber;
    @NotNull(message = "This field must not be empty!")
    private BloodType bloodType;
    @NotNull(message = "This field must not be empty!")
    private Rh rh;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String comorbidities;
    private AddressDTO address;
}
