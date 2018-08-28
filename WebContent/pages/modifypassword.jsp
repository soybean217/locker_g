<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title><s:text name="content.firewallRule.add.title"/></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script type="text/javascript" src="pages/js/validator.js" ></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="pages/css/style.css" />
	<link  rel="stylesheet" href="pages/css/highguard_table.css"/>
	<link href="pages/css/unicode_cn.css" rel="stylesheet" type="text/css">
	<link href="pages/css/highguardnetworks_cn.css" rel="stylesheet" type="text/css">
	
	<script language="javascript">	
	function validate(form) {
		if(isNull(form.oldPassword.value)) {
			alert("请输入旧密码!");
			return false;
		}
		if(isNull(form.newPassword.value)) {
			alert("请输入新密码!");
			return false;
		}
		if(isNull(form.reNewPassword.value)) {
			alert("请再次输入新密码!");
			return false;
		}
		if(form.newPassword.value != form.reNewPassword.value) {
			alert("两次输入新密码不一致!");
			return false;
		}
		return true;
	}
</script>

  </head>
  <body onload="window.status='finished';">
    <s:form action="modifyPassword.action" onsubmit="return validate(this)">
    <table width="100%" border="0" cellpadding="0" cellspacing="10" class="NormalText">
    <tr>
        <td class="TipWord"><s:text name="content.mainPage.tree.modifypswd" /></td>
    </tr>
    <tr>
        <td class="activeFenceLine"></td>
    </tr>
    <tr>
        <td><font color="green"><span>&nbsp;&nbsp; ${resultMes }</span></font>
    <font color="red"><s:fielderror /></font></td>
    </tr>
    <tr>
    <td>
    	<table width="100%" border="1" bordercolor="#319499">
    	<tr>
	    	<td width="25%">旧密码:</td>
	    	<td> 
	    		<s:password name="oldPassword"></s:password>
	    	</td>
    	</tr>
    	<tr>
    		<td>新密码:</td>
    		<td><s:password name="newPassword"></s:password></td>
    	</tr>
    	<tr>
    		<td>新密码:</td>
    		<td><s:password name="reNewPassword"></s:password></td>
    	</tr>
    </table>
    <br/>
    <table>
   	<tr>
   		<td width="25%">&nbsp;</td>
	   	<td>
	   	<s:submit type="submit" value="%{getText('common.button.submit')}" cssClass="FixButtonStyle"></s:submit>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	   	</td>
   	</tr>
    </table>
    </td>
    </tr>
    </table>
    </s:form>
  </body>
</html>
