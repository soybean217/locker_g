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

<title>欢迎页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>

<body>
<table border="0" width="100%" height="100%">
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>
		<div align="center">
		<h2><font color="#EA7500">欢迎使用本系统！</font></h2>
		</div>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
</body>
</html>
