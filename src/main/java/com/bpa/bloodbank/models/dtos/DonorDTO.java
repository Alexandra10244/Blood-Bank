package com.bpa.bloodbank.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
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
    @NotBlank(message = "This field must not be empty!")
    private String bloodType;
    @NotBlank(message = "This field must not be empty!")
    private Boolean rh;
    @NotBlank(message = "This field must not be empty!")
    private String comorbidities;
}
