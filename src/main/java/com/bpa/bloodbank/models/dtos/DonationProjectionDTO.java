package com.bpa.bloodbank.models.dtos;

import com.bpa.bloodbank.models.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationProjectionDTO {
    private String donorFirstName;
    private String donorLastname;
    private int bloodAmountDonated;
    private String bloodType;
    private String rh;
    private String productType;
    private String centerName;
}
