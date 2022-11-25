package com.malds.groceriesProject.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.malds.groceriesProject.models.ShoppingList;



@Repository
public class ShoppingListRepository {
    /**
     * Shopping List Repository. Contains methods allowing for
     * creating, reading, updating, and deleting from
     * the Shopping List Table in DynamoDB.
     */
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    /**
     * Retrieves the shopping lists in the DB that matches
     * the specified shoppingListID.
     * @param shoppingListId
     * @return A list containing the shopping lists matching
     * the specified shoppingListID
     */
    public List<ShoppingList> retriveList(final String shoppingListId) {
        // dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(shoppingListId));

        DynamoDBQueryExpression<ShoppingList> queryExpression =
                new DynamoDBQueryExpression<ShoppingList>()
                        .withKeyConditionExpression("shoppingListID = :val1")
                        .withExpressionAttributeValues(eav);
        List<ShoppingList> returnedList = dynamoDBMapper
                .query(ShoppingList.class, queryExpression);
        return returnedList;
    }

    /**
     * Retrieves all the shopping lists in the ShoppingList
     * Table in the DB.
     * @return A list containing all the shopping lists
     */
    public List<ShoppingList> retriveAllItems() {
        // dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        /*
        DynamoDBQueryExpression<ShoppingList> queryExpression =
                new DynamoDBQueryExpression<ShoppingList>();
        List<ShoppingList> returnedList = dynamoDBMapper
                .query(ShoppingList.class, queryExpression);
        return returnedList;*/
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<ShoppingList> scanResult = dynamoDBMapper.scan(ShoppingList.class, scanExpression);
        return scanResult;
    }

    /**
     * Saves shoppingList to ShoppingList table in DynamoDB
     * and returns the saved ShoppingList in a List.
     * @param newItem
     * @return A list containing the ShoppingList that was saved
     */
    public List<ShoppingList> saveItem(final ShoppingList newItem) {
        dynamoDBMapper.save(newItem);
        return List.of(newItem);
    }

    /**
     * Searches for ShoppingList with shoppingListID
     * and returns the ShoppingList object.
     * @param shoppingListID
     * @return the Shopping List with specified shoppingListID
     */
    public ShoppingList getShoppingListByID(final String shoppingListID) {

        ShoppingList listed = dynamoDBMapper
                .load(ShoppingList.class, shoppingListID);
        return listed;
    }

    /**
     * Searches for ShoppingList with clientID
     * and returns the ShoppingList object.
     * @param clientID
     * @return the Shopping List with specified clientID
     */
    public ShoppingList getShoppingListByClientID(final String clientID) {
        List<ShoppingList> shoppingLists = retriveAllItems();
        ShoppingList listed = null;
        for (ShoppingList shoppingList : shoppingLists) {
            if (shoppingList.getClientID().equals(clientID)) {
                listed = shoppingList;
                break;
            }
        }
        return listed;
    }

    /**
     * Saves the newly created shopping list to
     * Shopping List table in DynamoDB
     * and returns the saved ShoppingList in a List.
     * @param shoppingList
     * @return A list containing the Shopping List that was saved
     */
    public ShoppingList createShoppingList(final ShoppingList shoppingList) {
        dynamoDBMapper.save(shoppingList);
        return shoppingList;
    }

    /**
     * Deletes ShoppingList with shoppingListID from
     * the ShoppingList table in dynamoDB and returns
     * the deleted the Shopping List.
     * @param shoppingListID
     * @return A list containing the Shopping List that was deleted
     */

    public List<ShoppingList> deleteShoppingListByID(
            final String shoppingListID) {
        ShoppingList shoppingList = dynamoDBMapper
                .load(ShoppingList.class, shoppingListID);
        dynamoDBMapper.delete(shoppingList);
        return List.of(shoppingList);
    }

    /**
     * Updates shoppingList from ShoppingList table in dynamoDB
     * and returns the updated ShoppingList in a list.
     * @param shoppingList
     * @return A list containing the ShoppingList that was updated
     */
    public List<ShoppingList> updateShoppingList(
            final ShoppingList shoppingList) {
        dynamoDBMapper.save(shoppingList,
                new DynamoDBSaveExpression().withExpectedEntry("shoppingListID",
                        new ExpectedAttributeValue(
                                new AttributeValue().withS(
                                        shoppingList.getShoppingListID()))));
        return List.of(shoppingList);
    }

    /**
     * Searches for ShoppingList with shoppingListID
     * and returns True if ShoppingList exists in Shopping List table,
     * otherwise False.
     * @param shoppingListID
     * @return True if ShoppingList with shoppingListID exists, otherwise False
     */
    public boolean existsByID(final String shoppingListID) {
        ShoppingList shoppingList = dynamoDBMapper
                .load(ShoppingList.class, shoppingListID);
        if (shoppingList == null) {
            return false;
        }
        return true;
    }

    /**
     * Searches for ShoppingList with shoppingListID and
     * returns a hashmap containing the products with their
     * respective quantity of that ShoppingList.
     * @param shoppingListID
     * @return A hashmap where the key is the productID and
     * the value is the quantity
     */
    public Map<String, String> getProductsToQuantityByID(
            final String shoppingListID) {
        ShoppingList listed = dynamoDBMapper
                .load(ShoppingList.class, shoppingListID);
        return listed.getProductIDToQuantity();
    }
}
