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
</head>

<body>
	<table width="100%" border="0" cellpadding="0" cellspacing="10" class="NormalText">
		<tr>
			<td class="TipWord">
				<s:if test="updateType==0">
					集中管控客户端管理
				</s:if>
				<s:else>
					集中文印客户端管理
				</s:else>
			</td>
		</tr>
		<tr>
			<td class="activeFenceLine"></td>
		</tr>
		<tr>
			<td style="font-size: 12px;color: red" colspan="3" align="left"><s:fielderror /></td>
		</tr>
		
		 <tr>
            <td>
            	<table>
            		<tr>
            			<td>
            				<s:form action="goPublish.action">
            					<s:hidden name="updateType"/>
            					<s:submit value="发布新版本" cssClass="FixButtonStyle"></s:submit>
            				</s:form>
            			</td>
            			<td>
            				<s:form action="goEditVersion.action">
            					<s:hidden name="minVersion"/>
            					<s:hidden name="maxVersion"/>
            					<s:hidden name="updateType"/>
            					<s:submit value="编辑版本" cssClass="FixButtonStyle"></s:submit>
            				</s:form>
            			</td>
            		</tr>
            	</table>
            </td>
        </tr>
        
		<tr>
			<td>
			<fieldset class="FieldsetBorder">
			<legend class="LegendTitle">当前版本信息 </legend>
			
			<table width="98%" align="center" border="1" cellspacing="0"
				cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
				frame="void" rules="rows" class="NormalText">
                <tr>
                	<td width="10%">当前版本号</td>
                	<td> <s:property value="currentVersion"/> </td>
                </tr>
                <tr>
                	<td width="10%">版本更新区间</td>
                	<td> <s:property value="minVersion"/> &nbsp;~&nbsp; <s:property value="maxVersion"/> </td>
                </tr>
                <tr>
                	<td width="10%">是否需要重启</td>
                	<td> <s:property value="restart"/></td>
                </tr>
                <tr>
                	<td>版本发布日期 </td>
                	<td><s:property value="publishDate"/> </td>
                </tr>
                <tr>
                    <td>版本更新说明</td>
                    <td>
                    	<ul style="margin: 0">
	                    	<s:iterator value="publishDesc" status="brlist" id="desc">
	                   			<li>&diams; <s:property value="desc"/></li>
	                   		</s:iterator>
                    	</ul>
                   		
                    </td>
                </tr>
			</table>
			</fieldset>
			</td>
		</tr>
		<tr> 
          <td class="GrooveArea"><div class=datagrid style="position:relative; width:100%; height:320; z-index:1; overflow: scroll; clip: rect(0 auto 320 0);"> 
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
              <td align="left" class="<s:if test='#brlist.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">&nbsp;<s:property value="fileName"/></td>
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
          </div>
          </td> 
        </tr> 
        <tr>
        	<td align="right">总记录条数：<s:property value="allFileCount" />&nbsp; </td>
        </tr>
	</table>
	
	</body>
</html>
