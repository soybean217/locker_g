<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://www.tldExample.cn/test/functions"   prefix="myfn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title><s:text name="数据查询管理" /></title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <link rel="stylesheet" href="pages/css/wap.css" />
</head>
<body>
    <div class="s_content2_by">
    <ul>
    <li><div class="success"></div></li>
    <li>
    <p>设备已经成功打开.<span style="color:Red;">请尽快取走您的水果。</span></p>
    <p>祝您享用愉快</p>
    <p>如有其他疑问.欢迎致电：</p>
    </li>
    </ul>
    </div>
</body>
</html>