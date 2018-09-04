package com.highguard.Wisdom.struts.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.dao.SmsRecordDao;
import com.highguard.Wisdom.mgmt.dao.SystemUserDao;
import com.highguard.Wisdom.mgmt.dao.UserDao;
import com.highguard.Wisdom.mgmt.dao.UserInfoDao;
import com.highguard.Wisdom.mgmt.hibernate.beans.SmsRecord;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.hibernate.beans.UserInfo;
import com.highguard.Wisdom.mgmt.manager.UserInfoManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.constant.SmsTemplate;
import com.highguard.Wisdom.struts.dto.UserInformation;
import com.highguard.Wisdom.struts.service.ValidationService;
import com.highguard.Wisdom.util.JsonUtil;
import com.highguard.Wisdom.util.Sendsms;

import net.sf.json.JSONObject;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class ValidationServiceImpl implements ValidationService{
	@Resource
	UserDao userDao;
	@Resource
	SystemUserDao systemUserDao;
	@Resource
	UserInfoDao userInfoDao;
	@Resource
	SmsRecordDao smsDao;
	
	@Resource
	UserInfoManager userInfoManager;
	@Resource
	UserManager userManager;
	
	@Override
	public JSONObject sendMobileVerifyCode(String mobileNumber,SmsTemplate template,String checkCode) {
		JSONObject o = checkBeforeSendSmsCode(mobileNumber);
		if(o.getBoolean("state")){
			String content = getSmsTemplate(template, checkCode);
			boolean sendSms = Sendsms.sendSms(mobileNumber, content);
			if(sendSms){
				o=JsonUtil.JsonMsg(true, "", "发送成功");
			}else{
				o=JsonUtil.JsonMsg(true, "", "发送失败");
			}
			o.put("content", content);
		}
		return o;
	}

	private String getSmsTemplate(SmsTemplate template,String number) {
		String content="";
		if(SmsTemplate.注册验证.equals(template)){
			content="您的验证码是：" + number +"。请不要把验证码泄露给其他人。如非本人操作，可不用理会！";
		}else if(SmsTemplate.登陆验证.equals(template)){
			content="您的验证码是：" + number + "。请不要把验证码泄露给其他人。如非本人操作，可不用理会！";
		}
		return content;
	}
	/**
	 * 校验是否可以发送验证码
	 * 1：重复发送
	 * 2：黑名单客户
	 * @return
	 */
	private JSONObject checkBeforeSendSmsCode(String phoneNumber) {
		//TODO 验证是否可以发送验证码
		//code
		return JsonUtil.JsonMsg(true, "", "符合验证码发送规则");
	}

	@Override
	public UserInformation getUserInformationByPhoneNumber(String phoneNumber) {
		//主账号
		User user = userDao.getUserByPhone(phoneNumber);
		//亲情账号（副账号）
		UserInfo viceUser = userInfoDao.getUserInfoByPhone(phoneNumber);
		
		UserInformation userinfoDto = null;
		if(null != user){
			//主账号
			List<UserInfo> userInfoList = userInfoDao.getUserInfoListByUserId(user.getId()+"");
			userinfoDto=new UserInformation(user.getId(), 0, user, userInfoList);
		}else if(null != viceUser){
			//亲情账号
			//TODO 将数据set到UserInformation中
			List<UserInfo> userInfoList =new ArrayList<UserInfo>();
			userInfoList.add(viceUser);
			userinfoDto=new UserInformation(viceUser.getId(), 1, userDao.getUserById(viceUser.getUserId()), userInfoList);
		}
		return userinfoDto;
	}
	
	@Override
	public UserInformation getUserInformationById(String userType,int id) {
		UserInformation userinfoDto = null;
		User user =null;
		int usertype=0; 
		if(userType!=null&&userType.equals("0")){
			user = userManager.getUserById(id);	
			 usertype=0; 
		}else if(userType!=null&&userType.equals("1")){
			UserInfo ziUser = userInfoManager.getUserInfoById(id);
			user = userManager.getUserById(ziUser.getUserId());	
			 usertype=1; 
		}
//		if(null==user){
//			UserInfo ziUser = userInfoManager.getUserInfoById(id);
//			if(null != ziUser){
//				//亲情账号
//				//TODO 将数据set到UserInformation中
//				List<UserInfo> userInfoList =new ArrayList<UserInfo>();
//				userInfoList.add(ziUser);
//				userinfoDto=new UserInformation(ziUser.getId(), 1, userDao.getUserById(ziUser.getUserId()), userInfoList);
//			}
//		}else{
			//主账号
			List<UserInfo> userInfoList = userInfoDao.getUserInfoListByUserId(user.getId()+"");
			userinfoDto=new UserInformation(id, usertype, user, userInfoList);
		//}
		return userinfoDto;
	}


	@Override
	public void saveSmsSendRecord(SmsRecord smsRecord) {
		smsDao.save(smsRecord);
	}

	@Override
	public SmsRecord getSmsRecordByPhoneAndYzm(String phone, String yzm) {
		return smsDao.getSmsRecordByPhoneAndYzm(phone,yzm);
	}

	@Override
	public void saveUserLoginToken(Integer userId, Integer userType,
			String md5Encode) {
		if(userType==0){
			//主用户
			User user = userDao.getUserById(userId);
			user.setLoginToken(md5Encode);
			userDao.updateUser(user);
		}else if(userType==1){
			//子客户
			UserInfo userInfo = userInfoDao.getUserInfoById(userId);
			userInfo.setLoginToken(md5Encode);
			userInfoDao.updateUserInfo(userInfo);
		}else{
		}
		
	}
	
	
	@Override
	public Map<String,Integer> getUserIdByToken(String md5Encode)  {
		 Map<String,Integer>  map = new HashMap<String, Integer>();
		int id=-1;
		int usertype = -1;
		
		User user = userDao.getUserByToken(md5Encode);
		if(null!=user){
			id= user.getId();
			usertype = 0;
		}else{
			UserInfo userInfo = userInfoDao.getUserInfoByToken(md5Encode);
			if(null!=userInfo){
				id = userInfo.getId();
				usertype = 1;
			}
		}
		map.put("id", id);
		map.put("usertype", usertype);
		return map;
	}

	
	
	

}
