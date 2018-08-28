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

<title><s:text name="金额管理" /></title>

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
</head>
<script type="text/javascript">

function testdeleteLDAPUsers(){
	document.forms[0].action = "testdeleteLocalLdapUser.action";
	document.forms[0].submit();	
}

function toAddRecharge(){
	var userid = document.getElementById("userid").value;
	document.forms[0].action = "toAddRecharge.action?userid="+userid;
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
			document.forms[0].action = "deleteRecharge.action";
			document.forms[0].submit();	
			}
		}
	}
	function searchUser(){
		document.forms[0].action = "rechargeList.action";
		document.forms[0].submit();	
	}
	
	function exp(){
		
		document.forms[0].action = "rechargeExport.action";
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
            <td class="TipWord">金额管理</td>
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
				<td  align="left">
                     <s:text
						name="用户ID" />  <input type="text" name="num" size="15" value="<s:property value='#request.num'/>"/>
					<s:text
						name="姓名" />
					
                       <input type="text" name="name" size="15" value="<s:property value='#request.name'/>"/>
                       <name="充值金额" />
					
                       <input type="radio" <s:if test="price==1"> checked </s:if> name="price" size="15" value="1"/>正
                       <input type="radio" <s:if test="price==2"> checked </s:if>name="price" size="15" value="2"/>负
					<s:text
						name="充值时间" />
                    
							<s:textfield name="fTime" cssClass="Wdate" onfocus="WdatePicker({skin:'default',startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
                    cssStyle="cursor: hand;" value="%{getText('global.date.full',{fTime == null ? '' : fTime})}"/>
                    <s:text name="log.to"/>&nbsp;<s:textfield name="eTime" cssClass="Wdate" onfocus="WdatePicker({skin:'default',startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"
                    cssStyle="cursor: hand;" value="%{getText('global.date.full',{eTime == null ? '' : eTime})}"/>                
		
					<input type="button" id="search" class="FixButtonStyle"
						value="<s:text name='common.button.search'/>"
						onclick="searchUser();" />
						
					<input type="button" id="search" class="FixButtonStyle"
						value="<s:text name='导出'/>"
						onclick="exp();" />
						</td>
						
				</tr>
		
		<tr>
			<td width="1%"></td>
			<td><input type="button" id="add" class="FixButtonStyle"
				value="<s:text name='common.button.add'/>"
				onclick="window.location.href='toAddRecharge.action'" />&nbsp; <input
				type="button" id="delete" class="FixButtonStyle"
				value="<s:text name='common.button.delete'/>"
				onclick="deleteLDAPUsers()" /> &nbsp;
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
                        <TH vAlign=center align=middle noWrap width="5%"><s:text name="序号" /></TH>
                        <TH vAlign=center align=middle noWrap width="10%"><s:text name="金额(元)"/></TH>
                         <TH vAlign=center align=middle noWrap width="20%"><s:text name="用户ID"/></TH>
                        <TH vAlign=center align=middle noWrap width="10%"><s:text name="充值用户"/></TH> 
                        <TH vAlign=center align=middle noWrap width="15%"><s:text name="充值日期"/></TH>
                          <TH vAlign=center align=middle noWrap width="15%"><s:text name="充值人"/></TH>
                        <TH vAlign=center align=middle noWrap width="40%"><s:text name="充值说明"/></TH>
 
                    </tr>
                </THEAD>
                <!--Resource View Table Entry -->
				<s:iterator value="rechargeList" status="index">
					<tr class="<s:if test='#index.Even'>DarkEntryTr</s:if><s:else>LightEntryTr</s:else>">
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>">
						<input type="checkbox" name="ids" value="<s:property value='id'/>" /></td>
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="#index.index+1" /></td>
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="price"></s:property></td>
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="uuid"></s:property></td>
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="username"></s:property></td>
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="rechargeUser"></s:property></td>
						<td align="center" class="<s:if test='#index.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"><s:property value="comment"></s:property></td>

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
                <s:if test="#request.rechargeList != null && #request.rechargeList.size > 0">
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
