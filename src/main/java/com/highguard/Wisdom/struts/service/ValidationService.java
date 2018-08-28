package com.highguard.Wisdom.struts.service;

import java.util.Map;

import com.highguard.Wisdom.mgmt.hibernate.beans.SmsRecord;
import com.highguard.Wisdom.struts.constant.SmsTemplate;
import com.highguard.Wisdom.struts.dto.UserInformation;

import net.sf.json.JSONObject;

public interface ValidationService {
	
	/**
	 * 发送手机验证码
	 * @param mobileNumber
	 * @param content
	 * @return 参见JsonUtil.JsonMsg();
	 */
	public JSONObject sendMobileVerifyCode(String mobileNumber,SmsTemplate template,String number);
	
	/**
	 * 通过手机号查询用户
	 * @return UserInformation
	 */
	public UserInformation getUserInformationByPhoneNumber(String phoneNumber);
	public UserInformation getUserInformationById(String userType,int id);
	
	//保存短信发送记录 
	public void saveSmsSendRecord(SmsRecord smsRecord);

	public SmsRecord getSmsRecordByPhoneAndYzm(String phone, String yzm);

	//保存用户登录token
	public void saveUserLoginToken(Integer userId, Integer userType,
			String md5Encode);
	
	public Map<String,Integer> getUserIdByToken(String token); 
	

}
