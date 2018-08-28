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

function update(id){

	document.forms[0].action = "toeditDevice.action?id="+id;
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
			alert("<s:text name='请选择需要删除的项'/>");
		}else{
			if (confirm("<s:text name='确定删除吗?'/>")){
			document.forms[0].action = "deleteDevice.action";
			document.forms[0].submit();	
			}
		}
	}
  	
  	function add(){
  		
  		document.forms[0].action = "toAddTrading.action";
		document.forms[0].submit();	
  		
  	}
	function searchUser(){
		document.forms[0].action = "socketLogList.action";
		document.forms[0].submit();	
	}
	
	function searchUser1(){
		document.forms[0].action = "socketlogDetail.action";
		document.forms[0].submit();	
	}
	function deleteTrading(){
		document.forms[0].action = "deleteTrading.action";
		document.forms[0].submit();	
	}
	
	function exportTrading(){
		document.forms[0].action = "exportTrading.action";
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
<s:form name="input"  method="post">
    <table width="100%" border="0" cellpadding="0" cellspacing="10" class="NormalText">
        <tr>
            <td class="TipWord">数据查询管理</td>
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
			<legend class="LegendTitle"><s:text name="数据查询搜索" /></legend>
			<table class="NormalText">			
			
				<tr>
			 
					<td width="5%" align="right"><s:text
						name="关键词" /></td>
                    <td  width="36%" align="left"> 
							<input type="text" name="lattice" size="30" value="<s:property value='#request.lattice'/>"/>        </td>
					<td>
					<td><input type="button" id="search" class="FixButtonStyle"
						value="<s:text name='common.button.search'/>"
						
						
						onclick="searchUser();" />
						<input type="button" id="search" class="FixButtonStyle"
						value="<s:text name='格子详细信息'/>"
						onclick="searchUser1();" />
						<!-- <input type="button" id="search" class="FixButtonStyle"
						value="<s:text name='导出报表'/>"
						onclick="exportTrading();" />
						
						   <input type="button" id="search" class="FixButtonStyle"
						value="<s:text name='新增'/>"
						onclick="add();" />  -->
						
						
						</td>
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
                            onclick="reverseAllCheckboxes(this, document.input.ids)" /></TH>
                        <TH vAlign=center noWrap width="5%"><s:text name="序号" /></TH>
                        <TH vAlign=center noWrap width="10%"><s:text name="用户名"/></TH>
                        <TH vAlign=center noWrap width="15%"><s:text name="类名" /></TH>
                        <TH vAlign=center noWrap width="10%"><s:text name="方法" /></TH>
                        <TH vAlign=center noWrap width="10%"><s:text name="级别" /></TH>
                        <TH vAlign=center><s:text name="内容" /></TH>
                        <TH vAlign=center noWrap width="15%"><s:text name="操作时间" /></TH>
                    </tr>
                </THEAD>
                <!--Resource View Table Entry -->
				<s:iterator value="list"  status="index">
					<tr class="<s:if test='#index.Even'>DarkEntryTr</s:if><s:else>LightEntryTr</s:else>">
						<td class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">
						<input type="checkbox" name="ids" value="<s:property value='id'/>" /></td>
						<td class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="id" /></td>
						<td class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="username" /></td>
						<td class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="classname" /></td>
                        <td class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="method" /></td>
                        <td class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="level" /></td>
                        <td class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="msg" /></td>
						<td class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">
						  <s:property value="createtime" />
                        </td>
						</tr>
				</s:iterator>
			</table></div>
			</td><td width="1%"></td>
		</tr>
	</table>
	<div align="right" style="width:100%;">
      <div align="right" style="padding-right:10px;"
                class="NormalText">
                <s:if test="#request.list != null && #request.list.size > 0">
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
