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
		if(isNull(form.je.value)) {
			alert("请输入金额!");
			return false;
		}
	
		return true;
	}
</script>

  </head>
  <body onload="window.status='finished';">
    <s:form action="editJE.action" onsubmit="return validate(this)">
    <table width="100%" border="0" cellpadding="0" cellspacing="10" class="NormalText">
    <tr>
        <td class="TipWord">设置最低金额</td>
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
    	<tr align="left">
					<td align="left"></td>
                    <td>设置最低金额:</td>
                    <td align="left" colspan="3"><s:textfield name="je" size="30"  id="je"  cssClass="Input" /></td>
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
