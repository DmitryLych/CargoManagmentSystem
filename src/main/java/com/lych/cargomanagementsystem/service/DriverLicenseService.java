package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.Driver;
import com.lych.cargomanagementsystem.domain.DriverLicense;
import com.lych.cargomanagementsystem.domain.User;
import com.lych.cargomanagementsystem.repository.DriverLicenseRepository;
import com.lych.cargomanagementsystem.repository.DriverRepository;
import com.lych.cargomanagementsystem.security.AuthoritiesConstants;
import com.lych.cargomanagementsystem.service.dto.DriverLicenseDTO;
import com.lych.cargomanagementsystem.service.mapper.DriverLicenseMapper;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing DriverLicense.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class DriverLicenseService {

    private final Logger log = LoggerFactory.getLogger(DriverLicenseService.class);

    private final DriverLicenseRepository driverLicenseRepository;
    private final UserProvider userProvider;
    private final DriverLicenseMapper driverLicenseMapper;
    private final DriverRepository driverRepository;

    /**
     * Save a driverLicense.
     *
     * @param driverLicenseDTO the entity to save
     * @return the persisted entity
     */
    public DriverLicenseDTO save(DriverLicenseDTO driverLicenseDTO) {
        final Driver driver = driverRepository.findAllByUserId(userProvider.getCurrentUser().getId()).get(0);
        log.debug("Request to save DriverLicense : {}", driverLicenseDTO);
        DriverLicense driverLicense = driverLicenseMapper.toEntity(driverLicenseDTO);
        driverLicense = driverLicenseRepository.save(driverLicense);
        driver.setDriverLicense(driverLicense);
        driverRepository.save(driver);
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

        final User user = userProvider.getCurrentUser();
        if (user.getAuthorities().stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ADMIN))) {
            return driverLicenseRepository.findAll(pageable)
                .map(driverLicenseMapper::toDto);
        }

        final List<Driver> drivers = driverRepository.findAllByUserId(userProvider.getCurrentUser().getId());
        final Driver driver = drivers.isEmpty() ? null : drivers.get(0);
        if(driver==null){
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        final DriverLicense driverLicense = Optional.ofNullable(driverLicenseRepository.findByDriverId(driver.getId())).orElse(null);

        if (driverLicense == null) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        return new PageImpl<>(Collections.singletonList(driverLicenseMapper.toDto(driverLicense)), pageable, 1);
    }

    /**
     * get all the driverLicenses where Driver is null.
     *
     * @return the list of entities
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
