const addProductBtn = document.getElementById("add-product-btn");
const productNameInput = document.getElementById("product-name");
const productPriceInput = document.getElementById("product-price");
const productQuantityInput = document.getElementById("product-quantity");
const vendorProductsDiv = document.getElementById("vendor-products")

const vendor = JSON.parse(sessionStorage.getItem("vendor"));
const vendorID = vendor["vendorID"];

const getProductsByVendorID = async () =>{
    const response = await fetch(`https://groceries-project.herokuapp.com/products/get_product_by_vendor_id/${vendorID}`, {
        method: 'GET',
        headers: {
            //TODO add token
        },
    });
    const data = await response.json();
    return data
}

const deleteProductWithID = (productID) => {
    $.ajax({
        type: 'DELETE',
        url: `https://groceries-project.herokuapp.com/products/${productID}`,
        contentType: 'application/json; charset=utf-8',
        //TODO add token
        headers: {},
        success: function(result){
            console.log(result);
            location.reload()
        },
        error: function(request, status, error){
            console.log('Error');
            console.log(request);
            console.log(status);
            console.log(error);
        }
    })
}

const updateProduct = (product, productID) => {
    $.ajax({
        type: 'PUT',
        url: `https://groceries-project.herokuapp.com/products/${productID}`,
        data: JSON.stringify(product),
        dataType: "json",
        contentType: 'application/json; charset=utf-8',
        //TODO add token
        headers: {},
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

const addProduct = (product) => {
    $.ajax({
        type: 'POST',
        url: `https://groceries-project.herokuapp.com/products`,
        data: JSON.stringify(product),
        dataType: "json",
        contentType: 'application/json; charset=utf-8',
        //TODO add token
        headers: {},
        success: function(result){
            console.log(result);
            productNameInput.value = ""
            productPriceInput.value = ""
            productQuantityInput.value = ""
            location.reload()
        },
        error: function(request, status, error){
            console.log('Error');
            console.log(request);
            console.log(status);
            console.log(error);
        }
    })
}

const getInputValues = () => {
    const productName = productNameInput.value;
    const productPrice = productPriceInput.value;
    const productQuantity = productQuantityInput.value;

    const product = {
        "productName": productName,
        "vendorID": vendorID,
        "price": productPrice,
        "quantity": productQuantity,
        "industry": "Grocery",
    }
    return product;
}

addProductBtn.addEventListener("click", ()=>{
    const product = getInputValues();
    addProduct(product);

})

const displayProducts = (products) =>{
    for (let index in products) {
        const product = products[index]
        const productID = product['productID']
        const productName = product['productName']
        let productPrice = product['price'];
        let productQuantity = product['quantity'];

        const rowDiv = document.createElement("div");
        const productNameDiv = document.createElement("div");
        const priceDiv = document.createElement("div");
        const quantityDiv = document.createElement("div");
        const editBtnDiv = document.createElement("div");
        const editBtn = document.createElement("btn");
        const saveBtnDiv = document.createElement("div");
        const saveBtn = document.createElement("btn");
        const deleteBtnDiv = document.createElement("div");
        const deleteBtn = document.createElement("btn");

        productNameDiv.classList.add("col-2");
        productNameDiv.innerHTML = productName;
        priceDiv.classList.add("col-2")
        priceDiv.innerHTML = productPrice;
        quantityDiv.classList.add("col-2");
        quantityDiv.innerHTML = productQuantity;


        editBtn.innerHTML = "Edit"
        editBtn.classList.add("btn", "btn-dark");
        editBtnDiv.classList.add("col-2");
        editBtnDiv.append(editBtn)

        saveBtn.innerHTML = "Save"
        saveBtn.classList.add("btn", "btn-primary")
        saveBtnDiv.classList.add("col-2", "d-none");
        saveBtnDiv.append(saveBtn)

        deleteBtn.innerHTML = "Delete";
        deleteBtn.classList.add("btn", "btn-danger")
        deleteBtnDiv.classList.add("col-2", "d-none");
        deleteBtnDiv.append(deleteBtn);

        deleteBtn.addEventListener("click", () => {
            deleteProductWithID(productID);
        })

        saveBtn.addEventListener("click", ()=>{
            const priceInput = document.getElementById("price-input");
            const quantityInput = document.getElementById("quantity-input");
            const newPrice = priceInput.value;
            const newQuantity = quantityInput.value;

            if ( ( (newPrice != productPrice) || (newQuantity != productQuantity) )
                && (newPrice.length != 0) && (newQuantity.length !=0) ) {
                product['price'] = newPrice;
                product['quantity'] = newQuantity;
                updateProduct(product, productID);
                priceDiv.innerHTML = newPrice;
                quantityDiv.innerHTML = newQuantity;
                productPrice = newPrice;
                productQuantity = newQuantity;
            } else {
                priceDiv.innerHTML = productPrice;
                quantityDiv.innerHTML = productQuantity;
            }
            editBtn.innerHTML = "Edit"
            saveBtnDiv.classList.add("d-none")
            deleteBtnDiv.classList.add("d-none")

        })

        editBtn.addEventListener("click", ()=>{
            if (editBtn.innerHTML == "Cancel"){
                priceDiv.innerHTML = productPrice;
                quantityDiv.innerHTML = productQuantity;
                editBtn.innerHTML = "Edit"
                saveBtnDiv.classList.add("d-none")
                deleteBtnDiv.classList.add("d-none")
            } else {
                priceDiv.innerHTML = "";
                quantityDiv.innerHTML = "";
                saveBtnDiv.classList.remove("d-none");
                deleteBtnDiv.classList.remove("d-none")

                const priceInput = document.createElement("input");
                const quantityInput = document.createElement("input");
                priceInput.id = "price-input";
                quantityInput.id = "quantity-input";

                priceInput.classList.add("form-control");
                quantityInput.classList.add("form-control");

                priceInput.placeholder = productPrice;
                quantityInput.placeholder = productQuantity;

                priceDiv.append(priceInput);
                quantityDiv.append(quantityInput);

                editBtn.innerHTML = "Cancel"
            }
        })


        rowDiv.classList.add("row", "py-4", "mx-auto");
        rowDiv.append(productNameDiv, priceDiv, quantityDiv , editBtnDiv, saveBtnDiv, deleteBtnDiv);
        vendorProductsDiv.append(rowDiv);
    }
}

const init = async () =>{
    const products = await getProductsByVendorID();
    displayProducts(products);
}

init();
