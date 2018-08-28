
			  
function doCurrentPage(value) {
	var totalObject = document.getElementById("totalPages");
	if (totalObject != null) {
		var total = document.getElementById("totalPages").value;
		if (Number(value) > Number(total)) {
			value = 1;
		}
		if (doNeedChangeToGet()) {
			document.getElementById("input").method = "Get";
		}
		document.getElementById("currentPage").value = value;
	} else {
		if (doNeedChangeToGet()) {
			document.getElementById("input").method = "Get";
		}
	}
}
function doGoto() {
	try {
		var value = document.getElementById("gotoText").value;
		var total = document.getElementById("totalPages").value;
		if (Number(value) > Number(total)) {
			return;
		}
		document.getElementById("currentPage").value = value;
		if (doNeedChangeToGet()) {
			document.getElementById("input").method = "Get";
			document.getElementById("input").submit();
		} else {
			var submitNodeId = getThePostNodeName();
			document.getElementById(submitNodeId).click();
		}
	}
	catch (e) {
	}
}
function getCurrentPage() {
	return Number(document.getElementById("currentPage").value);
}
function getTotal() {
	var total = document.getElementById("totalPages").value;
	return Number(total);
}
function setLinesPerPage(value) {
	try {
		document.getElementById("linesPerPage").value = value;
		if (doNeedChangeToGet()) {
			document.getElementById("input").method = "Get";
			document.getElementById("input").submit();
		} else {
			var submitNodeId = getThePostNodeName();
			document.getElementById(submitNodeId).click();
		}
	}
	catch (e) {
	}
}
function setHiddenValue(hiddenName, value) {
		try{
		   document.getElementById(hiddenName).value = value;
		}catch(e){
		}		      
	
}
function sortList() {
	try {
		var sortHidden = document.getElementById("sortType");
		var sortNow = sortHidden.value;
		sortHidden.value = Number(sortNow) == 0 ? "1" : "0";
		doCurrentPage(1);
		if (doNeedChangeToGet()) {
			document.getElementById("input").submit();
		} else {
			var submitNodeId = getThePostNodeName();
			document.getElementById(submitNodeId).click();
		}
	}
	catch (e) {
	   e.message;
		//alert(e.message );
	}
}
/* we all need let the action do the "get" method ,
				    *  so we should change the form method to "get" (is default),
				    *  if not ,we let the struts action class to do this thing .
				    * and we make the mark at the page who include this jsp page.
				    */
function doNeedChangeToGet() {
	try {
		var changeToGet = document.getElementById("changeToGet");
		if ("atAction" == changeToGet.value) {
			return false;
		} else {
			return true;
		}
	}
	catch (e) {
		e.message;		       //alert(e.message); 
		return true;
	}
}
function getThePostNodeName() {
	var markNode = document.getElementById("submitNodeId");
	var submitNodeId = markNode.childNodes[0].data;
	return submitNodeId;
}
function resetSearcherFielter() {
	try {
		var selects = document.getElementsByTagName("select");
		for (var i = 0; i < selects.length; i++) {
			selects[i].value = "0";
			var oldValue = selects[i].value;
			if (oldValue == "0" || oldValue == "1") {
				selects[i].value = "0";
			} else {
				if (oldValue == "1" || oldValue == "2" || oldValue == "3" || oldValue == "4") {//name="lineSelect"
					selects[i].value = "2";
				}
			}
		}
		var inputTexts = document.getElementsByTagName("input");
		for (var i = 0; i < inputTexts.length; i++) {
			if (inputTexts[i].getAttribute("type") == "text" && inputTexts[i].getAttribute("name") != "gotoText") {
				inputTexts[i].value = "*";
			}
		}
	}
	catch (ex) {
	    ex.message;
		//alert(ex.name + "   " + ex.lineNumber + "   " + ex.message);
	}
}
function isCheckAll(handle) {
	try {
		var inputs = document.getElementsByTagName("input");
		if (handle.getAttribute("checked")) {
			for (var i = 0; i < inputs.length; i++) {
				if (inputs[i].getAttribute("type") == "checkbox") {
					inputs[i].setAttribute("checked", true);
				}
			}
		} else {
			for (var i = 0; i < inputs.length; i++) {
				if (inputs[i].getAttribute("type") == "checkbox") {
					inputs[i].setAttribute("checked", false);
				}
			}
		}
	}
	catch (e) {
	   e.message;
		//alert(e.name + "  " + e.message);
	}
}
 
     
     
function getChangeToGet() {
   try{
	return document.getElementById("changeToGet").value;
	}catch(e){
	  return "";
	}
}


//note:because the tag "logic:lessThan" is not work when it's "value" attribuet be set by "bean:write" 
//so we  use the followed method 
		    
			  try{
			      
				  if(document.getElementById("nextPageId")){
				     var total = getTotal();
				     var currentPage = getCurrentPage();			     
				     
				     if(total == currentPage){
				        document.getElementById("nextPageId").disabled = true;
				         if(document.getElementById("toEndPage")){
				             document.getElementById("toEndPage").disabled = true;
				          }
				     }
				     
				  }
				 
			  }catch(e){
			     
			  }
			  