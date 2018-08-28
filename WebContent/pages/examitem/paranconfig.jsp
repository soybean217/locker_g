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
    document.input.action = "listparanCfgInfo.action";
    document.input.submit();
    
}

function adduser(){

	  document.input.submit();
}

</script>
<script language="javascript" src="/javascript/JumpMenu.js"></script>
<body >
<s:form name="input" action="updateparanCfgInfo.action"  method="post" enctype="multipart/form-data">
	<table width="100%" border="0" cellpadding="0" cellspacing="10" class="NormalText">
	  <input type="hidden" name="score.id" id="score.id" value="<s:property value="score.id"/>"/>
        <tr>
            <td class="TipWord">考核参数配置</td>
        </tr>
        <tr>
            <td class="activeFenceLine"></td>
        </tr></table>&nbsp;&nbsp;&nbsp;<font color="red"><s:fielderror /></font>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="10" class="NormalText">
		<tr>
			<td>
			<fieldset class="FieldsetBorder"><legend
				class="LegendTitle">配置信息</legend>
			<table width="100%" align="center" border="1" cellspacing="0"
                cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
                frame="void" rules="rows" class="NormalText">
				<tr align="left">
					<td align="left"></td>
                    <td>训练结束耗时:</td>
                    <td align="left" ><s:textfield name="ParanConfig.timeout" size="30"   cssClass="Input" />(单位：秒)</td>
                    <td>多少时间接收不到数据代表本次训练结束</td>
			    </tr>
			   
               		<tr align="left">
					<td align="left"></td>
                    <td>显示成绩耗时:</td>
                    <td align="left" ><s:textfield name="ParanConfig.timeshow" size="30"   cssClass="Input" />(单位：秒)</td>
                    <td>到达终点多少时间后显示成绩</td>
			    </tr>
			    <tr align="left">
					<td align="left"></td>
                    <td>清空屏幕耗时:</td>
                    <td align="left" ><s:textfield name="ParanConfig.cleanshow" size="30"   cssClass="Input" />(单位：秒)</td>
                    <td>多少时间后 清空屏幕</td>
			    </tr>
			    <tr align="left">
					<td align="left"></td>
                    <td>读数有效时间间隔:</td>
                    <td align="left" ><s:textfield name="ParanConfig.delaytime" size="30"   cssClass="Input" />(单位：秒)</td>
                    <td>有效读取数据的时间间隔</td>
			    </tr>
			    <tr align="left">
					<td align="left"></td>
                    <td>有效距离:</td>
                    <td align="left" ><s:textfield name="ParanConfig.range" size="30"   cssClass="Input" />(单位：米)</td>
                    <td>必须跑多少米这次成绩有效</td>
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
                <input type="button" name="ok" value="保存" class="FixButtonStyle" onclick="adduser()" >  ${resultMes}                        
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

