package com.bpa.bloodbank.models.entities;

import com.bpa.bloodbank.models.enums.DonationStatus;
import com.bpa.bloodbank.models.enums.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(name = "blood_collection_date")
    private LocalDate bloodCollectionDate;
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;
    @Column(name = "amount")
    private int amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "donation_status")
    private DonationStatus donationStatus;
    @Column(name = "reason_for_refusal")
    private String reasonForRefusal;

    @ManyToOne()
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @ManyToOne()
    @JoinColumn(name = "center_id")
    private Center center;

}
