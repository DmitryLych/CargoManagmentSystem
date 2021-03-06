package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.Truck;
import com.lych.cargomanagementsystem.repository.TruckRepository;
import com.lych.cargomanagementsystem.service.dto.TruckDTO;
import com.lych.cargomanagementsystem.service.mapper.TruckMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Truck.
 */
@Service
@Transactional
public class TruckService {

    private final Logger log = LoggerFactory.getLogger(TruckService.class);

    private final TruckRepository truckRepository;

    private final TruckMapper truckMapper;

    public TruckService(TruckRepository truckRepository, TruckMapper truckMapper) {
        this.truckRepository = truckRepository;
        this.truckMapper = truckMapper;
    }

    /**
     * Save a truck.
     *
     * @param truckDTO the entity to save
     * @return the persisted entity
     */
    public TruckDTO save(TruckDTO truckDTO) {
        log.debug("Request to save Truck : {}", truckDTO);
        Truck truck = truckMapper.toEntity(truckDTO);
        truck = truckRepository.save(truck);
        return truckMapper.toDto(truck);
    }

    /**
     * Get all the trucks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TruckDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trucks");
        return truckRepository.findAll(pageable)
            .map(truckMapper::toDto);
    }

    /**
     * Get one truck by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TruckDTO findOne(Long id) {
        log.debug("Request to get Truck : {}", id);
        Truck truck = truckRepository.findOne(id);
        return truckMapper.toDto(truck);
    }

    /**
     * Delete the truck by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Truck : {}", id);
        truckRepository.delete(id);
    }
}
