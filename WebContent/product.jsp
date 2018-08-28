<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://www.tldExample.cn/test/functions"   prefix="myfn" %>

    <!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title><s:text name="数据查询管理" /></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="pages/css/wap.css" />
</head>
<body>
<form action="./addWxUser.html" method='GET' id="open">
<input type="hidden" name="username" 
			value="<s:property value="username"/>" />
<input type="hidden" name="wx" 
			value="<s:property value="wx"/>" />
<input type="hidden" name="deviceId" 
			value="<s:property value="deviceId"/>" />
<input type="hidden" name="LatticeId" 
			value="<s:property value="LatticeId"/>" />
    <section class="box hg">
        <div class="succ">验证用户信息成功！</div>
           <div class="product">
               <img src="images/apple.png">
           </div>
            <p class="desc">
                你购买的水果是 <s:property value="fruit"/>
            </p>
        <p class="prompt">
            点击确认打开，打开设备
        </p>
        <div class="cl">
            <input class="btn" type="button" value="打开设备" onclick="sureClick();"/>
        </div>
    </section>
</form>
</body>
<script type="text/javascript">
	function sureClick(){
		document.forms[0].action = "sendSocket.action";
		document.forms[0].submit();	
	}

</script>
</html>