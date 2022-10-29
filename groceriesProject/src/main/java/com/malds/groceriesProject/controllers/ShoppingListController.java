package com.malds.groceriesProject.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.malds.groceriesProject.services.ShoppingListService;
import com.malds.groceriesProject.models.ShoppingList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class ShoppingListController {
    @Autowired
    private ShoppingListService shoppingListService;

    @GetMapping("/shopping_list/{id}")
    public List<ShoppingList> getShoppingList(@PathVariable("id") String shoppingListID) {
        return shoppingListService.getShoppingListByID(shoppingListID);
    }
    @PostMapping("/shopping_list")
    public List<ShoppingList> createShoppingList(@RequestBody ShoppingList shoppingList) throws Exception{
        try {
            if (shoppingList.getShoppingListID() != null) {
                throw new Exception("Do not provide shopping list ID");
            }
            System.out.println(shoppingList);
            return shoppingListService.createShoppingList(shoppingList);
        } catch (Exception e){
            throw new Exception("ERROR: check input values");
        }
    }
    @PutMapping("/shopping_list")
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
    @DeleteMapping("/shopping_list/{id}")
    public void deleteShoppingListByID(@PathVariable("id") String shoppingListID) {
        shoppingListService.deleteShoppingListByID(shoppingListID);
    }
}