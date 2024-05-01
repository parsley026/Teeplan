
function backToLogin(){
    window.location.href = "/WebApplication/loginScreen.html"; 
}
function showOptionsEvents() {
    var middlePanel = document.getElementById("middlePanel");
    middlePanel.innerHTML = `<div id="section_panel">
    <div onclick="goToAddEvents()" id="option_add">
        <div id="add_text">ADD</div>
        <div id="add_icon"></div>
    </div>
        <div onclick="goToSearchEvents()" id="option_search">
            <div id="search_text">FIND</div>
            <div id="search_icon"></div>
        </div>
</div>`;
}

function goToSearchEvents() {
    window.location.href = "/WebApplication/searchEvents.html"; // Adres strony z wyszukiwaniem
}

function goToAddEvents() {
    window.location.href = "/WebApplication/addEvents.html"; // Adres strony z dodawaniem
}
function showOptionsCoupon() {
    var middlePanel = document.getElementById("middlePanel");
    middlePanel.innerHTML = ` <div id="section_panel">
    <div onclick="goToAddCoupon()" id="option_add">
        <div id="add_text">ADD</div>
        <div id="add_icon"></div>
    </div>
        <div onclick="goToSearchCoupon()" id="option_search">
            <div id="search_text">FIND</div>
            <div id="search_icon"></div>
        </div>
</div>`;

}

function goToSearchCoupon() {
    window.location.href = "/WebApplication/searchCoupon.html"; // Adres strony z wyszukiwaniem
}

function goToAddCoupon() {
    window.location.href = "/WebApplication/addCoupon.html"; // Adres strony z dodawaniem
}