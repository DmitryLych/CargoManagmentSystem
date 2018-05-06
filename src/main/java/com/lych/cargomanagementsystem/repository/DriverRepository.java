package com.lych.cargomanagementsystem.repository;

import com.lych.cargomanagementsystem.domain.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Driver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query("select driver from Driver driver where driver.user.login = ?#{principal.username}")
    List<Driver> findByUserIsCurrentUser();

    Page<Driver> findAllByCompanyId(Pageable pageable, Long companyId);

    List<Driver> findAllByCompanyId(Long companyId);

    List<Driver> findAllByUserId(Long userID);

    Page<Driver> findAllByCompanyUserId(Pageable pageable, Long companyId);

    void deleteAllByCompanyId(Long companyId);
}
