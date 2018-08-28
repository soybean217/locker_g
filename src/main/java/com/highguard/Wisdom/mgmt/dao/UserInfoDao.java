package com.highguard.Wisdom.mgmt.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.highguard.Wisdom.mgmt.hibernate.beans.UserInfo;

@SuppressWarnings("unchecked")
@Repository
public class UserInfoDao {
	@Resource
	SessionFactory factory;
	
	
	//-----------亲情号相关-------

	public UserInfo getUserInfoByPhone(String phone){
		StringBuffer hql = new StringBuffer("SELECT * FROM userinfo where phone='"+phone+"'  ");
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(UserInfo.class);
		if(query.list().size()>0){
			return  (UserInfo) query.list().get(0);
		}
		return null;
	}

	public List<UserInfo> getUserInfoListByUserId(int userId){
		StringBuffer hql = new StringBuffer("SELECT * FROM userinfo where userId="+userId);
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(UserInfo.class);
		if(query.list().size()>0){
			return query.list();
		}
		return null;
	}




	/**
	 * 判断亲情号是否是 主账户
	 * 
	 * @param map
	 * @return
	 */
	public boolean  getUserListCount(Map map) {
		StringBuffer hql = new StringBuffer(
				"SELECT count(id) FROM user where 1=1  ");
	
		if (null != map.get("phone")
				&& !"".equals(map.get("phone"))) {
			hql.append(" and telephone = '" + map.get("phone") + "' ");
		}
		
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		int res  =  Integer.parseInt(query.uniqueResult().toString());
		if(res>0){
			return true;
		}else{
			return false;
		}
		
	}

	/**
	 * 判断该账户是不是已经为亲情号码了
	 * @param map
	 * @return
	 */
	 public boolean getUserInfoListCount(Map map){
		StringBuffer hql = new StringBuffer(
				"SELECT * FROM userinfo where 1=1  ");
		if (null != map.get("phone")
				&& !"".equals(map.get("phone"))) {
			hql.append(" and phone = '" + map.get("phone") + "' ");
		   }
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		List list = query.list();
		
		if(null != list && list.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	public void saveUserInfo(UserInfo userInfo) {
		factory.getCurrentSession().save(userInfo);
	}

	public void updateUserInfo(UserInfo userInfo) {
		factory.getCurrentSession().update(userInfo);
	}

	public UserInfo getUserInfoById(int id) {
		return (UserInfo) factory.getCurrentSession().get(UserInfo.class, id);
	}
	

	public void deleteUserInfo(String ids) {

		StringBuffer hql = new StringBuffer("delete FROM userinfo where id in ("
				+ ids + ")  ");

		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		query.executeUpdate();
	}
	

	/**
	 * 判断该账户亲情号码 是不是超过3个
	 * 	 * @param map
	 * @return
	 */
	 public boolean getUserInfoListCountOverThree(Map map){
		StringBuffer hql = new StringBuffer(
				"SELECT * FROM userinfo where 1=1  ");
		if (null != map.get("userId")
				&& !"".equals(map.get("userId"))) {
			hql.append(" and userId = '" + map.get("userId") + "' ");
		   }
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		List list = query.list();
		if(null!=list && list.size()>=3){
			return true;
		}else{
			return false;
		}
	}
	 
	 

		/**
		 * 根据主表用户ID 获取他的所有亲情号码
		 * @param map
		 * @return
		 */
		 public List<UserInfo> getUserInfoListByUserId(String userId){
			StringBuffer hql = new StringBuffer(
					"SELECT * FROM userinfo where 1=1  ");
			hql.append(" and userId =" +userId);
			 
			Query query = factory.getCurrentSession()
					.createSQLQuery(hql.toString()).addEntity(UserInfo.class);
			 return query.list();
		}
	 
	 //-------------发送验证码相关---------------
		 
	public UserInfo getUserInfoByToken(String token){
		Query query = factory.getCurrentSession().createSQLQuery("select * from userinfo where loginToken='"+token+"'").addEntity(UserInfo.class);
		List<UserInfo> list = query.list();
		if(null!=list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	
	
}
