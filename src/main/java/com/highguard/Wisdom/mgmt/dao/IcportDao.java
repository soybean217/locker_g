package com.highguard.Wisdom.mgmt.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.springframework.stereotype.Repository;

import com.highguard.Wisdom.mgmt.hibernate.beans.ICPort; 

@SuppressWarnings("unchecked")
@Repository
public class IcportDao {
	@Resource
	SessionFactory factory;


	
	
	/**
	 * 获取列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<ICPort> getICPortList(int page, int rows, Map map) {
		StringBuffer hql = new StringBuffer("SELECT * FROM icport where 1=1  ");
	hql.append(" order by createtime desc ");
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(ICPort.class);
		if (page >= 0 && rows >= 0) {
			query.setFirstResult((page - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.list();
	}


	public ICPort get(int id){
		Criteria query = factory.getCurrentSession().createCriteria(ICPort.class);
		query.add(Expression.eq("id", id));

		return (ICPort)query.uniqueResult();
	}
	public ICPort getByLockid(String lockid){
		Criteria query = factory.getCurrentSession().createCriteria(ICPort.class);
		query.add(Expression.eq("lattice", lockid));

		return (ICPort)query.uniqueResult();
	}

	public ICPort getByCard(String cardId){

		Criteria query = factory.getCurrentSession().createCriteria(ICPort.class);
		query.add(Expression.eq("iccard", cardId));

		if(query.list().size()>0){
			return (ICPort) query.list().get(0);
		}
		return null;
	}



	/**
	 * 获取数量
	 * 
	 * @param map
	 * @return
	 */
	public int getICPortListCount(Map map) {
		StringBuffer hql = new StringBuffer(
				"SELECT count(id) FROM icport where 1=1  ");
		
		if(map.get("card")!=null&&!"".equals(map.get("card"))){
			
			hql.append("  and iccard='"+map.get("card")+"' ");
		}
		
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return Integer.parseInt(query.uniqueResult().toString());
	}

	
	

	public void saveICPort(ICPort user) {
		factory.getCurrentSession().saveOrUpdate(user);
	}

	
	public void deleteICPort(String ids) {

		StringBuffer hql = new StringBuffer("delete FROM icport where id in ("
				+ ids + ")  ");

		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		query.executeUpdate();
	}
	
}
