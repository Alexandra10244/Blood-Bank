package com.bpa.bloodbank.repositories;

import com.bpa.bloodbank.models.entities.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CenterRepository extends JpaRepository<Center,Long> {

    @Query("SELECT center FROM Center center WHERE LOWER(center.centerName) = LOWER(:centerName)")
    Optional<Center> findCenterByNameIgnoreCase(@Param("centerName") String centerName);

    Optional<Center> findByEmail(String email);
}
