package com.bpa.bloodbank.repositories;

import com.bpa.bloodbank.models.entities.Donor;
import com.bpa.bloodbank.repositories.projections.DonationsProjection;
import com.bpa.bloodbank.repositories.projections.DonorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    Optional<Donor> findDonorByEmail(String email);

    @Query(value = "SELECT donors.first_name, donors.last_name, donors.blood_type, donors.rh " +
            "FROM donors WHERE blood_type = :bloodType AND rh = :rh", nativeQuery = true)
    List<DonorProjection> findDonorByBloodTypeAndRh(@Param("bloodType") String bloodType,
                                                    @Param("rh") String rh);

//    @Query(value = "SELECT donors.first_name, donors.last_name " +
//            "FROM(donors " +
//            "WHERE YEAR(donations.bloodCollectionDate)= :year " +
//            "GROUP BY donor.id HAVING COUNT(donation)>3")
//    List<DonationsProjection> findDonorsWithMoreThan3DonationsInYear(@Param("year") int year);
}
