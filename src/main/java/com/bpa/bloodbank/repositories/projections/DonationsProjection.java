package com.bpa.bloodbank.repositories.projections;

import com.bpa.bloodbank.models.enums.ProductType;

public interface DonationsProjection {
    String getFirst_name();
    String getLast_name();
    int getAmount();
    String getBlood_type();
    String getRh();
    ProductType getProduct_type();
    String getCenter_name();
}
