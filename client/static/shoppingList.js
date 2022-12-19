const shoppingListItemsDiv = document.getElementById("shopping-list-items");
let shoppingListObj;


const client = JSON.parse(sessionStorage.getItem("client"));
const clientID = client["clientID"];
console.log(clientID)

const getShoppingListByClientID = async (clientID) => {
    const response = await fetch(`https://groceries-project.herokuapp.com/shopping_list/client/${clientID}`, {
        method: 'GET',
        headers: {
            //TODO add token
            "authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjBOU3BHZjBETVFBcjgtQnd1eVJ0cyJ9.eyJpc3MiOiJodHRwczovL2Rldi16c3l4d3hxeWRheDFzamR3LnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJrQVRvRm5JTjNSNjl0TVo1bG1iTmN6ZXhxOU9vVWJhc0BjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9ncm9jZXJpZXNBUEkuZXhhbXBsZS5jb20iLCJpYXQiOjE2NzE0MDY4MDIsImV4cCI6MTY3MTQ5MzIwMiwiYXpwIjoia0FUb0ZuSU4zUjY5dE1aNWxtYk5jemV4cTlPb1ViYXMiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.suMppPq-BqZeTXZGSLtKDdwuWjuGRaL0-vRkBY0wjhTsdy53U6B9dBuYi2fqqNY2MpuyADf6a9XsS3qdC2TmceCd_Q7JE3kiOu6_tV_NalFqsJhsDPbZzCR2suauew7grgCGFyNqBuPdJryr94i7Okp5sE1jgR_IkzShoboaR9ePcDqvCmUvhfJc8C9PvnA1nYdEI36xE-O8bQWX7mnb8REH5P-x3QxsLyDhXLIdhmho8hkwNeil9LdwtUqAZfycaipqKe3TtxEDRaOnesI6DGSlivlnUjXReNDdsCPOZgIhnjxoAINSrNdSx4WoIqga8qHBj3eyhknlixlTg7VXCw"
        },
    });
    const data = await response.json();
    console.log(data)
    return data;
}

const updateShoppingList = (shoppingList) => {
    console.log(JSON.stringify(shoppingList))
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
            location.reload();
        },
        error: function(request, status, error){
            console.log('Error');
            console.log(request);
            console.log(status);
            console.log(error);
        }
    })
}

const getProductByID = async (productID) => {
    const response = await fetch(`https://groceries-project.herokuapp.com/products/get_product_by_id/${productID}`, {
        method: 'GET',
        headers: {
            //TODO add token
            "authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjBOU3BHZjBETVFBcjgtQnd1eVJ0cyJ9.eyJpc3MiOiJodHRwczovL2Rldi16c3l4d3hxeWRheDFzamR3LnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJrQVRvRm5JTjNSNjl0TVo1bG1iTmN6ZXhxOU9vVWJhc0BjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9ncm9jZXJpZXNBUEkuZXhhbXBsZS5jb20iLCJpYXQiOjE2NzE0MDY4MDIsImV4cCI6MTY3MTQ5MzIwMiwiYXpwIjoia0FUb0ZuSU4zUjY5dE1aNWxtYk5jemV4cTlPb1ViYXMiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.suMppPq-BqZeTXZGSLtKDdwuWjuGRaL0-vRkBY0wjhTsdy53U6B9dBuYi2fqqNY2MpuyADf6a9XsS3qdC2TmceCd_Q7JE3kiOu6_tV_NalFqsJhsDPbZzCR2suauew7grgCGFyNqBuPdJryr94i7Okp5sE1jgR_IkzShoboaR9ePcDqvCmUvhfJc8C9PvnA1nYdEI36xE-O8bQWX7mnb8REH5P-x3QxsLyDhXLIdhmho8hkwNeil9LdwtUqAZfycaipqKe3TtxEDRaOnesI6DGSlivlnUjXReNDdsCPOZgIhnjxoAINSrNdSx4WoIqga8qHBj3eyhknlixlTg7VXCw"
        },
    });
    const data = await response.json();
    return data[0];
}

