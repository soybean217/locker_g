package com.highguard.Wisdom.struts.actions;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.highguard.Wisdom.exception.SocketRuntimeException;
import com.highguard.Wisdom.exception.WebSocketRuntimeException;
import com.highguard.Wisdom.mgmt.hibernate.beans.*;
import com.highguard.Wisdom.mgmt.manager.*;
import com.highguard.Wisdom.struts.actions.wxapp.WebSocket;
import com.highguard.Wisdom.struts.common.GetPathCommon;
import com.highguard.Wisdom.struts.common.ShortMessageUtils;
import com.highguard.Wisdom.struts.common.WxApp;
import com.highguard.Wisdom.struts.listener.*;
import com.highguard.Wisdom.util.CommunityUtils;
import com.highguard.Wisdom.util.JsonUtil;
import com.highguard.Wisdom.util.StringUtil;
import com.highguard.Wisdom.util.WxPayUtil;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.Int;
import org.apache.struts2.ServletActionContext;
import org.dom4j.DocumentException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.json.JSONException;
import org.junit.internal.runners.statements.Fail;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.ShortMessage;
import javax.websocket.Session;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ws on 2017/7/15.
 */
@Controller
@Scope("prototype")
public class ControlAction extends BaseAction {

	private Logger logger = Logger.getLogger(ControlAction.class);

	@Resource
	DeviceManager deviceManager;

	@Resource
	TradingOrderManager orderManager;

	public void getCode() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String mobile = request.getParameter("mobile");
		String key = ShortMessageUtils.createRandomVcode();

		HttpSession session = request.getSession();
		session.setAttribute("vcode", key);

		JSONObject response = new JSONObject();
		try {
			//old sms provider
			//ShortMessageUtils.sendSMSGet(mobile, "您的验证码是：" + key, ""); 
			String[] params = { key,"5" };
			SmsSingleSender ssender = new SmsSingleSender(1400167573, "06b26763070fb808086a102baddf2c27");
			try {
				SmsSingleSenderResult result = ssender.sendWithParam("86", mobile, 240864, params, null, "", "");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HTTPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 签名参数未提供或者为空时，会使用默认签名发送短信
			jsonResponse(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		catch (MalformedURLException e) {
//			jsonError(e.getMessage());
//		} catch (UnsupportedEncodingException e) {
//			jsonError(e.getMessage());
//		}

	}

	public void bindMobile() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		try {
			String postedData = IOUtils.toString(request.getInputStream());
			JSONObject data = JSONObject.fromObject(postedData);
			String code = data.getString("code");
			String mobile = data.getString("mobile");
			String key = (String) session.getAttribute("vcode");

			if (StringUtil.isEmpty(key) || !key.equals(code)) {
				jsonError("请输入正确的验证码");
				return;
			}
			UserManager userManager = (UserManager) ApplicationUtil.act.getBean("userManager");
			User user = (User) session.getAttribute("user");
			if (user == null) {
				jsonError("请授权小程序登陆");
				return;
			}

			JSONObject response = new JSONObject();
			user.setTelephone(mobile);

			userManager.updateUser(user);
			session.setAttribute("user", user);
			session.removeAttribute("vcode");
			jsonResponse(response);
		} catch (IOException e) {
			jsonError(e.getMessage());
		}
	}

	public void code2session() {
		HttpServletRequest request = ServletActionContext.getRequest();

		String code = request.getParameter("code");

		try {
			JSONObject res = WxApp.getSessionKey(code);
			HttpSession session = request.getSession();
			res.put("jsessionid", session.getId());
			session.setAttribute("session_key", res.get("session_key"));
			jsonResponse(res);
		} catch (Exception e) {
			jsonError(e.getMessage());
		}
	}

	public void userInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String postedData = IOUtils.toString(request.getInputStream());
			HttpSession session = request.getSession();
			String sessionKey = (String) session.getAttribute("session_key");

			JSONObject data = JSONObject.fromObject(postedData);
			JSONObject userInfo = WxApp.getUserInfo(data.getString("encryptedData"), sessionKey, data.getString("iv"));
			if (userInfo != null) {
				User user = WxApp.saveUserInfo(userInfo);
				session.setAttribute("user", user);
				jsonResponse(JSONObject.fromObject(user));
			}
		} catch (IOException e) {
			jsonError(e.getMessage());
		}
	}

	public void touch() {
		HttpServletRequest request = ServletActionContext.getRequest();
		JSONObject res = new JSONObject();
		res.put("status", 1);
		HttpSession session = request.getSession();
		res.put("jsessionid", session.getId());

		jsonResponse(res);
	}

