package com.highguard.Wisdom.mgmt.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;

import com.highguard.Wisdom.mgmt.hibernate.beans.Markpassword;
import com.highguard.Wisdom.mgmt.hibernate.beans.SmsRecord;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;

@SuppressWarnings("unchecked")
@Repository
public class SmsRecordDao {
	@Resource
	SessionFactory factory;

	public void save(SmsRecord smsRecord){
		Serializable save = factory.getCurrentSession().save(smsRecord);
	}
	
	public SmsRecord getSmsRecordByPhoneAndYzm(String phoneNumber,String yzm){
		SQLQuery createSQLQuery = factory.getCurrentSession().createSQLQuery("select * from sms_record where "
				+ " yzmCode="+yzm
				+ " and receiverNumber="+phoneNumber
				+ " order by createTime desc limit 1").addEntity(SmsRecord.class);
		List<SmsRecord> list = createSQLQuery.list();
		if(null != list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
}
