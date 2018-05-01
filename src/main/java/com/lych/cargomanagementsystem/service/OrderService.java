package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.Order;
import com.lych.cargomanagementsystem.repository.OrderRepository;
import com.lych.cargomanagementsystem.service.dto.CommonOrderDTO;
import com.lych.cargomanagementsystem.service.dto.OrderDTO;
import com.lych.cargomanagementsystem.service.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing Order.
 */
@Service
@Transactional
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    /**
     * Save a order.
     *
     * @param orderDTO the entity to save
     * @return the persisted entity
     */
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAll(pageable)
            .map(orderMapper::toDto);
    }

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommonOrderDTO> findAllByDriver(Pageable pageable, Long driverId) {
        log.debug("Request to get all Orders");
        final Page<Order> orders = orderRepository.findAllByDriverId(pageable, driverId);

        return new PageImpl<>(prepare(orders.getContent()), pageable, orders.getTotalElements());
    }

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommonOrderDTO> findAllByCustomer(Pageable pageable, Long customerId) {
        log.debug("Request to get all Orders");
        final Page<Order> orders = orderRepository.findAllByCustomerId(pageable, customerId);

        return new PageImpl<>(prepare(orders.getContent()), pageable, orders.getTotalElements());
    }

    /**
     * Get one order by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OrderDTO findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        Order order = orderRepository.findOne(id);
        return orderMapper.toDto(order);
    }

    /**
     * Delete the order by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.delete(id);
    }

    private List<CommonOrderDTO> prepare(List<Order> orders) {
        return orders.stream()
            .map(order -> {
                final CommonOrderDTO dto = new CommonOrderDTO();
                dto.setId(order.getId());
                dto.setAddresses(order.getDownloadAddress().concat(" - ").concat(order.getUnloadingAddress()));
                return dto;
            }).collect(Collectors.toList());
    }
}
