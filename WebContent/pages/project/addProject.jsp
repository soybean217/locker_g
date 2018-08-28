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
<script type="text/javascript" src="pages/js/jquery-1.4.4.min.js"></script>
<link href="pages/css/xtree.css" rel="stylesheet" type="text/css">
<link href="pages/css/unicode_cn.css" rel="stylesheet" type="text/css">
<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
<link href="pages/css/highguardnetworks_cn.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">

function back() {
    document.input.action = "listProject.action";
    document.input.submit();
}

function adduser(){

	  document.input.submit();
}




function change(){
	var id;
	var  ids = document.getElementById("project.firstproject");
	for(var i =0;i<ids.length;i++){

		if(ids.options[i].selected){
			id =  ids.options[i].value;
			}
		}
	var url = "projectajax.action";
	var params={'firstprojectid':id};
	$.post(url,params,callback);
}

function callback(result,textstatus){
	if(textstatus=='success'){
            if(result!=null&&result!=""){
               var select2 = $("#select2");
               select2.empty();
               var temp = result.split(";");
               for(var i = 0;i<temp.length;i++){
                   var temp1=temp[i].split(":");
                   select2.append("<option value='"+temp1[0]+"'>"+temp1[1]+"</option>");

                   }
                }

		}
	
}

function a(){
	  var select2 = $("#select2");
      select2.empty();
      select2.append("<option value='1'>111111</option>");
}

</script>
<script language="javascript" src="/javascript/JumpMenu.js"></script>
<body >
<s:form name="input" action="addProject.action"  method="post" enctype="multipart/form-data">
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
                    <td>名称:</td>
                    <td align="left" colspan="3"><s:textfield name="project.name" size="30"   cssClass="Input" /></td>
			    </tr>
				<tr align="left">
					<td align="left"></td>
                    <td>一级科目名:</td>
                    <td align="left" colspan="3"><s:select  onchange="change();" headerKey="-1" headerValue="--  请选择 --"  id="project.firstproject" name="project.firstproject" list="itemMap"  cssStyle="width:180px;" value="project.firstproject"/></td>
			    </tr>
			   
               <tr align="left">
					<td align="left"></td>
                    <td>二级科目名:</td>
                    <td align="left" colspan="3"><s:select id="select2" name="project.secondproject" list="#{1:'-- 无 --'}" cssStyle="width:180px;" value="project.secondproject"/></td>
			    </tr>
			        <tr align="left">
					<td align="left"></td>
                    <td>成绩类型:</td>
                    <td align="left" colspan="3"><s:select id="select2" name="project.scoretype" list="scoretypeMap" cssStyle="width:180px;" value="score.item"/></td>
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

