/*
 * This JavaScript file contains couple of methods. 
 * This methods will take an Array (mostly JSON) and Destination (#elementID). it will put the json objects into rows in table. 
 * and set it in #elementID destination (div for example). 
 * 
 * OR set a message if it's just one object (like getCompanyByID fn).
 */

/**
 * <b>Relevant to CompanyData Only!</b></br>
 * This function will get JSON-response {@String} and a Destination {#IDElement}. 
 * The function will create new table results (with style) in the location you will give it in the 'destionation'.
 * 
 * @param responseJSON - String JSON Array
 * @param destination - String (#id)
 * @returns a dynamic table in the <b>destination</b> and load the table with the data in the JSON-String-Array 
 */

function createTableResult(responseJSON, destination, clientType) {
	
	var h3 = '<h3>Result</h3>';
	if(clientType.toUpperCase() == 'COMPANY') {
		var str = h3 + "<table class='resultInTable'><tr><th>ID</th><th>Company Name</th><th>Email</th>";
		for (var i = 0; i < responseJSON.length ; i++) 
			{
				
				str = str + "<tr>";
				str = str + "<td>" + responseJSON[i].id + "</td><td>" + responseJSON[i].compName + "</td><td>" + responseJSON[i].email + "</td>";
			    str = str + "</tr>";

			    str = str + "<tr style='display:none;' id='info"+responseJSON[i].id+"'><td class='accountInfo' colspan='12'></td></tr>";
		    } // for loop
	} // if - COMPANY
	else if (clientType.toUpperCase() == 'CUSTOMER') {
		var str = h3 + "<table class='resultInTable'><tr><th>ID</th><th>Customer Name</th>";
		for (var i = 0; i < responseJSON.length ; i++) 
			{
				
				str = str + "<tr>";
				str = str + "<td>" + responseJSON[i].id + "</td><td>" + responseJSON[i].custName + "</td>";
			    str = str + "</tr>";

			    str = str + "<tr style='display:none;' id='info"+responseJSON[i].id+"'><td class='accountInfo' colspan='12'></td></tr>";
		    } // for loop
	} // else if - CUSTOMER
	else if (clientType.toUpperCase() == 'CUSTOMER_PURCHASE') {
		var str = h3 + "<table class='resultInTable'><tr><th>ID</th><th>Title</th><th>Start Date</th><th>End Date</th>" +
				"<th>Amount</th><th>Category</th><th>Message</th>" +
				"<th>Price</th><th>Image</th><th>Owner ID</th><th>Buy Click</th>";
		
		for (var i = 0; i < responseJSON.length ; i++) 
			{
				var coupID = responseJSON[i].id;
				// this var contains the button HTML code. and also, it will take the responseJSON[i].id and will put it directly in the purchase coupon method. 
				var buyButton = "<button type='button' id='btnPurchased' onclick='purchaseCouponButton(" + coupID + ");'>Purchase</button>";
				str = str + "<tr>";
				str = str + "<td>" + responseJSON[i].id + "</td>" +
						"<td>" + responseJSON[i].title + "</td>" + 
						"<td>" + responseJSON[i].startDate + "</td>" +
						"<td>" + responseJSON[i].endDate + "</td>" +
						"<td>" + responseJSON[i].amount + "</td>" +
						"<td>" + responseJSON[i].category + "</td>" +
						"<td>" + responseJSON[i].message + "</td>" +
						"<td>" + responseJSON[i].price + "</td>" +
						"<td>" + responseJSON[i].image + "</td>" +
						"<td>" + responseJSON[i].ownerID + "</td>" + 
						"<td id='btn" + coupID + "'>" + buyButton + "</td>";
				
			    str = str + "</tr>";
			    str = str + "<tr style='display:none;' id='info"+responseJSON[i].id+"'><td class='accountInfo' colspan='12'></td></tr>";
			    
			    // create an object with two properties 
//				var twoIDs = {buttonID: i, coupID: responseJSON[i].id};
			    // push the object into the array
//				btn_coupID.push(twoIDs);
				
		    } // for loop
	} // else if - COUPON
	else if (clientType.toUpperCase() == 'COUPON') {
		var str = h3 + "<table class='resultInTable'><tr><th>ID</th><th>Title</th><th>Start Date</th><th>End Date</th>" +
				"<th>Amount</th><th>Category</th><th>Message</th>" +
				"<th>Price</th><th>Image</th><th>Owner ID</th>";
		for (var i = 0; i < responseJSON.length ; i++) 
			{
				
				str = str + "<tr>";
				str = str + "<td>" + responseJSON[i].id + "</td>" +
						"<td>" + responseJSON[i].title + "</td>" + 
						"<td>" + responseJSON[i].startDate + "</td>" +
						"<td>" + responseJSON[i].endDate + "</td>" +
						"<td>" + responseJSON[i].amount + "</td>" +
						"<td>" + responseJSON[i].category + "</td>" +
						"<td>" + responseJSON[i].message + "</td>" +
						"<td>" + responseJSON[i].price + "</td>" +
						"<td>" + responseJSON[i].image + "</td>" +
						"<td>" + responseJSON[i].ownerID + "</td>";
				
			    str = str + "</tr>";
			    str = str + "<tr style='display:none;' id='info"+responseJSON[i].id+"'><td class='accountInfo' colspan='12'></td></tr>";
		    } // for loop
	} // else if - COUPON
	
	str = str + "</table>";
	$("#" + destination).html(str);

} // createCompanyTableResult

