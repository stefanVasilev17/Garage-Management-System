package com.stefan.security.GarageModule.services.implementations;

import com.stefan.security.GarageModule.data.entity.Garage;
import com.stefan.security.GarageModule.data.repository.GarageRepository;
import com.stefan.security.GarageModule.dto.GarageDTO;
import com.stefan.security.GarageModule.services.GarageService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.ws.rs.ForbiddenException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Validated
public class GarageServiceImpl implements GarageService
{
  private final GarageRepository garageRepository;
  private final ModelMapper        modelMapper = new ModelMapper();

  @Override
  public List<Garage> getGarages()
  {
    return new ArrayList<>(garageRepository.findAll());
  }

  @Override
  public Garage create(Garage createdGarage)
  {
    createdGarage.setTurnOver(BigDecimal.ZERO);
    return garageRepository.save(createdGarage);
  }

  @Override
  public Garage updateGarage(Long id, Garage updateGarage)
  {
    updateGarage.setId(id);
    return garageRepository.save(updateGarage);
  }

  @Override
  public void deleteGarage(Long id)
  {
    Garage garage = garageRepository.findById(id).orElseThrow(()
        -> new IllegalArgumentException("Invalid garage id:" + id));
    if (!garage.getActiveClients().isEmpty()) {
      throw new ForbiddenException("This garage cannot be deleted, because there are active clients. Please first fix the cars!");
    }
    else if (!garage.getHiredMechanics().isEmpty()) {
      throw new ForbiddenException("This garage cannot be deleted, because there are hired mechanics. Please, first fire them!");
    }
    else {
      garageRepository.deleteById(id);
    }
  }


  private GarageDTO convertToGarageDTO(Garage garage)
  {
    return modelMapper.map(garage, GarageDTO.class);
  }
}
