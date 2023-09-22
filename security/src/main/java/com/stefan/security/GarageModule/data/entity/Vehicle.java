package com.stefan.security.GarageModule.data.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vehicle")
public class Vehicle extends BaseEntity
{
  private String    licensePlate;
  @NotBlank
  private String    brand;
  @NotBlank
  private String    petrol;
  @NotBlank
  private String    litres;

  private Integer    horsePower;
  private String    drive;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate year;
  @NotBlank
  private String    driverComplaints;
  private String    mechanicDecision;
  private String    mechanics;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "client_id")
  private Client client;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "mechanic_id")
  private Mechanic mechanic;


}
