package com.lych.cargomanagementsystem.service.mapper;

import com.lych.cargomanagementsystem.domain.*;
import com.lych.cargomanagementsystem.service.dto.DriverLicenseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DriverLicense and its DTO DriverLicenseDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DriverLicenseMapper extends EntityMapper<DriverLicenseDTO, DriverLicense> {


    @Mapping(target = "driver", ignore = true)
    DriverLicense toEntity(DriverLicenseDTO driverLicenseDTO);

    default DriverLicense fromId(Long id) {
        if (id == null) {
            return null;
        }
        DriverLicense driverLicense = new DriverLicense();
        driverLicense.setId(id);
        return driverLicense;
    }
}
