package com.highguard.Wisdom.mgmt.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.dao.DeviceDao;
import com.highguard.Wisdom.mgmt.hibernate.beans.Device;
import com.highguard.Wisdom.mgmt.hibernate.beans.InitStatus;
import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.hibernate.beans.LatticeStatus;
import com.highguard.Wisdom.mgmt.hibernate.beans.Latticestage;

@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class DeviceManager {
	@Resource
	DeviceDao dao;


	/**
	 * 获取列表
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public List<Device> getDeviceList(int page, int rows, Map map) {

		return dao.getDeviceList(page, rows, map);
	}

	public List<Device> getDevicesByManagerid(int managerid){
		return dao.getDevicesByManagerid(managerid);
	}

	public List<Lattice> getLatticesByDevice(Device device){
		return dao.getLatticesByDevice(device);
	}

	public List<Lattice> getLatticeListByType(int type, String status){
		List<Lattice> lattices = new ArrayList<>();

		for( Lattice lattice : dao.getLatticeListByType(type, status) ){
			if( "0".equals(status) && lattice.getIcport() == null ){
				lattices.add(lattice);
			}
			else if( !"0".equals(status) ){
				lattices.add(lattice);
			}
		}
		return lattices;
	}
	public List<Lattice> getLatticeListByLockid(String lockid){
		return dao.getLatticeListByLockid(lockid);
	}
	/**
	 * 获取数量
	 * 
	 * @param map
	 * @return
	 */
	public int getDeviceListCount(Map map) {

		return dao.getDeviceListCount(map);
	}

	public int  changLatticeStatus(String ids,String status) {

		return dao.changLatticeStatus(ids, status);
	}
	public void saveDevice(Device device) {
		dao.saveDevice(device);
	}

	public void updateDevice(Device device) {
		dao.updateDevice(device);;
	}

	public Device getDeviceById(int id) {
		return dao.getDeviceById(id);
	}


	public void deleteDevice(String ids) {

		dao.deleteDevice(ids);
	}

	public void deleteDeviceAndLattice(String ids){
		deleteDevice(ids);
		deleteLatticeByDeviceid(ids);
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

		return dao.getLatticeList(page, rows, map);
	}

	public List<Lattice> getLatticeList(){
		return dao.getLatticeList();
	}

	public List<Object[]> getLatticeListByDeviceId(int id) {
		return dao.getLatticeListByDeviceId(id);
	}
	/**
	 * 获取数量
	 * 
	 * @param map
	 * @return
	 */
	public int getLatticeListCount(Map map) {

		return dao.getLatticeListCount(map);
	}


	public void saveLattice(Lattice lattice) {
		dao.saveLattice(lattice);
	}

	public void updateLattice(Lattice lattice) {
		dao.updateLattice(lattice);
	}

	public Lattice getLatticeById(int id) {
		return dao.getLatticeById(id);
	}


	public int deleteLattice(String ids) {

		return dao.deleteLattice(ids);
	}

	public void deleteLatticeByDeviceid(String id) {

		dao.deleteLatticeByDeviceid(id);
	}

	public void updateLatticeWeight(String weight,String latticeid){
		dao.updateLatticeWeight(weight, latticeid);
	}

	public void saveLatticestage(Latticestage stage){
		dao.saveLatticestage(stage);
	}
	/**
	 * 获取最近的一次格子重量
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public Latticestage getFirstLatticestage() {

		return dao.getFirstLatticestage();
	}

	/**
	 * 获取格子信息
	 * 
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 */
	public Lattice getLatticeBylockid(String lockid) {

		return dao.getLatticeBylockid(lockid);
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

		return dao.getLatticeStatus(page, rows, map);
	}

	/**
	 * 获取数量
	 * 
	 * @param map
	 * @return
	 */
	public int getLatticeStatusCount(Map map) {

		return dao.getLatticeStatusCount(map);
	}


	/**
	 * 用户数卡上报后 记录用户的连接状态 包括 格子的号码 用户的卡号 和第几次查询（默认第一次开锁去查询）
	 * @param latticeid
	 * @param cardid
	 */
	public void saveLatticeStatus(String  latticeid,String cardid) {
		//先去判断用户是否为超级用户 如果为超级用户 则记录开锁时间
		//if(dao.getSuperUser(cardid)>0){
		LatticeStatus latticeStatus = dao.getLatticeStatusById(latticeid);
		if(latticeStatus==null){
			latticeStatus = new LatticeStatus();
			latticeStatus.setLattice(latticeid);
			latticeStatus.setCreatetime(new Date());
			latticeStatus.setType("连接上");
			latticeStatus.setComment("用户连接");
			latticeStatus.setCard(cardid);
			latticeStatus.setReadstate("-1");
			dao.saveLatticeStatus(latticeStatus);
		}else{
			latticeStatus.setCreatetime(new Date());
			latticeStatus.setReadstate("-1");
			latticeStatus.setCard(cardid);
			dao.updateLatticeStatus(latticeStatus);
		}
		//}

	}

	public LatticeStatus getLatticeStatusById(String latticeid){
		return dao.getLatticeStatusById(latticeid);
	}

	public void updateLatticeStatus(LatticeStatus latticeStatus){
		dao.updateLatticeStatus(latticeStatus);
	}

	//-------------
	/**
	 * 根据格子编号 获取是否10秒后发送 单价-0-0-0
	 * @param lattice
	 * @return
	 */
	public InitStatus getInitStatusByLattice(String lattice){
		return dao.getInitStatusByLattice(lattice);
	}

	public void saveInitStatus(InitStatus  initStatus) {
		dao.saveInitStatus(initStatus);
	}

	public void updateInitStatus(InitStatus  initStatus) {
		dao.updateInitStatus(initStatus);
	}

}
