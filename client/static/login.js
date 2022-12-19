const signInBtn = document.getElementById("sign-in-btn");
const vendorSignInBtn = document.getElementById("vendor-sign-in-btn");


const getClientByID = async (clientID) => {
    try{
        const response = await fetch(`https://groceries-project.herokuapp.com/clients/${clientID}`, {
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

const isUserFound = async (user) =>{
    if (Object.keys(user).length > 0){
        return true;
    }
    return false;
}

signInBtn.addEventListener("click", async () =>{
    const usernameInput = document.getElementById("username-input");
    const username = usernameInput.value;

    let user = await getClientByID(username);
    if (await isUserFound(user)){
        sessionStorage.setItem("client", JSON.stringify(user));
        console.log(JSON.parse(sessionStorage.getItem("client")))
        window.location.href = "home.html";
        return;
    }
})

vendorSignInBtn.addEventListener("click", async () =>{
    const usernameInput = document.getElementById("vendor-username-input");
    const username = usernameInput.value;
    let user = await getVendorByID(username);
    if (await isUserFound(user)){
        sessionStorage.setItem("vendor", JSON.stringify(user));
        console.log(JSON.parse(sessionStorage.getItem("vendor")))
        window.location.href = "products.html";
        return;
    }
})