	public void checkOrder() {
		TradingOrderManager tradingOrderManager = (TradingOrderManager) ApplicationUtil.act.getBean("tradingOrderManager");
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute("user");
		JSONObject response = new JSONObject();
		GetPathCommon common = new GetPathCommon();

		try {
			if (user == null) {
				throw new WebSocketRuntimeException(SocketRuntimeException.INVALID_USER, "找不到用户信息");
			}
			HashMap<String, Object> map = new HashMap<>();
			map.put("user", user);
			map.put("state", "0");
			List<Order> orders0 = tradingOrderManager.getOrderList(1, 20, map);
			map.put("state", "1");
			List<Order> orders1 = tradingOrderManager.getOrderList(1, 20, map);
			int max0 = Integer.valueOf(common.getCommonDir("max0"));
			int max1 = Integer.valueOf(common.getCommonDir("max1"));

			if (orders0.size() >= max0) {
				throw new WebSocketRuntimeException(SocketRuntimeException.LASTORDER_NOTPAY, "有未支付订单");
			}
			if (orders1.size() >= max1) {
				throw new WebSocketRuntimeException(SocketRuntimeException.LASTORDER_NOTGIVEBACK, "有未还玩具");
			}
			jsonResponse(response);
		} catch (WebSocketRuntimeException ex) {
			jsonError(ex.getMessage(), ex.getErrorCode());
		} catch (Exception ex) {
			jsonError(ex.getMessage(), 500);
		}
	}

	public void lattice() {
		ServletRequest request = ServletActionContext.getRequest();

		Integer id = Integer.parseInt(request.getParameter("id"));
		JSONObject response = new JSONObject();
		if (id < 1) {
			response.put("status", 0);
			jsonResponse(response);
			return;
		}

		try {
//			String postedData = IOUtils.toString(request.getInputStream());
			String postedData = JsonUtil.getRequestInputStream(request);
			if (StringUtil.isEmpty(postedData)) {
				response.put("status", 0);
				jsonResponse(response);
				return;
			}
			JSONObject postedJson = JSONObject.fromObject(postedData);
			if (postedJson == null || postedJson.getJSONObject("user") == null) {
				response.put("status", 0);
				response.put("message", "请升级您的微信版本，再来扫码");
				jsonResponse(response);
				return;
			}

			int scale = postedJson.getInt("scale");
			String action = "";

			if (postedJson.containsKey("action"))
				action = postedJson.getString("action");

			// 验证签名，暂时省略
			// 获取设备ID
			Device device = deviceManager.getDeviceById(id);
			if (device == null) {
				response.put("status", 0);
				response.put("message", "找不到设备");
				jsonResponse(response);
				return;
			}
			if ("takeback".equals(action)) {

			} else {
				List<Lattice> lattices = new ArrayList<>();

				for (Lattice l : deviceManager.getLatticesByDevice(device)) {
					if (scale == l.getType()) {
						lattices.add(l);
					}
				}

				// 获取未使用的相应大小的控制器

				Map<String, Object> left = new HashMap<>();
				Map<String, Object> right = new HashMap<>();

				left.put("state", "1");
				left.put("type", "1");

				right.put("state", "2");
				right.put("type", "3");

				List<Criterion> conditions = new ArrayList<>();
				conditions.add(Restrictions.in("device", lattices));
				conditions.add(Restrictions.or(Restrictions.allEq(left),
						Restrictions.and(Restrictions.allEq(right), Restrictions.isNull("transactionId"))));

				List<Order> orders = orderManager.getOrders(conditions);

				Lattice lattice = null;

				for (Lattice l : lattices) {
					boolean isIn = false;
					for (Order o : orders) {
						if (l.getId().equals(o.getDevice().getId())) {
							isIn = true;
							break;
						}
					}

					if (!isIn) {
						lattice = l;
					}
				}
				if (lattice == null) {
					throw new WebSocketRuntimeException(SocketRuntimeException.DEFAULT_ERROR_CODE, "没有空闲的格子");
				}
				response.put("lattice", lattice);
			}

			response.put("status", 1);
			response.put("device", device);
			jsonResponse(response);
		} catch (Exception e) {
			jsonError(e.getMessage());
		}
	}

