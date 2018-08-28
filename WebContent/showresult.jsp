<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<title>实时成绩</title>
<head>
<script type="text/javascript" src="<%=path%>/pages/js/comet4j.js"></script> 
  <script type="text/javascript" src="<%=path%>/pages/js/jquery.1.4.4.js"></script> 
   <script type="text/javascript" src="<%=path%>/pages/js/a.js"></script> 
  <script type="text/javascript">
  function init(){
      var i = 0;

         // 建立连接，conn 即web.xml中 CometServlet的<url-pattern>
         JS.Engine.start('conn');
         // 监听后台某个频道
         JS.Engine.on(
                { 
                    // 对应服务端 “频道1” 的值 result1
                    result1 : function(num1){
                    	if(num1=="refresh"){//刷新页面
                    		window.location.reload();
                    	}else{//页面数据推送
                    		
                    	var value = num1.split("|");
                    	for (j=0;j<value.length ;j++ ) {
                    		
                    		$("#div1").prepend("<tr id='tr"+i+"'><td>"+value[j]+"</td></tr>"); 
                        	i++;
                        	if(i>=20){
                        		
                        		var a = i-20;
                        		$("#tr"+a).hide();   
                        	}	
                    	}
                    	}
                 }
             }
            );
 }
 </script>
 </head>
 <body onload="init()" style="text-align: center;">
 <div align="center">
   <table   border="1">
         <tr><td width="100pxp;" style="text-align: center;">姓名</td><td  style="text-align: center;" width="100pxp;">已跑路程</td><td  style="text-align: center;" width="100pxp;">用时</td><td  style="text-align: center;" width="100pxp;">速度</td>
   </table>
   <table   border="1">
   <div id ="div1"></div>
   </table>
  </div>
          </body>
</html>
