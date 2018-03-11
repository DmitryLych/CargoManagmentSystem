package com.lych.cargomanagementsystem.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Trailer entity.
 */
public class TrailerDTO implements Serializable {

    private Long id;

    @NotNull
    private String registerSign;

    @NotNull
    private String color;

    @NotNull
    private String trailerType;

    @NotNull
    private Double weight;

    @NotNull
    private Double height;

    @NotNull
    private Double longest;

    private Integer volume;

    @NotNull
    private LocalDate yearOfIssue;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTrailerType() {
        return trailerType;
    }

    public void setTrailerType(String trailerType) {
        this.trailerType = trailerType;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLongest() {
        return longest;
    }

    public void setLongest(Double longest) {
        this.longest = longest;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public LocalDate getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(LocalDate yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrailerDTO trailerDTO = (TrailerDTO) o;
        if(trailerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trailerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrailerDTO{" +
            "id=" + getId() +
            ", registerSign='" + getRegisterSign() + "'" +
            ", color='" + getColor() + "'" +
            ", trailerType='" + getTrailerType() + "'" +
            ", weight=" + getWeight() +
            ", height=" + getHeight() +
            ", longest=" + getLongest() +
            ", volume=" + getVolume() +
            ", yearOfIssue='" + getYearOfIssue() + "'" +
            "}";
    }
}
