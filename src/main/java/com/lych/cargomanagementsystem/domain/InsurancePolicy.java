package com.lych.cargomanagementsystem.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A InsurancePolicy.
 */
@Entity
@Table(name = "insurance_policy")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InsurancePolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_validate", nullable = false)
    private LocalDate validate;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "jhi_cost", nullable = false)
    private Double cost;

    @ManyToOne
    private Driver driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getValidate() {
        return validate;
    }

    public InsurancePolicy validate(LocalDate validate) {
        this.validate = validate;
        return this;
    }

    public void setValidate(LocalDate validate) {
        this.validate = validate;
    }

    public String getType() {
        return type;
    }

    public InsurancePolicy type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCost() {
        return cost;
    }

    public InsurancePolicy cost(Double cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Driver getDriver() {
        return driver;
    }

    public InsurancePolicy driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
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
        InsurancePolicy insurancePolicy = (InsurancePolicy) o;
        if (insurancePolicy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insurancePolicy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InsurancePolicy{" +
            "id=" + getId() +
            ", validate='" + getValidate() + "'" +
            ", type='" + getType() + "'" +
            ", cost=" + getCost() +
            "}";
    }
}
