package com.malds.groceriesProject.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.malds.groceriesProject.models.ShoppingList;
import org.springframework.web.bind.annotation.RequestBody;



@Repository
public class ShoppingListRepository{
   
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public List<ShoppingList> retriveList(String shoppingListId){
        //dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        Map<String,AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(shoppingListId));

        DynamoDBQueryExpression<ShoppingList> queryExpression = new DynamoDBQueryExpression<ShoppingList>()
            .withKeyConditionExpression("shoppingListID = :val1")
            .withExpressionAttributeValues(eav);
        List<ShoppingList> returnedList = dynamoDBMapper.query(ShoppingList.class,queryExpression);
        return returnedList;
    }
    public List<ShoppingList> retriveAllItems(){
        //dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        DynamoDBQueryExpression<ShoppingList> queryExpression = new DynamoDBQueryExpression<ShoppingList>();
        List<ShoppingList> returnedList = dynamoDBMapper.query(ShoppingList.class,queryExpression);
        return returnedList;
    }

    public List<ShoppingList> saveItem(ShoppingList newItem){
        dynamoDBMapper.save(newItem);
        return List.of(newItem);
    }

    public ShoppingList getShoppingListByID(String shoppingListID){
        ShoppingList listed = dynamoDBMapper.load(ShoppingList.class, shoppingListID);
        return listed;
    }
    
    public ShoppingList createShoppingList(ShoppingList shoppingList){
        dynamoDBMapper.save(shoppingList);
        return shoppingList;
    }

    public List<ShoppingList> deleteShoppingListByID(String shoppingListID) {
        ShoppingList shoppingList = dynamoDBMapper.load(ShoppingList.class, shoppingListID);
        dynamoDBMapper.delete(shoppingList);
        return List.of(shoppingList);
    }

    public List<ShoppingList> updateShoppingList(ShoppingList shoppingList) {
        dynamoDBMapper.save(shoppingList,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("shoppingListID",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(shoppingList.getShoppingListID())
                                )));
        return List.of(shoppingList);
    }

    public boolean existsByID(String shoppingListID) {
        ShoppingList shoppingList = dynamoDBMapper.load(ShoppingList.class, shoppingListID);
        if(shoppingList == null) {
            return false;
        }
        return true;
    }
    public Map<String,String> getProductsToQuantityByID(String shoppingListID) {
        ShoppingList listed = dynamoDBMapper.load(ShoppingList.class, shoppingListID);
        return listed.getProductIDToQuantity();
    }
}
