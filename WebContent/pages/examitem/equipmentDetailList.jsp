<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://www.tldExample.cn/test/functions"   prefix="myfn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="pages/js/validator.js"></script>
<script type="text/javascript" src="pages/js/strategy.js"></script>
<script type="text/javascript" src="pages/js/common.js"></script>
<script type="text/javascript" src="pages/js/partitionPageAndSearch.js"  ></script>
<link href="pages/css/unicode_cn.css" rel="stylesheet" type="text/css">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" href="pages/css/style.css" />
<link rel="stylesheet" href="pages/css/highguard_table.css" />
<link href="pages/css/unicode_cn.css" rel="stylesheet" type="text/css">
<link href="pages/css/highguardnetworks_cn.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="pages/js/WdatePicker/WdatePicker.js" defer="defer"></script>
    <script type="text/javascript" src="pages/js/WdatePicker/js/lang/zh-cn.js"></script>
</head>
<script type="text/javascript">

function testdeleteLDAPUsers(){
	document.forms[0].action = "testdeleteLocalLdapUser.action";
	document.forms[0].submit();	
}

  	function deleteLDAPUsers(){
		var checkLength = document.getElementsByName("ids").length;
		var countChecked = 0;
		for(var i=0;i<checkLength;i++){
			if(document.getElementsByName("ids")[i].checked == true){
				countChecked = countChecked+1;
			}
		}
		if(countChecked == 0){
			alert("请选择要删除的信息");
		}else{
			if (confirm("确认删除吗？")){
			document.forms[0].action = "deleteExamItem.action";
			document.forms[0].submit();	
			}
		}
	}
	function searchUser(){
		document.forms[0].action = "equipmentDetailResultList.action";
		document.forms[0].submit();	
	}
	
	function modifyStatus(username){
		//$("#status").load("",{userName:username},);
		$.post("modifyStatus.action",{userName:username},function(responseText){
				alert(responseText);
				$("#"+username).html('<s:text name="content.ldap.add.normal"/>');
			}
		);
	}
	
    function validateDoGoto(totalPages) {
        var goPage = document.input.gotoText.value;
        if (!isInteger(goPage)) {
            alert("<s:text name='gototext.fail'/>" + totalPages + "<s:text name='gototext.fail1'/>");
            return false;
        } else {
            if (goPage > totalPages || goPage < 1) {
                alert("<s:text name='gototext.fail'/>" + totalPages + "<s:text name='gototext.fail1'/>");
                return false;
            } else {
                return true;
            }
        }
    }
    function doGoto(totalPages) {
        if (validateDoGoto(totalPages)) {
            var goPage = document.input.gotoText.value;
            document.input.currentPage.value = goPage;
            document.input.submit();
            return;
        }
    }
  </script>
