package com.malds.groceriesProject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malds.groceriesProject.models.Client;
import com.malds.groceriesProject.repositories.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepo;
    public ClientService(ClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    public Client saveClient(Client client) {
        return clientRepo.saveClient(client);
    }

    public Client getClientByID(Integer clientID) {
        return clientRepo.getClientByID(clientID);
    }

    public void deleteClientByID(Integer clientID) {
        clientRepo.deleteClientByID(clientID);
    }

    public Client updateClient(Integer clientID, Client client) {
        return clientRepo.updateClient(clientID, client);
    }

    public List<Client> findAll() {
            return clientRepo.findAll();
    }

}
