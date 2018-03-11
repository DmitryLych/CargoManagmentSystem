package com.lych.cargomanagementsystem.service.mapper;

import com.lych.cargomanagementsystem.domain.*;
import com.lych.cargomanagementsystem.service.dto.InsurancePolicyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InsurancePolicy and its DTO InsurancePolicyDTO.
 */
@Mapper(componentModel = "spring", uses = {DriverMapper.class})
public interface InsurancePolicyMapper extends EntityMapper<InsurancePolicyDTO, InsurancePolicy> {

    @Mapping(source = "driver.id", target = "driverId")
    InsurancePolicyDTO toDto(InsurancePolicy insurancePolicy);

    @Mapping(source = "driverId", target = "driver")
    InsurancePolicy toEntity(InsurancePolicyDTO insurancePolicyDTO);

    default InsurancePolicy fromId(Long id) {
        if (id == null) {
            return null;
        }
        InsurancePolicy insurancePolicy = new InsurancePolicy();
        insurancePolicy.setId(id);
        return insurancePolicy;
    }
}
