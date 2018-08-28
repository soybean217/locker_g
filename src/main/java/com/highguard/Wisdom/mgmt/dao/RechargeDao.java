package com.highguard.Wisdom.mgmt.dao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.highguard.Wisdom.mgmt.hibernate.beans.Fruit;
import com.highguard.Wisdom.mgmt.hibernate.beans.Recharge;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;

@SuppressWarnings("unchecked")
@Repository
public class RechargeDao {
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
	public List<Recharge> getRechargeList(int page, int rows, Map map) {
		StringBuffer hql = new StringBuffer("SELECT * FROM recharge where 1=1  ");
		if (null != map.get("num")
				&& !"".equals(map.get("num"))) {
			hql.append(" and uuid like '%" + map.get("num") + "%' ");
		}
		if (null != map.get("name")
				&& !"".equals(map.get("name"))) {
			hql.append(" and username  like '%" + map.get("name") + "%' ");
		}
		if (null != map.get("fTime") && !"".equals(map.get("fTime"))) {
			hql.append(" and createtime >= '" + df.format(map.get("fTime"))
					+ "'");
		}
		if (null != map.get("eTime") && !"".equals(map.get("eTime"))) {
			hql.append(" and createtime <= '" + df.format(map.get("eTime"))
					+ "'");
		}
		if (null != map.get("price") && !"".equals(map.get("price"))) {
			if(map.get("price").equals("1")){
				hql.append(" and price >0 ");
			}else{
				hql.append(" and price <0 ");
						
			}
			
		}
		hql.append("order by createtime desc");
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(Recharge.class);
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
	public int getRechargeListCount(Map map) {
		StringBuffer hql = new StringBuffer(
				"SELECT count(id) FROM recharge where 1=1  ");
		if (null != map.get("num")
				&& !"".equals(map.get("num"))) {
			hql.append(" and uuid like '%" + map.get("num") + "%' ");
		}
		if (null != map.get("name")
				&& !"".equals(map.get("name"))) {
			hql.append(" and username  like '%" + map.get("name") + "%' ");
		}
		if (null != map.get("fTime") && !"".equals(map.get("fTime"))) {
			hql.append(" and createtime >= '" + df.format(map.get("fTime"))
					+ "'");
		}
		if (null != map.get("eTime") && !"".equals(map.get("eTime"))) {
			hql.append(" and createtime <= '" + df.format(map.get("eTime"))
					+ "'");
		}
		if (null != map.get("price") && !"".equals(map.get("price"))) {
			if(map.get("price").equals("1")){
				hql.append(" and price >0 ");
			}else{
				hql.append(" and price <0 ");
						
			}
			
		}
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return Integer.parseInt(query.uniqueResult().toString());
	}


	public void saveRecharge(Recharge recharge) {
		factory.getCurrentSession().save(recharge);
	}



	

	public void deleteRecharge(String ids) {

		StringBuffer hql = new StringBuffer("delete FROM recharge where id in ("
				+ ids + ")  ");

		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		query.executeUpdate();
	}

	public Recharge getByOrderNumber(String out_trade_no) {
		String sql="select * from recharge where orderNumber='"+out_trade_no+"'";
		List<Recharge> list = factory.getCurrentSession().createSQLQuery(sql).addEntity(Recharge.class).list();
		if(null!= list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	
	public void updateRecharge(Recharge recharge){
		factory.getCurrentSession().update(recharge);
	}
	
}
