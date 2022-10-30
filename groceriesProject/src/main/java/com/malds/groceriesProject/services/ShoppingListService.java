package com.malds.groceriesProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malds.groceriesProject.repositories.ShoppingListRepository;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.ShoppingList;

@Service
public class ShoppingListService {
    @Autowired    
    private ShoppingListRepository shoppingListRepository;

    public ShoppingList getShoppingListByID(String shoppingListID) throws ResourceNotFoundException{
        ShoppingList shop;
        if(shoppingListRepository.retriveList(shoppingListID).size() > 0){
            shop = shoppingListRepository.getShoppingListByID(shoppingListID); 
        }else{
           throw new ResourceNotFoundException("This shoppingList ID doesn't exists");
        }
        return shop;
    }

    public ShoppingList createShoppingList(ShoppingList shoppingList) throws Exception{
        ShoppingList shop;
        if(shoppingListRepository.retriveList(shoppingList.getShoppingListID()).size() == 0){
            shop = shoppingListRepository.createShoppingList(shoppingList); 
        }else{
           throw new Exception("This shoppingList ID already exists");
        }
        return shop;
    }

    public void deleteShoppingListByID(String shoppingListID) throws ResourceNotFoundException{
        if(shoppingListRepository.retriveList(shoppingListID).size() == 0){
            shoppingListRepository.deleteShoppingListByID(shoppingListID);
        }else{
           throw new ResourceNotFoundException("This shoppingList ID already exists");
        }
    }
}
