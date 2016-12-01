/*/*************************
 * Company Part is Here
 **************************/
//===================================
function createCompany_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/createCompany.html');
	});
} // createCompany_page()

function createCompany() {
	var xhttp = new XMLHttpRequest();
	var compName = document.getElementById("compName").value;
	var password = document.getElementById("password").value;
	var email = document.getElementById("email").value;
	var url = "./rest/adminService/createCompany/";
	var response = "";
	
	// Check if the fields are empty
	if(valueChecker(compName) == true 
			&& valueChecker(password) == true
			&& valueChecker(email) == true) {
		
			xhttp.onreadystatechange = function() {
				// if the connection was fine 
				if (xhttp.readyState == 4) {
					// load the form page (turn it on)
					turnOnFormPage();
					// if status was OK
					if (xhttp.status == 200) {
						response = JSON.parse(this.responseText);		
						createMessageObjectEdited(response, 'resultPopup', 'created', 'company');
					} // if - xhttp
					else {
						response = JSON.parse(this.responseText);
						createResponseMessageResult(response, 'resultPopup');
					} // else - xhttp
			} // if - readyState
	};
	xhttp.open("POST", url + compName + "/" + password + "/" + email);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.send();
	} // if - valueChecker
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
} // createCompany
//===================================

function removeCompanyByID_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/removeCompanyByID.html');
	});
} // removeCompanyByID_page(

function removeCompanyByID() {
	var xhttp = new XMLHttpRequest();
	var compID = document.getElementById("compID").value;
	var url = "./rest/adminService/removeCompanyByID/";
	var response = "";
	
	// Check if the fields are empty
	if(valueChecker(compID) == true) {
		
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4) {
				// load the form page (turn it on)
				turnOnFormPage();
				if (xhttp.status == 200) {
					response = JSON.parse(this.responseText);
					createMessageObjectEdited(response, 'resultPopup', 'removed', 'company');
				} // if - status xhttp
				else {
					response = JSON.parse(this.responseText);
					createResponseMessageResult(response, 'resultPopup');
				} // else - status xhttp
			} // if - readyState
		};
		xhttp.open("POST", url + compID);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	}
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
} // removeCompanyByID
//===================================

function removeCompanyByName_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/removeCompanyByName.html');
	});
} // removeCompanyByID()

function removeCompanyByName() {
	var xhttp = new XMLHttpRequest();
	var compName = document.getElementById("compName").value;
	var url = "./rest/adminService/removeCompanyByName/";
	var response = "";
	
	// Check if the fields are empty
	if(valueChecker(compName) == true) {
		
				xhttp.onreadystatechange = function() {
					if (xhttp.readyState == 4) {
						// load the form page (turn it on)
						turnOnFormPage();
						if (xhttp.status == 200) {
							response = JSON.parse(this.responseText);
							createMessageObjectEdited(response, 'resultPopup', 'removed', 'company');
						} // if - status xhttp
						else {
							response = JSON.parse(this.responseText);
							createResponseMessageResult(response, 'resultPopup');
						} // else - status xhttp
					} // if - readyState
				};
				xhttp.open("POST", url + compName);
				xhttp.setRequestHeader("Content-type", "application/json");
				xhttp.send();
	} // if - valueChecker	
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
} // removeCompanyByName
//===================================

function updateCompany_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/updateCompany.html');
	});
} // removeCompanyByName()

function updateCompany() {
	var xhttp = new XMLHttpRequest();
	var compID = document.getElementById("compID").value;
	var password = document.getElementById("password").value;
	var email = document.getElementById("email").value;
	var url = "./rest/adminService/updateCompany/";
	var response = "";

	// Check if the fields are empty
	if(valueChecker(compID) == true 
			&& valueChecker(password) == true
			&& valueChecker(email) == true) {
	
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4) {
				// load the form page (turn it on)
				turnOnFormPage();
				if (xhttp.status == 200) {
					response = JSON.parse(this.responseText);
					createMessageObjectEdited(response, 'resultPopup', 'updated', 'company');
				} // if - status xhttp
				else {
					response = JSON.parse(this.responseText);
					createResponseMessageResult(response, 'resultPopup');
				} // else - status xhttp
			} // if - readyState
		};
		xhttp.open("POST", url + compID + '/' + password + '/' + email);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	} // if - valueChecker	
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
}// updateCompany
//===================================

