package com.bpa.bloodbank.repositories;

import com.bpa.bloodbank.models.entities.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor,Long> {

    Optional<Donor> findDonorByEmail(String email);
}