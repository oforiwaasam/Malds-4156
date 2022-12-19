const signInBtn = document.getElementById("sign-in-btn");


const getClientByID = async (clientID) => {
    try{
        const response = await fetch(`https://groceries-project.herokuapp.com/clients/${clientID}`, {
            method: 'GET',
            headers: {
                //TODO add token
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
    user = await getVendorByID(username);
    if (await isUserFound(user)){
        sessionStorage.setItem("vendor", JSON.stringify(user));
        console.log(JSON.parse(sessionStorage.getItem("vendor")))
        window.location.href = "products.html";
        return;
    }

})