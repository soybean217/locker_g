
var autoSelectOperatorObject=null;
function autoSelectOperator(operatorObjId,modelType,stepId,stepObjId)
{
	var step="";
	var stepObj=document.getElementById(stepObjId);
	if((stepId!=undefined)&&(stepId!=""))
	{
		step=stepId;
	}else if(stepObj!=null)
	{
		step=stepObj.value;
	}
	if((step==null||step=="")||(operatorObjId==null||operatorObjId==""))
	{
		return;
	}
	var tmpObj=document.getElementById(operatorObjId);
	if(tmpObj!=null)
	{
		autoSelectOperatorObject=tmpObj;
		FlowManagerDwr.autoSelectOperator(doSelectOperator,step,modelType);
	}
}

function doSelectOperator(data)
{
	var hiddenObj=window.document.getElementById("hidden_" + autoSelectOperatorObject.id);
	if(data!=null)
	{
		autoSelectOperatorObject.value = data[1];
		if (hiddenObj != null) {
			hiddenObj.value = data[0];
		}
	}
}

//下载附件
function doLoad(type,path)
{
	var url=path+"/flowStepAttachFileAction.do?method=downLoadTemplateFile&type="+type;
	window.open(url,"downFrame");
}
/**
 *页面初始化时加载表格的tr样式（隔行换色）
 *param tableID:需要隔行还色的table的id
 *param hasTitle:boolean值 true:表格具有标题，false：表格没有标题
 */
function loadTableStyle(tableID,hasTitle){
	var tb=document.all(tableID);
	if(tb==null)
	{
		return;
	}
	tb.className="tb";
	tb.borderColor="#B3E1F0";
	var i=0;
	if(hasTitle){
		tb.rows[0].className="formTitleBar";
		i=1;
	}
	var len=tb.rows.length;
	for(;i<len;i++){
		if(i%2==0){
			tb.rows[i].className="trFirst";
		}else{
			tb.rows[i].className="trSecond";
		}
	}
}


//判断是否闰年
 function isLeapYear(year)				
    {												
        return (year % 4 == 0)					
               &&									
               (									
                   (year % 100 != 0)				
                   ||								
                   (year % 400 == 0)				
               );									
    }	
    
//拆分日期
function decodeDate(date)				
    {												
        var ymd = new Array();					
        ymd[0] = -1;								
        ymd[1] = -1;								
        ymd[2] = -1;								
        if (date == null || date == "")			
            return ymd;							
        var values = date.split("-");			
        if (values.length != 3)					
            return ymd;							
        ymd[0] = parseInt(values[0], 10);			
        ymd[1] = parseInt(values[1], 10);			
        ymd[2] = parseInt(values[2], 10);			
        return ymd;								
    }		

