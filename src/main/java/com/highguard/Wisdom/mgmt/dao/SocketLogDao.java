package com.highguard.Wisdom.mgmt.dao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.highguard.Wisdom.mgmt.hibernate.beans.*;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository
public class SocketLogDao {
	@Resource
	SessionFactory factory;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 获取SocketLog列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<SocketLog> getSocketLogList(int page, int rows, Map map) {
		StringBuffer hql = new StringBuffer("SELECT * FROM syslogs where 1=1  ");
		if (null != map.get("fTime") && !"".equals(map.get("fTime"))) {
			hql.append(" and createtime >= '" + df.format(map.get("fTime"))
					+ "'");
		}
		if (null != map.get("eTime") && !"".equals(map.get("eTime"))) {
			hql.append(" and createtime <= '" + df.format(map.get("eTime"))
					+ "'");
		}

		hql.append(" order by id desc");
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(Syslog.class);
		if (page >= 0 && rows >= 0) {
			query.setFirstResult((page - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.list();
	}
	
	/**
	 * 获取用户数量
	 * 
	 * @param map
	 * @return
	 */
	public int getSocketLogListCount(Map map) {
		StringBuffer hql = new StringBuffer(
				"SELECT count(id) FROM syslogs where 1=1  ");
		if (null != map.get("fTime") && !"".equals(map.get("fTime"))) {
			hql.append(" and createtime >= '" + df.format(map.get("fTime"))
					+ "'");
		}
		if (null != map.get("eTime") && !"".equals(map.get("eTime"))) {
			hql.append(" and createtime <= '" + df.format(map.get("eTime"))
					+ "'");
		}
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return Integer.parseInt(query.uniqueResult().toString());
	}


	public void saveSocketLog(SocketLog socketLog) {
		factory.getCurrentSession().save(socketLog);
	}
 
	
}
