//Cookie
const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

//DOM Elements
const submitForm = document.getElementById ("food-form")

var foodIdInStorage = null


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

const handleSubmit = async (e) => {
    e.preventDefault()
    let bodyObj = {
        name: document.getElementById("enter-name").value,
        description: document.getElementById("enter-description").value
    }

    await addMeal(bodyObj);
    document.getElementById("enter-name").value = ''
    document.getElementById("enter-description").value = ''
    sessionStorage.removeItem("foodIdInStorage")
}

async function addMeal(obj) {
    foodIdInStorage = sessionStorage.getItem("foodIdInStorage");
    if (foodIdInStorage) {
        const response = await fetch(`http://localhost:8080/api/v1/food/user/${userId}/${foodIdInStorage}`, {
            method: "POST",
            body: JSON.stringify(obj),
            headers: headers
        }).then(res => res.json())
            .catch(err => console.error(err.message))
    }

    else {
            const response = await fetch(`http://localhost:8080/api/v1/meal/user/${userId}`, {
                method: "POST",
                body: JSON.stringify(obj),
                headers: headers
            }).then(res => res.json())
                .catch(err => console.error(err.message))
        }
}

submitForm.addEventListener("submit", handleSubmit)


