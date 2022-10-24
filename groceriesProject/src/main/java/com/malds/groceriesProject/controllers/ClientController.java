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
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.Client;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients")
    public Client saveClient(@RequestBody Client client)throws Exception{
        try {
            if (client.getClientID() != null) {
                throw new Exception("Do not provide ClientID");
            }
            clientService.checkValidInput(client);
            return clientService.saveClient(client);
        } catch (Exception e){
            throw new Exception("ERROR: check input values");
        } 
    }

    @GetMapping("/clients/{id}")
    public Client getClientByID(@PathVariable("id") Integer clientID) throws ResourceNotFoundException{
        try {
            return clientService.getClientByID(clientID);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ERROR: check clientID value");
        }
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClientByID(@PathVariable("id") Integer clientID) throws ResourceNotFoundException{
        try {
            clientService.deleteClientByID(clientID);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ERROR: check clientID value");
        }
        
    }

    @PutMapping("/clients/{id}")
    public Client updateClient(@RequestBody Client client) throws Exception {
        try {
            if (client.getClientID() == null) {
                throw new Exception("Must provide ClientID");
            }
            clientService.checkValidInput(client);
            return clientService.updateClient(client);
        } catch (Exception e){
            throw new Exception("ERROR: check input values; be sure to include clientID");
        } 
    }
}
