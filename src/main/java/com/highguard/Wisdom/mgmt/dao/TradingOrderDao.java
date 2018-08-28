package com.highguard.Wisdom.mgmt.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.highguard.Wisdom.mgmt.hibernate.beans.*;
import org.bouncycastle.asn1.isismtt.x509.Restriction;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.highguard.Wisdom.util.BasePath;

@SuppressWarnings("unchecked")
@Repository
public class TradingOrderDao {

	@Resource
	SessionFactory factory;
	////主表
	
	public int getOrderListCount( Map map){
		String sql = "select count(*) from orderr o where 1=1 ";
		if(map.get("deciceId")!=null&&!"".equals(map.get("deciceId"))){
			sql+=" and o.deviceid='"+map.get("deciceId")+"'";
		}
		Query query = factory.getCurrentSession()
				.createSQLQuery(sql);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	public Order getOrderByUserAndLattice(User user, Lattice lattice){
		Criteria query = factory.getCurrentSession().createCriteria(Order.class);
		query.add(Restrictions.or(Restrictions.eq("state","0"), Restrictions.eq("state","1")));
		if( user != null ){
			query.add(Restrictions.eq("user", user));
			query.add(Restrictions.eq("type", "3"));
		}
		query.add(Restrictions.eq("device", lattice));

		List<Order> result = query.list();
		if( result.size() > 0 ){
			return result.get(0);
		}
		return null;
	}

	public Order getLastNotPayOrderByType(User user,String type) {
		Criteria query = factory.getCurrentSession().createCriteria(Order.class);
		query.add(Restrictions.or(Restrictions.eq("state", "0"), Restrictions.eq("state", "1")));
		query.add(Restrictions.eq("user", user));
		query.add(Restrictions.eq("type", type));

		List<Order> result = query.list();
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	public Order getLastNotPayOrder(User user){
		Criteria query = factory.getCurrentSession().createCriteria(Order.class);
		query.add(Restrictions.or(Restrictions.eq("state","0"), Restrictions.eq("state","1")));
		query.add(Restrictions.eq("user", user));
		query.add(Restrictions.eq("type", "1"));

		List<Order> result = query.list();
		if( result.size() > 0 ){
			return result.get(0);
		}
		return null;
	}

	public List<Order> getOrders(List<Criterion> conditions){
		Criteria query = factory.getCurrentSession().createCriteria(Order.class);
		for (Criterion c : conditions){
			query.add(c);
		}
		return query.list();
	}


	public int getCountByUser(User user){
		int count = 0;
		Criteria query = factory.getCurrentSession().createCriteria(Order.class);
		query.add(Restrictions.eq("user", user));
		count = query.list().size();
		return count;
	}
	
	/**
	 * 获取列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<Order> getOrderList(int page, int rows, Map map) {
		String sql = "select o.* from orderr o where 1=1 ";
		
		if(map.get("deciceId")!=null&&!"".equals(map.get("deciceId"))){
			sql+=" and o.deviceid='"+map.get("deciceId")+"'";
		}
		sql+="order by o.id desc  ";
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				sql);
		 query.addEntity(Order.class);
		if (page >= 0 && rows >= 0) {
			query.setFirstResult((page - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.list();
	}
	
	public List<Order> getOrderList (Map map) {
		String sql = "select o.* from orderr o where 1=1 ";
		if(map.get("id")!=null&&!"".equals(map.get("id"))){
			sql +=" and o.id="+map.get("id");
		}
		if(map.get("usertype")!=null&&!"".equals(map.get("usertype"))){
				
			if("0".equals(map.get("usertype")))	{//主用户
				if(map.get("userid")!=null&&!"".equals(map.get("userid"))){
					sql +=" and (o.userid="+map.get("userid")+" and o.type='0') or  (type='1' and userid in(select id from userinfo where userId="+map.get("userid")+"))";
				}
			}
			if("1".equals(map.get("usertype")))	{//亲情号
				if(map.get("userid")!=null&&!"".equals(map.get("userid"))){
					sql +=" and o.userid="+map.get("userid")+" and o.type='1' ";
				///sql +=" and o.id in ( select orderid from tradingorder where userid = '"+map.get("userid")+"'  )";
				}
			}
		}
		if(map.get("state")!=null&&!"".equals(map.get("state"))){
			sql +=" and o.state="+map.get("state");
		}
		sql+=" order by o.id desc";
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				sql);
		 query.addEntity(Order.class);
		return query.list();
	}

	public List<Order> getOrderList2(int page, int rows, Map map){
	    Criteria query = factory.getCurrentSession().createCriteria(Order.class);
	    for(Object  key:map.keySet()){
			query.add(Expression.eq(key.toString(), map.get(key)));
		}
		query.add(Expression.ne("type", "2"));
		query.addOrder(org.hibernate.criterion.Order.desc("id"));
	    query.setFirstResult((page-1)*rows);
	    query.setMaxResults(rows);
	    return query.list();
	}
	
	public void addOrder(Order order){
		factory.getCurrentSession().saveOrUpdate(order);
	}
	
	public Order getOrder(int id){
		return (Order) factory.getCurrentSession().get(Order.class	, id);
	}

	public Order getOrderByIcport(ICPort port){

		Criteria query = factory.getCurrentSession().createCriteria(Order.class);
		query.add(Restrictions.eq("icport", port));
		query.add(Restrictions.eq("state", "0"));

		if(query.list().size()>0){
			return (Order) query.list().get(0);
		}
		return null;
	}

	public Order getOrderByOrdersn(String ordersn){

		Criteria query = factory.getCurrentSession().createCriteria(Order.class);
		query.add(Restrictions.eq("ordersn", ordersn));

		if(query.list().size()>0){
			return (Order) query.list().get(0);
		}
		return null;
	}
	public void deleteOrder(int id){
		 StringBuffer hql = new StringBuffer("delete FROM orderr where id in ("
					+ id + ")  ");

			Query query = factory.getCurrentSession()
					.createSQLQuery(hql.toString());
			query.executeUpdate();
	}
	
	///// --------------------- 辅助表
	/**
	 * 获取列表
	 * 
	 * @param map
	 * @return
	 */
	public List<TradingOrder> getTradingOrderList (Map map) {
		String sql = "select id,orderid,fruitname,latticeid,copies,price,userid,place,texture,"
				+ "CONCAT('"+BasePath.baseUrl+"',imageurl) as imageurl,place "
				+ "from tradingorder where 1=1 ";
		if(map.get("userid")!=null&&!"".equals(map.get("userid"))){
			sql +=" and userid="+map.get("userid");
		}
		if(map.get("phone")!=null&&!"".equals(map.get("phone"))){
			sql +=" and userid="+map.get("userid");
		}
		if(map.get("state")!=null&&!"".equals(map.get("state"))){
			sql +=" and state="+map.get("state");
		}
		if(map.get("orderid")!=null&&!"".equals(map.get("orderid"))){
			sql +=" and orderid="+map.get("orderid");
		}
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				sql);
		 query.addEntity(TradingOrder.class);
		return query.list();
	}

	 public void addTradingOrder(TradingOrder tradingOrder){
		 factory.getCurrentSession().save(tradingOrder);
	 }
	 
	 public void updateTradingOrder(TradingOrder tradingOrder){
		 factory.getCurrentSession().update(tradingOrder);
	 }
	 
	 public void deleteTradingOrder(int ids){
		 StringBuffer hql = new StringBuffer("delete FROM tradingorder where orderid in ("
					+ ids + ")  ");

			Query query = factory.getCurrentSession()
					.createSQLQuery(hql.toString());
			query.executeUpdate();
	 }
	 
	 public void deleteTradingOrderById(int id){
		 StringBuffer hql = new StringBuffer("delete FROM tradingorder where id ="+ id);
			Query query = factory.getCurrentSession()
					.createSQLQuery(hql.toString());
			query.executeUpdate();
	 }
	 
	
		public TradingOrder getTradingOrder(int id){
			return (TradingOrder) factory.getCurrentSession().get(TradingOrder.class, id);
		}
}
