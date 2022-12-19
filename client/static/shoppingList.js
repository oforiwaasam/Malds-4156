const shoppingListItemsDiv = document.getElementById("shopping-list-items");
let shoppingListObj;
const updateShoppingList = (shoppingList) => {
    console.log(JSON.stringify(shoppingList))
    $.ajax({
        type: 'PUT',
        url: `https://groceries-project.herokuapp.com/shopping_list`,
        data: JSON.stringify(shoppingList),
        dataType: "json",
        contentType: 'application/json; charset=utf-8',
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
    const response = await fetch(`https://groceries-project.herokuapp.com/products/get_product_by_id/${productID}`);
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

const getShoppingListByClientID = async (clientID) => {
    const response = await fetch(`https://groceries-project.herokuapp.com/shopping_list/client/${clientID}`);
    const data = await response.json();
    console.log(data)
    return data;
}

const init = async () => {
    //TODO: change hardcoded value
    shoppingListObj = await getShoppingListByClientID('1');
    const productIDToQuantity = shoppingListObj['productIDToQuantity'];
    const products = await getProducts(productIDToQuantity);

    displayShoppingList(products);
}

init();


