package com.example.provapratica.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "The origin parameter must not be blank!")
    private String origin;

    @NotNull(message = "The destination parameter must not be blank!")
    private String destination;

    @NotNull(message = "The departureTime parameter must not be blank!")
    private LocalDateTime departureTime;

    @DecimalMin(value = "0.0", inclusive = true, message = "The price must be non-negative!")
    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public Trip setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public Trip setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public Trip setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public Trip setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Trip setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
