package com.highguard.Wisdom.mgmt.hibernate.beans;

import java.util.Date;

/**
 * 格子重量变化
 * 
 * @author Administrator
 * 
 */
public class Latticestage {

	private Integer id;
	private String lattice;// 
    private String weight;
    private Date createtime;
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
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
