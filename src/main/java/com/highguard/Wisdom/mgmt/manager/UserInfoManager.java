package com.highguard.Wisdom.mgmt.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.dao.UserDao;
import com.highguard.Wisdom.mgmt.dao.UserInfoDao;
import com.highguard.Wisdom.mgmt.hibernate.beans.Markpassword;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.hibernate.beans.UserInfo;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class UserInfoManager {
	@Resource
	UserInfoDao userInfoDao;

	public UserInfo getUserInfoByPhone(String phone) {

		return userInfoDao.getUserInfoByPhone(phone);
	}

	/**
	 * 判断亲情号是否是 主账户
	 * 
	 * @param map
	 * @return
	 */
	public boolean getUserListCount(Map map) {
		return userInfoDao.getUserListCount(map);

	}

	/**
	 * 判断该账户是不是已经为亲情号码了
	 * 
	 * @param map
	 * @return
	 */
	public boolean getUserInfoListCount(Map map) {
		return userInfoDao.getUserInfoListCount(map);
	}

	public void saveUserInfo(UserInfo userInfo) {
		userInfoDao.saveUserInfo(userInfo);
	}

	public void updateUserInfo(UserInfo userInfo) {
		userInfoDao.updateUserInfo(userInfo);
	}

	public UserInfo getUserInfoById(int id) {
		return userInfoDao.getUserInfoById(id);
	}

	public void deleteUserInfo(String ids) {
		userInfoDao.deleteUserInfo(ids);
	}

	/**
	 * 判断该账户亲情号码 是不是超过3个 * @param map
	 * 
	 * @return
	 */
	public boolean getUserInfoListCountOverThree(Map map) {
		return userInfoDao.getUserInfoListCountOverThree(map);
	}

	/**
	 * 根据主表用户ID 获取他的所有亲情号码
	 * 
	 * @param map
	 * @return
	 */
	public List<UserInfo> getUserInfoListByUserId(String userId) {
		return userInfoDao.getUserInfoListByUserId(userId);
	}
}
