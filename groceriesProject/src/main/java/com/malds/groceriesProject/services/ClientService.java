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
    /**
     * ClientService. Carries out the operations
     * and interacts with the persistence layer, client
     * Repository, to create, read, update, and delete from the database.
     */

    @Autowired
    private ClientRepository clientRepo;
    /**
     * max length for email.
     */
    public static final int EMAIL_UPPER_LENGTH = 320;
    /**
     * max length for name.
     */
    public static final int NAME_UPPER_LENGTH = 128;
    /**
     * max length for gender.
     */
    public static final int GENDER_UPPER_LENGTH = 20;
    /**
     * max length for zipcode.
     */
    public static final int ZIPCODE_UPPER_LENGTH = 10;

    /**
     * ClientService Constructor.
     * @param clientRepository
     */
    public ClientService(final ClientRepository clientRepository) {
        this.clientRepo = clientRepository;
    }

    /**
     * Saves Client into Client table and returns saved client in a list.
     * Throws Exception if clientID already exists.
     * @param client
     * @return List containing the saved client.
     * @throws Exception
     */
    public List<Client> saveClient(final Client client) throws Exception {
        if (clientRepo.existsByID(client.getClientID())) {
            throw new Exception("client ID already exists"
            + " - must use unique clientID");
        }
        return clientRepo.saveClient(client);
    }

    /**
     * Given clientID, searches for client
     * with clientID and return client. Throws ResourceNotFoundException if
     *  client with clientID does not exist.
     * @param clientID
     * @return List containing Client with specified clientID
     * @throws ResourceNotFoundException
     */
    public List<Client> getClientByID(final String clientID)
    throws ResourceNotFoundException {
        if (clientRepo.existsByID(clientID)) {
            return clientRepo.getClientByID(clientID);
        } else {
            throw new ResourceNotFoundException("Client ID not found");
        }
    }

    /**
     * Deletes client by clientID provided.
     * Throws ResourceNotFoundException if client with clientID does not exist.
     * @param clientID
     * @throws ResourceNotFoundException
     */
    public void deleteClientByID(final String clientID)
    throws ResourceNotFoundException {
        if (clientRepo.existsByID(clientID)) {
            clientRepo.deleteClientByID(clientID);
        } else {
            throw new ResourceNotFoundException("Client ID not found");
        }
    }

    /**
     * Updates existing client info and returns updated client in a list.
     * Throws ResourceNotFoundException if client does not exist.
     * @param client
     * @return List containing the updated Client
     * @throws ResourceNotFoundException
     */
    public List<Client> updateClient(final Client client)
    throws ResourceNotFoundException {
        if (clientRepo.existsByID(client.getClientID())) {
            return clientRepo.updateClient(client);
        } else {
            throw new ResourceNotFoundException("Client ID not found");
        }
    }

    /**
     * Finds and returns all existing clients in dynamoDB Client table.
     * @return List of all clients that exists
     */
    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    /**
     * Checks whether email is of the valid format
     * and returns true if valid, otherwise false.
     * @param email
     * @return True if email is valid, False otherwise
     */
    public boolean isValidEmail(final String email) {
        return new EmailValidator().isValid(email, null);
    }

    /**
     * Checks whether inputted values by users are valid,
     * is not blank, and is of accepted lengths.
     * Throws Exception if inputs are invalid.
     * @param client
     * @throws Exception
     */
    public void checkValidInput(final Client client) throws Exception {
        if (client.getClientID() == null || client.getEmail() == null
                || client.getFirstName() == null || client.getLastName() == null
                || client.getGender() == null || client.getZipcode() == null) {
            throw new Exception("Value cannot be null");
        }
        if (!isValidEmail(client.getEmail())) {
            throw new Exception("Email is invalid");
        } else if (client.getEmail().isBlank()
        || client.getEmail().length() > EMAIL_UPPER_LENGTH) {
            throw new Exception("Email must not be blank"
            + " or longer than 320 chars");
        } else if (client.getFirstName().isBlank()
                || client.getFirstName().length() > NAME_UPPER_LENGTH) {
            throw new Exception("First Name must not be blank"
            + " or longer than 128 chars");
        } else if (client.getLastName().isBlank()
                || client.getLastName().length() > NAME_UPPER_LENGTH) {
            throw new Exception("Last Name must not be blank"
            + " or longer than 128 chars");
        } else if (client.getGender().isBlank()
                || client.getGender().length() > GENDER_UPPER_LENGTH) {
            throw new Exception("Gender must not be blank"
            + " or longer than 20 chars");
        } else if (client.getZipcode().isBlank()
                || client.getZipcode().length() > ZIPCODE_UPPER_LENGTH) {
            throw new Exception("Zipcode must not be blank"
            + " or longer than 10 chars");
        } else {
            Date today = new Date();
            try {
                Date dob = new SimpleDateFormat("MM/dd/yyyy")
                .parse(client.getDateOfBirth());
                if (dob.compareTo(today) >= 0) {
                    throw new Exception(
                            "Date of Birth cannot be greater than"
                            + " or equal to today's date");
                }
                if (!GenericValidator.isDate(client.getDateOfBirth(),
                "MM/dd/yyyy", false)) {
                    throw new Exception("Invalid date of birth format:"
                    + " 'MM/dd/yyyy'");
                }
            } catch (ParseException e) {
                throw new Exception("Invalid date of birth format:"
                + " 'MM/dd/yyyy'");
            }
        }
    }
}
