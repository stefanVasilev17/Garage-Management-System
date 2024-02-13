package com.stefan.security.GarageModule.data.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class Human extends BaseEntity
{
  @NotBlank
  private String name;

  private String surname;

}

