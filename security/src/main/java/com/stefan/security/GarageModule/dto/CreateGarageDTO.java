package com.stefan.security.GarageModule.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreateGarageDTO
{
//  @NotBlank
//  @Size(min = 2, max = 45, message = "Min 2, Max 45 characters")
  private String name;

//  @NotNull
//  @Max(value = 80, message = "Max 80")
  private Integer        maxNumberOfCars;

}
