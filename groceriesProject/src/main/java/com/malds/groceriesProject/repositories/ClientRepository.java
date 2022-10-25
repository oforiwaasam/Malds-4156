package com.malds.groceriesProject.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.malds.groceriesProject.models.Client;

@Repository
public class ClientRepository{
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public List<Client> saveClient(Client client) {
        dynamoDBMapper.save(client);
        return List.of(client);
    }

    public List<Client> getClientByID(String clientID) {
        Client client = dynamoDBMapper.load(Client.class, clientID);
        return List.of(client);
    }

    public boolean existsByID(String clientID) {
        Client client = dynamoDBMapper.load(Client.class, clientID);
        if(client == null) {
            return false;
        }
        return true;
    }

    public void deleteClientByID(String clientID) {
        dynamoDBMapper.delete(dynamoDBMapper.load(Client.class, clientID));
    }

    public List<Client> updateClient(Client client) {
        dynamoDBMapper.save(client);
        return List.of(client);
    }

    public List<Client> findAll() {
        return dynamoDBMapper.scan(Client.class, new DynamoDBScanExpression());
    }
}