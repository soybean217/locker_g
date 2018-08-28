package com.highguard.Wisdom.mgmt.hibernate.beans;

import java.sql.Blob;
import java.util.Date;

/**
 * 设备信息
 * 
 * @author Administrator
 * 
 */
public class Device {

	private Integer id;
	private String name;
	private String num;// 编号
	private String location;//位置
	private String cellnum;//数量
	private String status;//
	private Blob wximage;
	private String comment;
	private Integer managerid;

	public Blob getWximage() {
		return wximage;
	}
	public void setWximage(Blob wximage) {
		this.wximage = wximage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getCellnum() {
		return cellnum;
	}
	public void setCellnum(String cellnum) {
		this.cellnum = cellnum;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}


	public Integer getManagerid() {
		return managerid;
	}

	public void setManagerid(Integer managerid) {
		this.managerid = managerid;
	}
}
