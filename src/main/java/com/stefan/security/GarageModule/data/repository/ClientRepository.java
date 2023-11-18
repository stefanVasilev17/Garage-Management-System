package com.stefan.security.GarageModule.data.repository;

import com.stefan.security.GarageModule.data.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClientRepository extends JpaRepository<Client, Long>
{
//  @Modifying
//  @Query(value = "INSERT INTO CLIENTS(budget,ownerOf,name,surname,garage_id,telephone_number) VALUES(:budget,:ownerOf,:fName,:lName,:garageId,:telNumber)", nativeQuery = true)
//  Client addNewClient(@Param("budget") BigDecimal budget,
//                      @Param("ownerOf") String licensePlate,
//                      @Param("fName") String fName,
//                      @Param("lName") String lName,
//                      @Param("garageId") Long garageID,
//                      @Param("telNumber") String telephoneNumber);


  List<Client> findAllClientsByGarageId(Long id);

  List<Client> findAllByTelephoneNumber(String number);
}
