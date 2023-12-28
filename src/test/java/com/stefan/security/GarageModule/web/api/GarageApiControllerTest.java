package com.stefan.security.GarageModule.web.api;

import com.stefan.security.GarageModule.data.entity.Garage;
import com.stefan.security.GarageModule.data.entity.KindOfServices;
import com.stefan.security.GarageModule.data.entity.Mechanic;
import com.stefan.security.GarageModule.data.repository.GarageRepository;
import com.stefan.security.GarageModule.data.repository.MechanicRepository;
import com.stefan.security.GarageModule.services.implementations.GarageServiceImpl;
import com.stefan.security.GarageModule.services.implementations.MechanicServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GarageApiControllerTest {

    @Mock
    private GarageRepository garageRepository;
    @Mock
    private MechanicRepository mechanicRepository;

    @InjectMocks
    private GarageServiceImpl garageService;

    @InjectMocks
    private MechanicServiceImpl mechanicService;

    Garage     firstGarage =
            new Garage("Cent50Garage"
                    , KindOfServices.FULL_EXPERIENCE.toString()
                    , BigDecimal.ZERO
                    , BigDecimal.ZERO
                    , new ArrayList<>()
                    , new ArrayList<>());
    Garage secondGarage =
            new Garage("SlimShadyGrg"
                    , KindOfServices.FULL_EXPERIENCE.toString()
                    , BigDecimal.ZERO
                    , BigDecimal.ZERO
                    , new ArrayList<>()
                    , new ArrayList<>());

    Mechanic mechanic = new Mechanic(KindOfServices.FULL_EXPERIENCE
            ,"EXPERT"
            ,"PEUGEOT"
            ,BigDecimal.valueOf(4000)
            ,true
            ,firstGarage
            ,new ArrayList<>());


    @Test
    public void getGarages() {

        List<Garage> listOfGarages = List.of(firstGarage, secondGarage);
        when(garageRepository.findAll()).thenReturn(listOfGarages);

        List<Garage> savedGarages = garageService.getGarages();

        Assertions.assertThat(savedGarages.size()).isEqualTo(2);
    }

    @Test
    public void createGarage() {

        when(garageRepository.save(any())).thenReturn(firstGarage);

        Garage result = garageService.create(firstGarage);

        assertNotNull(result);
        assertEquals(BigDecimal.ZERO, result.getTurnOver());
        verify(garageRepository, times(1)).save(firstGarage);

    }

    @Test
    public void updateGarage() {
        firstGarage.setName("ThorGarage");
        when(garageRepository.save(any())).thenReturn(firstGarage);
        Garage result = garageService.updateGarage(firstGarage.getId(),firstGarage);

        assertEquals(firstGarage.getName(),result.getName());
        verify(garageRepository, times(1)).save(firstGarage);
    }

    @Test
    public void deleteGarage() {

        when(garageRepository.save(firstGarage)).thenReturn(firstGarage);

        Garage result = garageService.create(firstGarage);

        assertNotNull(result);
        assertEquals(0, result.getId());
        verify(garageRepository, times(1)).save(firstGarage);
        when(garageRepository.findById(firstGarage.getId())).thenReturn(Optional.ofNullable(firstGarage));

        doNothing().when(garageRepository).deleteById(firstGarage.getId());
        garageService.deleteGarage(firstGarage.getId());
        verify(garageRepository, times(1)).deleteById(firstGarage.getId());

        //try to delete the same garage again.
        doThrow(NoSuchElementException.class).when(garageRepository).deleteById(firstGarage.getId());
        assertThrows(NoSuchElementException.class, () -> garageService.deleteGarage(firstGarage.getId()));
        verify(garageRepository, times(2)).deleteById(firstGarage.getId());

    }

    @Test
    public void getAllMechanicsByGarageId() {

        when(mechanicRepository.save(mechanic)).thenReturn(mechanic);
        when(garageRepository.findById(firstGarage.getId())).thenReturn(Optional.ofNullable(firstGarage));
        when(mechanicRepository.findAllMechanicsByGarageId(firstGarage.getId())).thenReturn(List.of(mechanic));

        mechanicService.hireMechanic(mechanic);

        List<Mechanic> hiredMechanics = mechanicService.findAllMechanicsByGarageId(firstGarage.getId());

        Assertions.assertThat(hiredMechanics.size()).isEqualTo(1);
        verify(mechanicRepository, times(1)).save(mechanic);

    }

    @Test
    public void hireMechanic() {

        when(mechanicRepository.save(mechanic)).thenReturn(mechanic);
        when(garageRepository.findById(firstGarage.getId())).thenReturn(Optional.ofNullable(firstGarage));

        mechanicService.hireMechanic(mechanic);
        verify(mechanicRepository, times(1)).save(mechanic);
    }

    @Test
    public void fireMechanic() {

        when(mechanicRepository.save(mechanic)).thenReturn(mechanic);
        when(garageRepository.findById(firstGarage.getId())).thenReturn(Optional.ofNullable(firstGarage));
        when(mechanicRepository.findById(firstGarage.getId())).thenReturn(Optional.ofNullable(mechanic));

        mechanicService.hireMechanic(mechanic);
        verify(mechanicRepository, times(1)).save(mechanic);
        doNothing().when(mechanicRepository).deleteById(mechanic.getId());

        mechanicService.fireMechanic(firstGarage.getId());
        verify(mechanicRepository,times(1)).deleteById(mechanic.getId());
    }
}