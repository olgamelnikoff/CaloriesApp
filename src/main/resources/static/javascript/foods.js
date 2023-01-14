//Cookie
const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

//Modal Elements
let foodName = document.getElementById('food-name')
let foodCalories = document.getElementById('food-calories')
let foodDescription = document.getElementById('food-description')

let mealIdInStorage = sessionStorage.getItem("mealIdInStorage");

const headers = {
    'Content-Type': 'application/json'/**/
}

const baseUrl = "http://localhost:8080/api/v1/food/"

function handleLogout(){
    let c = document.cookie.split(";");
    for (let i in c) {
        document.cookie = /^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
    }
}

let tab = document.getElementsByTagName("tbody")[0];

window.onload = function() {
    fullTable();
}

async function addFoodToMeal(mealIdInStorage, foodId){
    await fetch(`http://localhost:8080/api/v1/food/meal/${mealIdInStorage}/${foodId}`, {
        method: "POST",
        headers: headers
    })
        .catch(err => console.error(err.message))
    sessionStorage.removeItem(mealIdInStorage)
}

async function handleSubmitOne(foodId) {
    sessionStorage.setItem("foodIdInStorage", foodId);
    await fetch(`http://localhost:8080/api/v1/food/user/${userId}/${foodId}`, {
        method: "POST",
        headers: headers
    })
        .catch(err => console.error(err.message))
}

let fullTable = function(data) {
        let xhttp = new XMLHttpRequest();

        xhttp.onreadystatechange = function() {
            if (xhttp.readyState == 4 && xhttp.status == 200) {
                let allFoods = JSON.parse(xhttp.responseText);
                for (let i = 0; i < allFoods.length; i++) {
                    let singleObjectNew = allFoods[i];
                    let singleArrayNew = Object.values(singleObjectNew);
                    let foodId = singleArrayNew[0];
                    let foodName = singleArrayNew[1];
                    let foodCalories = singleArrayNew[2];
                    let foodDescription = singleArrayNew[4];
                    let newRowNew = tab.insertRow(tab.rows.length - 1);

                        for (let j = 0; j < 6; j++) {
                            let cellNew = newRowNew.insertCell(j);
                            if (j == 0) {
                                cellNew.innerText = foodName;
                            }
                            else if (j == 1) {
                                cellNew.innerText = foodCalories;
                            }
                            else if (j == 2) {
                                cellNew.innerText = foodDescription;
                            }

                            else if (j == 3) {
                                cellNew.innerHTML= `<form id="create-new-meal-form">
                                          <a class="btn btn-success" href="./add_meal.html" id="create-new-meal-button" type="submit" onclick="handleSubmitOne(${foodId})">Create A New Meal</a></form></div>`
                            }

                            else if (j == 4) {
                                cellNew.innerHTML= `<form id="create-new-meal-form"><a class="btn btn-primary" href="./home.html" onclick="addFoodToMeal(mealIdInStorage, ${foodId})">Add to Current Meal</a></form></div>`
                            }

                            else if (j == 5) {
                                cellNew.innerHTML= `<button class="btn btn-warning" onclick="handleDelete(${foodId})">Delete</button>`
                            }
                        }
                }
            }
        }
        xhttp.open("GET", `${baseUrl}user/${userId}`);

        xhttp.send();

}

async function getFoodById(foodId){
    await fetch(baseUrl + foodId, {
        method: "GET",
        headers: headers
    })
        .then(res => res.json())
        .then(data => populateModal(data))
        .catch(err => console.error(err.message))
}

async function handleDelete(foodId) {
    await fetch (baseUrl + foodId, {
        method: "DELETE",
        headers: headers
    })
        .catch (err => console.error(err))
    location.reload()
}

const populateModal = (bodyObj) => {
    foodName.innerText = ''
    foodCalories.innerText = ''
    foodDescription.innerText = ''
    foodName.innerText = bodyObj.name
    foodCalories.innerText = bodyObj.calories
    foodDescription.innerText = bodyObj.description
}

window.onunload = function () {
    location.reload();
};

window.addEventListener("beforeunload", function () {
    location.reload();
})