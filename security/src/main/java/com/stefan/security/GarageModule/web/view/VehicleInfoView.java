package com.stefan.security.GarageModule.web.view;

import com.stefan.security.GarageModule.data.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class VehicleInfoView extends BaseEntity
{
  private String    licensePlate;
  private String    brand;
  private String    petrol;
  private String    litres;
  private Long      horsePower;
  private String    drive;
  private LocalDate year;
  private String    mechanicDecision;
}
