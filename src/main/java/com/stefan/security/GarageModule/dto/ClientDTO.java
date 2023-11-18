package com.stefan.security.GarageModule.dto;

import com.stefan.security.GarageModule.data.entity.Garage;
import com.stefan.security.GarageModule.data.entity.Human;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@Validated
@NoArgsConstructor
public class ClientDTO extends Human
{
  private String telephoneNumber;

  @Min(20)
  private BigDecimal budget;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "garage_id")
  private Garage garage;
}
