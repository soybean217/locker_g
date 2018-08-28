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
		var type=document.getElementById("updateType").value;
		if(type==0){
			window.location.href="backStep1.action";
		}else{
			window.location.href="backStep1Print.action";
		}
	}

	function validate(){
		var version =document.getElementById("pubVersion").value;
		var desc =document.getElementById("pubDesc").value;
		if(version=="" || desc==""){
			alert("请输入版本号与版本说明");
			return false;
		}
		return true;
	}

</script>
</head>


<body>
	<s:form action="publishVersion.action" onsubmit="return validate()">
	<table width="100%" border="0" cellpadding="0" cellspacing="10" class="NormalText">
		<tr>
			<td class="TipWord">
			<s:if test="updateType==0">
					集中管控客户端发布
				</s:if>
				<s:else>
					集中文印客户端发布
				</s:else>
			</td>
		</tr>
		<tr>
			<td class="activeFenceLine"></td>
		</tr>
        <tr>
			<td width="300px">
				<div style="width: 90px;height: 13px;background-color: #C7E3F3; font-size: 16px;text-align: center;float: left;"><font color="#F48C05"> <b>导入文件>></b></font></div>
				<div style="width: 90px;height: 15px;background-color: #36A6AB; font-size: 16px;text-align: center; float: left;"><font color="#F48C05"><b>发布</b></font></div>
			</td>
		</tr>
		<tr>
			<td>
			<fieldset class="FieldsetBorder">
			<legend class="LegendTitle">版本信息与说明 </legend>
			<table width="98%" align="center" border="1" cellspacing="0"
				cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
				frame="void" rules="rows" class="NormalText">
				
				<tr>
					<td width="12%">是否需要重启客户端:</td>
					<td>
						<s:select name="restart" cssClass="NormalText" list="restarts" value="restart"/>
					</td>
				</tr>
                <tr>
                	<td width="12%">新版本号<font color="red">*</font>:</td>
                	<td><input name="pubVersion" id="pubVersion" size="80" type="text">  </td>
                </tr>
                <tr>
                    <td width="12%">版本更新说明<font color="red">*</font>:</td>
                    <td>
                    	<textarea rows="15" cols="80" id="pubDesc" name="pubDesc"></textarea>
                    </td>
                </tr>
			</table>
			</fieldset>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<s:hidden id="updateType" name="updateType"></s:hidden>
				<s:submit value="发布" cssClass="FixButtonStyle"></s:submit>
				<input type="button" onclick="goBack()" value="上一步" class="FixButtonStyle">
			 </td>
		</tr>
	</table>
	</s:form>
	
	</body>
</html>

