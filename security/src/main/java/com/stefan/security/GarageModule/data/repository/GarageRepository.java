package com.stefan.security.GarageModule.data.repository;

import com.stefan.security.GarageModule.data.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GarageRepository extends JpaRepository<Garage, Long>
{
  @Query(value = "SELECT g FROM garage g", nativeQuery = true)
  List<Garage> findAllInformationAboutGarages();
}
