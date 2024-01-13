package com.bpa.bloodbank.repositories;

import com.bpa.bloodbank.models.entities.Donation;
import com.bpa.bloodbank.repositories.projections.DonationsProjection;
import com.bpa.bloodbank.repositories.projections.RejectionProjection;
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
    List<DonationsProjection> findDonationsByDate(@Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);


    @Query(value = "SELECT centers.center_name " +
            "FROM (( donations " +
            "INNER JOIN donors ON donations.donor_id = donors.id) " +
            "INNER JOIN centers ON donations.center_id = centers.id) " +
            "WHERE donors.blood_type = :bloodType " +
            "AND donors.rh = :rh " +
            "AND donations.product_type = :productType " +
            "AND donations.donation_status = 'DONATED'", nativeQuery = true)
    List<DonationsProjection> findStockByBloodType_Rh_ProductType(
                                                @Param("bloodType") String bloodType,
                                                @Param("rh") String rh,
                                                @Param("productType") String productType);


    @Query(value = "SELECT donors.first_name, donors.last_name, donations.donation_status, donations.reason_for_refusal, centers.center_name " +
            "FROM (( donations " +
            "INNER JOIN donors ON donations.donor_id = donors.id) " +
            "INNER JOIN centers ON donations.center_id = centers.id) " +
            "WHERE donations.blood_collection_date = :setDate " +
            "AND donations.donation_status = 'REFUSED'", nativeQuery = true)
    List<RejectionProjection> findDonationsRejectionsByDate(@Param("setDate") LocalDate setDate);


    @Query(value = "SELECT donor_id, COUNT(donor_id) AS count " +
            "FROM donations " +
            "WHERE blood_collection_date BETWEEN :startDate AND :endDate " +
            "GROUP BY donor_id " +
            "HAVING COUNT(donor_id) > 3;" , nativeQuery = true)
    List<Long> findDonorsIdThatDonatedMoreThan3Times(@Param("startDate") LocalDate startDate,
                                                     @Param("endDate") LocalDate endDate);

}
