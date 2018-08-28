/* 用途：校验ip地址的格式
 * 输入：strIP：ip地址
 * 返回：如果通过验证返回true,否则返回false；
*/
function isIP(strIP) {
	if (isNull(strIP)) {
		return false;
	}
	var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g; // 匹配IP地址的正则表达式
	if(re.test(strIP)) {		
		if(RegExp.$1 <256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256) {
			return true;
		}
	}
	return false;
}
/*
 * 用途：检查输入字符串是否为空或者全部都是空格 输入：str 返回： 如果全是空返回true,否则返回false
 */
function isNull(str) {
	if (str == "") {
		return true;
	}
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
}

/*
 * 用途：检查输入对象的值是否符合整数格式 输入：str 输入的字符串 返回：如果通过验证返回true,否则返回false
 */
function isInteger(str) {
	var regu = /^[-]{0,1}[0-9]{1,}$/;
	return regu.test(str);
}
/*
 * 用途：检查输入字符串是否符合正整数格式 输入： s：字符串 返回： 如果通过验证返回true,否则返回false
 */
function isNumber(s){	
	var regu = "^[0-9]+$";
	var re = new RegExp(regu);
	if (s.search(re) != -1) {
		return true;
	} else {
		return false;
	}
}
/*
 * 用途：检查输入对象的值是否符合端口号格式 输入：str 输入的字符串 返回：如果通过验证返回true,否则返回false
 */
function isPort(str){
	return (isNumber(str) && str<65536);
}
/*
 * 用途：检查输入对象的值是否符合E-Mail格式 输入：str 输入的字符串 返回：如果通过验证返回true,否则返回false
 */
function isEmail(str){
	var myReg = /^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;
	if(myReg.test(str)) return true;
	return false;
}
/*
 * 用途：检查输入字符串是否只由英文字母和数字和下划线组成 输入： s：字符串 返回： 如果通过验证返回true,否则返回false
 */
function isNumberOr_Letter(s){// 判断是否是数字或字母
	var regu = "^[0-9a-zA-Z\_]+$";
	var re = new RegExp(regu);
	if (re.test(s)) {
		return true;
	} else {
		return false;
	}
}
/*
 * 用途：检查输入字符串是否只由英文字母和数字组成 输入： s：字符串 返回： 如果通过验证返回true,否则返回false
 */
function isNumberOrLetter(s){// 判断是否是数字或字母
	var regu = "^[0-9a-zA-Z]+$";
	var re = new RegExp(regu);
	if (re.test(s)) {
		return true;
	} else {
		return false;
	}
}
/*
 * 用途：检查输入字符串是否只由汉字、字母、数字组成 输入： value：字符串 返回： 如果通过验证返回true,否则返回false
 */
function isChinaOrNumbOrLett( s ){// 判断是否是汉字、字母、数字组成
	var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
	var re = new RegExp(regu);
	if(re.test(s)) {
		return true;
	} else {
		return false;
	}
}


/*******************************************************************************
 * function:cTrim(sInputString,iType) description:字符串去空格的函数
 * parameters:iType：1=去掉字符串左边的空格 2=去掉字符串左边的空格 0=去掉字符串左边和右边的空格 return
 * value:去掉空格的字符串
 ******************************************************************************/
function cTrim(sInputString,iType) {
	var sTmpStr = ' ';
	var i = -1;
	if(iType == 0 || iType == 1) {
		while(sTmpStr == ' ') {
			++i;
			sTmpStr = sInputString.substr(i,1);
		}
		sInputString = sInputString.substring(i);
	}
	if(iType == 0 || iType == 2) {
		sTmpStr = ' ';
		i = sInputString.length;
		while(sTmpStr == ' ') {
			--i;
			sTmpStr = sInputString.substr(i,1);
		}
		sInputString = sInputString.substring(0,i+1);
	}
	return sInputString;
}
