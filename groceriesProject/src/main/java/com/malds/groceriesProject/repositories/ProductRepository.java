package com.malds.groceriesProject.repositories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.malds.groceriesProject.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Product findProductById(String productId) {
        return dynamoDBMapper.load(Product.class, productId);

    }

    public List<Product> findAllProducts(){
        DynamoDBQueryExpression<Product> query = new DynamoDBQueryExpression<>();
        return dynamoDBMapper.query(Product.class, query);
    }

    public Product addProduct(Product newProduct) {
        dynamoDBMapper.save(newProduct);
        return newProduct;
        // maybe add a print statement?
    }

    public void deleteProductByID(String productId) {
        Product product = dynamoDBMapper.load(Product.class, productId);
        dynamoDBMapper.delete(product);
    }

    public void getListOfProductsByName(String productName){}
}
