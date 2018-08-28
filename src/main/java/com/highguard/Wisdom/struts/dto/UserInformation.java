package com.highguard.Wisdom.struts.dto;

import java.util.List;

import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.hibernate.beans.UserInfo;


//用户信息
public class UserInformation {
	
	private Integer userId;   //用户id  userType为0时是user表的主键，userType为1时是userInfo表的主键，
	private Integer userType; //用户类型  0：主账号 1：亲情号
	private User userMaster;  //主账号信息
	private List<UserInfo> viceUsers; //子帐号信息
	private String phoneNumber;
	
	public UserInformation(){}
	
	public UserInformation(Integer userId, Integer userType, User userMaster,
			List<UserInfo> viceUsers) {
		this.userId = userId;
		this.userType = userType;
		this.userMaster = userMaster;
		this.viceUsers = viceUsers;
		if(userType==0){
			this.phoneNumber=userMaster.getTelephone();
		}else{
			if(null==viceUsers){
				this.phoneNumber=userMaster.getTelephone();
			}else{
				this.phoneNumber=viceUsers.get(0).getPhone();
			}
		}
		
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public User getUserMaster() {
		return userMaster;
	}
	public void setUserMaster(User userMaster) {
		this.userMaster = userMaster;
	}
	public List<UserInfo> getViceUsers() {
		return viceUsers;
	}
	public void setViceUsers(List<UserInfo> viceUsers) {
		this.viceUsers = viceUsers;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	

}
