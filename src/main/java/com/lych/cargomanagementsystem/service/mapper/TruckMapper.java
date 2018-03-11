package com.lych.cargomanagementsystem.service.mapper;

import com.lych.cargomanagementsystem.domain.*;
import com.lych.cargomanagementsystem.service.dto.TruckDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Truck and its DTO TruckDTO.
 */
@Mapper(componentModel = "spring", uses = {TrailerMapper.class})
public interface TruckMapper extends EntityMapper<TruckDTO, Truck> {

    @Mapping(source = "trailer.id", target = "trailerId")
    TruckDTO toDto(Truck truck);

    @Mapping(source = "trailerId", target = "trailer")
    Truck toEntity(TruckDTO truckDTO);

    default Truck fromId(Long id) {
        if (id == null) {
            return null;
        }
        Truck truck = new Truck();
        truck.setId(id);
        return truck;
    }
}
