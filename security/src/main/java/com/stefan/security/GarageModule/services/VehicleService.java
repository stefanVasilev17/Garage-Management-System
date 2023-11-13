package com.stefan.security.GarageModule.services;

import com.stefan.security.GarageModule.data.entity.Vehicle;
import com.stefan.security.GarageModule.web.view.VehicleInfoView;

import java.math.BigDecimal;
import java.util.List;

public interface VehicleService
{
  Vehicle create(Vehicle vehicle);

  <T> T fixVehicle(Long id, BigDecimal amountOfMoney,boolean byFacture,Long companyNumber);

  void deleteVehicle(Long id);

  List<VehicleInfoView> findAllVehiclesByClientId(Long id);
}
