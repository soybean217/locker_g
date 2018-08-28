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

<title><s:text name="sfa.version.mgmt" /></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="pages/js/validator.js"></script>
<script type="text/javascript" src="pages/js/strategy.js"></script>
<script type="text/javascript" src="pages/js/common.js"></script>
<link href="pages/css/unicode_cn.css" rel="stylesheet" type="text/css">
<style type="text/css">


.dddd{
	background-color: #36A6AB;
}
.FixButtonStyle2 {
	font-family: "宋体", "Arial", "Helvetica", "sans-serif";
	font-size: 12px;
	font-style: normal;
	line-height: 150%;
	color: #ffffff;
	text-decoration: none;
	background-color: #C7E3F3;
	cursor: hand;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #70CCD1;
	border-right-color: #298185;
	border-bottom-color: #298185;
	border-left-color: #70CCD1;
	width: 80px;
}

</style>

<script type="text/javascript">

	function nestStep(){
		var type = document.getElementById("updateType").value;
		if(type==0){
			window.location.href="goPublish2.action";
		}else{
			window.location.href="goPublishPrint2.action";
		}
	}

	function goBack(){
		var type = document.getElementById("updateType").value;
		if(type==0){
			window.location.href="manageSfaClientUpdate.action";
		}else{
			window.location.href="managePrintClientUpdate.action";	
		}
	}

</script>
</head>


<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="10" class="NormalText">
		<tr>
			<td class="TipWord">
				<s:if test="updateType==0">
					集中管控客户端发布
				</s:if>
				<s:else>
					集中文印客户端发布
				</s:else>
			</td>
		</tr>
		<tr>
			<td class="activeFenceLine"></td>
		</tr>
		
		<tr>
			<td><font color="red"><s:fielderror /></font></td>
		</tr>
		<tr>
			<td width="300px">
				<div style="width: 90px;height: 13px;background-color: #36A6AB; font-size: 16px;text-align: center;float: left;"><font color="#F48C05"> <b>导入文件>></b></font></div>
				<div style="width: 90px;height: 15px;background-color: #C7E3F3; font-size: 16px;text-align: center; float: left;"><font color="#F48C05"><b>发布</b></font></div>
			</td>
		</tr>
		<tr>
			<td>
			<fieldset class="FieldsetBorder">
			<legend class="LegendTitle">客户端文件 </legend>
			<table width="98%" align="center" border="1" cellspacing="0"
				cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
				frame="void" rules="rows" class="NormalText">
				<tr>
               		<td></td>
               		<td>说明：请导入zip格式的压缩文件</td>
                </tr>
                <tr>
                	<td width="8%">文件</td>
                	<td>
	                	<s:form action="uploadFile.action" method="POST"  enctype="multipart/form-data">
	                		<s:hidden id="updateType" name="updateType"></s:hidden>
							<s:file name="myFile" value="ddd" label="wen" size="80" />
							<s:submit value="导入" cssClass="FixButtonStyle"></s:submit>
					    </s:form>
				   </td>
                </tr>
                <s:if test="listFiles">
                <tr>
                	<td colspan="2" class="GrooveArea"><div class=datagrid style="position:relative; width:100%; height:320; z-index:1; overflow: scroll; clip: rect(0 auto 320 0);"> 
			          <table width="100%" border="0" cellpadding="2" cellspacing="0">
			          <!--Resource View Table Title -->     
			            <THEAD>
			            <tr> 
			              <TH vAlign="middle" align="center" noWrap width="25%" ><s:text name="文件名"/></TH>
			              <TH vAlign="middle" align="center" noWrap width="3%" ><s:text name='文件版本号'/></TH>
			              <TH vAlign="middle" align="center" noWrap width="25%" ><s:text name='文件MD5值'/></TH>
			              <TH vAlign="middle" align="center" noWrap width="5%" ><s:text name='大小'/></TH>
			              <TH vAlign="middle" align="center" noWrap width="6%" ><s:text name='修改日期'/></TH>
			            </tr>
			            </THEAD>
			            <!--Resource View Table Entry -->
			            <s:iterator value="listFiles" status="brlist" id="cf">
			            <tr align="left">
			              <td align="left" class="<s:if test='#brlist.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">&nbsp;<s:property value="filePath"/></td>
			              <td class="<s:if test='#brlist.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">&nbsp;<s:property value="version"/></td>
			              <td class="<s:if test='#brlist.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">&nbsp;<s:property value="md5Value"/></td>
			              <td class="<s:if test='#brlist.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">&nbsp;<s:property value="fileSize"/>&nbsp;KB</td>
			              <td class="<s:if test='#brlist.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">&nbsp;<s:property value="lastModifyDate"/></td>
			            </tr>
			            </s:iterator>
			               <s:bean name="org.apache.struts2.util.Counter" id="counter">
			                 <s:param name="first" value="#request.rows + 1" />
			                 <s:param name="last" value="#request.pageNum" />
			                 <s:iterator>
			                     <tr
			                         class="<s:if test='current%2 > 0'>DarkEntryTr</s:if><s:else>LightEntryTr</s:else>">
			                         <td
			                             class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"
			                             style="BORDER-LEFT: 0px" width="10%">&nbsp;</td>
			                         <td
			                             class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"
			                             width="20%">&nbsp;</td>
			                         <td
			                             class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"
			                             width="20%">&nbsp;</td>
			                         <td
			                             class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"
			                             width="14%">&nbsp;</td>
			                         <td
			                             class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"
			                             width="14%">&nbsp;</td>
			                     </tr>
			                 </s:iterator>
			              </s:bean>
			          </table>
          			</td> 
                </tr>
                <tr align="right">
        			<td colspan="2" align="right">总记录条数：<s:property value="allFileCount" />&nbsp; </td>
        		</tr>
                </s:if>
			 </table>
			</fieldset>
			</td>
		</tr>
		<tr>
			<td>
				 <s:if test="listFiles">
				 	<input type="button" value="下一步" onclick="nestStep()" class="FixButtonStyle">
				 </s:if>
				<s:else>
					<input type="button" readonly="readonly" disabled="disabled" value="下一步" onclick="nestStep()" class="FixButtonStyle2">
				</s:else>
				<input type="button" value="取消" onclick="goBack()" class="FixButtonStyle">
			 </td>
			<td></td>
		</tr>
	</table>
	</body>
</html>