const getProducts = async (productIDToQuantity) => {
    const products = [];
    for (const [productID, quantity] of Object.entries(productIDToQuantity)) {
        const product = await getProductByID(productID);
        product['quantityInShoppingList'] = quantity;
        products.push(product);
    }
    console.log(products)
    return products;
}

const displayShoppingList = (products) => {
    //for (const [productID, product] of Object.entries(products)) {
    for (const index in products){
        const product = products[index]

        const rowDiv = document.createElement("div");
        const productNameDiv = document.createElement("div");
        const priceDiv = document.createElement("div");
        const quantityDiv = document.createElement("div");
        const minusBtnDiv = document.createElement("div");
        const addBtnDiv = document.createElement("div");
        const addBtn = document.createElement("btn");
        const minusBtn = document.createElement("btn");

        const productID = product['productID']
        const productName = product['productName']
        const price = `$${parseFloat(product['price']).toFixed(2).toString()}`
        const quantityInShoppingList = product['quantityInShoppingList'];

        productNameDiv.classList.add("col-4");
        productNameDiv.innerHTML = productName;
        priceDiv.classList.add("col-2")
        priceDiv.innerHTML = price;
        quantityDiv.classList.add("col-2");
        quantityDiv.innerHTML = quantityInShoppingList;
        minusBtnDiv.classList.add("col-2")

        addBtn.innerHTML = "+"
        addBtn.classList.add("btn", "btn-dark", "py-0");
        addBtnDiv.classList.add("col-2");
        addBtnDiv.append(addBtn)

        minusBtn.innerHTML = "-"
        minusBtn.classList.add("btn", "btn-dark", "py-0");
        minusBtnDiv.classList.add("col-2");
        minusBtnDiv.append(minusBtn)

        addBtn.addEventListener("click", (e)=>{
            e.preventDefault()
            const productIDToQuantity = shoppingListObj['productIDToQuantity']
            if (productID in productIDToQuantity){
                let quantity = productIDToQuantity[productID]
                let updatedQuantity = parseInt(quantity) + 1
                productIDToQuantity[productID] = updatedQuantity.toString();
            } else{
                productIDToQuantity[productID] = '1'
            }
            shoppingListObj['productIDToQuantity'] = productIDToQuantity;
            updateShoppingList(shoppingListObj);
            /*const products = getProducts(productIDToQuantity);
            displayShoppingList(products)*/
            //location.reload()
        })
        minusBtn.addEventListener("click", (e)=>{
            e.preventDefault()
            const productIDToQuantity = shoppingListObj['productIDToQuantity']
            console.log(productIDToQuantity);
            if (productID in productIDToQuantity){
                let quantity = productIDToQuantity[productID]
                let updatedQuantity = parseInt(quantity) - 1
                if  (updatedQuantity <= 0) {
                    delete productIDToQuantity[productID]
                } else {
                    productIDToQuantity[productID] = updatedQuantity.toString();
                }
            }
            shoppingListObj['productIDToQuantity'] = productIDToQuantity;
            console.log(shoppingListObj)
            updateShoppingList(shoppingListObj);
            //location.reload()
            /*const products = getProducts(productIDToQuantity);
            displayShoppingList(products)*/

        })

        rowDiv.classList.add("row", "py-4", "mx-auto");
        rowDiv.append(productNameDiv, priceDiv, quantityDiv , addBtnDiv, minusBtnDiv);
        shoppingListItemsDiv.append(rowDiv);
    }
}


const init = async () => {
    shoppingListObj = await getShoppingListByClientID(clientID);
    const productIDToQuantity = shoppingListObj['productIDToQuantity'];
    const products = await getProducts(productIDToQuantity);

    displayShoppingList(products);
}

init();


