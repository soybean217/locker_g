package com.highguard.Wisdom.mgmt.manager;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.dao.FruitDao;
import com.highguard.Wisdom.mgmt.hibernate.beans.Fruit;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class FruitManager {
	@Resource
	FruitDao dao;

	public List<Fruit> getAllFruitList() {
		return dao.getAllFruitList();
	}
	
	public List<Fruit> getFruitList(int page, int rows, Map map) {
		return dao.getFruitList(page, rows, map);
	}

	public int getFruitCount(Map map) {
		return dao.getFruitListCount(map);
	}

	public void saveFruit(Fruit fruit) {
		dao.saveFruit(fruit);
	}

	public void updateFruit(Fruit fruit) {
		dao.updateFruit(fruit);
	}
    
	public void delete(String ids){
		dao.deleteFruit(ids);
	}
    
	public Fruit getFruitById(int id){
		return dao.getFruitById(id);
	}
	public Fruit getLocketFruitById(int id){
		return dao.getLocketFruitById(id);
	}
	
	/**
	 * 判断所删除的水果是否被格子引用
	 * 
	 * @param map
	 * @return
	 */
	public int getFruitCountInLattice(String ids) {
		
		return dao.getFruitCountInLattice(ids);
	}
}
