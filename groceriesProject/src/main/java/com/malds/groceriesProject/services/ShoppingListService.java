package com.malds.groceriesProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.malds.groceriesProject.repositories.ShoppingListRepository;
import com.malds.groceriesProject.models.ShoppingList;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;

@Service
public class ShoppingListService {
    @Autowired    
    private ShoppingListRepository shoppingListRepository;

    public List<ShoppingList> getShoppingListByID(String shoppingListID) {
        System.out.println("Got to shopping list service");
        return shoppingListRepository.getShoppingListByID(shoppingListID);
    }

    public List<ShoppingList> createShoppingList(ShoppingList shoppingList) throws Exception{
        if(shoppingListRepository.existsByID(shoppingList.getShoppingListID())) {
            throw new Exception("client ID already exists - must use unique clientID");
        }
        return shoppingListRepository.createShoppingList(shoppingList);
    }

    public List<ShoppingList> updateShoppingList(ShoppingList shoppingList) throws ResourceNotFoundException{
        if(shoppingListRepository.getShoppingListByID(shoppingList.getShoppingListID()) != null) {
            return shoppingListRepository.updateShoppingList(shoppingList);
        } else {
            throw new ResourceNotFoundException("Shopping List ID not found");
        }
    }

    public void deleteShoppingListByID(String shoppingListID) {
        shoppingListRepository.deleteShoppingListByID(shoppingListID);
    }
}
