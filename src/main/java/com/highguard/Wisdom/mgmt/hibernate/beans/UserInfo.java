package com.highguard.Wisdom.mgmt.hibernate.beans;

import java.util.Date;

/**
 * 用户关联表
 * 
 * @author Administrator
 *
 */
public class UserInfo {

	private Integer id;
	private Integer userId;// 用户表关联
	private String phone;// 手机号
	private String num;// 验证码
	private String status;// 状态 0：注销 1：已经验证
	private String createtime;// 创建时间
	private String yzTime;//发送验证码的时间
	private String loginToken;

	public String getYzTime() {
		return yzTime;
	}

	public void setYzTime(String yzTime) {
		this.yzTime = yzTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", userId=" + userId + ", phone=" + phone
				+ ", num=" + num + ", status=" + status + ", createtime="
				+ createtime + ", yzTime=" + yzTime + ", loginToken="
				+ loginToken + "]";
	}
	
	
}
