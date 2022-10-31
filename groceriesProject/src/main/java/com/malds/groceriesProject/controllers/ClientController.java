package com.malds.groceriesProject.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.malds.groceriesProject.services.ClientService;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.Client;

@RestController
public class ClientController extends BaseController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients")
    public List<Client> saveClient(@RequestBody Client client)throws Exception{
        try {
            clientService.checkValidInput(client);
            return clientService.saveClient(client);
        } catch (Exception e){
            throw new Exception("ERROR: check input values; client ID must not already exist in DB");
        } 
    }

    @GetMapping("/clients/{id}")
    public List<Client> getClientByID(@PathVariable("id") String clientID) throws ResourceNotFoundException{
        try {
            return clientService.getClientByID(clientID);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ERROR: check clientID value");
        }
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClientByID(@PathVariable("id") String clientID) throws ResourceNotFoundException{
        try {
            clientService.deleteClientByID(clientID);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ERROR: check clientID value");
        }
    }

    @PutMapping("/clients/{id}")
    public List<Client> updateClient(@RequestBody Client client) throws Exception {
        try {
            clientService.checkValidInput(client);
            return clientService.updateClient(client);
        } catch (Exception e){
            throw new Exception("ERROR: check input values; be sure to include clientID");
        } 
    }
}
