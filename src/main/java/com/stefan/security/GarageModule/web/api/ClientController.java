package com.stefan.security.GarageModule.web.api;

import com.stefan.security.GarageModule.data.entity.*;
import com.stefan.security.GarageModule.services.ClientService;
import com.stefan.security.GarageModule.services.MechanicService;
import com.stefan.security.GarageModule.services.VehicleService;
import com.stefan.security.GarageModule.web.view.ClientView;
import com.stefan.security.GarageModule.web.view.MechanicView;
import com.stefan.security.GarageModule.web.view.VehicleInfoView;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;
    private final VehicleService vehicleService;
    private final MechanicService mechanicService;

    /**
     * This method returns all clients by garage id.
     *
     * @param id - garage id.
     * @return list of ClientView's objects.
     */
    @GetMapping("/get-all-clients-by-garage-id/{id}")
    public List<ClientView> getAllClientsByGarageId(@PathVariable("id") Long id) {
        return clientService.findAllClientsByGarageId(id);
    }

    /**
     * This method creates new Client.
     *
     * @param addNewClient - client's information.
     * @return created object.
     */
    @PostMapping("/add-new-client")
    public Client addNewClient(@RequestBody @Valid Client addNewClient) {
        return clientService.create(addNewClient);
    }

    /**
     * This method updates client by id.
     *
     * @param id           - id of the garage
     * @param updateClient - this object store information about client.
     * @return the updated object.
     */
    @PutMapping("/update-client/{id}")
    public Client updateClient(@PathVariable("id") Long id, @RequestBody @Valid Client updateClient) {
        return clientService.updateClient(id, updateClient);
    }

    /**
     * This method deletes client by id.
     *
     * @param id - id of the client
     */
    @DeleteMapping("/delete-client/{id}")
    public void deleteClient(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
    }

    /**
     * This method returns all vehicles by client id.
     *
     * @param id - client id.
     * @return list of vehicle's objects.
     */
    @GetMapping("/find-all-vehicles-by-client-id/{id}")
    public List<VehicleInfoView> findAllVehiclesByClientId(@PathVariable("id") Long id) {
        return vehicleService.findAllVehiclesByClientId(id);
    }

    /**
     * This method adds new vehicle.
     *
     * @param vehicle - vehicle's information.
     * @return created object.
     */
    @PostMapping("/add-new-vehicle")
    public Vehicle addNewVehicle(@RequestBody @Valid Vehicle vehicle) {
        return vehicleService.create(vehicle);
    }

    /**
     * This method deletes vehicle by id.
     *
     * @param id - id of the vehicle
     */
    @DeleteMapping("/delete-vehicle/{id}")
    public void deleteVehicleById(@PathVariable("id") Long id) {
        vehicleService.deleteVehicle(id);
    }

    /**
     * This method fixes vehicle by id.
     *
     * @param id - id of the vehicle
     * @param amountOfMoney - the fix cost.
     * @param byFacture - paid by facture or not
     * @param companyNumber - company number(if by facture = true)
     * @param byCredit - paid by credit or not
     * @param contractReqBody - contract information(if by credit = true)
     *
     * @return CashReceipt - receipt information
     */
    @PatchMapping("/fix-vehicle/{id}/{amount}")
    public CashReceipt fixVehicleById(@PathVariable("id") Long id,
                                      @PathVariable("amount") BigDecimal amountOfMoney,
                                      @RequestParam("byFacture") boolean byFacture,
                                      @RequestParam(value = "companyNumber", required = false) Long companyNumber,
                                      @RequestParam(value = "byCredit", required = false) boolean byCredit,
                                      @RequestBody(required = false) CreditContractReqBody contractReqBody) {
        return vehicleService.fixVehicle(id, amountOfMoney, byFacture, companyNumber, byCredit,contractReqBody);
    }

    /**
     * This returns mechanics by qualification and garage id.
     *
     * @param qualification - type of qualification.
     * @param id            - garage id.
     * @return MechanicView's object.
     */
    @GetMapping("/get-mechanics-by-qualification-and-by-garage-id/{qualification}/{id}")
    public List<MechanicView> getMechanicsByQualificationAndByGarageId(@PathVariable("qualification") KindOfServices qualification,
                                                                       @PathVariable("id") Long id) {
        return mechanicService.findMechanicByQualificationAndGarageId(qualification, id);
    }
}
