package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.DriverLicense;
import com.lych.cargomanagementsystem.repository.DriverLicenseRepository;
import com.lych.cargomanagementsystem.service.dto.DriverLicenseDTO;
import com.lych.cargomanagementsystem.service.mapper.DriverLicenseMapper;
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
 * Service Implementation for managing DriverLicense.
 */
@Service
@Transactional
public class DriverLicenseService {

    private final Logger log = LoggerFactory.getLogger(DriverLicenseService.class);

    private final DriverLicenseRepository driverLicenseRepository;

    private final DriverLicenseMapper driverLicenseMapper;

    public DriverLicenseService(DriverLicenseRepository driverLicenseRepository, DriverLicenseMapper driverLicenseMapper) {
        this.driverLicenseRepository = driverLicenseRepository;
        this.driverLicenseMapper = driverLicenseMapper;
    }

    /**
     * Save a driverLicense.
     *
     * @param driverLicenseDTO the entity to save
     * @return the persisted entity
     */
    public DriverLicenseDTO save(DriverLicenseDTO driverLicenseDTO) {
        log.debug("Request to save DriverLicense : {}", driverLicenseDTO);
        DriverLicense driverLicense = driverLicenseMapper.toEntity(driverLicenseDTO);
        driverLicense = driverLicenseRepository.save(driverLicense);
        return driverLicenseMapper.toDto(driverLicense);
    }

    /**
     * Get all the driverLicenses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DriverLicenseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DriverLicenses");
        return driverLicenseRepository.findAll(pageable)
            .map(driverLicenseMapper::toDto);
    }


    /**
     *  get all the driverLicenses where Driver is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<DriverLicenseDTO> findAllWhereDriverIsNull() {
        log.debug("Request to get all driverLicenses where Driver is null");
        return StreamSupport
            .stream(driverLicenseRepository.findAll().spliterator(), false)
            .filter(driverLicense -> driverLicense.getDriver() == null)
            .map(driverLicenseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one driverLicense by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DriverLicenseDTO findOne(Long id) {
        log.debug("Request to get DriverLicense : {}", id);
        DriverLicense driverLicense = driverLicenseRepository.findOne(id);
        return driverLicenseMapper.toDto(driverLicense);
    }

    /**
     * Delete the driverLicense by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DriverLicense : {}", id);
        driverLicenseRepository.delete(id);
    }
}
