package com.stefan.security.GarageModule.dto;

import com.stefan.security.GarageModule.data.entity.BaseEntity;
import com.stefan.security.GarageModule.data.entity.Client;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class VehicleDTO extends BaseEntity
{
  private String    licensePlate;
  private String    brand;
  private String    petrol;
  private String    litres;
  private Double    horsePower;
  private String    drive;
  private LocalDate year;

  private String driverComplaints;
  private String mechanicDecision;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "client_id")
  private Client client;
}
