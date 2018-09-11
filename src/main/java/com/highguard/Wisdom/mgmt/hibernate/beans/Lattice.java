package com.highguard.Wisdom.mgmt.hibernate.beans;

/**
 * 设备信息
 * 
 * @author Administrator
 * 
 */
public class Lattice {

	private Integer id;
	private Integer deviceid;//
	private String lockid;// socket ip地址
	private String qrcode;// 二维码地址
	private Integer fruitid;// 水果ID
	private String price;// 价格
	private String fruit;
	private String weight;
	private String status;// 开锁状态
	private String date;// 允许开锁时间
	private String copynum;// 每份数量 （几个水果）
	private String copyprice;// 每份价格

	private Integer ispromotion; // 是否开启促销 ，0，否 1：是
	private String promotionprice; // 促销价格
	private String promotionweight; // 促销阀值
	private String promotiontime; // 开启促销的时间

	private Integer type;

	private ICPort icport;
	private Device device;

	@Override
	public String toString() {
		return "Lattice [id=" + id + ", deviceid=" + deviceid + ", lockid=" + lockid + ", qrcode=" + qrcode + ", fruitid="
				+ fruitid + ", price=" + price + ", fruit=" + fruit + ", weight=" + weight + ", status=" + status + ", date="
				+ date + ", copynum=" + copynum + ", copyprice=" + copyprice + ", ispromotion=" + ispromotion
				+ ", promotionprice=" + promotionprice + ", promotionweight=" + promotionweight + ", promotiontime="
				+ promotiontime + ", type=" + type + ", icport=" + icport + "]";
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getPromotiontime() {
		return promotiontime;
	}

	public void setPromotiontime(String promotiontime) {
		this.promotiontime = promotiontime;
	}

	public Integer getIspromotion() {
		return ispromotion;
	}

	public void setIspromotion(Integer ispromotion) {
		this.ispromotion = ispromotion;
	}

	public String getPromotionprice() {
		return promotionprice;
	}

	public void setPromotionprice(String promotionprice) {
		this.promotionprice = promotionprice;
	}

	public String getPromotionweight() {
		return promotionweight;
	}

	public void setPromotionweight(String promotionweight) {
		this.promotionweight = promotionweight;
	}

	public String getCopynum() {
		return copynum;
	}

	public void setCopynum(String copynum) {
		this.copynum = copynum;
	}

	public String getCopyprice() {
		return copyprice;
	}

	public void setCopyprice(String copyprice) {
		this.copyprice = copyprice;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getFruit() {
		return fruit;
	}

	public void setFruit(String fruit) {
		this.fruit = fruit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(Integer deviceid) {
		this.deviceid = deviceid;
	}

	public String getLockid() {
		return lockid;
	}

	public void setLockid(String lockid) {
		this.lockid = lockid;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public Integer getFruitid() {
		return fruitid;
	}

	public void setFruitid(Integer fruitid) {
		this.fruitid = fruitid;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public ICPort getIcport() {
		return icport;
	}

	public void setIcport(ICPort icport) {
		this.icport = icport;
	}
}
