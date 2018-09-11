package com.highguard.Wisdom.mgmt.hibernate.beans;

import java.util.Date;

import net.sf.json.JSONArray;

/**
 * APP 下订单主表
 *
 * @author Administrator
 */
public class Order {
	private Integer id;
	private String ordersn;// 订单生成日期
	private String createtime;// 订单生成日期
	private String paytime;// 支付日期
	private String state;// 状态 0 未支付 1 支付成功
	private String type;// 用户类型 0主用户 1 亲情号
	private Integer userid;// 用户ID
	private String phone;// 用户手机号
	private String totalprice;// 总价
	private String quantity;// 商品数量
	private String orderPlace;// 收货地址 信息
	private String freight;// 运费
	private String isSend;// 是否配送 若为空 则是未配送。 1 为已经配送
	private String show;// 显示商品的详细信息
	private Integer deviceid;// 设备的ID
	private Integer icportid;
	private Integer storeid;
	private String givebacktime;
	private String transactionId;
	private Integer managerId;

	private Lattice device;
	private ICPort icport;
	private User user;

	private float pay;

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}

	public Integer getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(Integer deviceid) {
		this.deviceid = deviceid;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getIsSend() {
		return isSend;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getOrderPlace() {
		return orderPlace;
	}

	public void setOrderPlace(String orderPlace) {
		this.orderPlace = orderPlace;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getGivebacktime() {
		return givebacktime;
	}

	public void setGivebacktime(String givebacktime) {
		this.givebacktime = givebacktime;
	}

	public String getPaytime() {
		return paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getIcportid() {
		return icportid;
	}

	public void setIcportid(Integer icportid) {
		this.icportid = icportid;
	}

	public String getOrdersn() {
		return ordersn;
	}

	public void setOrdersn(String ordersn) {
		this.ordersn = ordersn;
	}

	public Lattice getDevice() {
		return device;
	}

	public void setDevice(Lattice device) {
		this.device = device;
	}

	public ICPort getIcport() {
		return icport;
	}

	public void setIcport(ICPort icport) {
		this.icport = icport;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public float getPay() {
		return pay;
	}

	public void setPay(float pay) {
		this.pay = pay;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", ordersn=" + ordersn + ", createtime=" + createtime + ", paytime=" + paytime
				+ ", state=" + state + ", type=" + type + ", userid=" + userid + ", phone=" + phone + ", totalprice="
				+ totalprice + ", quantity=" + quantity + ", orderPlace=" + orderPlace + ", freight=" + freight + ", isSend="
				+ isSend + ", show=" + show + ", deviceid=" + deviceid + ", icportid=" + icportid + ", storeid=" + storeid
				+ ", givebacktime=" + givebacktime + ", transactionId=" + transactionId + ", managerId=" + managerId
				+ ", device=" + device + ", icport=" + icport + ", user=" + user + ", pay=" + pay + "]";
	}
}
