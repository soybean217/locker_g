package com.highguard.Wisdom.mgmt.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.highguard.Wisdom.mgmt.hibernate.beans.Device;
import com.highguard.Wisdom.mgmt.hibernate.beans.InitStatus;
import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.hibernate.beans.LatticeStatus;
import com.highguard.Wisdom.mgmt.hibernate.beans.Latticestage;
import com.highguard.Wisdom.mgmt.hibernate.beans.Recharge;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;

@SuppressWarnings("unchecked")
@Repository
public class DeviceDao {
	@Resource
	SessionFactory factory;

	/**
	 * 获取列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<Device> getDeviceList(int page, int rows, Map map) {
		StringBuffer hql = new StringBuffer("SELECT * FROM device where 1=1  ");
		if(map.get("cellnumSearch")!=null&&!"".equals(map.get("cellnumSearch"))){
			hql.append(" and cellnum='"+map.get("cellnumSearch")+"'");
		}
		if(map.get("localSearch")!=null&&!"".equals(map.get("localSearch"))){
			hql.append(" and local like '%"+map.get("localSearch")+"%'");
		}
		if(map.get("numSearch")!=null&&!"".equals(map.get("numSearch"))){
			hql.append(" and num like '%"+map.get("numSearch")+"%'");
		}
		if(map.get("storeId")!=null&&!"".equals(map.get("storeId"))){
			hql.append(" and storeid = '"+map.get("storeId")+"'");
		}

		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(Device.class);
		if (page >= 0 && rows >= 0) {
			query.setFirstResult((page - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.list();
	}


	public List<Device> getDevicesByManagerid(int managerid){
		Criteria query = factory.getCurrentSession().createCriteria(Device.class);
		query.add(Restrictions.eq("managerid", managerid));
		return query.list();
	}

	public List<Lattice> getLatticesByDevice(Device device){
		Criteria query = factory.getCurrentSession().createCriteria(Lattice.class);
		query.add(Restrictions.eq("deviceid", device.getId()));
		return query.list();
	}
	/**
	 * 获取数量
	 * 
	 * @param map
	 * @return
	 */
	public int getDeviceListCount(Map map) {
		StringBuffer hql = new StringBuffer(
				"SELECT count(id) FROM device where 1=1  ");
		if(map.get("cellnumSearch")!=null&&!"".equals(map.get("cellnumSearch"))){
			hql.append(" and cellnum='"+map.get("cellnumSearch")+"'");
		}
		if(map.get("localSearch")!=null&&!"".equals(map.get("localSearch"))){
			hql.append(" and local like '%"+map.get("localSearch")+"%'");
		}
		if(map.get("numSearch")!=null&&!"".equals(map.get("numSearch"))){
			hql.append(" and num like '%"+map.get("numSearch")+"%'");
		}
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return Integer.parseInt(query.uniqueResult().toString());
	}


	public void saveDevice(Device device) {
		factory.getCurrentSession().save(device);
	}

	public void updateDevice(Device device) {
		factory.getCurrentSession().update(device);
	}

	public Device getDeviceById(int id) {
		return (Device) factory.getCurrentSession().get(Device.class, id);
	}


	public void deleteDevice(String ids) {

		StringBuffer hql = new StringBuffer("delete FROM device where id in ("
				+ ids + ")  ");

		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		query.executeUpdate();
	}

	public int changLatticeStatus(String ids,String status) {

		StringBuffer hql = new StringBuffer("update lattice set status='"+status+"' where lockid = '"
				+ ids + "'  ");
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return	query.executeUpdate();
	}


	//格子
	/**
	 * 获取列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<Lattice> getLatticeList(int page, int rows, Map map) {
		StringBuffer hql = new StringBuffer("SELECT * FROM lattice where 1=1  ");

		if(map.get("lockid")!=null&&!"".equals(map.get("lockid"))){
			hql.append(" and lockid='"+map.get("lockid")+"' ");
		}
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(Lattice.class);
		if (page >= 0 && rows >= 0) {
			query.setFirstResult((page - 1) * rows);
			query.setMaxResults(rows);
		}
		return query.list();
	}

	public List<Lattice> getLatticeList(){
		Criteria query = factory.getCurrentSession().createCriteria(Lattice.class);
		return query.list();
	}

	public List<Lattice> getLatticeListByLockid(String lockid){
		Criteria query = factory.getCurrentSession().createCriteria(Lattice.class);
		query.add(Expression.eq("lockid", lockid));
		return query.list();
	}
	public List<Lattice> getLatticeListByType(int type, String status){
		Criteria query = factory.getCurrentSession().createCriteria(Lattice.class);
		query.add(Expression.eq("type", type));
//		query.add(Expression.eq("status", status));
		return query.list();
	}

	public List<Object[]> getLatticeListByDeviceId(int id) {
		StringBuffer hql = new StringBuffer("SELECT l.id,l.lockid,l.qrcode,l.fruit,f.productname,l.price,l.weight,f.id,l.status,l.type FROM lattice l  left join fruit f on f.id =l.fruitid where l.deviceid=  "+id+" ORDER BY l.id DESC");


		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		return query.list();
	}

	/**
	 * 获取数量
	 * 
	 * @param map
	 * @return
	 */
	public int getLatticeListCount(Map map) {
		StringBuffer hql = new StringBuffer(
				"SELECT count(id) FROM lattice where 1=1  ");
		if(map.get("lockid")!=null&&!"".equals(map.get("lockid"))){
			hql.append(" and lockid='"+map.get("lockid")+"' ");
		}
		if(map.get("date")!=null&&!"".equals(map.get("date"))){
			//hql.append(" and date >='"+map.get("date")+"' ");
		}
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return Integer.parseInt(query.uniqueResult().toString());
	}


