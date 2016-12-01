/*
 * This JavaScript file contains couple of methods. 
 * This methods will take an Array (mostly JSON) and Destination (#elementID). it will put the json objects into rows in table. 
 * and set it in #elementID destination (div for example). 
 * 
 * OR set a message if it's just one object (like getCompanyByID fn).
 */


function createCompanyTableResult(responseJSON, destination) {
//	var style = "style = table, th , td  {border: 1px solid grey; border-collapse: collapse; padding: 5px};";
//	style = style + " table {text-align: left; margin: auto;}";
//	style = style + " table tr:nth-child(odd) {background-color: #f1f1f1;};";
	
	var h3 = '<h3>Result</h3>';
	var str = h3 + "<table class='resultInTable'><tr><th>ID</th><th>Company Name</th><th>Email</th>";
	for (var i = 0; i < responseJSON.length ; i++) 
		{
			
			str = str + "<tr>";
			str = str + "<td>" + responseJSON[i].id + "</td><td>" + responseJSON[i].compName + "</td><td>" + responseJSON[i].email + "</td>";
		    str = str + "</tr>";

		    str = str + "<tr style='display:none;' id='info"+responseJSON[i].id+"'><td class='accountInfo' colspan='12'></td></tr>";
	    }
	str = str + "</table>";
	$("#" + destination).html(str);

}

function createCustomerTableResult(responseJSON, destination) {
//	var style = "style = table, th , td  {border: 1px solid grey; border-collapse: collapse; padding: 5px};";
//	style = style + " table {text-align: left; margin: auto;}";
//	style = style + " table tr:nth-child(odd) {background-color: #f1f1f1;};";
	
	var str = "<table class='resultInTable'><tr><th>ID</th><th>Customer Name</th>";
	for (var i = 0; i < responseJSON.length ; i++) 
		{
			
			str = str + "<tr>";
			str = str + "<td>" + responseJSON[i].id + "</td><td>" + responseJSON[i].custName + "</td>";
		    str = str + "</tr>";

		    str = str + "<tr style='display:none;' id='info"+responseJSON[i].id+"'><td class='accountInfo' colspan='12'></td></tr>";
	    }
	str = str + "</table>";
	$("#" + destination).html(str);

}

function createMessageResult(responseJSON, destination) {
//	var style = "style = table, th , td  {border: 1px solid grey; border-collapse: collapse; padding: 5px};";
//	style = style + " table {text-align: left; margin: auto;}";
//	style = style + " table tr:nth-child(odd) {background-color: #f1f1f1;};";
	
	var str = "<div id='resultMessage'>" + "" + "</div>";
	for (var i = 0; i < responseJSON.length ; i++) 
		{
			
			str = str + "<tr>";
			str = str + "<td>" + responseJSON[i].id + "</td><td>" + responseJSON[i].custName + "</td>";
		    str = str + "</tr>";

		    str = str + "<tr style='display:none;' id='info"+responseJSON[i].id+"'><td class='accountInfo' colspan='12'></td></tr>";
	    }
	str = str + "</table>";
	$("#" + destination).html(str);

}
