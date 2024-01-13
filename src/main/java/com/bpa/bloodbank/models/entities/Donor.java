package com.bpa.bloodbank.models.entities;

import com.bpa.bloodbank.models.enums.BloodType;
import com.bpa.bloodbank.models.enums.Rh;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donors")
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type")
    private BloodType bloodType;
    @Enumerated(EnumType.STRING)
    @Column(name = "rh")
    private Rh rh;
    @Column(name = "comorbidities")
    private String comorbidities;

    @OneToMany(mappedBy = "donor")
    private Set<Donation> donations;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
