package com.highguard.Wisdom.mgmt.hibernate.beans;

import java.util.Date;

/**
 *  充值 用户信息
 * 
 * @author Administrator
 * 
 */
public class RechargeUser {

	private Integer id;

	// 姓名
	private String name;


	private String password;
	private Date createtime ;
	private String comment;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	 
}
