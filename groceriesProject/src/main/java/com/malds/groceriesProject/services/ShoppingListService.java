package com.malds.groceriesProject.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.ShoppingList;
import com.malds.groceriesProject.repositories.ShoppingListRepository;

@Service
public class ShoppingListService {
    @Autowired
    private ShoppingListRepository shoppingListRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }


    /**
     * @param shoppingList
     * @return List<ShoppingList>
     * @throws ResourceNotFoundException
     */
    public List<ShoppingList> updateShoppingList(ShoppingList shoppingList)
            throws ResourceNotFoundException {
        if (shoppingListRepository.getShoppingListByID(
                shoppingList.getShoppingListID()) != null) {
            return shoppingListRepository.updateShoppingList(shoppingList);
        } else {
            throw new ResourceNotFoundException("Shopping List ID not found");
        }
    }


    /**
     * @param shoppingListID
     * @return List<ShoppingList>
     * @throws ResourceNotFoundException
     */
    public List<ShoppingList> getShoppingListByID(String shoppingListID)
            throws ResourceNotFoundException {
        ShoppingList shop;
        if (shoppingListRepository.retriveList(shoppingListID).size() > 0) {
            shop = shoppingListRepository.getShoppingListByID(shoppingListID);
        } else {
            throw new ResourceNotFoundException(
                    "This shoppingList ID doesn't exists");
        }
        return List.of(shop);
    }


    /**
     * @param shoppingList
     * @return List<ShoppingList>
     * @throws Exception
     */
    public List<ShoppingList> createShoppingList(ShoppingList shoppingList)
            throws Exception {
        ShoppingList shop;
        if (shoppingListRepository.retriveList(shoppingList.getShoppingListID())
                .size() == 0) {
            shop = shoppingListRepository.createShoppingList(shoppingList);
        } else {
            throw new Exception("This shoppingList ID already exists");
        }
        return List.of(shop);
    }


    /**
     * @param shoppingListID
     * @throws ResourceNotFoundException
     */
    public void deleteShoppingListByID(String shoppingListID)
            throws ResourceNotFoundException {
        if (shoppingListRepository.existsByID(shoppingListID)) {
            shoppingListRepository.deleteShoppingListByID(shoppingListID);
        } else {
            throw new ResourceNotFoundException(
                    "This shoppingList ID doesn't exist");
        }
    }


    /**
     * @param shoppingListID
     * @return Map<String, String>
     * @throws ResourceNotFoundException
     */
    public Map<String, String> getProductsToQuantityByID(
            final String shoppingListID) throws ResourceNotFoundException {
        if (shoppingListRepository.existsByID(shoppingListID)) {
            return shoppingListRepository
                    .getProductsToQuantityByID(shoppingListID);
        } else {
            throw new ResourceNotFoundException(
                    "This shoppingList ID doesn't exist");
        }
    }
}
