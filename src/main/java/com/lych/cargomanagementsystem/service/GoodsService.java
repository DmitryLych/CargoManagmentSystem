package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.Goods;
import com.lych.cargomanagementsystem.domain.User;
import com.lych.cargomanagementsystem.repository.GoodsRepository;
import com.lych.cargomanagementsystem.security.AuthoritiesConstants;
import com.lych.cargomanagementsystem.service.dto.GoodsDTO;
import com.lych.cargomanagementsystem.service.mapper.GoodsMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


/**
 * Service Implementation for managing Goods.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class GoodsService {

    private final Logger log = LoggerFactory.getLogger(GoodsService.class);

    private final GoodsRepository goodsRepository;

    private final GoodsMapper goodsMapper;
    private final UserProvider userProvider;

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
        final User user = userProvider.getCurrentUser();
        if (user.getAuthorities().stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ADMIN))) {
            return goodsRepository.findAll(pageable)
                .map(goodsMapper::toDto);
        }
        log.debug("Request to get all Goods");
        if (user.getAuthorities().stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.DRIVER))) {
            return goodsRepository.findAllByOrderDriverUserId(user.getId(), pageable).map(goodsMapper::toDto);
        }
        if (user.getAuthorities().stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.CUSTOMER))) {
            return goodsRepository.findAllByOrderCustomerUserId(user.getId(), pageable).map(goodsMapper::toDto);
        }
        return new PageImpl<>(Collections.emptyList(), pageable, 0);
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
