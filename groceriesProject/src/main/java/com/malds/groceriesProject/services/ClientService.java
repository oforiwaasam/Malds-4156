package com.malds.groceriesProject.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * max length for category.
     */
    public static final int CATEGORY_UPPER_LENGTH = 30;

    /**
     * multiply by 100 - stats.
     */
    public static final int MULTIPLY_HUNDRED = 100;

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
                || client.getGender() == null || client.getZipcode() == null
                || client.getCategory() == null) {
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
        } else if (client.getCategory().isBlank()
                || client.getCategory().length() > CATEGORY_UPPER_LENGTH) {
            throw new Exception("Category must not be blank"
            + "or longer than 30 chars");
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

    /**
     * Checks whether inputted values by users are valid,
     * is not blank, and is of accepted lengths.
     * Throws Exception if inputs are invalid.
     * @param client
     * @throws Exception
     */
    public void checkUpdatedInput(final Client client) throws Exception {
        if (client.getClientID() == null
                || !clientRepo.existsByID(client.getClientID())) {
            throw new Exception("Client ID cannot be null "
            + "and must exist in DB");
        }
        String existingClientCategory = clientRepo.getClientByID(
                client.getClientID()).get(0).getCategory();

        if (client.getEmail() != null && !isValidEmail(client.getEmail())) {
            throw new Exception("Email is invalid");
        } else if (client.getFirstName() != null
                && client.getFirstName().length() > NAME_UPPER_LENGTH) {
            throw new Exception("First Name must not be longer than 128 chars");
        } else if (client.getLastName() != null && client.getLastName().length()
            > NAME_UPPER_LENGTH) {
            throw new Exception("Last Name must not be longer than 128 chars");
        } else if (client.getGender() != null && client.getGender().length()
            > GENDER_UPPER_LENGTH) {
            throw new Exception("Gender must not be longer than 20 chars");
        } else if (client.getZipcode() != null && client.getZipcode().length()
            > ZIPCODE_UPPER_LENGTH) {
            throw new Exception("Zipcode must not be longer than 10 chars");
        } else if (client.getCategory() != null
                && !client.getCategory().equals(existingClientCategory)) {
            throw new Exception("Category must not be different "
            + "to existing category: " + existingClientCategory
            + ", " + client.getCategory());
        } else if (client.getDateOfBirth() != null) {
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

    /**
     * fill in missing info for client to be updated
     * with existing info for client.
     * @param client
     * @return client with missing info filled in
     */
    public Client fillClient(final Client client) {
        Client existingClient = clientRepo.getClientByID(client.getClientID())
            .get(0);

        if (client.getEmail() == null) {
            client.setEmail(existingClient.getEmail());
        }
        if (client.getFirstName() == null) {
            client.setFirstName(existingClient.getFirstName());
        }
        if (client.getLastName() == null) {
            client.setLastName(existingClient.getLastName());
        }
        if (client.getGender() == null) {
            client.setGender(existingClient.getGender());
        }
        if (client.getZipcode() == null) {
            client.setZipcode(existingClient.getZipcode());
        }
        if (client.getCategory() == null) {
            client.setCategory(existingClient.getCategory());
        }
        if (client.getDateOfBirth() == null) {
            client.setDateOfBirth(existingClient.getDateOfBirth());
        }
        return client;
    }

    /**
     * check if category exists.
     * @param category
     * @throws Exception
     */
    public void checkCategory(final String category) throws Exception {
        if (!clientRepo.existsByCategory(category)) {
            throw new Exception("The requested category does not yet exist");
        }
    }

    /**
     * get statistics of clients based on category.
     * stats:
     * gender ratio
     * zipcode
     * age
     * @param category
     * @return String of statistics
     * @throws Exception
     */
    public Map<String, Map<String, Integer>> getStats(final String category)
            throws Exception {
        Map<String, Map<String, Integer>> stats = new HashMap<>();
        List<Client> clientCategory = clientRepo.getClientsByCategory(category);
        Map<String, Integer> gender = new HashMap<>();
        Map<String, Integer> zipcode = new HashMap<>();
        Map<String, Integer> age = new HashMap<>();

        for (Client client : clientCategory) {
            Integer currVal;
            String clientGender = client.getGender();
            String clientZipcode = client.getZipcode();
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("MM/dd/yyyy");
                //convert String to LocalDate
                LocalDate dob = LocalDate.parse(client.getDateOfBirth(),
                    formatter);
                LocalDate today = LocalDate.now();

            String clientAge = String.valueOf(Period.between(dob,
                today).getYears());

            if (!gender.containsKey(clientGender)) {
                gender.put(clientGender, 1);
            } else {
                currVal = gender.get(clientGender);
                gender.put(clientGender, currVal + 1);
            }

            if (!age.containsKey(clientAge)) {
                age.put(clientAge, 1);
            } else {
                currVal = age.get(clientAge);
                age.put(clientAge, currVal + 1);
            }

            if (!zipcode.containsKey(clientZipcode)) {
                zipcode.put(clientZipcode, 1);
            } else {
                currVal = zipcode.get(clientZipcode);
                zipcode.put(clientZipcode, currVal + 1);
            }
        }

        //add to stats
        HashMap<String, Integer> genderStats = new HashMap<>();
        gender.forEach((genderName, total)
            -> genderStats.put(genderName, (total * MULTIPLY_HUNDRED
                / clientCategory.size()))
            );
        stats.put("Gender", genderStats);

        HashMap<String, Integer> ageStats = new HashMap<>();
        age.forEach((ageName, total)
            -> ageStats.put(ageName, (total * MULTIPLY_HUNDRED
                / clientCategory.size()))
            );
        stats.put("Age", ageStats);

        HashMap<String, Integer> zipcodeStats = new HashMap<>();
        zipcode.forEach((zipcodeName, total)
            -> zipcodeStats.put(zipcodeName, (total * MULTIPLY_HUNDRED
                / clientCategory.size()))
            );
        stats.put("Zipcode", zipcodeStats);

        return stats;
    }
}