	public void confirmOrder() {

		ServletRequest request = ServletActionContext.getRequest();

		JSONObject response = new JSONObject();
		JSONObject data = null;
		String postedData = null;
		try {
			postedData = IOUtils.toString(request.getInputStream());
			data = JSONObject.fromObject(postedData);
			int orderid = data.getInt("orderid");
			if (orderid > 0) {
				TradingOrderManager tradingOrderManager = (TradingOrderManager) ApplicationUtil.act
						.getBean("tradingOrderManager");
				Order order = tradingOrderManager.getOrder(orderid);
				if (order.getState().equals("1") && Double.parseDouble(order.getTotalprice()) == 0) {
					order.setState("2");
					tradingOrderManager.addOrder(order);
					jsonResponse(response);
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			jsonError(ex.getMessage());
		}
	}

	public void createPrePayOrder() {
		ServletRequest request = ServletActionContext.getRequest();
		String price = "0";

		JSONObject response = new JSONObject();
		String postedData = null;
		try {
			postedData = IOUtils.toString(request.getInputStream());
			JSONObject data = JSONObject.fromObject(postedData);

			String state = data.getString("state");
			String type = data.getString("type");

			JSONObject jsonUser = data.getJSONObject("user");

			price = data.getString("amount");

			TradingOrderManager tradingOrderManager = (TradingOrderManager) ApplicationUtil.act
					.getBean("tradingOrderManager");
			DeviceManager deviceManager = (DeviceManager) ApplicationUtil.act.getBean("deviceManager");
			UserManager userManager = (UserManager) ApplicationUtil.act.getBean("userManager");
			Lattice lattice = deviceManager.getLatticeById(data.getInt("id"));
			User user = userManager.getUserById(Integer.parseInt(jsonUser.getString("id")));

			Order order = new Order();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			order.setCreatetime(sdf.format(new Date()));
			order.setState(StringUtils.isEmpty(state) ? "0" : state);
			order.setType(StringUtils.isEmpty(type) ? "2" : type);
			order.setUser(user);
			if (!StringUtils.isEmpty(price)) {
				order.setTotalprice(String.valueOf(price));
			} else {
				order.setTotalprice(lattice.getPromotionprice());
			}
			order.setGivebacktime(data.getString("time"));
			order.setOrderPlace(URLDecoder.decode(data.getString("place"), "UTF-8"));
			order.setOrdersn(tradingOrderManager.getOrderIdByUUId());

			if (lattice != null) {
				order.setDevice(lattice);
			}

			tradingOrderManager.addOrder(order);

			response.put("status", 1);
			response.put("result", order);
			jsonResponse(response);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			jsonError(e.getMessage());
		}
	}

	// 发起微信支付
	public String payWithTestFee() {
		logger.debug("enter payWithTestFee");
		HttpServletRequest request = ServletActionContext.getRequest();
		JSONObject response = new JSONObject();

		GetPathCommon common = new GetPathCommon();

		TradingOrderManager orderManager = (TradingOrderManager) ApplicationUtil.act.getBean("tradingOrderManager");
		// 商户号
		String mchId = "1502130451";
//	        String mchId = "";
		// 支付密钥
		String key = "&key=yixingli180ov192d2s007746xxxxxxx";
//	        String key = "&key=";
		// 交易类型
		String tradeType = "JSAPI";
		// 随机字符串
		String nonceStr = WxPayUtil.getNonceStr();
		// 微信支付完成后给该链接发送消息，判断订单是否完成
		String notifyUrl = "https://lug.popbot.cn/locker/payResult.action";
		// 微信用户唯一id

		if (request.getParameter("openid") == null) {
			response.put("status", 0);
			response.put("data", "支付失败，openid is null");
			jsonResponse(response);
			return ERROR;
		}
		String openId = request.getParameter("openid");
		// 小程序id
		try {
			if (common.getCommonDir("appid") == null) {
				response.put("status", 0);
				response.put("data", "支付失败，appid is null");
				jsonResponse(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String appid = null;
		try {
			appid = common.getCommonDir("appid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 商品订单号(保持唯一性)
		String outTradeNo = orderManager.getOrderIdByUUId();
		// 支付金额
		String totalFee = "1";

		// 发起支付设备ip
		String spbillCreateIp = "127.0.0.1";
		// 商品描述
		String body = "租赁玩具";
		// 附加数据，商户携带的订单的自定义数据 (原样返回到通知中,这类我们需要系统中订单的id 方便对订单进行处理)
		String attach = "";

		// 我们后面需要键值对的形式，所以先装入map
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("appid", appid);
		sParaTemp.put("attach", attach);
		sParaTemp.put("body", body);
		sParaTemp.put("mch_id", mchId);
		sParaTemp.put("nonce_str", nonceStr);
		sParaTemp.put("notify_url", notifyUrl);
		sParaTemp.put("openid", openId);
		sParaTemp.put("out_trade_no", outTradeNo);
		sParaTemp.put("spbill_create_ip", spbillCreateIp);
		sParaTemp.put("total_fee", totalFee);
		sParaTemp.put("trade_type", tradeType);

		logger.debug(sParaTemp.toString());
		// 去掉空值 跟 签名参数(空值不参与签名，所以需要去掉)
		Map<String, String> map = WxPayUtil.paraFilter(sParaTemp);
		logger.debug(map.toString());
		// 按照 参数=参数值&参数2=参数值2 这样的形式拼接（拼接需要按照ASCII码升序排列
		String mapStr = WxPayUtil.createLinkString(map);
		// MD5运算生成签名
		logger.debug(mapStr.toString());
		logger.debug(key);
		String sign = WxPayUtil.sign(mapStr, key, "utf-8").toUpperCase();
		sParaTemp.put("sign", sign);
		logger.debug(sParaTemp.toString());
		/**
		 * 组装成xml参数,此处偷懒使用手动组装，严格代码可封装一个方法，XML标排序需要注意，ASCII码升序排列
		 */
		// + "<attach>" + attach + "</attach>"
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<body>" + body + "</body>" + "<mch_id>" + mchId
				+ "</mch_id>" + "<nonce_str>" + nonceStr + "</nonce_str>" + "<notify_url>" + notifyUrl + "</notify_url>"
				+ "<openid>" + openId + "</openid>" + "<out_trade_no>" + outTradeNo + "</out_trade_no>" + "<spbill_create_ip>"
				+ spbillCreateIp + "</spbill_create_ip>" + "<total_fee>" + totalFee + "</total_fee>" + "<trade_type>"
				+ tradeType + "</trade_type>" + "<sign>" + sign + "</sign>" + "</xml>";
		logger.debug(xml);
		// 统一下单url，生成预付id
		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String result = WxPayUtil.httpRequest(url, "POST", xml);

		logger.debug("Payment unifiedorder: " + result);

		Map<String, String> paramMap = new HashMap<String, String>();
		String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
		// 得到预支付id
		String prepay_id = "";
		try {
			prepay_id = WxPayUtil.getPayNo(result);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String packages = "prepay_id=" + prepay_id;
		String nonceStr1 = WxPayUtil.getNonceStr();
		// 开始第二次签名
		String mapStr1 = "appId=" + appid + "&nonceStr=" + nonceStr1 + "&package=prepay_id=" + prepay_id
				+ "&signType=MD5&timeStamp=" + timeStamp;
		String paySign = WxPayUtil.sign(mapStr1, key, "utf-8").toUpperCase();
		// 前端所需各项参数拼接
		String finaPackage = "\"appId\":\"" + appid + "\",\"timeStamp\":\"" + timeStamp + "\",\"nonceStr\":\"" + nonceStr1
				+ "\",\"package\":\"" + packages + "\",\"signType\" : \"MD5" + "\",\"paySign\":\"" + paySign + "\"";

		Map<String, String> params = new HashMap<>();
		params.put("timeStamp", timeStamp);
		params.put("nonceStr", nonceStr1);
		params.put("package", packages);
		params.put("signType", "MD5");
		params.put("paySign", paySign);

		response.put("status", 1);
		response.put("data", params);
		jsonResponse(response);

		return SUCCESS;
	}

	// 发起微信支付
	public String pay() {
		HttpServletRequest request = ServletActionContext.getRequest();
		JSONObject response = new JSONObject();

		GetPathCommon common = new GetPathCommon();

		TradingOrderManager orderManager = (TradingOrderManager) ApplicationUtil.act.getBean("tradingOrderManager");
		// 商户号
		String mchId = "1502130451";
//        String mchId = "";
//支付密钥
		String key = "&key=yixingli180ov192d2s007746xxxxxxx";
//        String key = "&key=";
//交易类型
		String tradeType = "JSAPI";
//随机字符串
		String nonceStr = WxPayUtil.getNonceStr();
//微信支付完成后给该链接发送消息，判断订单是否完成
		String notifyUrl = "https://lug.popbot.cn/locker/payResult.action";
//微信用户唯一id

		if (StringUtil.isEmpty(request.getParameter("orderid"))) {
			response.put("status", 0);
			response.put("data", "支付失败，orderid is null");
			jsonResponse(response);
			return ERROR;
		}
		Order order = orderManager.getOrder(Integer.parseInt(request.getParameter("orderid")));
		if (order == null) {
			response.put("status", 0);
			response.put("data", "支付失败，order is null");
			jsonResponse(response);
			return ERROR;
		}
		// 重新生成新的订单号，确保这笔订单可以反复支付
		order.setOrdersn(orderManager.getOrderIdByUUId());
		orderManager.addOrder(order);

		if (request.getParameter("openid") == null) {
			response.put("status", 0);
			response.put("data", "支付失败，openid is null");
			jsonResponse(response);
			return ERROR;
		}
		String openId = request.getParameter("openid");
		// 小程序id
		try {
			if (common.getCommonDir("appid") == null) {
				response.put("status", 0);
				response.put("data", "支付失败，appid is null");
				jsonResponse(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String appid = null;
		try {
			appid = common.getCommonDir("appid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 商品订单号(保持唯一性)
		String outTradeNo = order.getOrdersn();
		// 支付金额
		String fee = order.getTotalprice();
		String totalFee = WxPayUtil.getMoney(fee);

		// 发起支付设备ip
		String spbillCreateIp = "127.0.0.1";
		// 商品描述
		String body = "租赁玩具";
		// 附加数据，商户携带的订单的自定义数据 (原样返回到通知中,这类我们需要系统中订单的id 方便对订单进行处理)
		String attach = "";

		// 我们后面需要键值对的形式，所以先装入map
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("appid", appid);
		sParaTemp.put("attach", attach);
		sParaTemp.put("body", body);
		sParaTemp.put("mch_id", mchId);
		sParaTemp.put("nonce_str", nonceStr);
		sParaTemp.put("notify_url", notifyUrl);
		sParaTemp.put("openid", openId);
		sParaTemp.put("out_trade_no", outTradeNo);
		sParaTemp.put("spbill_create_ip", spbillCreateIp);
		sParaTemp.put("total_fee", totalFee);
		sParaTemp.put("trade_type", tradeType);

		logger.debug(sParaTemp.toString());
		// 去掉空值 跟 签名参数(空值不参与签名，所以需要去掉)
		Map<String, String> map = WxPayUtil.paraFilter(sParaTemp);
		logger.debug(map.toString());
// 按照 参数=参数值&参数2=参数值2 这样的形式拼接（拼接需要按照ASCII码升序排列
		String mapStr = WxPayUtil.createLinkString(map);
		// MD5运算生成签名
		logger.debug(mapStr.toString());
		logger.debug(key);
		String sign = WxPayUtil.sign(mapStr, key, "utf-8").toUpperCase();
		sParaTemp.put("sign", sign);
		logger.debug(sParaTemp.toString());
		/**
		 * 组装成xml参数,此处偷懒使用手动组装，严格代码可封装一个方法，XML标排序需要注意，ASCII码升序排列
		 */
		// + "<attach>" + attach + "</attach>"
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<body>" + body + "</body>" + "<mch_id>" + mchId
				+ "</mch_id>" + "<nonce_str>" + nonceStr + "</nonce_str>" + "<notify_url>" + notifyUrl + "</notify_url>"
				+ "<openid>" + openId + "</openid>" + "<out_trade_no>" + outTradeNo + "</out_trade_no>" + "<spbill_create_ip>"
				+ spbillCreateIp + "</spbill_create_ip>" + "<total_fee>" + totalFee + "</total_fee>" + "<trade_type>"
				+ tradeType + "</trade_type>" + "<sign>" + sign + "</sign>" + "</xml>";
		logger.debug(xml);
		// 统一下单url，生成预付id
		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String result = WxPayUtil.httpRequest(url, "POST", xml);

		logger.debug("Payment unifiedorder: " + result);

		Map<String, String> paramMap = new HashMap<String, String>();
		String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
		// 得到预支付id
		String prepay_id = "";
		try {
			prepay_id = WxPayUtil.getPayNo(result);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String packages = "prepay_id=" + prepay_id;
		String nonceStr1 = WxPayUtil.getNonceStr();
		// 开始第二次签名
		String mapStr1 = "appId=" + appid + "&nonceStr=" + nonceStr1 + "&package=prepay_id=" + prepay_id
				+ "&signType=MD5&timeStamp=" + timeStamp;
		String paySign = WxPayUtil.sign(mapStr1, key, "utf-8").toUpperCase();
		// 前端所需各项参数拼接
		String finaPackage = "\"appId\":\"" + appid + "\",\"timeStamp\":\"" + timeStamp + "\",\"nonceStr\":\"" + nonceStr1
				+ "\",\"package\":\"" + packages + "\",\"signType\" : \"MD5" + "\",\"paySign\":\"" + paySign + "\"";

		Map<String, String> params = new HashMap<>();
		params.put("timeStamp", timeStamp);
		params.put("nonceStr", nonceStr1);
		params.put("package", packages);
		params.put("signType", "MD5");
		params.put("paySign", paySign);

		response.put("status", 1);
		response.put("data", params);
		jsonResponse(response);

		return SUCCESS;
	}

	public void payback() {
		// 用于处理结束后返回的xml
		HttpServletRequest request = ServletActionContext.getRequest();
		String resXml = "";
		String key = "&key=yixingli180ov192d2s007746xxxxxxx";
		WebSocketManager webSocketManager = WebSocketManager.newInstance();
		boolean debug = false;
		try {
			InputStream in = request.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int len = 0;
			byte[] b = new byte[1024];
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			out.close();
			in.close();
			// 将流 转为字符串
			String result = new String(out.toByteArray(), "utf-8");
			logger.debug("PaymentCallback recived:" + result);
			String return_code = "";
			Map<String, String> map = null;
			if (debug) {
				map = new HashMap<>();
				return_code = "SUCCESSS";
			} else {
				map = WxPayUtil.getNotifyUrl(result);
				return_code = map.get("return_code").toString().toUpperCase();
			}
			if (return_code.equals("SUCCESS") || debug) {
				// 进行签名验证，看是否是从微信发送过来的，防止资金被盗
				if (WxPayUtil.verifyWeixinNotify(map, key) || debug) {
					// 设置订单信息
					TradingOrderManager orderManager = (TradingOrderManager) ApplicationUtil.act.getBean("tradingOrderManager");
					UserManager userManager = (UserManager) ApplicationUtil.act.getBean("userManager");

					float cashFee = 0;
					Order order = null;
					if (debug) {
						order = orderManager.getOrderByOrdersn(request.getParameter("sn"));
					} else {
						cashFee = Float.valueOf(map.get("cash_fee")) / 100;
						order = orderManager.getOrderByOrdersn(map.get("out_trade_no"));
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					order.setPaytime(sdf.format(new Date()));
					order.setState("2");
					order.setPay(cashFee);
					orderManager.addOrder(order);

					// 如果是预充值订单，则给用户充值
					if (order.getType().equals("2")) {
						User user = order.getUser();
						double newBalance = Double.parseDouble(order.getTotalprice()) + Double.parseDouble(user.getBalance());
						BigDecimal bd = new BigDecimal(newBalance);
						bd = bd.setScale(2, RoundingMode.HALF_UP);
						newBalance = bd.doubleValue();
						user.setBalance(String.valueOf(newBalance));
						userManager.updateUser(user);
					}

					// 通知客户端
					if (webSocketManager.contains(order.getUser())) {
						Session session = webSocketManager.get(order.getUser());
						WxApp.sendMessage(session, order, "paysuccessed");
					}

					// 签名验证成功后按照微信要求返回的xml
					resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
							+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
					result(resXml);
					return;
				}
			} else {
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
						+ "<return_msg><![CDATA[sign check error]]></return_msg>" + "</xml> ";
				result(resXml);
				return;
			}
		} catch (IOException | DocumentException e) {
			logger.error("PaymentCallback Error:" + e.getMessage(), e);
		}
		resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[xml error]]></return_msg>"
				+ "</xml> ";
		result(resXml);
	}

	/**
	 * 配送订单
	 */
	public void bindNumber() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			if (user == null) {
				throw new WebSocketRuntimeException(SocketRuntimeException.INVALID_USER, "登陆超时");
			}
			if (!"2".equals(user.getType())) {
				throw new WebSocketRuntimeException(SocketRuntimeException.DEFAULT_ERROR_CODE, "您不是配送人员，无权配送订单");
			}
			JSONObject requestData = JSONObject.fromObject(IOUtils.toString(request.getInputStream()));
			JSONObject response = new JSONObject();

			int orderid = requestData.getInt("orderid");

			Order order = orderManager.getOrder(orderid);

			if (order == null) {
				throw new WebSocketRuntimeException(SocketRuntimeException.DEFAULT_ERROR_CODE, "找不到订单信息");
			}

			int deviceid = order.getDevice().getDeviceid();

			Device device = deviceManager.getDeviceById(deviceid);

			if (device == null) {
				throw new WebSocketRuntimeException(SocketRuntimeException.DEFAULT_ERROR_CODE, "找不到设备信息");
			}

			if (!device.getManagerid().equals(user.getId())) {
				throw new WebSocketRuntimeException(SocketRuntimeException.DEFAULT_ERROR_CODE, "您无权管理这个设备的订单");
			}

			String code = requestData.getString("code");
			order.setTransactionId(code);

			orderManager.addOrder(order);

			jsonResponse(response);
		} catch (SocketRuntimeException ex) {
			jsonError(ex.getMessage(), ex.getErrorCode());
		} catch (IOException ex) {
			jsonError(ex.getMessage(), 500);
		}
	}

	/**
	 * 开锁指令
	 */
	public void openLock() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			if (user == null) {
				throw new WebSocketRuntimeException(SocketRuntimeException.INVALID_USER, "登陆超时");
			}
			if (!"2".equals(user.getType())) {
				throw new WebSocketRuntimeException(SocketRuntimeException.DEFAULT_ERROR_CODE, "您不是配送人员，无权配送订单");
			}
			JSONObject requestData = JSONObject.fromObject(IOUtils.toString(request.getInputStream()));
			JSONObject response = new JSONObject();

			int orderid = requestData.getInt("orderid");

			Order order = orderManager.getOrder(orderid);

			if (order == null) {
				throw new WebSocketRuntimeException(SocketRuntimeException.DEFAULT_ERROR_CODE, "找不到订单信息");
			}
			int deviceid = order.getDevice().getDeviceid();

			Device device = deviceManager.getDeviceById(deviceid);

			if (device == null) {
				throw new WebSocketRuntimeException(SocketRuntimeException.DEFAULT_ERROR_CODE, "找不到设备信息");
			}

			if (!device.getManagerid().equals(user.getId())) {
				throw new WebSocketRuntimeException(SocketRuntimeException.DEFAULT_ERROR_CODE, "您无权管理这个设备");
			}
			// 检测订单的设备和扫码的设备一致
			if (deviceid != requestData.getInt("deviceid")) {
				throw new WebSocketRuntimeException(SocketRuntimeException.DEFAULT_ERROR_CODE, "您扫描的设备与订单所在设备不符合");
			}

			// 执行开锁指令
			OpenLockCallback callback = new OpenLockCallback(order.getDevice(), user, session);
			callback.setOnlyOpen(true);
			MainSocketThread socketThread = SocketThered.findByLatticeId(order.getDevice().getId());
			if (socketThread.addCallback(callback)) {
				callback.openLock();
			}

			jsonResponse(response);
		} catch (SocketRuntimeException ex) {
			jsonError(ex.getMessage(), ex.getErrorCode());
		} catch (IOException ex) {
			jsonError(ex.getMessage(), 500);
		}
	}

	/**
	 * 判断用户是否是还杆，根据用户主动发起和设备的各种状态
	 */
	public void scan() {
		HttpServletRequest request = ServletActionContext.getRequest();
		final HttpSession session = request.getSession();
		session.removeAttribute("usermessage");
		String postedData = null;
		try {
			final User user = (User) session.getAttribute("user");
			if (user == null) {
				throw new WebSocketRuntimeException(SocketRuntimeException.INVALID_USER, "请先登录");
			}

			if (StringUtil.isEmpty(user.getTelephone())) {
				throw new WebSocketRuntimeException(SocketRuntimeException.INVALID_USER_MOBILE, "请绑定您的手机号");
			}
			postedData = IOUtils.toString(request.getInputStream());
			final JSONObject data = JSONObject.fromObject(postedData);
			final String action = data.getString("action");

			final JSONObject response = new JSONObject();

			int latticeId = data.getInt("id");
			final Lattice lattice = deviceManager.getLatticeById(latticeId);
			final MainSocketThread socketThread = SocketThered.findByLatticeId(latticeId);
			// 寄存
			if (action.equals("save")) {
				// 检查用户选择的柜门类型与扫码的柜门是否一致
				int scale = data.getInt("scale");
				if (lattice.getType() != scale) {
					throw new WebSocketRuntimeException(SocketRuntimeException.CARD_NOTEXISTS, "请选择相通型号的柜门扫码", session);
				}

				OpenLockCallback callback = new OpenLockCallback(lattice, user, session);
				callback.setAction(action);
				if (socketThread.addCallback(callback)) {
					callback.openLock();
					response.put("status", 1);
				}
			} else if (action.equals("takeback")) {
				// 取回行李
				final Order order = orderManager.getOrder(data.getInt("orderid"));

				if (order == null) {
					throw new WebSocketRuntimeException(SocketRuntimeException.DEFAULT_ERROR_CODE, "抱歉，查询不到您的订单信息", session);
				}
				if (!user.equals(order.getUser())) {
					throw new WebSocketRuntimeException(SocketRuntimeException.DEFAULT_ERROR_CODE, "用户信息与订单信息不符", session);
				}
				if (lattice.equals(order.getDevice())) {
					throw new WebSocketRuntimeException(SocketRuntimeException.DEFAULT_ERROR_CODE, "订单信息与扫码信息不匹配", session);
				}
				OpenLockCallback openLockCallback = new OpenLockCallback(lattice, user, session);
				openLockCallback.setOrder(order);
				openLockCallback.setAction(action);
				if (socketThread.addCallback(openLockCallback)) {
					openLockCallback.openLock();
					response.put("status", 1);
				}
			}
			// 用户寄送
			else {
				final Order order = orderManager.getOrder(data.getInt("orderid"));
				OpenLockCallback openLockCallback = new OpenLockCallback(lattice, user, session);
				openLockCallback.setAction(action);
				openLockCallback.setOrder(order);
				openLockCallback.setData(data);
				if (socketThread.addCallback(openLockCallback)) {
					openLockCallback.openLock();
					response.put("status", 1);
				}
			}

			jsonResponse(response);
		} catch (SocketRuntimeException e) {
			jsonError(e.getMessage(), e.getErrorCode());
		} catch (IOException e) {
			jsonError(e.getMessage());
		}
	}

	// 查询地区
	public void queryAreas() {
		HttpServletRequest request = ServletActionContext.getRequest();
		FruitManager fruitManager = (FruitManager) ApplicationUtil.act.getBean("fruitManager");

		JSONObject data = new JSONObject();

		// 根据大小获取没有礼物的窗口
		List<Fruit> fruits = fruitManager.getAllFruitList();
		data.put("areas", JSONArray.fromObject(fruits));
		jsonResponse(data);

	}

	// 查询订单可以还回的格子窗口
	public void queryLattices() {
		HttpServletRequest request = ServletActionContext.getRequest();
		TradingOrderManager tradingOrderManager = (TradingOrderManager) ApplicationUtil.act.getBean("tradingOrderManager");
		int id = 0;
		try {
			String postedData = IOUtils.toString(request.getInputStream());
			JSONObject params = JSONObject.fromObject(postedData);
			id = params.getInt("orderid");
		} catch (IOException e) {
		}

		JSONObject data = new JSONObject();
		if (id > 0) {
			Order order = tradingOrderManager.getOrder(id);
			if (order != null) {
				// 获取礼物的大小规格
				int type = order.getIcport().getType();

				// 根据大小获取没有礼物的窗口
				DeviceManager deviceManager = (DeviceManager) ApplicationUtil.act.getBean("deviceManager");
				List<Lattice> lattices = deviceManager.getLatticeListByType(type, "0");
				data.put("lattices", JSONArray.fromObject(lattices));
				jsonResponse(data);
				return;
			}
		}

		jsonError("查询不到订单信息");
	}

	public void caculateOrder() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		GetPathCommon common = new GetPathCommon();
		int id = Integer.valueOf(request.getParameter("id"));

		try {
			if (user == null) {
				throw new WebSocketRuntimeException(SocketRuntimeException.INVALID_USER, "登陆超时");
			}

			Order order = orderManager.getOrder(id);

			if (order == null) {
				throw new WebSocketRuntimeException(SocketRuntimeException.DEFAULT_ERROR_CODE, "查询不到订单");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			order.setGivebacktime(sdf.format(new Date()));

			double totalPrice = orderManager.caculate(order);
			order.setTotalprice(String.valueOf(totalPrice));
			order.setState("1");
			orderManager.addOrder(order);

			JSONObject data = new JSONObject();
			jsonResponse(data);
		} catch (WebSocketRuntimeException ex) {
			jsonError(ex.getMessage(), ex.getErrorCode());
		}

	}

	public void queryOrder() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		GetPathCommon common = new GetPathCommon();

		try {

			String host = "";
			try {
				host = common.getCommonDir("host");
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (user == null) {
				throw new WebSocketRuntimeException(SocketRuntimeException.INVALID_USER, "登陆超时");
			}

			Order order = null;

			String _do = request.getParameter("do");

			if ("deliver".equals(_do)) {
				// 获取用户最后的寄送订单
				List<Criterion> conditions = new ArrayList<>();
				conditions.add(Restrictions.eq("user", user));
				conditions.add(Restrictions.eq("type", "3"));
				conditions.add(Restrictions.or(Restrictions.eq("state", "0"), Restrictions.eq("state", "1")));
				List<Order> orders = orderManager.getOrders(conditions);
				if (orders.size() > 0)
					order = orders.get(0);
			} else if ("save".equals(_do)) {
				// 获取用户最后的寄送订单
				List<Criterion> conditions = new ArrayList<>();
				conditions.add(Restrictions.eq("user", user));
				conditions.add(Restrictions.eq("type", "1"));
				conditions.add(Restrictions.ne("state", "2"));
				List<Order> orders = orderManager.getOrders(conditions);
				if (orders.size() > 0)
					order = orders.get(0);
			} else {
				int id = Integer.valueOf(request.getParameter("id"));

				order = orderManager.getOrder(id);
			}

			JSONObject data = new JSONObject();
			if (order != null) {
				ICPort icPort = order.getIcport();
				if (icPort != null) {
					order.getIcport().setLattice(null);
					order.getDevice().setIcport(null);
					String thumb = host + icPort.getThumb();
					icPort.setThumb(thumb);
				}
			}
			data.put("order", order);
			jsonResponse(data);

		} catch (WebSocketRuntimeException ex) {
			jsonError(ex.getMessage(), ex.getErrorCode());
		}

	}

	public void devices() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		DeviceManager deviceManager = (DeviceManager) ApplicationUtil.act.getBean("deviceManager");

		Map<String, String> map = new HashMap<>();

		List<Device> devices = deviceManager.getDeviceList(1, 100000000, map);

		JSONObject response = new JSONObject();
		response.put("devices", devices);
		jsonResponse(response);
	}

	// 获取当前用户的订单列表
	public void orders() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		GetPathCommon common = new GetPathCommon();
		String host = "";
		String state = "";
		JSONObject data = new JSONObject();

		try {
			host = common.getCommonDir("host");

			int page = 1;

			if (!StringUtils.isEmpty(request.getParameter("page"))) {
				page = Integer.valueOf(request.getParameter("page"));
			}

			if (page < 1) {
				page = 1;
			}

			if (!StringUtils.isEmpty(request.getParameter("state"))) {
				state = request.getParameter("state");
			}

			User user = (User) session.getAttribute("user");

			if (user == null) {
				// 报错
				throw new WebSocketRuntimeException(WebSocketRuntimeException.INVALID_USER, "登陆超时");
			}

			List<Device> devices = deviceManager.getDevicesByManagerid(user.getId());

			HashMap<String, Object> map = new HashMap<>();
			if (!StringUtils.isEmpty(state)) {
				map.put("state", state);
			}
			String f = request.getParameter("f");
			if ("delivery".equals(f)) {
				map.put("state", "2");
				map.put("type", "3");
			} else {
				map.put("user", user);
			}

			List<Order> orders = orderManager.getOrderList(page, 200, map);

			for (Order o : orders) {
				ICPort icPort = o.getIcport();
				if (icPort != null && !icPort.getThumb().startsWith("http")) {
					icPort.setLattice(null);
					String thumb = host + icPort.getThumb();
					icPort.setThumb(thumb);
				}
				o.getDevice().setIcport(null);
			}
			data.put("orders", JSONArray.fromObject(orders));
			jsonResponse(data);
		} catch (SocketRuntimeException ex) {
			jsonError(ex.getMessage(), ex.getErrorCode());
		} catch (Exception e) {
			jsonError(e.getMessage(), 500);
		}
	}

	public void queryMessage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		JSONObject message = (JSONObject) session.getAttribute("usermessage");

		if (message != null) {
			session.removeAttribute("usermessage");
			jsonResponse(message);
		} else {
			jsonError("no message");
		}
	}

	private void jsonResponse(JSONObject object) {
		if (!object.containsKey("status")) {
			object.put("status", 1);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(object.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}

	private void jsonError(String message, int status) {
		JSONObject res = new JSONObject();
		res.put("status", status);
		res.put("message", message);
		logger.error(message);
		jsonResponse(res);
	}

	private void jsonError(String message) {
		jsonError(message, 0);
	}

	void result(String content) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(content);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				out.close();
		}
	}
}
