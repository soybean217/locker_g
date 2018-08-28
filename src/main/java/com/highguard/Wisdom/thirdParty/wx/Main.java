package com.highguard.Wisdom.thirdParty.wx;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.highguard.Wisdom.thirdParty.wx.beans.UnifiedOrder;
import com.highguard.Wisdom.thirdParty.wx.common.Signature;
import com.highguard.Wisdom.thirdParty.wx.service.UnifiedOrderService;
import com.highguard.Wisdom.util.MatrixToImageWriter;

public class Main {
	public static void main(String[] args) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {
//		UnifiedOrder order=new UnifiedOrder();
//		order.setAppid(Configure.getAppID()); 
//		order.setMch_id(Configure.getMchID());
//		order.setNonce_str("kncryo1bcvqr3gmitue3gb2jei1wy0sl");//RandomStringGenerator.getRandomStringByLength(32)
//		order.setBody("这个商品是白色的金属质感的骨瓷茶杯");
//		order.setOut_trade_no("CS_DD_10203998");
//		order.setTotal_fee(1);//“分”为单位
//		//order.setSpbill_create_ip("192.168.15.15");  //调用微信支付的终端IP
//		
//		order.setNotify_url("http://jkywxtext.ngrok.natapp.cn/Market/notify/wx_pay_notify.action"); //支付通知回调地址
//		order.setTrade_type("NATIVE");  
//		
//		String sign="";
//		try {
//			sign=Signature.getSign(order);
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		order.setSign(sign);
//		
//		
//		UnifiedOrderService service = new UnifiedOrderService();
//		JSONObject json = service.requestUnifiedOrder(order);
//		json.get("code_url");
//		System.out.println(json.toString());
		
		
//		Main main=new Main();
//		main.qrCode();
		
		Main.ss();
	}
	
	
	public static String  ss(){
		UnifiedOrder order=new UnifiedOrder();
//		order.setAppid(Configure.getAppID()); 
//		order.setMch_id(Configure.getMchID());
		order.setNonce_str("jkj1ckgt2h5gf0dwiwyulmmkpf2y93yu");//RandomStringGenerator.getRandomStringByLength(32)
		order.setBody("这个商品是白色的金属质感的骨瓷茶杯");
		order.setOut_trade_no("CS_DD_102039");
		order.setTotal_fee(1);//“分”为单位
		//order.setSpbill_create_ip("192.168.15.15");  //调用微信支付的终端IP
		
		order.setNotify_url("http://jkywxtext.ngrok.natapp.cn/Market/notify/wx_pay_notify.action"); //支付通知回调地址
		order.setTrade_type("NATIVE");  
		
		String sign="";
		try {
			sign=Signature.getSign(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		order.setSign(sign);
		
		UnifiedOrderService service = new UnifiedOrderService();
		Map json = service.requestUnifiedOrder(order);
		return json.get("code_url").toString();
//		System.out.println(json.toString());
	}
	
	public void qrCode(){
		try {
            
		     String content = "120605181003;http://www.cnblogs.com/jtmjx";
		     String path = "d:";
		     
		     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		     Map hints = new HashMap();
		     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		     BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400,hints);
		     File file1 = new File(path,"餐巾纸.jpg");
		     MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
	}

}
