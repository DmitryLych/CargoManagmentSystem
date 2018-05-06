package com.lych.cargomanagementsystem.repository;

import com.lych.cargomanagementsystem.domain.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Goods entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    Page<Goods> findAllByOrderId(Pageable pageable, Long orderId);

    Page<Goods> findAllByOrderCustomerUserId(Long userId, Pageable pageable);

    Page<Goods> findAllByOrderDriverUserId(Long userId, Pageable pageable);
}
