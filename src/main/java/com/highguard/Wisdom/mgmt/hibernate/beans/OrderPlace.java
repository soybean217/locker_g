package com.highguard.Wisdom.mgmt.hibernate.beans;

/**
 * 订单地址 
 * @author huangqm
 *
 */
public class OrderPlace {

	private Integer id;
	private Integer userid;//用户主表ID
	private String phone;//收货人手机号码
	private String username;//收货人姓名
	private String address;//收货人地址
	//private String post;//收货人邮编
	private String comment;//备注 
	private String city;// 省 市 区 
	//private String street;//街道
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
