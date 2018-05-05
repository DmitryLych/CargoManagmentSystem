package com.lych.cargomanagementsystem.repository;

import com.lych.cargomanagementsystem.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Company entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("select company from Company company where company.user.login = ?#{principal.username}")
    List<Company> findByUserIsCurrentUser();

    Page<Company> findByUserId(Long userId, Pageable pageable);
}
