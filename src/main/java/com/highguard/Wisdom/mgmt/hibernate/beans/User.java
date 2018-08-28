package com.highguard.Wisdom.mgmt.hibernate.beans;

import java.sql.Blob;
import java.util.Date;
import java.util.Objects;

/**
 * 用户信息
 * 
 * @author Administrator
 * 
 */
public class User {

	private Integer id;

	// 姓名
	private String name;
	// 卡号
	private String card;

	private String avatar;

	// 微信号
	private String wx;

	private String address;

	private String openid;

	// 身份证
	private String sfz;
	// 电话
	private String telephone;
	private String checkbalance;
	// 性别
	private String sex;
	// 身份证照片
	private Blob sfzphoto;
	//其他证照图片
	private Blob otherphoto;
	//账户余额
	private String balance;  //数据库存储长度现在现在在万元，最大金额不能超过99999元  存储是以分为单位的。  
	private String consumption;//消费金额
	private String uuid;
	private String type;//1 超级管理员 2普通用户
	private String num;// 验证码
	private String status;// 状态 0：注销 1：已经验证
	private String yzTime;//发送验证码的时间
	private String loginToken;
	private String smTime;//扫码时间
	private String czTime;//充值时间
	private String lastpaytime; // 最后消费时间
	private String lastlattice; // 最后一次消费的柜子
	private String lastdevice;  // 最后一次消费的小区
    private String outcardnumber; //
 
	public String getOutcardnumber() {
		return outcardnumber;
	}
	public void setOutcardnumber(String outcardnumber) {
		this.outcardnumber = outcardnumber;
	}
	public String getCzTime() {
		return czTime;
	}
	public void setCzTime(String czTime) {
		this.czTime = czTime;
	}
 
	public String getLastpaytime() {
		return lastpaytime;
	}
	public void setLastpaytime(String lastpaytime) {
		this.lastpaytime = lastpaytime;
	}
	public String getLastlattice() {
		return lastlattice;
	}
	public void setLastlattice(String lastlattice) {
		this.lastlattice = lastlattice;
	}
	public String getLastdevice() {
		return lastdevice;
	}
	public void setLastdevice(String lastdevice) {
		this.lastdevice = lastdevice;
	}
	public String getSmTime() {
		return smTime;
	}
	public void setSmTime(String smTime) {
		this.smTime = smTime;
	}
	public String getYzTime() {
		return yzTime;
	}
	public void setYzTime(String yzTime) {
		this.yzTime = yzTime;
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
	private String createtime;
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getConsumption() {
		return consumption;
	}
	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getWx() {
		return wx;
	}
	public void setWx(String wx) {
		this.wx = wx;
	}
	public String getSfz() {
		return sfz;
	}
	public void setSfz(String sfz) {
		this.sfz = sfz;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public Blob getSfzphoto() {
		return sfzphoto;
	}
	public void setSfzphoto(Blob sfzphoto) {
		this.sfzphoto = sfzphoto;
	}
	public Blob getOtherphoto() {
		return otherphoto;
	}
	public void setOtherphoto(Blob otherphoto) {
		this.otherphoto = otherphoto;
	}
	public String getLoginToken() {
		return loginToken;
	}
	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", card=" + card + ", wx=" + wx + ", sfz=" + sfz + ", telephone="
				+ telephone + ", sex=" + sex + ", sfzphoto=" + sfzphoto + ", otherphoto=" + otherphoto + ", balance="
				+ balance + ", consumption=" + consumption + ", uuid=" + uuid + ", type=" + type + ", num=" + num
				+ ", status=" + status + ", yzTime=" + yzTime + ", loginToken=" + loginToken + ", smTime=" + smTime
				+ ", czTime=" + czTime + ", createtime=" + createtime + "]";
	}


	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	@Override
	public boolean equals(Object obj) {
	    if( obj instanceof User ){
	    	return Objects.equals(((User) obj).getId(), this.getId());
		}
		return false;
	}


	@Override
	public int hashCode() {
	    return this.id;
	}

	public String getCheckbalance() {
		return checkbalance;
	}

	public void setCheckbalance(String checkbalance) {
		this.checkbalance = checkbalance;
	}
}
