package com.malds.groceriesProject.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.malds.groceriesProject.models.ShoppingList;
import com.malds.groceriesProject.repositories.ShoppingListRepository;
import com.malds.groceriesProject.services.ShoppingListService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingListTest {
    @Autowired
    private ShoppingListService shoppingListService;

    @MockBean
    private ShoppingListRepository shoppingListRepository;

    @Test
    public void testSaveShoppingList() throws Exception {

        final String EXPECTED_SHOPPINGLIST_ID = "1";
        final String EXPECTED_CLIENT_ID = "123";
        final Map<String,String> EXPECTED_PRODUCT_ID_TO_QUANTITY = new HashMap<String,String>();
        EXPECTED_PRODUCT_ID_TO_QUANTITY.put("445","1");

        Map<String,String> productIDToQuantity = new HashMap<String,String>();
        productIDToQuantity.put("445","1");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("1");
        shoppingList.setClientID("123");
        shoppingList.setProductIDToQuantity(productIDToQuantity);


        Mockito.when(shoppingListRepository.createShoppingList(shoppingList)).thenReturn(shoppingList);
        assertEquals(shoppingListService.createShoppingList(shoppingList).get(0).getShoppingListID(), EXPECTED_SHOPPINGLIST_ID);
        assertEquals(shoppingListService.createShoppingList(shoppingList).get(0).getClientID(), EXPECTED_CLIENT_ID);
        assertEquals(shoppingListService.createShoppingList(shoppingList).get(0).getProductIDToQuantity(), EXPECTED_PRODUCT_ID_TO_QUANTITY);

    }
    @Test
    public void testUpdateShoppingList() throws Exception{
        ShoppingList updatedShoppingList = new ShoppingList();
        updatedShoppingList.setShoppingListID("1");
        updatedShoppingList.setClientID("123");
        Map<String,String> productIDToQuantity = new HashMap<String,String>();
        productIDToQuantity.put("445","1");
        updatedShoppingList.setProductIDToQuantity(productIDToQuantity);

        Mockito.when(shoppingListRepository.getShoppingListByID("1")).thenReturn(updatedShoppingList);
        Mockito.when(shoppingListRepository.updateShoppingList(updatedShoppingList)).thenReturn(List.of(updatedShoppingList));

        assertEquals(shoppingListService.updateShoppingList(updatedShoppingList).get(0).getShoppingListID(), updatedShoppingList.getShoppingListID());
        assertEquals(shoppingListService.updateShoppingList(updatedShoppingList).get(0).getClientID(), updatedShoppingList.getClientID());
        assertEquals(shoppingListService.updateShoppingList(updatedShoppingList).get(0).getProductIDToQuantity(), updatedShoppingList.getProductIDToQuantity());

    }
    @Test
    public void testDeleteShoppingListByID() {
        //initialize Client to be deleted
        ShoppingList deleteShoppingList = new ShoppingList();
        deleteShoppingList.setShoppingListID("3");
        deleteShoppingList.setClientID("123");
        Map<String,String> productIDToQuantity = new HashMap<String,String>();
        productIDToQuantity.put("445","1");
        deleteShoppingList.setProductIDToQuantity(productIDToQuantity);

        //Mockito.when(shoppingListRepository.getShoppingListByID("3")).thenReturn(List.of(deleteShoppingList));
        //shoppingListService.deleteShoppingListByID("3");
    }

}