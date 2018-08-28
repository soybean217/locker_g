<%@ page language="java" pageEncoding="utf-8"%>
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
<base href="<%=basePath%>">

<title>辅助训练系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<link rel="stylesheet" href="pages/css/basic.css" />
<script type="text/javascript" src="pages/js/jquery.js"></script>
</head>
<LINK rel="stylesheet" type="text/css" href="images/main.css">
<SCRIPT language="javascript" type="text/javascript"
	src="images/jquery.lib.js"></SCRIPT>
<script type="text/javascript">
	function noticedetail(id) {
		window.location.url("notice/noticedetil.jsp");
	}
</script>

<body>
<div class="main ny_bg">
		
		<div class="sidebar">
			<div class="title"><img src="images/tubiao.png"/>新闻中心</div>
			<div class="intro">
				<ul>



<s:iterator value="noticeList" id="list"  status="index">
<LI><a  href="noticedetail.action?noticeid=<s:property value="id"/>" title="aa"><s:property value="name"/></a></LI>
</s:iterator>

					 
				</ul>
			</div>
			
			
					<div class="title"><img src="images/tubiao.png"/>联系我们</div>
			<div class="intro">
				<ul>
				
<LI style="height:27px;">企业SEO站V11.2</LI>
<LI style="height:27px;">电话：0158-54751925</LI>
<LI style="height:27px;">传真：0158-54751925</LI>
<LI style="height:27px;">手机：15854751925</LI>
<LI style="height:27px;">Q&nbsp;&nbsp;Q：305018647</LI>
<LI style="height:27px;">联系人：徐珂</LI>
<LI style="height:27px;">邮箱：comcms@163.com</LI>
<LI style="height:27px;">地址：山东济宁市经济开发区</LI>


			

					 
				</ul>
			</div>
		</div>

		<div class="content">
			<div class="title"><img src="images/tubiao.png"/><a  href=index.html title='网站首页' >网站首页</a>&nbsp;&nbsp;&nbsp;&nbsp;<font color=#717171>>></font>&nbsp;&nbsp; 
<a  href=jszc.html title='技术支持'>新闻中心</a></span> 
<font color=#717171>>></font>  <span><a  href=315.html>电子商务搜索引擎优化技术</a></div>
			<div class="intro">
				<font color=#717171>作者：<s:property value="notice.user"/></font>&nbsp;&nbsp; <font color=#717171>日期：<s:date name="notice.createtime" format="yyyy-MM-dd hh:mm:ss" /></font>
<p>
	&nbsp;&nbsp;&nbsp;<s:property value="notice.comtent"/>


	　</p></div>
		</div>
		
	


</div>

<DIV class="footer" >
<font style="font-style: normal;">
企业SEO站V11.2 Copyright&copy; 2010-2015&nbsp;&nbsp;1111111111111111111111111</font>
</DIV>
</body>
</html>