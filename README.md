# Malds-4156
Team members: Lily Sam (los2119), Ammran Mohamed (ahm2169), Deji Oyerinde(oko2107), Mark Calvario (mac2492), Sarina Sugita (ss6168)

## Running and Testing API
1. Clone the repository: `git clone https://github.com/oforiwaasam/Malds-4156.git`
2. Navigate into project directory: `cd groceriesProject`
3. Run the application with: `mvn spring-boot:run`
4. Access and test API locally using this link: http://localhost:8080/swagger-ui/index.html


P.S: access & secret key cannot be published to public repo (so cannot access dynamoDB directly) 

## Generating Reports
- To generate reports, run `mvn clean test`
    - To see code coverage, run `mvn jacoco:report` after runnning `mvn clean test`. The report should be accessible at `target/site/jacoco/index.html`
## Demo Testing (On Heroku)
You can also test the API in Heroku using this link: https://groceries-project.herokuapp.com/swagger-ui/index.html
- To deploy the latest code onto Heroku, run `mvn clean heroku:deploy`
