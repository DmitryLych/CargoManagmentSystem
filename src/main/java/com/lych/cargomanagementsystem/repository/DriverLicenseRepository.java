package com.lych.cargomanagementsystem.repository;

import com.lych.cargomanagementsystem.domain.DriverLicense;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DriverLicense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DriverLicenseRepository extends JpaRepository<DriverLicense, Long> {

}
