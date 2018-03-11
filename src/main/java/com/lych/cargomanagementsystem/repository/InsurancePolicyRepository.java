package com.lych.cargomanagementsystem.repository;

import com.lych.cargomanagementsystem.domain.InsurancePolicy;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InsurancePolicy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long> {

}
