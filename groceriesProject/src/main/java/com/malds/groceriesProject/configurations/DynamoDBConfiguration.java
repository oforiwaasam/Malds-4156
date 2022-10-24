package com.malds.groceriesProject.configurations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.malds.groceriesProject.models.ShoppingList;


//Used baeldung dynamodb setup

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.malds.groceriesProject.repositories")
public class DynamoDBConfiguration {
    
    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;
    
    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;
    
    @Value("${aws.region}")
    private String awsRegion;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    @Bean
    public DynamoDBMapper dynamoDBMapper(){
        DynamoDBMapper mape = new DynamoDBMapper(amazonDynamoDB());
        System.out.println("Connected mapper");
        /*Test Code to verify Mapper.
        Map<String,AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS("1-fg-3"));

        DynamoDBQueryExpression<ShoppingList> queryExpression = new DynamoDBQueryExpression<ShoppingList>()
            .withKeyConditionExpression("shoppingListID = :val1")
            .withExpressionAttributeValues(eav);
        List<ShoppingList> returnedList = mape.query(ShoppingList.class,queryExpression);

        System.out.println(returnedList.get(0));*/
        return mape;
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        System.out.println("Connected");        


        AmazonDynamoDB createdClient=  AmazonDynamoDBClientBuilder.
            standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, awsRegion))
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey)))
            .build();

        //Code snatched from AWS website to listing tables.
        /*ListTablesRequest request;
        boolean more_tables = true;
        String last_name = null;
        
        while(more_tables) {
            try {
                if (last_name == null) {
                    request = new ListTablesRequest().withLimit(10);
                }
                else {
                    request = new ListTablesRequest()
                            .withLimit(10)
                            .withExclusiveStartTableName(last_name);
                }
        
                ListTablesResult table_list = createdClient.listTables(request);
                List<String> table_names = table_list.getTableNames();
        
                if (table_names.size() > 0) {
                    for (String cur_name : table_names) {
                        System.out.format("* %s\n", cur_name);
                    }
                } else {
                    System.out.println("No tables found!");
                    System.exit(0);
                }
        
                last_name = table_list.getLastEvaluatedTableName();
                if (last_name == null) {
                    more_tables = false;
                }
            }catch (AmazonServiceException e){
                System.err.println(e.getErrorMessage());
                System.exit(1);
            }
        }   
        System.out.println("\nDone listing up tables");*/
        return createdClient;

    }

}
