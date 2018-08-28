package com.highguard.Wisdom.mgmt.hibernate.beans;

import java.util.Date;

public class SmsRecord {

	private Integer id;
	private Integer userId; //接受短信的用户Id
	private String receiverNumber; //接受短信的用户电话号码
	private String content; //短信内容
	private String yzmCode;
	private Date createTime; //创建时间
	
	
	
	
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
	public String getReceiverNumber() {
		return receiverNumber;
	}
	public void setReceiverNumber(String receiverNumber) {
		this.receiverNumber = receiverNumber;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getYzmCode() {
		return yzmCode;
	}
	public void setYzmCode(String yzmCode) {
		this.yzmCode = yzmCode;
	}
	
}
