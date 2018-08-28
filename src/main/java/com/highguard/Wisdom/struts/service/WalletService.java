package com.highguard.Wisdom.struts.service;

import java.util.Map;

public interface WalletService {

	//保存消费流水
	public int savePayRecord();
//	
//	//查询订单状态
//	public Map<String, Object> getOrderState(String orderNo);
	
	
	//查询第三方支付平台，并更新订单状态
	public void updateOrderState(String orderNo,int count);
		
}
