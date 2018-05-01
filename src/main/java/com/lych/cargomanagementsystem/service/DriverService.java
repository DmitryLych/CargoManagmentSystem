package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.Driver;
import com.lych.cargomanagementsystem.repository.DriverRepository;
import com.lych.cargomanagementsystem.service.dto.CommonDriverDTO;
import com.lych.cargomanagementsystem.service.dto.DriverDTO;
import com.lych.cargomanagementsystem.service.mapper.DriverMapper;
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
 * Service Implementation for managing Driver.
 */
@Service
@Transactional
public class DriverService {

    private final Logger log = LoggerFactory.getLogger(DriverService.class);

    private final DriverRepository driverRepository;

    private final DriverMapper driverMapper;

    public DriverService(DriverRepository driverRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    /**
     * Save a driver.
     *
     * @param driverDTO the entity to save
     * @return the persisted entity
     */
    public DriverDTO save(DriverDTO driverDTO) {
        log.debug("Request to save Driver : {}", driverDTO);
        Driver driver = driverMapper.toEntity(driverDTO);
        driver = driverRepository.save(driver);
        return driverMapper.toDto(driver);
    }

    /**
     * Get all the drivers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DriverDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Drivers");
        return driverRepository.findAll(pageable)
            .map(driverMapper::toDto);
    }

    /**
     * Get all the drivers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommonDriverDTO> findAllByCompany(Pageable pageable, Long companyId) {
        final Page<Driver> drivers = driverRepository.findAllByCompanyId(pageable, companyId);

        final List<CommonDriverDTO> content = drivers.getContent().stream()
            .map(driver -> {
                final CommonDriverDTO dto = new CommonDriverDTO();
                dto.setFullName(driver.getLastName().concat(" ").concat(driver.getFirstName()));
                dto.setId(driver.getId());
                return dto;
            }).collect(Collectors.toList());

        return new PageImpl<>(content, pageable, drivers.getTotalElements());
    }

    /**
     * Get one driver by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DriverDTO findOne(Long id) {
        log.debug("Request to get Driver : {}", id);
        Driver driver = driverRepository.findOne(id);
        return driverMapper.toDto(driver);
    }

    /**
     * Delete the driver by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Driver : {}", id);
        driverRepository.delete(id);
    }
}
