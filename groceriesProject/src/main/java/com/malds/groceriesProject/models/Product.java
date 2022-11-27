package com.malds.groceriesProject.models;

// import java.util.Hashtable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Data;

@Data
@DynamoDBTable(tableName = "Product")
public class Product {

    /**
     * DynamoDBAttribute: productID. type: String Hash Key
     */
    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String productID;

    /**
     * DynamoDBAttribute: productName. type: String
     */
    @DynamoDBAttribute
    private String productName;

    /**
     * DynamoDBAttribute: vendorID. type: String
     */
    @DynamoDBAttribute
    private String vendorID;

    /**
     * DynamoDBAttribute: price. type: String
     */
    @DynamoDBAttribute
    private String price;

    /**
     * DynamoDBAttribute: quantity. type: String
     */
    @DynamoDBAttribute
    private String quantity;

    /**
     * Product default constructor.
     */
    public Product() {
        this.productID = null;
        this.productName = null;
        this.vendorID = null;
        this.price = null;
        this.quantity = null;
    }

}
