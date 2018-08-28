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
		document.input.action = "latticeList.action";
		document.input.submit();
	}

	function adduser() {
		document.input.submit();
	}





</script>
<script language="javascript" src="/javascript/JumpMenu.js"></script>
<body>
<s:form name="input" action="editLattice.action" method="post"
	enctype="multipart/form-data">
	<table width="100%" border="0" cellpadding="0" cellspacing="10"
		class="NormalText">
		<input type="hidden" name="id" id="id"
			value="<s:property value="id"/>" />
		<input type="hidden" name="lattice.id" id="lattice.id"
			value="<s:property value="lattice.id"/>" />
			<input type="hidden" name="lattice.deviceid" id="lattice.deviceid"
			value="<s:property value="lattice.deviceid"/>" />
			<input type="hidden" name="lattice.status" id="lattice.status"
			value="<s:property value="lattice.status"/>" />
		<tr>
			<td class="TipWord">修改信息</td>
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
				class="LegendTitle">格子信息</legend>
			<table width="100%" align="center" border="1" cellspacing="0"
				cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
				frame="void" rules="rows" class="NormalText">
		
			<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>控制器地址:</td>
					<td align="left"><s:textfield name="lattice.lockid"
						size="30" cssClass="Input" /></td>
					<td>编号:</td>
					<td align="left"><s:textfield name="lattice.qrcode" size="30"
						cssClass="Input" /></td>
				</tr>



				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>寄存价格:</td>
					<td align="left">
						<s:textfield name="lattice.price" placeholder="寄存的价格/小时" size="30" cssClass="Input" />
					</td>
					<td>寄送:</td>
					<td align="left">
						<s:textfield name="lattice.promotionprice" size="30" placeholder="寄送价格/单" cssClass="Input" />
					</td>
				</tr>

				
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>格子大小：</td>
					<td align="left">
						<input type="radio"  name="lattice.type" value="1"  <s:if test="lattice.type==\"1\"" > checked </s:if> /> 大
						<input type="radio"  name="lattice.type" value="2"   <s:if test="lattice.type==\"2\"" > checked </s:if> /> 中
						<input type="radio"  name="lattice.type" value="3"   <s:if test="lattice.type==\"3\"" > checked </s:if> /> 小
					</td>
					<td>门编号：</td>
					<td align="left"><s:textfield name="lattice.fruit" class="Input" /></td>
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

