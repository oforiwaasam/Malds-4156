package com.malds.groceriesProject.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.malds.groceriesProject.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Product getProductById(Integer productId) {

        return dynamoDBMapper.load(Product.class, productId);
    }

    public List<Product> getAllProducts(){

        DynamoDBQueryExpression<Product> queryExpression = new DynamoDBQueryExpression<Product>();
        List<Product> productList = dynamoDBMapper.query(Product.class,queryExpression);
        return productList;

    }
}
