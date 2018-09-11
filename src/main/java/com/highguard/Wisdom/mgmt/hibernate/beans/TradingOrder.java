package com.highguard.Wisdom.mgmt.hibernate.beans;

/**
 * APP 下订单辅助
 * 
 * @author Administrator
 *
 */
public class TradingOrder {
	private Integer id;
	private Integer orderid;// 订单主表ID
	private String fruitname;// 水果名称
	private Integer latticeid;// 格子ID
	private String copies;// 份数
	private String price;// 价格
	private Integer userid;// 用户id
	private String place;// 产地
	private String texture;// 口感
	private String imageurl;// 照片路径
	private Integer managerId;

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public Integer getUserid() {
		return userid;
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

	public String getFruitname() {
		return fruitname;
	}

	public void setFruitname(String fruitname) {
		this.fruitname = fruitname;
	}

	public Integer getLatticeid() {
		return latticeid;
	}

	public void setLatticeid(Integer latticeid) {
		this.latticeid = latticeid;
	}

	public String getCopies() {
		return copies;
	}

	public void setCopies(String copies) {
		this.copies = copies;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

}
