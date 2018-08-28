package com.highguard.Wisdom.mgmt.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.highguard.Wisdom.mgmt.hibernate.beans.AppVersion;

@Repository
public class AppVersionDao {

	@Resource
	SessionFactory factory;
	
	
	public AppVersion save(AppVersion appVersion){
		factory.getCurrentSession().save(appVersion);
		return appVersion;
	}
	
	@SuppressWarnings("unchecked")
	public AppVersion getMaxVersion(){
		 List<AppVersion> list = factory.getCurrentSession()
			.createSQLQuery("select * from appversion order by appversion desc")
			.setResultTransformer(Transformers.aliasToBean(AppVersion.class))
			.setMaxResults(1)
			.list();
		 if(null!=list && list.size()>0){
			 return list.get(0);
		 }else{
			 return null;
		 }
	}
	
	
}
