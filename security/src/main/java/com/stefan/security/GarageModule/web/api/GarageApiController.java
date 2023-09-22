package com.stefan.security.GarageModule.web.api;

import com.stefan.security.GarageModule.data.entity.Garage;
import com.stefan.security.GarageModule.data.entity.Mechanic;
import com.stefan.security.GarageModule.services.GarageService;
import com.stefan.security.GarageModule.services.MechanicService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * This controller represent C,R,U,D operations about garage.
 *
 * @author svasilev
 */

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/garages")
public class GarageApiController {
    private final GarageService garageService;
    private final MechanicService mechanicService;

    @GetMapping()
    public List<Garage> getGarages() {
        return garageService.getGarages();
    }

    /**
     * This method creates garage.
     *
     * @param createdGarage - this object store information about garage.
     * @return the created object.
     */
    @PostMapping("/create-garage")
    public Garage createGarage(@RequestBody @Valid Garage createdGarage) {
        return garageService.create(createdGarage);
    }

    /**
     * This method updates garage by id.
     *
     * @param id           - id of the garage
     * @param updateGarage - this object store information about garage.
     * @return the updated object.
     */
    @PutMapping("/update-garage/{id}")
    public Garage updateGarage(@PathVariable("id") Long id, @RequestBody @Valid Garage updateGarage) {
        return garageService.updateGarage(id, updateGarage);
    }

    /**
     * This method deletes garage by id.
     *
     * @param id - id of the garage
     */
    @DeleteMapping("/delete-garage/{id}")
    public void deleteGarage(@PathVariable("id") Long id) {
        garageService.deleteGarage(id);
    }

    /**
     * This method returns all mechanics by garage id.
     *
     * @param id - id of the garage.
     * @return list of mechanic objects.
     */
    @GetMapping("/get-mechanics-by-garage-id/{id}")
    public List<Mechanic> getAllMechanicsByGarageId(@PathVariable("id") Long id) {
        return mechanicService.findAllMechanicsByGarageId(id);
    }

    /**
     * This method hire mechanics on duty.
     *
     * @param hiredMechanic - information about mechanic.
     * @return the created object of Mechanic's class.
     */
    @PostMapping("/hire-mechanic")
    public Mechanic hireMechanic(@RequestBody @Valid Mechanic hiredMechanic) {
        return mechanicService.hireMechanic(hiredMechanic);
    }

    /**
     * This method fire mechanics.
     *
     * @param id - the id of the mechanic who have to be deleted.
     */

    @DeleteMapping("/fire-mechanic/{id}")
    public void fireMechanic(@PathVariable("id") Long id) {
        mechanicService.fireMechanic(id);
    }

}
