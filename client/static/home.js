const searchBtn = document.getElementById('search-btn');
const searchInput = document.getElementById('search-input');
const searchResultsDiv = document.getElementById("search-results")
const zeroResultsDiv = document.getElementById("zero-results");
const searchResultsColumns = document.getElementById("column-names");

const client = JSON.parse(sessionStorage.getItem("client"));
const clientID = client["clientID"];
const clientIndustry = client["category"];
console.log(client);

const getVendorByID = async (vendorID) => {
    try{
        const response = await fetch(`https://groceries-project.herokuapp.com/vendors/${vendorID}`, {
                method: 'GET',
                headers: {
                   //TODO add token
                    "authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjBOU3BHZjBETVFBcjgtQnd1eVJ0cyJ9.eyJpc3MiOiJodHRwczovL2Rldi16c3l4d3hxeWRheDFzamR3LnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJrQVRvRm5JTjNSNjl0TVo1bG1iTmN6ZXhxOU9vVWJhc0BjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9ncm9jZXJpZXNBUEkuZXhhbXBsZS5jb20iLCJpYXQiOjE2NzE0MDY4MDIsImV4cCI6MTY3MTQ5MzIwMiwiYXpwIjoia0FUb0ZuSU4zUjY5dE1aNWxtYk5jemV4cTlPb1ViYXMiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.suMppPq-BqZeTXZGSLtKDdwuWjuGRaL0-vRkBY0wjhTsdy53U6B9dBuYi2fqqNY2MpuyADf6a9XsS3qdC2TmceCd_Q7JE3kiOu6_tV_NalFqsJhsDPbZzCR2suauew7grgCGFyNqBuPdJryr94i7Okp5sE1jgR_IkzShoboaR9ePcDqvCmUvhfJc8C9PvnA1nYdEI36xE-O8bQWX7mnb8REH5P-x3QxsLyDhXLIdhmho8hkwNeil9LdwtUqAZfycaipqKe3TtxEDRaOnesI6DGSlivlnUjXReNDdsCPOZgIhnjxoAINSrNdSx4WoIqga8qHBj3eyhknlixlTg7VXCw"
                },
            });
        const data = await response.json();
        return data[0];
    } catch {
        return {}
    }

}

const updateShoppingList = (shoppingList) => {
    console.log(JSON.stringify(shoppingList));
    $.ajax({
        type: 'PUT',
        url: `https://groceries-project.herokuapp.com/shopping_list`,
        data: JSON.stringify(shoppingList),
        dataType: "json",
        contentType: 'application/json; charset=utf-8',
        //TODO add token
        headers: {
            "authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjBOU3BHZjBETVFBcjgtQnd1eVJ0cyJ9.eyJpc3MiOiJodHRwczovL2Rldi16c3l4d3hxeWRheDFzamR3LnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJrQVRvRm5JTjNSNjl0TVo1bG1iTmN6ZXhxOU9vVWJhc0BjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9ncm9jZXJpZXNBUEkuZXhhbXBsZS5jb20iLCJpYXQiOjE2NzE0MDY4MDIsImV4cCI6MTY3MTQ5MzIwMiwiYXpwIjoia0FUb0ZuSU4zUjY5dE1aNWxtYk5jemV4cTlPb1ViYXMiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.suMppPq-BqZeTXZGSLtKDdwuWjuGRaL0-vRkBY0wjhTsdy53U6B9dBuYi2fqqNY2MpuyADf6a9XsS3qdC2TmceCd_Q7JE3kiOu6_tV_NalFqsJhsDPbZzCR2suauew7grgCGFyNqBuPdJryr94i7Okp5sE1jgR_IkzShoboaR9ePcDqvCmUvhfJc8C9PvnA1nYdEI36xE-O8bQWX7mnb8REH5P-x3QxsLyDhXLIdhmho8hkwNeil9LdwtUqAZfycaipqKe3TtxEDRaOnesI6DGSlivlnUjXReNDdsCPOZgIhnjxoAINSrNdSx4WoIqga8qHBj3eyhknlixlTg7VXCw"
        },
        success: function(result){
            console.log(result);
        },
        error: function(request, status, error){
            console.log('Error');
            console.log(request);
            console.log(status);
            console.log(error);
        }
    })
}

