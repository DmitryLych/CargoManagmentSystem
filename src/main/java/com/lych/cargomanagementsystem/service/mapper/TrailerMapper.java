package com.lych.cargomanagementsystem.service.mapper;

import com.lych.cargomanagementsystem.domain.*;
import com.lych.cargomanagementsystem.service.dto.TrailerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Trailer and its DTO TrailerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrailerMapper extends EntityMapper<TrailerDTO, Trailer> {


    @Mapping(target = "truck", ignore = true)
    Trailer toEntity(TrailerDTO trailerDTO);

    default Trailer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Trailer trailer = new Trailer();
        trailer.setId(id);
        return trailer;
    }
}
