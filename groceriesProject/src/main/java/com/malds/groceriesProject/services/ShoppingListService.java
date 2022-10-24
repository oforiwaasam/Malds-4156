package com.malds.groceriesProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malds.groceriesProject.repositories.ShoppingListRepository;
import com.malds.groceriesProject.models.ShoppingList;

@Service
public class ShoppingListService {

    @Autowired    
    private ShoppingListRepository shoppingListRepository;

    public ShoppingList getShoppingListByID(String shoppingListID) {
        return shoppingListRepository.getShoppingListByID(shoppingListID);
    }

    public ShoppingList createShoppingList(ShoppingList shoppingList){
        return shoppingListRepository.createShoppingList(shoppingList);
    }

    public void deleteShoppingListByID(String shoppingListID) {
        shoppingListRepository.deleteShoppingListByID(shoppingListID);
    }
}