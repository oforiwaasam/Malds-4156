package com.malds.groceriesProject.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.malds.groceriesProject.models.ShoppingList;

@Repository
public class ShoppingListRepository{
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public ShoppingList getShoppingListByID(String shoppingListID){
        return dynamoDBMapper.load(ShoppingList.class, shoppingListID);
    }

}
