package com.highguard.Wisdom.struts.actions.appApi;

import java.text.DecimalFormat;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.Recharge;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.RechargeManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.dto.UserInformation;
import com.highguard.Wisdom.thirdParty.wx.common.Signature;
import com.highguard.Wisdom.thirdParty.wx.common.XMLParser;

import net.sf.json.JSONObject;


/**
 * 微信相关回调接口
 * @author west
 *
 */
@Controller
@Scope("prototype")
public class WxNotify extends ApiBaseAction{
	@Resource
	RechargeManager rechargeManager;
	@Resource
	UserManager userManager;
	/**
	 * 接受微信的支付通知
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public void wxPayNotify(){
		JSONObject json = new JSONObject();
		try {
			ServletInputStream inputStream = getRequest().getInputStream();
			Map<String, Object> parameters = XMLParser.getXmlStrFromInputStream(inputStream);

			//校验微信签名，在官网文档里没找到校验方式
			if(Signature.validateSign(parameters)){
				if(parameters.get("return_code").equals("SUCCESS")){
					//获取内部订单号进行业务处理
					String out_trade_no = parameters.get("out_trade_no").toString();
					String transaction_id = parameters.get("transaction_id").toString();
					//金额（单位为 分）
					Double total_fee = Double.parseDouble(parameters.get("total_fee").toString());
					//处理业务逻辑的代码
					//code
					//判断该订单是否已经处理过了
					synchronized (this) {
						Recharge recharge1 = rechargeManager.getByOrderNumber(out_trade_no);
						//第三方订单号为空的表示充值没成功，需要处理的。
						if(null!=recharge1 && recharge1.getOutOrderNumber().equals("")){
							String[] attaches = parameters.get("attach").toString().split(";");
							UserInformation userInfomation = getUserInfomation(Integer.parseInt(attaches[0]));
							//更新充值记录 
							
							recharge1.setOutOrderNumber(transaction_id);
							rechargeManager.updateRecharge(recharge1);
							DecimalFormat    df   = new DecimalFormat("######0.00");
							User userMaster = userInfomation.getUserMaster();
							String balance = userMaster.getBalance();
							//更新用户余额
							double newbalance = null==balance?0d:Double.parseDouble(balance);
							newbalance+=(total_fee/100);
							userMaster.setBalance(df.format(newbalance));
							userManager.updateUser(userMaster);
							json.put("return_code", "SUCCESS");
						}else{
							json.put("return_code", "FAILED");
						}
					}
				}
			}else{
				json.put("return_code", "FAILED");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("return_code", "FAILED");
		}
		
		String xml="<xml>"
				+"<return_code>"+json.get("return_code")+"</return_code>"
  				+"</xml>";
		writeXmlToFrontPage(xml);
		
		
	}
	
	public static void main(String[] args) {
	}
}

