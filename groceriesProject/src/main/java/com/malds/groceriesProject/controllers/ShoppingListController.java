package com.malds.groceriesProject.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import com.malds.groceriesProject.services.ShoppingListService;

@RestController
public class ShoppingListController {

    private final ShoppingListService shoppingListService;
    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService){
        this.shoppingListService = shoppingListService;
    }

    @RequestMapping(value = "/get_shopping_list", method = RequestMethod.GET)
    public void getShoppingList() {}

    @RequestMapping(value = "/create_shopping_list", method = RequestMethod.POST)
    public void createShoppingList() {}

    @RequestMapping(value = "/delete_shopping_list", method = RequestMethod.DELETE)
    public void deleteShoppingList() {}

}
