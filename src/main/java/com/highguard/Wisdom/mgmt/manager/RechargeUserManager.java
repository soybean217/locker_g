package com.highguard.Wisdom.mgmt.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.dao.RechargeUserDao;
import com.highguard.Wisdom.mgmt.dao.UserDao;
import com.highguard.Wisdom.mgmt.hibernate.beans.FeedBack;
import com.highguard.Wisdom.mgmt.hibernate.beans.Markpassword;
import com.highguard.Wisdom.mgmt.hibernate.beans.RechargeUser;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class RechargeUserManager {
	@Resource
	RechargeUserDao rechargeUserDao;
	

	
	

	/**
	 * 获取用户列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<RechargeUser> getRechargeUserList(int page, int rows, Map map) {
		
		return  rechargeUserDao.getRechargeUserList(page, rows, map);
	}
	



	/**
	 * 获取用户数量
	 * 
	 * @param map
	 * @return
	 */
	public int getRechargeUserListCount(Map map) {
		
		return rechargeUserDao.getRechargeUserListCount(map);
	}

	public void saveRechargeUser(RechargeUser rechargeUser) {
		rechargeUserDao.saveRechargeUser(rechargeUser);
	}

	public RechargeUser getRechargeUserById(int id) {
		return rechargeUserDao.getRechargeUserById(id);
	}
	
	public void deleteUser(String ids) {

		rechargeUserDao.deleteUser(ids);
	}

}
