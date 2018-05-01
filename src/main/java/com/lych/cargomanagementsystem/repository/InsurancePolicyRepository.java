package com.lych.cargomanagementsystem.repository;

import com.lych.cargomanagementsystem.domain.InsurancePolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InsurancePolicy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long> {

    Page<InsurancePolicy> findAllByDriverId(Pageable pageable, Long driverId);
}
