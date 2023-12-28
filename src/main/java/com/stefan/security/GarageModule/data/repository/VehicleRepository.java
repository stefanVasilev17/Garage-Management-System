package com.stefan.security.GarageModule.data.repository;

import com.stefan.security.GarageModule.data.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>
{
  List<Vehicle> findAllVehiclesByClientId(Long id);

  Vehicle findVehicleByLicensePlate(String licensePlate);
}
