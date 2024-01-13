package com.bpa.bloodbank.repositories.projections;

public interface RejectionProjection {
    String getFirst_name();
    String getLast_name();
    String getDonation_status();
    String getReason_for_refusal();
    String getCenter_name();
}