function getCompanyByID_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/getCompanyByID.html');
	});
} // getCompanyByID_page()

function getCompanyByID() {

	var xhttp = new XMLHttpRequest();
	var compID = document.getElementById("compID").value;
	var url = "./rest/adminService/getCompanyByID/";
	var response = "";
	
	// Check if the fields are empty
	if(valueChecker(compID) == true) {
		xhttp.onreadystatechange = function() {
			// load the form page (turn it on)
			turnOnFormPage();
			if (xhttp.readyState == 4) {
				// load the form page (turn it on)
				turnOnFormPage();
				if (xhttp.status == 200) {
					response = JSON.parse(this.responseText);
					createMessageObjectEdited(response, 'resultPopup', '', 'company');
				} // if - status xhttp
				else {
					response = JSON.parse(this.responseText);
					createResponseMessageResult(response, 'resultPopup');
				} // else - status xhttp
			} // if - readyState
		};
		xhttp.open("POST", url + compID);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	} // if - valueChecker
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
} // getCompanyByID 
//===================================

function getCompanyByName_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/getCompanyByName.html');
	});
} // getCompanyByName_page()

function getCompanyByName() {
	var xhttp = new XMLHttpRequest();
	var compName = document.getElementById("compName").value;
	var url = "./rest/adminService/getCompanyByName/";
	var response = "";

	// Check if the fields are empty
	if(valueChecker(compName) == true) {
		
		xhttp.onreadystatechange = function() {
			// if the connection was fine
			if (xhttp.readyState == 4) {
				// load the form page (turn it on)
				turnOnFormPage();
				if (xhttp.status == 200) {
					response = JSON.parse(this.responseText);
					createMessageObjectEdited(response, 'resultPopup', '', 'company');
				} // if - status xhttp
				else {
					response = JSON.parse(this.responseText);
					createResponseMessageResult(response, 'resultPopup');
				} // else
			} // if
		};
		xhttp.open("POST", url + compName);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	} // if - valueChecker
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
} // getCompanyByName
//===================================

function getAllCompanies_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/getAllCompanies.html');
	});
	// run the method
	$(document).ready(function() {
		getAllCompanies();
	});
} // getAllCompanies_page()

function getAllCompanies() {
	var xhttp = new XMLHttpRequest();
	var url = "./rest/adminService/getAllCompanies/";
	var response = "";

	xhttp.onreadystatechange = function() {
		// if the connection was fine
		if (xhttp.readyState == 4) {
			// load the form page (turn it on)
			turnOnFormPage();
			response = JSON.parse(this.responseText);
			
			// if the status was 200 (OK)
			if (xhttp.status == 200) {
				// craete table with the JSON results
				createTableResult(response, 'resultPopup', 'company');
			} // if - status xhttp
			else {
				response = JSON.parse(this.responseText);
				createResponseMessageResult(response, 'resultPopup');
			} // else
		} // if
	};
	xhttp.open("GET", url);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.send();
} // getAllCompanies
//===================================
/*/*************************
 * Customer Part is Here
 **************************/
//===================================

function createCustomer_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/createCustomer.html');
	});
} // createCustomer_page

