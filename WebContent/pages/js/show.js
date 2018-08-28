var xmlhttp;
function createXMLHttpRequest() {
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		if (window.ActiveXObject) {
			try {
				xmlhttp = new ActiveXObject("Msxm12.XMLHTTP");
			}
			catch (e) {
				try {
					xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
				}
				catch (e) {
				}
			}
		}
	}
}
function show(id) {
	createXMLHttpRequest();
	switch(id)
	{
	  case '1':
	   var url="showServlet?id=1";
	   break;
	    case '2':
	   url="showServlet?id=2";
	   break;
	    case '3':
	    url="showServlet?id=3";
	   break;
	    case '4':
	    url="showServlet?id=4";
	   break;
	    case '5':
	    url="showServlet?id=5";
	   break;
	    case '6':
	    url="showServlet?id=6";
	   break;
	}
	xmlhttp.open("GET", url, true);
	xmlhttp.onreadystatechange = showContent;
	xmlhttp.send(null);
}
function showContent() {
	if (xmlhttp.readyState == 4) {
		if (xmlhttp.status == 200) {
			Content();
		}
	 else {
	 alert(xmlhttp.readyState);
		//alert("\u9875\u9762\u51fa\u73b0\u5f02\u5e38\uff01");
	  }
	}
}

function Content()
{
  var xmldoc = xmlhttp.responseXML;
  var Contents = xmldoc.getElementsByTagName("Contents");
  var currentContent;
	for (var i = 0; i < Contents.length; i++) {
		currentContent = Contents[i];
		var name = currentContent.getElementsByTagName("Contents_Name")[0].firstChild.nodeValue;
		var content = currentContent.getElementsByTagName("Contents_content")[0].firstChild.nodeValue;
		showoneContent(name, content);
	}
}

function showoneContent(name, content)
{ 
    var parent=document.getElementById("contentlist");
    parent.innerText="";
    var row = document.createElement("tr");
	row.setAttribute("id", 1);
	var cell = document.createElement("td");
	cell.appendChild(document.createTextNode(name));
	row.appendChild(cell);
	cell = document.createElement("td");
	cell.appendChild(document.createTextNode(content));
	row.appendChild(cell);
	cell = document.createElement("td");
    var text = document.createElement("input");
	text.setAttribute("type", "Text");
	cell.appendChild(text);
	row.appendChild(cell);
    parent.appendChild(row);
  
}