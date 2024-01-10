package com.bpa.bloodbank.models.entities;

import com.bpa.bloodbank.models.enums.ProductType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "blood_collection_date")
    private LocalDate bloodCollectionDate;
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    @Column(name = "product_type")
    private ProductType productType;
    @Column(name = "amount")
    private int amount;
    @Column(name = "donation_status")
    private Boolean donationStatus;

    @ManyToOne()
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @ManyToOne()
    @JoinColumn(name = "center_id")
    private Center center;

}
