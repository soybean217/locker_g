package com.highguard.Wisdom.mgmt.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.highguard.Wisdom.mgmt.hibernate.beans.Fruit;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;

@SuppressWarnings("unchecked")
@Repository
public class FruitDao {
	@Resource
	SessionFactory factory;

	
	
	public List<Fruit> getAllFruitList() {
		StringBuffer hql = new StringBuffer("SELECT * FROM fruit where 1=1  ");

		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(Fruit.class);

		return query.list();
	}

	/**
	 * 获取列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<Fruit> getFruitList(int page, int rows, Map map) {
		StringBuffer hql = new StringBuffer("SELECT * FROM fruit where 1=1  ");

		if (null != map.get("numSearch")
				&& !"".equals(map.get("numSearch"))) {
			hql.append(" and num like '" + map.get("numSearch") + "%' ");
		}
		if (null != map.get("categorySearch")
				&& !"".equals(map.get("categorySearch"))) {
			hql.append(" and category like '" + map.get("categorySearch") + "%' ");
		}
		if (null != map.get("productnameSearch")
				&& !"".equals(map.get("productnameSearch"))) {
			hql.append(" and productname like '" + map.get("productnameSearch") + "%' ");
		}
		if (null != map.get("gradeSearch")
				&& !"".equals(map.get("gradeSearch"))) {
			hql.append(" and grade like '" + map.get("gradeSearch") + "%' ");
		}
		if (null != map.get("priceSearch")
				&& !"".equals(map.get("priceSearch"))) {
			hql.append(" and price like '" + map.get("priceSearch") + "%' ");
		}
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(Fruit.class);
		if (page >= 0 && rows >= 0) {
			query.setFirstResult((page - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.list();
	}

	/**
	 * 获取数量
	 * 
	 * @param map
	 * @return
	 */
	public int getFruitListCount(Map map) {
		StringBuffer hql = new StringBuffer(
				"SELECT count(id) FROM fruit where 1=1  ");
		if (null != map.get("numSearch")
				&& !"".equals(map.get("numSearch"))) {
			hql.append(" and num like '" + map.get("numSearch") + "%' ");
		}
		if (null != map.get("categorySearch")
				&& !"".equals(map.get("categorySearch"))) {
			hql.append(" and category like '" + map.get("categorySearch") + "%' ");
		}
		if (null != map.get("productnameSearch")
				&& !"".equals(map.get("productnameSearch"))) {
			hql.append(" and productname like '" + map.get("productnameSearch") + "%' ");
		}
		if (null != map.get("gradeSearch")
				&& !"".equals(map.get("gradeSearch"))) {
			hql.append(" and grade like '" + map.get("gradeSearch") + "%' ");
		}
		if (null != map.get("priceSearch")
				&& !"".equals(map.get("priceSearch"))) {
			hql.append(" and price like '" + map.get("priceSearch") + "%' ");
		}
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return Integer.parseInt(query.uniqueResult().toString());
	}


	public void saveFruit(Fruit fruit) {
		factory.getCurrentSession().save(fruit);
	}

	public void updateFruit(Fruit fruit) {
		factory.getCurrentSession().update(fruit);
	}

	public Fruit getFruitById(int id) {
		return (Fruit) factory.getCurrentSession().get(Fruit.class, id);
	}
	public Fruit getLocketFruitById(int id) {
		return (Fruit) factory.getCurrentSession().get(Fruit.class, id, LockMode.UPGRADE);
	}

	public void deleteFruit(String ids) {

		StringBuffer hql = new StringBuffer("delete FROM fruit where id in ("
				+ ids + ")  ");

		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		query.executeUpdate();
	}
	
	
	/**
	 * 判断所删除的水果是否被格子引用
	 * 
	 * @param map
	 * @return
	 */
	public int getFruitCountInLattice(String ids) {
		StringBuffer hql = new StringBuffer(
				"SELECT count(id) FROM lattice where deviceid in("+ids+")  ");
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return Integer.parseInt(query.uniqueResult().toString());
	}

	
}
