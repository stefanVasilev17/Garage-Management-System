package com.stefan.security.GarageModule.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stefan.security.GarageModule.customAnnotations.KindOfServiceValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "garage")
@Validated
public class Garage extends BaseEntity
{

  @Size(min = 2, max = 45, message = "Min 2, Max 45 characters")
  private String         name;

  @KindOfServiceValidation
  private String kindOfServices;

  @Min(1)
  private BigDecimal dayBudget;

  @Digits(integer = 15, fraction = 2)
  private BigDecimal turnOver;

  @OneToMany(mappedBy = "garage")
  @JsonIgnoreProperties({"garage", "repairVehicles"})
  private List<Mechanic> hiredMechanics = new ArrayList<>();

  @OneToMany(mappedBy = "garage")
  @JsonIgnoreProperties({"garage", "ownedVehicles"})
  private List<Client> activeClients = new ArrayList<>();

}
