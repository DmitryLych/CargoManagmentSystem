package com.lych.cargomanagementsystem.repository;

import com.lych.cargomanagementsystem.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByDriverId(Pageable pageable, Long driverId);

    Page<Order> findAllByCustomerId(Pageable pageable, Long customerId);

    List<Order> findAllByCustomerId(Long customerId);

    void deleteAllByCustomerId(Long customerId);
}
