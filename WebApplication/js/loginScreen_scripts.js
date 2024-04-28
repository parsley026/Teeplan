
function checkCredentials() {

        // Pobierz wartości wpisane przez użytkownika
        var email = document.getElementById("email_input").value;
        var password = document.getElementById("password_input").value;
        
        //Dodać walidacje z bazą danych

     if (email === "123" && password === "123") {
        window.location.href = "/WebApplication/screenAfterLogin.html";
    } else {
        alert("Nieprawidłowy email lub hasło. Spróbuj ponownie.");
    }
}