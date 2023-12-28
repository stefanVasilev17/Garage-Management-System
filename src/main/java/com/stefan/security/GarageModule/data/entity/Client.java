package com.stefan.security.GarageModule.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "clients")
@AllArgsConstructor
public class Client extends Human
{
  private String     telephoneNumber;
  @Min(20)
  private BigDecimal budget;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "garage_id")
  @JsonIgnoreProperties("activeClients")
  private Garage garage;

  @OneToMany(mappedBy = "client")
  @JsonIgnoreProperties("client")
  private List<Vehicle> ownedVehicles = new ArrayList<>();

}