function createCustomer() {
	var xhttp = new XMLHttpRequest();
	var custName = document.getElementById("custName").value;
	var password = document.getElementById("password").value;
	var url = "./rest/adminService/createCustomer/";
	var response = "";
	
	// Check if the fields are empty
	if(valueChecker(custName) == true 
			&& valueChecker(password) == true) {
		
			xhttp.onreadystatechange = function() {
				// if the connection was fine 
				if (xhttp.readyState == 4) {
					// load the form page (turn it on)
					turnOnFormPage();
					// if status was OK
					if (xhttp.status == 200) {
						response = JSON.parse(this.responseText);		
						createMessageObjectEdited(response, 'resultPopup', 'created', 'customer');
					} // if - xhttp
					else {
						response = JSON.parse(this.responseText);
						createResponseMessageResult(response, 'resultPopup');
					} // else - xhttp
			} // if - readyState
	};
	xhttp.open("POST", url + custName + "/" + password);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.send();
	} // if - valueChecker
	else {
		alert("One or more of the fileds are Empty!");
	}// else - valueChecker
} // createCompany

//===================================

function removeCustomerByID_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/removeCustomerByID.html');
	});
} // removeCustomerByID_page()

function removeCustomerByID() {
	var xhttp = new XMLHttpRequest();
	var custID = document.getElementById("custID").value;
	var url = "./rest/adminService/removeCustomerByID/";
	var response = "";
	
	// Check if the fields are empty
	if(valueChecker(custID) == true) {
		
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4) {
				// load the form page (turn it on)
				turnOnFormPage();
				if (xhttp.status == 200) {
					response = JSON.parse(this.responseText);
					createMessageObjectEdited(response, 'resultPopup', 'removed', 'Customer');
				} // if - status xhttp
				else {
					response = JSON.parse(this.responseText);
					createResponseMessageResult(response, 'resultPopup');
				} // else - status xhttp
			} // if - readyState
		};
		xhttp.open("POST", url + custID);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	}
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
} // removeCustomerByID
//===================================

function removeCustomerByName_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/removeCustomerByName.html');
	});
} // removeCustomerByName_page()

function removeCustomerByName() {
	var xhttp = new XMLHttpRequest();
	var custName = document.getElementById("custName").value;
	var url = "./rest/adminService/removeCustomerByName/";
	var response = "";
	
	// Check if the fields are empty
	if(valueChecker(custName) == true) {
		
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4) {
				// load the form page (turn it on)
				turnOnFormPage();
				if (xhttp.status == 200) {
					response = JSON.parse(this.responseText);
					createMessageObjectEdited(response, 'resultPopup', 'removed', 'Customer');
				} // if - status xhttp
				else {
					response = JSON.parse(this.responseText);
					createResponseMessageResult(response, 'resultPopup');
				} // else - status xhttp
			} // if - readyState
		};
		xhttp.open("POST", url + custName);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	}
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
} // removeCustomerByName
//===================================
function updateCustomer_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/updateCustomer.html');
	});
} // removeCustomerByName_page

function updateCustomer() {
	var xhttp = new XMLHttpRequest();
	var custID = document.getElementById("custID").value;
	var password = document.getElementById("password").value;
	var custName = document.getElementById("custName").value;
	var url = "./rest/adminService/updateCustomer/";
	var response = "";

	// Check if the fields are empty
	if(valueChecker(custID) == true 
			&& valueChecker(password) == true
			&& valueChecker(custName) == true) {
	
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4) {
				// load the form page (turn it on)
				turnOnFormPage();
				if (xhttp.status == 200) {
					response = JSON.parse(this.responseText);
					createMessageObjectEdited(response, 'resultPopup', 'updated', 'customer');
				} // if - status xhttp
				else {
					response = JSON.parse(this.responseText);
					createResponseMessageResult(response, 'resultPopup');
				} // else - status xhttp
			} // if - readyState
		};
		xhttp.open("POST", url + custID + '/' + password + '/' + custName);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	} // if - valueChecker	
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
} // removeCustomerByID
//===================================

function getCustomerByID_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/getCustomerByID.html');
	});
} // getCustomerByID_page

