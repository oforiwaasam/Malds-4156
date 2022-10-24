package com.malds.groceriesProject.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.malds.groceriesProject.services.ClientService;
import com.malds.groceriesProject.models.Client;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients")
    public Client saveClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @GetMapping("/clients/{id}")
    public Client getClientByID(@PathVariable("id") Integer clientID) {
        return clientService.getClientByID(clientID);
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClientByID(@PathVariable("id") Integer clientID) {
        clientService.deleteClientByID(clientID);
    }

    @PutMapping("/clients/{id}")
    public Client updateClient(@PathVariable("id") Integer clientID, @RequestBody Client client) {
        return clientService.updateClient(clientID, client);
    }
    
}
