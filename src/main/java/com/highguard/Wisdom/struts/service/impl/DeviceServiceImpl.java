package com.highguard.Wisdom.struts.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Expression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.hibernate.beans.Device;
import com.highguard.Wisdom.struts.service.DeviceService;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class DeviceServiceImpl implements DeviceService{

	@Resource
	SessionFactory factory;
	
	@Override
	public List<Device> getDeviceList(String location) {
		Session session = factory.getCurrentSession();
		Criteria criteria = session.createCriteria(Device.class);
		if(null!=location){
			criteria.add(Expression.like("location", "%"+location+"%"));
		}
		List <Device> list = criteria.list();
		return list;
	}
}
