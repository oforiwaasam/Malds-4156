package com.malds.groceriesProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.malds.groceriesProject.repositories.ShoppingListRepository;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.ShoppingList;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;

@Service
public class ShoppingListService {
    @Autowired    
    private ShoppingListRepository shoppingListRepository;
    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    public List<ShoppingList> updateShoppingList(ShoppingList shoppingList) throws ResourceNotFoundException{
        if(shoppingListRepository.getShoppingListByID(shoppingList.getShoppingListID()) != null) {
            return shoppingListRepository.updateShoppingList(shoppingList);
        } else {
            throw new ResourceNotFoundException("Shopping List ID not found");
        }
    }

    public List<ShoppingList> getShoppingListByID(String shoppingListID) throws ResourceNotFoundException{
        ShoppingList shop;
        if(shoppingListRepository.retriveList(shoppingListID).size() > 0){
            shop = shoppingListRepository.getShoppingListByID(shoppingListID);
        }else{
           throw new ResourceNotFoundException("This shoppingList ID doesn't exists");
        }
        return List.of(shop);
    }

    public List<ShoppingList> createShoppingList(ShoppingList shoppingList) throws Exception{
        ShoppingList shop;
        if(shoppingListRepository.retriveList(shoppingList.getShoppingListID()).size() == 0){
            shop = shoppingListRepository.createShoppingList(shoppingList);
        }else{
           throw new Exception("This shoppingList ID already exists");
        }
        return List.of(shop);
    }

    public void deleteShoppingListByID(String shoppingListID) throws ResourceNotFoundException{
        if(shoppingListRepository.retriveList(shoppingListID).size() == 0){
            shoppingListRepository.deleteShoppingListByID(shoppingListID);
        }else{
           throw new ResourceNotFoundException("This shoppingList ID already exists");
        }
    }
}
