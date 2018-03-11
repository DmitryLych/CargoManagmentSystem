package com.lych.cargomanagementsystem.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the DriverLicense entity.
 */
public class DriverLicenseDTO implements Serializable {

    private Long id;

    @NotNull
    private String category;

    private LocalDate validate;

    private String specialNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getValidate() {
        return validate;
    }

    public void setValidate(LocalDate validate) {
        this.validate = validate;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DriverLicenseDTO driverLicenseDTO = (DriverLicenseDTO) o;
        if(driverLicenseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driverLicenseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DriverLicenseDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", validate='" + getValidate() + "'" +
            ", specialNotes='" + getSpecialNotes() + "'" +
            "}";
    }
}
