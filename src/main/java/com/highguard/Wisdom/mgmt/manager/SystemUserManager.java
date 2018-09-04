package com.highguard.Wisdom.mgmt.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.dao.SystemUserDao;
import com.highguard.Wisdom.mgmt.hibernate.beans.SystemUser;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class SystemUserManager {
	@Resource
	SystemUserDao systemUserDao;

	public SystemUser getUserByNameAndPassword(String name,String password){
	
		return systemUserDao.getUserByNameAndPassword(name,password);
	}
}
