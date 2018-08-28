package com.highguard.Wisdom.mgmt.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.dao.RechargeDao;
import com.highguard.Wisdom.mgmt.dao.RechargeDao;
import com.highguard.Wisdom.mgmt.hibernate.beans.Recharge;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class RechargeManager {
	@Resource
	RechargeDao dao;

	public List<Recharge> getRechargeList(int page, int rows, Map map) {
		return dao.getRechargeList(page, rows, map);
	}

	public int getRechargeCount(Map map) {
		return dao.getRechargeListCount(map);
	}

	public void saveRecharge(Recharge Recharge) {
		dao.saveRecharge(Recharge);
	}

	public void delete(String ids){
		dao.deleteRecharge(ids);
	}

	public Recharge getByOrderNumber(String out_trade_no) {
		
		return dao.getByOrderNumber(out_trade_no);
	}
	
	public void updateRecharge(Recharge recharge) {
		 dao.updateRecharge(recharge);
	}
    

}
