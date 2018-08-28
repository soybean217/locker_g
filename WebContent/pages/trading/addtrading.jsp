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
		document.input.action = "tradingList.action";
		document.input.submit();
	}

	function adduser() {
   //    var ss = document.getElementById("trading.fruitnum").value;

	// var   type="^[0-9]*[1-9][0-9]*$"; 
    //   var   re   =   new   RegExp(type); 
    //  if(ss.match(re)==null) 
    //  { 
     //   alert( "数量请输入大于零的整数!"); 
     //  return false;
     //  } 
		document.input.submit();
	}



function get(device){
	var id = device.value;
	if(id==null||""==id){
		return false;
	}
	 $.ajax({   
	        url : "latticeAjax.action",   
	        type : "GET",   
	        data : "id=" + id,   
	        success : function(data, textStatus) {   
	        	$("#gz").html(data);   
	        }   
	    });   

	
	//$("#gz").html("<select name='select'> <option value='001' onclick='getValue(this)'>中国</option><option value='001' onclick='getValue(this)'>222</option></select>");
}

</script>
<script language="javascript" src="/javascript/JumpMenu.js"></script>
<body>
<s:form name="input" action="addTrading.action" method="post"
	enctype="multipart/form-data">
	<table width="100%" border="0" cellpadding="0" cellspacing="10"
		class="NormalText">
		<input type="hidden" name="id" id="id"
			value="<s:property value="id"/>" />
		<input type="hidden" name="lattice.id" id="lattice.id"
			value="<s:property value="lattice.id"/>" />
			<input type="hidden" name="lattice.deviceid" id="lattice.deviceid"
			value="<s:property value="lattice.deviceid"/>" />
		<tr>
			<td class="TipWord">新增信息</td>
		</tr>
		<tr>
			<div>&nbsp;&nbsp; ${resultMes }</div>
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
				class="LegendTitle">购买信息</legend>
			<table width="100%" align="center" border="1" cellspacing="0"
				cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
				frame="void" rules="rows" class="NormalText">
		
			<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>购买用户</td>
					<td align="left"><s:select list="usermap" name="trading.userid"  label="abc" listKey="key" listValue="value"  headerKey="0"  value="trading.userid"/>

					<td></td>
					<td align="left"></td>
				</tr>
				
				
		
				
			<!-- 	
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>分配水果</td>
					<td align="left"><s:select list="fruitmap" name="trading.fruitid"  label="abc" listKey="key" listValue="value"  headerKey="0"  value="trading.fruitid"/>

</td>
					<td></td>
					<td align="left"></td>
				</tr>
				 -->
					<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>设备选择</td>
					<td align="left"><s:select onclick="get(this);" list="devicemap" name="trading.deviceid"  label="abc" listKey="key" listValue="value"  headerKey="0"  value="trading.deviceid"/>

                   </td>
					<td></td>
					<td align="left"></td>
				</tr>
					<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>格子</td>
                   <td align="left"><div id="gz"></div></td>
                   </td>
					<td></td>
					<td align="left"></td>
				</tr>
				<tr align="left">
					<td width="2%">&nbsp;</td>
					<td>购买数量:</td>
					<td align="left"><s:textfield name="trading.fruitnum"
						size="30" cssClass="Input" />(千克)</td>
					<td>时间 
					<s:textfield name="usetime"
						size="30" cssClass="Input" /> 格式（2012-12-12 12:12:12）
					</td>
					<td align="left"></td>
				</tr>
			</table>
			</fieldset>
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

