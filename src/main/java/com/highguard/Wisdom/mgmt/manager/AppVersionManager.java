package com.highguard.Wisdom.mgmt.manager;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.dao.AppVersionDao;
import com.highguard.Wisdom.mgmt.hibernate.beans.AppVersion;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class AppVersionManager {
	@Resource
	AppVersionDao dao;
	
	public AppVersion addNewVersion(AppVersion version){
		return dao.save(version);
	}
	
	public AppVersion findMaxVersion(){
		return dao.getMaxVersion();
	}

}
