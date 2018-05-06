package com.lych.cargomanagementsystem.config;

import com.lych.cargomanagementsystem.domain.Driver;
import com.lych.cargomanagementsystem.service.dto.DetailDriverDTO;
import com.lych.cargomanagementsystem.service.dto.DriverDTO;
import org.dozer.loader.api.BeanMappingBuilder;

import static org.dozer.loader.api.FieldsMappingOptions.copyByReference;

public class Driver2DriverDTO extends BeanMappingBuilder {

    @Override
    protected void configure() {

        mapping(Driver.class, DetailDriverDTO.class)
            .fields("yearOfIssue", "yearOfIssue", copyByReference());

        mapping(DriverDTO.class, Driver.class)
            .fields("yearOfIssue", "yearOfIssue", copyByReference());
    }
}