function getCustomerByID() {

	var xhttp = new XMLHttpRequest();
	var custID = document.getElementById("custID").value;
	var url = "./rest/adminService/getCustomerByID/";
	var response = "";
	
	// Check if the fields are empty
	if(valueChecker(custID) == true) {
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4) {
				// load the form page (turn it on)
				turnOnFormPage();
				if (xhttp.status == 200) {
					response = JSON.parse(this.responseText);
					createMessageObjectEdited(response, 'resultPopup', '', 'customer');
				} // if - status xhttp
				else {
					response = JSON.parse(this.responseText);
					createResponseMessageResult(response, 'resultPopup');
				} // else - status xhttp
			} // if - readyState
		};
		xhttp.open("POST", url + custID);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	} // if - valueChecker
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
} // getCustomerByID 
//===================================

function getCustomerByName_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/getCustomerByName.html');
	});
} // getCustomerByName_page

function getCustomerByName() {

	var xhttp = new XMLHttpRequest();
	var custName = document.getElementById("custName").value;
	var url = "./rest/adminService/getCustomerByName/";
	var response = "";
	
	// Check if the fields are empty
	if(valueChecker(custName) == true) {
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4) {
				// load the form page (turn it on)
				turnOnFormPage();
				if (xhttp.status == 200) {
					response = JSON.parse(this.responseText);
					createMessageObjectEdited(response, 'resultPopup', '', 'customer');
				} // if - status xhttp
				else {
					response = JSON.parse(this.responseText);
					createResponseMessageResult(response, 'resultPopup');
				} // else - status xhttp
			} // if - readyState
		};
		xhttp.open("POST", url + custName);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	} // if - valueChecker
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
} // getCustomerByName 
//===================================
function getAllCustomers_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/getAllCustomers.html');
	});
	// load the function
	$(document).ready(function() {
		getAllCustomers();
	});
} // getAllCustomers_page()

function getAllCustomers() {
	var xhttp = new XMLHttpRequest();
	var url = "./rest/adminService/getAllCustomers/";
	var response = "";

	xhttp.onreadystatechange = function() {
		// if the connection was fine
		if (xhttp.readyState == 4) {
			// load the form page (turn it on)
			turnOnFormPage();
			
			// if the status was 200 (OK)
			if (xhttp.status == 200) {
				response = JSON.parse(this.responseText);
				createTableResult(response, 'resultPopup', 'customer');
			} // if - status xhttp
			else {
				response = JSON.parse(this.responseText);
				createResponseMessageResult(response, 'resultPopup');
			} // else
		} // if
	};
	xhttp.open("GET", url);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.send();
}
//===================================

/*/*************************
 * Coupon Part is Here
 **************************/

//===================================
function getCouponByID_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/getCouponByID.html');
	});
} // getCouponByID_page

function getCouponByID() {

	var xhttp = new XMLHttpRequest();
	var coupID = document.getElementById("coupID").value;
	var url = "./rest/adminService/getCouponByID/";
	var response = "";
	
	// Check if the fields are empty
	if(valueChecker(coupID) == true) {
		xhttp.onreadystatechange = function() {
			// load the form page (turn it on)
			turnOnFormPage();
			if (xhttp.readyState == 4) {
				// load the form page (turn it on)
				turnOnFormPage();
				if (xhttp.status == 200) {
					response = JSON.parse(this.responseText);
					createMessageObjectEdited(response, 'resultPopup', '', 'coupon');
				} // if - status xhttp
				else {
					response = JSON.parse(this.responseText);
					createResponseMessageResult(response, 'resultPopup');
				} // else - status xhttp
			} // if - readyState
		};
		xhttp.open("POST", url + coupID);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	} // if - valueChecker
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
} // getCouponByID 
//===================================
function removeCouponByID_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/removeCouponByID.html');
	});
} // removeCouponByID_page

