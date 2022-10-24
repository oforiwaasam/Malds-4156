package com.malds.groceriesProject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
    public void testSaveClient() throws Exception {
        //initialize client
        Client client = new Client();
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        //create newly inserted client
        Client insertedClient = new Client();
        insertedClient.setClientID("1");
        insertedClient.setEmail("sd2818@columbia.edu");
        insertedClient.setFirstName("Sarah");
        insertedClient.setLastName("Delgado");
        insertedClient.setGender("Female");
        insertedClient.setDateOfBirth("01/08/2002");
        insertedClient.setZipcode("11101");

        Mockito.when(clientRepo.saveClient(client)).thenReturn(List.of(insertedClient));
        
        assertEquals(clientService.saveClient(client).get(0).getClientID(), "1");
    }

    @Test
    public void testUpdateClient() {
        
        //initialize updated Client
        Client updatedClient = new Client();
        updatedClient.setClientID("1");
        updatedClient.setEmail("sarah.delgado@columbia.edu");
        updatedClient.setFirstName("Sarah");
        updatedClient.setLastName("Delgado");
        updatedClient.setGender("Female");
        updatedClient.setDateOfBirth("01/08/2002");
        updatedClient.setZipcode("10260");

        Mockito.when(clientRepo.getClientByID("1")).thenReturn(List.of(updatedClient));

        Mockito.when(clientRepo.updateClient(updatedClient)).thenReturn(List.of(updatedClient));

        assertEquals(clientService.updateClient(updatedClient).get(0).getClientID(), updatedClient.getClientID());
        assertEquals(clientService.updateClient(updatedClient).get(0).getEmail(), updatedClient.getEmail());
        assertEquals(clientService.updateClient(updatedClient).get(0).getFirstName(), updatedClient.getFirstName());
        assertEquals(clientService.updateClient(updatedClient).get(0).getLastName(), updatedClient.getLastName());
        assertEquals(clientService.updateClient(updatedClient).get(0).getGender(), updatedClient.getGender());
        assertEquals(clientService.updateClient(updatedClient).get(0).getDateOfBirth(), updatedClient.getDateOfBirth());
        assertEquals(clientService.updateClient(updatedClient).get(0).getZipcode(), updatedClient.getZipcode());
    }

    @Test
    public void testDeleteClientByID() {
        //initialize Client to be deleted
        Client deleteClient = new Client();
        deleteClient.setClientID("3");
        deleteClient.setEmail("sarah.delgado@columbia.edu");
        deleteClient.setFirstName("Sarah");
        deleteClient.setLastName("Delgado");
        deleteClient.setGender("Female");
        deleteClient.setDateOfBirth("01/08/2002");
        deleteClient.setZipcode("10260");

        Mockito.when(clientRepo.getClientByID("3")).thenReturn(List.of(deleteClient));

        clientService.deleteClientByID("3");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testNoClientIDUpdate() throws ResourceNotFoundException {
        //Initialize client update
        Client updatedClient = new Client();
        updatedClient.setClientID("32");
        updatedClient.setEmail("josh.snow@columbia.edu");
        updatedClient.setFirstName("Josh");
        updatedClient.setLastName("Snow");
        updatedClient.setGender("Male");
        updatedClient.setDateOfBirth("03/08/2000");
        updatedClient.setZipcode("11023");

        Mockito.when(clientRepo.getClientByID("32")).thenReturn(null);
        
        clientService.updateClient(updatedClient);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testNoClientIDDelete() throws ResourceNotFoundException {

        Mockito.when(clientRepo.getClientByID("32")).thenReturn(null);
        clientService.deleteClientByID("32");
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

    @Test(expected = Exception.class)
    public void testCheckInputInvalidEmail() throws Exception {
        //initialize invalid email client - no @
        Client client = new Client();
        client.setEmail("sd2818columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");

        clientService.checkValidInput(client);
    }

    @Test(expected = Exception.class)
    public void testCheckInputInvalidName () throws Exception {
        //initialize invalid name - too long
       Client client = new Client();
       client.setEmail("sd2818@columbia.edu");
       client.setFirstName("SarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarahSarah");
       client.setLastName("Delgado");
       client.setGender("Female");
       client.setDateOfBirth("01/08/2002");
       client.setZipcode("11101");

       clientService.checkValidInput(client);
    }

    @Test(expected = Exception.class)
    public void testCheckInputInvalidLastName () throws Exception {
        //initialize invalid name - blank space
       Client client = new Client();
       client.setEmail("sd2818@columbia.edu");
       client.setFirstName("Sarah");
       client.setLastName("   ");
       client.setGender("Female");
       client.setDateOfBirth("01/08/2002");
       client.setZipcode("11101");

       clientService.checkValidInput(client);
    }

    @Test(expected = Exception.class)
    public void testCheckInputInvalidGender () throws Exception {
        //initialize invalid gender -  null
       Client client = new Client();
       client.setEmail("sd2818@columbia.edu");
       client.setFirstName("Sarah");
       client.setLastName("Delgado");
       client.setGender(null);
       client.setDateOfBirth("01/08/2002");
       client.setZipcode("11101");

       clientService.checkValidInput(client);
    }

    @Test(expected = Exception.class)
    public void testCheckInputInvalidDoBYear() throws Exception {
        //initialize invalid dob - past today's date
       Client client = new Client();
       client.setEmail("sd2818@columbia.edu");
       client.setFirstName("Sarah");
       client.setLastName("Delgado");
       client.setGender("Female");
       client.setDateOfBirth("10/25/2024");
       client.setZipcode("11101");

       clientService.checkValidInput(client);
    }

    @Test(expected = Exception.class) 
    public void testCheckInputInvalidDoBYear2() throws Exception {
        //initialize invalid dob - year 0000
       Client client = new Client();
       client.setEmail("sd2818@columbia.edu");
       client.setFirstName("Sarah");
       client.setLastName("Delgado");
       client.setGender("Female");
       client.setDateOfBirth("10/10/0000");
       client.setZipcode("11101");

       clientService.checkValidInput(client);
    }
    
    @Test(expected = Exception.class) 
    public void testCheckInputInvalidDoBFormat() throws Exception {
        //initialize invalid dob - incorrect format
       Client client = new Client();
       client.setEmail("sd2818@columbia.edu");
       client.setFirstName("Sarah");
       client.setLastName("Delgado");
       client.setGender("Female");
       client.setDateOfBirth("10-25-2010");
       client.setZipcode("11101");

       clientService.checkValidInput(client);
    }

    @Test(expected = Exception.class) 
    public void testCheckInvalidDoBMonth() throws Exception {
        //initialize invalid dob - month 13
       Client client = new Client();
       client.setEmail("sd2818@columbia.edu");
       client.setFirstName("Sarah");
       client.setLastName("Delgado");
       client.setGender("Female");
       client.setDateOfBirth("13/25/2010");
       client.setZipcode("11101");

       clientService.checkValidInput(client);
    }

    @Test(expected = Exception.class) 
    public void testCheckInputInvalidDoBDay() throws Exception {
        //initialize invalid dob - day 33
       Client client = new Client();
       client.setEmail("sd2818@columbia.edu");
       client.setFirstName("Sarah");
       client.setLastName("Delgado");
       client.setGender("Female");
       client.setDateOfBirth("10/33/2010");
       client.setZipcode("11101");

       clientService.checkValidInput(client);
    }

    @Test(expected = Exception.class)
    public void testCheckInputInvalidZipcode() throws Exception {
        //initialize invalid dob - day 33
        Client client = new Client();
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("female");
        client.setDateOfBirth("12/33/2002");
        client.setZipcode("11000000000");
 
        clientService.checkValidInput(client);
    }
}
