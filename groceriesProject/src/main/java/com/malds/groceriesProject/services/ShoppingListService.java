package com.malds.groceriesProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

import com.malds.groceriesProject.repositories.ShoppingListRepository;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.ShoppingList;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;

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
        if(shoppingListRepository.existsByID(shoppingListID)){
            shoppingListRepository.deleteShoppingListByID(shoppingListID);
        }else{
           throw new ResourceNotFoundException("This shoppingList ID doesn't exist");
        }
    }
    public Map<String,String> getProductsToQuantityByID(String shoppingListID) throws ResourceNotFoundException{
        if (shoppingListRepository.existsByID(shoppingListID)) {
            return shoppingListRepository.getProductsToQuantityByID(shoppingListID);
        }else{
            throw new ResourceNotFoundException("This shoppingList ID doesn't exist");
        }
    }

    public void checkValidInput(ShoppingList shoppingList) throws Exception{
        if (shoppingList.getShoppingListID() == null || shoppingList.getClientID() == null
            || shoppingList.getProductIDToQuantity() == null) {
            throw new Exception("Value cannot be null");
        }

        for (Map.Entry<String, String> entry : shoppingList.getProductIDToQuantity().entrySet()) {
            String quantity = entry.getValue();
            if (!(quantity.matches("[0-9]+"))){
                throw new Exception("The quantity value within the productIDToQuantity is invalid. Make sure it only contains numbers");
            }
        }

    }
}
