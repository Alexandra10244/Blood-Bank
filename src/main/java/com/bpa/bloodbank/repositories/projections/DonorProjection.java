package com.bpa.bloodbank.repositories.projections;

import java.time.LocalDate;

public interface DonorProjection {
    String getFirstName();
    String getLastName();
    String getBloodType();
    String getRh();

}
