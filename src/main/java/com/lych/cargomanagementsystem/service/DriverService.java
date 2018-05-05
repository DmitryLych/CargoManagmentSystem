package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.Company;
import com.lych.cargomanagementsystem.domain.Driver;
import com.lych.cargomanagementsystem.repository.CompanyRepository;
import com.lych.cargomanagementsystem.repository.DriverRepository;
import com.lych.cargomanagementsystem.service.dto.SearchDriverDTO;
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
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing Driver.
 */
@Service
@Transactional
public class DriverService {

    private final Logger log = LoggerFactory.getLogger(DriverService.class);

    private final DriverRepository driverRepository;
    private final CompanyRepository companyRepository;

    private final DriverMapper driverMapper;

    public DriverService(DriverRepository driverRepository, DriverMapper driverMapper,
                         CompanyRepository companyRepository) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
        this.companyRepository=companyRepository;
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
        final Company company=companyRepository.findOne(driverDTO.getCompanyId());
        driver = driverRepository.save(driver);
        final Set<Driver> drivers=company.getDrivers();
        drivers.add(driver);
        company.setDrivers(drivers);
        companyRepository.save(company);
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
    public Page<SearchDriverDTO> findAllByCompany(Pageable pageable, Long companyId) {
        final Page<Driver> drivers = driverRepository.findAllByCompanyId(pageable, companyId);

        final List<SearchDriverDTO> content = drivers.getContent().stream()
            .map(driver -> {
                final SearchDriverDTO dto = new SearchDriverDTO();
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
