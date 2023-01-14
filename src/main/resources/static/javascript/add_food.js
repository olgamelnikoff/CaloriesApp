//Cookie
const cookieArr = document.cookie.split("=")
const userId = cookieArr[1];

//DOM Elements
const submitForm = document.getElementById ("food-form")

const headers = {
    'Content-Type': 'application/json'
}

const baseUrl = "http://localhost:8080/api/v1/food/"

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
        calories: document.getElementById("enter-calories").value,
        description: document.getElementById("enter-description").value
    }

    await addFood(bodyObj);
    document.getElementById("enter-name").value = ''
    document.getElementById("enter-calories").value = ''
    document.getElementById("enter-description").value = ''
}

async function addFood(obj) {
    const response = await fetch (`${baseUrl}user/${userId}`, {
        method: "POST",
        body: JSON.stringify(obj),
        headers: headers
    })
        .catch(err => console.error(err.message))

}

submitForm.addEventListener("submit", handleSubmit)

