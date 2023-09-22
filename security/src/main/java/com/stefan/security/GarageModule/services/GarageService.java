package com.stefan.security.GarageModule.services;

import com.stefan.security.GarageModule.data.entity.Garage;

import java.util.List;

public interface GarageService
{
  List<Garage> getGarages();

  Garage create(Garage createdGarage);

  Garage updateGarage(Long id, Garage updateGarage);

  void deleteGarage(Long id);

}
