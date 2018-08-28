package com.highguard.Wisdom.mgmt.dao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.highguard.Wisdom.mgmt.hibernate.beans.Trading;

@SuppressWarnings("unchecked")
@Repository
public class TradingDao {
	@Resource
	SessionFactory factory;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<Object[]> getTradingList(int page, int rows, Map map) {
		StringBuffer hql = new StringBuffer(
				"SELECT t.id,t.username,t.wx,t.balance,t.consumption,t.takeway,t.createtime,f.productname,t.fruitnum,d.name,l.lockid,t.status FROM trading t left join fruit f on t.fruitid=f.id left join device d on d.id=t.deviceid  left join lattice l on l.id=t.latticeid where 1=1 and t.consumption!='' ");
		if (map.get("wx") != null && !"".equals(map.get("wx"))) {
			hql.append(" and t.wx='" + map.get("wx") + "' ");
		}
		if (map.get("takeway") != null && !"".equals(map.get("takeway"))) {
			hql.append(" and t.takeway='" + map.get("takeway") + "' ");
		}
		if (map.get("ids") != null && !"".equals(map.get("ids"))) {
			hql.append(" and t.id='" + map.get("ids") + "' ");
		}
		if (map.get("deviceId") != null && !"".equals(map.get("deviceId"))) {
			hql.append(" and d.num='" + map.get("deviceId") + "' ");
		}
		if (map.get("username") != null && !"".equals(map.get("username"))) {
			hql.append(" and t.username like '%" + map.get("username") + "%' ");
		}
		if (null != map.get("fTime") && !"".equals(map.get("fTime"))) {
			hql.append(" and t.createtime >= '" + df.format(map.get("fTime"))
					+ "'");
		}
		if (null != map.get("eTime") && !"".equals(map.get("eTime"))) {
			hql.append(" and t.createtime <= '" + df.format(map.get("eTime"))
					+ "'");
		}
		if (map.get("fruitid") != null && !"".equals(map.get("fruitid"))) {
			hql.append(" and f.id='" + map.get("fruitid") + "' ");
		}
		if (map.get("status") != null && !"".equals(map.get("status"))) {
			hql.append(" and t.status='" + map.get("status") + "' ");
		}
		if (map.get("LatticeId") != null && !"".equals(map.get("LatticeId"))) {
			hql.append(" and l.lockid='" + map.get("LatticeId") + "' ");
		}
		hql.append("  order by t.createtime desc ");
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		if (page >= 0 && rows >= 0) {
			query.setFirstResult((page - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.list();
	}

	/**
	 * 获取数量
	 * 
	 * @param map
	 * @return
	 */
	public int getTradingListCount(Map map) {
		StringBuffer hql = new StringBuffer(
				"SELECT count(t.id) FROM trading t left join fruit f on t.fruitid=f.id left join device d on d.id=t.deviceid  left join lattice l on l.id=t.latticeid where 1=1  and t.consumption!=''  ");

		if (map.get("wx") != null && !"".equals(map.get("wx"))) {
			hql.append(" and t.wx='" + map.get("wx") + "'");
		}
		if (map.get("takeway") != null && !"".equals(map.get("takeway"))) {
			hql.append(" and t.takeway='" + map.get("takeway") + "' ");
		}
		if (map.get("ids") != null && !"".equals(map.get("ids"))) {
			hql.append(" and t.id='" + map.get("ids") + "' ");
		}
		if (map.get("deviceId") != null && !"".equals(map.get("deviceId"))) {
			hql.append(" and d.num='" + map.get("deviceId") + "' ");
		}
		if (null != map.get("fTime") && !"".equals(map.get("fTime"))) {
			hql.append(" and t.createtime >= '" + df.format(map.get("fTime"))
					+ "'");
		}
		if (null != map.get("eTime") && !"".equals(map.get("eTime"))) {
			hql.append(" and t.createtime <= '" + df.format(map.get("eTime"))
					+ "'");
		}
		if (map.get("username") != null && !"".equals(map.get("username"))) {
			hql.append(" and t.username like '%" + map.get("username") + "%' ");
		}
		if (map.get("fruitid") != null && !"".equals(map.get("fruitid"))) {
			hql.append(" and f.id='" + map.get("fruitid") + "' ");
		}
		if (map.get("LatticeId") != null && !"".equals(map.get("LatticeId"))) {
			hql.append(" and l.lockid='" + map.get("LatticeId") + "' ");
		}
		if (map.get("status") != null && !"".equals(map.get("status"))) {
			hql.append(" and t.status='" + map.get("status") + "' ");
		}
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return Integer.parseInt(query.uniqueResult().toString());
	}

	public Trading getTrading(Integer id) {
		return (Trading) factory.getCurrentSession().get(Trading.class, id);
	}

	public void updateTrading(Trading trading) {
		factory.getCurrentSession().update(trading);
	}

	public void saveTrading(Trading trading) {
		factory.getCurrentSession().save(trading);
	}

	public void deleteTrading(String ids) {

		StringBuffer hql = new StringBuffer("delete FROM trading where id in ("
				+ ids + ")  ");

		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		query.executeUpdate();
	}

}
