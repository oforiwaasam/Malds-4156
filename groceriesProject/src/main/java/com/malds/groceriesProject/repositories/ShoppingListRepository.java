package com.malds.groceriesProject.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private DynamoDBMapper dynamoDBMapper;


    /**
     * @param shoppingListId
     * @return List<ShoppingList>
     */
    public List<ShoppingList> retriveList(String shoppingListId) {
        // dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(shoppingListId));

        DynamoDBQueryExpression<ShoppingList> queryExpression =
                new DynamoDBQueryExpression<ShoppingList>()
                        .withKeyConditionExpression("shoppingListID = :val1")
                        .withExpressionAttributeValues(eav);
        List<ShoppingList> returnedList =
                dynamoDBMapper.query(ShoppingList.class, queryExpression);
        return returnedList;
    }


    /**
     * @return List<ShoppingList>
     */
    public List<ShoppingList> retriveAllItems() {
        // dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        DynamoDBQueryExpression<ShoppingList> queryExpression =
                new DynamoDBQueryExpression<ShoppingList>();
        List<ShoppingList> returnedList =
                dynamoDBMapper.query(ShoppingList.class, queryExpression);
        return returnedList;
    }


    /**
     * @param newItem
     * @return List<ShoppingList>
     */
    public List<ShoppingList> saveItem(ShoppingList newItem) {
        dynamoDBMapper.save(newItem);
        return List.of(newItem);
    }


    /**
     * @param shoppingListID
     * @return ShoppingList
     */
    public ShoppingList getShoppingListByID(String shoppingListID) {
        ShoppingList listed =
                dynamoDBMapper.load(ShoppingList.class, shoppingListID);
        return listed;
    }


    /**
     * @param shoppingList
     * @return ShoppingList
     */
    public ShoppingList createShoppingList(ShoppingList shoppingList) {
        dynamoDBMapper.save(shoppingList);
        return shoppingList;
    }


    /**
     * @param shoppingListID
     * @return List<ShoppingList>
     */
    public List<ShoppingList> deleteShoppingListByID(String shoppingListID) {
        ShoppingList shoppingList =
                dynamoDBMapper.load(ShoppingList.class, shoppingListID);
        dynamoDBMapper.delete(shoppingList);
        return List.of(shoppingList);
    }


    /**
     * @param shoppingList
     * @return List<ShoppingList>
     */
    public List<ShoppingList> updateShoppingList(ShoppingList shoppingList) {
        dynamoDBMapper.save(shoppingList,
                new DynamoDBSaveExpression().withExpectedEntry("shoppingListID",
                        new ExpectedAttributeValue(new AttributeValue()
                                .withS(shoppingList.getShoppingListID()))));
        return List.of(shoppingList);
    }


    /**
     * @param shoppingListID
     * @return boolean
     */
    public boolean existsByID(String shoppingListID) {
        ShoppingList shoppingList =
                dynamoDBMapper.load(ShoppingList.class, shoppingListID);
        if (shoppingList == null) {
            return false;
        }
        return true;
    }


    /**
     * @param shoppingListID
     * @return Map<String, String>
     */
    public Map<String, String> getProductsToQuantityByID(
            String shoppingListID) {
        ShoppingList listed =
                dynamoDBMapper.load(ShoppingList.class, shoppingListID);
        return listed.getProductIDToQuantity();
    }
}