function removeCouponByID() {

	var xhttp = new XMLHttpRequest();
	var coupID = document.getElementById("coupID").value;
	var url = "./rest/adminService/removeCouponByID/";
	var response = "";
	
	// Check if the fields are empty
	if(valueChecker(coupID) == true) {
		xhttp.onreadystatechange = function() {
			// load the form page (turn it on)
			turnOnFormPage();
			if (xhttp.readyState == 4) {
				// load the form page (turn it on)
				turnOnFormPage();
				if (xhttp.status == 200) {
					response = JSON.parse(this.responseText);
					createMessageObjectEdited(response, 'resultPopup', 'removed', 'coupon');
				} // if - status xhttp
				else {
					response = JSON.parse(this.responseText);
					createResponseMessageResult(response, 'resultPopup');
				} // else - status xhttp
			} // if - readyState
		};
		xhttp.open("POST", url + coupID);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	} // if - valueChecker
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
} // removeCouponByID 
//===================================
function getCouponsByPrice_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/getCouponsByPrice.html');
	});
} // getAllCoupons_page
 
function getCouponsByPrice() {
	var xhttp = new XMLHttpRequest();
	var maxPrice = document.getElementById('maxPrice').value;
	var url = "./rest/adminService/getCouponsByPrice/";
	var response = "";
//
	xhttp.onreadystatechange = function() {
		// if the connection was fine
		if (xhttp.readyState == 4) {
			// load the form page (turn it on)
			turnOnFormPage();
			// if the status was 200 (OK)
			if (xhttp.status == 200) {
				response = JSON.parse(this.responseText);
				createTableResult(response, 'resultPopup', 'Coupon');
			} // if - status xhttp
			else {
				response = JSON.parse(this.responseText);
				createResponseMessageResult(response, 'resultPopup');
			} // else
		} // if
	};
	xhttp.open("POST", url + maxPrice);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.send();
} // getCouponsByPrice
//===================================
function getCouponsByType_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/getCouponsByType.html');
	});
} // getAllCoupons_page
 
function getCouponsByType() {
	var xhttp = new XMLHttpRequest();
	var category = document.getElementById('category').value;
	var url = "./rest/adminService/getCouponsByType/";
	var response = "";
//
	xhttp.onreadystatechange = function() {
		// if the connection was fine
		if (xhttp.readyState == 4) {
			// load the form page (turn it on)
			turnOnFormPage();
			// if the status was 200 (OK)
			if (xhttp.status == 200) {
				response = JSON.parse(this.responseText);
				createTableResult(response, 'resultPopup', 'Coupon');
			} // if - status xhttp
			else {
				response = JSON.parse(this.responseText);
				createResponseMessageResult(response, 'resultPopup');
			} // else
		} // if
	};
	xhttp.open("POST", url + category);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.send();
} // getCouponsByPrice
//===================================
function getAllCoupons_page() {
	$(document).ready(function() {
		$('#content').load('html_files/admin_html/getAllCoupons.html');
	});
	// load the function
	$(document).ready(function() {
		getAllCoupons();
	});
} // getAllCoupons_page
 
function getAllCoupons() {
	var xhttp = new XMLHttpRequest();
	var url = "./rest/adminService/getAllCoupons/";
	var response = "";
//
	xhttp.onreadystatechange = function() {
		// if the connection was fine
		if (xhttp.readyState == 4) {
			// load the form page (turn it on)
			turnOnFormPage();
			// if the status was 200 (OK)
			if (xhttp.status == 200) {
				response = JSON.parse(this.responseText);
				createTableResult(response, 'resultPopup', 'Coupon');
			} // if - status xhttp
			else {
				response = JSON.parse(this.responseText);
				createResponseMessageResult(response, 'resultPopup');
			} // else
		} // if
	};
	xhttp.open("GET", url);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.send();
} // getAllCoupons
//===================================
/*/*************************
 * Other Methods
 **************************/

function valueChecker(variable) {
	if(variable.length > 0) {
		return true;
	}
	return false;
}


