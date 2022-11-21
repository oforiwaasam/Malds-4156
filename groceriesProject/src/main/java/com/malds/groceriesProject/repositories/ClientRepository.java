package com.malds.groceriesProject.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.malds.groceriesProject.models.Client;

@Repository
public class ClientRepository {
    /**
     * Client Repository. Contains functions allowing for creating, reading, updating, and deleting
     * from the Client Table in DynamoDB
     */
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    /**
     * Saves client to Client table in DynamoDB and returns the saved Client in a List.
     * 
     * @param client
     * @return A list containing the Client that was saved
     */
    public List<Client> saveClient(final Client client) {
        dynamoDBMapper.save(client);
        return List.of(client);
    }

    /**
     * Searches for Client with clientID and returns the Client object.
     * 
     * @param clientID
     * @return A list containing the Client with specified clientID
     */
    public List<Client> getClientByID(final String clientID) {
        Client client = dynamoDBMapper.load(Client.class, clientID);
        return List.of(client);
    }

    /**
     * Searches for Client with clientID and returns True if Client exists in Client table,
     * otherwise False.
     * 
     * @param clientID
     * @return True if client with clientID exists, otherwise False
     */
    public boolean existsByID(final String clientID) {
        Client client = dynamoDBMapper.load(Client.class, clientID);
        if (client == null) {
            return false;
        }
        return true;
    }

    /**
     * Deletes client with clientID from the Client table in dynamoDB.
     * 
     * @param clientID
     */
    public void deleteClientByID(final String clientID) {
        dynamoDBMapper.delete(dynamoDBMapper.load(Client.class, clientID));
    }

    /**
     * Updates client from Client table in dynamoDB and returns the updated Client in a list.
     * 
     * @param client
     * @return A list containing the client that was updated
     */
    public List<Client> updateClient(final Client client) {
        dynamoDBMapper.save(client);
        return List.of(client);
    }

    /**
     * Returns a list containing all clients from Client table in DynamoDB.
     * 
     * @return A list containing all clients
     */
    public List<Client> findAll() {
        return dynamoDBMapper.scan(Client.class, new DynamoDBScanExpression());
    }
}
