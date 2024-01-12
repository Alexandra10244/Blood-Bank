package com.bpa.bloodbank.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonorProjectionDTO {
    private String firstName;
    private String lastname;
    private String bloodType;
    private String rh;
}
