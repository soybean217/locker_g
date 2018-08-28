package com.highguard.Wisdom.struts.actions.appApi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.Recharge;
import com.highguard.Wisdom.mgmt.manager.RechargeManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.dto.UserInformation;
import com.highguard.Wisdom.thirdParty.alipay.config.AlipayConfig;
import com.highguard.Wisdom.thirdParty.alipay.sign.RSA;
import com.highguard.Wisdom.thirdParty.alipay.util.AlipayCore;
import com.highguard.Wisdom.thirdParty.alipay.util.AlipayNotify;
import com.highguard.Wisdom.util.CodeUtil;
import com.highguard.Wisdom.util.JsonUtil;
import com.highguard.Wisdom.util.StringUtil;


/**
 * 支付宝支付通知接口
 * @author west
 *
 */
@Controller
@Scope("prototype")
public class AlipayAction extends ApiBaseAction{
	
	private static Logger logger = Logger.getLogger(AlipayAction.class);
	@Resource
	RechargeManager rechargeManager;
	@Resource
	UserManager userManager;
	
	public void getPayParams(){
		//userId
		// totalFee
		String totalFee=getRequest().getParameter("price"); //单位元
		String userId=getRequest().getParameter("userId");
		if(StringUtil.isEmpty(userId) || StringUtil.isEmpty(totalFee)){
			writeToFrontPage(JsonUtil.JsonMsg(false, "", "请补齐参数！"));
			return;
		}
		UserInformation userInformation = getUserInfomation(Integer.parseInt(userId));
	
		String out_trade_no=CodeUtil.getCzOrderNo("ZFB");
		
		String[] parameters={
			    "service=mobile.securitypay.pay",
			    "partner="+AlipayConfig.partner,
			    "_input_charset="+AlipayConfig.input_charset,
			    "notify_url="+AlipayConfig.notify_url,
			    "out_trade_no="+out_trade_no,
			    "subject=充值",
			    "payment_type=1",
			    "seller_id="+AlipayConfig.seller_id,
			    "total_fee="+totalFee,
			    "out_context="+userInformation.getUserId()+";"+userInformation.getUserType()+";"+out_trade_no
			};
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
		
		logger.info("发起支付宝支付请求所需参数返回："+jsonMsg);
		//保存充值记录
		Recharge recharge  = new Recharge();
		recharge.setCreatetime(new Date());
		recharge.setPrice(totalFee+"");
		recharge.setType("1");//充值类型 为手机充值 
		recharge.setPhone(userInformation.getPhoneNumber());//充值号码
		recharge.setUserid(userInformation.getUserId());
		recharge.setUsername(userInformation.getPhoneNumber());
		recharge.setUuid(userInformation.getUserMaster().getId()+"");
		recharge.setOutOrderNumber("");
		recharge.setOrderNumber(out_trade_no);
		rechargeManager.saveRecharge(recharge);//新增充值记录
		writeToFrontPage(jsonMsg);
	}
}

