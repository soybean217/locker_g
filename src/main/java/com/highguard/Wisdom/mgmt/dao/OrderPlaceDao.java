package com.highguard.Wisdom.mgmt.dao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.highguard.Wisdom.mgmt.hibernate.beans.OrderPlace;
import com.highguard.Wisdom.mgmt.hibernate.beans.Trading;

@SuppressWarnings("unchecked")
@Repository
public class OrderPlaceDao {
	@Resource
	SessionFactory factory;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取列表
	 * 
	 * @param map
	 * @return
	 */
	public List<OrderPlace> getOrderPlaceList( Map map) {
		String sql = "select * from orderplace where 1=1";
		if(map.get("userid")!=null&&!"".equals(map.get("userid"))){
			sql += " and userid='"+map.get("userid")+"'";
		}
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				sql);
		query.addEntity(OrderPlace.class);
		return query.list();
	}

	
	public OrderPlace getOrderPlace(Integer id) {
		return  (OrderPlace) factory.getCurrentSession().get(OrderPlace.class, id);
	}


	public void saveOrderPlace(OrderPlace orderPlace) {
		factory.getCurrentSession().saveOrUpdate(orderPlace);
	}

	public void deleteOrderPlace(String ids) {

		StringBuffer hql = new StringBuffer("delete FROM orderplace where id in ("
				+ ids + ")  ");

		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		query.executeUpdate();
	}

}
