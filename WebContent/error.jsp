<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
    <meta charset="UTF-8">
    <title>水果管理</title>
    <link rel="stylesheet" href="pages/css/wap.css" />
</head>
<body id=“top">
<form action="./addWxUser.html" method='GET' id="open">
    <section class="box hg">
        <div class="fail">验证用户信息失败！</div>
        <p class="prompt fail-prompt">
            请核实微信号等信息，重新输入
        </p>
        <div class="cl">
            <input class="btn" type="button" value="确定" onclick="checksubmit();"/>
        </div>
    </section>
</form>
<script type="text/javascript">

function checksubmit(){
	history.go(-1);
}

</script>
</body>
</html>
