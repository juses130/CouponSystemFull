/**
 * 
 */

function login() {
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;
	var radios = document.getElementsByName("clienttype");
	var pageToLoad = '';
	
	for (var i = 0, length = radios.length; i < length; i++) {
	    if (radios[i].checked) {
	        var client = radios[i].value;
	        break;
	    } // if
	} // for loop
	
	var xhttp = new XMLHttpRequest();
	var url = "./rest/";
	var response;
	
	if(client == "customer") {
		url = url + "customerService/customerLogin/";
		pageToLoad = 'customerPanel.html';
	} // if - customer
	else if(client == 'company') {
		url = url + "companyService/companyLogin/";
		pageToLoad = 'companyPanel.html';
	} // else if - company
	else if(client == 'admin') {
		url = url + "adminService/adminLogin/";
		pageToLoad = 'adminPanel.html';
	} // else if -admin
	
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4) {
			response = JSON.parse(this.responseText);
			if(xhttp.status == 200) {
				// load the relevant page per client
				window.location = pageToLoad;
		
			} // if
			else {
				alert(response['message']);
			} // else
		} // if readyState - 4
	}; // Function
	  xhttp.open("POST", url + username + "/" + password);
	  xhttp.send();
} // login


