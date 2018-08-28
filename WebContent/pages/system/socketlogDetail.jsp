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

<link rel="stylesheet" type="text/css" href="pages/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="pages/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="pages/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="pages/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">









	function back() {
		document.input.action = "userList.action";
		document.input.submit();
	}

	function adduser() {
		var name = document.getElementsByName("user.name")[0].value;
		var wx = document.getElementsByName("user.wx")[0].value;
		var file1 = document.getElementsByName("otherupload")[0].value;
		
		if (null == name || "" == name) {
			alert("姓名不能为空！");
			return false;
		}
		if (null == wx || "" == wx) {
			alert("微信不能为空！");
			return false;
		}
		document.input.submit();
	}


	function serachJS(){
		var num = document.getElementsByName("num")[0].value;
		   $.post("getICportCard.action",{"num":num},function(data){
			   document.getElementsByName("user.card")[0].value=data;
		         },"text");
	}


</script>
<script language="javascript" src="/javascript/JumpMenu.js"></script>
<body>
<s:form name="input" action="addUser.action" method="post"
	enctype="multipart/form-data">
	<table width="100%" border="0" cellpadding="0" cellspacing="10"
		class="NormalText">
		<input type="hidden" name="user.id" id="user.id"
			value="<s:property value="user.id"/>" />
		<tr>
			<td class="TipWord">格子详细信息</td>
		</tr>
         <input type="hidden"  name="user.deptid" id="parentId" value="<s:property value="department.deptid"/>" >
		
		<tr>
			<td class="activeFenceLine"></td>
		</tr>
	</table>&nbsp;&nbsp;&nbsp;<font color="red"> <span>&nbsp;&nbsp; ${resultMes }</span><s:fielderror /></font>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="10"
		class="NormalText">
		<tr>
			<td>
			    
			    <s:iterator value="list" status="index">
			       <s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" /> &nbsp;&nbsp;<s:property value="type" /> &nbsp;&nbsp;<s:property value="action"></s:property></br>
			    </s:iterator>
			    
			</td>
		</tr>
		 
	</table>
</s:form>
</body>
</html>

