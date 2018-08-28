<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
        <base href="<%=basePath%>">
        
    <title><s:text name="content.mainPage.title"></s:text></title>   
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <link rel="stylesheet" href="pages/css/basic.css" /> 
    <script type="text/javascript" src="pages/js/jquery.js" ></script>
</head>

<script type="text/javascript">
	function show(dd)
	{
	    for(var i=1;i<=8;i++)
	    {
	        var list = document.getElementById('list'+i);
	        if (list != null) {
	            if(i==Number(dd))
	            {
	                if (list.style.display == 'none') {
	                    list.style.display='block';
	                } else {
	                    list.style.display='none';
	                }
	            }
	        }
	    }
	}
	function detailShowSrc(src){
	    detailShow.location.href = src;
	}
	function useview() {
	    window.open('pages/help/srahelp.htm','newwindow','width=980,height=680,top=100,left=260,scrollbars=yes,resizeable=yes');
	}
</script>
<body bgcolor="#C7E3F3">
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="5%">
    <tr background="">
        <td height="5%" background="pages/images/systemLogo2.JPG">
            <img src="pages/images/systemLogoNew.JPG" height="60" />
        </td>
    </tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="90%">
    <tr>
        <td width="15%" style="background-color:#C7E3F3;vertical-align:top;">
            <table border="0" cellpadding="0" cellspacing="0" width="98%" id="tab">
                <tr>
                    <td><img src="pages/images/base.gif" width="100%" height="45px"/></td>
                </tr>
         
                <tr>
                    <td>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
                            <tr>
                                <td width="2px"></td>
                                <td style="background-image:url(pages/images/bg.jpg); line-height:24px; color:#fff; font-weight:bold; cursor:pointer;"onclick="show(3);"><img src="pages/images/folder.gif" width="20" height="20" style="float:left; margin-left:2px; margin-right:5px;"/>报表查询</td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
                                        <tr>
                                            <td width="20%"></td>
                                            <td width="80%">
                                                <div id="list3">
                                                    <a href="javascript:detailShowSrc('splitReport.action')" >打印追述</a>
                                                    <a href="javascript:detailShowSrc('splitReport.action')" >分账报表</a>
                                                    
                                                    <a href="javascript:detailShowSrc('managePrinter.action')" >打印机管理</a>
                                                    <a href="javascript:detailShowSrc('manageDevice.action')" >打印设备管理</a>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                              </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
                            <tr>
                                <td width="2px"></td>
                                <td style="background-image:url(pages/images/bg.jpg); line-height:24px; color:#fff; font-weight:bold; cursor:pointer;"onclick="show(6);"><img src="pages/images/folder.gif" width="20" height="20" style="float:left;margin-left:2px; margin-right:5px;"/><s:text name="content.mainPage.tree.personalsetting"></s:text></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
                                        <tr>
                                            <td width="20%"></td>
                                            <td width="80%">
                                                <div id="list6">
                                                    <a href="javascript:detailShowSrc('toModifyPassword.action')" ><s:text name="content.mainPage.tree.modifypswd"></s:text></a>   
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                              </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td style="background-image:url(pages/images/bg.jpg); line-height:24px; color:#0753A7; font-weight:bold; ">
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
                            <tr>
                                <td width="2px"><img src="pages/images/Logoff.gif" width="15" height="15" style="float:left;margin-left:7px; margin-right:10px;"></td>
                                <td><a href="logout.action" style="text-decoration: none; font-weight:bold;"><s:text name="admin.cancellation"></s:text></a></td>
                            </tr>
                        </table>
                    </td>
                </tr>   
            </table>
      </td>
      <td width="85%" height="100%" style="background-color:#73ACB5;">
          <iframe src="pages/welcome.jsp" name="detailShow" id="detailShow" align="MIDDLE" width="100%" height="100%" marginwidth="1" marginheight="1" frameborder="1" scrolling="yes" frameborder="0"> 
                <s:text name="home.internetexplorer.error"></s:text>
          </iframe> 
      </td> 
    </tr>
</table>
<span class="STYLE3"></span>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="5%">
    <tr>
        <td align="center">
            <font color="#282828">&copy;2008-2012<s:text name="highguard.cop"></s:text></font> 
        </td>   
    </tr>
</table>
</body>
</html>