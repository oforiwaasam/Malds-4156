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
    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    public List<ShoppingList> getShoppingListByID(String shoppingListID) {
        System.out.println("Got to shopping list service");
        return shoppingListRepository.getShoppingListByID(shoppingListID);
    }

    public List<ShoppingList> createShoppingList(ShoppingList shoppingList){
        System.out.println(shoppingList);
        System.out.println("hi");
        return shoppingListRepository.createShoppingList(shoppingList);
    }

    public List<ShoppingList> updateShoppingList(ShoppingList shoppingList) throws ResourceNotFoundException{
        if(shoppingListRepository.getShoppingListByID(shoppingList.getShoppingListID()) != null) {
            return shoppingListRepository.updateShoppingList(shoppingList);
        } else {
            throw new ResourceNotFoundException("Shopping List ID not found");
        }
    }

    public List<ShoppingList> deleteShoppingListByID(String shoppingListID) {
        return shoppingListRepository.deleteShoppingListByID(shoppingListID);
    }
}