//日期格式校验
function validateDate(date)				
    {												
        if (date == null || date == "")			
            return false;																	
        var ymd = decodeDate(date);			
        var year  = ymd[0];						
        var month = ymd[1];						
        var day   = ymd[2];						
        if (year < 0 || year > 9999)				
            return false;							
        if (month < 1 || month > 12)				
            return false;							
        if (day < 1)								
            return false;							
        var isBigMonth   = month == 1 ||			
                           month == 3 ||			
                           month == 5 ||			
                           month == 7 ||			
                           month == 8 ||			
                           month == 10 ||			
                           month == 12;			
        var isSmallMonth = month == 4 ||			
                           month == 6 ||			
                           month == 9 ||			
                           month == 11;			
        if (isBigMonth && day > 31)				
            return false;							
        if (isSmallMonth && day > 30)				
            return false;							
        if (month == 2)							
        {											
            if (isLeapYear(year))			
            {										
                if (day > 29)						
                    return false;					
            }										
            else									
            {										
                if (day > 28)						
                    return false;					
            }										
        }											
        return true;								
    }	
    
    //校验月份
		function toChk(cycle)
		{
			
			var currentDate = new Date();
			var inputDate = cycle.substring(0,4)+'/'+cycle.substring(4)+'/'+'01';
			var regexs = /^[1,2]{1}[0-9]{3}((0[1-9]{1})|(1[0,1,2]))$/;
			var check = regexs.test(cycle);
			if(check==false)
			{
				alert('请按照规定格式输入月份(例:200801)');
				return false;
			}
			return true;
		}

   	function click() 
	{
		if(event.keyCode==13)
		{
			return false;
		}
	}
	
	document.onkeydown=click;
	
	//长度校验
	function getStrLen(s)                   
	    {                                             
	        var count = 0;                            
	        var lenByte = s.length;                   
	        for (i = 0; i < lenByte; i++)             
	        {                                         
	            if (s.charCodeAt(i) > 256)            
	                count = count + 2;                
	            else                                  
	                count = count + 1;                
	        }                                         
	        return count;                             
	    }  
	    
	function trim(s) 
	{
		return s.replace( /^\s*/, "" ).replace( /\s*$/, "" );
	}

	function showHiddenColumn(ID , path) 
	{
		var iconObj = document.getElementById("columnIcon"+ID );
		var divObj = document.getElementById("columnDiv"+ID);
		if(divObj.style.display=="none")
		{
			iconObj.src = path +"/images/icon_column_opened.gif"
			divObj.style.display = "inline";
		}
		else
		{
			iconObj.src = path + "/images/icon_column_closed.gif"
			divObj.style.display = "none";
		}
	}
	
	//归入版本时，走的选择处理人方法
	function doSelectSenderForEditionFlow(path, isMultiSelect, objId,stepId)
	{
		if((stepId==""))
		{
			alert("请先选择操作步骤!");
			return ;
		}
		doSelectSender(path, isMultiSelect, objId,stepId);
	}
	
	/**
	 * doSelectSender 选择处理人
	 * @param {type} 
	 * path :request.getContextPath()
	 * isMultiSelect: true 表示显示按钮多选 false 表示显示按钮单选
	 * objId： 返回值的ID 
	 */
function doSelectSender(path, isMultiSelect, objId, stepId, opertype) {
//		if((stepId==""))
//		{
//			alert("请先选择操作步骤!");
//			return ;
//		}
	var hidden_obj=document.getElementById("hidden_" + objId);
	var selectedValue="";
	if (hidden_obj != null) {
		selectedValue=hidden_obj.value;
	}
	   //多选
	if (isMultiSelect) {
		var url = path + "/userManager.do?method=queryUserList&selecttype=multi&objId=" + objId + "&stepId=" + stepId+"&objValue="+selectedValue;
	} else {
		var url = path + "/userManager.do?method=queryUserList&selecttype=select&objId=" + objId + "&stepId=" + stepId + "&operType=" + opertype+"&objValue="+selectedValue;
	}
	   //window.open(url);
	var returnStr = window.showModalDialog(url, "", "dialogHeight:600px;dialogWidth:800px;center: yes;help:no;resizable:no;status:no;");
	if (returnStr == undefined) {
		return;
	}
	window.document.getElementById(objId).value = returnStr[1];
	if (window.document.getElementById("hidden_" + objId) != null) {
		window.document.getElementById("hidden_" + objId).value = returnStr[0];
	}
//	window.document.getElementById("hidden_" + objId).value = returnStr[0];
}
/**
	 * 归入版本时调用查询版本页面。
	 * path :request.getContextPath()
	 * objId： 返回值的ID 
	 */
