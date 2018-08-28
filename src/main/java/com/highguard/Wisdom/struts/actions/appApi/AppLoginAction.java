package com.highguard.Wisdom.struts.actions.appApi;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.SmsRecord;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.hibernate.beans.UserInfo;
import com.highguard.Wisdom.mgmt.manager.UserInfoManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.constant.SmsTemplate;
import com.highguard.Wisdom.struts.dto.UserInformation;
import com.highguard.Wisdom.struts.service.ValidationService;
import com.highguard.Wisdom.util.JsonUtil;
import com.highguard.Wisdom.util.NumberUtil;
import com.highguard.Wisdom.util.StringUtil;

/**
 * 用户登录 和 亲情账户action
 * @author west
 *
 */
@Controller
@Scope("prototype")
public class AppLoginAction extends ApiBaseAction{
	@Resource
	UserInfoManager userInfoManager;
	@Resource
	UserManager userManager;
	@Resource
	ValidationService validationService;
	
	
	
	private String phone;
	private String yzm;
	private String userId;
	private String id;
	private String userType;
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * 用token登陆
	 */
	public void autoLogin(){
		String token = getRequest().getParameter("token");
		Map<String,Integer> map = validationService.getUserIdByToken(token);
		int userId = map.get("id");
		int userType = map.get("usertype");
		//userId
		if(userId>0){
			JSONObject jsonMsg = JsonUtil.JsonMsg(true, "login", "登陆成功");
			//UserInformation userDto = validationService.getUserInformationById(userId);
			String newToken=StringUtil.MD5Encode(token);
			getRequest().getSession().setAttribute("token",newToken );
			jsonMsg.put("userId", userId);
			jsonMsg.put("userType", userType);
			jsonMsg.put("token", newToken);
			validationService.saveUserLoginToken(userId,userType,newToken);
			writeToFrontPage(jsonMsg);
		}else{
			writeToFrontPage(JsonUtil.JsonMsg(false, "token_expires", "登陆超时，请重新登陆"));
		}
	}
	
	/**
	 * 登陆
	 * 登陆成功后生成一个token返回到客户端，以后每次的接口都需要验证token是否一致，如不一致，则需要重新登陆
	 */
	public void applogin(){
		JSONObject jsonMsg = new JSONObject();
		if(!StringUtil.isEmpty(phone) && !StringUtil.isEmpty(yzm)){
			//TODO 验证手机号和验证码
			SmsRecord smsRecord = validationService.getSmsRecordByPhoneAndYzm(phone,yzm);
			if(null!=smsRecord){
				UserInformation userDto = validationService.getUserInformationByPhoneNumber(phone);
				Long timePass = System.currentTimeMillis()-smsRecord.getCreateTime().getTime();
				long time=1000*60*60*24*7l;
				if(timePass<time){
					//短信发送时间是1周的，此时验证码有效，验证通过
					jsonMsg = JsonUtil.JsonMsg(true, "", "登陆成功！");	
					jsonMsg.put("token", StringUtil.MD5Encode(phone+yzm));
					jsonMsg.put("userId", userDto.getUserId());
					jsonMsg.put("userType", userDto.getUserType());
					validationService.saveUserLoginToken(userDto.getUserId(),userDto.getUserType(),StringUtil.MD5Encode(phone+yzm));
					getRequest().getSession().setAttribute("token", StringUtil.MD5Encode(phone+yzm));
				}else{
					jsonMsg=JsonUtil.JsonMsg(false, "", "验证失败,验证码超时！");
				}
				
			}else{
				jsonMsg=JsonUtil.JsonMsg(false, "", "验证失败,无效的验证码！");
			}
		}else{
			jsonMsg = JsonUtil.JsonMsg(false, "", "请填写正确的手机号和验证码！");
		}
		writeToFrontPage(jsonMsg);
	}
	
	
	public void logout(){
		String token = getRequest().getParameter("token");
		if(StringUtil.isEmpty(token)){
			writeToFrontPage(JsonUtil.JsonMsg(false, "", "缺少参数"));
			return;
		}
		Map<String,Integer> map = validationService.getUserIdByToken(token);
		int userId = map.get("id");
		int type = map.get("usertype");
		if(userId<=0){
			writeToFrontPage(JsonUtil.JsonMsg(true, "arealdy logout", "已处于登出状态"));
			return;
		}
		UserInformation userDto = validationService.getUserInformationById(type+"",userId);
		
		if(userDto.getUserType()==0){
			//主账号
			User user = userManager.getUserById(userDto.getUserId());
			user.setLoginToken("");
			userManager.updateUser(user);
		}else{
			UserInfo userInfo = userInfoManager.getUserInfoById(userDto.getUserId());
			userInfo.setLoginToken("");
			userInfoManager.updateUserInfo(userInfo);
		}
		getRequest().getSession().invalidate();
		writeToFrontPage(JsonUtil.JsonMsg(true, "", "您已安全退出"));
	}

	/**
	 * 发送验证码
	 */
	public void getSecurityCode(){
		JSONObject jsonMsg = JsonUtil.JsonMsg(false, "", "");	
		if(null!= phone && !phone.equals("")){
			
			UserInformation userDto = validationService.getUserInformationByPhoneNumber(phone);
			
			String num = NumberUtil.getRandomNumbers(6);
			if(null!=userDto){
				jsonMsg = validationService.sendMobileVerifyCode(phone, SmsTemplate.登陆验证, num);
			}else{
				//不存在的用户，直接创建一个用户
				User user = new User();
			 	user.setUuid(System.currentTimeMillis()+"");
			 	//TODO 余额字段类型问题
				user.setBalance("0");
				user.setConsumption("0");
				user.setTelephone(phone);
				user.setName(phone);
				user.setType("2");//普通用户
				user.setCard(user.getUuid());
				userManager.saveUser(user);
				userDto=new UserInformation(user.getId(), 0, user, null);
				jsonMsg = validationService.sendMobileVerifyCode(phone, SmsTemplate.注册验证, num);
				
			}
			//短信发送成功，保存发送记录
			if(jsonMsg.getBoolean("state")){
				SmsRecord smsRecord=new SmsRecord();
				smsRecord.setContent(jsonMsg.getString("content").toString());
				smsRecord.setCreateTime(new Date());
				smsRecord.setUserId(userDto.getUserId());
				smsRecord.setReceiverNumber(phone);
				smsRecord.setYzmCode(num);
				validationService.saveSmsSendRecord(smsRecord);
			}
		}else{
			jsonMsg=JsonUtil.JsonMsg(false, "", "电话号码不能为空！");
		}
		writeToFrontPage(jsonMsg);
	}
	
	/**
	 * 未登录
	 */
	public void noLogin(){
		JSONObject json=JsonUtil.JsonMsg(false, "noLogin", "请登录！");
		writeToFrontPage(json);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getYzm() {
		return yzm;
	}

	public void setYzm(String yzm) {
		this.yzm = yzm;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
