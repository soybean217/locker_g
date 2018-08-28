function init() {
	var mailSendEnable = document.getElementsByName("mailSendBean.mailSendEnable")[0].value;
	var keywordEnable = document.getElementsByName("mailSendBean.keywordEnable")[0].value;
	var fileTypeEnable = document.getElementsByName("mailSendBean.fileTypeEnable")[0].value;
	
	var allowDelayMailEnable = document.getElementsByName("mailDelayBean.allowDelayMailEnable")[0].value;
	var refuseDelayMailEnable = document.getElementsByName("mailDelayBean.refuseDelayMailEnable")[0].value;
	var mailSize = document.getElementsByName("mailDelayBean.mailSize")[0].value;
	var appendixNum = document.getElementsByName("mailDelayBean.appendixNum")[0].value;
	var delayKeywordEnable = document.getElementsByName("mailDelayBean.keywordEnable")[0].value;
	var mailAddress = document.getElementsByName("mailDelayBean.mailAddress")[0].value;
	
	var timeControlTimeGroupId = document.getElementsByName("fluxTimeStatisticsBean.timeGroupId")[0].value;
	
	var mailSendRadio = document.getElementsByName("mailSendRadio");
	var mailContentBox = document.getElementsByName("mailContentBox");
	var delayMailBox = document.getElementsByName("delayMailBox");
	var delayMailContentBox = document.getElementsByName("delayMailContentBox");
	if(mailSendEnable != null) {
		if(mailSendRadio != null && mailSendRadio.length > 0) {
			for(var i = 0;i < mailSendRadio.length;i++) {
				if(mailSendRadio[i].value == mailSendEnable) {
					mailSendRadio[i].checked = true;
					break;
				}
			}
		}
		if(i == 0) {
			document.getElementsByName("mailSendBean.refuseMail")[0].readOnly = false;
			document.getElementsByName("mailSendBean.allowMail")[0].readOnly = true;
		} else if(i == 1) {
			document.getElementsByName("mailSendBean.refuseMail")[0].readOnly = true;
			document.getElementsByName("mailSendBean.allowMail")[0].readOnly = false;
		}
	}
	if(keywordEnable != null && keywordEnable != "") {
		mailContentBox[0].checked = true;
		document.getElementsByName("mailSendBean.keywordContent")[0].readOnly = false;
	}
	if(fileTypeEnable != null && fileTypeEnable != "") {
		mailContentBox[1].checked = true;
		document.getElementsByName("mailSendBean.fileTypeContent")[0].readOnly = false;
	}
	if(allowDelayMailEnable != null && allowDelayMailEnable !=  "") {
		delayMailBox[0].checked = true;
		document.getElementsByName("mailDelayBean.allowDelayMail")[0].readOnly = false;
	}
	if(refuseDelayMailEnable != null && refuseDelayMailEnable != "") {
		delayMailBox[1].checked = true;
		document.getElementsByName("mailDelayBean.refuseDelayMail")[0].readOnly = false;
	}
	if(mailSize != null && mailSize != "") {
		delayMailContentBox[0].checked = true;
		document.getElementsByName("mailDelayBean.mailSize")[0].readOnly = false;
	}
	if(appendixNum != null && appendixNum != "") {
		delayMailContentBox[1].checked = true;
		document.getElementsByName("mailDelayBean.appendixNum")[0].readOnly = false;
	}
	if(delayKeywordEnable !=  null && delayKeywordEnable != "") {
		delayMailContentBox[2].checked = true;
		document.getElementsByName("mailDelayBean.delayMailContent")[0].readOnly = false;
	}
	if(mailAddress !=  null && mailAddress != "") {
		document.getElementsByName("mailNoticeBox")[0].checked = true;
		document.getElementsByName("mailDelayBean.mailAddress")[0].readOnly = false;
	}
	if(timeControlTimeGroupId != null && timeControlTimeGroupId != "") {
		getTimeGroups("controlTimeGroup");
	}
}
var response = true;
function checkIsNull() {
	if(document.getElementsByName("strategyName")[0].value == "") {
		alert("请输入策略名称!");
		return false;
	}
	if(document.getElementsByName("strategyDescription")[0].value == "") {
		alert("请输入策略描述!");
		return false;
	}
	return true;
}
// 获取xmlHttp对象
function createXMLHttpRequest() {
	var xmlHttp;
	if(window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	} else if(window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
	}
	return xmlHttp;
}
// 获取时间计划组
function getTimeGroups(group) {
	var xmlHttp = createXMLHttpRequest();
	var url = "GetTimeGroups.action";
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {showTimeGroupHandle(xmlHttp,group);};
	xmlHttp.send(null);
}
function showTimeGroupHandle(xmlHttp,group) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showTimeGroups(xmlHttp,group);
		}
	}
}
function showTimeGroups(xmlHttp,group) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var timeGroup = null;
	var id = "";
	var name = "";
	var timeGroups = results.getElementsByTagName("timeGroup");
	var timeGroupSelect = document.getElementById(group);
	clearSelectBox(timeGroupSelect);
	for(var i = 0;i < timeGroups.length;i++) {
		timeGroup = timeGroups[i];
		id = timeGroup.getElementsByTagName("id")[0].firstChild.nodeValue;
		name = decodeURI(decodeURI(timeGroup.getElementsByTagName("name")[0].firstChild.nodeValue));
		addSelectBox(timeGroupSelect,id,name);
	}
	if(group == "controlTimeGroup") {
		var timeControlTimeGroupId = document.getElementsByName("fluxTimeStatisticsBean.timeGroupId")[0].value;
		var timeGroupSelect = document.getElementById("controlTimeGroup");
		for(var i = 0;i < timeGroupSelect.options.length;i++) {
			if(timeGroupSelect.options[i].value == timeControlTimeGroupId) {
				timeGroupSelect.selectedIndex = i;
				break;
			}
		}
	}
}
// 判断session是否已过期
function checkSessionIsOut(results) {
	var result = results.getElementsByTagName("session")[0];
	if(result == null) {
		response = false;
		return false;
	} else if(result.firstChild.nodeValue == "timeOut") {
		alert(decodeURI(decodeURI(results.getElementsByTagName("message")[0].firstChild.nodeValue)));
		response = false;
		return true;
	} else {
		response = false;
		return false;
	}
}
// 获取IP组
function getIpGroups() {
	var xmlHttp = createXMLHttpRequest();
	var url = "GetIpGroups.action";
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {showIpGroupHandle(xmlHttp);};
	xmlHttp.send(null);
}
function showIpGroupHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showIpGroups(xmlHttp);
		}
	}
}
function showIpGroups(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var ipGroup = null;
	var id = "";
	var name = "";
	var ipGroups = results.getElementsByTagName("ipGroup");
	var ipGroupSelect = document.getElementById("ipGroupSelect");
	clearSelectBox(ipGroupSelect);
	for(var i = 0;i < ipGroups.length;i++) {
		ipGroup = ipGroups[i];
		id = ipGroup.getElementsByTagName("id")[0].firstChild.nodeValue;
		name = decodeURI(decodeURI(ipGroup.getElementsByTagName("name")[0].firstChild.nodeValue));
		addSelectBox(ipGroupSelect,id,name);
	}
}
// 清空下拉列表框
function clearSelectBox(selectBox) {
	while(selectBox.childNodes.length > 0) {
		selectBox.removeChild(selectBox.childNodes[0]);
	}
}
// 追加下拉列表框值
function addSelectBox(selectBox,id,name) {
	var option = document.createElement("option");
	selectBox.appendChild(option);
	option.setAttribute("value",id);
	var text = document.createTextNode(name);
	option.appendChild(text);
}
// 获取应用类型下拉列表
function getApplicationTypeList() {
	var xmlHttp = createXMLHttpRequest();
	var url = "GetApplicationType.action?";
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {showApplicationTypeHandle(xmlHttp);};
	xmlHttp.send(null);	
}
function showApplicationTypeHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showApplicationType(xmlHttp);
		}
	}
}
function showApplicationType(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var applicationType = null;
	var value = "";
	var name = "";
	var applicationTypes = results.getElementsByTagName("applicationType");
	var applicationTypeSelect = document.getElementById("applicationTypeSelect");		
	clearSelectBox(applicationTypeSelect);
	addSelectBox(applicationTypeSelect,"ALL","所有已知应用");
	for(var i = 0;i < applicationTypes.length;i++) {
		applicationType = applicationTypes[i];
		value = decodeURI(decodeURI(applicationType.getElementsByTagName("value")[0].firstChild.nodeValue));
		name = decodeURI(decodeURI(applicationType.getElementsByTagName("name")[0].firstChild.nodeValue));
		addSelectBox(applicationTypeSelect,value,name);
	}
}
// 根据应用类型获取应用名称
function getApplicationNameByType(type) {
	var xmlHttp = createXMLHttpRequest();
	var url = "GetApplicationNameByType.action?type=" + type;
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {showApplicationNameByTypeHandle(xmlHttp);};
	xmlHttp.send(null);
}
function showApplicationNameByTypeHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showApplicationNameByType(xmlHttp);
		}
	}
}
function showApplicationNameByType(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var applicationName = null;
	var value = "";
	var name = "";
	var applicationNames = results.getElementsByTagName("applicationName");
	var applicationNameSelect = document.getElementById("applicationNameSelect");
	clearSelectBox(applicationNameSelect);
	addSelectBox(applicationNameSelect,"ALL","全部");
	for(var i = 0;i < applicationNames.length;i++) {
		applicationName = applicationNames[i];
		value = decodeURI(decodeURI(applicationName.getElementsByTagName("value")[0].firstChild.nodeValue));
		name = decodeURI(decodeURI(applicationName.getElementsByTagName("name")[0].firstChild.nodeValue));
		addSelectBox(applicationNameSelect,value,name);
	}
}
// 根据应用类型和应用名称获取规则名
function getApplicationRules(value) {
	if(value == "type") {
		var applicationType = document.getElementById("applicationTypeSelect").value;
		getApplicationNameByType(applicationType);
	}
	var xmlHttp = createXMLHttpRequest();
	var applicationType = document.getElementById("applicationTypeSelect").value;
	var applicationName = document.getElementById("applicationNameSelect").value;
	if(value == "ALL") {
		applicationType = "ALL";
		applicationName = "ALL";
	}
	var url = "GetApplicationRules.action?applicationType=" + applicationType+ "&applicationName=" + applicationName;
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {showApplicationRuleHandle(xmlHttp);};
	xmlHttp.send(null);
}
function showApplicationRuleHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showApplicationRules(xmlHttp);
		}
	}
}
function showApplicationRules(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var applicationRule = null;
	var id = "";
	var name = "";
	var applicationRules = results.getElementsByTagName("applicationRule");
	var ruleName = document.getElementById("ruleNameSelect");		
	clearSelectBox(ruleName);
	addSelectBox(ruleName,"ALL","全部");
	for(var i = 0;i < applicationRules.length;i++) { 
		applicationRule = applicationRules[i];
		id = applicationRule.getElementsByTagName("id")[0].firstChild.nodeValue;
		name = decodeURI(decodeURI(applicationRule.getElementsByTagName("name")[0].firstChild.nodeValue));
		addSelectBox(ruleName,id,name);
	}
}

