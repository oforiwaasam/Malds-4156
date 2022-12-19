const searchBtn = document.getElementById('search-btn');
const searchResultsDiv = document.getElementById("search-results")
const zeroResultsDiv = document.getElementById("zero-results");

const clientStats = async (category) => {
    console.log(JSON.stringify(category));
    try{
        const response = await 
        fetch(`https://groceries-project.herokuapp.com/clients/${category}/stats`, {
            method: 'GET',
            headers: {
                //TODO add token
                "authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjBOU3BHZjBETVFBcjgtQnd1eVJ0cyJ9.eyJpc3MiOiJodHRwczovL2Rldi16c3l4d3hxeWRheDFzamR3LnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJrQVRvRm5JTjNSNjl0TVo1bG1iTmN6ZXhxOU9vVWJhc0BjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9ncm9jZXJpZXNBUEkuZXhhbXBsZS5jb20iLCJpYXQiOjE2NzE0MDY4MDIsImV4cCI6MTY3MTQ5MzIwMiwiYXpwIjoia0FUb0ZuSU4zUjY5dE1aNWxtYk5jemV4cTlPb1ViYXMiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.suMppPq-BqZeTXZGSLtKDdwuWjuGRaL0-vRkBY0wjhTsdy53U6B9dBuYi2fqqNY2MpuyADf6a9XsS3qdC2TmceCd_Q7JE3kiOu6_tV_NalFqsJhsDPbZzCR2suauew7grgCGFyNqBuPdJryr94i7Okp5sE1jgR_IkzShoboaR9ePcDqvCmUvhfJc8C9PvnA1nYdEI36xE-O8bQWX7mnb8REH5P-x3QxsLyDhXLIdhmho8hkwNeil9LdwtUqAZfycaipqKe3TtxEDRaOnesI6DGSlivlnUjXReNDdsCPOZgIhnjxoAINSrNdSx4WoIqga8qHBj3eyhknlixlTg7VXCw"
            },
        });
        const data = await response.json();
        console.log(data)
        return data;
    } catch {
        return
    }
}

const display_stats = async (stats) => {
    console.log(stats);
    return
    // if (stats.size == 0) {
    //     zeroResultsDiv.classList.remove("d-none");
    //     searchResultsDiv.append(zeroResultsDiv)
    //     return
    // }
    // zeroResultsDiv.classList.add("d-none");
    // searchResultsDiv.append(searchResultsColumns)
    // for (let i in stats){
    //     const statDiv = document.createElement("div");

    //     const stat = stats[i]
    //     statDiv.innerHTML = stat;
    //     // rowDiv.classList.add("row", "py-2", "mx-auto", "w-50");
    //     searchResultsDiv.append(statDiv);
    // }
}

searchBtn.addEventListener('click',async ()=> {
    const searchInput = document.getElementById('search-input');
    const category = searchInput.value;
    const stats = await clientStats(category);
    // console.log(stats);
    searchResultsDiv.innerHTML = ""
    display_stats(stats)
    searchInput.value = ""
})
