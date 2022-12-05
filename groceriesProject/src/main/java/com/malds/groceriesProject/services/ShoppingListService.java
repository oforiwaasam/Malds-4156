package com.malds.groceriesProject.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.ShoppingList;
import com.malds.groceriesProject.repositories.ShoppingListRepository;
import com.malds.groceriesProject.repositories.ClientRepository;
import com.malds.groceriesProject.repositories.ProductRepository;

@Service
public class ShoppingListService {
    /**
     * ShoppingListService
     * Carries out the operations and interacts with the persistence layer,
     * ShoppingList Repository, to create, read, update, and
     * delete from the database.
     */
    @Autowired
    private ShoppingListRepository shoppingListRepository;

    /**
     * ProductRepository
     * Carries out the operations and interacts with the persistence layer,
     * ProductRepository to create, read, update, and
     * delete from the database.
     */
    @Autowired
    private ProductRepository productRepository;

    /**
     * ProductRepository
     * Carries out the operations and interacts with the persistence layer,
     * ProductRepository to create, read, update, and
     * delete from the database.
     */
    @Autowired
    private ClientRepository clientRepository;

    /**
     * ShoppingListService Constructor.
     * @param shoppingListRepo
     */
    public ShoppingListService(final ShoppingListRepository
                                       shoppingListRepo) {
        this.shoppingListRepository = shoppingListRepo;
    }

    /**
     * Updates existing ShoppingList info and returns
     * updated ShoppingList in a list.
     * Throws ResourceNotFoundException if ShoppingList does not exist.
     * @param shoppingList
     * @return List containing the updated ShoppingList
     * @throws ResourceNotFoundException
     */
    public List<ShoppingList> updateShoppingList(
            final ShoppingList shoppingList)
            throws ResourceNotFoundException {
        try {
            checkValidInput(shoppingList);
            if (shoppingListRepository.getShoppingListByID(
                    shoppingList.getShoppingListID()) != null) {
                return shoppingListRepository.updateShoppingList(shoppingList);
            } else {
                throw new ResourceNotFoundException("Shopping "
                        + "List ID not found");
            }

        } catch (Exception e) {
            throw new ResourceNotFoundException("Shopping List ID not found");
        }
    }

    /**
     * given shoppingListID input, searches the ShoppingList
     * table in dynamoDB and returns the shoppingList with
     * shoppingListID.
     * Throws ResourceNotFoundException if shoppingListID does not exist.
     * @param shoppingListID
     * @return List containing ShoppingList with specified shoppingListID
     * @throws ResourceNotFoundException
     */
    public List<ShoppingList> getShoppingListByID(final String shoppingListID)
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
     * Searches for ShoppingList with clientID
     * and returns the ShoppingList object.
     * Throws ResourceNotFoundException if clientID
     * does not match any shoppingList
     * @param clientID
     * @return the Shopping List with specified clientID
     * @throws ResourceNotFoundException
     */
    public ShoppingList getShoppingListByClientID(final String clientID)
            throws ResourceNotFoundException {
        ShoppingList shoppingList = shoppingListRepository
                .getShoppingListByClientID(clientID);
        if (shoppingList != null) {
            return shoppingList;
        } else {
            throw new ResourceNotFoundException("Shopping List does "
                    + "not exist for clientID");
        }
    }

    /**
     * Saves/creates new ShoppingList into the ShoppingList
     * table in dynamoDB and returns the newly created ShoppingList.
     * throws Exception if shoppingListID already exists
     * @param shoppingList
     * @return List containing the saved/created shoppingList
     * @throws Exception
     */
    public List<ShoppingList> createShoppingList(
            final ShoppingList shoppingList)throws Exception {
        ShoppingList shop;
        if (shoppingList.getShoppingListID() == null
        || shoppingListRepository.retriveList(
                shoppingList.getShoppingListID()).size() == 0) {
            checkValidInputForCreate(shoppingList);
            shop = shoppingListRepository.createShoppingList(shoppingList);
        } else {
            throw new Exception("This shoppingList ID already exists");
        }
        return List.of(shop);
    }

