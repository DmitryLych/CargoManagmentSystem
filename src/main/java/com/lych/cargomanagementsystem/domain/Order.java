package com.lych.cargomanagementsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_cost", precision=10, scale=2, nullable = false)
    private BigDecimal cost;

    @NotNull
    @Column(name = "download_address", nullable = false)
    private String downloadAddress;

    @NotNull
    @Column(name = "unloading_address", nullable = false)
    private String unloadingAddress;

    @Column(name = "issued")
    private Boolean issued;

    @Column(name = "completed")
    private Boolean completed;

    @Column(name = "paid")
    private Boolean paid;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Goods> goods = new HashSet<>();

    @ManyToOne
    private Driver driver;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Order cost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDownloadAddress() {
        return downloadAddress;
    }

    public Order downloadAddress(String downloadAddress) {
        this.downloadAddress = downloadAddress;
        return this;
    }

    public void setDownloadAddress(String downloadAddress) {
        this.downloadAddress = downloadAddress;
    }

    public String getUnloadingAddress() {
        return unloadingAddress;
    }

    public Order unloadingAddress(String unloadingAddress) {
        this.unloadingAddress = unloadingAddress;
        return this;
    }

    public void setUnloadingAddress(String unloadingAddress) {
        this.unloadingAddress = unloadingAddress;
    }

    public Boolean isIssued() {
        return issued;
    }

    public Order issued(Boolean issued) {
        this.issued = issued;
        return this;
    }

    public void setIssued(Boolean issued) {
        this.issued = issued;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public Order completed(Boolean completed) {
        this.completed = completed;
        return this;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean isPaid() {
        return paid;
    }

    public Order paid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Order customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Goods> getGoods() {
        return goods;
    }

    public Order goods(Set<Goods> goods) {
        this.goods = goods;
        return this;
    }

    public Order addGoods(Goods goods) {
        this.goods.add(goods);
        goods.setOrder(this);
        return this;
    }

    public Order removeGoods(Goods goods) {
        this.goods.remove(goods);
        goods.setOrder(null);
        return this;
    }

    public void setGoods(Set<Goods> goods) {
        this.goods = goods;
    }

    public Driver getDriver() {
        return driver;
    }

    public Order driver(Driver driver) {
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
        Order order = (Order) o;
        if (order.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", cost=" + getCost() +
            ", downloadAddress='" + getDownloadAddress() + "'" +
            ", unloadingAddress='" + getUnloadingAddress() + "'" +
            ", issued='" + isIssued() + "'" +
            ", completed='" + isCompleted() + "'" +
            ", paid='" + isPaid() + "'" +
            "}";
    }
}
