package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.Goods;
import com.lych.cargomanagementsystem.repository.GoodsRepository;
import com.lych.cargomanagementsystem.service.dto.CommonGoodsDTO;
import com.lych.cargomanagementsystem.service.dto.GoodsDTO;
import com.lych.cargomanagementsystem.service.mapper.GoodsMapper;
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
 * Service Implementation for managing Goods.
 */
@Service
@Transactional
public class GoodsService {

    private final Logger log = LoggerFactory.getLogger(GoodsService.class);

    private final GoodsRepository goodsRepository;

    private final GoodsMapper goodsMapper;

    public GoodsService(GoodsRepository goodsRepository, GoodsMapper goodsMapper) {
        this.goodsRepository = goodsRepository;
        this.goodsMapper = goodsMapper;
    }

    /**
     * Save a goods.
     *
     * @param goodsDTO the entity to save
     * @return the persisted entity
     */
    public GoodsDTO save(GoodsDTO goodsDTO) {
        log.debug("Request to save Goods : {}", goodsDTO);
        Goods goods = goodsMapper.toEntity(goodsDTO);
        goods = goodsRepository.save(goods);
        return goodsMapper.toDto(goods);
    }

    /**
     * Get all the goods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GoodsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Goods");
        return goodsRepository.findAll(pageable)
            .map(goodsMapper::toDto);
    }

    /**
     * Get all the goods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommonGoodsDTO> findAllByOrder(Pageable pageable, Long orderId) {
        log.debug("Request to get all Goods");
        final Page<Goods> goods = goodsRepository.findAllByOrderId(pageable, orderId);

        final List<CommonGoodsDTO> content = goods.getContent().stream()
            .map(g -> {
                final CommonGoodsDTO dto = new CommonGoodsDTO();
                dto.setId(g.getId());
                dto.setName(g.getName());
                return dto;
            })
            .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, goods.getTotalElements());
    }

    /**
     * Get one goods by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public GoodsDTO findOne(Long id) {
        log.debug("Request to get Goods : {}", id);
        Goods goods = goodsRepository.findOne(id);
        return goodsMapper.toDto(goods);
    }

    /**
     * Delete the goods by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Goods : {}", id);
        goodsRepository.delete(id);
    }
}
