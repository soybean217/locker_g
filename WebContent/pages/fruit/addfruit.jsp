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
		document.input.action = "fruitList.action";
		document.input.submit();
	}

	function adduser() {
	
	
		document.input.submit();
	}





</script>
<script language="javascript" src="/javascript/JumpMenu.js"></script>
<body>
<s:form name="input" action="addFruit.action" method="post"
	enctype="multipart/form-data">
	<table width="100%" border="0" cellpadding="0" cellspacing="10"
		class="NormalText">
		<input type="hidden" name="fruit.id" id="fruit.id"
			value="<s:property value="fruit.id"/>" />
		<tr>
			<td class="TipWord">添加水果信息</td>
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
				class="LegendTitle">水果信息</legend>
			<table width="100%" align="center" border="1" cellspacing="0"
				cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
				frame="void" rules="rows" class="NormalText">
		
			<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>编号:</td>
					<td align="left"><s:textfield name="fruit.num"
						size="30" cssClass="Input" /></td>
					<td>品类:</td>
					<td align="left"><s:textfield name="fruit.category" size="30"
						cssClass="Input" /></td>
				</tr>
				
				
		
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>品名:</td>
					<td align="left"><s:textfield name="fruit.productname" size="30"
						cssClass="Input" /></td>
					<td>等级:</td>
					<td align="left"><s:textfield name="fruit.grade" size="30"
						cssClass="Input" /></td>
				</tr>
					<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>产地:</td>
					<td align="left"><s:textfield name="fruit.place" size="30"
						cssClass="Input" /></td>
					<td>口感:</td>
					<td align="left"><s:textfield name="fruit.texture" size="30"
						cssClass="Input" /></td>
				</tr>
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>参考价格</td>
					<td align="left"><s:textfield name="fruit.price" size="30"
						cssClass="Input" />(元/公斤)</td>
					<td>描述</td>
					<td align="left"><s:textfield name="fruit.description" size="30"
						cssClass="Input" /></td>
				</tr>
				
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td >水果照片：</td>
					<td align="left"><s:file name="upload"
						label="请选择照片" /></td>
					<td ></td>
					<td align="left"></td>
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

