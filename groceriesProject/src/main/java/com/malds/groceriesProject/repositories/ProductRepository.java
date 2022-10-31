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

    public boolean existsByID(String productId) {
        Product product = dynamoDBMapper.load(Product.class, productId);
        if(product == null) {
            return false;
        }
        return true;
    }

    public boolean existsByName(String productName) {
        Product product = dynamoDBMapper.load(Product.class, productName);
        if(product == null) {
            return false;
        }
        return true;
    }

    public List<Product> findProductById(String productId) {
        return List.of(dynamoDBMapper.load(Product.class, productId));
    }

    //Find product by name
    public List<Product> findProductByName(String productName) {
        return List.of(dynamoDBMapper.load(Product.class, productName));
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

    public List<Product> updateProduct(Product product) {
        dynamoDBMapper.save(product);
        return List.of(product);
    }

    public void deleteProductByID(String productId) {
        Product product = dynamoDBMapper.load(Product.class, productId);
        dynamoDBMapper.delete(product);
    }
}
