package com.bpa.bloodbank.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressDTO {

    private Long id;
    @NotBlank(message = "This field must not be empty!")
    private String country;
    @NotBlank(message = "This field must not be empty!")
    private String county;
    @NotBlank(message = "This field must not be empty!")
    private String city;
    @NotBlank(message = "This field must not be empty!")
    private String street;
    @NotBlank(message = "This field must not be empty!")
    private int number;
}

