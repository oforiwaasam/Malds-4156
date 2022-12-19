const searchBtn = document.getElementById('search-btn');
const searchInput = document.getElementById('search-input');
const searchResultsDiv = document.getElementById("search-results")
const zeroResultsDiv = document.getElementById("zero-results");
const searchResultsColumns = document.getElementById("column-names");
//TODO: change hardcoded value
localStorage.setItem("clientID", "445")

const getVendorByID = async (vendorID) => {
    try{
        const response = await fetch(`https://groceries-project.herokuapp.com/vendors/${vendorID}`);
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
    const response = await fetch(`https://groceries-project.herokuapp.com/shopping_list/client/${clientID}`);
    const data = await response.json();
    return data;
}

const get_search_results = async (query) => {
    const response = await fetch(`https://groceries-project.herokuapp.com/products/get_product_by_name/${query}`);
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
            //TODO: change hardcoded value
            const shoppingList = await getShoppingListByClientID("1");
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






