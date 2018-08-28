<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
    function checkDeleteIds(ids) {
        var flag = false;
        if (ids != null) {
            if (ids.length != undefined) {
	            for (var i = 0; i < ids.length; i++) {
	                if (ids[i].checked == true) {
	                    flag = true;
	                    break;
	                }
	            }
            } else {
                if (ids.checked == true)
                    flag = true;
            }
        }
        if (flag) {
        	return true;
        } else {
            alert("<s:text name='content.fileapprove.deleteorg.error'/>");
            return false;
        }
    }
    function deleteOrgs(ids) {
        if (checkDeleteIds(ids)) {
        	document.input.action='deleteOrgs.action';
            document.input.submit();
            return;
        }
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
    function addOrg() {
        document.input.action = "toSaveOrg.action";
        document.input.submit();
        return;
    }
  </script>
<body>
<s:form name="input" action="manageOrgs.action" method="post">
	<table width="100%" border="0" cellpadding="0" cellspacing="10"
		class="NormalText">
		<tr>
			<td class="TipWord"><s:text name="content.mainPage.tree.organization" /></td>
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
					<td><s:fielderror /></td>
				</tr>
				<tr>
					<td><s:submit value="%{getText('allpgjsp.add')}"
						cssClass="FixButtonStyle" onclick="addOrg();" /> <input
						type="button" value='<s:text name="allpgjsp.del"/>'
						class="FixButtonStyle"
						onclick="deleteOrgs(document.input.ids);" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td class="GrooveArea">
			<div class=datagrid
				style="position: relative; width: 100%; height: 320; z-index: 1; overflow: scroll; clip: rect(0, auto, 320, 0);">
			<table width="100%" border="0" cellpadding="2" cellspacing="0">
				<!--Resource View Table Title -->
				<THEAD>
					<tr>
						<TH vAlign=center align="center" noWrap width="5%"><s:checkbox
							name="main"
							onclick="reverseAllCheckboxes(this, document.input.ids)" /></TH>
						<TH vAlign=center align=middle noWrap width="5%"><s:text name="confnetrule.number" /></TH>
						<TH vAlign=center align=middle noWrap width=30%><s:text
							name='tab.name' /></TH>
						<TH vAlign=center align=middle noWrap width=60%><s:text
                            name='cfgldpcrtjsp.desp' /></TH>
					</tr>
				</THEAD>

				<!--Resource View Table Entry -->
				<s:iterator value="orgs" status="brlist" id="org">
					<tr
						class="<s:if test='#brlist.Even'>DarkEntryTr</s:if><s:else>LightEntryTr</s:else>">
						<td
							class="<s:if test='#brlist.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"
							style="BORDER-LEFT: 0px" width="5%" height="26"><input
							type="checkbox" name="ids" value="<s:property value='id'/>" /></td>
						<td
							class="<s:if test='#brlist.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"
							width="5%">&nbsp;<a
							href="toSaveOrg.action?id=<s:property value='id'/>"><s:property
							value="(#request.cp - 1) * #request.pn + #brlist.index + 1" /></a></td>
						<td
							class="<s:if test='#brlist.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"
							width="30%">&nbsp;<s:property value="name" /></td>
					    <td
                            class="<s:if test='#brlist.Even'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"
                            style="BORDER-RIGHT: 0px" width="60%">&nbsp;<s:property value="remark" /></td>
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
                            style="BORDER-LEFT: 0px" width="5%" height="26">&nbsp;</td>
                        <td
                            class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"
                            width="5%">&nbsp;</td>
                        <td
                            class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"
                            width="30%">&nbsp;</td>
                        <td
                            class="<s:if test='current%2 > 0'>DarkLogLine</s:if><s:else>LightLogLine</s:else>"
                            style="BORDER-RIGHT: 0px" width="60%">&nbsp;</td>      
                        </tr>
                    </s:iterator>
                </s:bean>
			</table>
			</div>
			</td>
		</tr>
		<tr>
			<td>
			<div align="right" style="width: 100%;">
			<div align="right" style="padding-right: 10px;" class="NormalText">
			<s:if
				test="#request.orgs != null && #request.orgs.size > 0">
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
						onclick="doCurrentPage('first');" cssStyle="width:38px;" />
				</s:else>
				<s:if test="#request.currentPage == #request.totalPages">
					<s:submit id="toEndPage" cssClass="FixButtonStyle"
						value="%{getText('PAGE_END_PAGE')}" disabled="true"
						cssStyle="width:38px;" />
				</s:if>
				<s:else>
					<s:submit id="toEndPage" cssClass="FixButtonStyle"
						value="%{getText('PAGE_END_PAGE')}"
						onclick="doCurrentPage('end');" cssStyle="width:38px;" />
				</s:else>
				<s:if test="#request.currentPage == 1">
					<s:submit cssClass="FixButtonStyle"
						value="%{getText('PAGE_PREVIOUS_PAGE')}" disabled="true"
						cssStyle="width:38px;" />
				</s:if>
				<s:else>
					<s:submit cssClass="FixButtonStyle"
						value="%{getText('PAGE_PREVIOUS_PAGE')}"
						onclick="doCurrentPage('previous');" cssStyle="width:38px;" />
				</s:else>
				<s:if test="#request.currentPage == #request.totalPages">
					<s:submit id="nextPageId" cssClass="FixButtonStyle"
						value="%{getText('PAGE_NEXT_PAGE')}" disabled="true"
						cssStyle="width:38px;" />
				</s:if>
				<s:else>
					<s:submit id="nextPageId" cssClass="FixButtonStyle"
						value="%{getText('PAGE_NEXT_PAGE')}"
						onclick="doCurrentPage('next');" cssStyle="width:38px;" />
				</s:else>
				<span><s:text name="PAGE_GOTO_PAGE" /></span>
				<s:textfield id="gotoText" name="gotoText" size="6" maxlength="6"
					cssStyle="width:25px;" />
				<s:submit onclick="doGoto(document.input.totalPages.value)"
					value="GO" />
				<span>&nbsp;&nbsp;</span>
				<s:property value="#request.currentPage" />
				<span> / </span>
				<s:property value="#request.totalPages" />
				<span>&nbsp;&nbsp;</span>
			</s:if></div>
			</div>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>


