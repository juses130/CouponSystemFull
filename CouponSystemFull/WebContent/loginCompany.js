
function login() {
	var compName = document.getElementById("compName").value;
	var password = document.getElementById("password").value;

var request = new XMLHttpRequest;
	request.onreadystatechange = function() {
		if(this.readyState == 4 && this.status == 200) {
			var login = JSON.parse(this.responseText);
			
			if(login.login == "false") {
				document.getElementById("loginMessage").innerHTML = "Login Failed!";
			} // if - login was false
			else {
				document.getElementById("loginMessage").innerHTML = "Login OK";
				window.location = "companyPage.html";
			} // else - login was true
			
		} // if - response was OK
	};// function
	
	request.open("GET", "companyLogin/" + compName + "/"
			+ password);
	request.send();
} // LOGIN 
