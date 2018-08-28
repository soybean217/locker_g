<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.security.Principal"%>
<%@page import="java.security.cert.X509Certificate"%>
<%@page import="java.util.Enumeration"%>
<%
	final int Undefine = -1;
	final int Tomcat = 1;
	final int WebLogic = 4;
	final int WebSphere = 8;
	final int Unknown = 0;

	final String Default_Cert_Key = "javax.servlet.request.X509Certificate";
	final String Tomcat_Cert_Key = Default_Cert_Key;
	final String WebLogic_Cert_Key = Default_Cert_Key;
	final String WebSphere_Cert_Key = Default_Cert_Key;

	String key = null;
	Object value = null;
	Enumeration en = request.getAttributeNames();

	while (en.hasMoreElements()) {
		key = (String) en.nextElement();
		value = request.getAttribute(key);
		
	}

	X509Certificate[] certs = (java.security.cert.X509Certificate[]) request
			.getAttribute(Tomcat_Cert_Key);


	String user = null;
	if (certs != null) {
		for (int i = 0; i < certs.length; i++) {

			Principal principal = certs[i].getSubjectDN();
			if (principal != null) {
				String dn = principal.getName();
				StringTokenizer tokenizer = new StringTokenizer(dn, ",");
				while (tokenizer.hasMoreTokens() && user == null) {
					StringTokenizer subTokenizer = new StringTokenizer(
							tokenizer.nextToken(), "=");
					String name = subTokenizer.nextToken();
					String val = subTokenizer.nextToken();
					if ("CN".equalsIgnoreCase(name.trim())) {
						user = val;
					}
				}
			}
		}
	}
	if (user != null || "".equals(user)) {
		if("secadmin".equals(user) || "logadmin".equals(user) || "admin".equals(user) ){
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher("main.jsp").forward(request,response);
		}else{
			request.setAttribute("error", "用户证书认证失败");
			request.getRequestDispatcher("error.jsp").forward(request,response);
		}
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title><s:text name="content.login.title"/></title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <link href="pages/css/unicode_cn.css" rel="stylesheet" type="text/css">
    <link href="pages/css/highguardnetworks_cn.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="pages/js/validator.js" ></script>
    
    <script language="javascript">
    function validate(form) {
        if(isNull(form.username.value)) {
            alert("<s:text name='validator.login.username.null'/>");
            return false;
        }
        if(isNull(form.password.value)) {
            alert("<s:text name='validator.login.password.null'/>");
            return false;
        }
    }
    function init(){
        if(window!=top){
            window.top.location.href=window.location.href;
        }
         document.getElementById("username").focus();
         document.getElementById("username").selected;
    }
    </script>
  </head>
  
  <body onload="init();"> 
    <s:form action="login.action" method="post" onsubmit="return validate(this)">
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="center" valign="middle">
                <p><img src="pages/images/index.jpg" width="770" height="310" /></p>
                <font color="red"><s:fielderror /></font>
                <table width="50%" border="1" align="center" cellpadding="5" cellspacing="0" frame="void" rules="rows">
                    <tr><td width='35%'><img src="pages/images/list1.gif" width="9" height="10" hspace="5"><s:text name="content.login.user"/></td>
                        <td><s:textfield name="username"></s:textfield></td>
                    </tr>
                    <tr><td width='35%'><img src="pages/images/list1.gif" width="9" height="10" hspace="5"><s:text name="content.login.password"/></td>
                        <td><s:password name="password"></s:password></td>
                    </tr>
                    <tr>
                        <td colspan='2'>
                            <s:submit value="%{getText('common.button.submit')}" cssClass="FixButtonStyle"></s:submit>
                            <s:reset value="%{getText('common.button.reset')}" cssClass="FixButtonStyle"></s:reset>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    </s:form>
  </body>
</html>
