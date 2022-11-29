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
## Running Sample Client
- To run the sample client go open up any of these files in /Malds-4156/client/templates/
- There are 4 html to run:
    * home.html
    * products.html
    * shoppingList.html
    * login.html
- A third party can create their client by using any of the API endpoints which can be seen in this link: https://groceries-project.herokuapp.com/swagger-ui/index.html
- Any API endpoint used needs to have https://groceries-project.herokuapp.com/ before the API endpoint
- For example, https://groceries-project.herokuapp.com/shopping_list
