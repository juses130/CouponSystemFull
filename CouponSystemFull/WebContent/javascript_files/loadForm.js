/**
 * 
 */

// load the page as str into some specific #id
function loadFormPage() {
	
	var str = "<div id='id01' class='modal'>";
	str = str + "<form class='modal-content animate' action='action_page.php'>";
	str = str + "<div class='imgcontainer;>";
	str = str + "<!-- This is span click for X (closing)-->";
	str = str + "<span onclick='document.getElementById('id01').style.display='none'" + "class='close'" + "title='Close Modal'></span>";
	str = str + "<!-- Here is the results in the popup window. can be table or just message -->";	
	str = str + "<div id='resultPopup'>";		
	str = str + "</div>";
	str = str + "</div>";
	str = str + "<div class='container'>";
	str = str + "<label></label>";
	str = str + "</div>";
	str = str + "<div class='container' style='background-color:#f1f1f1'>";
	str = str + "<button type='button' onclick='document.getElementById('id01').style.display='none'' class='cancelbtn'>Close</button>";
	str = str + "</div>";		
	str = str + "</form>";
	str = str + "<script>";
	str = str + "// Get the modal";
	str = str + "var modal = document.getElementById('id01');";
	str = str + "// When the user clicks anywhere outside of the modal, close it";
	str = str + "window.onclick = function(event) {";
	str = str + "if (event.target == modal) {modal.style.display = 'none';}}";
	str = str + "</script>";
	str = str + "</div>";
	str = str + "$('#formPage').html(str);";
	
	$("#content").html(str);
//	turnOnFormPage();
}
	
/**
 * This function will TURN ON the specific FORM in the html files.
 */
function turnOnFormPage() {
	document.getElementById('id01').style.display='block';
}
