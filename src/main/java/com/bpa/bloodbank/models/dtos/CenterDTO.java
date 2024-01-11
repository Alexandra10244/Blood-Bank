package com.bpa.bloodbank.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CenterDTO {

    private Long id;
    @Email
    @NotBlank(message = "This field must not be empty!")
    private String email;
    @NotBlank(message = "This field must not be empty!")
    private String phoneNumber;
    @NotBlank(message = "This field must not be empty!")
    private String centerName;
    private AddressDTO address;
}
