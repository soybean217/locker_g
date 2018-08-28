package com.highguard.Wisdom.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Sendsms {
	
	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	
	
	/**
	 * 发送短信验证
	 * @param phone 手机号 
	 * @param num  随机验证码
	 * @return
	 */
	public static boolean sendSms(String phone,String content) {
		
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(Url); 
			
		//client.getParams().setContentCharset("GBK");		
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
		boolean flag = 	true;		
		NameValuePair[] data = {//提交短信
			    new NameValuePair("account", "cf_shwj"), 
			    new NameValuePair("password", "2016chenlei"), //密码可以使用明文密码或使用32位MD5加密
			    //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
			    new NameValuePair("mobile", phone), 
			    new NameValuePair("content", content),
		};
		
		method.setRequestBody(data);		
		try {
			client.executeMethod(method);	
			
			String SubmitResult =method.getResponseBodyAsString();
			Document doc = DocumentHelper.parseText(SubmitResult); 
			Element root = doc.getRootElement();
			String code = root.elementText("code");	
			String msg = root.elementText("msg");	
			String smsid = root.elementText("smsid");	
			
			 if("2".equals(code)){
			}else{
				flag = false;
			}
			
		} catch (HttpException e) {
			flag = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			flag = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		}	
		return flag;
		
	}
	
	public static void main(String[] args) {
		boolean sendSms = Sendsms.sendSms("15210551665",  new String("您的验证码是：" + "8848" + "。请不要把验证码泄露给其他人。如非本人操作，可不用理会！") );
		if(sendSms){
		}else{
		}
	}
	
}