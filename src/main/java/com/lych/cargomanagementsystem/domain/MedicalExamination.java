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
 * A MedicalExamination.
 */
@Entity
@Table(name = "medical_examination")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MedicalExamination implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_validate", nullable = false)
    private LocalDate validate;

    @OneToOne(mappedBy = "medicalExamination")
    @JsonIgnore
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

    public MedicalExamination validate(LocalDate validate) {
        this.validate = validate;
        return this;
    }

    public void setValidate(LocalDate validate) {
        this.validate = validate;
    }

    public Driver getDriver() {
        return driver;
    }

    public MedicalExamination driver(Driver driver) {
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
        MedicalExamination medicalExamination = (MedicalExamination) o;
        if (medicalExamination.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicalExamination.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicalExamination{" +
            "id=" + getId() +
            ", validate='" + getValidate() + "'" +
            "}";
    }
}
