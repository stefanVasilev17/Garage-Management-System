package com.stefan.security.GarageModule.services.implementations;

import com.stefan.security.GarageModule.data.entity.*;
import com.stefan.security.GarageModule.data.repository.ClientRepository;
import com.stefan.security.GarageModule.data.repository.GarageRepository;
import com.stefan.security.GarageModule.data.repository.MechanicRepository;
import com.stefan.security.GarageModule.data.repository.VehicleRepository;
import com.stefan.security.GarageModule.services.VehicleService;
import com.stefan.security.GarageModule.web.view.VehicleInfoView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Validated
public class VehicleServiceImpl implements VehicleService
{
  private final VehicleRepository  vehicleRepository;
  private final MechanicRepository mechanicRepository;
  private final GarageRepository   garageRepository;
  private final ClientRepository   clientRepository;
  private final ModelMapper        modelMapper = new ModelMapper();

  @Override
  public Vehicle create(Vehicle vehicle)
  {
    if (vehicleRepository.findVehicleByLicensePlate(vehicle.getLicensePlate())
        .stream()
        .anyMatch(vehicle1
            -> vehicle1.getLicensePlate()
            .equals(vehicle.getLicensePlate()))) {
      throw new IllegalArgumentException("This vehicle " + vehicle.getLicensePlate() + " is already in the database!");
    }
    translateDriverComplaints(vehicle);
    Client client = clientRepository.getById(vehicle.getClient().getId());
    vehicle.setClient(client);
    return vehicleRepository.save(vehicle);
  }

  @Override
  public void fixVehicle(Long id, BigDecimal amountOfMoney)
  {

    Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(
        () -> new NoSuchElementException("This vehicle does not exist!"));

    Client ownerOfTheVeh = vehicle.getClient();

    if (amountOfMoney.compareTo(ownerOfTheVeh.getBudget()) > 0) {
      throw new NoSuchElementException("Client's money are not enough.");
    }

    findSuitableMechanic(vehicle, ownerOfTheVeh.getGarage().getId());
    vehicleRepository.save(vehicle);

    if (garageRepository.findById(ownerOfTheVeh.getGarage().getId()).isPresent()) {
      Garage garage = garageRepository.findById(ownerOfTheVeh.getGarage().getId()).get();

      ownerOfTheVeh.setBudget(ownerOfTheVeh.getBudget().subtract(amountOfMoney));

      clientRepository.save(ownerOfTheVeh);

      garage.setDayBudget(ownerOfTheVeh.getGarage().getDayBudget().add(amountOfMoney));
      garage.setTurnOver(amountOfMoney);
      garageRepository.save(garage);
      vehicleRepository.deleteById(vehicle.getId());

    }
  }

  private void findSuitableMechanic(Vehicle vehicle, Long id)
  {
    if (vehicle.getMechanicDecision().contains(Problem.TIRE.toString())
        || vehicle.getMechanicDecision().contains(Problem.TIRES.toString())
        || vehicle.getMechanicDecision().contains(Problem.WHEEL.toString())
        || vehicle.getMechanicDecision().contains(Problem.WHEELS.toString())) {

      vehicle.setMechanic(mechanicRepository.findMechanicByQualificationAndGarageId("LIGHT", id).stream().findFirst().
          orElse(mechanicRepository.findMechanicByQualificationAndGarageId("ADVANCED", id).stream().findFirst().
              orElse(mechanicRepository.findMechanicByQualificationAndGarageId("FULL EXPERIENCE", id).stream().findFirst()
                  .orElseThrow(() -> new NoSuchElementException("There are no mechanics with these qualifications!")))));

    }

    if (vehicle.getMechanicDecision().contains(Problem.DAMAGE.toString())
        || vehicle.getMechanicDecision().contains(Problem.CHECK.toString())
        || vehicle.getMechanicDecision().contains(Problem.BREAKS.toString())
        || vehicle.getMechanicDecision().contains(Problem.SUSPENSION.toString())) {

      vehicle.setMechanics(mechanicRepository.findMechanicByQualificationAndGarageId("ADVANCED", id).stream().findFirst().
          orElse(mechanicRepository.findMechanicByQualificationAndGarageId("FULL_EXPERIENCE", id).stream().findFirst()
              .orElseThrow(() -> new NoSuchElementException("There are no mechanics with these qualifications!"))).getName());
    }

    if (vehicle.getMechanicDecision().contains(Problem.GEARBOX.toString())
        || vehicle.getMechanicDecision().contains(Problem.ENGINE.toString())) {
      vehicle.setMechanics(mechanicRepository.findMechanicByQualificationAndGarageId("FULL EXPERIENCE", id).stream().findFirst()
          .orElseThrow(() -> new NoSuchElementException("There are no mechanics with these qualifications!")).getName());
    }
  }

  /**
   * Translates the driver's complaints in engineer's language.
   */
  private void translateDriverComplaints(Vehicle vehicle)
  {
    StringBuilder translateDriverComplaints = new StringBuilder();
    List<Problem> problemList = new ArrayList<>();
    problemList.add(Problem.BREAKS);
    problemList.add(Problem.DAMAGE);
    problemList.add(Problem.ENGINE);
    problemList.add(Problem.GEARBOX);
    problemList.add(Problem.SUSPENSION);
    problemList.add(Problem.TIRES);
    problemList.add(Problem.TIRE);
    problemList.add(Problem.WHEELS);
    problemList.add(Problem.WHEEL);
    problemList.add(Problem.CHECK);

    for (Problem problem : problemList) {

      if (vehicle.getDriverComplaints().contains(problem.toString())) {
        translateDriverComplaints.append(problem.toString()).append(",");

        vehicle.setMechanicDecision(translateDriverComplaints.toString());
      }
    }

    vehicle.setMechanicDecision(vehicle.getMechanicDecision().endsWith(",")
        ? vehicle.getMechanicDecision().substring(0, vehicle.getMechanicDecision()
        .lastIndexOf(",")) : vehicle.getMechanicDecision());

  }


  @Override
  public void deleteVehicle(Long id)
  {
    vehicleRepository.deleteById(id);
  }

  @Override
  public List<VehicleInfoView> findAllVehiclesByClientId(Long id)
  {
    return vehicleRepository.findAllVehiclesByClientId(id)
        .stream().map(vehicle -> modelMapper.map(vehicle, VehicleInfoView.class)).collect(Collectors.toList());
  }
}
