package com.malds.groceriesProject.models;

import static org.junit.jupiter.api.Assertions.*;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.malds.groceriesProject.repositories.ClientRepository;
import com.malds.groceriesProject.repositories.ProductRepository;
import com.malds.groceriesProject.repositories.ShoppingListRepository;
import com.malds.groceriesProject.services.ShoppingListService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingListTest {
    @Autowired
    private ShoppingListService shoppingListService;

    @MockBean
    private ShoppingListRepository shoppingListRepository;

    @MockBean
    private ProductRepository productRepository ;

    @MockBean
    private ClientRepository clientRepository;

    @Test
    public void testGetShoppingListByID() throws Exception {

        final String EXPECTED_SHOPPING_LIST_ID = "1";
        final String EXPECTED_CLIENT_ID = "123";
        final Map<String, String> EXPECTED_PRODUCT_ID_TO_QUANTITY = new HashMap<String, String>();
        EXPECTED_PRODUCT_ID_TO_QUANTITY.put("445", "1");

        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("445", "1");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("1");
        shoppingList.setClientID("123");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        Mockito.when(shoppingListRepository.existsByID("1")).thenReturn(true);
        Mockito.when(shoppingListRepository.retriveList("1")).thenReturn(List.of(shoppingList));
        Mockito.when(shoppingListRepository.getShoppingListByID("1")).thenReturn(shoppingList);

        assertEquals(shoppingListService.getShoppingListByID("1").get(0).getShoppingListID(),
                EXPECTED_SHOPPING_LIST_ID);
        assertEquals(shoppingListService.getShoppingListByID("1").get(0).getClientID(),
                EXPECTED_CLIENT_ID);
        assertEquals(shoppingListService.getShoppingListByID("1").get(0).getProductIDToQuantity(),
                EXPECTED_PRODUCT_ID_TO_QUANTITY);
    }

    @Test
    public void testGetShoppingListByIDNotFound() throws Exception {

        final String EXPECTED_EXCEPTION = "This shoppingList ID doesn't exists (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)";

        Mockito.when(shoppingListRepository.retriveList("1")).thenReturn(new ArrayList<ShoppingList>());

        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> {
                shoppingListService.getShoppingListByID("1");
            });
            assertEquals(EXPECTED_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testGetShoppingListByClientID() throws Exception {

        final String EXPECTED_SHOPPING_LIST_ID = "1";
        final String EXPECTED_CLIENT_ID = "123";
        final Map<String, String> EXPECTED_PRODUCT_ID_TO_QUANTITY = new HashMap<String, String>();
        EXPECTED_PRODUCT_ID_TO_QUANTITY.put("445", "1");

        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("445", "1");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("1");
        shoppingList.setClientID("123");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        Mockito.when(shoppingListRepository.retriveAllItems()).thenReturn(List.of(shoppingList));
        Mockito.when(shoppingListRepository.getShoppingListByClientID("123")).thenReturn(shoppingList);

        assertEquals(shoppingListService.getShoppingListByClientID("123").getShoppingListID(),
                EXPECTED_SHOPPING_LIST_ID);
        assertEquals(shoppingListService.getShoppingListByClientID("123").getClientID(),
                EXPECTED_CLIENT_ID);
        assertEquals(shoppingListService.getShoppingListByClientID("123").getProductIDToQuantity(),
                EXPECTED_PRODUCT_ID_TO_QUANTITY);
    }

    @Test
    public void testGetShoppingListByClientIDNotFound() throws Exception {

        final String EXPECTED_EXCEPTION = "Shopping List does not exist for clientID (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)";

        Mockito.when(shoppingListRepository.retriveAllItems()).thenReturn(new ArrayList<ShoppingList>());
        Mockito.when(shoppingListRepository.getShoppingListByClientID("123")).thenReturn(null);

        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> {
                shoppingListService.getShoppingListByClientID("123");
            });
            assertEquals(EXPECTED_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testSaveShoppingList() throws Exception {

        final String EXPECTED_SHOPPINGLIST_ID = "1";
        final String EXPECTED_CLIENT_ID = "123";
        final Map<String, String> EXPECTED_PRODUCT_ID_TO_QUANTITY = new HashMap<String, String>();
        EXPECTED_PRODUCT_ID_TO_QUANTITY.put("445", "1");

        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("445", "1");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("1");
        shoppingList.setClientID("123");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        Mockito.when(shoppingListRepository.createShoppingList(shoppingList))
                .thenReturn(shoppingList);
        Mockito.when(clientRepository.existsByID(shoppingList.getClientID()))
                .thenReturn(true);

        for (Map.Entry<String, String> entry : shoppingList
                .getProductIDToQuantity().entrySet()) {
            String productID = entry.getKey();
            Mockito.when(productRepository.existsByID(productID))
                    .thenReturn(true);
        }

        assertEquals(
                shoppingListService.createShoppingList(shoppingList).get(0).getShoppingListID(),
                EXPECTED_SHOPPINGLIST_ID);
        assertEquals(shoppingListService.createShoppingList(shoppingList).get(0).getClientID(),
                EXPECTED_CLIENT_ID);
        assertEquals(shoppingListService.createShoppingList(shoppingList).get(0)
                .getProductIDToQuantity(), EXPECTED_PRODUCT_ID_TO_QUANTITY);

    }

    @Test
    public void testSaveShoppingListIDExists() throws Exception {

        final String EXPECTED_EXCEPTION = "This shoppingList ID already exists";
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("445", "1");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("1");
        shoppingList.setClientID("123");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        Mockito.when(shoppingListRepository.retriveList("1")).thenReturn(List.of(shoppingList));
        
        Throwable exception = assertThrows(Exception.class, () -> {
                shoppingListService.createShoppingList(shoppingList);
            });
            assertEquals(EXPECTED_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testUpdateShoppingList() throws Exception {
        ShoppingList updatedShoppingList = new ShoppingList();
        updatedShoppingList.setShoppingListID("1");
        updatedShoppingList.setClientID("123");
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("445", "1");
        updatedShoppingList.setProductIDToQuantity(productIDToQuantity);

        Mockito.when(shoppingListRepository.getShoppingListByID("1"))
                .thenReturn(updatedShoppingList);
        Mockito.when(shoppingListRepository.updateShoppingList(updatedShoppingList))
                .thenReturn(List.of(updatedShoppingList));

        assertEquals(shoppingListRepository.updateShoppingList(updatedShoppingList).get(0)
                .getShoppingListID(), updatedShoppingList.getShoppingListID());
        assertEquals(
                shoppingListRepository.updateShoppingList(updatedShoppingList).get(0).getClientID(),
                updatedShoppingList.getClientID());
        assertEquals(shoppingListRepository.updateShoppingList(updatedShoppingList).get(0)
                .getProductIDToQuantity(), updatedShoppingList.getProductIDToQuantity());

    }

    @Test
    public void testDeleteShoppingListByID() {
        // initialize Client to be deleted
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("445", "1");

        ShoppingList deleteShoppingList = new ShoppingList();
        deleteShoppingList.setShoppingListID("3");
        deleteShoppingList.setClientID("123");
        deleteShoppingList.setProductIDToQuantity(productIDToQuantity);

        Mockito.when(shoppingListRepository.existsByID("3")).thenReturn(true);
        shoppingListService.deleteShoppingListByID("3");
    }

    @Test
    public void testGetProductsToQuantity() {
        final Map<String, String> EXPECTED_PRODUCT_ID_TO_QUANTITY = new HashMap<String, String>();
        EXPECTED_PRODUCT_ID_TO_QUANTITY.put("445", "1");

        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("445", "1");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("1");
        shoppingList.setClientID("123");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        Mockito.when(shoppingListRepository.existsByID("1")).thenReturn(true);
        Mockito.when(shoppingListRepository.getProductsToQuantityByID("1"))
                .thenReturn(shoppingList.getProductIDToQuantity());

        assertEquals(shoppingListService.getProductsToQuantityByID("1"),
                EXPECTED_PRODUCT_ID_TO_QUANTITY);
    }

    @Test
    public void testNoShoppingListIDUpdate() {
        // Initialize client update
        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        productIDToQuantity.put("445", "1");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("1");
        shoppingList.setClientID("123");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        Mockito.when(shoppingListRepository.existsByID("32")).thenReturn(false);
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> {
            shoppingListService.updateShoppingList(shoppingList);
        });
        assertEquals(
                "Shopping List ID not found (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)",
                exception.getMessage());
    }

    @Test
    public void testNoShoppingListIDDelete() {
        Mockito.when(shoppingListRepository.existsByID("32")).thenReturn(false);
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> {
            shoppingListService.deleteShoppingListByID("32");
        });
        assertEquals(
                "This shoppingList ID doesn't exist (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)",
                exception.getMessage());
    }

    @Test
    public void testNoGetProductsToQuantity() {
        Mockito.when(shoppingListRepository.existsByID("32")).thenReturn(false);
        Throwable exception = assertThrows(ResourceNotFoundException.class, () -> {
            shoppingListService.getProductsToQuantityByID("32");
        });
        assertEquals(
                "This shoppingList ID doesn't exist (Service: null; Status Code: 0; Error Code: null; Request ID: null; Proxy: null)",
                exception.getMessage());
    }

    @Test
    public void testInvalidShoppingListID() throws Exception {
        Map<String,String> productIDToQuantity = new HashMap<String,String>();
        productIDToQuantity.put("445","1");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setClientID("123");
        shoppingList.setProductIDToQuantity(productIDToQuantity);


        Throwable exception = assertThrows(Exception.class,
                ()->{shoppingListService.checkValidInput(shoppingList);} );
        assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void testInvalidClientID() throws Exception{
        Map<String,String> productIDToQuantity = new HashMap<String,String>();
        productIDToQuantity.put("445","1");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("1");
        shoppingList.setProductIDToQuantity(productIDToQuantity);

        Throwable exception = assertThrows(Exception.class,
                ()->{shoppingListService.checkValidInput(shoppingList);} );
        assertEquals("Value cannot be null", exception.getMessage());
    }
    @Test
    public void testInvalidProductIDToQuantity() throws Exception{
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("1");
        shoppingList.setClientID("123");

        Throwable exception = assertThrows(Exception.class,
                ()->{shoppingListService.checkValidInput(shoppingList);} );
        assertEquals("Value cannot be null", exception.getMessage());
    }

    @Test
    public void testInvalidProductIDToQuantityType() throws Exception {
        Map<String,String> productIDToQuantity = new HashMap<String,String>();
        productIDToQuantity.put("445","abc");

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("1");
        shoppingList.setClientID("123");
        shoppingList.setProductIDToQuantity(productIDToQuantity);


        Throwable exception = assertThrows(Exception.class,
                ()->{shoppingListService.checkValidInput(shoppingList);} );
        //assertEquals("The quantity value within the productIDToQuantity is invalid. Make sure it only contains numbers", exception.getMessage());
    }
}


