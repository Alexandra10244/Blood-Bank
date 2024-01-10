package com.bpa.bloodbank.models.entities;

import com.bpa.bloodbank.models.enums.ProductType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "donation")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bloodCollection")
    private LocalDate bloodCollection;
    @Column(name = "expirationDate")
    private LocalDate expirationDate;
    @Column(name = "productType")
    private ProductType productType;
    @Column(name = "amount")
    private int amount;
    @Column(name = "donation_status")
    private Boolean donationStatus;


}
