package com.lych.cargomanagementsystem.service.mapper;

import com.lych.cargomanagementsystem.domain.*;
import com.lych.cargomanagementsystem.service.dto.DriverDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Driver and its DTO DriverDTO.
 */
@Mapper(componentModel = "spring", uses = {DriverLicenseMapper.class, MedicalExaminationMapper.class, TruckMapper.class, CompanyMapper.class, UserMapper.class})
public interface DriverMapper extends EntityMapper<DriverDTO, Driver> {

    @Mapping(source = "driverLicense.id", target = "driverLicenseId")
    @Mapping(source = "medicalExamination.id", target = "medicalExaminationId")
    @Mapping(source = "truck.id", target = "truckId")
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    DriverDTO toDto(Driver driver);

    @Mapping(source = "driverLicenseId", target = "driverLicense")
    @Mapping(source = "medicalExaminationId", target = "medicalExamination")
    @Mapping(source = "truckId", target = "truck")
    @Mapping(target = "insurancePolicies", ignore = true)
    @Mapping(source = "companyId", target = "company")
    @Mapping(target = "orders", ignore = true)
    @Mapping(source = "userId", target = "user")
    Driver toEntity(DriverDTO driverDTO);

    default Driver fromId(Long id) {
        if (id == null) {
            return null;
        }
        Driver driver = new Driver();
        driver.setId(id);
        return driver;
    }
}
