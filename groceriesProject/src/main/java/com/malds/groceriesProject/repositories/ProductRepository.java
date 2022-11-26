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
    /**
     * Product Repository. Contains functions allowing for creating,
     * reading, updating, and deleting from the Product Table in DynamoDB
     */

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    /**
     * Searches for Product with productId and returns True if
     * Product exists in Product table, otherwise False.
     *
     * @param productId
     * @return True if product with productId exists, otherwise False
     */
    public boolean existsByID(final String productId) {
        if(productId == null){
            return false;
        }
        Product product = dynamoDBMapper.load(Product.class, productId);
        if (product == null) {
            return false;
        }
        return true;
    }

    /**
     * Searches for Product with productName and returns True if
     * Product exists in Product table, otherwise False.
     *
     * @param productName
     * @return True if product with productName exists, otherwise False
     */
    public boolean existsByName(final String productName) {
        Product product = dynamoDBMapper.load(Product.class, productName);
        if (product == null) {
            return false;
        }
        return true;
    }

    /**
     * Searches for Product with productId and returns the Product
     * object in a list.
     *
     * @param productId
     * @return A list containing the Product with specified productId
     */
    public List<Product> findProductById(final String productId) {
        return List.of(dynamoDBMapper.load(Product.class, productId));
    }

    /**
     * Searches for Product with productName and returns the Product
     * object in a list.
     *
     * @param productName
     * @return A list containing the Product with specified productName
     */
    public List<Product> findProductByName(final String productName) {
        Map<String, AttributeValue> productNames = new HashMap<>();
        productNames.put(":productName", new AttributeValue()
                .withS(productName));
        DynamoDBScanExpression scanExpression =
                new DynamoDBScanExpression()
                        .withFilterExpression("productName = :productName")
                        .withExpressionAttributeValues(productNames);
        return dynamoDBMapper.scan(Product.class, scanExpression);
    }

    /**
     * Returns a list containing all products from Product table in DynamoDB.
     *
     * @return A list containing all products
     */
    public List<Product> findAllProducts() {
        DynamoDBQueryExpression<Product> query =
                new DynamoDBQueryExpression<>();
        return dynamoDBMapper.query(Product.class, query);
    }

    /**
     * Adds product to Product table in DynamoDB and returns the added Product.
     *
     * @param newProduct
     * @return The Product that was added
     */
    public Product addProduct(final Product newProduct) {
        dynamoDBMapper.save(newProduct);
        return newProduct;
    }

    /**
     * Updates product from Product table in dynamoDB and returns the
     * updated Product in a list.
     *
     * @param product
     * @return A list containing the product that was updated
     */
    public List<Product> updateProduct(final Product product) {
        dynamoDBMapper.save(product);
        return List.of(product);
    }

    /**
     * Deletes product with productId from the Product table in dynamoDB.
     *
     * @param productId
     */
    public void deleteProductByID(final String productId) {
        Product product = dynamoDBMapper.load(Product.class, productId);
        dynamoDBMapper.delete(product);
    }

}

