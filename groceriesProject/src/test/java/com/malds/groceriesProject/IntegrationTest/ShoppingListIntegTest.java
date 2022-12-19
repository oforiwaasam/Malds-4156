package com.malds.groceriesProject.IntegrationTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.malds.groceriesProject.models.ShoppingList;
import com.malds.groceriesProject.services.ShoppingListService;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class ShoppingListIntegTest {
    @Autowired
    private ShoppingListService shoppingListService;

    //@Test
    public void createShoppingListInteg() throws Exception  {

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListID("created_integ_1");
        shoppingList.setClientID("client_integ_1");

        Map<String, String> productIDToQuantity = new HashMap<String, String>();
        for(int i = 0; i < 5; i++){
            productIDToQuantity.put(("product_integ_"+i),String.valueOf(i));
        } 
        shoppingList.setProductIDToQuantity(productIDToQuantity);
        shoppingListService.createShoppingList(shoppingList);

        //Test that the shopping list exists
        Assert.assertEquals(shoppingList.getShoppingListID(), shoppingListService.getShoppingListByID("created_integ_1").get(0).getShoppingListID());
        
        //Cleanup
        shoppingListService.deleteShoppingListByID("created_integ_1");
    }
}
