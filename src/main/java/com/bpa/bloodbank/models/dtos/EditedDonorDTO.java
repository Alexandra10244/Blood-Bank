package com.bpa.bloodbank.models.dtos;
import com.bpa.bloodbank.models.enums.BloodType;
import com.bpa.bloodbank.models.enums.Rh;
import lombok.Data;

@Data
public class EditedDonorDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private BloodType bloodType;
    private Rh rh;
    private String comorbidities;
}
