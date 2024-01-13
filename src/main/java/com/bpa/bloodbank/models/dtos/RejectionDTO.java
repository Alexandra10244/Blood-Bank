package com.bpa.bloodbank.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RejectionDTO {
    private String firstName;
    private String lastName;
    private String donationStatus;
    private String reasonForRefusal;
    private String centerName;
}
