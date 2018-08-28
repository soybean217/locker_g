package com.highguard.Wisdom.mgmt.hibernate.beans;

import java.sql.Blob;
import java.util.Date;

/**
 * 交易信息
 * 
 * @author Administrator
 * 
 */
public class Trading {

	private Integer id;
	private String username;
	private String wx;
	private Integer userid;
	private String balance;// 余额
	private String consumption;// 消费金额
	private String takeway;// 是否取走  0 未取走 1表示 取走
	private String status;// 2  表示放入水果(或者放入杂物)   1表示取出水果
	private Integer fruitid;
	private Date createtime;
    private String fruitnum;
    private String fruitname;
    private Integer deviceid; 
    private Integer latticeid;
    private String lockid;
    public String getLockid() {
		return lockid;
	}

	public void setLockid(String lockid) {
		this.lockid = lockid;
	}

	public Integer getLatticeid() {
		return latticeid;
	}

	public void setLatticeid(Integer latticeid) {
		this.latticeid = latticeid;
	}

	public Integer getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(Integer deviceid) {
		this.deviceid = deviceid;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	private String device;
	public String getFruitnum() {
		return fruitnum;
	}

	public void setFruitnum(String fruitnum) {
		this.fruitnum = fruitnum;
	}

	public String getFruitname() {
		return fruitname;
	}

	public void setFruitname(String fruitname) {
		this.fruitname = fruitname;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getWx() {
		return wx;
	}

	public void setWx(String wx) {
		this.wx = wx;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

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

	public String getTakeway() {
		return takeway;
	}

	public void setTakeway(String takeway) {
		this.takeway = takeway;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getFruitid() {
		return fruitid;
	}

	public void setFruitid(Integer fruitid) {
		this.fruitid = fruitid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
