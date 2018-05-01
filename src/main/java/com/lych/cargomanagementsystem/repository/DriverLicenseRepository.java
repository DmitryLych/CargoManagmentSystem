package com.lych.cargomanagementsystem.repository;

import com.lych.cargomanagementsystem.domain.DriverLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the DriverLicense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DriverLicenseRepository extends JpaRepository<DriverLicense, Long> {

    DriverLicense findByDriverId(Long driverId);
}
