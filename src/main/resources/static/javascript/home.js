//Cookie
const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

//DOM Elements
const foodContainer = document.getElementById("food-container")

let tab = document.getElementsByTagName("tbody")[0];

const headers = {
    'Content-Type': 'application/json'
}

const baseUrl = "http://localhost:8080/api/v1/meal/"

function handleLogout(){
    let c = document.cookie.split(";");
    for (let i in c) {
        document.cookie = /^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 1970 00:00:00 GMT"
    }
}

let addFoodToMeal = async function addFood(mealId, foodId){
    await fetch(`http://localhost:8080/api/v1/food/meal/${mealId}/${foodId}`, {
        method: "POST",
        headers: headers
    })
        .then(res => res.json())
        .then(data => populateModal(data))
        .catch(err => console.error(err.message))
}

async function getMeals(userId) {
    await fetch(`${baseUrl}user/${userId}`, {
        method: "GET",
        headers: headers

    })
        .then(response => response.json())
        .then(data => createMealCards(data))
        .catch(err => console.error(err))
}

const createMealCards = (array) => {
    foodContainer.innerHTML = ''
    array.forEach(obj => {
        var mealId = obj.id
        let nameOnCard = obj.name;
        let description = obj.description;
        let foodCard = document.createElement("div")
        foodCard.classList.add("m-2")
        foodCard.innerHTML = `
        <div class="card d-flex" style="width: 18rem; height: 18rem;">
            <div class="card-body d-flex flex-column justify-content-between" style="height:available">
                <p class="card-text">${nameOnCard}</p>
                <p class="card-text">${description}</p>
                <div class="d-flex justify-content-between">
                    <button class="btn btn-danger" onclick="handleDelete(${obj.id})">Delete</button>
                    <button onclick="getMealById(${mealId})" type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#food-edit-modal">View/Edit</button>
                </div>
            </div>
        </div>
        `
        foodContainer.append(foodCard);
    })
}

async function getMealById(mealId){
    await fetch(baseUrl + mealId, {
        method: "GET",
        headers: headers
    })
        .then(res => res.json())
        .then(data => populateModal(data))
        .catch(err => console.error(err.message))
}


async function handleDelete(mealId) {
    await fetch (baseUrl + mealId, {
        method: "DELETE",
        headers: headers
    })
        .catch (err => console.error(err))
    window.location.reload()
}

async function handleRemove(mealId, foodId) {
    await fetch (`http://localhost:8080/api/v1/food/meal/${mealId}/${foodId}`, {
        method: "DELETE",
        headers: headers
    }).then (window.location.reload())
        .catch (err => console.error(err))
    window.alert("Food removed")
}

const populateModal = (data) => {
    let totalCalories=0;
    let mealId = data.id
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {

        var arrayOfFood = data["foodList"];
        sessionStorage.setItem("mealIdInStorage", mealId);
        for (let i = 0; i < arrayOfFood.length; ++i) {
            let thisFood = arrayOfFood[i];
            let thisFoodId = thisFood.id;
            let thisFoodName = thisFood.name;
            let thisFoodCalories = thisFood.calories;
            totalCalories+=thisFoodCalories
            let thisFoodDescription = thisFood.description;
            let newRowNew = tab.insertRow(tab.rows.length - 1);
            for (let j = 0; j < 5; j++) {
                let cellNew = newRowNew.insertCell(j);
                if (j == 0) {
                    cellNew.innerText = thisFoodName;
                }
                if (j == 1) {
                    cellNew.innerText = thisFoodCalories;
                }
                if (j == 2) {
                    cellNew.innerText = thisFoodDescription;
                }
                if (j == 3) {
                    cellNew.innerHTML= `
                                    <button class="btn btn-warning" onclick="handleRemove(${mealId},${thisFoodId})">Remove</button>`

                }
            }
        }
            let newRowNew = tab.insertRow();
            let cellNewOne = newRowNew.insertCell(0);
            cellNewOne.innerHTML= `<h6><b>Total calories in this meal:</b></h6>`
            let cellNewTwo = newRowNew.insertCell(1);
            cellNewTwo.innerHTML= `<h6><b>${totalCalories}</b></h6>`

        }
    }

    xhttp.open("GET", `${baseUrl}${mealId}`);

    xhttp.send();
}

getMeals(userId);
