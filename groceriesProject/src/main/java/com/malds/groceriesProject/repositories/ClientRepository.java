package com.malds.groceriesProject.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Repository
public class ClientRepository{
    @Autowired
    private DynamoDBMapper dynamoDBMapper;
}
