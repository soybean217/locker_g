<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
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
<base href="<%=basePath%>" />

<title><s:text name="content.approvalManage.title" /></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="pages/js/validator.js"></script>
<script type="text/javascript" src="pages/js/jquery.js"></script>
<script type="text/javascript" src="pages/js/strategy.js"></script>
<script type="text/javascript" src="pages/js/common.js"></script>
<script language="JavaScript" src="pages/js/partitionPageAndSearch.js"></script>
<script type="text/javascript" src="pages/js/xtree.js"></script>
<script type="text/javascript" src="pages/js/xmlextras.js"></script>
<script type="text/javascript" src="pages/js/xloadtree.js"></script>
<script type="text/javascript" src="pages/js/map.js"></script>
<script type="text/javascript" src="pages/js/checkboxTreeItem.js"></script>
<script type="text/javascript" src="pages/js/checkboxXLoadTree.js"></script>
<script type="text/javascript" src="pages/js/radioTreeItem.js"></script>
<script type="text/javascript" src="pages/js/radioXLoadTree.js"></script>
<script type="text/javascript" src="pages/js/WdatePicker/WdatePicker.js"
	defer="defer"></script>
<script type="text/javascript"
	src="pages/js/WdatePicker/js/lang/zh-cn.js"></script>
<link href="pages/css/xtree.css" rel="stylesheet" type="text/css">
<link href="pages/css/unicode_cn.css" rel="stylesheet" type="text/css">
<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
<link href="pages/css/highguardnetworks_cn.css" rel="stylesheet"
	type="text/css">
</head>

<link rel="stylesheet" type="text/css" href="pages/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="pages/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="pages/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="pages/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">









	function back() {
		document.input.action = "deviceList.action";
		document.input.submit();
	}

	function adduser() {
		//var oldcell = document.getElementById("cellnum").value;
		
		
	//	var ss = document.getElementById("device.cellnum").value;
		// var   type="^[0-9]*[1-9][0-9]*$"; 
	    //    var   re   =   new   RegExp(type); 
	    //   if(ss.match(re)==null) 
	     //   { 
	     //    alert( "请输入大于零的整数!"); 
	     //   return false;
	     //   } 

        //  if(oldcell>ss){
        //	  alert( "请先删除格子!"); 
  	    //    return false;
        //  }
	
		document.input.submit();
	}


	function changeimg() {
		document.getElementById("showimg").style.display = "none";
		document.getElementById("uploadimg").style.display = "block";
	}


</script>
<script language="javascript" src="/javascript/JumpMenu.js"></script>
<body>
<s:form name="input" action="editDevice.action" method="post"
	enctype="multipart/form-data">
	<table width="100%" border="0" cellpadding="0" cellspacing="10"
		class="NormalText">
		<input type="hidden" name="device.id" 
			value="<s:property value="device.id"/>" />
		<input type="hidden" name="device.wximage" 
			value="<s:property value="device.wximage"/>" />	
			<input type="hidden" name="device.status" 
			value="<s:property value="device.status"/>" />
			
			<input type="hidden" name="cellnum" id="cellnum"
			value="<s:property value="device.cellnum"/>" />
		<tr>
			<td class="TipWord">修改设备信息</td>
		</tr>
		
		<tr>
			<td class="activeFenceLine"></td>
		</tr>
	</table>&nbsp;&nbsp;&nbsp;<font color="red"><s:fielderror /></font>
	<br />
	<table width="100%" border="0" cellpadding="0" cellspacing="10"
		class="NormalText">
		<tr>
			<td>
			<fieldset class="FieldsetBorder"><legend
				class="LegendTitle">设备信息</legend>
			<table width="100%" align="center" border="1" cellspacing="0"
				cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
				frame="void" rules="rows" class="NormalText">
		
			<tr align="left">
					<td width="2%">&nbsp;</td>
					<td width="10%">设备名称:</td>
					<td width="10%" align="left"><s:textfield name="device.name"
						size="30" cssClass="Input" /></td>
				<td align="right">运营人员ID:</td>
				<td align="left"><s:textfield id="device.managerid" name="device.managerid" size="30"
											  cssClass="Input" /></td>
				</tr>
		<tr align="left">
				<td width="2%">&nbsp;</td>
				<td>编号:</td>
				<td align="left"><s:textfield name="device.num"
					size="30" cssClass="Input" /></td>
				<td align="right">位置:</td>
					<td align="left">
						<s:textfield name="device.location" size="30" placeholder="请输入腾讯地图坐标" cssClass="Input" />
                        <a href="http://lbs.qq.com/tool/getpoint/" target="_blank">坐标拾取</a>
					</td>
				</tr>

				<tr align="left"  style="display: none">
					<td width="2%">&nbsp;</td>
					<td >微信二维码：</td>
					<td align="left">
					<s:if test="device.wximage != null">
						<span id="showimg" name="showimg"> <img
							src="showWxImage.action?id=<s:property value="device.id"/>"
							width="220" height="320"> <a href="javascript:changeimg()">编辑</a></span>
						<span id="uploadimg" name="uploadimg" style="display: none">
						<s:file name="upload" id="upload" label="请选择照片" /></span>
					</s:if>
					<s:else>
					 <s:file name="upload" label="请选择照片" />
					</s:else>
					
					</td>
					<td ></td>
					<td align="left"></td>
				</tr>
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>说明</td>
					<td align="left"><s:textfield id="device.comment" name="device.comment" size="30"
						cssClass="Input" /></td>
					<td colspan="2"></td>
					
				</tr>
				
			</table>
			</fieldset>
			</td>
		</tr>
		<tr>
		  <td>
		    <iframe width="100%" height="380" src="latticeList.action?id=<s:property value="device.id"/>"></iframe>
		  </td>
		</tr>
		<tr>
			<td>
			<table width="100%" align="center" border="1" cellspacing="0"
				cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
				frame="void" rules="rows">
				<tr>
					<td width="3%"></td>
					<td><input type="button" name="ok" value="保存"
						class="FixButtonStyle" onclick=
							adduser();
> <input
						type="button" name="cancel" value="取消" class="FixButtonStyle"
						onClick=
	back();
></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>

