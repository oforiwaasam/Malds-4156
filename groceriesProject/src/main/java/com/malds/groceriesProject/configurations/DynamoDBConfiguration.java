package com.malds.groceriesProject.configurations;

import java.io.FileNotFoundException;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;


// Used baeldung dynamodb setup

@Configuration
@EnableDynamoDBRepositories(basePackages = 
        "com.malds.groceriesProject.repositories")
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
    public DynamoDBMapper dynamoDBMapper() throws FileNotFoundException {
        DynamoDBMapper mape = new DynamoDBMapper(amazonDynamoDB());
        System.out.println("Connected mapper");
        /*
         * Test Code to verify Mapper. Map<String,AttributeValue> eav = new HashMap<>();
         * eav.put(":val1", new AttributeValue().withS("1-fg-3"));
         * 
         * DynamoDBQueryExpression<ShoppingList> queryExpression = new
         * DynamoDBQueryExpression<ShoppingList>()
         * .withKeyConditionExpression("shoppingListID = :val1")
         * .withExpressionAttributeValues(eav); List<ShoppingList> returnedList =
         * mape.query(ShoppingList.class,queryExpression);
         * 
         * System.out.println(returnedList.get(0));
         */

        /*
         * List<List<String>> records = new ArrayList<>(); try (Scanner scanner = new Scanner(new
         * File("../../../../Downloads/throw/product_mock.csv"));) { scanner.nextLine(); while
         * (scanner.hasNextLine()) { String[] items = scanner.nextLine().split(","); Product newProd
         * = new Product(); newProd.setPrice(items[1]); newProd.setProductID(items[0]);
         * newProd.setProductName(items[2]); newProd.setQuantity(items[3]);
         * newProd.setVendorID(items[4]); System.out.println(newProd); } }
         */
        return mape;
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        System.out.println("Connected");

        System.out.println("This is the endpoints + " + amazonDynamoDBEndpoint);
        AmazonDynamoDB createdClient = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        amazonDynamoDBEndpoint, awsRegion))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey)))
                .build();

        // Code snatched from AWS website to listing tables.
        /*
         * ListTablesRequest request; boolean more_tables = true; String last_name = null;
         * 
         * while(more_tables) { try { if (last_name == null) { request = new
         * ListTablesRequest().withLimit(10); } else { request = new ListTablesRequest()
         * .withLimit(10) .withExclusiveStartTableName(last_name); }
         * 
         * ListTablesResult table_list = createdClient.listTables(request); List<String> table_names
         * = table_list.getTableNames();
         * 
         * if (table_names.size() > 0) { for (String cur_name : table_names) {
         * System.out.format("* %s\n", cur_name); } } else { System.out.println("No tables found!");
         * System.exit(0); }
         * 
         * last_name = table_list.getLastEvaluatedTableName(); if (last_name == null) { more_tables
         * = false; } }catch (AmazonServiceException e){ System.err.println(e.getErrorMessage());
         * System.exit(1); } } System.out.println("\nDone listing up tables");
         */
        return createdClient;

    }

}
