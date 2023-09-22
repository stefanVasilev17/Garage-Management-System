package com.stefan.security.GarageModule.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stefan.security.GarageModule.customAnnotations.KindOfServiceValidation;
import com.stefan.security.GarageModule.customAnnotations.RankValidation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import jakarta.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employee")
@Validated
public class Mechanic extends Human
{
  @KindOfServiceValidation
  private String     qualification;
  @RankValidation
  private String     typeOfMechanic;
  private String     specializedCarBrand;
  @Min(1000)
  @Digits(integer = 13, fraction = 2)
  private BigDecimal salary;
  private boolean    isFree;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "garage_id")
  @JsonIgnoreProperties({"hiredMechanics", "activeClients"})
  private Garage garage;

  @OneToMany(mappedBy = "mechanic")
  @JsonIgnoreProperties("mechanic")
  private List<Vehicle> repairVehicles = new ArrayList<>();


  public void setQualification(String qualification)
  {
    this.qualification = qualification.toUpperCase();
  }

  public void setTypeOfMechanic(String typeOfMechanic)
  {
    this.typeOfMechanic = typeOfMechanic.toUpperCase();
  }
}
