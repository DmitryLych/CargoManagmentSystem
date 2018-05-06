package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.Driver;
import com.lych.cargomanagementsystem.domain.MedicalExamination;
import com.lych.cargomanagementsystem.domain.User;
import com.lych.cargomanagementsystem.repository.DriverRepository;
import com.lych.cargomanagementsystem.repository.MedicalExaminationRepository;
import com.lych.cargomanagementsystem.security.AuthoritiesConstants;
import com.lych.cargomanagementsystem.service.dto.MedicalExaminationDTO;
import com.lych.cargomanagementsystem.service.mapper.MedicalExaminationMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing MedicalExamination.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class MedicalExaminationService {

    private final Logger log = LoggerFactory.getLogger(MedicalExaminationService.class);

    private final MedicalExaminationRepository medicalExaminationRepository;
    private final MedicalExaminationMapper medicalExaminationMapper;
    private final UserProvider userProvider;
    private final DriverRepository driverRepository;

    /**
     * Save a medicalExamination.
     *
     * @param medicalExaminationDTO the entity to save
     * @return the persisted entity
     */
    public MedicalExaminationDTO save(MedicalExaminationDTO medicalExaminationDTO) {
        log.debug("Request to save MedicalExamination : {}", medicalExaminationDTO);
        final User user = userProvider.getCurrentUser();
        final Driver driver = driverRepository.findAllByUserId(user.getId()).get(0);
        MedicalExamination medicalExamination = medicalExaminationMapper.toEntity(medicalExaminationDTO);
        medicalExamination.setDriver(driver);
        medicalExamination = medicalExaminationRepository.save(medicalExamination);
        return medicalExaminationMapper.toDto(medicalExamination);
    }

    /**
     * Get all the medicalExaminations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MedicalExaminationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalExaminations");
        final User user = userProvider.getCurrentUser();
        if (user.getAuthorities().stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.ADMIN))) {
            return medicalExaminationRepository.findAll(pageable)
                .map(medicalExaminationMapper::toDto);
        }
        final List<Driver> drivers = driverRepository.findAllByUserId(user.getId());
        if (CollectionUtils.isEmpty(drivers)) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        if (user.getAuthorities().stream().anyMatch(authority -> authority.getName().equals(AuthoritiesConstants.DRIVER))) {
            final Driver driver = drivers.get(0);
            final MedicalExamination medicalExamination = medicalExaminationRepository.findByDriverId(driver.getId());

            return new PageImpl<>(Collections.singletonList(medicalExaminationMapper.toDto(medicalExamination)), pageable, 1);
        }
        return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }

    /**
     * get all the medicalExaminations where Driver is null.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<MedicalExaminationDTO> findAllWhereDriverIsNull() {
        log.debug("Request to get all medicalExaminations where Driver is null");
        return StreamSupport
            .stream(medicalExaminationRepository.findAll().spliterator(), false)
            .filter(medicalExamination -> medicalExamination.getDriver() == null)
            .map(medicalExaminationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one medicalExamination by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public MedicalExaminationDTO findOne(Long id) {
        log.debug("Request to get MedicalExamination : {}", id);
        MedicalExamination medicalExamination = medicalExaminationRepository.findOne(id);
        return medicalExaminationMapper.toDto(medicalExamination);
    }

    /**
     * Delete the medicalExamination by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MedicalExamination : {}", id);
        medicalExaminationRepository.delete(id);
    }
}
