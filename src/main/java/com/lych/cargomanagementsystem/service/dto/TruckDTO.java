package com.lych.cargomanagementsystem.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Truck entity.
 */
public class TruckDTO implements Serializable {

    private Long id;

    @NotNull
    private String registerSign;

    @NotNull
    private String bodyNumber;

    @NotNull
    private Double weight;

    @NotNull
    private String color;

    @NotNull
    private LocalDate yearOfIssue;

    private Long trailerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegisterSign() {
        return registerSign;
    }

    public void setRegisterSign(String registerSign) {
        this.registerSign = registerSign;
    }

    public String getBodyNumber() {
        return bodyNumber;
    }

    public void setBodyNumber(String bodyNumber) {
        this.bodyNumber = bodyNumber;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(LocalDate yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public Long getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(Long trailerId) {
        this.trailerId = trailerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TruckDTO truckDTO = (TruckDTO) o;
        if(truckDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), truckDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TruckDTO{" +
            "id=" + getId() +
            ", registerSign='" + getRegisterSign() + "'" +
            ", bodyNumber='" + getBodyNumber() + "'" +
            ", weight=" + getWeight() +
            ", color='" + getColor() + "'" +
            ", yearOfIssue='" + getYearOfIssue() + "'" +
            "}";
    }
}
