package com.bpa.bloodbank.repositories.projections;

import java.time.LocalDate;

public interface DonorProjection {
    String getFirst_name();
    String getLast_name();
    String getBlood_type();
    String getRh();

}
