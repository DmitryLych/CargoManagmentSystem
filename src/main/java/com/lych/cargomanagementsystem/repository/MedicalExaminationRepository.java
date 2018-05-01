package com.lych.cargomanagementsystem.repository;

import com.lych.cargomanagementsystem.domain.MedicalExamination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the MedicalExamination entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalExaminationRepository extends JpaRepository<MedicalExamination, Long> {

    MedicalExamination findByDriverId(Long driverId);
}
