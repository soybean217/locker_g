package com.highguard.Wisdom.thirdParty.wx;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.highguard.Wisdom.thirdParty.wx.beans.QueryOrder;
import com.highguard.Wisdom.thirdParty.wx.beans.UnifiedOrder;
import com.highguard.Wisdom.thirdParty.wx.common.ConfigureXian63;
import com.highguard.Wisdom.thirdParty.wx.common.RandomStringGenerator;
import com.highguard.Wisdom.thirdParty.wx.common.Signature;
import com.highguard.Wisdom.thirdParty.wx.service.UnifiedOrderService;


/**
 * SDK总入口
 */
public class WXPay {
	
	/**
	 * 统一下单接口
	 * @param tradeNo
	 * @param body
	 * @param totalFee
	 * @return
	 */
	public static Map<String, Object> unifiedOrder(String tradeNo,String body,int totalFee,String attach){
		UnifiedOrder order = new UnifiedOrder();
		order.setAppid(ConfigureXian63.getAppID()); 
		order.setMch_id(ConfigureXian63.getMchID());
		String nonceStr=RandomStringGenerator.getRandomStringByLength(32);
		order.setNonce_str(nonceStr);
		order.setBody(body);
		order.setAttach(attach);
		order.setOut_trade_no(tradeNo);
		order.setTotal_fee(totalFee);//“分”为单位
		
		order.setNotify_url(ConstantUrl.WX_PAY_NOTIFY_URL); //支付通知回调地址
		order.setTrade_type("APP");  
		
		String sign="";
		try {
			sign=Signature.getSign(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		order.setSign(sign);
		UnifiedOrderService service = new UnifiedOrderService();
		Map<String, Object> rs = service.requestUnifiedOrder(order);
		rs.put("nonce_str", nonceStr);
		return rs;
	}
	
	public Map<String, Object> queryOrder(String orderNo){
		QueryOrder order=new QueryOrder();
		order.setAppid(ConfigureXian63.getAppID());
		order.setMch_id(ConfigureXian63.getMchID());
		String nonceStr=RandomStringGenerator.getRandomStringByLength(32);
		order.setNonce_str(nonceStr);
		order.setOut_trade_no(orderNo);
		String sign="";
		try {
			sign=Signature.getSign(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		order.setSign(sign);
		UnifiedOrderService service = new UnifiedOrderService();
		return service.queryOrder(order);
	}
	
	public static void main(String[] args) {
		Map<String, Object> map = WXPay.unifiedOrder("213333", "test", 1,"");
	}

}
