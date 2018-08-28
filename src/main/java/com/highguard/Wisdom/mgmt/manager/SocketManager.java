package com.highguard.Wisdom.mgmt.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.dao.SocketLogDao;
import com.highguard.Wisdom.mgmt.hibernate.beans.SocketLog;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class SocketManager {
	@Resource
	SocketLogDao socketLogDao;
	public List<SocketLog> getSocketLogList(int page, int rows, Map map) {
		
		return socketLogDao.getSocketLogList(page, rows, map);
	}
	
	/**
	 * 获取用户数量
	 * 
	 * @param map
	 * @return
	 */
	public int getSocketLogListCount(Map map) {
		 
		return socketLogDao.getSocketLogListCount(map);
	}


	public void saveSocketLog(SocketLog socketLog) {
		socketLogDao.saveSocketLog(socketLog);
	}
	
}
