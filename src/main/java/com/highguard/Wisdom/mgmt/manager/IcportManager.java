package com.highguard.Wisdom.mgmt.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.dao.IcportDao;
import com.highguard.Wisdom.mgmt.hibernate.beans.ICPort;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class IcportManager {
	@Resource
	IcportDao icportDao;

	/**
	 * 获取列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<ICPort> getICPortList(int page, int rows, Map map) {
		StringBuffer hql = new StringBuffer("SELECT * FROM icport where 1=1  ");
	
		return icportDao.getICPortList(page, rows, map);
	}

	public ICPort getICPortByCard(String cardId){
		return icportDao.getByCard(cardId);
	}
	public ICPort get(int id){
		return icportDao.get(id);
	}
	public ICPort getByLockid(String lockid){
		return icportDao.getByLockid(lockid);
	}
	/**
	 * 获取数量
	 * 
	 * @param map
	 * @return
	 */
	public int getICPortListCount(Map map) {
		
		return icportDao.getICPortListCount(map);
	}

	
	

	public void saveICPort(ICPort user) {
		icportDao.saveICPort(user);
	}

	
	public void deleteICPort(String ids) {
		icportDao.deleteICPort(ids);
		
	}
	
	/**
	 * 验证这个卡是否存在
	 * @param card
	 */
	public void checkICPrt(String card, String lattice){
		Map<String,String> map = new HashMap<String,String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("card", card);
		if( getICPortListCount(map)==0 ){//不存在这个IC卡 需要创建
			ICPort ic = new ICPort();
			ic.setIccard(card);
			ic.setNum(0);
			ic.setCreatetime(sdf.format(new Date()));
//			ic.setLattice(lattice);
			saveICPort(ic);
		}
		
	}
}
