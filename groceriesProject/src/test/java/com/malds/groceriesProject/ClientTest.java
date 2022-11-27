package com.malds.groceriesProject;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.malds.groceriesProject.repositories.ClientRepository;
import com.malds.groceriesProject.services.ClientService;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.Client;;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientTest {
    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepo;

    @Test
    public void testGetClientByID() throws Exception {

        final String EXPECTED_CLIENT_ID = "1";
        final String EXPECTED_EMAIL = "sd2818@columbia.edu";
        final String EXPECTED_FIRST_NAME = "Sarah";
        final String EXPECTED_LAST_NAME = "Delgado";
        final String EXPECTED_GENDER = "Female";
        final String EXPECTED_DOB = "01/08/2002";
        final String EXPECTED_ZIPCODE = "11101";

        // create new client to be saved
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Mockito.when(clientRepo.existsByID("1")).thenReturn(true);
        Mockito.when(clientRepo.getClientByID("1")).thenReturn(List.of(client));

        assertEquals(clientService.getClientByID("1").get(0).getClientID(), EXPECTED_CLIENT_ID);
        assertEquals(clientService.getClientByID("1").get(0).getEmail(), EXPECTED_EMAIL);
        assertEquals(clientService.getClientByID("1").get(0).getFirstName(), EXPECTED_FIRST_NAME);
        assertEquals(clientService.getClientByID("1").get(0).getLastName(), EXPECTED_LAST_NAME);
        assertEquals(clientService.getClientByID("1").get(0).getGender(), EXPECTED_GENDER);
        assertEquals(clientService.getClientByID("1").get(0).getDateOfBirth(), EXPECTED_DOB);
        assertEquals(clientService.getClientByID("1").get(0).getZipcode(), EXPECTED_ZIPCODE);
    }

    @Test
    public void testGetClientByIDNotFound() throws Exception {

        final String EXPECTED_EXCEPTION = "Client ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)";

        Mockito.when(clientRepo.existsByID("1")).thenReturn(false);

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.getClientByID("1");
        });
        assertEquals(EXPECTED_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testSaveClient() throws Exception {

        final String EXPECTED_CLIENT_ID = "1";
        final String EXPECTED_EMAIL = "sd2818@columbia.edu";
        final String EXPECTED_FIRST_NAME = "Sarah";
        final String EXPECTED_LAST_NAME = "Delgado";
        final String EXPECTED_GENDER = "Female";
        final String EXPECTED_DOB = "01/08/2002";
        final String EXPECTED_ZIPCODE = "11101";

        // create new client to be saved
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Mockito.when(clientRepo.existsByID("1")).thenReturn(false);
        Mockito.when(clientRepo.saveClient(client)).thenReturn(List.of(client));

        assertEquals(clientService.saveClient(client).get(0).getClientID(), EXPECTED_CLIENT_ID);
        assertEquals(clientService.saveClient(client).get(0).getEmail(), EXPECTED_EMAIL);
        assertEquals(clientService.saveClient(client).get(0).getFirstName(), EXPECTED_FIRST_NAME);
        assertEquals(clientService.saveClient(client).get(0).getLastName(), EXPECTED_LAST_NAME);
        assertEquals(clientService.saveClient(client).get(0).getGender(), EXPECTED_GENDER);
        assertEquals(clientService.saveClient(client).get(0).getDateOfBirth(), EXPECTED_DOB);
        assertEquals(clientService.saveClient(client).get(0).getZipcode(), EXPECTED_ZIPCODE);
    }

    @Test
    public void testSaveClientIDExists() throws Exception {

        final String EXPECTED_EXCEPTION = "client ID already exists - must use unique clientID";

        // create new client to be saved
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Mockito.when(clientRepo.existsByID("1")).thenReturn(true);
        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.saveClient(client);
        });
        assertEquals(EXPECTED_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testUpdateClient() {

        final String EXPECTED_CLIENT_ID = "1";
        final String EXPECTED_EMAIL = "sarah.delgado@columbia.edu";
        final String EXPECTED_FIRST_NAME = "Sarah";
        final String EXPECTED_LAST_NAME = "Delgado";
        final String EXPECTED_GENDER = "Female";
        final String EXPECTED_DOB = "08/01/2002";
        final String EXPECTED_ZIPCODE = "10260";

        // initialize updated Client
        Client updatedClient = new Client();
        updatedClient.setClientID("1");
        updatedClient.setEmail("sarah.delgado@columbia.edu");
        updatedClient.setFirstName("Sarah");
        updatedClient.setLastName("Delgado");
        updatedClient.setGender("Female");
        updatedClient.setDateOfBirth("08/01/2002");
        updatedClient.setZipcode("10260");

        Mockito.when(clientRepo.existsByID("1")).thenReturn(true);
        Mockito.when(clientRepo.updateClient(updatedClient)).thenReturn(List.of(updatedClient));

        assertEquals(clientService.updateClient(updatedClient).get(0).getClientID(),
                EXPECTED_CLIENT_ID);
        assertEquals(clientService.updateClient(updatedClient).get(0).getEmail(), EXPECTED_EMAIL);
        assertEquals(clientService.updateClient(updatedClient).get(0).getFirstName(),
                EXPECTED_FIRST_NAME);
        assertEquals(clientService.updateClient(updatedClient).get(0).getLastName(),
                EXPECTED_LAST_NAME);
        assertEquals(clientService.updateClient(updatedClient).get(0).getGender(), EXPECTED_GENDER);
        assertEquals(clientService.updateClient(updatedClient).get(0).getDateOfBirth(),
                EXPECTED_DOB);
        assertEquals(clientService.updateClient(updatedClient).get(0).getZipcode(),
                EXPECTED_ZIPCODE);
    }

    @Test
    public void testNoClientIDUpdate() {
        // Initialize client update
        Client updatedClient = new Client();
        updatedClient.setClientID("32");
        updatedClient.setEmail("josh.snow@columbia.edu");
        updatedClient.setFirstName("Josh");
        updatedClient.setLastName("Snow");
        updatedClient.setGender("Male");
        updatedClient.setDateOfBirth("03/08/2000");
        updatedClient.setZipcode("11023");

        Mockito.when(clientRepo.existsByID("32")).thenReturn(false);
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> {
            clientService.updateClient(updatedClient);
        });
        // clientService.updateClient(updatedClient);
        assertEquals(
                "Client ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)",
                exception.getMessage());
    }

    @Test
    public void testDeleteClientByID() {
        // initialize Client to be deleted
        Client deleteClient = new Client();
        deleteClient.setClientID("3");
        deleteClient.setEmail("sarah.delgado@columbia.edu");
        deleteClient.setFirstName("Sarah");
        deleteClient.setLastName("Delgado");
        deleteClient.setGender("Female");
        deleteClient.setDateOfBirth("01/08/2002");
        deleteClient.setZipcode("10260");

        Mockito.when(clientRepo.existsByID("3")).thenReturn(true);
        clientService.deleteClientByID("3");
    }

    @Test
    public void testNoClientIDDelete() {

        Mockito.when(clientRepo.existsByID("32")).thenReturn(false);
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> {
            clientService.deleteClientByID("32");
        });
        assertEquals(
                "Client ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)",
                exception.getMessage());
        // clientService.deleteClientByID("32");
    }

    @Test
    public void testFindAllClients() {
        // create new clients to be saved
        Client client1 = new Client();
        client1.setClientID("1");
        client1.setEmail("sd2818@columbia.edu");
        client1.setFirstName("Sarah");
        client1.setLastName("Delgado");
        client1.setGender("Female");
        client1.setDateOfBirth("01/08/2002");
        client1.setZipcode("11101");

        Client client2 = new Client();
        client2.setClientID("2");
        client2.setEmail("samuel18@gmail.com");
        client2.setFirstName("Samuel");
        client2.setLastName("Smith");
        client2.setGender("Male");
        client2.setDateOfBirth("01/28/1992");
        client2.setZipcode("11101");

        Client client3 = new Client();
        client3.setClientID("3");
        client3.setEmail("blake_s@cu.edu");
        client3.setFirstName("Blake");
        client3.setLastName("Smith");
        client3.setGender("Female");
        client3.setDateOfBirth("02/28/2000");
        client3.setZipcode("11101");

        Mockito.when(clientRepo.findAll()).thenReturn(List.of(client1, client2, client3));
        assertEquals(clientService.findAll(), List.of(client1, client2, client3));
    }

    @Test
    public void testEmailValidity() {
        String invalid_email = "incorrect email input";
        String blank_email = "      ";
        String valid_email = "ss6168@columbia.edu";

        assertFalse(clientService.isValidEmail(invalid_email));
        assertFalse(clientService.isValidEmail(blank_email));
        assertTrue(clientService.isValidEmail(valid_email));
    }

    @Test
    public void testCheckInputInvalidEmail() {
        // initialize invalid email client - no @
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Email is invalid", exception.getMessage());
    }

    @Test
    public void testNullEmail() {
        // initialize invalid email - null
        Client client = new Client();
        client.setClientID("1");
        client.setEmail(null);
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void testBlankEmail() {
        // initialize invalid email client - "" - blank
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Email must not be blank or longer than 320 chars", exception.getMessage());
    }

    @Test
    public void testNullClientID() {
        // initialize invalid clientID - null
        Client client = new Client();
        client.setClientID(null);
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void testCheckInputInvalidName() {
        // initialize invalid name - too long
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName(
                "SarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("First Name must not be blank or longer than 128 chars",
                exception.getMessage());
        // clientService.checkValidInput(client);
    }

    @Test
    public void testNullFirstName() {
        // initialize invalid name - null
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName(null);
        client.setLastName("   ");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void testBlankFirstName() {
        // initialize invalid name - blank
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("");
        client.setLastName("   ");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("First Name must not be blank or longer than 128 chars", exception.getMessage());
    }

    @Test
    public void testCheckInputInvalidLastName() {
        // initialize invalid name - blank space
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("   ");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Last Name must not be blank or longer than 128 chars",
                exception.getMessage());
    }

    @Test
    public void testNullLastName() {
        // initialize invalid name - null
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName(null);
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void testCheckInputInvalidGender() {
        // initialize invalid gender - null
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender(null);
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void testCheckInputOver20CharsGender() {
        // initialize invalid gender - over 20 chars
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("manymanymanymanymanymany chars");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Gender must not be blank or longer than 20 chars", exception.getMessage());
    }

    @Test
    public void testCheckBlankGender() {
        // initialize invalid gender - blank
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Gender must not be blank or longer than 20 chars", exception.getMessage());
    }

    @Test
    public void testCheckInputInvalidDoBYear() {
        // initialize invalid dob - past today's date
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("10/25/2024");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Date of Birth cannot be greater than or equal to today's date",
                exception.getMessage());
        // clientService.checkValidInput(client);
    }

    @Test
    public void testCheckInputInvalidDoBYear2() {
        // initialize invalid dob - year 0000
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("10/10/0000");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Invalid date of birth format: 'MM/dd/yyyy'", exception.getMessage());
        // clientService.checkValidInput(client);
    }

    @Test
    public void testCheckInputInvalidDoBFormat() {
        // initialize invalid dob - incorrect format
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("10-25-2010");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Invalid date of birth format: 'MM/dd/yyyy'", exception.getMessage());
        // clientService.checkValidInput(client);
    }

    @Test
    public void testCheckInvalidDoBMonth() {
        // initialize invalid dob - month 13
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("13/25/2010");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Invalid date of birth format: 'MM/dd/yyyy'", exception.getMessage());
    }

    @Test
    public void testCheckInputInvalidDoBDay() {
        // initialize invalid dob - day 33
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("10/33/2010");
        client.setZipcode("11101");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Invalid date of birth format: 'MM/dd/yyyy'", exception.getMessage());
    }

    @Test
    public void testCheckInputInvalidZipcodeOver10Chars() {
        // initialize invalid zipcode - over 10 chars
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("female");
        client.setDateOfBirth("12/30/2002");
        client.setZipcode("110011100111001");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Zipcode must not be blank or longer than 10 chars", exception.getMessage());
    }

    @Test
    public void testNullZipcode() {
        // initialize invalid zipcode - null
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("female");
        client.setDateOfBirth("12/30/2002");
        client.setZipcode(null);

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void testBlankZipcode() {
        // initialize invalid zipcode - blank
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("female");
        client.setDateOfBirth("12/30/2002");
        client.setZipcode("     ");

        Throwable exception = assertThrows(Exception.class, () -> {
            clientService.checkValidInput(client);
        });
        assertEquals("Zipcode must not be blank or longer than 10 chars", exception.getMessage());
    }
}
