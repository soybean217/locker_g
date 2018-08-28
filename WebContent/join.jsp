<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<%--<base href="<%=basePath%>" />--%>

<title><s:text name="content.approvalManage.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" href="pages/css/wap.css" />
<script type="text/javascript" src="pages/js/jquery.min.js"></script>
<script type="text/javascript" src="pages/js/wap.js"></script>
</head>
    <script type="text/javascript">
    function lado(){
    	 var search = window.location.search;
    	    function getQueryString(name) {
    	        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"), r = search.substr(1).match(reg);
    	        return null != r ? unescape(r[2]) : null
    	    }
    	    var devId = getQueryString('deviceId');
    	    var LatticeId = getQueryString('LatticeId');
    	    document.getElementById("deviceId").value=devId;
    	    document.getElementById("LatticeId").value=LatticeId;
    }
   
    function checksubmit(){
         var username = document.getElementById("username").value;
         var wx = document.getElementById("wx").value;
         if(username==null||""==username){
            Modal.tips("请填写用户姓名!", "error", 2000);
            return false;
         }
         if(wx==null||""==wx){
            Modal.tips("请填写微信号!", "error", 2000);
            return false;
         }
         document.input.submit();
    }
</script>
<body onload="lado();">
<s:form name="input"  action="checkUser.action"  method="post"
	enctype="multipart/form-data">
    <section class="box hg">
        <div class="banner"></div>
        <ul class="title">
            <input name="deviceId" id="deviceId"  type="hidden" />
            <input name="LatticeId" id="LatticeId"  type="hidden" />
            <li>
                <div class="input-box">
                    <input type="text" id="username" class="input-text" name="username" value='<s:property value="username"/>'  placeholder="姓名">
                </div>
            </li>
            <li>
                <div class="input-box">
                    <input type="text" id="wx" class="input-text" name="wx" value='<s:property value="wx"/>' placeholder="微信号">
                </div>
            </li>
        </ul>
        <div class="cl">
            <input class="btn" type="button" value="提交" onclick="checksubmit();"/>
            <%--<div>&nbsp;&nbsp; ${resultMes }</div>--%>
        </div>
    </section>
</s:form>
</body>
</html>