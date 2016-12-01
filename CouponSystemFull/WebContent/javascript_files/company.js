/*/*************************
 * Function Here
 **************************/
//===================================
function createCoupon_page() {
	$(document).ready(function() {
		$('#content').load('html_files/company_html/createCoupon.html');
	});
} // createCoupon_page()

function createCoupon() {
	var xhttp = new XMLHttpRequest();
	var title = document.getElementById("title").value;
	var stDay = document.getElementById("stDay").value;
	var stMonth = document.getElementById("stMonth").value;
	var stYear = document.getElementById("stYear").value;
	var enDay = document.getElementById("enDay").value;
	var enMonth = document.getElementById("enMonth").value;
	var enYear = document.getElementById("enYear").value;
	var amount = document.getElementById("amount").value;
	var category = document.getElementById("category").value;
	var message = document.getElementById("message").value;
	var price = document.getElementById("price").value;
	var image = document.getElementById("image").value;
	
	var url = "./rest/companyService/createCoupon/";
	var response = "";
 
	// Check if the fields are empty
	if(valueChecker(title) == true 
			&& valueChecker(stDay) == true
			&& valueChecker(stMonth) == true
			&& valueChecker(stYear) == true
			&& valueChecker(enDay) == true
			&& valueChecker(enMonth) == true
			&& valueChecker(enYear) == true
			&& valueChecker(amount) == true
			&& valueChecker(category) == true
			&& valueChecker(message) == true
			&& valueChecker(price) == true
			&& valueChecker(image) == true) {
		
			xhttp.onreadystatechange = function() {
				// if the connection was fine 
				if (xhttp.readyState == 4) {
					// load the form page (turn it on)
					turnOnFormPage();
					// if status was OK
					if (xhttp.status == 200) {
						response = JSON.parse(this.responseText);		
						createMessageObjectEdited(response, 'resultPopup', 'created', 'coupon');
					} // if - xhttp
					else {
						response = JSON.parse(this.responseText);
						createResponseMessageResult(response, 'resultPopup');
					} // else - xhttp
			} // if - readyState
	};
	xhttp.open("POST", url 
			+ title + "/" 
			+ stDay + "/" 
			+ stMonth + "/" 
			+ stYear + "/" 
			+ enDay + "/"
			+ enMonth + "/"
			+ enYear + "/"
			+ amount + "/"
			+ category + "/"
			+ message + "/"
			+ price + "/"
			+ image);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.send();
	} // if - valueChecker
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
} // createCompany
//===================================

function removeCouponByID_page() {
	$(document).ready(function() {
		$('#content').load('html_files/company_html/removeCouponByID.html');
	});
} // removeCompanyByID_page(

function removeCouponByID() {
	var xhttp = new XMLHttpRequest();
	var coupID = document.getElementById("coupID").value;
	var url = "./rest/companyService/removeCouponByID/";
	var response = "";
	
	// Check if the fields are empty
	if(valueChecker(coupID) == true) {
		
		xhttp.onreadystatechange = function() {
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
	}
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
} // removeCouponByID
//===================================
function getCouponByID_page() {
	$(document).ready(function() {
		$('#content').load('html_files/company_html/getCouponByID.html');
	});
} // getCouponByID_page

function getCouponByID() {

	var xhttp = new XMLHttpRequest();
	var coupID = document.getElementById("coupID").value;
	var url = "./rest/companyService/getCouponByID/";
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
function updateCoupon_page() {
	$(document).ready(function() {
		$('#content').load('html_files/company_html/updateCoupon.html');
	});
} // updateCoupon_page()

function updateCoupon() {
	var xhttp = new XMLHttpRequest();
	var coupID = document.getElementById("coupID").value;
	var enDay = document.getElementById("enDay").value;
	var enMonth = document.getElementById("enMonth").value;
	var enYear = document.getElementById("enYear").value;
	var amount = document.getElementById("amount").value;
	var message = document.getElementById("message").value;
	var price = document.getElementById("price").value;
	var url = "./rest/companyService/updateCoupon/";
	var response = "";

	// Check if the fields are empty
	if(valueChecker(coupID) == true 
			&& valueChecker(enDay) == true
			&& valueChecker(enMonth) == true
			&& valueChecker(enYear) == true
			&& valueChecker(amount) == true
			&& valueChecker(message) == true
			&& valueChecker(price) == true) {
	
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4) {
				// load the form page (turn it on)
				turnOnFormPage();
				if (xhttp.status == 200) {
					response = JSON.parse(this.responseText);
					createMessageObjectEdited(response, 'resultPopup', 'updated', 'coupon');
				} // if - status xhttp
				else {
					response = JSON.parse(this.responseText);
					createResponseMessageResult(response, 'resultPopup');
				} // else - status xhttp
			} // if - readyState
		};
		xhttp.open("POST", url 
				+ coupID + '/' 
				+ enDay + '/' 
				+ enMonth + '/'
				+ enYear + '/'
				+ amount + '/'
				+ message + '/'
				+ price);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	} // if - valueChecker	
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
}// updateCoupon
//===================================

// PAY ATTENTION - this method working Conversely (not like the others here).
function viewCompany_page() {
	$(document).ready(function() {
		$('#content').load('html_files/company_html/viewCompany.html');
	});
	
	$(document).ready(function() {
		viewCompany();
	});
}

function viewCompany() {

	var xhttp = new XMLHttpRequest();
	var url = "./rest/companyService/viewCompany/";
	var response = "";

		xhttp.onreadystatechange = function() {
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
		xhttp.open("GET", url);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
} // viewCompany 
//===================================

function getCouponsByPrice_page() {
	$(document).ready(function() {
		$('#content').load('html_files/company_html/getCouponsByPrice.html');
	});
} // getAllCoupons_page
 
function getCouponsByPrice() {
	var xhttp = new XMLHttpRequest();
	var maxPrice = document.getElementById('maxPrice').value;
	var url = "./rest/companyService/getCouponsByPrice/";
	var response = "";
//
	// Check if the fields are empty
	if(valueChecker(maxPrice) == true) {
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
	} // if - valueChecker
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
	
} // getCouponsByPrice
//===================================
function getCouponsByType_page() {
	$(document).ready(function() {
		$('#content').load('html_files/company_html/getCouponsByType.html');
	});
} // getAllCoupons_page
 
function getCouponsByType() {
	var xhttp = new XMLHttpRequest();
	var category = document.getElementById('category').value;
	var url = "./rest/companyService/getCouponsByType/";
	var response = "";
//
	// Check if the fields are empty
	if(valueChecker(category) == true) {
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
			} 
		};
		xhttp.open("POST", url + category);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();
	} // if - valueChecker
	else {
		alert("One or more of the fileds are Empty!");
	} // else - valueChecker
	
} // getCouponsByPrice
//===================================

//PAY ATTENTION - this method working Conversely (not like the others here).
function getAllCoupons_page() {
	// load the page into the #content
	$(document).ready(function() {
		$('#content').load('html_files/company_html/getAllCoupons.html');
	});
	// run the function
	$(document).ready(function() {
		getAllCoupons();
	});
} // getAllCoupons_page
 
function getAllCoupons() {
	var xhttp = new XMLHttpRequest();
	var url = "./rest/companyService/getAllCoupons/";
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