/**
 * <b>Relevant to CustomerData Only!</b></br>
 * This function will get JSON-response {@String} and a Destination {#IDElement}. 
 * The function will create new table results (with style) in the location you will give it in the 'destionation'.
 * 
 * @param responseJSON - String JSON Array
 * @param destination - String (#id)
 * @returns a dynamic table in the <b>destination</b> and load the table with the data in the JSON-String-Array 
 */

function createMessageResult(responseJSON, destination) {
	
	var str = "<h3 id='messageH3'>" + responseJSON.id + " " + responseJSON.compName + " " + responseJSON.email + "</h3>";
	
	$("#" + destination).html(str);

}

function createMessageObjectEdited(responseJSON, destination, method, clientType) {
	
	// only the message
	var message = '';
	var str = "<h3 id='messageH3'>";
	if(method == null || method == '' || method == undefined) {
		message = 'Done!';
	}
	else {
		message = 'Was ' + method + ' Successfully!';
	}
	
	// check client type
	if(clientType.toUpperCase() == 'COMPANY') {
		str = str + "Company:" + "<h4 id='messageH4'>" + 
		"[ID: " + responseJSON.id + " | " +
		"Name: " + responseJSON.compName + " | " + 
		"Email: " + responseJSON.email + "] " + 
		"</h4>";
	} // if - COMPANY
	else if (clientType.toUpperCase() == 'COUPON') {
		str = str + "Coupon:" + "<h4 id='messageH4'>" + 
		"[ID: " + responseJSON.id + " | " +
		"Title: " + responseJSON.title + " | " +
		"Start Date: " + responseJSON.startDate + " | " +
		"End Date: " + responseJSON.endDate + " | " +
		"Amount: " + responseJSON.amount + " | " +
		"End Date: " + responseJSON.amount + " | " +
		"Category: " + responseJSON.category + " | " +
		"Message: " + responseJSON.message + " | " +
		"Price: " + responseJSON.price + " | " +
		"Image: " + responseJSON.image + " | " +
		"Owner ID: " + responseJSON.ownerID + " | " +
		"] " + 
		"</h4>";
	}
	else if (clientType.toUpperCase() == 'CUSTOMER') {
		str = str + "Customer:" + "<h4 id='messageH4'>" + 
		"[ID: " + responseJSON.id + " | " +
		"Name: " + responseJSON.custName + "] " + 
		"</h4>";
		
	}
	
	str = str + message + "</h3>";
	$("#" + destination).html(str);

} // createMessageObjectEdited

/**
 * This function dealing with the ERROR status (NOT 200). 
 */
function createResponseMessageResult(responseJSON, destination) {
	// check if the the filter Service stops us. if yes, redirect!
	if (responseJSON.errorCode==900)
	{
		window.location=responseJSON.url;
	}
	var str = "<h3 id='messageH3'>" + responseJSON.message + "</h3>";
	$("#" + destination).html(str);

}

function createLoginFailedResponse(responseJSON, destination) {
	// check if the the filter Service stops us. if yes, redirect!
	var str = "<h3 id='messageH3'>" + responseJSON.message + "</h3>";
	$("#" + destination).html(str);

}

function createMessageEmptyFilds(destination) {
	var messageText = 'Empty Fileds!';
	var str = "<h3 id='messageH3'>" + messageText + "</h3>";
	
	$("#" + destination).html(str);

}
