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

import java.util.List;
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
    /*
    @Test
    public void testSaveShoppingList() throws Exception {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setClientID("123");
        shoppingList.setProductID("444");

        ShoppingList insertedShoppingList = new ShoppingList();
        insertedShoppingList.setShoppingListID("1");
        insertedShoppingList.setClientID("123");
        insertedShoppingList.setProductID("444");

        Mockito.when(shoppingListRepository.saveItem(shoppingList)).thenReturn(List.of(insertedShoppingList));
        assertEquals(shoppingListService.createShoppingList(shoppingList).size(), 0);
    }
    @Test
    public void testUpdateShoppingList() throws Exception{
        ShoppingList updatedShoppingList = new ShoppingList();
        updatedShoppingList.setShoppingListID("1");
        updatedShoppingList.setClientID("123");
        updatedShoppingList.setProductID("445");

        Mockito.when(shoppingListRepository.getShoppingListByID("1")).thenReturn(List.of(updatedShoppingList));
        Mockito.when(shoppingListRepository.updateShoppingList(updatedShoppingList)).thenReturn(List.of(updatedShoppingList));

        assertEquals(shoppingListService.updateShoppingList(updatedShoppingList).get(0).getShoppingListID(), updatedShoppingList.getShoppingListID());
        assertEquals(shoppingListService.updateShoppingList(updatedShoppingList).get(0).getClientID(), updatedShoppingList.getClientID());
        assertEquals(shoppingListService.updateShoppingList(updatedShoppingList).get(0).getProductID(), updatedShoppingList.getProductID());

    }
    @Test
    public void testDeleteShoppingListByID() {
        //initialize Client to be deleted
        ShoppingList deleteShoppingList = new ShoppingList();
        deleteShoppingList.setShoppingListID("3");
        deleteShoppingList.setClientID("123");
        deleteShoppingList.setProductID("445");

        Mockito.when(shoppingListRepository.getShoppingListByID("3")).thenReturn(List.of(deleteShoppingList));
        shoppingListService.deleteShoppingListByID("3");
    }
    */
}