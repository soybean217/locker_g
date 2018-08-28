package com.highguard.Wisdom.mgmt.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.dao.OrderPlaceDao;
import com.highguard.Wisdom.mgmt.hibernate.beans.OrderPlace;
import com.highguard.Wisdom.mgmt.hibernate.beans.Recharge;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class OrderPlaceManager {
	@Resource
	OrderPlaceDao dao;


	/**
	 * 获取列表
	 * 
	 * @param map
	 * @return
	 */
	public List<OrderPlace> getOrderPlaceList( Map map) {
		return dao.getOrderPlaceList(map);
	}

	
	public OrderPlace getOrderPlace(Integer id) {
		return  dao.getOrderPlace(id);
	}


	public void saveOrderPlace(OrderPlace orderPlace) {
		dao.saveOrderPlace(orderPlace);
	}

	public void deleteOrderPlace(String id) {
		dao.deleteOrderPlace(id);
	}
    

}
