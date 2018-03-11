package com.lych.cargomanagementsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Trailer.
 */
@Entity
@Table(name = "trailer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Trailer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "register_sign", nullable = false)
    private String registerSign;

    @NotNull
    @Column(name = "color", nullable = false)
    private String color;

    @NotNull
    @Column(name = "trailer_type", nullable = false)
    private String trailerType;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Double weight;

    @NotNull
    @Column(name = "height", nullable = false)
    private Double height;

    @NotNull
    @Column(name = "longest", nullable = false)
    private Double longest;

    @Column(name = "volume")
    private Integer volume;

    @NotNull
    @Column(name = "year_of_issue", nullable = false)
    private LocalDate yearOfIssue;

    @OneToOne(mappedBy = "trailer")
    @JsonIgnore
    private Truck truck;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegisterSign() {
        return registerSign;
    }

    public Trailer registerSign(String registerSign) {
        this.registerSign = registerSign;
        return this;
    }

    public void setRegisterSign(String registerSign) {
        this.registerSign = registerSign;
    }

    public String getColor() {
        return color;
    }

    public Trailer color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTrailerType() {
        return trailerType;
    }

    public Trailer trailerType(String trailerType) {
        this.trailerType = trailerType;
        return this;
    }

    public void setTrailerType(String trailerType) {
        this.trailerType = trailerType;
    }

    public Double getWeight() {
        return weight;
    }

    public Trailer weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public Trailer height(Double height) {
        this.height = height;
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getLongest() {
        return longest;
    }

    public Trailer longest(Double longest) {
        this.longest = longest;
        return this;
    }

    public void setLongest(Double longest) {
        this.longest = longest;
    }

    public Integer getVolume() {
        return volume;
    }

    public Trailer volume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public LocalDate getYearOfIssue() {
        return yearOfIssue;
    }

    public Trailer yearOfIssue(LocalDate yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
        return this;
    }

    public void setYearOfIssue(LocalDate yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public Truck getTruck() {
        return truck;
    }

    public Trailer truck(Truck truck) {
        this.truck = truck;
        return this;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trailer trailer = (Trailer) o;
        if (trailer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trailer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Trailer{" +
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
