package com.malds.groceriesProject.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;

import com.malds.groceriesProject.services.ShoppingListService;
import com.malds.groceriesProject.models.ShoppingList;

@RestController
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    @RequestMapping(value = "/get_shopping_list/{id}", method = RequestMethod.GET)
    public ShoppingList getShoppingList(@PathVariable("id") String shoppingListID) {
        System.out.println("Got to shopping list controller before call");
        ShoppingList returnedList = shoppingListService.getShoppingListByID(shoppingListID);
        System.out.println("Got to shopping list controller after call");
        System.out.println(shoppingListID); 
        System.out.println(returnedList);
        return returnedList;
    }

    @RequestMapping(value = "/create_shopping_list", method = RequestMethod.POST)
    public ShoppingList createShoppingList(@RequestBody ShoppingList shoppingList) {
        return shoppingListService.createShoppingList(shoppingList);
    }

    @RequestMapping(value = "/delete_shopping_list/{id}", method = RequestMethod.DELETE)
    public void deleteShoppingListByID(@PathVariable("id") String shoppingListID) {
        shoppingListService.deleteShoppingListByID(shoppingListID);
    }

    
}