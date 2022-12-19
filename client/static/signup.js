const clientSignUpBtn = document.getElementById("client-sign-up-btn");
const vendorSignUpBtn = document.getElementById("vendor-sign-up-btn");

const createNewShoppingList = (shoppingList) =>{
    $.ajax({
        type: 'POST',
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
            window.location.href = "home.html"

        },
        error: function(request, status, error){
            console.log('Error');
            console.log(request);
            console.log(status);
            console.log(error);
        }
    })
}

const postClient = (data) => {
    $.ajax({
        type: 'POST',
        url: `https://groceries-project.herokuapp.com/clients`,
        data: JSON.stringify(data),
        dataType: "json",
        contentType: 'application/json; charset=utf-8',
        //TODO add token
        headers: {
            "authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjBOU3BHZjBETVFBcjgtQnd1eVJ0cyJ9.eyJpc3MiOiJodHRwczovL2Rldi16c3l4d3hxeWRheDFzamR3LnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJrQVRvRm5JTjNSNjl0TVo1bG1iTmN6ZXhxOU9vVWJhc0BjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9ncm9jZXJpZXNBUEkuZXhhbXBsZS5jb20iLCJpYXQiOjE2NzE0MDY4MDIsImV4cCI6MTY3MTQ5MzIwMiwiYXpwIjoia0FUb0ZuSU4zUjY5dE1aNWxtYk5jemV4cTlPb1ViYXMiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.suMppPq-BqZeTXZGSLtKDdwuWjuGRaL0-vRkBY0wjhTsdy53U6B9dBuYi2fqqNY2MpuyADf6a9XsS3qdC2TmceCd_Q7JE3kiOu6_tV_NalFqsJhsDPbZzCR2suauew7grgCGFyNqBuPdJryr94i7Okp5sE1jgR_IkzShoboaR9ePcDqvCmUvhfJc8C9PvnA1nYdEI36xE-O8bQWX7mnb8REH5P-x3QxsLyDhXLIdhmho8hkwNeil9LdwtUqAZfycaipqKe3TtxEDRaOnesI6DGSlivlnUjXReNDdsCPOZgIhnjxoAINSrNdSx4WoIqga8qHBj3eyhknlixlTg7VXCw"
        },
        success: function(result){
            console.log(result);
            sessionStorage.setItem("client", JSON.stringify(data));
            console.log(JSON.parse(sessionStorage.getItem("client")))
            const shoppingList = {
                "clientID": data["clientID"],
                "productIDToQuantity": {}
            }
            createNewShoppingList(shoppingList);


        },
        error: function(request, status, error){
            console.log('Error');
            console.log(request);
            console.log(status);
            console.log(error);
        }
    })
}

const postVendor = (data) => {
    $.ajax({
        type: 'POST',
        url: `https://groceries-project.herokuapp.com/vendors`,
        data: JSON.stringify(data),
        dataType: "json",
        contentType: 'application/json; charset=utf-8',
        //TODO add token
        headers: {
            "authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjBOU3BHZjBETVFBcjgtQnd1eVJ0cyJ9.eyJpc3MiOiJodHRwczovL2Rldi16c3l4d3hxeWRheDFzamR3LnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJrQVRvRm5JTjNSNjl0TVo1bG1iTmN6ZXhxOU9vVWJhc0BjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9ncm9jZXJpZXNBUEkuZXhhbXBsZS5jb20iLCJpYXQiOjE2NzE0MDY4MDIsImV4cCI6MTY3MTQ5MzIwMiwiYXpwIjoia0FUb0ZuSU4zUjY5dE1aNWxtYk5jemV4cTlPb1ViYXMiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.suMppPq-BqZeTXZGSLtKDdwuWjuGRaL0-vRkBY0wjhTsdy53U6B9dBuYi2fqqNY2MpuyADf6a9XsS3qdC2TmceCd_Q7JE3kiOu6_tV_NalFqsJhsDPbZzCR2suauew7grgCGFyNqBuPdJryr94i7Okp5sE1jgR_IkzShoboaR9ePcDqvCmUvhfJc8C9PvnA1nYdEI36xE-O8bQWX7mnb8REH5P-x3QxsLyDhXLIdhmho8hkwNeil9LdwtUqAZfycaipqKe3TtxEDRaOnesI6DGSlivlnUjXReNDdsCPOZgIhnjxoAINSrNdSx4WoIqga8qHBj3eyhknlixlTg7VXCw"
        },
        success: function(result){
            console.log(result);
            sessionStorage.setItem("vendor", JSON.stringify(data));
            console.log(JSON.parse(sessionStorage.getItem("vendor")))
            window.location.href = "products.html";

        },
        error: function(request, status, error){
            console.log('Error');
            console.log(request);
            console.log(status);
            console.log(error);
        }
    })
}

clientSignUpBtn.addEventListener("click", async ()=>{
    const clientID = document.getElementById("client-id-input").value;
    const category = document.getElementById("client-category-input").value;
    const dob = document.getElementById("client-dob-input").value;
    const email = document.getElementById("client-email-input").value;
    const firstName = document.getElementById("client-first-name-input").value;
    const lastName = document.getElementById("client-last-name-input").value;
    const zipcode = document.getElementById("client-zip-code").value;
    const gender = document.getElementById("client-gender-input").value;

    const data =
    {
        "clientID": clientID,
        "email": email,
        "firstName": firstName,
        "lastName": lastName,
        "gender": gender,
        "dateOfBirth": dob,
        "zipcode": zipcode,
        "category": category
    }


    postClient(data);


})

vendorSignUpBtn.addEventListener("click", ()=>{
    const vendorID = document.getElementById("vendor-id-input").value;
    const companyName = document.getElementById("vendor-company-name-input").value;
    const email = document.getElementById("vendor-email-input").value;
    const industry = document.getElementById("vendor-industry-input").value;
    const zipcode = document.getElementById("vendor-zipcode-input").value;

    const data =
        {
            "vendorID": vendorID,
            "email": email,
            "companyName": companyName,
            "industry": industry,
            "zipcode": zipcode,
        }

    postVendor(data);


})