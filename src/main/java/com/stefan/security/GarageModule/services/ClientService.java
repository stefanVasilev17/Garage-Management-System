package com.stefan.security.GarageModule.services;

import com.stefan.security.GarageModule.data.entity.Client;
import com.stefan.security.GarageModule.web.view.ClientView;

import java.util.List;


public interface ClientService
{
  Client create(Client addNewClient);

  Client updateClient(Long id, Client updateClient);

  void deleteClient(Long id);

  List<ClientView> findAllClientsByGarageId(Long id);

}
