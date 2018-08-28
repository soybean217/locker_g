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
			<td class="TipWord">添加本地用户</td>
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
			<fieldset class="FieldsetBorder"><legend
				class="LegendTitle">用户信息</legend>
			<table width="100%" align="center" border="1" cellspacing="0"
				cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
				frame="void" rules="rows" class="NormalText">
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>用户ID:</td>
					<td align="left" colspan=><s:textfield name="user.uuid" readonly="true" size="40"
						cssClass="Input" /></td>
						<td>角色:</td>
					<td align="left"><input type="radio"  name="user.type" value="1">超级管理员<input type="radio" checked="checked" name="user.type" value="2">普通用户</td>
						
					
				</tr>
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>姓名:</td>
					<td align="left"><s:textfield name="user.name"
						size="30" cssClass="Input" /></td>
					<td>性别:</td>
					<td align="left"><input type="radio" checked="checked" name="user.sex" value="男">男<input type="radio"  name="user.sex" value="女">女</td>
				</tr>
			<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>电话:</td>
					<td align="left"><s:textfield name="user.telephone"
						size="30" cssClass="Input" /></td>
					<td>卡号:</td>
					<td align="left"><s:textfield name="user.card" size="60"
						cssClass="Input" />
						</br>
						编号：<s:textfield name="num" id="num" size="30"
						cssClass="Input" /><input type="button" value="检索" name="检索" onclick="javascript:serachJS();" />
						
						</td>
				</tr>
				
				
		
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>微信号:</td>
					<td align="left"><s:textfield name="user.wx" size="30"
						cssClass="Input" /></td>
					<td>身份证号:</td>
					<td align="left"><s:textfield name="user.sfz" size="30"
						cssClass="Input" /></td>
				</tr>
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td >身份证照片：</td>
					<td align="left"><s:file name="upload"
						label="请选择照片" /></td>
					<td >其他证件照片：</td>
					<td align="left"><s:file name="otherupload"
						label="请选择照片" /></td>
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