// 获取网络服务控制规则
function getNetworkServices() {
	var xmlHttp = createXMLHttpRequest();
	var url = "GetNetworkServices.action";
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {showNetworkServiceRuleHandle(xmlHttp);};
	xmlHttp.send(null);
}
function showNetworkServiceRuleHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showNetworkServiceRules(xmlHttp);
		}
	}
}
function showNetworkServiceRules(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var networkService = null;
	var id = "";
	var name = "";
	var networkServices = results.getElementsByTagName("networkService");
	var serviceName = document.getElementById("serviceSelect");		
	clearSelectBox(serviceName);
	for(var i = 0;i < networkServices.length;i++) {
		networkService = networkServices[i];
		id = networkService.getElementsByTagName("id")[0].firstChild.nodeValue;
		name = decodeURI(decodeURI(networkService.getElementsByTagName("name")[0].firstChild.nodeValue));
		addSelectBox(serviceName,id,name);
	}
}
function showStrategy(divId) {
	document.getElementById("authorityStrategy").style.display = "none";
	document.getElementById("pageStrategy").style.display = "none";
	document.getElementById("mailStrategy").style.display = "none";
	document.getElementById("netStrategy").style.display = "none";
	document.getElementById(divId).style.display = "";
}

function showSubStrategy(divId) {
	var isDisplay = true;
	if(divId == "applicationService") {
		getApplicationTypeList();
		getApplicationNameByType("ALL");
		getApplicationRules("ALL");
		getTimeGroups("applicationTimeGroup");
	} else if(divId == "networkService") {
		getIpGroups();
		getNetworkServices();
		getTimeGroups("networkTimeGroup");
	} else if(divId == "urlFiltration") {
		getUrlGroups();
		getTimeGroups("urlTimeGroup");
	} else if(divId == "keywordFiltration") {
		getKeywordGroups();
		getTimeGroups("keywordTimeGroup");
	} else if(divId == "fileTypeFiltration") {
		getFileTypeGroups();
		getTimeGroups("fileTypeTimeGroup");
	} else if(divId == "timeControl") {
		getTimeGroups("controlTimeGroup");
	} else if(divId == "bandwidth") {
		getApplicationTypeList();
		getApplicationNameByType("ALL");
		getUrlGroups();
		getFileTypeGroups();
		return;
	}
	document.getElementById("applicationService").style.display = "none";
	document.getElementById("networkService").style.display = "none";
	document.getElementById("urlFiltration").style.display = "none";
	document.getElementById("keywordFiltration").style.display = "none";
	document.getElementById("fileTypeFiltration").style.display = "none";
	document.getElementById("sendMail").style.display = "none";
	document.getElementById("delayMail").style.display = "none";
	document.getElementById("timeStatistics").style.display = "none";
	document.getElementById("timeControl").style.display = "none";
	document.getElementById("connectNum").style.display = "none";
	document.getElementById(divId).style.display = "";
}
// 点击新增时显示新增区域
function showAddAreaDiv(div) {
	document.getElementById(div).style.display = "";
}
// 新增一条应用服务控制
function addApplicationService() {
	if(!checkIsNull()) {
		return;
	}
	var xmlHttp = createXMLHttpRequest();
	var applicationType = document.getElementById("applicationTypeSelect").value;
	var applicationName = document.getElementById("applicationNameSelect").value;
	var url = "AddApplicationService.action?" + getApplicationServiceQueryUrl();
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {showApplicationServiceHandle(xmlHttp);};
	xmlHttp.send(null);
}
function showApplicationServiceHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showApplicationServices(xmlHttp);
		}
	}
}
function showApplicationServices(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("applicationServiceList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var row = document.createElement("tr");
	tbody.appendChild(row);
	var applicationTypeSelect = document.getElementById("applicationTypeSelect");
	var applicationNameSelect = document.getElementById("applicationNameSelect");
	var ruleNameSelect = document.getElementById("ruleNameSelect");
	var applicationTimeGroup = document.getElementById("applicationTimeGroup");
	var index = list.rows.length - 1;
	addTableRow(row,"<input type='checkbox' name='applicationServiceCheck' value='" + results.getElementsByTagName("applicationServiceId")[0].firstChild.nodeValue + "'/>");
	addTableRow(row,list.rows.length - 1);
	addTableRow(row,getTextByValue(applicationTypeSelect,results.getElementsByTagName("applicationType")[0].firstChild.nodeValue));
	addTableRow(row,getTextByValue(applicationNameSelect,results.getElementsByTagName("applicationName")[0].firstChild.nodeValue));
	addTableRow(row,getTextByValue(ruleNameSelect,results.getElementsByTagName("ruleId")[0].firstChild.nodeValue));
	addTableRow(row,decodeURI(decodeURI(results.getElementsByTagName("enable")[0].firstChild.nodeValue)));
	addTableRow(row,getTextByValue(applicationTimeGroup,results.getElementsByTagName("timeGroupId")[0].firstChild.nodeValue));
	addTableRow(row,'<a href="javascript:enableApplicationService(\'' + results.getElementsByTagName("applicationServiceId")[0].firstChild.nodeValue
			 + '\',\'allow\',' + index + ')">允许</a>&nbsp;&nbsp;<a href="javascript:enableApplicationService(\''
			 + results.getElementsByTagName("applicationServiceId")[0].firstChild.nodeValue + '\',\'forbidden\',' + index + ')">拒绝</a>');
	document.getElementsByName("strategyId")[0].value = results.getElementsByTagName("id")[0].firstChild.nodeValue;
	document.getElementsByName("hiddenIpGroupId")[0].value = results.getElementsByTagName("hiddenIpGroupId")[0].firstChild.nodeValue;
}
// 通过下拉列表框的value获取text值
function getTextByValue(selectBox,value) {
	for(var i = 0;i < selectBox.options.length;i++) {
		if(selectBox.options[i].value == value) {
			return selectBox.options[i].text;
		}
	}
}
function getApplicationServiceQueryUrl() {
	var url = "";
	url += "strategyId=" + document.getElementsByName("strategyId")[0].value;
	url += "&strategyName=" + encodeURI(encodeURI(document.getElementsByName("strategyName")[0].value));
	url += "&strategyDescription=" + encodeURI(encodeURI(document.getElementsByName("strategyDescription")[0].value));
	url += "&ipGroupId=" + document.getElementsByName("ipGroupId")[0].value;
	url += "&hiddenIpGroupId=" + document.getElementsByName("hiddenIpGroupId")[0].value;
	var applicationTypeSelect = document.getElementById("applicationTypeSelect");
	var applicationNameSelect = document.getElementById("applicationNameSelect");
	var ruleNameSelect = document.getElementById("ruleNameSelect");
	var applicationTimeGroup = document.getElementById("applicationTimeGroup");
	url += "&applicationType=" + applicationTypeSelect.options[applicationTypeSelect.selectedIndex].value;
	url += "&applicationName=" + applicationNameSelect.options[applicationNameSelect.selectedIndex].value;
	url += "&ruleId=" + ruleNameSelect.options[ruleNameSelect.selectedIndex].value;
	url += "&timeGroupId=" + applicationTimeGroup.options[applicationTimeGroup.selectedIndex].value;
	var radio = document.getElementsByName("applicationServiceEnable");
	var enable = null;
	for(var i = 0;i < radio.length;i++) {
		if(radio[i].checked) {
			enable = radio[i].value;
			break;
		}
	}
	url += "&enable=" + enable;
	return url;
}
// 在表中的row行处新增一单元格
function addTableRow(row,value) {
	var cell = document.createElement("td");
	cell.setAttribute("align","center");
	cell.innerHTML = value;
	row.appendChild(cell);
}
// 允许/拒绝应用服务控制
function enableApplicationService(id,type,index) {
	var xmlHttp = createXMLHttpRequest();
	var strategyId = document.getElementsByName("strategyId")[0].value;
	var url = "EnableApplicationService.action?strategyId=" + strategyId + "&id=" + id + "&enable=" + type;
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {enableApplicationServiceHandle(xmlHttp,index);};
	xmlHttp.send(null);
}
function enableApplicationServiceHandle(xmlHttp,index) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showEnabledApplicationService(xmlHttp,index);
		}
	}
}
function showEnabledApplicationService(xmlHttp,index) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("applicationServiceList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var enable = decodeURI(decodeURI(results.getElementsByTagName("enable")[0].firstChild.nodeValue));
	list.rows[index].cells[5].innerHTML = enable;
}
// 删除选中的应用服务控制
function deleteApplicationServices() {
	var strategyId = document.getElementsByName("strategyId")[0].value;
	var checkbox = document.getElementsByName("applicationServiceCheck");
	if(checkbox != null && checkbox.length > 0) {
		var total = 0;// make a flag to the number of checkbox selected
		var ruleId = "";
		for(var i = 0;i < checkbox.length;i++) {
			if(checkbox[i].checked) {
				total++;
				ruleId += checkbox[i].value + "AND";
			}
		}
		if(total == 0) {
			alert("请选择要删除的规则!");
		} else if(confirm("确定要删除选中的规则吗?")) {
			var xmlHttp = createXMLHttpRequest();
			var url = "DeleteApplicationServices.action?strategyId=" + strategyId + "&id=" + ruleId;
			xmlHttp.open("GET",url,true);
			xmlHttp.onreadystatechange = function() {DeleteApplicationServicesHandle(xmlHttp);};
			xmlHttp.send(null);
		}
	}
}
function DeleteApplicationServicesHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showDeletedApplicationServices(xmlHttp);
		}
	}
}
function showDeletedApplicationServices(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("applicationServiceList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var result = results.getElementsByTagName("result")[0].firstChild.nodeValue;
	if(result == "success") {
		var checkbox = document.getElementsByName("applicationServiceCheck");
		if(checkbox != null && checkbox.length > 0) {
			for(var i = checkbox.length - 1;i >= 0;i--) {
				if(checkbox[i].checked) {
					tbody.removeChild(tbody.childNodes[i + 1]);
				}
			}
			for(var index = 1;index < tbody.childNodes.length;index++) {
				tbody.rows[index].cells[1].innerHTML = index;
				tbody.rows[index].cells[7].innerHTML = 	'<a href="javascript:enableApplicationService(\'' + checkbox[index - 1].value
			 + '\',\'allow\',' + index + ')">允许</a>&nbsp;&nbsp;<a href="javascript:enableApplicationService(\''
			 + checkbox[index - 1].value + '\',\'forbidden\',' + index + ')">拒绝</a>';
			}
		}
	}
}
// 新增一条网络服务控制
function addNetworkService() {
	if(!checkIsNull()) {
		return;
	}
	if(document.getElementById("serviceSelect").length == 0) {
		alert("请先定义网络服务服务对象!");
		return;
	}
	var xmlHttp = createXMLHttpRequest();
	var url = "AddNetworkService.action?" + getNetworkServiceQueryUrl();
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {showNetworkServiceHandle(xmlHttp);};
	xmlHttp.send(null);
}
function showNetworkServiceHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showNetworkServices(xmlHttp);
		}
	}
}
function showNetworkServices(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("networkServiceList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var row = document.createElement("tr");
	tbody.appendChild(row);
	var ipGroupSelect = document.getElementById("ipGroupSelect");
	var serviceSelect = document.getElementById("serviceSelect");
	var networkTimeGroup = document.getElementById("networkTimeGroup");
	var index = list.rows.length - 1;
	addTableRow(row,"<input type='checkbox' name='networkServiceCheck' value='" + results.getElementsByTagName("networkServiceId")[0].firstChild.nodeValue + "'/>");
	addTableRow(row,list.rows.length - 1);
	addTableRow(row,getTextByValue(ipGroupSelect,results.getElementsByTagName("ipGroupId")[0].firstChild.nodeValue));
	addTableRow(row,getTextByValue(serviceSelect,results.getElementsByTagName("serviceId")[0].firstChild.nodeValue));
	addTableRow(row,decodeURI(decodeURI(results.getElementsByTagName("enable")[0].firstChild.nodeValue)));
	addTableRow(row,getTextByValue(networkTimeGroup,results.getElementsByTagName("timeGroupId")[0].firstChild.nodeValue));
	addTableRow(row,'<a href="javascript:enableNetworkService(\'' + results.getElementsByTagName("networkServiceId")[0].firstChild.nodeValue
			 + '\',\'allow\',' + index + ')">允许</a>&nbsp;&nbsp;<a href="javascript:enableNetworkService(\''
			 + results.getElementsByTagName("networkServiceId")[0].firstChild.nodeValue + '\',\'forbidden\',' + index + ')">拒绝</a>');
	document.getElementsByName("strategyId")[0].value = results.getElementsByTagName("id")[0].firstChild.nodeValue;
	document.getElementsByName("hiddenIpGroupId")[0].value = results.getElementsByTagName("hiddenIpGroupId")[0].firstChild.nodeValue;
}
function getNetworkServiceQueryUrl() {
	var url = "";
	url += "strategyId=" + document.getElementsByName("strategyId")[0].value;
	url += "&strategyName=" + encodeURI(encodeURI(document.getElementsByName("strategyName")[0].value));
	url += "&strategyDescription=" + encodeURI(encodeURI(document.getElementsByName("strategyDescription")[0].value));
	url += "&ipGroupId=" + document.getElementsByName("ipGroupId")[0].value;
	url += "&hiddenIpGroupId=" + document.getElementsByName("hiddenIpGroupId")[0].value;
	var ipGroupSelect = document.getElementById("ipGroupSelect");
	url += "&targetIpGroupId=" + ipGroupSelect.options[ipGroupSelect.selectedIndex].value;
	var serviceSelect = document.getElementById("serviceSelect");
	url += "&serviceId=" + serviceSelect.options[serviceSelect.selectedIndex].value;
	var networkTimeGroup = document.getElementById("networkTimeGroup");
	url += "&timeGroupId=" + networkTimeGroup.options[networkTimeGroup.selectedIndex].value;
	var radio = document.getElementsByName("networkServiceEnable");
	var enable = null;
	for(var i = 0;i < radio.length;i++) {
		if(radio[i].checked) {
			enable = radio[i].value;
			break;
		}
	}
	url += "&enable=" + enable;
	return url;
}
// 允许/拒绝网络服务控制
function enableNetworkService(id,type,index) {
	var xmlHttp = createXMLHttpRequest();
	var strategyId = document.getElementsByName("strategyId")[0].value;
	var url = "EnableNetworkService.action?strategyId=" + strategyId + "&id=" + id + "&enable=" + type;
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {enableNetworkServiceHandle(xmlHttp,index);};
	xmlHttp.send(null);
}
function enableNetworkServiceHandle(xmlHttp,index) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showEnabledNetworkService(xmlHttp,index);
		}
	}
}
function showEnabledNetworkService(xmlHttp,index) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("networkServiceList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var enable = decodeURI(decodeURI(results.getElementsByTagName("enable")[0].firstChild.nodeValue));
	list.rows[index].cells[4].innerHTML = enable;
}
// 删除选中的网络服务控制
function deleteNetworkServices() {
	var strategyId = document.getElementsByName("strategyId")[0].value;
	var checkbox = document.getElementsByName("networkServiceCheck");
	if(checkbox != null && checkbox.length > 0) {
		var total = 0;// make a flag to the number of checkbox selected
		var ruleId = "";
		for(var i = 0;i < checkbox.length;i++) {
			if(checkbox[i].checked) {
				total++;
				ruleId += checkbox[i].value + "AND";
			}
		}
		if(total == 0) {
			alert("请选择要删除的规则!");
		} else if(confirm("确定要删除选中的规则吗?")) {
			var xmlHttp = createXMLHttpRequest();
			var url = "DeleteNetworkServices.action?strategyId=" + strategyId + "&id=" + ruleId;
			xmlHttp.open("GET",url,true);
			xmlHttp.onreadystatechange = function() {DeleteNetworkServicesHandle(xmlHttp);};
			xmlHttp.send(null);
		}
	}
}
function DeleteNetworkServicesHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showDeletedNetworkServices(xmlHttp);
		}
	}
}
function showDeletedNetworkServices(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("networkServiceList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var result = results.getElementsByTagName("result")[0].firstChild.nodeValue;
	if(result == "success") {
		var checkbox = document.getElementsByName("networkServiceCheck");
		if(checkbox != null && checkbox.length > 0) {
			for(var i = checkbox.length - 1;i >= 0;i--) {
				if(checkbox[i].checked) {
					tbody.removeChild(tbody.childNodes[i + 1]);
				}
			}
			for(var index = 1;index < tbody.childNodes.length;index++) {
				tbody.rows[index].cells[1].innerHTML = index;
				tbody.rows[index].cells[6].innerHTML = 	'<a href="javascript:enableNetworkService(\'' + checkbox[index - 1].value
			 + '\',\'allow\',' + index + ')">允许</a>&nbsp;&nbsp;<a href="javascript:enableNetworkService(\''
			 + checkbox[index - 1].value + '\',\'forbidden\',' + index + ')">拒绝</a>';
			}
		}
	}
}
// 获取URL组
function getUrlGroups() {
	var xmlHttp = createXMLHttpRequest();
	var url = "GetUrlGroups.action";
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {showUrlGroupHandle(xmlHttp);};
	xmlHttp.send(null);
}
function showUrlGroupHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showUrlGroups(xmlHttp);
		}
	}
}
function showUrlGroups(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var urlGroup = null;
	var id = "";
	var name = "";
	var urlGroups = results.getElementsByTagName("urlGroup");
	var urlGroupSelect = document.getElementById("urlGroupSelect");
	clearSelectBox(urlGroupSelect);
	for(var i = 0;i < urlGroups.length;i++) {
		urlGroup = urlGroups[i];
		id = urlGroup.getElementsByTagName("id")[0].firstChild.nodeValue;
		name = decodeURI(decodeURI(urlGroup.getElementsByTagName("name")[0].firstChild.nodeValue));
		addSelectBox(urlGroupSelect,id,name);
	}
}
// 新增一条URL过滤
function addUrlFiltration() {
	if(!checkIsNull()) {
		return;
	}
	if(document.getElementById("urlGroupSelect").length == 0) {
		alert("请先定义URL组!");
		return;
	}
	var xmlHttp = createXMLHttpRequest();
	var url = "AddUrlFiltration.action?" + getUrlFiltrationQueryUrl();
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {showUrlFiltrationHandle(xmlHttp);};
	xmlHttp.send(null);
}
function showUrlFiltrationHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showUrlFiltrations(xmlHttp);
		}
	}
}
function showUrlFiltrations(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("urlFiltrationList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var row = document.createElement("tr");
	tbody.appendChild(row);
	var urlGroupSelect = document.getElementById("urlGroupSelect");
	var urlTimeGroup = document.getElementById("urlTimeGroup");
	var index = list.rows.length - 1;
	addTableRow(row,"<input type='checkbox' name='urlFiltrationCheck' value='" + results.getElementsByTagName("urlFiltrationId")[0].firstChild.nodeValue + "'/>");
	addTableRow(row,list.rows.length - 1);
	addTableRow(row,getTextByValue(urlGroupSelect,results.getElementsByTagName("urlGroupId")[0].firstChild.nodeValue));
	addTableRow(row,decodeURI(decodeURI(results.getElementsByTagName("description")[0].firstChild.nodeValue)));
	addTableRow(row,decodeURI(decodeURI(results.getElementsByTagName("enable")[0].firstChild.nodeValue)));
	addTableRow(row,getTextByValue(urlTimeGroup,results.getElementsByTagName("timeGroupId")[0].firstChild.nodeValue));
	addTableRow(row,'<a href="javascript:enableUrlFiltration(\'' + results.getElementsByTagName("urlFiltrationId")[0].firstChild.nodeValue
			 + '\',\'allow\',' + index + ')">允许</a>&nbsp;&nbsp;<a href="javascript:enableUrlFiltration(\''
			 + results.getElementsByTagName("urlFiltrationId")[0].firstChild.nodeValue + '\',\'forbidden\',' + index + ')">拒绝</a>');
	document.getElementsByName("strategyId")[0].value = results.getElementsByTagName("id")[0].firstChild.nodeValue;
	document.getElementsByName("hiddenIpGroupId")[0].value = results.getElementsByTagName("hiddenIpGroupId")[0].firstChild.nodeValue;
}
function getUrlFiltrationQueryUrl() {
	var url = "";
	url += "strategyId=" + document.getElementsByName("strategyId")[0].value;
	url += "&strategyName=" + encodeURI(encodeURI(document.getElementsByName("strategyName")[0].value));
	url += "&strategyDescription=" + encodeURI(encodeURI(document.getElementsByName("strategyDescription")[0].value));
	url += "&ipGroupId=" + document.getElementsByName("ipGroupId")[0].value;
	url += "&hiddenIpGroupId=" + document.getElementsByName("hiddenIpGroupId")[0].value;
	var urlGroupSelect = document.getElementById("urlGroupSelect");
	url += "&urlGroupId=" + urlGroupSelect.options[urlGroupSelect.selectedIndex].value;
	var urlTimeGroup = document.getElementById("urlTimeGroup");
	url += "&timeGroupId=" + urlTimeGroup.options[urlTimeGroup.selectedIndex].value;
	var radio = document.getElementsByName("urlFiltrationEnable");
	var enable = null;
	for(var i = 0;i < radio.length;i++) {
		if(radio[i].checked) {
			enable = radio[i].value;
			break;
		}
	}
	url += "&enable=" + enable;
	return url;
}
// 允许/拒绝URL过滤
function enableUrlFiltration(id,type,index) {
	var xmlHttp = createXMLHttpRequest();
	var strategyId = document.getElementsByName("strategyId")[0].value;
	var url = "EnableUrlFiltration.action?strategyId=" + strategyId + "&id=" + id + "&enable=" + type;
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {enableUrlFiltrationHandle(xmlHttp,index);};
	xmlHttp.send(null);
}
function enableUrlFiltrationHandle(xmlHttp,index) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showEnabledUrlFiltration(xmlHttp,index);
		}
	}
}
function showEnabledUrlFiltration(xmlHttp,index) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("urlFiltrationList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var enable = decodeURI(decodeURI(results.getElementsByTagName("enable")[0].firstChild.nodeValue));
	list.rows[index].cells[4].innerHTML = enable;
}
// 删除选中的URL过滤
function deleteUrlFiltrations() {
	var strategyId = document.getElementsByName("strategyId")[0].value;
	var checkbox = document.getElementsByName("urlFiltrationCheck");
	if(checkbox != null && checkbox.length > 0) {
		var total = 0;// make a flag to the number of checkbox selected
		var ruleId = "";
		for(var i = 0;i < checkbox.length;i++) {
			if(checkbox[i].checked) {
				total++;
				ruleId += checkbox[i].value + "AND";
			}
		}
		if(total == 0) {
			alert("请选择要删除的规则!");
		} else if(confirm("确定要删除选中的规则吗?")) {
			var xmlHttp = createXMLHttpRequest();
			var url = "DeleteUrlFiltrations.action?strategyId=" + strategyId + "&id=" + ruleId;
			xmlHttp.open("GET",url,true);
			xmlHttp.onreadystatechange = function() {DeleteUrlFiltrationsHandle(xmlHttp);};
			xmlHttp.send(null);
		}
	}
}
function DeleteUrlFiltrationsHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showDeletedUrlFiltrations(xmlHttp);
		}
	}
}
function showDeletedUrlFiltrations(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("urlFiltrationList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var result = results.getElementsByTagName("result")[0].firstChild.nodeValue;
	if(result == "success") {
		var checkbox = document.getElementsByName("urlFiltrationCheck");
		if(checkbox != null && checkbox.length > 0) {
			for(var i = checkbox.length - 1;i >= 0;i--) {
				if(checkbox[i].checked) {
					tbody.removeChild(tbody.childNodes[i + 1]);
				}
			}
			for(var index = 1;index < tbody.childNodes.length;index++) {
				tbody.rows[index].cells[1].innerHTML = index;
				tbody.rows[index].cells[6].innerHTML = 	'<a href="javascript:enableUrlFiltration(\'' + checkbox[index - 1].value
			 + '\',\'allow\',' + index + ')">允许</a>&nbsp;&nbsp;<a href="javascript:enableUrlFiltration(\''
			 + checkbox[index - 1].value + '\',\'forbidden\',' + index + ')">拒绝</a>';
			}
		}
	}
}
// 获取关键字组
function getKeywordGroups() {
	var xmlHttp = createXMLHttpRequest();
	var url = "GetKeywordGroups.action";
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {showKeywordGroupHandle(xmlHttp);};
	xmlHttp.send(null);
}
function showKeywordGroupHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showKeywordGroups(xmlHttp);
		}
	}
}
function showKeywordGroups(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var keywordGroup = null;
	var id = "";
	var name = "";
	var keywordGroups = results.getElementsByTagName("keywordGroup");
	var keywordGroupSelect = document.getElementById("keywordGroupSelect");
	clearSelectBox(keywordGroupSelect);
	for(var i = 0;i < keywordGroups.length;i++) {
		keywordGroup = keywordGroups[i];
		id = keywordGroup.getElementsByTagName("id")[0].firstChild.nodeValue;
		name = decodeURI(decodeURI(keywordGroup.getElementsByTagName("name")[0].firstChild.nodeValue));
		addSelectBox(keywordGroupSelect,id,name);
	}
}
// 新增一条关键字过滤
function addKeywordFiltration() {
	if(!checkIsNull()) {
		return;
	}
	if(document.getElementById("keywordGroupSelect").length == 0) {
		alert("请先定义关键字组!");
		return;
	}
	var xmlHttp = createXMLHttpRequest();
	var url = "AddKeywordFiltration.action?" + getKeywordFiltrationQueryUrl();
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {showKeywordFiltrationHandle(xmlHttp);};
	xmlHttp.send(null);
}
function showKeywordFiltrationHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showKeywordFiltrations(xmlHttp);
		}
	}
}
function showKeywordFiltrations(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("keywordFiltrationList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var row = document.createElement("tr");
	tbody.appendChild(row);
	var keywordGroupSelect = document.getElementById("keywordGroupSelect");
	var keywordTimeGroup = document.getElementById("keywordTimeGroup");
	var index = list.rows.length - 1;
	addTableRow(row,"<input type='checkbox' name='keywordFiltrationCheck' value='" + results.getElementsByTagName("keywordFiltrationId")[0].firstChild.nodeValue + "'/>");
	addTableRow(row,list.rows.length - 1);
	addTableRow(row,getTextByValue(keywordGroupSelect,results.getElementsByTagName("keywordGroupId")[0].firstChild.nodeValue));
	addTableRow(row,decodeURI(decodeURI(results.getElementsByTagName("description")[0].firstChild.nodeValue)));
	addTableRow(row,decodeURI(decodeURI(results.getElementsByTagName("enable")[0].firstChild.nodeValue)));
	addTableRow(row,getTextByValue(keywordTimeGroup,results.getElementsByTagName("timeGroupId")[0].firstChild.nodeValue));
	addTableRow(row,'<a href="javascript:enableKeywordFiltration(\'' + results.getElementsByTagName("keywordFiltrationId")[0].firstChild.nodeValue
			 + '\',\'allow\',' + index + ')">允许</a>&nbsp;&nbsp;<a href="javascript:enableKeywordFiltration(\''
			 + results.getElementsByTagName("keywordFiltrationId")[0].firstChild.nodeValue + '\',\'forbidden\',' + index + ')">拒绝</a>');
	document.getElementsByName("strategyId")[0].value = results.getElementsByTagName("id")[0].firstChild.nodeValue;
	document.getElementsByName("hiddenIpGroupId")[0].value = results.getElementsByTagName("hiddenIpGroupId")[0].firstChild.nodeValue;
}
function getKeywordFiltrationQueryUrl() {
	var url = "";
	url += "strategyId=" + document.getElementsByName("strategyId")[0].value;
	url += "&strategyName=" + encodeURI(encodeURI(document.getElementsByName("strategyName")[0].value));
	url += "&strategyDescription=" + encodeURI(encodeURI(document.getElementsByName("strategyDescription")[0].value));
	url += "&ipGroupId=" + document.getElementsByName("ipGroupId")[0].value;
	url += "&hiddenIpGroupId=" + document.getElementsByName("hiddenIpGroupId")[0].value;
	var keywordGroupSelect = document.getElementById("keywordGroupSelect");
	url += "&keywordGroupId=" + keywordGroupSelect.options[keywordGroupSelect.selectedIndex].value;
	var keywordTimeGroup = document.getElementById("keywordTimeGroup");
	url += "&timeGroupId=" + keywordTimeGroup.options[keywordTimeGroup.selectedIndex].value;
	var radio = document.getElementsByName("keywordFiltrationEnable");
	var enable = null;
	for(var i = 0;i < radio.length;i++) {
		if(radio[i].checked) {
			enable = radio[i].value;
			break;
		}
	}
	url += "&enable=" + enable;
	return url;
}
// 允许/拒绝关键字过滤
function enableKeywordFiltration(id,type,index) {
	var xmlHttp = createXMLHttpRequest();
	var strategyId = document.getElementsByName("strategyId")[0].value;
	var url = "EnableKeywordFiltration.action?strategyId=" + strategyId + "&id=" + id + "&enable=" + type;
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {enableKeywordFiltrationHandle(xmlHttp,index);};
	xmlHttp.send(null);
}
function enableKeywordFiltrationHandle(xmlHttp,index) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showEnabledKeywordFiltration(xmlHttp,index);
		}
	}
}
function showEnabledKeywordFiltration(xmlHttp,index) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("keywordFiltrationList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var enable = decodeURI(decodeURI(results.getElementsByTagName("enable")[0].firstChild.nodeValue));
	list.rows[index].cells[4].innerHTML = enable;
}
// 删除选中的关键字过滤
function deleteKeywordFiltrations() {
	var strategyId = document.getElementsByName("strategyId")[0].value;
	var checkbox = document.getElementsByName("keywordFiltrationCheck");
	if(checkbox != null && checkbox.length > 0) {
		var total = 0;// make a flag to the number of checkbox selected
		var ruleId = "";
		for(var i = 0;i < checkbox.length;i++) {
			if(checkbox[i].checked) {
				total++;
				ruleId += checkbox[i].value + "AND";
			}
		}
		if(total == 0) {
			alert("请选择要删除的规则!");
		} else if(confirm("确定要删除选中的规则吗?")) {
			var xmlHttp = createXMLHttpRequest();
			var url = "DeleteKeywordFiltrations.action?strategyId=" + strategyId + "&id=" + ruleId;
			xmlHttp.open("GET",url,true);
			xmlHttp.onreadystatechange = function() {DeleteKeywordFiltrationsHandle(xmlHttp);};
			xmlHttp.send(null);
		}
	}
}
function DeleteKeywordFiltrationsHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showDeletedKeywordFiltrations(xmlHttp);
		}
	}
}
function showDeletedKeywordFiltrations(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("keywordFiltrationList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var result = results.getElementsByTagName("result")[0].firstChild.nodeValue;
	if(result == "success") {
		var checkbox = document.getElementsByName("keywordFiltrationCheck");
		if(checkbox != null && checkbox.length > 0) {
			for(var i = checkbox.length - 1;i >= 0;i--) {
				if(checkbox[i].checked) {
					tbody.removeChild(tbody.childNodes[i + 1]);
				}
			}
			for(var index = 1;index < tbody.childNodes.length;index++) {
				tbody.rows[index].cells[1].innerHTML = index;
				tbody.rows[index].cells[6].innerHTML = 	'<a href="javascript:enableKeywordFiltration(\'' + checkbox[index - 1].value
			 + '\',\'allow\',' + index + ')">允许</a>&nbsp;&nbsp;<a href="javascript:enableKeywordFiltration(\''
			 + checkbox[index - 1].value + '\',\'forbidden\',' + index + ')">拒绝</a>';
			}
		}
	}
}
// 获取文件类型组
function getFileTypeGroups() {
	var xmlHttp = createXMLHttpRequest();
	var url = "GetFileTypeGroups.action";
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {showFileTypeGroupHandle(xmlHttp);};
	xmlHttp.send(null);
}
function showFileTypeGroupHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showFileTypeGroups(xmlHttp);
		}
	}
}
function showFileTypeGroups(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var fileTypeGroup = null;
	var id = "";
	var name = "";
	var fileTypeGroups = results.getElementsByTagName("fileTypeGroup");
	var fileTypeGroupSelect = document.getElementById("fileTypeGroupSelect");
	clearSelectBox(fileTypeGroupSelect);
	for(var i = 0;i < fileTypeGroups.length;i++) {
		fileTypeGroup = fileTypeGroups[i];
		id = fileTypeGroup.getElementsByTagName("id")[0].firstChild.nodeValue;
		name = decodeURI(decodeURI(fileTypeGroup.getElementsByTagName("name")[0].firstChild.nodeValue));
		addSelectBox(fileTypeGroupSelect,id,name);
	}
}
// 新增一条文件类型过滤
function addFileTypeFiltration() {
	if(!checkIsNull()) {
		return;
	}
	if(document.getElementById("fileTypeGroupSelect").length == 0) {
		alert("请先定义文件类型组!");
		return;
	}
	var xmlHttp = createXMLHttpRequest();
	var url = "AddFileTypeFiltration.action?" + getFileTypeFiltrationQueryUrl();
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {showFileTypeFiltrationHandle(xmlHttp);};
	xmlHttp.send(null);
}
function showFileTypeFiltrationHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showFileTypeFiltrations(xmlHttp);
		}
	}
}
function showFileTypeFiltrations(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("fileTypeFiltrationList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var row = document.createElement("tr");
	tbody.appendChild(row);
	var fileTypeGroupSelect = document.getElementById("fileTypeGroupSelect");
	var fileTypeTimeGroup = document.getElementById("fileTypeTimeGroup");
	var index = list.rows.length - 1;
	addTableRow(row,"<input type='checkbox' name='fileTypeFiltrationCheck' value='" + results.getElementsByTagName("fileTypeFiltrationId")[0].firstChild.nodeValue + "'/>");
	addTableRow(row,list.rows.length - 1);
	addTableRow(row,getTextByValue(fileTypeGroupSelect,results.getElementsByTagName("fileTypeGroupId")[0].firstChild.nodeValue));
	addTableRow(row,decodeURI(decodeURI(results.getElementsByTagName("description")[0].firstChild.nodeValue)));
	addTableRow(row,decodeURI(decodeURI(results.getElementsByTagName("enable")[0].firstChild.nodeValue)));
	addTableRow(row,getTextByValue(fileTypeTimeGroup,results.getElementsByTagName("timeGroupId")[0].firstChild.nodeValue));
	addTableRow(row,'<a href="javascript:enableFileTypeFiltration(\'' + results.getElementsByTagName("fileTypeFiltrationId")[0].firstChild.nodeValue
			 + '\',\'allow\',' + index + ')">允许</a>&nbsp;&nbsp;<a href="javascript:enableFileTypeFiltration(\''
			 + results.getElementsByTagName("fileTypeFiltrationId")[0].firstChild.nodeValue + '\',\'forbidden\',' + index + ')">拒绝</a>');
	document.getElementsByName("strategyId")[0].value = results.getElementsByTagName("id")[0].firstChild.nodeValue;
	document.getElementsByName("hiddenIpGroupId")[0].value = results.getElementsByTagName("hiddenIpGroupId")[0].firstChild.nodeValue;
}
function getFileTypeFiltrationQueryUrl() {
	var url = "";
	url += "strategyId=" + document.getElementsByName("strategyId")[0].value;
	url += "&strategyName=" + encodeURI(encodeURI(document.getElementsByName("strategyName")[0].value));
	url += "&strategyDescription=" + encodeURI(encodeURI(document.getElementsByName("strategyDescription")[0].value));
	url += "&ipGroupId=" + document.getElementsByName("ipGroupId")[0].value;
	url += "&hiddenIpGroupId=" + document.getElementsByName("hiddenIpGroupId")[0].value;
	var fileTypeGroupSelect = document.getElementById("fileTypeGroupSelect");
	url += "&fileTypeGroupId=" + fileTypeGroupSelect.options[fileTypeGroupSelect.selectedIndex].value;
	var fileTypeTimeGroup = document.getElementById("fileTypeTimeGroup");
	url += "&timeGroupId=" + fileTypeTimeGroup.options[fileTypeTimeGroup.selectedIndex].value;
	var radio = document.getElementsByName("fileTypeFiltrationEnable");
	var enable = null;
	for(var i = 0;i < radio.length;i++) {
		if(radio[i].checked) {
			enable = radio[i].value;
			break;
		}
	}
	url += "&enable=" + enable;
	return url;
}
// 允许/拒绝文件类型过滤
function enableFileTypeFiltration(id,type,index) {
	var xmlHttp = createXMLHttpRequest();
	var strategyId = document.getElementsByName("strategyId")[0].value;
	var url = "EnableFileTypeFiltration.action?strategyId=" + strategyId + "&id=" + id + "&enable=" + type;
	xmlHttp.open("GET",url,true);
	xmlHttp.onreadystatechange = function() {enableFileTypeFiltrationHandle(xmlHttp,index);};
	xmlHttp.send(null);
}
function enableFileTypeFiltrationHandle(xmlHttp,index) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showEnabledFileTypeFiltration(xmlHttp,index);
		}
	}
}
function showEnabledFileTypeFiltration(xmlHttp,index) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("fileTypeFiltrationList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var enable = decodeURI(decodeURI(results.getElementsByTagName("enable")[0].firstChild.nodeValue));
	list.rows[index].cells[4].innerHTML = enable;
}
// 删除选中的文件类型过滤
function deleteFileTypeFiltrations() {
	var strategyId = document.getElementsByName("strategyId")[0].value;
	var checkbox = document.getElementsByName("fileTypeFiltrationCheck");
	if(checkbox != null && checkbox.length > 0) {
		var total = 0;// make a flag to the number of checkbox selected
		var ruleId = "";
		for(var i = 0;i < checkbox.length;i++) {
			if(checkbox[i].checked) {
				total++;
				ruleId += checkbox[i].value + "AND";
			}
		}
		if(total == 0) {
			alert("请选择要删除的规则!");
		} else if(confirm("确定要删除选中的规则吗?")) {
			var xmlHttp = createXMLHttpRequest();
			var url = "DeleteFileTypeFiltrations.action?strategyId=" + strategyId + "&id=" + ruleId;
			xmlHttp.open("GET",url,true);
			xmlHttp.onreadystatechange = function() {DeleteFileTypeFiltrationsHandle(xmlHttp);};
			xmlHttp.send(null);
		}
	}
}
function DeleteFileTypeFiltrationsHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showDeletedFileTypeFiltrations(xmlHttp);
		}
	}
}
function showDeletedFileTypeFiltrations(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var list = document.getElementById("fileTypeFiltrationList");
	var tbody = list.getElementsByTagName("tbody")[0];
	var result = results.getElementsByTagName("result")[0].firstChild.nodeValue;
	if(result == "success") {
		var checkbox = document.getElementsByName("fileTypeFiltrationCheck");
		if(checkbox != null && checkbox.length > 0) {
			for(var i = checkbox.length - 1;i >= 0;i--) {
				if(checkbox[i].checked) {
					tbody.removeChild(tbody.childNodes[i + 1]);
				}
			}
			for(var index = 1;index < tbody.childNodes.length;index++) {
				tbody.rows[index].cells[1].innerHTML = index;
				tbody.rows[index].cells[6].innerHTML = 	'<a href="javascript:enableFileTypeFiltration(\'' + checkbox[index - 1].value
			 + '\',\'allow\',' + index + ')">允许</a>&nbsp;&nbsp;<a href="javascript:enableFileTypeFiltration(\''
			 + checkbox[index - 1].value + '\',\'forbidden\',' + index + ')">拒绝</a>';
			}
		}
	}
}
// 设置发送和接收邮件单选文本框是否只读
function changeRadioReadonly(value) {
	var refuseMail = document.getElementsByName("mailSendBean.refuseMail")[0];
	var allowMail = document.getElementsByName("mailSendBean.allowMail")[0];
	refuseMail.readOnly = true;
	allowMail.readOnly = true;
	if(value == "refuse") {
		refuseMail.readOnly = false;
	} else if(value == "allow") {
		allowMail.readOnly = false;
	}
}
// 设置发送和接收邮件复选文本框是否只读
function changeCheckboxReadonly(widgetName,index,fieldName) {
	var widget = document.getElementsByName(widgetName);
	if(widget != null && widget.length > 0) {
		if(widget[index].checked) {
			document.getElementsByName(fieldName)[0].readOnly = false;
		} else {
			document.getElementsByName(fieldName)[0].readOnly = true;
		}
	}
}
// 配置发送或接收邮件过滤规则
function configMailSendRule() {
	if(!checkIsNull()) {
		return;
	}
	var xmlHttp = createXMLHttpRequest();
	var url = "ConfigMailSendRule.action?";
	var queryUrl = getMailSendRuleQueryUrl();
	xmlHttp.open("POST",url,true);
	xmlHttp.onreadystatechange = function() {showMailSendRuleHandle(xmlHttp);};
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");
	xmlHttp.send(queryUrl);
}
function showMailSendRuleHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showMailSendRule(xmlHttp);
		}
	}
}
function showMailSendRule(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var result = results.getElementsByTagName("result")[0].firstChild.nodeValue;
	var div = document.getElementById("showSendMailMessage");
	if(result == "success") {
		document.getElementsByName("strategyId")[0].value = results.getElementsByTagName("id")[0].firstChild.nodeValue;
		document.getElementsByName("hiddenIpGroupId")[0].value = results.getElementsByTagName("hiddenIpGroupId")[0].firstChild.nodeValue;
		div.innerHTML = "<font color='green'>" + results.getElementsByTagName("message")[0].firstChild.nodeValue + "</font>";
	} else {
		div.innerHTML = "<font color='red'>" + results.getElementsByTagName("message")[0].firstChild.nodeValue + "</font>";
	}
}
function getMailSendRuleQueryUrl() {
	var url = "";
	url += "strategyId=" + document.getElementsByName("strategyId")[0].value;
	url += "&strategyName=" + document.getElementsByName("strategyName")[0].value;
	url += "&strategyDescription=" + document.getElementsByName("strategyDescription")[0].value;
	url += "&ipGroupId=" + document.getElementsByName("ipGroupId")[0].value;
	url += "&hiddenIpGroupId=" + document.getElementsByName("hiddenIpGroupId")[0].value;
	var radio = document.getElementsByName("mailStrategyEnable");
	var enable = null;
	for(var i = 0;i < radio.length;i++) {
		if(radio[i].checked) {
			enable = radio[i].value;
			break;
		}
	}
	url += "&enable=" + enable;
	var mailSendRadio = document.getElementsByName("mailSendRadio");
	var mailSendEnable = null;
	for(var i = 0;i < mailSendRadio.length;i++) {
		if(mailSendRadio[i].checked) {
			mailSendEnable = mailSendRadio[i].value;
			break;
		}
	}
	url += "&mailSendEnable=" + mailSendEnable;
	var mailAddress = null;
	if(mailSendRadio[0].checked) {
		mailAddress = document.getElementsByName("mailSendBean.refuseMail")[0].value;
	} else if(mailSendRadio[1].checked) {
		mailAddress = document.getElementsByName("mailSendBean.allowMail")[0].value;
	}
	url += "&mailAddress=" + mailAddress;
	var mailContentBox = document.getElementsByName("mailContentBox");
	if(mailContentBox[0].checked) {
		url += "&keyword=" + document.getElementsByName("mailSendBean.keywordContent")[0].value;
	}
	if(mailContentBox[1].checked) {
		url += "&fileType=" + document.getElementsByName("mailSendBean.fileTypeContent")[0].value;
	}
	var enableSpamMail = document.getElementsByName("mailSendBean.enableSpamMail");
	var spamMailEnable = null;
	if(enableSpamMail[0].checked) {
		spamMailEnable = "true";
	} else {
		spamMailEnable = "false";
	}
	url += "&spamMailEnable=" + spamMailEnable;
	return url;
}
// 配置延迟审计规则
function configMailDelayRule() {
	if(!checkIsNull()) {
		return;
	}
	var xmlHttp = createXMLHttpRequest();
	var url = "ConfigMailDelayRule.action?";
	var queryUrl = getMailDelayRuleQueryUrl();
	xmlHttp.open("POST",url,true);
	xmlHttp.onreadystatechange = function() {showMailDelayRuleHandle(xmlHttp);};
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");
	xmlHttp.send(queryUrl);
}
function showMailDelayRuleHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showMailDelayRule(xmlHttp);
		}
	}
}
function showMailDelayRule(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var result = results.getElementsByTagName("result")[0].firstChild.nodeValue;
	var div = document.getElementById("showDelayMailMessage");
	if(result == "success") {
		document.getElementsByName("strategyId")[0].value = results.getElementsByTagName("id")[0].firstChild.nodeValue;
		document.getElementsByName("hiddenIpGroupId")[0].value = results.getElementsByTagName("hiddenIpGroupId")[0].firstChild.nodeValue;
		div.innerHTML = "<font color='green'>" + results.getElementsByTagName("message")[0].firstChild.nodeValue + "</font>";
	} else {
		div.innerHTML = "<font color='red'>" + results.getElementsByTagName("message")[0].firstChild.nodeValue + "</font>";
	}
}
function getMailDelayRuleQueryUrl() {
	var url = "";
	url += "strategyId=" + document.getElementsByName("strategyId")[0].value;
	url += "&strategyName=" + document.getElementsByName("strategyName")[0].value;
	url += "&strategyDescription=" + document.getElementsByName("strategyDescription")[0].value;
	url += "&ipGroupId=" + document.getElementsByName("ipGroupId")[0].value;
	url += "&hiddenIpGroupId=" + document.getElementsByName("hiddenIpGroupId")[0].value;
	var radio = document.getElementsByName("mailStrategyEnable");
	var enable = null;
	for(var i = 0;i < radio.length;i++) {
		if(radio[i].checked) {
			enable = radio[i].value;
			break;
		}
	}
	url += "&enable=" + enable;
	var enableDelayMail = document.getElementsByName("mailDelayBean.enableDelayMail");
	var mailDelayEnable = null;
	if(enableDelayMail[0].checked) {
		mailDelayEnable = "true";
	} else {
		mailDelayEnable = "false";
	}
	url += "&mailDelayEnable=" + mailDelayEnable;
	var delayMailBox = document.getElementsByName("delayMailBox");
	if(delayMailBox[0].checked) {
		url += "&allowDelayMail=" + document.getElementsByName("mailDelayBean.allowDelayMail")[0].value;
	}
	if(delayMailBox[1].checked) {
		url += "&refuseDelayMail=" + document.getElementsByName("mailDelayBean.refuseDelayMail")[0].value;
	}
	var delayMailContentBox = document.getElementsByName("delayMailContentBox");
	if(delayMailContentBox[0].checked) {
		url += "&mailSize=" + document.getElementsByName("mailDelayBean.mailSize")[0].value;
	}
	if(delayMailContentBox[1].checked) {
		url += "&appendixNum=" + document.getElementsByName("mailDelayBean.appendixNum")[0].value;
	}
	if(delayMailContentBox[2].checked) {
		url += "&keyword=" + document.getElementsByName("mailDelayBean.delayMailContent")[0].value;
	}	
	var mailNoticeBox = document.getElementsByName("mailNoticeBox");
	if(mailNoticeBox[0].checked) {
		url += "&mailAddress=" + document.getElementsByName("mailDelayBean.mailAddress")[0].value;
	}
	return url;
}
// 配置流量与上网时长统计
function configTimeStatistics() {
	if(!checkIsNull()) {
		return;
	}
	var xmlHttp = createXMLHttpRequest();
	var url = "ConfigTimeStatistics.action?";
	var queryUrl = getTimeStatisticsQueryUrl();
	xmlHttp.open("POST",url,true);
	xmlHttp.onreadystatechange = function() {showTimeStatisticsHandle(xmlHttp);};
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");
	xmlHttp.send(queryUrl);
}
function showTimeStatisticsHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showTimeStatistics(xmlHttp);
		}
	}
}
function showTimeStatistics(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var result = results.getElementsByTagName("result")[0].firstChild.nodeValue;
	var div = document.getElementById("showTimeStatisticsMessage");
	if(result == "success") {
		document.getElementsByName("strategyId")[0].value = results.getElementsByTagName("id")[0].firstChild.nodeValue;
		document.getElementsByName("hiddenIpGroupId")[0].value = results.getElementsByTagName("hiddenIpGroupId")[0].firstChild.nodeValue;
		div.innerHTML = "<font color='green'>" + results.getElementsByTagName("message")[0].firstChild.nodeValue + "</font>";
	} else {
		div.innerHTML = "<font color='red'>" + results.getElementsByTagName("message")[0].firstChild.nodeValue + "</font>";
	}
}
function getTimeStatisticsQueryUrl() {
	var url = "";
	url += "strategyId=" + document.getElementsByName("strategyId")[0].value;
	url += "&strategyName=" + document.getElementsByName("strategyName")[0].value;
	url += "&strategyDescription=" + document.getElementsByName("strategyDescription")[0].value;
	url += "&ipGroupId=" + document.getElementsByName("ipGroupId")[0].value;
	url += "&hiddenIpGroupId=" + document.getElementsByName("hiddenIpGroupId")[0].value;
	var enableFluxStatistics = document.getElementsByName("fluxTimeStatisticsBean.enableFluxStatistics");
	var fluxStatisticsEnable = null;
	if(enableFluxStatistics[0].checked) {
		fluxStatisticsEnable = "true";
	} else {
		fluxStatisticsEnable = "false";
	}
	url += "&fluxStatisticsEnable=" + fluxStatisticsEnable;
	
	var enableTimeStatistics = document.getElementsByName("fluxTimeStatisticsBean.enableTimeStatistics");
	var timeStatisticsEnable = null;
	if(enableTimeStatistics[0].checked) {
		timeStatisticsEnable = "true";
	} else {
		timeStatisticsEnable = "false";
	}
	url += "&timeStatisticsEnable=" + timeStatisticsEnable;
	
	return url;
}
// 配置上网时间控制
function configTimeControl() {
	if(!checkIsNull()) {
		return;
	}
	var xmlHttp = createXMLHttpRequest();
	var url = "ConfigTimeControl.action?";
	var queryUrl = getTimeControlQueryUrl();
	xmlHttp.open("POST",url,true);
	xmlHttp.onreadystatechange = function() {showTimeControlHandle(xmlHttp);};
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");
	xmlHttp.send(queryUrl);
}
function showTimeControlHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showTimeControl(xmlHttp);
		}
	}
}
function showTimeControl(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var result = results.getElementsByTagName("result")[0].firstChild.nodeValue;
	var div = document.getElementById("showTimeControlMessage");
	if(result == "success") {
		document.getElementsByName("strategyId")[0].value = results.getElementsByTagName("id")[0].firstChild.nodeValue;
		document.getElementsByName("hiddenIpGroupId")[0].value = results.getElementsByTagName("hiddenIpGroupId")[0].firstChild.nodeValue;
		div.innerHTML = "<font color='green'>" + results.getElementsByTagName("message")[0].firstChild.nodeValue + "</font>";
	} else {
		div.innerHTML = "<font color='red'>" + results.getElementsByTagName("message")[0].firstChild.nodeValue + "</font>";
	}
}
function getTimeControlQueryUrl() {
	var url = "";
	url += "strategyId=" + document.getElementsByName("strategyId")[0].value;
	url += "&strategyName=" + document.getElementsByName("strategyName")[0].value;
	url += "&strategyDescription=" + document.getElementsByName("strategyDescription")[0].value;
	url += "&ipGroupId=" + document.getElementsByName("ipGroupId")[0].value;
	url += "&hiddenIpGroupId=" + document.getElementsByName("hiddenIpGroupId")[0].value;
	var enableTimeControl = document.getElementsByName("fluxTimeStatisticsBean.enableTimeControl");
	var timeControlEnable = null;
	if(enableTimeControl[0].checked) {
		timeControlEnable = "true";
	} else {
		timeControlEnable = "false";
	}
	url += "&timeControlEnable=" + timeControlEnable;
	var controlTimeGroup = document.getElementById("controlTimeGroup");
	url += "&timeGroupId=" + controlTimeGroup.options[controlTimeGroup.selectedIndex].value;
	url += "&maxTime=" + document.getElementsByName("fluxTimeStatisticsBean.maxTime")[0].value;
	url += "&excludePort=" + document.getElementsByName("fluxTimeStatisticsBean.excludePort")[0].value;
	return url;
}
// 配置连接数控制
function configConnectNum() {
	if(!checkIsNull()) {
		return;
	}
	var xmlHttp = createXMLHttpRequest();
	var url = "ConfigConnectNum.action?";
	var queryUrl = getConnectNumQueryUrl();
	xmlHttp.open("POST",url,true);
	xmlHttp.onreadystatechange = function() {showConnectNumHandle(xmlHttp);};
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");
	xmlHttp.send(queryUrl);
}
function showConnectNumHandle(xmlHttp) {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status ==200) {
			showConnectNum(xmlHttp);
		}
	}
}
function showConnectNum(xmlHttp) {
	var results = xmlHttp.responseXML;
	if(checkSessionIsOut(results)) {
		return;
	}
	var result = results.getElementsByTagName("result")[0].firstChild.nodeValue;
	var div = document.getElementById("showConnectNumMessage");
	if(result == "success") {
		document.getElementsByName("strategyId")[0].value = results.getElementsByTagName("id")[0].firstChild.nodeValue;
		document.getElementsByName("hiddenIpGroupId")[0].value = results.getElementsByTagName("hiddenIpGroupId")[0].firstChild.nodeValue;
		div.innerHTML = "<font color='green'>" + results.getElementsByTagName("message")[0].firstChild.nodeValue + "</font>";
	} else {
		div.innerHTML = "<font color='red'>" + results.getElementsByTagName("message")[0].firstChild.nodeValue + "</font>";
	}
}
function getConnectNumQueryUrl() {
	var url = "";
	url += "strategyId=" + document.getElementsByName("strategyId")[0].value;
	url += "&strategyName=" + document.getElementsByName("strategyName")[0].value;
	url += "&strategyDescription=" + document.getElementsByName("strategyDescription")[0].value;
	url += "&ipGroupId=" + document.getElementsByName("ipGroupId")[0].value;
	url += "&hiddenIpGroupId=" + document.getElementsByName("hiddenIpGroupId")[0].value;
	var enableConnectNum = document.getElementsByName("fluxTimeStatisticsBean.enableConnectNum");
	var connectNumEnable = null;
	if(enableConnectNum[0].checked) {
		connectNumEnable = "true";
	} else {
		connectNumEnable = "false";
	}
	url += "&connectNumEnable=" + connectNumEnable;
	url += "&maxConnectNum=" + document.getElementsByName("fluxTimeStatisticsBean.maxConnectNum")[0].value;
	return url;
}