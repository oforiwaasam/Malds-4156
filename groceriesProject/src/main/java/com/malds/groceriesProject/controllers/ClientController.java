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

@CrossOrigin
@RestController
public class ClientController extends BaseController {

    /**
     * Client Controller. Processes the requests received by interacting with the client service
     * layer.
     */
    @Autowired
    private ClientService clientService;

    /**
     * Saves new client into the Client table in dynamoDB and returns the saved client. Throws
     * Exception if clientID alredy exists or invalid inputs.
     * 
     * @param client
     * @return List containing the saved client
     * @throws Exception
     */
    @PostMapping("/clients")
    public List<Client> saveClient(@RequestBody final Client client) throws Exception {
        try {
            clientService.checkValidInput(client);
            return clientService.saveClient(client);
        } catch (Exception e) {
            throw new Exception(
                    "ERROR: check input values;" + " client ID must not already exist in DB");
        }
    }

    /**
     * given clientID input, searches the Client table in dynamoDB and returns the client with
     * clientID. Throws ResourceNotFoundException if clientID does not exist.
     * 
     * @param clientID
     * @return List containing the client with specified clientID
     * @throws ResourceNotFoundException
     */
    @GetMapping("/clients/{id}")
    public List<Client> getClientByID(@PathVariable("id") final String clientID)
            throws ResourceNotFoundException {
        try {
            return clientService.getClientByID(clientID);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ERROR: check clientID value");
        }
    }

    /**
     * Deletes client in the Client table in dynamoDB Throws ResourceNotFoundException if clientID
     * does not exist.
     * 
     * @param clientID
     * @throws ResourceNotFoundException
     */
    @DeleteMapping("/clients/{id}")
    public void deleteClientByID(@PathVariable("id") final String clientID)
            throws ResourceNotFoundException {
        try {
            clientService.deleteClientByID(clientID);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ERROR: check clientID value");
        }
    }

    /**
     * Updates existing client in the Client table in dynamoDB and returns the updated client in a
     * list.
     * 
     * @param client
     * @return List containing the updated client
     * @throws Exception
     */
    @PutMapping("/clients/{id}")
    public List<Client> updateClient(@RequestBody final Client client) throws Exception {
        try {
            clientService.checkValidInput(client);
            return clientService.updateClient(client);
        } catch (Exception e) {
            throw new Exception("ERROR: check input values;" + " be sure to include clientID");
        }
    }
}
