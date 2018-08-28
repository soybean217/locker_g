package com.highguard.Wisdom.struts.actions.appApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.logging.WisdomLogger;
import com.highguard.Wisdom.mgmt.hibernate.beans.OrderPlace;
import com.highguard.Wisdom.mgmt.hibernate.beans.TradingOrder;
import com.highguard.Wisdom.mgmt.manager.OrderPlaceManager;
import com.highguard.Wisdom.mgmt.manager.RechargeManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.service.WalletService;
import com.highguard.Wisdom.util.JsonUtil;

/**
 * 订单收货地址接口
 * 
 * @author west
 * 
 */
@Controller
@Scope("prototype")
public class OrderPlaceApiAction extends ApiBaseAction {

	@Resource
	OrderPlaceManager orderPlaceManager;

	private String userId;// 主用户ID
	private String phone;// 收货人手机号码
	private String username;// 收货人姓名
	private String address;// 收货人地址
	private String post;// 收货人邮编
	private String comment;// 备注
	private String id;//地址id
	private String street;//街道
	private String city;// 省市区
	

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	public void getOrderPlaceList(){
		if(null!=userId&&!"".equals(userId)){
			Map<String,String> map = new HashMap<String, String>();
			map.put("userid", userId);
			List<OrderPlace> list =orderPlaceManager.getOrderPlaceList(map);
			JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "查询成功");
			jsonMsg.put("data", JSONArray.fromObject(list));
			writeToFrontPage(jsonMsg);
		}else{
			 WisdomLogger.logInfo("getOrderPlaceList","请补齐参数");
			writeToFrontPage(JsonUtil.JsonMsg(false, "", "请补齐参数！"));
		}
	}
	
	/**
	 * 新增和修改接口  修改 多一个id字段
	 */
	public void addOrderPlace(){
		if(null!=userId&&!"".equals(userId)){
			OrderPlace place = new OrderPlace();
			place.setUserid(Integer.parseInt(userId));
			place.setAddress(address);
			place.setComment(comment);
			place.setPhone(phone);
			//place.setPost(post);
			place.setUsername(username);
			place.setCity(city);
			//place.setStreet(street);
			if(id!=null&&!"".equals(id)){
				place.setId(Integer.parseInt(id));
			}
			orderPlaceManager.saveOrderPlace(place);
			JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "新增成功");
			writeToFrontPage(jsonMsg);
		}else{
			 WisdomLogger.logInfo("addOrderPlace","请补齐参数");
			writeToFrontPage(JsonUtil.JsonMsg(false, "", "请补齐参数！"));
		}
	}
	
	
	public void deleteOrderPlace(){
		if(null!=id&&!"".equals(id)){
		
			orderPlaceManager.deleteOrderPlace(id);
			JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "删除成功");
			writeToFrontPage(jsonMsg);
		}else{
			 WisdomLogger.logInfo("deleteOrderPlace","请补齐参数");
			writeToFrontPage(JsonUtil.JsonMsg(false, "", "请补齐参数！"));
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
