package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.Trailer;
import com.lych.cargomanagementsystem.repository.TrailerRepository;
import com.lych.cargomanagementsystem.service.dto.TrailerDTO;
import com.lych.cargomanagementsystem.service.mapper.TrailerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Trailer.
 */
@Service
@Transactional
public class TrailerService {

    private final Logger log = LoggerFactory.getLogger(TrailerService.class);

    private final TrailerRepository trailerRepository;

    private final TrailerMapper trailerMapper;

    public TrailerService(TrailerRepository trailerRepository, TrailerMapper trailerMapper) {
        this.trailerRepository = trailerRepository;
        this.trailerMapper = trailerMapper;
    }

    /**
     * Save a trailer.
     *
     * @param trailerDTO the entity to save
     * @return the persisted entity
     */
    public TrailerDTO save(TrailerDTO trailerDTO) {
        log.debug("Request to save Trailer : {}", trailerDTO);
        Trailer trailer = trailerMapper.toEntity(trailerDTO);
        trailer = trailerRepository.save(trailer);
        return trailerMapper.toDto(trailer);
    }

    /**
     * Get all the trailers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TrailerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trailers");
        return trailerRepository.findAll(pageable)
            .map(trailerMapper::toDto);
    }


    /**
     *  get all the trailers where Truck is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TrailerDTO> findAllWhereTruckIsNull() {
        log.debug("Request to get all trailers where Truck is null");
        return StreamSupport
            .stream(trailerRepository.findAll().spliterator(), false)
            .filter(trailer -> trailer.getTruck() == null)
            .map(trailerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one trailer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TrailerDTO findOne(Long id) {
        log.debug("Request to get Trailer : {}", id);
        Trailer trailer = trailerRepository.findOne(id);
        return trailerMapper.toDto(trailer);
    }

    /**
     * Delete the trailer by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Trailer : {}", id);
        trailerRepository.delete(id);
    }
}
