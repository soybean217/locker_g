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
    function selectRole(){
        document.forms[0].action = "toSaveRoleRight.action?roleid=" + document.getElementsByName("roleRight.roleId")[0].value;
        document.forms[0].submit();
    }
</script>
<body>
<s:form name="input" action="saveRoleRight.action" method="post">
    <table width="100%" border="0" cellpadding="0" cellspacing="10"
        class="NormalText">
        <tr>
            <td class="TipWord"><s:text name="mgfapprove.aore" />&nbsp;<s:text
                name="content.mainPage.tree.roleright" /></td>
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
                    <td><s:hidden name="roleRight.id" /></td>
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
                    <td colspan="3" style="color: red;"><s:fielderror /></td>
                </tr>
                <tr>
                    <td width="120"><s:text name="tab.role" />:</td>
                    <td><s:select name="roleRight.roleId" onchange="selectRole();"
                        cssClass="NormalText" cssStyle="width:200px" list="roleTabs"
                        listKey="id" listValue="description" value="roleRight.roleId" /></td>
                    <td width="200">&nbsp;</td>
                </tr>
                <tr>
                    <td width="120"><s:text name="confwebres.desc" />:</td>
                    <td><s:textfield name="roleRight.description" size="100"
                        maxlength="255" cssClass="Input" /></td>
                    <td width="200">&nbsp;</td>
                </tr>
                <tr>
                    <td width="120"><s:text name="tab.roleright" />:</td>
                    <td>
                    <s:iterator value="#request.rightTabs" status="rtb">
                <input id="label<s:property value='#request.rightTabs[#rtb.index].id' />" type="checkbox" name="rights" value="<s:property value='#request.rightTabs[#rtb.index].id' />"
                 <s:if test='#request.rightTabs[#rtb.index].id == 0 || #request.rightTabs[#rtb.index].id == 1 || #request.rightTabs[#rtb.index].id == 2 || #request.rightTabs[#rtb.index].id == 4 || #request.rightTabs[#rtb.index].id == 5 || #request.rightTabs[#rtb.index].id == 7 || #request.rightTabs[#rtb.index].id == 9'>checked onclick="return false;"</s:if>
                 <s:else><s:iterator value="#request.rights" status="rt"><s:property value="#request.rights[#rt.index].id"/><s:if test='#request.rights[#rt.index] == #request.rightTabs[#rtb.index].id'>checked</s:if></s:iterator></s:else>/>
                 <label for="label<s:property value='#request.rightTabs[#rtb.index].id' />"><s:property value='#request.rightTabs[#rtb.index].description' /></label><br/>
                </s:iterator>
                    </td>
                    <td width="200">&nbsp;</td>
                </tr>
            </table>
            </fieldset>
            </td>
        </tr>

        <tr>
            <td><input type="submit" name="ok"
                value='<s:text name="confnetrule.save"/>' class="FixButtonStyle" />
               <s:submit name="cancel"
                value="%{getText('confnetrule.cancel')}" cssClass="FixButtonStyle"
                onclick="document.input.action='manageRoleRights.action';" /></td>
        </tr>
    </table>
</s:form>
</body>
</html>

