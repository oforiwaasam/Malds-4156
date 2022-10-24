package com.malds.groceriesProject.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.malds.groceriesProject.models.Client;

@Repository
public class ClientRepository{
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Client saveClient(Client client) {
        dynamoDBMapper.save(client);
        return client;
    }

    public Client getClientByID(Integer clientID) {
        return dynamoDBMapper.load(Client.class, clientID);
    }

    public void deleteClientByID(Integer clientID) {
        dynamoDBMapper.delete(dynamoDBMapper.load(Client.class, clientID));
    }

    public Client updateClient(Integer clientID, Client client) {
        dynamoDBMapper.save(client,
                new DynamoDBSaveExpression()
        .withExpectedEntry("clientID",
                new ExpectedAttributeValue(
                        new AttributeValue().withN(Integer.toString(clientID))
                )));
        return client;
    }

    public List<Client> findAll() {
        return dynamoDBMapper.scan(Client.class, new DynamoDBScanExpression());
    }
}