package com.lych.cargomanagementsystem.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the InsurancePolicy entity.
 */
public class InsurancePolicyDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate validate;

    @NotNull
    private String type;

    @NotNull
    private Double cost;

    private Long driverId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InsurancePolicyDTO insurancePolicyDTO = (InsurancePolicyDTO) o;
        if(insurancePolicyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insurancePolicyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InsurancePolicyDTO{" +
            "id=" + getId() +
            ", validate='" + getValidate() + "'" +
            ", type='" + getType() + "'" +
            ", cost=" + getCost() +
            "}";
    }
}
