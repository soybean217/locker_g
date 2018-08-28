package com.highguard.Wisdom.struts.service.impl;

import java.text.DecimalFormat;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
//import org.comet4j.core.util.JSONUtil;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.hibernate.beans.Recharge;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.RechargeManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.actions.appApi.AlipayNotifyAction;
import com.highguard.Wisdom.struts.service.WalletService;
import com.highguard.Wisdom.thirdParty.wx.WXPay;
import com.highguard.Wisdom.util.JsonUtil;
@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class WalletServiceImpl implements WalletService{
	
	private static Logger logger = Logger.getLogger(AlipayNotifyAction.class);
	@Resource
	SessionFactory factory;
	@Resource
	RechargeManager rechargeManager;
	@Resource
	UserManager userManager;
	@Override
	public int savePayRecord() {
		// TODO Auto-generated method stub
		return 0;
	}
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOrderState(String orderNo) {
		String msg="";
		String code="";
		Recharge recharge1 = rechargeManager.getByOrderNumber(orderNo);
		if(null==recharge1){
			msg="订单不存在！";
			code="null";
		}else if(!recharge1.getOutOrderNumber().equals("")){
			msg="订单已完成！";
			code="over";
		}else if(recharge1.getOutOrderNumber().equals("")){
			msg="订单未完成！";
			code="doing";
		}
		return JsonUtil.JsonMsg(true, code, msg);
	}
	@Override
	public void updateOrderState(String orderNo,int count) {
		if(count<=20){
			Map<String, Object> orderState = getOrderState(orderNo);
			if(orderState.get("msgCode").equals("doing")){
				if(orderNo.indexOf("WX")>=0){
					logger.info("第"+count+"次查询微信订单状态："+orderNo);
					WXPay wxpay=new WXPay();
					Map<String, Object> wxorderInfo = wxpay.queryOrder(orderNo);
					if(wxorderInfo.get("result_code").equals("SUCCESS")){
						if(!wxorderInfo.get("trade_state").equals("SUCCESS")){
							try {
								//一分钟后重新查
								Thread.sleep(1000*60);
								updateOrderState(orderNo,count++);
							} catch (InterruptedException e) {
								
							}
						}else{
							Recharge order = rechargeManager.getByOrderNumber(orderNo);
							if(order.getOutOrderNumber().equals("")){
								
								order.setOutOrderNumber(wxorderInfo.get("transaction_id").toString());
								rechargeManager.updateRecharge(order);
								//更新用户余额
								Double total_fee = Double.parseDouble(wxorderInfo.get("total_fee").toString());
								DecimalFormat    df   = new DecimalFormat("######0.00");
								User userMaster = userManager.getUserById(order.getUserid());
								String balance = userMaster.getBalance();
								double newbalance = null==balance?0d:Double.parseDouble(balance);
								newbalance+=(total_fee/100);
								userMaster.setBalance(df.format(newbalance));
								userManager.saveUser(userMaster);
								logger.info("第"+count+"次查询支付宝订单["+orderNo+"]状态完成，订单状态发生改变，最新用户余额为"+newbalance+"元");
							}
						}
						
					}
					//微信
				}else if(orderNo.indexOf("ZFB")>=0){
					logger.info("第"+count+"次查询支付宝订单状态："+orderNo);
					//支付宝
				}
			}
		}
	}
	public static void main(String[] args) {
		WalletServiceImpl s=new WalletServiceImpl();
		s.updateOrderState("WXCZ-X65-160406195719257",1);
	}

}