function doSelectEdition(path, objId, type, infoId,trEdId) {
	var url = path + "/editionManage.do?method=queryBaseInfo&flag=true&objId=" + objId + "&type=" + type + "&infoId=" + infoId + "&trEdId="+trEdId;
	var returnStr = window.showModalDialog(url, "", "dialogHeight:600px;dialogWidth:800px;center: yes;help:no;resizable:no;status:no;");
	if (returnStr == undefined) {
		return;
	}
	window.document.getElementById(objId).value = returnStr[0];
	if (window.document.getElementById(objId + "_status") != null) {
		window.document.getElementById(objId + "_status").value = returnStr[1];
	}
}
/**add by wangjian 用于设置基本信息可编辑元素的方法 begin*/
function setBaseInfo(flowId, stepId) {
		//传入参数：流程ID、环节ID。
		FlowManagerDwr.getElementIds(setElementAttribute,flowId,stepId);
	}
	var isRequireEdit=false;
	function setElementAttribute(data)
	{
		if(data!=null&&(data!=""))
		{
			isRequireEdit=true;
			for(var i=0;i<data.length;i++)
			{
				if(data[i]=="requireModelType_new"||data[i]=="modelType")
                {
                    document.getElementById("showModelType").value="edit";
                }
                setElementInfo(data[i]);
			}
            init();
	}
}
function init()
    {
        if (document.getElementById("showModelType")==null)
        {
            return;
        }
        if (document.getElementById("_view")==null)
        {
            return;
        }
        if (document.getElementById("_edt")==null)
        {
            return;
        }
        var showValue = document.getElementById("showModelType").value; 
        //只读
        if (showValue=="readonly")
        {
            document.getElementById("_view").style.display = "inline";
            document.getElementById("_edt").style.display="none";
        }
        else
        {
            document.getElementById("_view").style.display="none";
            document.getElementById("_edt").style.display="inline";
        }   
    }
