package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.Company;
import com.lych.cargomanagementsystem.domain.Driver;
import com.lych.cargomanagementsystem.domain.User;
import com.lych.cargomanagementsystem.repository.CompanyRepository;
import com.lych.cargomanagementsystem.repository.DriverRepository;
import com.lych.cargomanagementsystem.security.AuthoritiesConstants;
import com.lych.cargomanagementsystem.service.dto.CommonDriverDTO;
import com.lych.cargomanagementsystem.service.dto.DetailDriverDTO;
import com.lych.cargomanagementsystem.service.dto.DriverDTO;
import com.lych.cargomanagementsystem.service.mapper.DriverMapper;
import lombok.RequiredArgsConstructor;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing Driver.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class DriverService {

    private final DriverRepository driverRepository;
    private final CompanyRepository companyRepository;
    private final DriverMapper driverMapper;
    private final DozerBeanMapper dozerBeanMapper;
    private final UserProvider userProvider;

    /**
     * Save a driver.
     *
     * @param driverDTO the entity to save
     * @return the persisted entity
     */
    public CommonDriverDTO save(DriverDTO driverDTO) {
        Driver driver = driverMapper.toEntity(driverDTO);
        final Company company = Optional.ofNullable(companyRepository.findOne(driverDTO.getCompanyId()))
            .orElse(null);
        driver = driverRepository.save(driver);
        if (company != null) {
            final Set<Driver> drivers = company.getDrivers();
            drivers.add(driver);
            company.setDrivers(drivers);
            companyRepository.save(company);
        }
        return dozerBeanMapper.map(driver, CommonDriverDTO.class);
    }

    /**
     * Save a driver.
     *
     * @param driverDTO the entity to save
     * @return the persisted entity
     */
    public CommonDriverDTO update(DriverDTO driverDTO) {
        final Driver driver = Optional.ofNullable(driverRepository
            .findOne(driverDTO.getId())).orElseThrow(() -> new IllegalArgumentException("Not found."));
        final Driver newDRiver = driverMapper.toEntity(driverDTO);
        newDRiver.setId(driver.getId());
        newDRiver.setUser(driver.getUser());
        return dozerBeanMapper.map(driverRepository.save(newDRiver), CommonDriverDTO.class);
    }

    /**
     * Get all the drivers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommonDriverDTO> findAll(Pageable pageable) {
        final User user = userProvider.getCurrentUser();
        if (user.getAuthorities().stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ADMIN))) {
            final Page<Driver> drivers = driverRepository.findAll(pageable);
            return new PageImpl<>(drivers.getContent().stream().map(driver -> dozerBeanMapper.map(driver, CommonDriverDTO.class))
                .collect(Collectors.toList()), pageable, drivers.getTotalElements());
        } else if (user.getAuthorities().stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.DRIVER))) {
            final Driver driver = Optional.ofNullable(driverRepository.findOne(user.getId())).orElse(null);
            if (driver == null) {
                return new PageImpl<>(Collections.emptyList(), pageable, 0);
            }
            return new PageImpl<>(Collections.singletonList(dozerBeanMapper.map(driver, CommonDriverDTO.class)), pageable, 1);
        } else if (user.getAuthorities().stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.USER))) {
            final Page<Driver> drivers = driverRepository.findAllByCompanyUserId(pageable, user.getId());
            return new PageImpl<>(drivers.getContent().stream().map(driver -> dozerBeanMapper.map(driver, CommonDriverDTO.class))
                .collect(Collectors.toList()), pageable, drivers.getTotalElements());
        }

        return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }

    /**
     * Get one driver by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Object findOne(Long id) {
        Driver driver = driverRepository.findOne(id);
        return dozerBeanMapper.map(driver, DetailDriverDTO.class);
    }

    /**
     * Delete the driver by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        driverRepository.delete(id);
    }
}
