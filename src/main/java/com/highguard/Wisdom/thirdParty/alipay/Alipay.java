package com.highguard.Wisdom.thirdParty.alipay;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.highguard.Wisdom.struts.dto.UserInformation;
import com.highguard.Wisdom.thirdParty.alipay.config.AlipayConfig;
import com.highguard.Wisdom.thirdParty.alipay.sign.RSA;
import com.highguard.Wisdom.thirdParty.alipay.util.AlipayCore;
import com.highguard.Wisdom.util.CodeUtil;
import com.highguard.Wisdom.util.JsonUtil;

public class Alipay {

	public static void createOrder(){
		//userId
		// totalFee
		String totalFee="0.01"; //单位元
		String userId="1";
		
		UserInformation userInfomation = new UserInformation(); //getUserInfomation(Integer.parseInt(userId));
		userInfomation.setUserId(1);
		userInfomation.setUserType(1);
		String out_trade_no=CodeUtil.getCzOrderNo("ZFB");
		
//		String[] parameters={
//				"service=mobile.securitypay.pay",
//			    "partner="+AlipayConfig.partner,
//			    "_input_charset="+AlipayConfig.input_charset,
//			    "notify_url="+AlipayConfig.notify_url,
//			    "out_trade_no="+out_trade_no,
//			    "subject=充值",
//			    "payment_type=1",
//			    "seller_id="+AlipayConfig.seller_id,
//			    "total_fee="+totalFee,
//			    "out_context="+userInfomation.getUserId()+";"+userInfomation.getUserType()+";"+out_trade_no
//			};
		String[] parameters={
				"partner="+AlipayConfig.partner,
				"seller_id="+AlipayConfig.seller_id,
				"out_trade_no=ZFBCZ-X65-160406152513641",
				"subject=充值",
				"total_fee=100.00",
				"notify_url="+AlipayConfig.notify_url,
				"service=mobile.securitypay.pay",
				"payment_type=1",
				"_input_charset="+AlipayConfig.input_charset,
				"it_b_pay=30m",
				"show_url=m.alipay.com"
		};
		
		
//		partner="2088811380368002"
//		&seller_id="wanglq1988@aliyun.com"
//		&out_trade_no="ZFBCZ-X65-160406152513641"
//		&subject="充值"
//		&total_fee="100.00"
//		&notify_url="http://112.74.106.17:8080/Market/wisdomApi/aliPayNotify.action"
//		&service="mobile.securitypay.pay"
//		&payment_type="1"
//		&_input_charset="utf-8"
//		&it_b_pay="30m"
//		&show_url="m.alipay.com"
		
		
		//转换成Map
		Map<String, String> params =new HashMap<String, String>();
		for (int i = 0; i < parameters.length; i++) {
			String[] split = parameters[i].split("=");
			params.put(split[0], split[1]);
		}
		
		
		
		String linkString = AlipayCore.createLinkString(AlipayCore.paraFilter(params));
		String sign = RSA.sign(linkString, AlipayConfig.private_key, AlipayConfig.input_charset);
		params.put("sign", sign);
		params.put("sign_type", "RSA");
		JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "发起支付宝支付参数获取成功");
		jsonMsg.put("data", JSONObject.fromObject(params));
		
	}
		
	/**
	 * 查询订单状态
	 * @param orderNo 系统内部订单号
	 * @return
	 */
	public static Map<String, Object> queryOrderState(String orderNo){
		
		
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
				AlipayConfig.app_id,AlipayConfig.private_key,"json","UTF-8",AlipayConfig.ali_public_key);
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		JSONObject json=new JSONObject();
		json.put("out_trade_no", orderNo);
		request.setBizContent(json.toString());
		try {
			AlipayTradeQueryResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				String bizContent = request.getBizContent();
			}
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	public static void main(String[] args) {
		Alipay.queryOrderState("sss");
//		Alipay.createOrder();
		
	}
		
}
