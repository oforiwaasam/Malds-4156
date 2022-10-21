package com.malds.groceriesProject.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.data.repository.CrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

import com.malds.groceriesProject.models.Client;

//@EnableScan
//public interface clientRepository extends CrudRepository<Client, Integer>{
@Repository
public class clientRepository{
    @Autowired
    private DynamoDBMapper dynamoDBMapper;
}
