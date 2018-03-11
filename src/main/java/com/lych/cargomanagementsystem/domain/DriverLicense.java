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
 * A DriverLicense.
 */
@Entity
@Table(name = "driver_license")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DriverLicense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "jhi_validate")
    private LocalDate validate;

    @Column(name = "special_notes")
    private String specialNotes;

    @OneToOne(mappedBy = "driverLicense")
    @JsonIgnore
    private Driver driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public DriverLicense category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getValidate() {
        return validate;
    }

    public DriverLicense validate(LocalDate validate) {
        this.validate = validate;
        return this;
    }

    public void setValidate(LocalDate validate) {
        this.validate = validate;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public DriverLicense specialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
        return this;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    public Driver getDriver() {
        return driver;
    }

    public DriverLicense driver(Driver driver) {
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
        DriverLicense driverLicense = (DriverLicense) o;
        if (driverLicense.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driverLicense.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DriverLicense{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", validate='" + getValidate() + "'" +
            ", specialNotes='" + getSpecialNotes() + "'" +
            "}";
    }
}
