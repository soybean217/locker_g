package com.highguard.Wisdom.struts.actions.appApi;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.tools.ant.types.CommandlineJava.SysProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.Recharge;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.RechargeManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.dto.UserInformation;
import com.highguard.Wisdom.thirdParty.alipay.util.AlipayNotify;
import com.highguard.Wisdom.thirdParty.wx.common.Signature;
import com.highguard.Wisdom.thirdParty.wx.common.XMLParser;
import com.highguard.Wisdom.util.StringUtil;


/**
 * 支付宝支付通知接口
 * @author west
 *
 */
@Controller
@Scope("prototype")
public class AlipayNotifyAction extends ApiBaseAction{
	
	private static Logger logger = Logger.getLogger(AlipayNotifyAction.class);
	@Resource
	RechargeManager rechargeManager;
	@Resource
	UserManager userManager;
	/**
	 * 接受支付宝的支付通知
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public void aliPayNotify(){
		//获取所有参数
		Map<String, String> parameters = getParameters();
		logger.info("支付宝通知参数："+parameters.toString());
		
		//是支付宝发送的请求
//		if(AlipayNotify.verify(parameters)){
			//参数说明
			//https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.W4k4m4&treeId=59&articleId=103666&docType=1
			String out_trade_no = parameters.get("out_trade_no");
			String total_fee_str = parameters.get("total_fee"); //单位 元
			double total_fee = Double.parseDouble(total_fee_str);
			String trade_no = parameters.get("trade_no");
			
			String trade_status = parameters.get("trade_status");
			//判断订单是否交易成功
			if(null!=trade_status &&(
					trade_status.equals("TRADE_FINISHED")||
					trade_status.equals("TRADE_SUCCESS"))
					){
				logger.info("收到支付宝付款通知,订单号"+out_trade_no);
				//判断订单是否重复处理
				synchronized (this) {
					Recharge recharge1 = rechargeManager.getByOrderNumber(out_trade_no);
					if(!StringUtil.isEmpty(recharge1) && recharge1.getOutOrderNumber().equals("")){
						int userId=recharge1.getUserid();
//						String[] attaches = parameters.get("out_context").toString().split(";");
						UserInformation userInfomation = getUserInfomation(userId);//(Integer.parseInt(attaches[0]));
						//添加充值记录 
						recharge1.setOutOrderNumber(trade_no);
						rechargeManager.updateRecharge(recharge1);//新增充值记录
						DecimalFormat    df   = new DecimalFormat("######0.00");
						User userMaster = userInfomation.getUserMaster();
						String balance = userMaster.getBalance();
						//更新用户余额
						double newbalance = null==balance?0d:Double.parseDouble(balance);
						newbalance+=(total_fee);
						userMaster.setBalance(df.format(newbalance));
						userManager.updateUser(userMaster);
						System.out.println("更新用户余额:"+df.format(newbalance)+"元");
						writeToFrontPage("success");
					}else{
						logger.info("支付宝付款通知订单号重复"+out_trade_no);
						writeToFrontPage("success");
					}
				}
			}
			//判断状态是否为TRADE_SUCCESS
//		}else{
//			logger.info("支付宝付款通知签名校验失败"+parameters.toString());
//		}
		
	}
	
	public static void main(String[] args) {
	}
}

