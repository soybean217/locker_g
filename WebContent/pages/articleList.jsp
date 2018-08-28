<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>Insert title here</title>
</head>
<body>  
    <table class="datalist">  
      <caption>文章列表</caption>  
      <thead>  
        <tr>  
          <th>#</th>  
          <th>标题</th>  
          <th>内容</th>  
          <th>创建时间</th>  
          <th></th>  
        </tr>  
      </thead>  
      <tbody>  
				<s:iterator value="articleList" status="index">
        <tr>  
          <td>1</td>  
          <td><s:property value="title"/></td>  
          <td><s:property value="context"/></td>  
          <td><s:property value="state"/></td>  
          <td>  
            <a href="article_<s:property value='id'/>_<s:property value='state'/>.html" target="_blank">查 看</a> 
          </td>  
        </tr>  
        </s:iterator> 
      </tbody>  
    </table>  
</body>
</html>