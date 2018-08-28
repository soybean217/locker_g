package com.highguard.Wisdom.mgmt.hibernate.beans;

import java.sql.Blob;
import java.util.Date;

/**
 * 充值信息
 * 
 * @author Administrator
 * 
 */
public class Recharge {

	private Integer id;
	private String price;
	private String comment;
	private String username;
	private Integer userid;
	private Date createtime;
	private String uuid;
	private String type;//充值类型 为null 或者为空 则是网站上添加  ；1 为手机充值 
	private String phone;//充值号码 只有手机充值才有
	private String orderNumber;  //订单号
	private String outOrderNumber; //外部订单号
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getUserid() {
		return userid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOutOrderNumber() {
		return outOrderNumber;
	}
	public void setOutOrderNumber(String outOrderNumber) {
		this.outOrderNumber = outOrderNumber;
	}
	
}
