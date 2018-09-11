package com.highguard.Wisdom.struts.actions.appApi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.Device;
import com.highguard.Wisdom.mgmt.hibernate.beans.Fruit;
import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.hibernate.beans.Order;
import com.highguard.Wisdom.mgmt.hibernate.beans.OrderPlace;
import com.highguard.Wisdom.mgmt.hibernate.beans.SystemUser;
import com.highguard.Wisdom.mgmt.hibernate.beans.TradingOrder;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.DeviceManager;
import com.highguard.Wisdom.mgmt.manager.FruitManager;
import com.highguard.Wisdom.mgmt.manager.OrderPlaceManager;
import com.highguard.Wisdom.mgmt.manager.TradingOrderManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.util.JsonUtil;
import com.highguard.Wisdom.util.StringUtil;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 订单接口
 * 
 * @author west
 *
 */
@Controller
@Scope("prototype")
public class TradingApiAction extends ApiBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(TradingApiAction.class);
	
	@Resource
	DeviceManager deviceManager;
	@Resource
	UserManager userManager;
	@Resource
	TradingOrderManager tradingOrderManager;
	@Resource
	FruitManager fruitManager;
	@Resource
	OrderPlaceManager orderPlaceManager;
	private String phone;// 消费用户号码
	private String id;// 订单ID
	private String usertype;// 0 主账户1 亲情号
	private String userId;// android
	private String userid;// ioshttp://item.jd.com/10072510812.html
	private String orderinfo;// 前台传来的 订单字符串 如 " 1:5,2:2" key value key 是格子id value 是份数。
	private String orderPlaceId;// 收货地址ID
	private List<Order> list = new ArrayList<Order>();
	private String deciceId;// 机器的ID

	public String getDeciceId() {
		return deciceId;
	}

	public void setDeciceId(String deciceId) {
		this.deciceId = deciceId;
	}

	private Map<String, String> deviceMap = new HashMap<String, String>();

	public Map<String, String> getDeviceMap() {
		return deviceMap;
	}

	public void setDeviceMap(Map<String, String> deviceMap) {
		this.deviceMap = deviceMap;
	}

	private Order order = new Order();
	private String status;
	private String ordersn;
	private String totalprice;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Order> getList() {
		return list;
	}

	public void setList(List<Order> list) {
		this.list = list;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOrderPlaceId() {
		return orderPlaceId;
	}

	public void setOrderPlaceId(String orderPlaceId) {
		this.orderPlaceId = orderPlaceId;
	}

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DecimalFormat df = new DecimalFormat("######0.00");

	public void chongZhi() {

	}

	public String orderList() {
		ActionContext ctx = ActionContext.getContext();
		Map<String, String> deviceSearchmap = new HashMap<String, String>();
		Map<String, Object> orderSearchmap = new HashMap<String, Object>();
		if (ctx.getSession().get("userInfo") != null) {
			if (((SystemUser) ctx.getSession().get("userInfo")).getRole_id() == 2) {
				deviceSearchmap.put("managerid", ((SystemUser) ctx.getSession().get("userInfo")).getId().toString());
				orderSearchmap.put("managerId", ((SystemUser) ctx.getSession().get("userInfo")).getId());
			}
		}else if (ctx.getSession().get("user")==null) {
			return INPUT;
		}
		List<Device> device = deviceManager.getDeviceList(-1, -1, deviceSearchmap);
		deviceMap.put("", "-- 请选择 --");
		for (int i = 0; i < device.size(); i++) {
			deviceMap.put(device.get(i).getId() + "", device.get(i).getName());
		}
		int currentPage = Integer.parseInt(getCurrentPage());
		int pageNum = Integer.parseInt(getPageNum());
//		map.put("deviceid", deciceId);
		
		list = tradingOrderManager.getOrderList(currentPage, pageNum, orderSearchmap);
		int total = tradingOrderManager.getOrderListCount(orderSearchmap);
		setTotalNum(String.valueOf(total));
		setTotalPages(String.valueOf(getTotalPage(total)));
		setRows(list.size());
		return SUCCESS;
	}

	public String toeditOrder() {
		order = tradingOrderManager.getOrder(Integer.parseInt(id));
		return SUCCESS;
	}

	public String editOrder() {
		order = tradingOrderManager.getOrder(Integer.parseInt(id));
		order.setState(status);
		order.setOrdersn(ordersn);
		order.setTotalprice(totalprice);
		tradingOrderManager.addOrder(order);
		return SUCCESS;
	}

	/**
	 * 根据 获取 用户ID 和类型 所有订单
	 */
	public void getTradingOrder() {

		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getRequestURI() + "     ";
		Enumeration a = request.getParameterNames();
		while (a.hasMoreElements()) {
			url += a.nextElement() + ",";
		}
//		WisdomLogger.logInfo("getTradingOrder", "url=" + url);
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", phone);
		String uid = "-1";
		if (userId != null && !userId.equals("")) {
			uid = userId;
		} else if (userid != null && !userid.equals("")) {
			uid = userid;
		}
		map.put("userid", uid);
		map.put("usertype", usertype);
		map.put("id", id);
		// if(map.size()<1){
		List<Order> list = tradingOrderManager.getOrderList(map);
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> orderMap = new HashMap<String, String>();
			orderMap.put("orderid", list.get(i).getId() + "");
			List<TradingOrder> orderList = tradingOrderManager.getTradingOrderList(orderMap);
			// list.get(i).setFreight("0");
			list.get(i).setQuantity(orderList.size() + "");
//				list.get(i).setTradingOrderList(JSONArray.fromObject(orderList));
		}
		JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "查询成功");
		jsonMsg.put("data", JSONArray.fromObject(list));
		writeToFrontPage(jsonMsg);
		// }else{
		// writeToFrontPage(JsonUtil.JsonMsg(false, "", "请补齐参数！"));
		// }

	}

	/**
	 * 新增订单
	 */
	public void addTradingOrder() {
		try {

//			WisdomLogger.logInfo("addTradingOrder", "orderPlaceId=" + orderPlaceId + " orderinfo=" + orderinfo + " userId="
//					+ userId + " usertyp=" + usertype + " phone=" + phone);
			if (StringUtil.isEmpty(orderPlaceId)) {
//				WisdomLogger.logInfo("addTradingOrder", "收货地址不能为空，请检查！");
				writeToFrontPage(JsonUtil.JsonMsg(false, "500", "收货地址不能为空，请检查！"));
				return;
			} else if (StringUtil.isEmpty(phone)) {
//				WisdomLogger.logInfo("addTradingOrder", "手机号不能为空，请检查！");
				writeToFrontPage(JsonUtil.JsonMsg(false, "500", "手机号不能为空，请检查！"));
				return;
			} else if (StringUtil.isEmpty(usertype)) {
//				WisdomLogger.logInfo("addTradingOrder", "usertype不能为空，请检查！");
				writeToFrontPage(JsonUtil.JsonMsg(false, "500", "usertype不能为空，请检查！"));
				return;
			}

			if (usertype.equals("null") || userId.equals("null")) {
//				WisdomLogger.logInfo("addTradingOrder", "用户ID 或者 用户类型为空 ");
				writeToFrontPage(JsonUtil.JsonMsg(false, "500", "用户ID 或者 用户类型为空"));
				return;
			}

			if (orderinfo != null && !"".equals(orderinfo)) {
				// 获取收货地址
				OrderPlace orderPlace = orderPlaceManager.getOrderPlace(Integer.parseInt(orderPlaceId));
				if (StringUtil.isEmpty(orderPlace)) {
//					WisdomLogger.logInfo("addTradingOrder", "orderPlaceId参数不符合规范，请检查");
					writeToFrontPage(JsonUtil.JsonMsg(false, "500", "orderPlaceId参数不符合规范，请检查！"));
					return;
				}
				if (orderinfo.split(",").length < 1) {
//					WisdomLogger.logInfo("addTradingOrder", "没有选择水果");
					writeToFrontPage(JsonUtil.JsonMsg(false, "500", "没有选择水果"));
					return;
				}
				if (userId == null || "".equals(userId)) {
//					WisdomLogger.logInfo("addTradingOrder", "userId 为空");
					writeToFrontPage(JsonUtil.JsonMsg(false, "500", "userId 为空"));
					return;
				}
				// 保存主表
				Order ord = new Order();
				ord.setCreatetime(sdf.format(new Date()));
				ord.setPhone(phone);
				ord.setState("0");
				ord.setType(usertype);
				ord.setUserid(Integer.parseInt(userId));
				ord.setFreight("0");
				ord.setOrderPlace(orderPlace.getUsername() + " " + orderPlace.getPhone() + " " + orderPlace.getCity() + " "
						+ orderPlace.getAddress());
				tradingOrderManager.addOrder(ord);
				String[] orderstr = orderinfo.split(",");
				double totalprice = 0.0;// 总价
				for (int i = 0; i < orderstr.length; i++) {
					double fruitprice = 0.0;// 单个格子总价
					String latticeid = orderstr[i].split(":")[0];
					String copies = orderstr[i].split(":")[1];
					Lattice lattice = deviceManager.getLatticeById(Integer.parseInt(latticeid));
					if (latticeid == null) {
//						WisdomLogger.logInfo("addTradingOrder", "latticeid = " + latticeid + " 不存在");
						writeToFrontPage(JsonUtil.JsonMsg(false, "500", "latticeid 为空"));
						return;
					}
					ord.setDeviceid(lattice.getDeviceid());
					Device device = deviceManager.getDeviceById(lattice.getDeviceid());
					
					Fruit fruit = fruitManager.getFruitById(lattice.getFruitid());
					double price = Double.parseDouble(
							(lattice.getCopyprice() == null || "".equals(lattice.getCopyprice())) ? "0" : lattice.getCopyprice());// 每份单价
//					WisdomLogger.logInfo("addTradingOrder", "Copyprice = " + price);
					double totprice = price * (Integer.parseInt(copies));
					TradingOrder order = new TradingOrder();
					order.setLatticeid(lattice.getId());
					order.setCopies(copies);
					order.setFruitname(fruit.getProductname());
					order.setOrderid(ord.getId());
					order.setPrice(totprice + "");
					order.setPlace(fruit.getPlace());
					order.setTexture(fruit.getTexture());
					order.setImageurl(fruit.getImageurl());
					order.setManagerId(device.getManagerid());
					tradingOrderManager.addTradingOrder(order);
					totalprice += totprice;
				}
				ord.setTotalprice(totalprice + "");
				if (totalprice < 30) {// 如果购买的少于30块钱 就要新增5块钱运费
					ord.setTotalprice((totalprice + 5) + "");
					ord.setFreight("5");
				}
				tradingOrderManager.addOrder(ord);
				JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "新增成功");
				jsonMsg.put("id", ord.getId());
				writeToFrontPage(jsonMsg);
			} else {
				writeToFrontPage(JsonUtil.JsonMsg(false, "", "请补齐参数！"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void deleteOrder() {
		if (id != null && !"".equals(id)) {
			tradingOrderManager.deleteTradingOrder(Integer.parseInt(id));
			tradingOrderManager.deleteOrder(Integer.parseInt(id));
			writeToFrontPage(JsonUtil.JsonMsg(true, "", "删除成功"));
		} else {
//			WisdomLogger.logInfo("deleteOrder", "请补齐参数");
			writeToFrontPage(JsonUtil.JsonMsg(false, "", "请补齐参数！"));
		}
	}

	public void deleteTradingOrder() {
		if (id != null && !"".equals(id)) {
			TradingOrder tradingOrder = tradingOrderManager.getTradingOrder(Integer.parseInt(id));
			if (tradingOrder == null) {
//				WisdomLogger.logInfo("deleteTradingOrder", "订单不存在");
				writeToFrontPage(JsonUtil.JsonMsg(false, "", "订单不存在！"));
				return;
			}
			double orderprice = Double.parseDouble(tradingOrder.getPrice());
			int oderid = tradingOrder.getOrderid();
			Order order = tradingOrderManager.getOrder(oderid);
			double totalprice = Double.parseDouble(order.getTotalprice());
			tradingOrderManager.deleteTradingOrderById(Integer.parseInt(id));
			order.setTotalprice(df.format((totalprice - orderprice)));
			tradingOrderManager.addOrder(order);
			writeToFrontPage(JsonUtil.JsonMsg(true, "", "删除成功"));
		} else {
//			WisdomLogger.logInfo("deleteOrder", "请补齐参数");
			writeToFrontPage(JsonUtil.JsonMsg(false, "", "请补齐参数！"));
		}
	}

	/*
	 * 支付接口
	 */
	public void payOrder() {
		if (id != null && !"".equals(id)) {
			Order order = tradingOrderManager.getOrder(Integer.parseInt(id));
			if (StringUtil.isEmpty(order)) {
//				WisdomLogger.logInfo("payOrder", "id参数不符合规范，请检查");
				writeToFrontPage(JsonUtil.JsonMsg(false, "500", "id参数不符合规范，请检查！"));
				return;
			}
			User user = validationService.getUserInformationById(order.getType(), order.getUserid()).getUserMaster();
			double price = Double.valueOf(order.getTotalprice());
			double yu = Double.valueOf(user.getBalance());

			if (price > yu) {
//				WisdomLogger.logInfo("payOrder", "余额不足，无法支付" + user.getName());
				writeToFrontPage(JsonUtil.JsonMsg(false, "", "余额不足，无法支付！"));
			} else {
				double newyu = yu - price;// 消费后的余额
				user.setBalance(df.format(newyu));
				userManager.updateUser(user);
				order.setPaytime(sdf.format(new Date()));
				order.setState("1");
				tradingOrderManager.addOrder(order);
				JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "支付成功");
				jsonMsg.put("orderid", JSONArray.fromObject(order.getId()));
				writeToFrontPage(jsonMsg);
			}

		} else {
			writeToFrontPage(JsonUtil.JsonMsg(false, "", "请补齐参数！"));
		}
	}

	public String getOrderinfo() {
		return orderinfo;
	}

	public void setOrderinfo(String orderinfo) {
		this.orderinfo = orderinfo;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public static void aa() {
		for (int i = 0; i < 6; i++) {

			if (i == 2) {

				return;
			}
		}
	}

	public static void main(String[] args) {
		aa();
	}

	public void setOrdersn(String ordersn) {
		this.ordersn = ordersn;
	}

	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}
}
