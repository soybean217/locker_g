package com.highguard.Wisdom.mgmt.hibernate.beans;

public class InitStatus {

	private Integer id;
	private String lattice;  //格子号
	private String createtime;// 需要发送时间
	private String status;//状态    0 未发送  1 已发送
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLattice() {
		return lattice;
	}
	public void setLattice(String lattice) {
		this.lattice = lattice;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
