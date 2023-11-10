package com.stefan.security.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "_car_table")
@Data
@Builder
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String model;

    @Column(name = "year_of_manufacture")
    private Integer yearOfManufacture;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private BrandEntity brand;
}
