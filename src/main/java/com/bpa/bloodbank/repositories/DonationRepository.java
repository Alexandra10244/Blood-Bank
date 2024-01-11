package com.bpa.bloodbank.repositories;

import com.bpa.bloodbank.models.entities.Donation;
import com.bpa.bloodbank.repositories.projections.DonationsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    @Query(value = "SELECT donors.first_name, donors.last_name, donors.blood_type, donors.rh, donations.amount, donations.product_type, centers.center_name " +
            "FROM (( donations " +
            "INNER JOIN donors ON donations.donor_id = donors.id) " +
            "INNER JOIN centers ON donations.center_id = centers.id) " +
            "WHERE donations.blood_collection_date >= :startDate " +
            "AND donations.blood_collection_date <= :endDate", nativeQuery = true)
    List<DonationsProjection> findDonationsByDate(@Param("startDate")LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);

}
