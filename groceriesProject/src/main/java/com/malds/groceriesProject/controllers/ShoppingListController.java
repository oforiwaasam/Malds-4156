package com.malds.groceriesProject.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.malds.groceriesProject.services.ShoppingListService;
import com.malds.groceriesProject.models.ShoppingList;

import java.util.List;

@RestController
public class ShoppingListController {
    @Autowired
    private ShoppingListService shoppingListService;

    @RequestMapping(value = "/get_shopping_list/{id}", method = RequestMethod.GET)
    public ShoppingList getShoppingList(@PathVariable("id") String shoppingListID) {
        return shoppingListService.getShoppingListByID(shoppingListID);
    }

    @RequestMapping(value = "/create_shopping_list", method = RequestMethod.POST)
    public ShoppingList createShoppingList(@RequestBody ShoppingList shoppingList) {
        return shoppingListService.createShoppingList(shoppingList);
    }

    @RequestMapping(value = "/update_shopping_list", method = RequestMethod.PUT)
    public List<ShoppingList> updateShoppingList(@RequestBody ShoppingList shoppingList) throws Exception {
        try {
            if (shoppingList.getShoppingListID() == null) {
                throw new Exception("Must provide ShoppingListID");
            }
            return shoppingListService.updateShoppingList(shoppingList);
        } catch (Exception e){
            throw new Exception("ERROR: check input values; be sure to include ShoppingListID");
        }
    }

    @RequestMapping(value = "/delete_shopping_list/{id}", method = RequestMethod.DELETE)
    public void deleteShoppingListByID(@PathVariable("id") String shoppingListID) {
        shoppingListService.deleteShoppingListByID(shoppingListID);
    }
}