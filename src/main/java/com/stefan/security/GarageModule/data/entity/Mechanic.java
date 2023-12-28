package com.stefan.security.GarageModule.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stefan.security.GarageModule.customAnnotations.RankValidation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

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
@AllArgsConstructor
public class Mechanic extends Human
{
  @Enumerated(EnumType.STRING)
  private KindOfServices qualification;
  @RankValidation
  private String     typeOfMechanic;
  private String     specializedCarBrand;
  @Min(1000)
  @Digits(integer = 13, fraction = 2)
  private BigDecimal salary;
  private boolean    isFree;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "garage_id")
  @JsonIgnoreProperties({"hiredMechanics","activeClients"})
  private Garage garage;

  @OneToMany(mappedBy = "mechanic")
  @JsonIgnoreProperties("mechanic")
  private List<Vehicle> repairVehicles = new ArrayList<>();

  public void setQualification(KindOfServices qualification)
  {
    this.qualification = qualification;
  }

  public void setTypeOfMechanic(String typeOfMechanic)
  {
    this.typeOfMechanic = typeOfMechanic.toUpperCase();
  }

}
