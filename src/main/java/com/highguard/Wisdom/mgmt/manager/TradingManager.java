package com.highguard.Wisdom.mgmt.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.dao.TradingDao;
import com.highguard.Wisdom.mgmt.dao.TradingDao;
import com.highguard.Wisdom.mgmt.hibernate.beans.Latticestage;
import com.highguard.Wisdom.mgmt.hibernate.beans.Trading;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class TradingManager {
	@Resource
	TradingDao dao;

	public List<Object[]> getTradingList(int page, int rows, Map map) {
		return dao.getTradingList(page, rows, map);
	}

	public int getTradingCount(Map map) {
		return dao.getTradingListCount(map);
	}

	public void saveTrading(Trading trading) {
		dao.saveTrading(trading);
	}

	public void delete(String ids){
		dao.deleteTrading(ids);
	}
	
	public Trading getTrading(Integer id) {
		return dao.getTrading(id);
	}
	
	
	public void updateTrading(Trading trading) {
		dao.updateTrading(trading);
	}
    

}
