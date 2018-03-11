package com.lych.cargomanagementsystem.service.mapper;

import com.lych.cargomanagementsystem.domain.*;
import com.lych.cargomanagementsystem.service.dto.MedicalExaminationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MedicalExamination and its DTO MedicalExaminationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MedicalExaminationMapper extends EntityMapper<MedicalExaminationDTO, MedicalExamination> {


    @Mapping(target = "driver", ignore = true)
    MedicalExamination toEntity(MedicalExaminationDTO medicalExaminationDTO);

    default MedicalExamination fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicalExamination medicalExamination = new MedicalExamination();
        medicalExamination.setId(id);
        return medicalExamination;
    }
}
