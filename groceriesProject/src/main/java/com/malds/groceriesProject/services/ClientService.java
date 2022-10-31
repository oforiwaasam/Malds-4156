package com.malds.groceriesProject.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.validator.GenericValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.Client;
import com.malds.groceriesProject.repositories.ClientRepository;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepo;
    public ClientService(ClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    public List<Client> saveClient(Client client) throws Exception {
        if(clientRepo.existsByID(client.getClientID())) {
            throw new Exception("client ID already exists - must use unique clientID");
        }
        return clientRepo.saveClient(client);
    }

    public List<Client> getClientByID(String clientID) throws ResourceNotFoundException {
        if(clientRepo.existsByID(clientID)) {
            return clientRepo.getClientByID(clientID);
        } else {
            throw new ResourceNotFoundException("Client ID not found");
        }   
    }

    public void deleteClientByID(String clientID) throws ResourceNotFoundException{
        if(clientRepo.existsByID(clientID)) {
            clientRepo.deleteClientByID(clientID);
        } else {
            throw new ResourceNotFoundException("Client ID not found");
        }
    }

    public List<Client> updateClient(Client client) throws ResourceNotFoundException{
        if(clientRepo.existsByID(client.getClientID())) {
            return clientRepo.updateClient(client);
        } else {
            throw new ResourceNotFoundException("Client ID not found");
        }
    }

    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    public boolean isValidEmail(String email) {
        return new EmailValidator().isValid(email, null);
    }

    public void checkValidInput(Client client) throws Exception{
        if (client.getClientID() == null || client.getEmail() == null 
            || client.getFirstName() == null || client.getLastName() == null
            || client.getGender() == null || client.getZipcode() == null) {
                throw new Exception("Value cannot be null");
            }
        if (!isValidEmail(client.getEmail())) {
            throw new Exception("Email is invalid");
        } else if (client.getEmail().isBlank() || client.getEmail().length() > 320 ) {
            throw new Exception("Email must not be blank or longer than 320 chars");
        } else if (client.getFirstName().isBlank() || client.getFirstName().length() > 128) {
            throw new Exception("First Name must not be blank or longer than 128 chars");
        } else if (client.getLastName().isBlank() || client.getLastName().length() > 128) {
            throw new Exception("Last Name must not be blank or longer than 128 chars");
        } else if (client.getGender().isBlank() || client.getGender().length() > 20) {
            throw new Exception("Gender must not be blank or longer than 20 chars");
        } else if (client.getZipcode().isBlank() || client.getZipcode().length() > 10) {
            throw new Exception("Zipcode must not be blank or longer than 10 chars");
        } else {
            Date today = new Date();
            try {
                Date dob = new SimpleDateFormat("MM/dd/yyyy").parse(client.getDateOfBirth());
                if (dob.compareTo(today) >= 0) {
                    throw new Exception("Date of Birth cannot be greater than or equal to today's date");
                }
                if (!GenericValidator.isDate(client.getDateOfBirth(), "MM/dd/yyyy", false)) {
                    throw new Exception("Invalid date of birth format: 'MM/dd/yyyy'");
                }
            } catch (ParseException e){
                throw new Exception("Invalid date of birth format: 'MM/dd/yyyy'");
            }
        }
    }
}
