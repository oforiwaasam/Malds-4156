package com.malds.groceriesProject.IntegrationTestsAssessment;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.malds.groceriesProject.models.Client;
import com.malds.groceriesProject.services.ClientService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientIntegTest {

    @Autowired
    private ClientService clientService;

    @Test
    public void createClientInteg() throws Exception  {

        Client deleteClient = new Client();
        deleteClient.setClientID("client_integ_1");
        deleteClient.setEmail("sarah.delgado@columbia.edu");
        deleteClient.setFirstName("Sarah");
        deleteClient.setLastName("Delgado");
        deleteClient.setGender("Female");
        deleteClient.setDateOfBirth("01/08/2002");
        deleteClient.setZipcode("10260");

        clientService.saveClient(deleteClient);
        //Test that the shopping list exists
        Assert.assertEquals(deleteClient.getClientID(), clientService.getClientByID("client_integ_1").get(0).getClientID());

    }

    @Test
    public void deleteClientInteg() throws Exception  {

        Client deleteClient = new Client();
        deleteClient.setClientID("client_integ_1");
        deleteClient.setEmail("sarah.delgado@columbia.edu");
        deleteClient.setFirstName("Sarah");
        deleteClient.setLastName("Delgado");
        deleteClient.setGender("Female");
        deleteClient.setDateOfBirth("01/08/2002");
        deleteClient.setZipcode("10260");

        clientService.deleteClientByID("client_integ_1");
        //Test that the shopping list exists
        
        Assert.assertThrows(Exception.class,
                ()->{clientService.getClientByID("client_integ_1");} );
        
    }
}
