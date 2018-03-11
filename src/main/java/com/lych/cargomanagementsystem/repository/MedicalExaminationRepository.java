package com.lych.cargomanagementsystem.repository;

import com.lych.cargomanagementsystem.domain.MedicalExamination;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MedicalExamination entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalExaminationRepository extends JpaRepository<MedicalExamination, Long> {

}