function setElementInfo(elementId) {
	var imgObj = document.getElementById("img_" + elementId);
	if (imgObj != null) {
		document.getElementById("img_" + elementId).src = "images/ico_Modify.gif";
	}
	var elementObj = document.getElementById(elementId);
	if (elementObj != null) {
		elementObj.readOnly = false;
		elementObj.disabled = false;
	}
	var buttonObj = document.getElementById("button_" + elementId);
	if (buttonObj != null) {
		buttonObj.disabled = false;
	}
    //控制红星显示
    var fontObj = document.getElementById("font_" + elementId);
    if (fontObj != null) {
        document.getElementById("font_" + elementId).style.display="inline";
    }
		//复选框和单选框设置
		var allBox=document.all("box_"+elementId);
		if(allBox==null)
		{
			return;
		}
		if(allBox.length==undefined)
		{
			allBox.onclick="";
		}
		else
		{
			for(var i=0;i<allBox.length;i++)
			{
				allBox[i].onclick="";
			}
		}
	}
	
	function setSelectValue(selObj)
	{
		var hiddenSelObj=document.getElementById('hidden_'+selObj.id);
		if(hiddenSelObj!=null)
		{
			hiddenSelObj.value=selObj.value;
		}
	}
	
	
	function AttachFNum0()
	{
		var optionsArray=document.all.AttachCount0.options;
		var tmpInt=optionsArray[document.all.AttachCount0.selectedIndex].text;
	    var tmp = parseInt(tmpInt)-1;
	    for (var i=1; i < tmp+2; i++) 
	    {
	    	attach = eval("document.all.attach0" + i);
	    	attach.style.display = ""; 
	    }
	    for (var j=tmp+2; j <7; j++)
		{
	    	attach = eval("document.all.attach0" + j); 
	        attach.style.display = "none";
	        tmpfile = attach.innerHTML;
	        attach.innerHTML = tmpfile; 
		}
	}
	
	//设置流程管理页面中父窗口的大小
	function setFrame(frameId,height)
	{
		var frameObj=window.parent.document.getElementById(frameId);
		if(frameObj==null)
		{
			return;
		}
		frameObj.height=height;
	}
	function showHiddenInfo(trObj,isopen){
		var tabObj=trObj.parentNode;
		var tdObj=tabObj.childNodes[1];
		if(isopen==undefined)
		{
			tdObj.style.display=(tdObj.style.display=="inline"?"none":"inline");		
		}
		else
		{
			tdObj.style.display=(isopen?"inline":"none");
		}
		var imgObj=trObj.childNodes[1].childNodes[0];
		if(tdObj.style.display=="inline")
		{
			imgObj.src="images/icon_open.gif"
		}
		else
		{
			imgObj.src="images/icon_closed.gif"
		}
	}
	//true为open
	function setFrameCloseOrOpen(frameId,isOpen)
	{
		var frameObj=window.parent.document.getElementById(frameId);
		if(frameObj==null)
		{
			return;
		}
		
		showHiddenInfo(window.parent.document.getElementById(frameId),isOpen);
		//frameObj.parentNode.parentNode.style.display=isOpen?"inline":"none";
	}
	
	var currentOperation="";
	//流程使用：传入选择的操作步骤的对象，根据传入的对象操作处理人是否显示。
	function clickopration(rdobj,oprationTr)
	{
       	var baseLineObj=document.getElementById("saveBaseLine");
		currentOperation=rdobj;
        //处理步骤为返回，撤销，授权给他人时 保存按钮不可见
        if (rdobj.value=="DELETE"||rdobj.value=="AUTHORIZATION"||rdobj.value=="BACK")
        {
        	if(baseLineObj!=null)
        	{
        		baseLineObj.style.display="none";
        	}
            document.getElementById(oprationTr).style.display="none";
			document.getElementById("nextStep").value=rdobj.value;
            if (rdobj.value=="AUTHORIZATION" )
            {
            	var stepObj=document.getElementById("currentStepId");
				document.getElementById("nextStep").value=(stepObj==null?"":stepObj.value);
                document.getElementById(oprationTr).style.display="inline";
            }
        }
        else if(rdobj.value!="")
        {
        	if(baseLineObj!=null)
        	{
        		baseLineObj.style.display="inline";
        	}
			document.getElementById("nextStep").value=rdobj.value;
            document.getElementById(oprationTr).style.display="inline";
        }
        else
        {
        	if(baseLineObj!=null)
        	{
        		baseLineObj.style.display="inline";
        	}
			var operObj=document.getElementById("TROper");
			operObj.style.display="none";
        }
        document.getElementById("Toperator").value="";
        document.getElementById("hidden_Toperator").value="";
	}
	
	//用于判断选中的环节是否为撤消、授权其它人、返回上一层。如果是正常流程，返回true，如果是非正常流程，返回false；
	function isNaturalFlow(radioObj)
	{
        //处理步骤为返回，撤销，授权给他人时 保存按钮不可见
        if (radioObj.value=="DELETE"||radioObj.value=="AUTHORIZATION"||radioObj.value=="BACK")
        {
        	return false;
        }
        else
        {
        	return true;
        }
		
	}
	
	function doExecNoNaturalFlow(operation,flowOid,type,infoId,stepId,operator,path)
	{
		if(window.event!=null)
		{
			window.event.srcElement.disabled=true;
		}
		var url=path+"/flowManager.do?method="+operation+"&flowOid="+flowOid+"&type="+type+"&infoId="+infoId+"&stepId="+stepId+"&dutyOperator="+operator;
        window.document.location=url;
	}
    var eventObj=null;
	//删除文件时使用
	function doDelFile(fileOid,absoluteName)
	{
        eventObj=window.event.srcElement;
		FlowManagerDwr.delFile(doReturn,fileOid,absoluteName);
	}
	function doReturn(data)
	{
        eventObj.style.display="none";
        eventObj.previousSibling.style.display="none";
	}
	
	var baseInfoIsSave=false;
	
	var operationFrameName="";
	//从环节页面调用基本信息页面的保存按钮。
	function doSubmitFromStep()
	{
		return true;
		if(window.parent.baseinfoFrame.isRequireEdit)
		{
			setFrameCloseOrOpen("baseinfoTr",true);
			var returnFlag=window.parent.baseinfoFrame.onSubmit();
		}
		return (returnFlag==undefined)?true:false;
	}
	
