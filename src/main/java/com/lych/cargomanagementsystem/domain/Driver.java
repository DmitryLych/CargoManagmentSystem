package com.lych.cargomanagementsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "year_of_issue", nullable = false)
    private LocalDate yearOfIssue;

    @Column(name = "address")
    private String address;

    @NotNull
    @Size(min = 9)
    @Column(name = "tepephone_number", nullable = false)
    private String tepephoneNumber;

    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private Boolean status;

    @OneToOne
    @JoinColumn(unique = true)
    private DriverLicense driverLicense;

    @OneToOne
    @JoinColumn(unique = true)
    private MedicalExamination medicalExamination;

    @OneToOne
    @JoinColumn(unique = true)
    private Truck truck;

    @OneToMany(mappedBy = "driver")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InsurancePolicy> insurancePolicies = new HashSet<>();

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "driver")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Order> orders = new HashSet<>();

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Driver firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Driver lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getYearOfIssue() {
        return yearOfIssue;
    }

    public Driver yearOfIssue(LocalDate yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
        return this;
    }

    public void setYearOfIssue(LocalDate yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public String getAddress() {
        return address;
    }

    public Driver address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTepephoneNumber() {
        return tepephoneNumber;
    }

    public Driver tepephoneNumber(String tepephoneNumber) {
        this.tepephoneNumber = tepephoneNumber;
        return this;
    }

    public void setTepephoneNumber(String tepephoneNumber) {
        this.tepephoneNumber = tepephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Driver email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isStatus() {
        return status;
    }

    public Driver status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public DriverLicense getDriverLicense() {
        return driverLicense;
    }

    public Driver driverLicense(DriverLicense driverLicense) {
        this.driverLicense = driverLicense;
        return this;
    }

    public void setDriverLicense(DriverLicense driverLicense) {
        this.driverLicense = driverLicense;
    }

    public MedicalExamination getMedicalExamination() {
        return medicalExamination;
    }

    public Driver medicalExamination(MedicalExamination medicalExamination) {
        this.medicalExamination = medicalExamination;
        return this;
    }

    public void setMedicalExamination(MedicalExamination medicalExamination) {
        this.medicalExamination = medicalExamination;
    }

    public Truck getTruck() {
        return truck;
    }

    public Driver truck(Truck truck) {
        this.truck = truck;
        return this;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public Set<InsurancePolicy> getInsurancePolicies() {
        return insurancePolicies;
    }

    public Driver insurancePolicies(Set<InsurancePolicy> insurancePolicies) {
        this.insurancePolicies = insurancePolicies;
        return this;
    }

    public Driver addInsurancePolicies(InsurancePolicy insurancePolicy) {
        this.insurancePolicies.add(insurancePolicy);
        insurancePolicy.setDriver(this);
        return this;
    }

    public Driver removeInsurancePolicies(InsurancePolicy insurancePolicy) {
        this.insurancePolicies.remove(insurancePolicy);
        insurancePolicy.setDriver(null);
        return this;
    }

    public void setInsurancePolicies(Set<InsurancePolicy> insurancePolicies) {
        this.insurancePolicies = insurancePolicies;
    }

    public Company getCompany() {
        return company;
    }

    public Driver company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Driver orders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Driver addOrder(Order order) {
        this.orders.add(order);
        order.setDriver(this);
        return this;
    }

    public Driver removeOrder(Order order) {
        this.orders.remove(order);
        order.setDriver(null);
        return this;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public User getUser() {
        return user;
    }

    public Driver user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Driver driver = (Driver) o;
        if (driver.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), driver.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Driver{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", yearOfIssue='" + getYearOfIssue() + "'" +
            ", address='" + getAddress() + "'" +
            ", tepephoneNumber='" + getTepephoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
