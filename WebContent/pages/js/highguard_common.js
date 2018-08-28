/*
 * 描述：跨浏览器的设置 innerHTML 方法
 * 允许插入的 HTML 代码中包含 script 和 style
 * 作者：kenxu <ken@ajaxwing.com>
 * 日期：2006-03-23
 * 参数：
 * el: 合法的 DOM 树中的节点
 * htmlCode: 合法的 HTML 代码
 * 经测试的浏览器：ie5+, firefox1.5+, opera8.5+
 */
var setInnerHTML = function (el, htmlCode) {
 var ua = navigator.userAgent.toLowerCase();
 if (ua.indexOf('msie') >= 0 && ua.indexOf('opera') < 0) {
 htmlCode = '<div style="display:none">for IE</div>' + htmlCode;
 htmlCode = htmlCode.replace(/<script([^>]*)>/gi,
 '<script$1 defer="defer">');
 el.innerHTML = htmlCode;
 el.removeChild(el.firstChild);
 }
 else {
 var el_next = el.nextSibling;
 var el_parent = el.parentNode;
 el_parent.removeChild(el);
 el.innerHTML = htmlCode;
 if (el_next) {
 el_parent.insertBefore(el, el_next)
 } else {
 el_parent.appendChild(el);
 }
 }
}
/*
 * 描述：通过重定义 document.write 函数，避免在使用 setInnerHTML 时，
 * 插入的 HTML 代码中包含 document.write ，导致原页面受到破坏的情况。
 */
document.write = function() {
 var body = document.getElementsByTagName('body')[0];
 for (var i = 0; i < arguments.length; i++) {
 argument = arguments[i];
 if (typeof argument == 'string') {
 var el = body.appendChild(document.createElement('div'));
 setInnerHTML(el, argument)
 }
 }
}


var http_request=false;
function detailurl(url){
		//初始化，指定处理函数，发送请求的函数
		http_request=false;
		//开始初始化XMLHttpRequest对象
		if(window.XMLHttpRequest){
			//Mozilla浏览器
		   http_request=new XMLHttpRequest();
		   if(http_request.overrideMimeType){
			//设置MIME类别
		     http_request.overrideMimeType("text/xml");
		   		}
		}
		else if(window.ActiveXObject){
		//IE浏览器
		   try{
		    http_request=new ActiveXObject("Msxml2.XMLHttp");
		  	 }catch(e){
		    try{
		    http_request=new ActiveXobject("Microsoft.XMLHttp");
		    }catch(e){}
		   }
    	 }
		if(!http_request){
		// 异常，创建对象实例失败
		   window.alert("创建XMLHttp对象失败！");
		   return false;
		}

		http_request.onreadystatechange=processrequest;
		// 确定发送请求方式，URL，及是否同步执行下段代码
		     http_request.open("GET",url,true);
		http_request.send(null);
	 }

   // 处理返回信息的函数

	   function processrequest(){
	    if(http_request.readyState==4){// 判断对象状态
		    if(http_request.status==200){// 信息已成功返回，开始处理信息
		    //detail.innerHTML=unescape(http_request.responseText);
		    setInnerHTML(document.getElementById("detail"),unescape(http_request.responseText));
		   } else{//页面不正常
		document.getElementById("detail").innerHTML=unescape("抱歉！无法获取内容页面！");
		   
			}
	    }
	   }
	function check(){
  		var thisForm = document.forms[0];
  		var flag = false ;
  		var test = document.getElementsByName("pass");
      		if (test.length) 
      		for (i = 0; i < test.length; i++) {
        		if(test[i].checked) 
        		{ flag = true ;}
      		}
      		
      	if(flag == false) {
		alert("您还没有选择 ！");return false ;}
  	}
  	
  	function selectAll(){	 
		var thisForm = document.forms[0];
    	if (!thisForm.pass) {
      	alert("don't have note to select!");
      	return;
    }
    	
    	if (thisForm.pass.length) {
      	for (i = 0; i < thisForm.pass.length; i++) {
        thisForm.pass[i].checked = true;
      }
    } else {
      	thisForm.pass.checked = true;
    }
}
	
	function reverse(){
		var thisForm = document.forms[0];	
    	if (!thisForm.pass) {
      	alert("don't have note to select!");
      	return;
    }
    	if (thisForm.pass.length) {
      		for (i = 0; i < thisForm.pass.length; i++) {
        	thisForm.pass[i].checked = !thisForm.pass[i].checked;
      }
    } else {
      		thisForm.pass.checked = !thisForm.pass.checked;
    }
}