<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>" />

<title><s:text name="content.approvalManage.title" /></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="pages/js/validator.js"></script>
<script type="text/javascript" src="pages/js/jquery.js"></script>
<script type="text/javascript" src="pages/js/strategy.js"></script>
<script type="text/javascript" src="pages/js/common.js"></script>
<script language="JavaScript" src="pages/js/partitionPageAndSearch.js"></script>
<script type="text/javascript" src="pages/js/xtree.js"></script>
<script type="text/javascript" src="pages/js/xmlextras.js"></script>
<script type="text/javascript" src="pages/js/xloadtree.js"></script>
<script type="text/javascript" src="pages/js/map.js"></script>
<script type="text/javascript" src="pages/js/checkboxTreeItem.js"></script>
<script type="text/javascript" src="pages/js/checkboxXLoadTree.js"></script>
<script type="text/javascript" src="pages/js/radioTreeItem.js"></script>
<script type="text/javascript" src="pages/js/radioXLoadTree.js"></script>
<script type="text/javascript" src="pages/js/WdatePicker/WdatePicker.js"
	defer="defer"></script>
<script type="text/javascript"
	src="pages/js/WdatePicker/js/lang/zh-cn.js"></script>
<link href="pages/css/xtree.css" rel="stylesheet" type="text/css">
<link href="pages/css/unicode_cn.css" rel="stylesheet" type="text/css">
<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
<link href="pages/css/highguardnetworks_cn.css" rel="stylesheet"
	type="text/css">
</head>
<script type="text/javascript">
	function back() {
		document.input.action = "positionList.action";
		document.input.submit();
	}

	function adduser() {

		document.input.submit();
	}

	function createXMLHttpRequest() {
		if (window.ActiveXObject) {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		} else if (window.XMLHttpRequest) {
			xmlHttp = new XMLHttpRequest();
		}
		return xmlHttp;
	}
	function testConn() {
		var no = document.getElementsByName("user.no")[0].value;

		var xmlHttp = createXMLHttpRequest();
		var url = "/Wisdom/ConnTestAjax.action?no=" + no;
		xmlHttp.open("GET", url, true);
		xmlHttp.setRequestHeader("If-Modified-Since", '0');//解决缓存问题
		xmlHttp.onreadystatechange = function() {
			testConnHandle();
		};
		xmlHttp.send(null);
	}

	function testConnHandle() {
		if (xmlHttp.readyState == 4) {
			if (xmlHttp.status == 200) {
				var text = xmlHttp.responseText;
				var result;
				if (text == 1) {
					alert("存在重复ID号！");
				} else if (text == 0) {
					adduser();
				}
			}
		}
	}
</script>
<script language="javascript" src="/javascript/JumpMenu.js"></script>
<body>
<s:form name="input" action="editPosition.action" method="post"
	enctype="multipart/form-data">
	<table width="100%" border="0" cellpadding="0" cellspacing="10"
		class="NormalText">
		<input type="hidden" name="position.id" id="position.id"
			value="<s:property value="position.id"/>" />
		<tr>
			<td class="TipWord">添加职务</td>
		</tr>
		<tr>
			<td class="activeFenceLine"></td>
		</tr>
	</table>&nbsp;&nbsp;&nbsp;<font color="red"><s:fielderror /></font>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="10"
		class="NormalText">
		<tr>
			<td>
			<fieldset class="FieldsetBorder"><legend
				class="LegendTitle">职务信息</legend>
			<table width="100%" align="center" border="1" cellspacing="0"
				cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
				frame="void" rules="rows" class="NormalText">
				
			
			
			
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>用户层级:</td>
					<td align="left"><s:textfield name="position.hierarchy"
						size="30" cssClass="Input" /></td>
					<td>职务代码:</td>
					<td align="left"><s:textfield name="position.num" size="60"
						cssClass="Input" /></td>
				</tr>
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>职务名称:</td>
					<td align="left"><s:textfield name="position.name"
						size="30" cssClass="Input" /></td>
					<td>显示名称:</td>
					<td align="left"><s:textfield name="position.showname" size="60"
						cssClass="Input" /></td>
				</tr>
			</table>
			</fieldset>
			</td>
		</tr>
		<tr>
			<td>
			<table width="100%" align="center" border="1" cellspacing="0"
				cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
				frame="void" rules="rows">
				<tr>
					<td width="3%"></td>
					<td><input type="button" name="ok" value="保存"
						class="FixButtonStyle" onclick=
							adduser();
> <input
						type="button" name="cancel" value="取消" class="FixButtonStyle"
						onClick=
	back();
></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>

