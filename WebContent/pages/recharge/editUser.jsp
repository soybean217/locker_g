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
	<link rel="stylesheet" type="text/css" href="pages/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="pages/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="pages/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="pages/js/jquery.ztree.excheck-3.5.js"></script>
</head>
<script type="text/javascript">

	function back() {
		document.input.action = "rechargeUserList.action";
		document.input.submit();
	}
	function adduser() {
		var name = document.getElementsByName("user.name")[0].value;
		
		if (null == name || "" == name) {
			alert("姓名不能为空！");
			return false;
		}

		document.input.submit();
	}
	
	
	
	function changeimg() {
		document.getElementById("showimg").style.display = "none";
		document.getElementById("uploadimg").style.display = "block";
	}
	
	
	function changeimg1() {
		document.getElementById("showimg1").style.display = "none";
		document.getElementById("uploadimg1").style.display = "block";
	}

</script>
<script language="javascript" src="/javascript/JumpMenu.js"></script>
<body>
<s:form name="input" action="editRechargeUser.action" method="post"
	enctype="multipart/form-data">
	<table width="100%" border="0" cellpadding="0" cellspacing="10"
		class="NormalText">
		<tr>
			<td class="TipWord">修改本地用户</td>
		</tr>
		
		<tr>
			<td class="activeFenceLine"></td>
		</tr>
		<tr><td> <span>&nbsp;&nbsp; ${resultMes }</span></td></tr>
	</table>	
	<input type="hidden" name="user.id" value="<s:property value="user.id"/>"/>
	<input type="hidden" name="user.createtime" value="<s:property value="user.createtime"/>"/>

	
	<table width="100%" align="center" border="1" cellspacing="0"
				cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
				frame="void" rules="rows" class="NormalText">
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>用户名:</td>
					<td align="left"><s:textfield name="user.name"
						size="30" cssClass="Input" /></td>
					<td>密码:</td>
					<td align="left"><s:textfield name="user.password"
						size="30" cssClass="Input" /></td>
				</tr>
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>备注:</td>
					<td align="left" colspan="3"><s:textfield name="user.comment"
						size="60" cssClass="Input" /></td>
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
						class="FixButtonStyle" onclick=adduser();> <input type="button" name="cancel" value="取消" class="FixButtonStyle" onClick=back();></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>

