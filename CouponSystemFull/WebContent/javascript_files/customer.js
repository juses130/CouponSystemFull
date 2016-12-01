/*/*************************
 * Function Here
 **************************/
//===================================
function couponsForPurchase_page() {
	// Load the html page of purchaseCouopon.
	$(document).ready(function() {
		$('#content').load('html_files/customer_html/purchaseCoupon.html');
	});
	// load the coupons are Available for purchasing.
	$(document).ready(function() {
		loadAvailableCoupons();
	});
	
} // createCoupon_page()

function loadAvailableCoupons() {
	var xhttp = new XMLHttpRequest();
	var url = "./rest/customerService/loadAvailableCoupons/";
	var response = "";		
			xhttp.onreadystatechange = function() {
				// if the connection was fine 
				if (xhttp.readyState == 4) {
					// load the form page (turn it on)
					// if status was OK
					if (xhttp.status == 200) {  
						turnOnFormPage();
						response = JSON.parse(this.responseText);
						createTableResult(response, 'resultPopup', 'CUSTOMER_PURCHASE');
					} // if - xhttp
					else {
						response = JSON.parse(this.responseText);
						createResponseMessageResult(response, 'resultPopup');
					} // else - xhttp
			} // if - readyState
	};
	xhttp.open("GET", url);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.send();
}

function purchaseCouponButton(coupID) {
	var xhttp = new XMLHttpRequest();
	var url = "./rest/customerService/purchaseCoupon/";
	var response = "";
		xhttp.onreadystatechange = function() {
			// if the connection was fine 
			if (xhttp.readyState == 4) {				
				// if status was OK
				if (xhttp.status == 200) {
					updateBtnTD('btn' + coupID);
				} // if - xhttp
				else {
					response = JSON.parse(this.responseText);
					alert(response['message']);
				} // else - xhttp
			} // if - readyState
		};
		xhttp.open("POST", url + coupID);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();		
} // purchaseCouponButton

//===================================
function getYourCoupons_page() {
	// Load the html page of purchaseCouopon.
	$(document).ready(function() {
		$('#content').load('html_files/customer_html/getYourCoupons.html');
	});
	// load all the coupons of the customer.
	$(document).ready(function() {
		getYourCoupons();
	});
	
} // createCoupon_page()

function getYourCoupons() {
	var xhttp = new XMLHttpRequest();
	var url = "./rest/customerService/getYourCoupons/";
	var response = "";
		xhttp.onreadystatechange = function() {
			// if the connection was fine 
			if (xhttp.readyState == 4) {
				turnOnFormPage();
				// if status was OK
				if (xhttp.status == 200) {
					response = JSON.parse(this.responseText);
					createTableResult(response, 'resultPopup', 'Coupon');
				} // if - xhttp
				else {
					response = JSON.parse(this.responseText);
					createResponseMessageResult(response, 'resultPopup');
				} // else - xhttp
			} // if - readyState
		};
		xhttp.open("GET", url);
		xhttp.setRequestHeader("Content-type", "application/json");
		xhttp.send();		
} // getYourCoupons

//===================================
function viewCustomer_page() {
	$(document).ready(function() {
		$('#content').load('html_files/customer_html/viewCustomer.html');
	});
	// load the data of the customer
	$(document).ready(function() {
		viewCustomer();
	});
} // viewCustomer_page

function viewCustomer() {
	var xhttp = new XMLHttpRequest();
	var url = "./rest/customerService/viewCustomer/";
	var response = "";		
			xhttp.onreadystatechange = function() {
				// if the connection was fine 
				if (xhttp.readyState == 4) {
					// load the form page (turn it on)
					turnOnFormPage();
					// if status was OK
					if (xhttp.status == 200) {   
						response = JSON.parse(this.responseText);		
						createMessageObjectEdited(response, 'resultPopup', '', 'customer');
					} // if - xhttp
					else {
						response = JSON.parse(this.responseText);
						createResponseMessageResult(response, 'resultPopup');
					} // else - xhttp
			} // if - readyState
	};
	xhttp.open("GET", url);
	xhttp.setRequestHeader("Content-type", "application/json");
	xhttp.send();
}
//===================================

function getCouponsByPrice_page() {
	$(document).ready(function() {
		$('#content').load('html_files/customer_html/getCouponsByPrice.html');
	});
} // getAllCoupons_page
 
function getCouponsByPrice() {
	var xhttp = new XMLHttpRequest();
	var maxPrice = document.getElementById('maxPrice').value;
	var url = "./rest/customerService/getCouponsByPrice/";
	var response = "";

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
		$('#content').load('html_files/customer_html/getCouponsByType.html');
	});
} // getAllCoupons_page
 
function getCouponsByType() {
	var xhttp = new XMLHttpRequest();
	var category = document.getElementById('category').value;
	var url = "./rest/customerService/getCouponsByType/";
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

function removeCouponByID_page() {
	$(document).ready(function() {
		$('#content').load('html_files/customer_html/removeCouponByID.html');
	});
} // removeCompanyByID_page(

function removeCouponByID() {
	var xhttp = new XMLHttpRequest();
	var coupID = document.getElementById("coupID").value;
	var url = "./rest/customerService/removeCouponByID/";
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
/*/*************************
 * Other Methods
 **************************/

function valueChecker(variable) {
	if(variable.length > 0) {
		return true;
	}
	return false;
} // valueChecker

function updateBtnTD(btnID) {
	$(document).ready(function() {
		var str = 'Purchased!';
		$("#" + btnID).html(str); 
	});
} // updateBtnTD



