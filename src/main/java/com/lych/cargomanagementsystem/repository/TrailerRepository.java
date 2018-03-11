package com.lych.cargomanagementsystem.repository;

import com.lych.cargomanagementsystem.domain.Trailer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Trailer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrailerRepository extends JpaRepository<Trailer, Long> {

}
