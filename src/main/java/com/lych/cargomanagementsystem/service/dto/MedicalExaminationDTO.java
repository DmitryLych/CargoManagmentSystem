package com.lych.cargomanagementsystem.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MedicalExamination entity.
 */
public class MedicalExaminationDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate validate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getValidate() {
        return validate;
    }

    public void setValidate(LocalDate validate) {
        this.validate = validate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedicalExaminationDTO medicalExaminationDTO = (MedicalExaminationDTO) o;
        if(medicalExaminationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicalExaminationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicalExaminationDTO{" +
            "id=" + getId() +
            ", validate='" + getValidate() + "'" +
            "}";
    }
}
