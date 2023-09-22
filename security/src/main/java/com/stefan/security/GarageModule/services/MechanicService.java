package com.stefan.security.GarageModule.services;

import com.stefan.security.GarageModule.data.entity.Mechanic;
import com.stefan.security.GarageModule.dto.MechanicDTO;
import com.stefan.security.GarageModule.web.view.MechanicView;

import java.util.List;

public interface MechanicService
{
  List<MechanicDTO> getMechanicsByGarageId(Long id);

  Mechanic hireMechanic(Mechanic hireMechanic);

  void fireMechanic(Long id);

  List<Mechanic> findAllMechanicsByGarageId(Long id);

  List<MechanicView> findMechanicByQualificationAndGarageId(String qualification, Long id);
}
