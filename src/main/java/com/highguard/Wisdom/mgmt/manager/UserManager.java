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
import com.highguard.Wisdom.mgmt.hibernate.beans.FeedBack;
import com.highguard.Wisdom.mgmt.hibernate.beans.Markpassword;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class UserManager {
	@Resource
	UserDao userDao;

	public User getUserByCard(String card){
	
		return userDao.getUserByCard(card);
	}
	public List<User> getUserList(int page, int rows, Map map) {
		return userDao.getUserList(page, rows, map);
	}
	
	public List<User> getUserList(Map map) {
		return userDao.getUserList(map);
	}

	public int getUserCount(Map map) {
		return userDao.getUserListCount(map);
	}

	public void saveUser(User user) {
		userDao.saveUser(user);
	}

	public void updateUser(User user) {
		userDao.updateUser(user);
	}
    
	public void delete(String ids){
		userDao.deleteUser(ids);
	}
    
	public User getUserById(int id){
		return userDao.getUserById(id);
	}

	public User getUserByOpenid(String openid){
		return userDao.getUserByOpenid(openid);
	}
	
	public int getUserByNameAndWx(String username,String wx){
		
		return userDao.getUserByNameAndWx(username, wx);
	}
	
	public User getUserByWx(String wx){
		
		return userDao.getUserByWx(wx);
	}
	
	public Markpassword getMarkpassword(String type){
		
		return userDao.getMarkpassword(type);
	}
	
	public void updateMarkPassword(Markpassword markpassword){
		userDao.updateMarkPassword(markpassword);
	}
	
	public User getUserByPhone(String phone){
		return userDao.getUserByPhone(phone);
	}
	
	

	public void saveFeedBack(FeedBack feedBack){
		userDao.saveFeedBack(feedBack);
	}
	
	
	/**
	 * 获取FeedBack列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<FeedBack> getFeedBackList(int page, int rows, Map map) {
		
		return userDao.getFeedBackList(page, rows, map);
	}
	
	/**
	 * 获取用户数量
	 * 
	 * @param map
	 * @return
	 */
	public int getFeedBackListCount(Map map) {
		
		return userDao.getFeedBackListCount(map);
	}
}
