package com.malds.groceriesProject.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.malds.groceriesProject.models.Vendor;

@Repository
public class VendorRepository {
    /**
     * Vendor Repository.
     * Contains functions allowing for creating, reading, updating, and deleting
     * from the Vendor Table in DynamoDB
     */
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    /**
     * Saves vendor to Vendor table in DynamoDB
     * and returns the saved Vendor in a List.
     * @param vendor
     * @return A list containing the Vendor object that was saved
     */
    public List<Vendor> saveVendor(final Vendor vendor) {
        dynamoDBMapper.save(vendor);
        return List.of(vendor);
    }

    /**
     * Searches for Vendor with vendorID and returns the Vendor object.
     * @param vendorID
     * @return A list containing the Vendor with specified vendorID
     */
    public List<Vendor> getVendorByID(final String vendorID) {
        Vendor vendor = dynamoDBMapper.load(Vendor.class, vendorID);
        return List.of(vendor);
    }

    /**
     * Searches for Vendor with vendorID
     * and returns True if Vendor exists in Vendor table,
     * otherwise False.
     * @param vendorID
     * @return True if vendor with vendorID exists, otherwise False
     */
    public boolean existsByID(final String vendorID) {
        Vendor vendor = dynamoDBMapper.load(Vendor.class, vendorID);
        if (vendor == null) {
            return false;
        }
        return true;
    }

    /**
     * Deletes vendor with vendorID from the Vendor table in dynamoDB.
     * @param vendorID
     */
    public void deleteVendorByID(final String vendorID) {
        dynamoDBMapper.delete(dynamoDBMapper.load(Vendor.class, vendorID));
    }

    /**
     * Updates vendor from Vendor table in dynamoDB
     * and returns the updated Vendor object in a list.
     * @param vendor
     * @return A list containing the vendor that was updated
     */
    public List<Vendor> updateVendor(final Vendor vendor) {
        dynamoDBMapper.save(vendor);
        return List.of(vendor);
    }

    /**
     * Returns a list containing all vendors from Vendor table in DynamoDB.
     * @return A list containing all vendors
     */
    public List<Vendor> findAll() {
        return dynamoDBMapper.scan(Vendor.class, new DynamoDBScanExpression());
    }
}
