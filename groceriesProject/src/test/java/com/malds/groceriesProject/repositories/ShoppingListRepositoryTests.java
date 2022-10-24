package com.malds.groceriesProject.repositories;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.malds.groceriesProject.configurations.DynamoDBConfiguration;
import com.malds.groceriesProject.models.ShoppingList;

@SpringBootTest
public class ShoppingListRepositoryTests {
    
    @Autowired
    ShoppingListRepository shoppingListRepository;

    private DynamoDBMapper dynamoDBMapper;

    /*@BeforeClass
    public void setup(){
        AmazonDynamoDB amazonDynamoDB = new DynamoDBConfiguration().amazonDynamoDB();
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest createTableRequest= dynamoDBMapper.generateCreateTableRequest(ShoppingList.class);
        amazonDynamoDB.createTable(createTableRequest);   
    }*/
    @Test
    public void randomTest(){
        Integer x = Integer.parseInt("2");
        Assert.assertNotNull(x);
    }
    /*@Test
    public void givenItemWithExpectedCost_whenRunFindAll_thenItemIsFound() {

        ShoppingList shop = new ShoppingList(Integer.valueOf(505), Integer.valueOf(725));
        shoppingListRepository.saveItem(shop);

        System.out.println("Halfway");
        List<ShoppingList> result = shoppingListRepository.retriveAllItems();
        Assert.assertNotNull(result.get(0).getClientID());
    
    }*/

}
