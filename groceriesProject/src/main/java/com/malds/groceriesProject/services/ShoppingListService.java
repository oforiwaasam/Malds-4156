package com.malds.groceriesProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malds.groceriesProject.repositories.ShoppingListRepository;
import com.malds.groceriesProject.models.ShoppingList;

@Service
public class ShoppingListService {
    
    private ShoppingListRepository shoppingListRepository;
    @Autowired
    public ShoppingListService(ShoppingListRepository shoppingListRepository){
        this.shoppingListRepository = shoppingListRepository;
    }

    public ShoppingList getShoppingListByID(String shoppingListID) {
        return shoppingListRepository.getShoppingListByID(shoppingListID);
    }
}
