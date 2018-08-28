package com.highguard.Wisdom.mgmt.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.highguard.Wisdom.mgmt.hibernate.beans.RechargeUser;

@SuppressWarnings("unchecked")
@Repository
public class RechargeUserDao {
	@Resource
	SessionFactory factory;


	
	
	
	

	/**
	 * 获取用户列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<RechargeUser> getRechargeUserList(int page, int rows, Map map) {
		StringBuffer hql = new StringBuffer("SELECT * FROM RechargeUser where 1=1  ");
		if(map.get("name")!=null&&!"".equals(map.get("name"))){
			hql.append(" and name = '"+map.get("name")+"' ");
		}
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(RechargeUser.class);
		if (page >= 0 && rows >= 0) {
			query.setFirstResult((page - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.list();
	}
	


	/**
	 * 获取用户数量
	 * 
	 * @param map
	 * @return
	 */
	public int getRechargeUserListCount(Map map) {
		StringBuffer hql = new StringBuffer(
				"SELECT count(id) FROM rechargeuser where 1=1  ");
		
		
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return Integer.parseInt(query.uniqueResult().toString());
	}

	public void saveRechargeUser(RechargeUser rechargeUser) {
		factory.getCurrentSession().saveOrUpdate(rechargeUser);
	}

	public RechargeUser getRechargeUserById(int id) {
		return (RechargeUser) factory.getCurrentSession().get(RechargeUser.class, id);
	}
	
	public void deleteUser(String ids) {

		StringBuffer hql = new StringBuffer("delete FROM rechargeuser where id in ("
				+ ids + ")  ");

		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		query.executeUpdate();
	}
	}
