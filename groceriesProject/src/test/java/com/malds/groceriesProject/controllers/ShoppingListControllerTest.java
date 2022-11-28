package com.malds.groceriesProject.controllers;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malds.groceriesProject.models.ShoppingList;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.malds.groceriesProject.GroceriesProjectApplication;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = GroceriesProjectApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class ShoppingListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetShoppingListByID() throws Exception {

        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("123456789", "5");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("988");
        shoppingList.setClientID("12345f");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        MvcResult mvcResult = this.mockMvc.perform(get("/shopping_list/988"))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shoppingListID").value(shoppingList.getShoppingListID()))
                .andReturn();
    }
    @Test
    public void testGetProductsToQuantityByID() throws Exception {

        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("123456789", "5");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("998");
        shoppingList.setClientID("12345f");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        MvcResult mvcResult = this.mockMvc.perform(get("/shopping_list/products/988"))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
                .andExpect(jsonPath("$['123456789']").value(productIDToQuantity.get("123456789")))
                .andReturn();
    }

    @Test
    public void testCreateShoppingList() throws Exception {
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("123456789", "5");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("9");
        shoppingList.setClientID("123");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/shopping_list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(shoppingList))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shoppingListID").value(shoppingList.getShoppingListID()))
                .andReturn();
    }

    @Test
    public void testUpdateShoppingList() throws Exception {
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("123456789", "10");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("9");
        shoppingList.setClientID("123");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(put("/shopping_list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(shoppingList))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shoppingListID").value(shoppingList.getShoppingListID()))
                .andExpect(jsonPath("$[0].productIDToQuantity").value(shoppingList.getProductIDToQuantity()))
                .andReturn();
    }

    @Test
    public void testDeleteShoppingList() throws Exception {
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("123456789", "10");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("9");
        shoppingList.setClientID("123");
        shoppingList.setProductIDToQuantity(productIDToQuantity);


        mockMvc.perform(delete("/shopping_list/9"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
