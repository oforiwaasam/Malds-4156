package com.malds.groceriesProject.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.malds.groceriesProject.models.ShoppingList;
import com.malds.groceriesProject.services.ShoppingListService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin
@RestController
@RequestMapping("/shopping_list")
public class ShoppingListController extends BaseController {
    /**
     * Shopping List Controller.
     * Processes the requests received by interacting
     * with the shopping list service layer.
     */
    @Autowired
    private ShoppingListService shoppingListService;

    /**
     * given shoppingListID input, searches the ShoppingList
     * table in dynamoDB and returns the shoppingList with
     * shoppingListID.
     * Throws ResourceNotFoundException if shoppingListID does not exist.
     * @param shoppingListID
     * @return List containing the ShoppingList with specified shoppingListID
     * @throws ResourceNotFoundException
     */
    @GetMapping("/{id}")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    /*public List<ShoppingList> getShoppingList(
            @PathVariable("id") final String shoppingListID)
            throws ResourceNotFoundException, Exception {
        try {
            return shoppingListService.getShoppingListByID(shoppingListID);
        } catch (ResourceNotFoundException re) {
            System.out.println(re.getMessage());
            throw new ResourceNotFoundException("ERROR: check input values");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new Exception("aut");
        }

    }*/
    public ResponseEntity<Object> getShoppingList(
        @PathVariable("id") final String shoppingListID) {
        try {
            return new ResponseEntity<>(shoppingListService.
            getShoppingListByID(shoppingListID), HttpStatus.OK);
        } catch (ResourceNotFoundException re) {
            System.out.println(re.getMessage());
            return new ResponseEntity<>(
                re.getErrorMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(
                ex.getMessage(), HttpStatus.BAD_REQUEST);
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
    @GetMapping("/products/{id}")
    @Operation(summary = "My endpoint",
        security = @SecurityRequirement(name = "bearerAuth"))
    public Map<String, String> getProductsToQuantityByID(
            @PathVariable("id") final String shoppingListID)
            throws ResourceNotFoundException, Exception {
        try {
            return shoppingListService
                    .getProductsToQuantityByID(shoppingListID);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ERROR: check input values");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new Exception("aut");
        }
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
    @GetMapping("/client/{id}")
    @Operation(summary = "My endpoint",
        security = @SecurityRequirement(name = "bearerAuth"))
    public ShoppingList getShoppingListByClientID(
            @PathVariable("id") final String clientID)
            throws ResourceNotFoundException {
        try {
            return shoppingListService.getShoppingListByClientID(clientID);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Shopping List does "
                    + "not exist for clientID");
        }
    }

    /**
     * Saves/creates new ShoppingList into the ShoppingList
     * table in dynamoDB and returns the newly created ShoppingList.
     * throws Exception if shoppingListID already exists or invalid inputs.
     * @param shoppingList
     * @return List containing the saved/created shoppingList
     * @throws Exception
     */
    @PostMapping()
    @Operation(summary = "My endpoint",
        security = @SecurityRequirement(name = "bearerAuth"))
    public List<ShoppingList> createShoppingList(
            @RequestBody final ShoppingList shoppingList)
            throws Exception {
        try {
            return shoppingListService.createShoppingList(shoppingList);
        } catch (Exception e) {
            //throw new Exception("ERROR: check input values");
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Updates existing ShoppingList in the ShoppingList table
     * in dynamoDB and returns the updated ShoppingList in a list.
     * @param shoppingList
     * @return List containing the updated ShoppingList
     * @throws Exception
     */
    @PutMapping()
    @Operation(summary = "My endpoint",
        security = @SecurityRequirement(name = "bearerAuth"))
    public List<ShoppingList> updateShoppingList(
            @RequestBody final ShoppingList shoppingList)
            throws Exception {
        try {
            if (shoppingList.getShoppingListID() == null) {
                throw new Exception("Must provide ShoppingListID");
            }
            return shoppingListService.updateShoppingList(shoppingList);
        } catch (Exception e) {
            throw new Exception("ERROR: check input values; "
                    + "be sure to include ShoppingListID");
        }
    }

    /**
     *  Deletes shoppingList in the ShoppingList table in dynamoDB
     *  Throws ResourceNotFoundException if shoppingListID does not exist.
     * @param shoppingListID
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "My endpoint",
        security = @SecurityRequirement(name = "bearerAuth"))
    public void deleteShoppingListByID(
            @PathVariable("id") final String shoppingListID) {
        try {
            shoppingListService.deleteShoppingListByID(shoppingListID);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(
                    "ERROR: check shoppingListID value");
        }
    }
}
