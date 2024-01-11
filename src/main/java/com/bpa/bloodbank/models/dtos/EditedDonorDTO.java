package com.bpa.bloodbank.models.dtos;
import lombok.Data;

@Data
public class EditedDonorDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String bloodType;
    private String rh;
    private String comorbidities;
}