	public void saveLattice(Lattice lattice) {
		factory.getCurrentSession().saveOrUpdate(lattice);
	}

	public void updateLattice(Lattice lattice) {
		factory.getCurrentSession().saveOrUpdate(lattice);
	}

	public Lattice getLatticeById(int id) {
		return (Lattice) factory.getCurrentSession().get(Lattice.class, id);
	}


	public int deleteLattice(String ids) {

		StringBuffer hql = new StringBuffer("delete FROM lattice where id in ("
				+ ids + ")  ");

		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return	query.executeUpdate();
	}

	public void deleteLatticeByDeviceid(String id) {

		StringBuffer hql = new StringBuffer("delete FROM lattice where deviceid in ("
				+ id + ")  ");

		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		query.executeUpdate();
	}

	public void saveLatticestage(Latticestage lattice) {
		factory.getCurrentSession().save(lattice);
	}

	public void updateLatticeWeight(String weight,String latticeid) {

		StringBuffer hql = new StringBuffer("update  lattice set weight='"+weight+"' where lockid='"+latticeid+"'  ");

		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		query.executeUpdate();
	}

	/**
	 * 获取最近的一次格子重量
	 * 
	 * @return
	 */
	public Latticestage getFirstLatticestage() {
		StringBuffer hql = new StringBuffer("SELECT * FROM latticestage order by createtime desc limit 1  ");


		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(Latticestage.class);
		if(query.list().size()>0){
			return (Latticestage) query.list().get(0);
		}
		return null;
	}


	/**
	 * 获取格子信息
	 * 
	 * @return
	 */
	public Lattice getLatticeBylockid(String lockid) {
		StringBuffer hql = new StringBuffer("SELECT * FROM lattice where lockid='"+lockid+"'  ");


		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(Lattice.class);
		if(query.list().size()>0){
			return (Lattice) query.list().get(0);
		}
		return null;
	}


	//-------- 设备socket链接状态 -------------

	/**
	 * 获取列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<LatticeStatus> getLatticeStatus(int page, int rows, Map map) {
		StringBuffer hql = new StringBuffer("SELECT * FROM latticestatus where 1=1  ");
		if (null != map.get("lattice")
				&& !"".equals(map.get("lattice"))) {
			hql.append(" and lattice  like '%" + map.get("lattice") + "%' ");
		}
		hql.append("order by createtime desc");
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(LatticeStatus.class);
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
	public int getLatticeStatusCount(Map map) {
		StringBuffer hql = new StringBuffer(
				"SELECT count(id) FROM latticestatus where 1=1  ");
		if (null != map.get("lattice")
				&& !"".equals(map.get("lattice"))) {
			hql.append(" and lattice  like '%" + map.get("lattice") + "%' ");
		}
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return Integer.parseInt(query.uniqueResult().toString());
	}


	public LatticeStatus getLatticeStatusById(String id){
		LatticeStatus latticeStatus = new LatticeStatus();
		StringBuffer hql = new StringBuffer("SELECT * FROM latticestatus where 1=1  ");
		hql.append(" and lattice  ='" + id+ "' ");
		SQLQuery query = factory.getCurrentSession().createSQLQuery(
				hql.toString());
		query.addEntity(LatticeStatus.class);
		if(query.list().size()>0){
			return (LatticeStatus) query.list().get(0);
		}
		return null;
	}

	public void saveLatticeStatus(LatticeStatus  latticeStatus) {
		factory.getCurrentSession().save(latticeStatus);
	}

	public void updateLatticeStatus(LatticeStatus latticeStatus) {
		factory.getCurrentSession().saveOrUpdate(latticeStatus);
	}

	public int getSuperUser(String  cardid) {
		StringBuffer hql = new StringBuffer(
				"SELECT count(id) FROM user where type=1 and card='"+cardid+"'  ");
		Query query = factory.getCurrentSession()
				.createSQLQuery(hql.toString());
		return Integer.parseInt(query.uniqueResult().toString());
	}


	//---------------------- initstatus
	/**
	 * 根据格子编号 获取是否10秒后发送 单价-0-0-0
	 * @param lattice
	 * @return
	 */
	public InitStatus getInitStatusByLattice(String lattice){
		StringBuffer hql = new StringBuffer("SELECT * FROM initstatus where 1=1  ");
		hql.append(" and lattice  ='" + lattice+ "' ");
		SQLQuery query = factory.getCurrentSession().createSQLQuery(hql.toString());
		query.addEntity(InitStatus.class);
		if(query.list().size()>0){
			return (InitStatus) query.list().get(0);
		}
		return null;
	}

	public void saveInitStatus(InitStatus  initStatus) {
		factory.getCurrentSession().save(initStatus);
	}

	public void updateInitStatus(InitStatus  initStatus) {
		factory.getCurrentSession().update(initStatus);
	}

}
