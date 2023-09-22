package com.stefan.security.GarageModule.web.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stefan.security.GarageModule.data.entity.Human;
import com.stefan.security.GarageModule.data.entity.Vehicle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ClientView extends Human
{
  private String telephoneNumber;
  private String budget;

  @JsonIgnoreProperties({"client", "mechanic"})
  private List<Vehicle> ownedVehicles;
}
