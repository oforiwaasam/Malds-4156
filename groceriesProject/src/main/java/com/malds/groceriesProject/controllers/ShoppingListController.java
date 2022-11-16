package com.malds.groceriesProject.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.ShoppingList;
import com.malds.groceriesProject.services.ShoppingListService;


@RestController
public class ShoppingListController extends BaseController {
    @Autowired
    private ShoppingListService shoppingListService;

    @GetMapping("/shopping_list/{id}")
    public List<ShoppingList> getShoppingList(@PathVariable("id") String shoppingListID)
            throws ResourceNotFoundException {
        return shoppingListService.getShoppingListByID(shoppingListID);
    }

    @GetMapping("/shopping_list/products/{id}")
    public Map<String, String> getProductsToQuantityByID(@PathVariable("id") String shoppingListID)
            throws ResourceNotFoundException {
        try {
            return shoppingListService.getProductsToQuantityByID(shoppingListID);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ERROR: check input values");
        }
    }

    @PostMapping("/shopping_list")
    public List<ShoppingList> createShoppingList(@RequestBody ShoppingList shoppingList)
            throws Exception {
        try {
            return shoppingListService.createShoppingList(shoppingList);
        } catch (Exception e) {
            throw new Exception("ERROR: check input values");
        }
    }

    @PutMapping("/shopping_list")
    public List<ShoppingList> updateShoppingList(@RequestBody ShoppingList shoppingList)
            throws Exception {
        try {
            if (shoppingList.getShoppingListID() == null) {
                throw new Exception("Must provide ShoppingListID");
            }
            return shoppingListService.updateShoppingList(shoppingList);
        } catch (Exception e) {
            throw new Exception("ERROR: check input values; be sure to include ShoppingListID");
        }
    }

    @DeleteMapping("/shopping_list/{id}")
    public void deleteShoppingListByID(@PathVariable("id") String shoppingListID) {
        try {
            shoppingListService.deleteShoppingListByID(shoppingListID);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ERROR: check shoppingListID value");
        }
    }
}
