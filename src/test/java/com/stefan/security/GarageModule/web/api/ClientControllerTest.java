package com.stefan.security.GarageModule.web.api;

import com.stefan.security.GarageModule.data.entity.*;
import com.stefan.security.GarageModule.data.repository.ClientRepository;
import com.stefan.security.GarageModule.data.repository.GarageRepository;
import com.stefan.security.GarageModule.data.repository.MechanicRepository;
import com.stefan.security.GarageModule.data.repository.VehicleRepository;
import com.stefan.security.GarageModule.services.implementations.ClientServiceImpl;
import com.stefan.security.GarageModule.services.implementations.VehicleServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientServiceImpl clientService;

    @Mock
    private GarageRepository garageRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Mock
    private MechanicRepository mechanicRepository;

    Garage firstGarage =
            new Garage("Cent50Garage"
                    , KindOfServices.FULL_EXPERIENCE.toString()
                    , BigDecimal.ZERO
                    , BigDecimal.ZERO
                    , new ArrayList<>()
                    , new ArrayList<>());

    Client client = new Client("88453214"
            , BigDecimal.valueOf(2000)
            , firstGarage
            , new ArrayList<>());

    Vehicle vehicle = new Vehicle("A8787VR"
            ,"PEUGEOT"
            ,"DIESEL"
            ,"2.0 HDI"
            ,140
            ,"FRONT"
            , LocalDate.of(2004,4,20)
            ,"noise at front left tire"
            ,"WHEEL"
            ,null
            ,client
            ,null
            ,false);

    Mechanic mechanic = new Mechanic(KindOfServices.FULL_EXPERIENCE
            ,"EXPERT"
            ,"PEUGEOT"
            ,BigDecimal.valueOf(4000)
            ,true
            ,firstGarage
            ,new ArrayList<>());

    @Test
    public void getAllClientsByGarageId() {
        when(garageRepository.findById(firstGarage.getId())).thenReturn(Optional.ofNullable(firstGarage));
        when(clientRepository.save(any())).thenReturn(client);

        Client savedClient = clientService.create(client);

        when(clientRepository.findAllClientsByGarageId(firstGarage.getId())).thenReturn(List.of(savedClient));

        List<Client> garageClients = clientRepository.findAllClientsByGarageId(firstGarage.getId());

        Assertions.assertThat(garageClients.size()).isEqualTo(1);
        verify(clientRepository,times(1)).findAllClientsByGarageId(firstGarage.getId());

    }

    @Test
    public void addNewClient() {
        addClient();
    }

    @Test
    public void updateClient() {
        when(clientRepository.getReferenceById(client.getId())).thenReturn(client);
        client.setBudget(BigDecimal.valueOf(800));
        when(clientRepository.save(any())).thenReturn(client);
        Client updatedClient = clientService.updateClient(client.getId(), client);

        assertEquals(client.getBudget(), updatedClient.getBudget());
        verify(clientRepository, times(1)).save(updatedClient);
    }

    @Test
    public void deleteClient() {
        addClient();

        doNothing().when(clientRepository).deleteById(client.getId());
        when(clientRepository.findById(client.getId())).thenReturn(Optional.ofNullable(client));
        clientService.deleteClient(client.getId());
        verify(clientRepository,times(1)).deleteById(client.getId());
    }

    private void addClient() {
        when(garageRepository.findById(firstGarage.getId())).thenReturn(Optional.ofNullable(firstGarage));
        when(clientRepository.save(any())).thenReturn(client);

        Client savedClient = clientService.create(client);

        assertNotNull(savedClient);
        assertEquals(client.getTelephoneNumber(), savedClient.getTelephoneNumber());
        verify(clientRepository, times(1)).save(savedClient);
    }

    @Test
    public void findAllVehiclesByClientId() {
        when(vehicleRepository
                .findAllVehiclesByClientId(client.getId())).thenReturn(List.of(vehicle));

        List<Vehicle> ownedVehicles = vehicleRepository.findAllVehiclesByClientId(client.getId());

        Assertions.assertThat(ownedVehicles.size()).isEqualTo(1);
        verify(vehicleRepository,times(1)).findAllVehiclesByClientId(client.getId());

    }

    @Test
    public void addNewVehicle() {
        addVehicleMethod();
    }

    @Test
    public void deleteVehicleById() {
        addVehicleMethod();

        doNothing().when(vehicleRepository).deleteById(any());
        vehicleService.deleteVehicle(vehicle.getId());
        verify(vehicleRepository,times(1)).deleteById(any());
    }

    private void addVehicleMethod() {
        when(vehicleRepository.findVehicleByLicensePlate(vehicle.getLicensePlate())).thenReturn(vehicle);
        when(clientRepository.getReferenceById(any())).thenReturn(client);
        when(vehicleRepository.save(any())).thenReturn(vehicle);

        Vehicle newVehicle = vehicleService.create(vehicle);

        assertNotNull(newVehicle);
        assertEquals(vehicle.getLicensePlate(),newVehicle.getLicensePlate());
        verify(vehicleRepository,times(1)).save(newVehicle);
    }

    @Test
    public void fixVehicleById() {
        BigDecimal amountOfMoney = BigDecimal.valueOf(500);

        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));
        when(mechanicRepository.findMechanicByQualificationAndGarageId(any(),any())).thenReturn(List.of(mechanic));
        when(garageRepository.findById(any())).thenReturn(Optional.ofNullable(firstGarage));
        when(clientRepository.save(any())).thenReturn(client);
        when(vehicleRepository.save(any())).thenReturn(vehicle);
        doNothing().when(vehicleRepository).deleteById(any());

        addNewVehicle();

        CashReceipt cashReceipt = vehicleService.fixVehicle(vehicle.getId()
                ,amountOfMoney
                ,false
                ,null
                ,false
                ,null);

        assertNotNull(cashReceipt);
        assertEquals(cashReceipt.getAmount(),firstGarage.getTurnOver());
        verify(vehicleRepository, times(1)).deleteById(any());

    }

//    @Test
//    public void getMechanicsByQualificationAndByGarageId() {
//    }
}