//	function jumpOut()
//	{
//		if(!baseInfoIsSave)
//		{
//			return jumpOut();
//		}
//		else
//		{
//			return true;
//		}
//	}
	/**add by wangjian 用于设置基本信息可编辑元素的方法 end*/
	
    /*add by zhaiqiang 用于判断选择处理步骤的返回值 没有选择处理步骤时返回""值*/
    function selectStepValue()
    {
        var objs = document.all.stepProcess;
        if (objs==null)
        {
            return "";
        }
        if (objs.length==null)
        {
            if (objs.checked)
            {
               return objs.value;
            }
            else
            {
                return "";  
            }
        }
        else
        {
            for (i=0; i<objs.length; ++i)
            {
                if (objs[i].checked)
                {
                    return objs[i].value;
                }
            }
        }
        return "";
    }   
    
    /*add by zhaiqiang 用于判断选择处理步骤的返回值 没有选择处理步骤时返回""值*/
     function showStepInfoStyle()
     {
        //处理步骤为返回，撤销，授权给他人时 保存按钮不可见
        if (selectStepValue()=="DELETE" 
                || selectStepValue()=="AUTHORIZATION"  
                ||selectStepValue()=="BACK")
        {
            document.getElementById("btn_save").style.display="none";
            document.getElementById("tr_dutyOperator").style.display="none";
            if (selectStepValue()=="AUTHORIZATION" )
            {
                document.getElementById("tr_dutyOperator").style.display="inline";    
            }
        }
        else
        {
             document.getElementById("btn_save").style.display="inline";
             document.getElementById("tr_dutyOperator").style.display="inline";
        }
        //按钮切换时清空处理人
        document.getElementById("hidden_dutyOperator").value="";
        document.getElementById("dutyOperator").value="";
     }
    
     /*add by zhaiqiang 用于显示当前环节选择的操作步骤*/
    function showStepValue(objId)
    {
        if (document.getElementById(objId)!=null)
        {
            document.getElementById(objId).checked = true;    
        }
        var objs = document.all.stepProcess;
        var selectStepValue = document.getElementById("nextStepId").value;
        if (objs==null)
        {
            return ;
        }
        if (objs.length==null)
        {
            if (selectStepValue == objs.value)
            {
               objs.checked=true;
            }
        }
        else
        {
            for (i=0; i<objs.length; ++i)
            {
                if (selectStepValue == objs[i].value)
                {
                    objs[i].checked = true;
                }
            }
        }
    }  
    //根据对象名称获取选中的模块字符串并用逗号连起来，checkbox    
function modelCheckedBuffer(ObjName) {
	var buffer = "";
	var objs = document.getElementsByName(ObjName);
	for (var i = 0; i < objs.length; i++) {
		if (objs[i].checked) {
			if (buffer != "") {
				buffer += ",";
			}
			buffer += objs[i].value;
		}
	}
	return buffer;
}  
//根据对象获取选中的模块字符串并用逗号连接起来，下拉框多选
function optionsSelectedBuffer(ObjName) {
	var buffer = "";
	var objs = document.getElementById(ObjName);
	for (var i = 0; i < objs.length; i++) {
		if (objs.options[i].selected) {
			if (buffer != "") {
				buffer += ",";
			}
			buffer += objs.options[i].value;
		}
	}
	return buffer;
}
//传一个String，选中上次查询的checkbox条件
function boxChecking(moduleBuffer, ObjName) {
	var model = moduleBuffer.split(",");
	var objs = document.getElementsByName(ObjName);
	for (var i = 0; i < objs.length; i++) {
		for (var j = 0; j < model.length; j++) {
			if (objs[i].value == model[j]) {
				objs[i].checked = true;
			}
		}
	}
}