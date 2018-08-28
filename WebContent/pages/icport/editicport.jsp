<%@ page language="java" import="java.util.*"
         contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>"/>

    <title><s:text name="content.approvalManage.title"/></title>

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
        document.input.action = "iCPortList.action";
        document.input.submit();
    }

    function adduser() {
        document.input.submit();
    }


</script>
<script language="javascript" src="/javascript/JumpMenu.js"></script>
<body>
<s:form name="input" action="editIcport.action" method="post"
        enctype="multipart/form-data">
    <table width="100%" border="0" cellpadding="0" cellspacing="10"
           class="NormalText">
        <input type="hidden" name="id" id="id"
               value="<s:property value="id"/>"/>
        <input type="hidden" name="icPort.id" id="icPort.id"
               value="<s:property value="icPort.id"/>" />
        <s:textfield type="hidden" name="icPort.iccard" />
        <s:textfield type="hidden" name="icPort.thumb" />
        <s:textfield type="hidden" name="icPort.createtime" />
        <s:textfield type="hidden" name="oldLatticeId" />
        <tr>
            <td class="TipWord">编辑玩具信息</td>
        </tr>

        <tr>
            <td class="activeFenceLine"></td>
        </tr>
    </table>
    &nbsp;&nbsp;&nbsp;<font color="red"> <span>&nbsp;&nbsp; ${resultMes }</span><s:fielderror/></font>
    <br/>
    <table width="100%" border="0" cellpadding="0" cellspacing="10"
           class="NormalText">
        <tr>
            <td>
                <fieldset class="FieldsetBorder">
                    <legend
                            class="LegendTitle">绑卡信息
                    </legend>
                    <table width="100%" align="center" border="1" cellspacing="0"
                           cellpadding="3" bordercolorlight="#C5EAED" bordercolordark="#ffffff"
                           frame="void" rules="rows" class="NormalText">

                        <tr align="left">
                            <td width="2%">&nbsp;</td>
                            <td>名称</td>
                            <td align="left"><s:textfield name="icPort.name" size="30" cssClass="Input" /></td>
                            <td>零售价格:</td>
                            <td align="left"><s:textfield name="icPort.price" size="30" cssClass="Input" /></td>
                        </tr>



                        <tr align="left">
                            <td width="2%">&nbsp;</td>
                            <td>玩具大小:</td>
                            <td align="left">
                                <input type="radio"  name="icPort.type" value="1"  <s:if test="icPort.type==\"1\"" > checked </s:if> /> 大
                                <input type="radio"  name="icPort.type" value="2"   <s:if test="icPort.type==\"2\"" > checked </s:if> /> 中
                                <input type="radio"  name="icPort.type" value="3"   <s:if test="icPort.type==\"3\"" > checked </s:if> /> 小
                            </td>
                            <td>缩略图:</td>
                            <td align="left">
                                <input name="thumb" type="file" />
                            </td>
                        </tr>

                        <tr align="left">
                            <td width="2%">&nbsp;</td>
                            <td>原价:</td>
                            <td align="left"><s:textfield name="icPort.oldprice" size="30" cssClass="Input" /></td>
                            <td>所在格子：</td>
                            <td align="left">
                                <s:select list="lattices" name="lattice"  label="所在格子" listKey="id" listValue="lockid+'-'+qrcode"  headerKey="0"  value="icPort.lattice.id"/>
                            </td>
                        </tr>

                        <tr align="left">
                            <td width="2%">&nbsp;</td>
                            <td>描述信息：</td>
                            <td align="left" colspan="3" height="300" valign="top" style="width: 90%;">
                                <script id="container" name="icPort.description" type="text/plain">
                                    <s:property value="icPort.description" escape="false" />
                                </script>
                                <!-- 配置文件 -->
                                <script type="text/javascript" src="ueditor/ueditor.config.js"></script>
                                <!-- 编辑器源码文件 -->
                                <script type="text/javascript" src="ueditor/ueditor.all.js"></script>
                                <!-- 实例化编辑器 -->
                                <script type="text/javascript">
                                    window.UEDITOR_HOME_URL = '/uedit/';
                                    var ue = UE.getEditor('container',{
                                        initialFrameHeight: 400,
                                        imagePathFormat: "/upload/"
                                    });
                                </script>
                            </td>
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

