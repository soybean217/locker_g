package com.highguard.Wisdom.mgmt.hibernate.beans;

import java.util.Date;

/**
 * 格子链接状态
 * 
 * @author Administrator
 * 
 */
public class LatticeStatus {

	private Integer id;
	private String lattice;//格子的id
	private String type;//格子状态
	private Date createtime;
	
	private String card;//用户的卡号
	private String readstate;//第几次查询重量
	
	private String comment;//说明
	
	private String flag ;// 流程是否结束 0 未结束 1 结束

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getId() {
		return id;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getReadstate() {
		return readstate;
	}

	public void setReadstate(String readstate) {
		this.readstate = readstate;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
