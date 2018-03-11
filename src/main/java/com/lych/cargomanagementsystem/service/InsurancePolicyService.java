package com.lych.cargomanagementsystem.service;

import com.lych.cargomanagementsystem.domain.InsurancePolicy;
import com.lych.cargomanagementsystem.repository.InsurancePolicyRepository;
import com.lych.cargomanagementsystem.service.dto.InsurancePolicyDTO;
import com.lych.cargomanagementsystem.service.mapper.InsurancePolicyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing InsurancePolicy.
 */
@Service
@Transactional
public class InsurancePolicyService {

    private final Logger log = LoggerFactory.getLogger(InsurancePolicyService.class);

    private final InsurancePolicyRepository insurancePolicyRepository;

    private final InsurancePolicyMapper insurancePolicyMapper;

    public InsurancePolicyService(InsurancePolicyRepository insurancePolicyRepository, InsurancePolicyMapper insurancePolicyMapper) {
        this.insurancePolicyRepository = insurancePolicyRepository;
        this.insurancePolicyMapper = insurancePolicyMapper;
    }

    /**
     * Save a insurancePolicy.
     *
     * @param insurancePolicyDTO the entity to save
     * @return the persisted entity
     */
    public InsurancePolicyDTO save(InsurancePolicyDTO insurancePolicyDTO) {
        log.debug("Request to save InsurancePolicy : {}", insurancePolicyDTO);
        InsurancePolicy insurancePolicy = insurancePolicyMapper.toEntity(insurancePolicyDTO);
        insurancePolicy = insurancePolicyRepository.save(insurancePolicy);
        return insurancePolicyMapper.toDto(insurancePolicy);
    }

    /**
     * Get all the insurancePolicies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InsurancePolicyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InsurancePolicies");
        return insurancePolicyRepository.findAll(pageable)
            .map(insurancePolicyMapper::toDto);
    }

    /**
     * Get one insurancePolicy by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public InsurancePolicyDTO findOne(Long id) {
        log.debug("Request to get InsurancePolicy : {}", id);
        InsurancePolicy insurancePolicy = insurancePolicyRepository.findOne(id);
        return insurancePolicyMapper.toDto(insurancePolicy);
    }

    /**
     * Delete the insurancePolicy by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InsurancePolicy : {}", id);
        insurancePolicyRepository.delete(id);
    }
}
