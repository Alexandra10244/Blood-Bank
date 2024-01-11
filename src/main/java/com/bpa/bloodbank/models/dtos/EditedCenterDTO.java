package com.bpa.bloodbank.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EditedCenterDTO {

    private String email;
    private String phoneNumber;
    private String centerName;
}