    /**
     * Deletes shoppingList in the ShoppingList table in dynamoDB
     * throws ResourceNotFoundException if shoppingListID does not exist.
     * @param shoppingListID
     * @throws ResourceNotFoundException
     */
    public void deleteShoppingListByID(final String shoppingListID)
            throws ResourceNotFoundException {
        if (shoppingListRepository.existsByID(shoppingListID)) {
            shoppingListRepository.deleteShoppingListByID(shoppingListID);
        } else {
            throw new ResourceNotFoundException("This "
                    + "shoppingList ID doesn't exist");
        }
    }

    /**
     * given shoppingListID input, searches the ShoppingList
     * table in dynamoDB and returns a hashmap containing
     * the products with their respective quantity within that ShoppingList.
     * Throws ResourceNotFoundException if shoppingListID does not exist.
     * @param shoppingListID
     * @return A hashmap where the key is the productID and
     * the value is the quantity
     * @throws ResourceNotFoundException
     */
    public Map<String, String> getProductsToQuantityByID(
            final String shoppingListID)
            throws ResourceNotFoundException {
        if (shoppingListRepository.existsByID(shoppingListID)) {
            return shoppingListRepository.
                    getProductsToQuantityByID(shoppingListID);
        } else {
            throw new ResourceNotFoundException("This "
                    + "shoppingList ID doesn't exist");
        }
    }


    /**
     * Checks whether inputted values by users are valid,
     * is not blank, and is of accepted data types.
     * Throws Exception if inputs are invalid.
     * @param shoppingList
     * @throws Exception if input is invalid
     */
    public void checkValidInput(
            final ShoppingList shoppingList) throws Exception {
        if (shoppingList.getShoppingListID() == null
                || shoppingList.getClientID() == null
                || shoppingList.getProductIDToQuantity() == null) {
            //return false;
            throw new Exception("Value cannot be null");
        }

        if (clientRepository.existsByID(shoppingList.getClientID())) {
            throw new Exception("ClientID doesn't exist");
        }


        for (Map.Entry<String, String> entry : shoppingList
                .getProductIDToQuantity().entrySet()) {
            String quantity = entry.getValue();
            String productID = entry.getKey();
            if ((!(quantity.matches("[0-9]+")))
                    || (!(productID.matches("[0-9]+")))
                    || productRepository.existsByID(productID)) {
                //return false;

                throw new Exception("The quantity value "
                        + "within the productIDToQuantity"
                        + " is invalid. Make sure it only "
                        + "contains numbers");
            }
        }
        //return true;
    }

        /**
     * Checks whether inputted values by users are valid,
     * is not blank, and is of accepted data types.
     * Throws Exception if inputs are invalid.
     * @param shoppingList
     * @throws Exception if input is invalid
     */
    public void checkValidInputForCreate(
            final ShoppingList shoppingList) throws Exception {
        if (shoppingList.getClientID() == null
            || shoppingList.getProductIDToQuantity() == null) {
            //return false;
            throw new Exception("ClientID or Product Map cannot be null");
        }

        if (clientRepository.existsByID(shoppingList.getClientID())) {
            throw new Exception("ClientID exist");
        }

        for (Map.Entry<String, String> entry : shoppingList
                .getProductIDToQuantity().entrySet()) {
            String quantity = entry.getValue();
            String productID = entry.getKey();
            if ((!(quantity.matches("[0-9]+")))
                    || (!(productID.matches("[0-9]+")))
                    || productRepository.existsByID(productID)) {
                //return false;

                throw new Exception("The quantity value "
                        + "within the productIDToQuantity"
                        + " is invalid. Make sure it only "
                        + "contains numbers");
            }
        }
        //return true;
    }
}
