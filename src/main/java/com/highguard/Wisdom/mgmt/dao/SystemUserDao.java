package com.highguard.Wisdom.mgmt.dao;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.highguard.Wisdom.mgmt.hibernate.beans.SystemUser;

@SuppressWarnings("unchecked")
@Repository
public class SystemUserDao {
	@Resource
	SessionFactory factory;
	
	public SystemUser getUserByNameAndPassword(String name,String password){
		StringBuffer hql = new StringBuffer(
				"SELECT * FROM system_user where 1=1  ");
		hql.append(" and username = '" + name + "' ");
		hql.append(" and password = '" + password + "' ");

		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(SystemUser.class);
		if(query.list().size()>0){
			return (SystemUser) query.list().get(0);
		}
		return null;
	}
	
}
