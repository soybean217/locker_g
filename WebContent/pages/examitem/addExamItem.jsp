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
<script type="text/javascript" src="pages/js/jquery.js"></script>
<script type="text/javascript" src="pages/js/strategy.js"></script>
<script type="text/javascript" src="pages/js/common.js"></script>
<script language="JavaScript" src="pages/js/partitionPageAndSearch.js"  ></script>
<script type="text/javascript" src="pages/js/xtree.js"></script>
<script type="text/javascript" src="pages/js/xmlextras.js"></script>
<script type="text/javascript" src="pages/js/xloadtree.js"></script>
<script type="text/javascript" src="pages/js/map.js"></script>
<script type="text/javascript" src="pages/js/checkboxTreeItem.js"></script>
<script type="text/javascript" src="pages/js/checkboxXLoadTree.js"></script>
<script type="text/javascript" src="pages/js/radioTreeItem.js"></script>
<script type="text/javascript" src="pages/js/radioXLoadTree.js"></script>
    <script type="text/javascript" src="pages/js/WdatePicker/WdatePicker.js" defer="defer"></script>
    <script type="text/javascript" src="pages/js/WdatePicker/js/lang/zh-cn.js"></script>
<link href="pages/css/xtree.css" rel="stylesheet" type="text/css">
<link href="pages/css/unicode_cn.css" rel="stylesheet" type="text/css">
<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
<link href="pages/css/highguardnetworks_cn.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">

function back() {
    document.input.action = "ExamItemList.action";
    document.input.submit();
}

function adduser(){

	  document.input.submit();
}

</script>
<script language="javascript" src="/javascript/JumpMenu.js"></script>
<body >
<s:form name="input" action="addExamItem.action"  method="post" enctype="multipart/form-data">
	<table width="100%" border="0" cellpadding="0" cellspacing="10" class="NormalText">
	  <input type="hidden" name="score.id" id="score.id" value="<s:property value="score.id"/>"/>
        <tr>
            <td class="TipWord">添加考核项目</td>
        </tr>
        <tr>
            <td class="activeFenceLine"></td>
        </tr></table>&nbsp;&nbsp;&nbsp;<font color="red"><s:fielderror /></font>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="10" class="NormalText">
		<tr>
			<td>
			<fieldset class="FieldsetBorder"><legend
				class="LegendTitle">考核信息</legend>
			<table width="100%" align="center" border="1" cellspacing="0"
                cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
                frame="void" rules="rows" class="NormalText">
				<tr align="left">
					<td align="left"></td>
                    <td>项目名称:</td>
                    <td align="left" colspan="3"><s:select id="examItem.name" name="examItem.name" list="itemMap"  cssStyle="width:180px;" value="score.item"/></td>
			    </tr>
			   
                <tr align="left">
                    <td width="2%">&nbsp;</td>
                    <td>说明</td>
                    <td align="left"><s:textfield name="examItem.remark" size="30"   cssClass="Input" /></td>
                    <td></td>
                    <td align="left"></td>
                </tr>
            </table>
            </fieldset>
            </td>
        </tr>
		<tr>
		  <td>
        <table width="100%" align="center" border="1" cellspacing="0" cellpadding="3" bordercolorlight="#C5EAED" 
            bordercolordark="#ffffff" frame="void" rules="rows">
            <tr>
            <td width="3%"></td>
              <td>
                <input type="button" name="ok" value="保存" class="FixButtonStyle" onclick="adduser()" >                          
                <input type="button" name="cancel" value="取消"  class="FixButtonStyle"  onClick="back()"> 
              </td>
            </tr>
        </table>
		  </td>
		</tr>
	</table>
</s:form>
</body>
</html>

