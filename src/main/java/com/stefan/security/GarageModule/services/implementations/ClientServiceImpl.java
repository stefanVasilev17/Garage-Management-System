package com.stefan.security.GarageModule.services.implementations;

import com.stefan.security.GarageModule.data.entity.Client;
import com.stefan.security.GarageModule.data.entity.Garage;
import com.stefan.security.GarageModule.data.entity.Vehicle;
import com.stefan.security.GarageModule.data.repository.ClientRepository;
import com.stefan.security.GarageModule.data.repository.GarageRepository;
import com.stefan.security.GarageModule.services.ClientService;
import com.stefan.security.GarageModule.services.VehicleService;
import com.stefan.security.GarageModule.web.view.ClientView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Validated
public class ClientServiceImpl implements ClientService
{
  private final ClientRepository  clientRepository;
  private final VehicleService    vehicleService;
  private final GarageRepository  garageRepository;
  private final ModelMapper        modelMapper = new ModelMapper();

  @Override
  public List<ClientView> findAllClientsByGarageId(Long id)
  {
    return clientRepository.findAllClientsByGarageId(id)
        .stream().map(client -> modelMapper.map(client, ClientView.class)).collect(Collectors.toList());
  }

  @Override
  public Client create(Client addNewClient)
  {
    garageRepository.findById(addNewClient.getGarage().getId()).orElseThrow(()
        -> new IllegalArgumentException("Invalid garage id:" + addNewClient.getGarage().getId()));
    Optional<Garage> garage = garageRepository.findById(addNewClient.getGarage().getId());
    garage.ifPresent(addNewClient::setGarage);

    if (
        clientRepository.findAllByTelephoneNumber(addNewClient.getTelephoneNumber())
            .stream()
            .anyMatch(client -> client.getTelephoneNumber()
                .equals(addNewClient.getTelephoneNumber()))) {
      throw new IllegalArgumentException("This client telephone number " + addNewClient.getTelephoneNumber() + " is already in the database!");
    }
    clientRepository.save(addNewClient);
    if (!addNewClient.getOwnedVehicles().isEmpty()) {
      for (Vehicle veh : addNewClient.getOwnedVehicles()) {
        veh.setClient(addNewClient);
        vehicleService.create(veh);
      }

      //    addNewClient.getOwnedVehicles().forEach(vehicleRepository::save);
    }

    return addNewClient;
  }

  @Override
  public Client updateClient(Long id, Client updateClient)
  {
    Client client = clientRepository.getReferenceById(id);
    client.setBudget(updateClient.getBudget());

    return clientRepository.save(client);
  }

  @Override
  public void deleteClient(Long id)
  {
    clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid client id:" + id));
    clientRepository.deleteById(id);
  }

}
