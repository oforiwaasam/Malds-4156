package com.malds.groceriesProject.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import com.malds.groceriesProject.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public boolean existsByID(String productId) {
        Product product = dynamoDBMapper.load(Product.class, productId);
        if (product == null) {
            return false;
        }
        return true;
    }

    public boolean existsByName(String productName) {
        Product product = dynamoDBMapper.load(Product.class, productName);
        if (product == null) {
            return false;
        }
        return true;
    }

    public List<Product> findProductById(String productId) {
        return List.of(dynamoDBMapper.load(Product.class, productId));
    }

    // Find products by name
    public List<Product> findProductByName(String productName) {
        Map<String, AttributeValue> productNames = new HashMap<>();
        productNames.put(":productName", new AttributeValue().withS(productName));
        DynamoDBScanExpression scanExpression =
                new DynamoDBScanExpression().withFilterExpression("productName = :productName")
                        .withExpressionAttributeValues(productNames);
        return dynamoDBMapper.scan(Product.class, scanExpression);
    }

    public List<Product> findAllProducts() {
        DynamoDBQueryExpression<Product> query = new DynamoDBQueryExpression<>();
        return dynamoDBMapper.query(Product.class, query);
    }

    public Product addProduct(Product newProduct) {
        dynamoDBMapper.save(newProduct);
        return newProduct;
        // maybe add a print statement?
    }

    public List<Product> updateProduct(Product product) {
        dynamoDBMapper.save(product);
        return List.of(product);
    }

    public void deleteProductByID(String productId) {
        Product product = dynamoDBMapper.load(Product.class, productId);
        dynamoDBMapper.delete(product);
    }

    public void getListOfProductsByName(String productName) {}
}

