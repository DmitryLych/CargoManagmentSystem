package com.lych.cargomanagementsystem.service.mapper;

import com.lych.cargomanagementsystem.domain.*;
import com.lych.cargomanagementsystem.service.dto.GoodsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Goods and its DTO GoodsDTO.
 */
@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface GoodsMapper extends EntityMapper<GoodsDTO, Goods> {

    @Mapping(source = "order.id", target = "orderId")
    GoodsDTO toDto(Goods goods);

    @Mapping(source = "orderId", target = "order")
    Goods toEntity(GoodsDTO goodsDTO);

    default Goods fromId(Long id) {
        if (id == null) {
            return null;
        }
        Goods goods = new Goods();
        goods.setId(id);
        return goods;
    }
}
