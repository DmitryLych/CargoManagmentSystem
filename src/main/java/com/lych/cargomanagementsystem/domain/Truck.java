package com.lych.cargomanagementsystem.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Truck.
 */
@Entity
@Table(name = "truck")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Truck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "register_sign", nullable = false)
    private String registerSign;

    @NotNull
    @Column(name = "body_number", nullable = false)
    private String bodyNumber;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Double weight;

    @NotNull
    @Column(name = "color", nullable = false)
    private String color;

    @NotNull
    @Column(name = "year_of_issue", nullable = false)
    private LocalDate yearOfIssue;

    @OneToOne
    @JoinColumn(unique = true)
    private Trailer trailer;

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

    public Truck registerSign(String registerSign) {
        this.registerSign = registerSign;
        return this;
    }

    public void setRegisterSign(String registerSign) {
        this.registerSign = registerSign;
    }

    public String getBodyNumber() {
        return bodyNumber;
    }

    public Truck bodyNumber(String bodyNumber) {
        this.bodyNumber = bodyNumber;
        return this;
    }

    public void setBodyNumber(String bodyNumber) {
        this.bodyNumber = bodyNumber;
    }

    public Double getWeight() {
        return weight;
    }

    public Truck weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public Truck color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getYearOfIssue() {
        return yearOfIssue;
    }

    public Truck yearOfIssue(LocalDate yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
        return this;
    }

    public void setYearOfIssue(LocalDate yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public Truck trailer(Trailer trailer) {
        this.trailer = trailer;
        return this;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
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
        Truck truck = (Truck) o;
        if (truck.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), truck.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Truck{" +
            "id=" + getId() +
            ", registerSign='" + getRegisterSign() + "'" +
            ", bodyNumber='" + getBodyNumber() + "'" +
            ", weight=" + getWeight() +
            ", color='" + getColor() + "'" +
            ", yearOfIssue='" + getYearOfIssue() + "'" +
            "}";
    }
}
