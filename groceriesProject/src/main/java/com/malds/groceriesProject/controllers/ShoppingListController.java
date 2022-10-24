package com.malds.groceriesProject.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.malds.groceriesProject.services.ShoppingListService;
import com.malds.groceriesProject.models.ShoppingList;

@RestController
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    @RequestMapping(value = "/get_shopping_list/{id}", method = RequestMethod.GET)
    public List<ShoppingList> getShoppingList(@PathVariable("id") String shoppingListID) {
        List<ShoppingList> newList = new ArrayList<>();
        newList.add(shoppingListService.getShoppingListByID(shoppingListID));
        return newList;
    }

    @RequestMapping(value = "/create_shopping_list", method = RequestMethod.POST)
    public List<ShoppingList> createShoppingList(@RequestBody ShoppingList shoppingList) {
        List<ShoppingList> newList = new ArrayList<>();
        newList.add(shoppingListService.createShoppingList(shoppingList));
        return newList;
    }

    @RequestMapping(value = "/delete_shopping_list/{id}", method = RequestMethod.DELETE)
    public void deleteShoppingListByID(@PathVariable("id") String shoppingListID) {
        shoppingListService.deleteShoppingListByID(shoppingListID);
    }

    
}