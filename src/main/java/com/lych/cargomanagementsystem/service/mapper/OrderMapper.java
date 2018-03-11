package com.lych.cargomanagementsystem.service.mapper;

import com.lych.cargomanagementsystem.domain.*;
import com.lych.cargomanagementsystem.service.dto.OrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Order and its DTO OrderDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class, DriverMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "driver.id", target = "driverId")
    OrderDTO toDto(Order order);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(target = "goods", ignore = true)
    @Mapping(source = "driverId", target = "driver")
    Order toEntity(OrderDTO orderDTO);

    default Order fromId(Long id) {
        if (id == null) {
            return null;
        }
        Order order = new Order();
        order.setId(id);
        return order;
    }
}
