package com.malds.groceriesProject.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.malds.groceriesProject.models.ShoppingList;
import com.malds.groceriesProject.services.ShoppingListService;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShoppingListController.class)
class ShoppingListControllerTest {

    @MockBean
    ShoppingListService shoppingListService;

    @Autowired
    MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    void testGetShoppingListByID() throws Exception{
        final String EXPECTED_RESPONSE = "[{\"shoppingListID\":\"988\",\"clientID\":\"12345f\",\"productIDToQuantity\":{\"123456789\":\"5\"}}]";
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("123456789", "5");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("988");
        shoppingList.setClientID("12345f");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        when(shoppingListService.getShoppingListByID("988")).thenReturn(List.of(shoppingList));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/shopping_list/988"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(shoppingListService).getShoppingListByID("988");
        assertEquals(EXPECTED_RESPONSE, content);
    }

    @Test
    public void testGetShoppingListByInvalidID() throws Exception {
        final String EXPECTED_RESPONSE = "This shoppingList ID doesn't exists (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null) (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)";
        when(shoppingListService.getShoppingListByID("988")).thenThrow(new ResourceNotFoundException("This shoppingList ID doesn't exists (Service: " +
                "null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/shopping_list/988"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(shoppingListService).getShoppingListByID("988");
        assertEquals(EXPECTED_RESPONSE, content);
    }


    @Test
    void testProductsToQuantityByID() throws Exception{
        final String EXPECTED_RESPONSE = "{\"123456789\":\"5\"}";
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("123456789", "5");
        when(shoppingListService.getProductsToQuantityByID("988")).thenReturn(productIDToQuantity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/shopping_list/products/988"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(shoppingListService).getProductsToQuantityByID("988");
        assertEquals(EXPECTED_RESPONSE, content);

    }

    @Test
    public void testGetProductsToQuantityByInvalidID() throws Exception {
        final String EXPECTED_RESPONSE = "ERROR: check input values (Service: null; Status Code: " +
                "0; Error Code: null; Request ID: null; Proxy: null)";
        when(shoppingListService.getProductsToQuantityByID("988")).thenThrow(new ResourceNotFoundException("ERROR: check input values (Service: null; Status Code: \" +\n" +
                "                        \"0; Error Code: null; Request ID: null; Proxy: null)"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/shopping_list/products/988"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(shoppingListService).getProductsToQuantityByID("988");
        assertEquals(EXPECTED_RESPONSE, content);
    }

    @Test
    void getShoppingListByClientID() throws Exception{
        final String EXPECTED_RESPONSE = "{\"shoppingListID\":\"988\",\"clientID\":\"12345f\",\"productIDToQuantity\":{\"123456789\":\"5\"}}";
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("123456789", "5");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("988");
        shoppingList.setClientID("12345f");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        when(shoppingListService.getShoppingListByClientID("12345f")).thenReturn(shoppingList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/shopping_list/client/12345f"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(shoppingListService).getShoppingListByClientID("12345f");
        assertEquals(EXPECTED_RESPONSE, content);
    }

    @Test
    void getShoppingListByNullClientID() throws Exception{
        final String EXPECTED_RESPONSE = "Shopping List does not exist for clientID (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)";
        when(shoppingListService.getShoppingListByClientID("12345f")).thenThrow(new ResourceNotFoundException("Shopping List does not exist for clientID"));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/shopping_list/client/12345f"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(shoppingListService).getShoppingListByClientID("12345f");
        assertEquals(EXPECTED_RESPONSE, content);
    }

    @Test
    public void testCreateShoppingList() throws Exception {
        final String EXPECTED_RESPONSE = "[{\"shoppingListID\":\"9\",\"clientID\":\"123\",\"productIDToQuantity\":{\"123456789\":\"5\"}}]";

        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("123456789", "5");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("9");
        shoppingList.setClientID("123");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(shoppingList);

        when(shoppingListService.createShoppingList(shoppingList)).thenReturn(List.of(shoppingList));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/shopping_list").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        Mockito.verify(shoppingListService).createShoppingList(shoppingList);
        assertEquals(EXPECTED_RESPONSE, content);


    }

    @Test
    public void testCreateShoppingListException() throws Exception {
        final String EXPECTED_RESPONSE =  "This shoppingList ID already exists";

        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("123456789", "5");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("9");
        shoppingList.setClientID("123");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(shoppingList);

        when(shoppingListService.createShoppingList(shoppingList)).thenThrow(new Exception("This shoppingList ID already exists"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/shopping_list").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        Mockito.verify(shoppingListService).createShoppingList(shoppingList);
        assertEquals(EXPECTED_RESPONSE, content);

    }

    @Test
    public void testUpdateShoppingList() throws Exception {
        final String EXPECTED_RESPONSE = "[{\"shoppingListID\":\"123\",\"clientID\":\"345\",\"productIDToQuantity\":{\"123456789\":\"5\"}}]";
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("123456789", "5");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("123");
        shoppingList.setClientID("345");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(shoppingList);

        when(shoppingListService.updateShoppingList(shoppingList)).thenReturn(List.of(shoppingList));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/shopping_list").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(shoppingListService).updateShoppingList(shoppingList);
        assertEquals(EXPECTED_RESPONSE, content);
    }

    @Test
    public void testUpdateShoppingListWithInvalidID() throws Exception {
        final String EXPECTED_RESPONSE = "ERROR: check input values; be sure to include ShoppingListID";
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("123456789", "5");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("2");
        shoppingList.setClientID("345");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(shoppingList);

        when(shoppingListService.updateShoppingList(shoppingList)).thenThrow(new ResourceNotFoundException("ERROR: check input values; be sure to include ShoppingListID"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/shopping_list").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        Mockito.verify(shoppingListService).updateShoppingList(shoppingList);
        assertEquals(EXPECTED_RESPONSE, content);
    }

    @Test
    public void testUpdateShoppingListWithNullClientID() throws Exception {
        final String EXPECTED_RESPONSE = "ERROR: check input values; be sure to include ShoppingListID";
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("123456789", "5");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("2");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(shoppingList);

        when(shoppingListService.updateShoppingList(shoppingList)).thenThrow(new ResourceNotFoundException("ERROR: check input values; be sure to include ShoppingListID"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/shopping_list").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        Mockito.verify(shoppingListService).updateShoppingList(shoppingList);
        assertEquals(EXPECTED_RESPONSE, content);
    }

    @Test
    public void testUpdateShoppingListWithInvalidProductToQuantityValue() throws Exception {
        final String EXPECTED_RESPONSE = "ERROR: check input values; be sure to include ShoppingListID";
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("123456789", "kjlk");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("2");
        shoppingList.setClientID("345");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(shoppingList);

        when(shoppingListService.updateShoppingList(shoppingList)).thenThrow(new ResourceNotFoundException("ERROR: check input values; be sure to include ShoppingListID"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/shopping_list").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        Mockito.verify(shoppingListService).updateShoppingList(shoppingList);
        assertEquals(EXPECTED_RESPONSE, content);
    }

    @Test
    public void testUpdateShoppingListWithInvalidProductToQuantityKey() throws Exception {
        final String EXPECTED_RESPONSE = "ERROR: check input values; be sure to include ShoppingListID";
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("faklsdf", "5");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("988");
        shoppingList.setClientID("344");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(shoppingList);

        when(shoppingListService.updateShoppingList(shoppingList)).thenThrow(new ResourceNotFoundException("ERROR: check input values; be sure to include ShoppingListID"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/shopping_list").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);
        Mockito.verify(shoppingListService).updateShoppingList(shoppingList);
        assertEquals(EXPECTED_RESPONSE, content);
    }

    @Test
    public void testDeleteShoppingList() throws Exception {
        doNothing().when(shoppingListService).deleteShoppingListByID("9");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/shopping_list/9"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(shoppingListService).deleteShoppingListByID("9");
        assertEquals("", content);
    }

    @Test
    public void testDeleteShoppingListInvalidID() throws Exception {
        final String EXPECTED_RESPONSE = "ERROR: check shoppingListID value (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)";
        doThrow(new ResourceNotFoundException("This shoppingList ID doesn't exist")).when(shoppingListService).deleteShoppingListByID("1000");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/shopping_list/1000"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Content: " + content);

        Mockito.verify(shoppingListService).deleteShoppingListByID("1000");
        assertEquals(EXPECTED_RESPONSE, content);

    }
}