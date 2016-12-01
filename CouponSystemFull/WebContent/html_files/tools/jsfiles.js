/**
 * 
 */
//==================================================
//loadJavaScriptFile('Mylib/jquery-1.9.1.js');
////==================================================
//loadJavaScriptFile('Mylib/jquery.min.js');
////==================================================
//loadJavaScriptFile('Mylib/jquery.xdomainajax.js');
//==================================================
loadJavaScriptFile('javascript_files/loadForm.js');	
//==================================================
loadJavaScriptFile('javascript_files/resultsCreator.js');
//==================================================
loadJavaScriptFile('Mylib/angular.min.js');
//==================================================
loadJavaScriptFile('javascript_files/tableResponse.js');
//==================================================
//loadJavaScriptFile('javascript_files/admin.js');
//==================================================


// This function allows us to load url (String) of JavaScrtipt file into the <head> tag. 
function loadJavaScriptFile(url) {
	var head = document.getElementsByTagName('head')[0];
	var script = document.createElement('script');
	script.type = 'text/javascript';
	script.src = url;
	head.appendChild(script);
}
