<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>" />

<title><s:text name="sfa.version.mgmt" /></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="pages/js/validator.js"></script>
<script type="text/javascript" src="pages/js/strategy.js"></script>
<script type="text/javascript" src="pages/js/common.js"></script>
<link href="pages/css/unicode_cn.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	function goBack(){
		var type = document.getElementById("updateType").value;
		if(type==0){
			window.location.href="manageSfaClientUpdate.action";
		}else{
			window.location.href="managePrintClientUpdate.action";	
		}
	}

	function validate(){
		var maxVersion =document.getElementById("maxVersion").value;
		var minVersion =document.getElementById("minVersion").value;
		if(maxVersion=="" || minVersion==""){
			return false;
		}
		return true;
	}

</script>
</head>


<body>
	<s:form action="editVersion.action" onsubmit="return validate()">
	<table width="100%" border="0" cellpadding="0" cellspacing="10" class="NormalText">
		<tr>
			<td class="TipWord">
				<s:if test="updateType==0">
					编辑集中管控客户端版本更新区间
				</s:if>
				<s:else>
					编辑集中文印客户端版本更新区间
				</s:else>
			</td>
		</tr>
		<tr>
			<td class="activeFenceLine"></td>
		</tr>
		<tr>
			<td>
			<fieldset class="FieldsetBorder">
			<legend class="LegendTitle">版本区间 </legend>
			<table width="98%" align="center" border="1" cellspacing="0"
				cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
				frame="void" rules="rows" class="NormalText">
                <tr>
                	<td width="8%">最小版本号：<font color="red">*</font>:</td>
                	<td><input id="minVersion" name="minVersion" value='<s:property value="minVersion"/>' id="pubVersion" size="80" type="text">  </td>
                </tr>
                <tr>
                    <td width="8%">最大版本号：<font color="red">*</font>:</td>
                    <td><input id="maxVersion" name="maxVersion" value='<s:property value="maxVersion"/>' id="pubVersion" size="80" type="text">  </td>
                </tr>
			</table>
			</fieldset>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<s:hidden name="updateType"/>
				<s:submit value="保存" cssClass="FixButtonStyle"></s:submit>
				<input type="button" onclick="goBack()" value="取消" class="FixButtonStyle">
			 </td>
		</tr>
	</table>
	</s:form>
	
	</body>
</html>

