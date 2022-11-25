const shoppingListItemsDiv = document.getElementById("shopping-list-items");

const getProductByID = async (productID) => {
    const response = await fetch(`http://localhost:8080/products/get_product_by_id/${productID}`);
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
        minusBtnDiv.innerHTML = "-"
        addBtnDiv.classList.add("col-2");
        addBtnDiv.innerHTML = "+"

        rowDiv.classList.add("row", "py-2", "mx-auto");
        rowDiv.append(productNameDiv, priceDiv, quantityDiv , addBtnDiv, minusBtnDiv);
        shoppingListItemsDiv.append(rowDiv);




    }
}

const getShoppingListByClientID = async (clientID) => {
    const response = await fetch(`http://localhost:8080/shopping_list/client/${clientID}`);
    const data = await response.json();
    return data;
}

const init = async () => {
    const shoppingListObj = await getShoppingListByClientID('445');
    const productIDToQuantity = shoppingListObj['productIDToQuantity'];
    const products = await getProducts(productIDToQuantity);

    displayShoppingList(products);
}

init();


