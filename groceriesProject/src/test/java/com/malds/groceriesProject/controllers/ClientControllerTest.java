package com.malds.groceriesProject.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.nio.charset.Charset;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.malds.groceriesProject.models.Client;
import com.malds.groceriesProject.services.ClientService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @MockBean
    ClientService clientService;

    @Autowired
    MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    public void testGetClientByID() throws Exception {
        final String EXPECTED_RESPONSE = "[{\"clientID\":\"1\",\"email\":\"sd2818@columbia.edu\",\"firstName\":\"Sarah\","
        + "\"lastName\":\"Delgado\",\"gender\":\"Female\",\"dateOfBirth\":\"01/08/2002\",\"zipcode\":\"11101\",\"category\":\"Grocery\"}]";
        // create new client to be saved
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");
        client.setCategory("Grocery");

        when(clientService.getClientByID("1")).thenReturn(List.of(client));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/clients/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(clientService).getClientByID("1");
        assertEquals(EXPECTED_RESPONSE, content);

    }

    @Test
    public void testGetClientByIDNotFound() throws Exception {

        final String EXPECTED_RESPONSE = "ERROR: check clientID value (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)";
        // create new client to be saved
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");
        client.setCategory("Grocery");

        when(clientService.getClientByID("1")).thenThrow(new ResourceNotFoundException("Client ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/clients/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(clientService).getClientByID("1");
        assertEquals(EXPECTED_RESPONSE, content);
    }

    @Test
    public void testSaveClient() throws Exception{
        final String EXPECTED_RESPONSE = "[{\"clientID\":\"1\",\"email\":\"sd2818@columbia.edu\",\"firstName\":\"Sarah\","
        + "\"lastName\":\"Delgado\",\"gender\":\"Female\",\"dateOfBirth\":\"01/08/2002\",\"zipcode\":\"11101\",\"category\":\"Grocery\"}]";
        
        // create new client to be saved
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");
        client.setCategory("Grocery");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(client);

        when(clientService.saveClient(client)).thenReturn(List.of(client));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/clients").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        Mockito.verify(clientService).saveClient(client);
        assertEquals(EXPECTED_RESPONSE, content);
    }
 
    @Test
    public void testSaveClientDuplicate() throws Exception{
        final String EXPECTED_RESPONSE =  "ERROR: check input values;"
        + " client ID must not already exist in DB";

        // create new client to be saved
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");
        client.setCategory("Grocery");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(client);

        when(clientService.saveClient(client)).thenThrow(new ResourceNotFoundException("client ID already exists"
            + " - must use unique clientID"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/clients").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(clientService).saveClient(client);
        assertEquals(EXPECTED_RESPONSE, content);
    }
 
    /* 
    @Test
    public void testUpdateClient() throws Exception{
        final String EXPECTED_RESPONSE = "[{\"clientID\":\"1\",\"email\":\"sarah.delgado@columbia.edu\",\"firstName\":\"Sarah\","
        + "\"lastName\":\"Delgado\",\"gender\":\"Female\",\"dateOfBirth\":\"08/01/2002\",\"zipcode\":\"10260\",\"category\":\"Grocery\"}]";
        
        // initialize updated Client
        Client updatedClient = new Client();
        updatedClient.setClientID("1");
        updatedClient.setEmail("sarah.delgado@columbia.edu");
        updatedClient.setFirstName("Sarah");
        updatedClient.setLastName("Delgado");
        updatedClient.setGender("Female");
        updatedClient.setDateOfBirth("08/01/2002");
        updatedClient.setZipcode("10260");
        updatedClient.setCategory("Grocery");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(updatedClient);

        when(clientService.updateClient(updatedClient)).thenReturn(List.of(updatedClient));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/clients/1").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(clientService).updateClient(updatedClient);
        assertEquals(EXPECTED_RESPONSE, content);
    }


    @Test
    public void testUpdateClientIDNotFound() throws Exception{

        final String EXPECTED_RESPONSE = "ERROR: check input values;"
        + " be sure to include clientID";
        // initialize updated Client
        Client updatedClient = new Client();
        updatedClient.setClientID("1");
        updatedClient.setEmail("sarah.delgado@columbia.edu");
        updatedClient.setFirstName("Sarah");
        updatedClient.setLastName("Delgado");
        updatedClient.setGender("Female");
        updatedClient.setDateOfBirth("08/01/2002");
        updatedClient.setZipcode("10260");
        updatedClient.setCategory("Grocery");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(updatedClient);

        when(clientService.updateClient(updatedClient)).thenThrow(new ResourceNotFoundException("Client ID not found"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/clients/1").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        Mockito.verify(clientService).updateClient(updatedClient);
        assertEquals(EXPECTED_RESPONSE, content);
    }
        */

    @Test
    public void testDeleteClientByID() throws Exception {

        // create new client to be deleted
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");
        client.setCategory("Grocery");

        doNothing().when(clientService).deleteClientByID("1");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/clients/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(clientService).deleteClientByID("1");
        assertEquals("", content);
    }

    @Test
    public void testDeleteClientByIDNotFOund() throws Exception {

        final String EXPECTED_RESPONSE = "ERROR: check clientID value (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)";
        // create new client to be deleted
        Client client = new Client();
        client.setClientID("1");
        client.setEmail("sd2818@columbia.edu");
        client.setFirstName("Sarah");
        client.setLastName("Delgado");
        client.setGender("Female");
        client.setDateOfBirth("01/08/2002");
        client.setZipcode("11101");
        client.setCategory("Grocery");

        doThrow(new ResourceNotFoundException("Client ID not found")).when(clientService).deleteClientByID("1");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/clients/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(clientService).deleteClientByID("1");
        assertEquals(EXPECTED_RESPONSE, content);
    }
}