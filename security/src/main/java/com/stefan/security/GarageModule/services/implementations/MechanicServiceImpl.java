package com.stefan.security.GarageModule.services.implementations;

import com.stefan.security.GarageModule.data.entity.Garage;
import com.stefan.security.GarageModule.data.entity.Mechanic;
import com.stefan.security.GarageModule.data.repository.GarageRepository;
import com.stefan.security.GarageModule.data.repository.MechanicRepository;
import com.stefan.security.GarageModule.dto.MechanicDTO;
import com.stefan.security.GarageModule.services.MechanicService;
import com.stefan.security.GarageModule.web.view.MechanicView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.ws.rs.ForbiddenException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.stefan.security.GarageModule.data.entity.KindOfServices.ADVANCED;
import static com.stefan.security.GarageModule.data.entity.KindOfServices.LIGHT;


@Service
@AllArgsConstructor
@Validated
public class MechanicServiceImpl implements MechanicService
{
  private final MechanicRepository mechanicRepository;
  private final GarageRepository   garageRepository;
  private final ModelMapper        modelMapper = new ModelMapper();

  @Override
  public List<MechanicDTO> getMechanicsByGarageId(Long id)
  {
    return mechanicRepository.findById(id).stream().filter(mechanic -> mechanic.getGarage().getId() == id)
        .map(this::convertToMechanicDTO).collect(Collectors.toList());
  }

  @Override
  public List<Mechanic> findAllMechanicsByGarageId(Long id)
  {
    return mechanicRepository.findAllMechanicsByGarageId(id);
  }

  @Override
  public Mechanic hireMechanic(Mechanic hiredMechanic)
  {
    Optional<Garage> garage = garageRepository.findById(hiredMechanic.getGarage().getId());
    garage.ifPresent(hiredMechanic::setGarage);

    if (LIGHT.toString().equals(hiredMechanic.getQualification())
        && hiredMechanic.getSalary().compareTo(BigDecimal.valueOf(2000)) > 0) {
      throw new ForbiddenException("Тhe qualification of the candidate does not allow a higher salary");
    }
    else if (ADVANCED.toString().equals(hiredMechanic.getQualification())
        && hiredMechanic.getSalary().compareTo(BigDecimal.valueOf(4000)) > 0) {

      throw new ForbiddenException("Тhe qualification of the candidate does not allow a higher salary");
    }
    return mechanicRepository.save(hiredMechanic);
  }

  @Override
  public void fireMechanic(Long id)
  {
    mechanicRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid mechanic id:" + id));
    mechanicRepository.deleteById(id);
  }

  @Override
  public List<MechanicView> findMechanicByQualificationAndGarageId(String qualification, Long id)
  {
    return mechanicRepository.findMechanicByQualificationAndGarageId(qualification, id).stream()
        .map(mechanic -> modelMapper.map(mechanic, MechanicView.class)).collect(Collectors.toList());
  }

  private MechanicDTO convertToMechanicDTO(Mechanic mechanic)
  {
    return modelMapper.map(mechanic, MechanicDTO.class);
  }

}
