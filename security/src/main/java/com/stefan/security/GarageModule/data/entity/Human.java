package com.stefan.security.GarageModule.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class Human extends BaseEntity
{
  //@NotBlank
  private String name;

  private String surname;

}

