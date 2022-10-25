package com.malds.groceriesProject.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.malds.groceriesProject.models.ShoppingList;

@Repository
public class ShoppingListRepository{
   
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public List<ShoppingList> retriveList(Integer shoppingListId){
        //dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        Map<String,AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withN(shoppingListId.toString()));

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

    public ShoppingList getShoppingListByID(String shoppingListID){
        ShoppingList listed = dynamoDBMapper.load(ShoppingList.class, shoppingListID);
        System.out.println(listed);
        return listed;
    }
    
    public ShoppingList createShoppingList(ShoppingList shoppingList){
        dynamoDBMapper.save(shoppingList);
        return shoppingList;
    }

    public void deleteShoppingListByID(String shoppingListID) {
        ShoppingList shoppingList = dynamoDBMapper.load(ShoppingList.class, shoppingListID);
        dynamoDBMapper.delete(shoppingList);
    }
}
