const addProductBtn = document.getElementById("add-product-btn");
const productNameInput = document.getElementById("product-name");
const productPriceInput = document.getElementById("product-price");
const productQuantityInput = document.getElementById("product-quantity");

const addProduct = (product) => {
    $.ajax({
        type: 'POST',
        url: `http://localhost:8080/products`,
        data: JSON.stringify(product),
        dataType: "json",
        contentType: 'application/json; charset=utf-8',
        success: function(result){
            console.log(result);
            productNameInput.value = ""
            productPriceInput.value = ""
            productQuantityInput.value = ""
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
    const vendorID = "2"

    const product = {
        "productName": productName,
        "vendorID": vendorID,
        "price": productPrice,
        "quantity": productQuantity
    }
    return product;
}

addProductBtn.addEventListener("click", ()=>{
    const product = getInputValues();
    addProduct(product);

})

const displayProducts = (products) =>{

}


