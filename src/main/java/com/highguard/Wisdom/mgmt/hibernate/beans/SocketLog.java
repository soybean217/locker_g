package com.highguard.Wisdom.mgmt.hibernate.beans;

import java.util.Date;

public class SocketLog {

	private Integer id;
	private String user;
	private String phone;
	private String lattice;
	private String action;
	private String type;// 发送 或者接受
	private Date createtime;
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLattice() {
		return lattice;
	}

	public void setLattice(String lattice) {
		this.lattice = lattice;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
