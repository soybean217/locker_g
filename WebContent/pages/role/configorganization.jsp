<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>" />

<title><s:text name="content.approvalManage.title" /></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="pages/js/validator.js"></script>
<script type="text/javascript" src="pages/js/strategy.js"></script>
<script type="text/javascript" src="pages/js/common.js"></script>
<link href="pages/css/unicode_cn.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
    function validateForm() {    
        var flag = true;
        var val = document.getElementsByName("org.name");
        if (val == null || trim(val[0].value) == '') {
        	alert("<s:text name='content.org.name.null'/>");
            flag = false;
        }
        return flag;
    }

    function submitForm() {
        if (validateForm()) {
            document.input.submit();
        } 
        return;
    }
  </script>
<body>
<s:form name="input" action="saveOrg.action" method="post">
	<table width="100%" border="0" cellpadding="0" cellspacing="10"
		class="NormalText">
		<tr>
			<td class="TipWord"><s:text name="mgfapprove.aore" />&nbsp;<s:text
				name="title.organization" /></td>
		</tr>
		<tr>
			<td class="activeFenceLine"></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="1" cellspacing="0" cellpadding="3"
				bordercolorlight="#C5EAED" bordercolordark="#ffffff" frame="void"
				rules="rows" class="NormalText">
				<tr>
				    <s:hidden name="org.id"/>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<fieldset class="FieldsetBorder"><legend
				class="LegendTitle"><s:text name="confnetrule.gene" /></legend>
			<table width="98%" align="center" border="1" cellspacing="0"
				cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
				frame="void" rules="rows" class="NormalText">
				<tr>
					<td colspan="3"><s:fielderror /></td>
				</tr>

                <tr>
                    <td width="120"><s:text name="tab.name" />:</td>
                    <td><s:textfield name="org.name" size="100" maxlength="80" cssClass="Input" /></td>
                    <td width="200">&nbsp;</td>
                </tr>
                
				<tr>
					<td width="120"><s:text name="tab.desc" />:</td>
					<td><s:textfield name="org.remark" size="100"
						maxlength="300" cssClass="Input" /></td>
					<td width="200">&nbsp;</td>
				</tr>
			</table>
			</fieldset>
			</td>
		</tr>

		<tr>
			<td><input type="button" name="ok"
				value='<s:text name="confnetrule.save"/>' class="FixButtonStyle"
				onclick="submitForm();" /> <s:submit name="cancel"
				value="%{getText('confnetrule.cancel')}" cssClass="FixButtonStyle"
				onclick="document.input.action='manageOrgs.action';" /></td>
		</tr>
	</table>
</s:form>
</body>
</html>

