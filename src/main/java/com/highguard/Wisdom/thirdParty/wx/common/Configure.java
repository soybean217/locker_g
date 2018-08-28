//package com.highguard.Wisdom.thirdParty.wx.common;
//
///**
// * 微信支付的各种配置信息
// * 健康云
// * @author west
// */
//public class Configure {
//	
//	//这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
//	// 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
//	// 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改
//	private static String key = "qjbsj6tt493sxlwrv2c4rvt9620ovtju";
//
//	//微信分配的公众号ID（开通公众号之后可以获取到）
//	private static String appID = "wx76a870eb69983d27";
//
//	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
//	private static String mchID = "1311727301";
//	
//	
//
//	public static String getKey() {
//		return key;
//	}
//
//	public static void setKey(String key) {
//		Configure.key = key;
//	}
//
//	public static String getAppID() {
//		return appID;
//	}
//
//	public static void setAppID(String appID) {
//		Configure.appID = appID;
//	}
//
//	public static String getMchID() {
//		return mchID;
//	}
//
//	public static void setMchID(String mchID) {
//		Configure.mchID = mchID;
//	}
//}
