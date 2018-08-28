package com.highguard.Wisdom.mgmt.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.highguard.Wisdom.mgmt.hibernate.beans.FeedBack;
import com.highguard.Wisdom.mgmt.hibernate.beans.Markpassword;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;

@SuppressWarnings("unchecked")
@Repository
public class UserDao {
	@Resource
	SessionFactory factory;

	public User getUserByCard(String card){
		StringBuffer hql = new StringBuffer("SELECT * FROM user where card='"+card+"'  ");
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(User.class);
		if(query.list().size()>0){
			return  (User) query.list().get(0);
		}
		return null;
	}
	
	
	public void saveFeedBack(FeedBack feedBack){
		factory.getCurrentSession().save(feedBack);
	}
	
	
	/**
	 * 获取FeedBack列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<FeedBack> getFeedBackList(int page, int rows, Map map) {
		StringBuffer hql = new StringBuffer("SELECT * FROM feedback where 1=1  ");
		hql.append(" order by createtime desc");
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(FeedBack.class);
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
	public int getFeedBackListCount(Map map) {
		StringBuffer hql = new StringBuffer("SELECT count(id) FROM feedback where 1=1  ");
		Query query = factory.getCurrentSession().createSQLQuery(hql.toString());
		return Integer.parseInt(query.uniqueResult().toString());
	}
	/**
	 * 获取用户列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<User> getUserList(int page, int rows, Map map) {
		StringBuffer hql = new StringBuffer("SELECT * FROM user where 1=1  ");
		if (null != map.get("useNameSearch")
				&& !"".equals(map.get("useNameSearch"))) {
			hql.append(" and name like '" + map.get("useNameSearch") + "%' ");
		}
		if (null != map.get("cardNameSearch")
				&& !"".equals(map.get("cardNameSearch"))) {
			hql.append(" and card like '" + map.get("cardNameSearch") + "%' ");
		}
		if (null != map.get("phoneSearch")
				&& !"".equals(map.get("phoneSearch"))) {
			hql.append(" and telephone like '" + map.get("phoneSearch") + "%' ");
		}
		
		
		if (null != map.get("uuidSearch")
		&& !"".equals(map.get("uuidSearch"))) {
	hql.append(" and uuid like '" + map.get("uuidSearch") + "%' ");
}
		if (null != map.get("card")
				&& !"".equals(map.get("card"))) {
			hql.append(" and card ='" + map.get("card") + "' ");
		}

		hql.append(" order by id desc");
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(User.class);
		if (page >= 0 && rows >= 0) {
			query.setFirstResult((page - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.list();
	}
	
	/**
	 * 获取用户列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<User> getUserList(Map<String,String> map) {
		StringBuffer hql = new StringBuffer("SELECT * FROM user where 1=1  ");
		if (null != map.get("phone")
				&& !"".equals(map.get("phone"))) {
			hql.append(" and telephone like '" + map.get("phone") + "%' ");
		}
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(User.class);
		
		return query.list();
	}


	/**
	 * 获取用户数量
	 * 
	 * @param map
	 * @return
	 */
	public int getUserListCount(Map map) {
		StringBuffer hql = new StringBuffer(
				"SELECT count(id) FROM user where 1=1  ");
		if (null != map.get("useNameSearch")
				&& !"".equals(map.get("useNameSearch"))) {
			hql.append(" and name like '" + map.get("useNameSearch") + "%' ");
		}
		if (null != map.get("cardNameSearch")
				&& !"".equals(map.get("cardNameSearch"))) {
			hql.append(" and card like '" + map.get("cardNameSearch") + "%' ");
		}
		if (null != map.get("uuidSearch")
				&& !"".equals(map.get("uuidSearch"))) {
			hql.append(" and uuid like '" + map.get("uuidSearch") + "%' ");
		}
		if (null != map.get("card")
				&& !"".equals(map.get("card"))) {
			hql.append(" and card ='" + map.get("card") + "' ");
		}
		if (null != map.get("phoneSearch")
				&& !"".equals(map.get("phoneSearch"))) {
			hql.append(" and telephone like '" + map.get("phoneSearch") + "%' ");
		}
		
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return Integer.parseInt(query.uniqueResult().toString());
	}

	public User getUserByWx(String wx){
		StringBuffer hql = new StringBuffer(
				"SELECT * FROM user where 1=1  ");
			hql.append(" and telephone = '" + wx + "' ");

			SQLQuery query = factory.getCurrentSession().createSQLQuery(
					hql.toString());
			query.addEntity(User.class);
			if(query.list().size()>0){
				return (User) query.list().get(0);
			}
		return null;
	}

	public User getUserByOpenid(String openid){
		StringBuffer hql = new StringBuffer(
				"SELECT * FROM user where 1=1  ");
		hql.append(" and openid = '" + openid + "' ");

		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(User.class);
		if(query.list().size()>0){
			return (User) query.list().get(0);
		}
		return null;
	}

	public int getUserByNameAndWx(String username,String wx){
		StringBuffer hql = new StringBuffer(
				"SELECT count(id) FROM user where 1=1  ");
			hql.append(" and wx = '" + wx + "' ");
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return Integer.parseInt(query.uniqueResult().toString());
	}

	public void saveUser(User user) {
		if( user.getId() == null ){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			user.setCreatetime(sdf.format(new Date()));
		}
		factory.getCurrentSession().save(user);
	}

	public void updateUser(User user) {
		factory.getCurrentSession().update(user);
	}

	public User getUserById(int id) {
		return (User) factory.getCurrentSession().get(User.class, id);
	}
	

	public void deleteUser(String ids) {

		StringBuffer hql = new StringBuffer("delete FROM user where id in ("
				+ ids + ")  ");

		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		query.executeUpdate();
	}
	
	public Markpassword getMarkpassword(String type){
		StringBuffer hql = new StringBuffer(
				"SELECT * FROM markpassword where 1=1  ");
			hql.append(" and type = '" + type + "' ");

			SQLQuery query = factory.getCurrentSession().createSQLQuery(
					hql.toString());
			query.addEntity(Markpassword.class);
			if(query.list().size()>0){
				return (Markpassword) query.list().get(0);
			}
		return null;
	}
	
	public void updateMarkPassword(Markpassword markpassword){
		factory.getCurrentSession().saveOrUpdate(markpassword);
	}
	
	public User getUserByPhone(String phone){
		StringBuffer hql = new StringBuffer(
				"SELECT * FROM user where 1=1  ");
			hql.append(" and telephone = '" + phone + "' ");

			SQLQuery query = factory.getCurrentSession().createSQLQuery(
					hql.toString());
			query.addEntity(User.class);
			if(query.list().size()>0){
				return (User) query.list().get(0);
			}else{
				return null;
			}
	}
//	public boolean checkLoginToken(String token){
//
//		try{
//			Query query = factory.getCurrentSession().createQuery("from user where loginToken='"+token+"'");
//			List list = query.list();
//			if(null!=list && list.size()>0){
//				return true;
//			}else{
//				return false;
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return false;
//	}
	
	public User getUserByToken(String token){
		Query query = factory.getCurrentSession().createSQLQuery("select * from user where loginToken='"+token+"'").addEntity(User.class);
		List<User> list = query.list();
		if(null!=list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}
