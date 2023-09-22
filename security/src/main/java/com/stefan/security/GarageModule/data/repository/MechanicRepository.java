package com.stefan.security.GarageModule.data.repository;

import com.stefan.security.GarageModule.data.entity.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MechanicRepository extends JpaRepository<Mechanic, Long>
{
  // List<Vehicle> findAllVehiclesByClientId(Long id);
  List<Mechanic> findAllMechanicsByGarageId(Long id);

  List<Mechanic> findMechanicByQualificationAndGarageId(String qualification,Long id);
}
