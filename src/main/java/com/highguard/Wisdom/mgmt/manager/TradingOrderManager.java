package com.highguard.Wisdom.mgmt.manager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import com.highguard.Wisdom.mgmt.hibernate.beans.*;
import com.highguard.Wisdom.struts.common.GetPathCommon;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.dao.TradingOrderDao;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class TradingOrderManager {
	@Resource
	TradingOrderDao dao;

	/// ------- 主表
	public double caculate(Order order) {
		GetPathCommon common = new GetPathCommon();
		double price = 0.0;

		if( order.getIcport() != null ){
			price = order.getIcport().getPrice();
		}
		else{
			price = Double.valueOf(order.getDevice().getPrice());
		}
		boolean oneHourPromotion = false;
		int oneMins = 1000 * 60;

		if (price == 0) {
			try {
				oneHourPromotion = Boolean.parseBoolean(common.getCommonDir("onehourPromotion"));
				price = Double.parseDouble(common.getCommonDir("price"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int count = getCountByUser(order.getUser());

		Timestamp st = Timestamp.valueOf(order.getCreatetime());
		Timestamp et = Timestamp.valueOf(order.getGivebacktime());

		//多少个半小时
		long seconds = et.getTime() - st.getTime();
		long miniutes = seconds % oneMins == 0 ? seconds / oneMins : (seconds / oneMins + 1);

		long halfHours = miniutes % 30 == 0 ? miniutes / 30 : (miniutes / 30 + 1);

		if (halfHours <= 0) {
			halfHours = 1;
		}
		double charge = 0;
		if (count == 1) {
			//首次一小时之内免费
			if (halfHours <= 2) {
				halfHours = 0;
			} else {
				halfHours = halfHours - 2;
			}
		}
		charge = halfHours * price;
		BigDecimal bd = new BigDecimal(charge);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		charge = bd.doubleValue();

		order.setFreight(String.valueOf(miniutes));
		return charge;
	}

	public String getOrderIdByUUId() {
		int machineId = 1;//最大支持1-9个集群机器部署
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {//有可能是负数
			hashCodeV = -hashCodeV;
		}
		// 0 代表前面补充0
		// 4 代表长度为4
		// d 代表参数为正数型
		return machineId + String.format("%015d", hashCodeV);
	}

	public Order getLastNotPayOrder(User user) {
		return dao.getLastNotPayOrder(user);
	}
	public Order getLastNotPayOrderByType(User user,String type) {
		return dao.getLastNotPayOrderByType(user, type);
	}

	public Order getOrderByUserAndLattice(User user, Lattice lattice){
		return dao.getOrderByUserAndLattice(user, lattice);
	}

	public int getCountByUser(User user) {
		return dao.getCountByUser(user);
	}

	public int getOrderListCount(Map map) {
		return dao.getOrderListCount(map);
	}

	public List<Order> getOrderList(int page, int rows, Map map) {
		return dao.getOrderList2(page, rows, map);
	}

	public List<Order> getOrderList(Map map) {
		return dao.getOrderList(map);
	}

	public void addOrder(Order order) {
		dao.addOrder(order);
	}

	public void deleteOrder(int id) {
		dao.deleteOrder(id);
	}

	public Order getOrder(int id) {
		return dao.getOrder(id);
	}

	public List<Order> getOrders(List<Criterion> conditions){
		return dao.getOrders(conditions);
	}

	public Order getOrderByIcport(ICPort id) {
		return dao.getOrderByIcport(id);
	}

	public Order getOrderByOrdersn(String id) {
		return dao.getOrderByOrdersn(id);
	}
///----------- 辅助表

	/**
	 * 获取列表
	 *
	 * @param map
	 * @return
	 */
	public List<TradingOrder> getTradingOrderList(Map map) {
		return dao.getTradingOrderList(map);
	}

	public void addTradingOrder(TradingOrder tradingOrder) {
		dao.addTradingOrder(tradingOrder);
	}

	public void updateTradingOrder(TradingOrder tradingOrder) {
		dao.updateTradingOrder(tradingOrder);
	}

	public void deleteTradingOrder(int id) {
		dao.deleteTradingOrder(id);
	}

	public void deleteTradingOrderById(int id) {
		dao.deleteTradingOrderById(id);
	}

	public TradingOrder getTradingOrder(int id) {
		return dao.getTradingOrder(id);
	}

}
