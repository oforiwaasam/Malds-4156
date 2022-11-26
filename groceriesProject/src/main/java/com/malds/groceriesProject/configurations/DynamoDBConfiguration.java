package com.malds.groceriesProject.configurations;

import java.io.FileNotFoundException;
import java.util.List;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;


// Used baeldung dynamodb setup

@Configuration
@EnableDynamoDBRepositories(
        basePackages = "com.malds.groceriesProject.repositories")
public class DynamoDBConfiguration {
    /**
     * amazon.dynamodb.endpoint --> DynamoDB Endpoint.
     */
    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    /**
     * amazon.aws.accesskey --> AWS credentials.
     */
    @Value("${amazon.aws.accesskey}")
    private String awsAccessKey;

    /**
     * amazon.region --> AWS region in which DynamoDB endpoint is stored in.
     */
    @Value("${aws.region}")
    private String awsRegion;

    /**
     * amazon.aws.secretkey --> AWS credentials.
     */
    @Value("${amazon.aws.secretkey}")
    private String awsSecretKey;

    /**
     * @return DynamoDBMapper
     * @throws FileNotFoundException
     */
    @Bean
    public DynamoDBMapper dynamoDBMapper() throws FileNotFoundException {
        DynamoDBMapper mape = new DynamoDBMapper(amazonDynamoDB());
        System.out.println("Connected mapper");
        /*
         * Test Code to verify Mapper. Map<String,AttributeValue> eav = new
         * HashMap<>(); eav.put(":val1", new AttributeValue().withS("1-fg-3"));
         *
         * DynamoDBQueryExpression<ShoppingList> queryExpression = new
         * DynamoDBQueryExpression<ShoppingList>()
         * .withKeyConditionExpression("shoppingListID = :val1")
         * .withExpressionAttributeValues(eav); List<ShoppingList> returnedList
         * = mape.query(ShoppingList.class,queryExpression);
         * System.out.println(returnedList.get(0)); List<List<String>> records =
         * new ArrayList<>(); try (Scanner scanner = new Scanner(new
         * File("../../../../Downloads/throw/product_mock.csv"));) {
         * scanner.nextLine(); while(scanner.hasNextLine()) { String[] items =
         * scanner.nextLine().split(","); Product newProd = new Product();
         * newProd.setPrice(items[1]); newProd.setProductID(items[0]);
         * newProd.setProductName(items[2]); newProd.setQuantity(items[3]);
         * newProd.setVendorID(items[4]); System.out.println(newProd); } }
         */
        return mape;
    }

    /**
     * @return AmazonDynamoDB
     */
    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        System.out.println("Connected");

        System.out.println("The endpoints + " + amazonDynamoDBEndpoint);
        AmazonDynamoDB createdClient = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                amazonDynamoDBEndpoint, awsRegion))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                .build();
        //listTablesInDynamo(createdClient);
        return createdClient;

    }

    /**
     * This method will test connectivity to actual DynamoDB database and list
     * tables.
     * @param dbClient created client for testing client
     */
    public void listTablesInDynamo(final AmazonDynamoDB dbClient) {
        // Code snatched from AWS website to listing tables.
        ListTablesRequest request;
        boolean moreTables = true;
        String lastName = null;

        while (moreTables) {
            try {
                if (lastName == null) {
                    request = new ListTablesRequest();
                } else {
                    request = new ListTablesRequest()
                            .withExclusiveStartTableName(lastName);
                }
                ListTablesResult tableList = dbClient.listTables(request);
                List<String> tableNames = tableList.getTableNames();

                if (tableNames.size() > 0) {
                    for (String curName : tableNames) {
                        System.out.format("* %s\n", curName);
                    }
                } else {
                    System.out.println("No tables found!");
                    System.exit(0);
                }
                lastName = tableList.getLastEvaluatedTableName();
                if (lastName == null) {
                    moreTables = false;
                }
            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());
                System.exit(1);
            }
        }
        System.out.println("\nDone listing up tables");
    }

}