<body onload="window.status='finished';">
<s:form name="input" enctype="multipart/form-data" method="post">
    <table width="100%" border="0" cellpadding="0" cellspacing="10" class="NormalText">
        <tr>
            <td class="TipWord">考核详细信息管理</td>
        </tr>
        <tr>
            <td class="activeFenceLine"></td>
        </tr>
        
    </table>
      <span>&nbsp;&nbsp; ${resultMes }</span>
	<br />
	<table width="100%" border="0" cellpadding="2" cellspacing="5" class="NormalText">

			<tr>
			<td width="1%"></td>
			<td>
			<fieldset class="fieldsetBorder">
			<legend class="LegendTitle"><s:text name="搜索条件" /></legend>
			<table class="NormalText">			
				<tr>
					<td width="6%" align="right"><s:text
						name="姓名" /></td>
					<td width="18%" align="left">
                       <input type="text" name="useNameSearch" size="15" value="<s:property value='#request.useNameSearch'/>"/>
                    </td>
                    <td width="5%" align="right"><s:text
						name="到达时间" /></td>
                    <td  width="36%" align="left"> 
							<s:textfield name="fTime" cssClass="Wdate" onfocus="WdatePicker({skin:'default',startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
                    cssStyle="cursor: hand;" value="%{getText('global.date.full',{fTime == null ? '' : fTime})}"/>
                    <s:text name="log.to"/>&nbsp;<s:textfield name="eTime" cssClass="Wdate" onfocus="WdatePicker({skin:'default',startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
                    cssStyle="cursor: hand;" value="%{getText('global.date.full',{eTime == null ? '' : eTime})}"/>                     </td> 
					<td> </td>
				</tr>
				<tr>
				   <td width="6%" align="right"><s:text
						name="天线号" /></td>
					<td width="18%" align="left">
                       <input type="text" name="antenna" size="15" value="<s:property value='#request.antenna'/>"/>
                    </td>
                     <td width="6%" align="right"><s:text
						name="设备串号" /></td>
					<td width="18%" align="left">
                       <input type="text" name="device" size="30" value="<s:property value='#request.device'/>"/>
                    </td>
					<td><input type="button" id="search" class="FixButtonStyle"
						value="<s:text name='common.button.search'/>"
						onclick="searchUser();" /></td>
				</tr>
			</table>
			</fieldset>
			</td>
		</tr>
		<tr>
			<td width="1%"></td>
			<td class="GrooveArea">
			<div class=datagrid style="position: relative; width: 100%; height: 320; z-index: 1; overflow: scroll; clip: rect(0, auto, 320, 0);">
			<table width="100%" border="0" cellpadding="2" cellspacing="0">
			    <!--Resource View Table Title -->
                <THEAD>
                    <tr><TH vAlign=center align=middle noWrap width="4%"><s:checkbox name="mainUser"
                            onclick="reverseAllCheckboxes(this, document.input.userIds)" /></TH>
                        <TH vAlign=center align=middle noWrap width="5%"><s:text name="序号" /></TH>
                        <TH vAlign=center align=middle noWrap width="10%"><s:text name="姓名" /></TH>
                        <TH vAlign=center align=middle noWrap width="15%"><s:text name="跑步距离" /></TH>
                        <TH vAlign=center align=middle noWrap width="15%"><s:text name="用时" /></TH>
                        <TH vAlign=center align=middle noWrap width="8%"><s:text name="天线号" /></TH>
                         <TH vAlign=center align=middle noWrap width="25%"><s:text name="设备串号" /></TH>
                        <TH vAlign=center align=middle noWrap width="20%"><s:text name="到达时间" /></TH>
                    </tr>
                </THEAD>
                <!--Resource View Table Entry -->
				<s:iterator value="resultlist" id="list"  status="index">
					<tr class="<s:if test='#index.Even'>DarkEntryTr</s:if><s:else>LightEntryTr</s:else>">
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">
						<input type="checkbox" name="ids" value="<s:property value='id'/>" /></td>
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="#index.index+1" /></td>
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="#list[0]" /></td>
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="#list[2]" /> m(单位:米)</td>
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="#list[1]" /> s(单位:秒)</td>
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"> 
						<s:property value="#list[4]" />
						</td>
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"> 
						<s:property value="#list[6]" />
						</td>
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"> 
						<s:property value="#list[5]" />
						</td>
				</tr>
				</s:iterator>
                  <s:bean name="org.apache.struts2.util.Counter" id="counter">
                    <s:param name="first" value="#request.rows + 1" />
                    <s:param name="last" value="#request.pageNum" />
                  <s:iterator>
                      <tr
                          class="<s:if test='current%2 > 0'>DarkEntryTr</s:if><s:else>LightEntryTr</s:else>">
                           <td
                              class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">&nbsp;</td>
                          <td
                              class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">&nbsp;</td>
                          <td
                              class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">&nbsp;</td>
                          <td
                              class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">&nbsp;</td>
                          <td
                              class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">&nbsp;</td>
                          <td
                              class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">&nbsp;</td>
                          <td
                              class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">&nbsp;</td>
                          <td
                              class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">&nbsp;</td>
                   </tr>
                  </s:iterator>
                </s:bean>
			</table></div>
			</td><td width="1%"></td>
		</tr>
	</table>
	<div align="right" style="width:100%;">
      <div align="right" style="padding-right:10px;"
                class="NormalText">
                <s:if test="#request.resultlist != null && #request.resultlist.size > 0">
                <s:hidden name="currentPage" />
                <s:hidden name="totalPages" />

                <span style="font-size: 13px;"><s:text name="PAGE_TOTAL" /><s:property
                    value="#request.totalNum" /><s:text name="PAGE_TOTALLINE" /> <span>&nbsp;</span>
                </span>

                <span style="font-size: 13px;"><s:text name="PAGE_PER_PAGE" /></span>
                <s:select name="pageNum" value="#request.pageNum"
                    list="#{'10':'10','20':'20','50':'50','100':'100'}" listKey="key"
                    listValue="value" onchange="changeLinesPerPage(this.value)" />
                <span>&nbsp;&nbsp;</span>
                <s:if test="#request.currentPage == 1">
                    <s:submit cssClass="FixButtonStyle"
                        value="%{getText('PAGE_FIRST_PAGE')}" disabled="true"
                        cssStyle="width:38px;" />
                </s:if>
                <s:else>
                    <s:submit cssClass="FixButtonStyle"
                        value="%{getText('PAGE_FIRST_PAGE')}"
                        onclick="toCurrentPage('first');" cssStyle="width:38px;" />
                </s:else>
                <s:if test="#request.currentPage == #request.totalPages">
                    <s:submit id="toEndPage" cssClass="FixButtonStyle"
                        value="%{getText('PAGE_END_PAGE')}" disabled="true"
                        cssStyle="width:38px;" />
                </s:if>
                <s:else>
                    <s:submit id="toEndPage" cssClass="FixButtonStyle"
                        value="%{getText('PAGE_END_PAGE')}"
                        onclick="toCurrentPage('end');" cssStyle="width:38px;" />
                </s:else>
                <s:if test="#request.currentPage == 1">
                    <s:submit cssClass="FixButtonStyle"
                        value="%{getText('PAGE_PREVIOUS_PAGE')}" disabled="true"
                        cssStyle="width:38px;" />
                </s:if>
                <s:else>
                    <s:submit cssClass="FixButtonStyle"
                        value="%{getText('PAGE_PREVIOUS_PAGE')}"
                        onclick="toCurrentPage('previous');" cssStyle="width:38px;" />
                </s:else>
                <s:if test="#request.currentPage == #request.totalPages">
                    <s:submit id="nextPageId" cssClass="FixButtonStyle"
                        value="%{getText('PAGE_NEXT_PAGE')}" disabled="true"
                        cssStyle="width:38px;" />
                </s:if>
                <s:else>
                <s:submit id="nextPageId" cssClass="FixButtonStyle"
                        value="%{getText('PAGE_NEXT_PAGE')}"
                        onclick="toCurrentPage('next');" cssStyle="width:38px;" />
                </s:else>
                <span><s:text name="PAGE_GOTO_PAGE" /></span>
                <s:textfield id="gotoText" name="gotoText" size="6" maxlength="6"
                    cssStyle="width:25px;" />
                <s:submit onclick="doGoto(document.input.totalPages.value)" value="GO" />
                <span>&nbsp;&nbsp;</span>
                <s:property value="#request.currentPage" />
                <span> / </span>
                <s:property value="#request.totalPages" />
                <span>&nbsp;&nbsp;</span>
            </s:if>
            </div>  
      </div>
</s:form>
</body>
</html>
