package com.highguard.Wisdom.struts.actions.appApi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.logging.WisdomLogger;
import com.highguard.Wisdom.mgmt.hibernate.beans.Recharge;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.RechargeManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.dto.UserInformation;
import com.highguard.Wisdom.struts.service.WalletService;
import com.highguard.Wisdom.thirdParty.wx.WXPay;
import com.highguard.Wisdom.thirdParty.wx.common.Signature;
import com.highguard.Wisdom.util.CodeUtil;
import com.highguard.Wisdom.util.JsonUtil;
import com.highguard.Wisdom.util.StringUtil;

/**
 * 充值或消费操作接口 
 * @author west
 *
 */
@Controller
@Scope("prototype")
public class WalletApiAction extends ApiBaseAction{
	
	private static Logger logger = Logger.getLogger(WalletApiAction.class);
	@Resource
	WalletService walletService;
	@Resource
	UserManager userManager;
	@Resource
	RechargeManager rechargeManager;
	private String userId;//主用户ID
	private String phone;//充值号码
	private String price;//充值金额 单位为元
	private String orderNo;
	
	
	/**
	 * @param 
	 * 	userId
	 * 	price 
	 */
	public void chongZhi(){//充值接口
		if(StringUtil.isEmpty(userId)|| StringUtil.isEmpty(price)){
			writeToFrontPage(JsonUtil.JsonMsg(false, "", "参数不全"));
		}
		Double priceYuan = -1d;
		try {
			priceYuan=Double.valueOf(price);
		} catch (Exception e) {
			writeToFrontPage(JsonUtil.JsonMsg(false, "", "金额必须为数字"));
			return;
		}
		priceYuan=priceYuan*100;
		int pricefen =priceYuan.intValue();
		
		
		UserInformation userInformation = getUserInfomation(Integer.parseInt(userId));
		// TODO 给用户充值 并保存充值记录，应该写到service层，并保证在同一事务内操作
		User user = userInformation.getUserMaster();
		if(user!=null){
			String czTime = user.getCzTime();
			if(czTime!=null&&!"".equals(czTime)){
				long time = Long.parseLong(czTime)/1000;
				long now = (new Date().getTime())/1000;
				
				if(now-time<=30){
					 JSONObject jsonMsg = JsonUtil.JsonMsg(false, "30秒内不许连续充值", "");
					 WisdomLogger.logInfo(OpenDoorACtion.class, "czTime");
						jsonMsg.put("errCode", "700"); 
							writeToFrontPage(jsonMsg);
							 WisdomLogger.logInfo("openDoor","czTime");
						return ;
				}
				 user.setCzTime(new Date().getTime()+"");
				 userManager.updateUser(user);
			}
		}
		WXPay wxpay=new WXPay();
		String orderNo=CodeUtil.getCzOrderNo("WX");
		Map<String, Object> unifiedOrder = wxpay.unifiedOrder(orderNo, "充值",pricefen,userInformation.getUserId()+";"+userInformation.getUserType()+";"+orderNo);
		
		if(unifiedOrder.get("return_code").equals("SUCCESS")){
			JSONObject msg=JsonUtil.JsonMsg(true, "", "SUCCESS");
			msg.putAll(unifiedOrder);
			msg.putAll(getSignStrForApp(unifiedOrder));
			msg.put("sign",getSignStrForApp(unifiedOrder).get("sign"));
			//保存充值记录
			Recharge recharge  = new Recharge();
			recharge.setCreatetime(new Date());
			recharge.setPrice((pricefen/100)+"");
			recharge.setType("1");//充值类型 为手机充值 
			recharge.setPhone(userInformation.getPhoneNumber());//充值号码
			recharge.setUserid(userInformation.getUserId());
			recharge.setUsername(userInformation.getPhoneNumber());
			recharge.setUuid(userInformation.getUserMaster().getId()+"");
			recharge.setOutOrderNumber("");
			recharge.setOrderNumber(orderNo);
			rechargeManager.saveRecharge(recharge);//新增充值记录
			writeToFrontPage(msg);
		}else{
			JSONObject msg=JsonUtil.JsonMsg(false, "", "error");
			msg.put("msgCode", unifiedOrder.get("return_code"));
			msg.put("msg", unifiedOrder.get("return_msg"));
			logger.info("微信预统一下单失败："+msg);
			writeToFrontPage(msg);
		}
	}
	
	private Map<String, Object> getSignStrForApp(Map<String, Object> param){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("appid", param.get("appid"));
		map.put("partnerid", param.get("mch_id"));
		map.put("prepayid", param.get("prepay_id"));
		map.put("noncestr", param.get("nonce_str"));
		map.put("timestamp", System.currentTimeMillis()/1000);
		map.put("package", "Sign=WXPay");
		String sign = Signature.getSign(map).toLowerCase();
		map.put("sign", sign);
		
		return map;
	}
	
	public void queryAndUpdateOrderState(){
//		CheckOrderState service=new CheckOrderState(orderNo);
//		service.run();
//		writeToFrontPage(JsonUtil.JsonMsg(true, "", "查询已开始！"));
	}
	
	
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
