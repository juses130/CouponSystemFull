/**
 * This is the JS for the mainAdmin page.
 */

// Login
function loginToService() {
    var xhttp = new XMLHttpRequest();
    var adminName = document.getElementById("adminName").value;
    var password = document.getElementById("password").value;
    var url = "http://localhost:8080/CouponSystemWeb/rest/adminService/adminLogin/";
    var response = "";

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            if (response.codeMessage == 200) {
                response = JSON.parse(this.responseText);
                alert(response.message);
                // load the page after success admin-login.
                load_adminPage();
            } // if

        } // if
        else {
            //	response = JSON.parse(this.responseText);
            alert("Error");
        } // else
    };

    xhttp.open("GET", url + adminName + "/" + password);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
} // loginToService

function load_adminPage() {
    $(document).ready(function () {
        $('#body').load('adminPanel.html');
    });
} // load_home

function createCompany() {
    $(document).ready(function () {
        $('#content').load('HtmlFiles/createCompany.html');
    });
    var xhttp = new XMLHttpRequest();
    var compName = document.getElementById("compName").value;
    var password = document.getElementById("password").value;
    var email = document.getElementById("email").value;
    var url = "http://localhost:8080/CouponSystemWeb/rest/adminService/createCompany/";
    var response = "";

}