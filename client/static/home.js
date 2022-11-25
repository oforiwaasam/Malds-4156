const searchBtn = document.getElementById('search-btn');
const searchInput = document.getElementById('search-input');
const searchResultsDiv = document.getElementById("search-results")

const get_search_results = async (query) => {
    const response = await fetch(`http://localhost:8080/products/get_product_by_name/${query}`);
    const data = await response.json();
    return data;
}

const display_search_results = (products) => {
    if (products.length == 0) {
        searchResultsDiv.innerHTML = "0 results"
        return
    }
    for (let i in products){
        const rowDiv = document.createElement("div");
        const productNameDiv = document.createElement("div");
        const priceDiv = document.createElement("div");
        const addBtnDiv = document.createElement("div");
        const addBtn = document.createElement("btn");

        const product = products[i]
        const productName = product['productName']
        const price = `$${parseFloat(product['price']).toFixed(2).toString()}`

        productNameDiv.classList.add("col-6");
        productNameDiv.innerHTML = productName;
        priceDiv.classList.add("col-3")
        priceDiv.innerHTML = price;
        addBtn.classList.add("btn", "btn-dark");
        addBtn.innerHTML = "Add product"
        addBtn.addEventListener("click",()=>{
            console.log(product);
        })

        addBtnDiv.classList.add("col-3");
        addBtnDiv.append(addBtn);

        rowDiv.classList.add("row", "py-2", "mx-auto", "w-50");
        rowDiv.append(productNameDiv, priceDiv, addBtnDiv);
        searchResultsDiv.append(rowDiv);
    }
}

searchBtn.addEventListener('click',async ()=> {
    const query = searchInput.value;
    const products = await get_search_results(query);
    searchResultsDiv.innerHTML = ""
    display_search_results(products)
})