const getShoppingListByClientID = async (clientID) => {
    const response = await fetch(`https://groceries-project.herokuapp.com/shopping_list/client/${clientID}`, {
        method: 'GET',
        headers: {
            //TODO add token
            "authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjBOU3BHZjBETVFBcjgtQnd1eVJ0cyJ9.eyJpc3MiOiJodHRwczovL2Rldi16c3l4d3hxeWRheDFzamR3LnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJrQVRvRm5JTjNSNjl0TVo1bG1iTmN6ZXhxOU9vVWJhc0BjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9ncm9jZXJpZXNBUEkuZXhhbXBsZS5jb20iLCJpYXQiOjE2NzE0MDY4MDIsImV4cCI6MTY3MTQ5MzIwMiwiYXpwIjoia0FUb0ZuSU4zUjY5dE1aNWxtYk5jemV4cTlPb1ViYXMiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.suMppPq-BqZeTXZGSLtKDdwuWjuGRaL0-vRkBY0wjhTsdy53U6B9dBuYi2fqqNY2MpuyADf6a9XsS3qdC2TmceCd_Q7JE3kiOu6_tV_NalFqsJhsDPbZzCR2suauew7grgCGFyNqBuPdJryr94i7Okp5sE1jgR_IkzShoboaR9ePcDqvCmUvhfJc8C9PvnA1nYdEI36xE-O8bQWX7mnb8REH5P-x3QxsLyDhXLIdhmho8hkwNeil9LdwtUqAZfycaipqKe3TtxEDRaOnesI6DGSlivlnUjXReNDdsCPOZgIhnjxoAINSrNdSx4WoIqga8qHBj3eyhknlixlTg7VXCw"
        },
    });
    const data = await response.json();
    return data;
}

const get_search_results = async (query) => {
    const response = await fetch(`https://groceries-project.herokuapp.com/products/get_product_by_name/${clientIndustry}/${query}`, {
        method: 'GET',
        headers: {
            //TODO add token
            "authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjBOU3BHZjBETVFBcjgtQnd1eVJ0cyJ9.eyJpc3MiOiJodHRwczovL2Rldi16c3l4d3hxeWRheDFzamR3LnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJrQVRvRm5JTjNSNjl0TVo1bG1iTmN6ZXhxOU9vVWJhc0BjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9ncm9jZXJpZXNBUEkuZXhhbXBsZS5jb20iLCJpYXQiOjE2NzE0MDY4MDIsImV4cCI6MTY3MTQ5MzIwMiwiYXpwIjoia0FUb0ZuSU4zUjY5dE1aNWxtYk5jemV4cTlPb1ViYXMiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.suMppPq-BqZeTXZGSLtKDdwuWjuGRaL0-vRkBY0wjhTsdy53U6B9dBuYi2fqqNY2MpuyADf6a9XsS3qdC2TmceCd_Q7JE3kiOu6_tV_NalFqsJhsDPbZzCR2suauew7grgCGFyNqBuPdJryr94i7Okp5sE1jgR_IkzShoboaR9ePcDqvCmUvhfJc8C9PvnA1nYdEI36xE-O8bQWX7mnb8REH5P-x3QxsLyDhXLIdhmho8hkwNeil9LdwtUqAZfycaipqKe3TtxEDRaOnesI6DGSlivlnUjXReNDdsCPOZgIhnjxoAINSrNdSx4WoIqga8qHBj3eyhknlixlTg7VXCw"
        },
    });
    const data = await response.json();
    return data;
}

const display_search_results = async (products) => {
    if (products.length == 0) {
        zeroResultsDiv.classList.remove("d-none");
        searchResultsDiv.append(zeroResultsDiv)
        return
    }
    zeroResultsDiv.classList.add("d-none");
    searchResultsDiv.append(searchResultsColumns)
    for (let i in products){
        const rowDiv = document.createElement("div");
        const vendorDiv = document.createElement("div");
        const productNameDiv = document.createElement("div");
        const priceDiv = document.createElement("div");
        const addBtnDiv = document.createElement("div");
        const addBtn = document.createElement("btn");

        const product = products[i]
        const vendorID = product["vendorID"]
        const vendor = await getVendorByID(vendorID)

        let vendorName = "placeholder"
        if (Object.keys(vendor).length != 0){
            vendorName = vendor["companyName"];
        }
        const productID = product['productID']
        const productName = product['productName']
        const price = `$${parseFloat(product['price']).toFixed(2).toString()}`

        vendorDiv.classList.add("col-3");
        vendorDiv.innerHTML = vendorName;
        productNameDiv.classList.add("col-3");
        productNameDiv.innerHTML = productName;
        priceDiv.classList.add("col-3")
        priceDiv.innerHTML = price;
        addBtn.classList.add("btn", "btn-dark");
        addBtn.innerHTML = "Add product"
        addBtn.addEventListener("click",async ()=>{
            const shoppingList = await getShoppingListByClientID(clientID);
            const productIDToQuantity = shoppingList['productIDToQuantity']

            if (productID in productIDToQuantity){
                let quantity = productIDToQuantity[productID]
                let updatedQuantity = parseInt(quantity) + 1
                productIDToQuantity[productID] = updatedQuantity.toString();
            } else{
                productIDToQuantity[productID] = '1'
            }
            shoppingList['productIDToQuantity'] = productIDToQuantity;

            updateShoppingList(shoppingList);
        })

        addBtnDiv.classList.add("col-3");
        addBtnDiv.append(addBtn);

        rowDiv.classList.add("row", "py-2", "mx-auto", "w-50");
        rowDiv.append(vendorDiv, productNameDiv, priceDiv, addBtnDiv);
        searchResultsDiv.append(rowDiv);
    }
}

searchBtn.addEventListener('click',async ()=> {
    const query = searchInput.value;
    const products = await get_search_results(query);
    searchResultsDiv.innerHTML = ""
    display_search_results(products)
    searchInput.value = ""
})






