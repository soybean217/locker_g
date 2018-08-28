package com.highguard.Wisdom.thirdParty.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner ="2088811380368002";
	public static String app_id ="2016030801195103";
	// 商户的私钥
	public static String private_key ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOM6vPoSQyUtiE9nCHndMUC5FvUi5k5zNqMb06jm+/Q3hfbqK0SNq+WouqJPhemdpC75kyCLBdPzbezcCA3j6Wzii+VUpBhU0/eFjXfLRJPiST0OhIt3yr45+0Pey08p9aTI/vP6QgdNf72ubkj+9j54b0jSh6OBth35zR3KozuVAgMBAAECgYASg8q6JZrLALbGoYKTS26j83DncHyLj9+30qCgdpG5CH0l7VaxM0n6YJE0gH5M1sd1/Gf8ktdauc8PKa6OUy/FxC1sA2pA1mwJYsb4P1ePLYcHAoRP02egunpXSizr5YJkfRbX/Ds26NUcrkmDY+aY2sx2EVJk3zceX8R/iKhDAQJBAPh2vECITtQbR+a4Gx0EInQsZUTuiSgmM5IldIqnkmAoGZUvO8kV1M1AQssWUtWX4uX9DC9A3KTuo9Fy3XowpQUCQQDqHx9zTgQmKy0WqqVwUoodd7YnxdY4RjBtZJYoyqlN0OZQOVPyHzLh2lmM8j+Juo7lm2x+DYbt2KjPC9rRUgFRAkEAlK7pBG0NTxxsMILnprt8qqyOhWuBJEeIAOLtFLQmgeg9NusnCbRIvfLAhat8MllL7Hk7O2PDJeewnHvyUVaeyQJAKYZrpZ/ozHxL+wE5Zrq9FIzvGg7U+BY68Kwulf2cGdk7gG8qnH88HyMDTySIcH1OaxsuGw17KSSMjGyl2wqYgQJBAMpQc7H7Hbf7NIcmYOdpSLXNfvFf9lT+6RdTd3AzJ7+V9ZtjJE7gR/IyXX1EDe3L5Wm1nAyrgnFoYa1WlnxTNfE=";
	//public static String private_key ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOM6vPoSQyUtiE9nCHndMUC5FvUi5k5zNqMb06jm+/Q3hfbqK0SNq+WouqJPhemdpC75kyCLBdPzbezcCA3j6Wzii+VUpBhU0/eFjXfLRJPiST0OhIt3yr45+0Pey08p9aTI/vP6QgdNf72ubkj+9j54b0jSh6OBth35zR3KozuVAgMBAAECgYASg8q6JZrLALbGoYKTS26j83DncHyLj9+30qCgdpG5CH0l7VaxM0n6YJE0gH5M1sd1/Gf8ktdauc8PKa6OUy/FxC1sA2pA1mwJYsb4P1ePLYcHAoRP02egunpXSizr5YJkfRbX/Ds26NUcrkmDY+aY2sx2EVJk3zceX8R/iKhDAQJBAPh2vECITtQbR+a4Gx0EInQsZUTuiSgmM5IldIqnkmAoGZUvO8kV1M1AQssWUtWX4uX9DC9A3KTuo9Fy3XowpQUCQQDqHx9zTgQmKy0WqqVwUoodd7YnxdY4RjBtZJYoyqlN0OZQOVPyHzLh2lmM8j+Juo7lm2x+DYbt2KjPC9rRUgFRAkEAlK7pBG0NTxxsMILnprt8qqyOhWuBJEeIAOLtFLQmgeg9NusnCbRIvfLAhat8MllL7Hk7O2PDJeewnHvyUVaeyQJAKYZrpZ/ozHxL+wE5Zrq9FIzvGg7U+BY68Kwulf2cGdk7gG8qnH88HyMDTySIcH1OaxsuGw17KSSMjGyl2wqYgQJBAMpQc7H7Hbf7NIcmYOdpSLXNfvFf9lT+6RdTd3AzJ7+V9ZtjJE7gR/IyXX1EDe3L5Wm1nAyrgnFoYa1WlnxTNfE=";
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "C:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "UTF-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";
	
	public static String notify_url="http://42.96.154.169:8080/Market/wisdomApi/aliPayNotify.action";
	public static String seller_id="wanglq1988@aliyun.com";
	
	//查询订单状态地址
	public static String query_url="https://openapi.alipay.com/gateway.do";

}
