package com.highguard.Wisdom.struts.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.SocketLog;
import com.highguard.Wisdom.mgmt.manager.SocketManager;

@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
public class SocketLogAction extends BaseAction {

	@Resource
	SocketManager socketManager;
	private int id;
	private String useNameSearch;
   
	private HttpServletResponse response;
	private String type;//类型 查询
	private String username;
	private String wx;
	private String uuidSearch;
	private String phoneSearch;
	private List<SocketLog> list = new ArrayList<SocketLog>();
	private Date fTime;
	private Date eTime;
	private String lattice;
	public String getLattice() {
		return lattice;
	}
	public void setLattice(String lattice) {
		this.lattice = lattice;
	}
	public Date getfTime() {
		return fTime;
	}
	public void setfTime(Date fTime) {
		this.fTime = fTime;
	}
	public Date geteTime() {
		return eTime;
	}
	public void seteTime(Date eTime) {
		this.eTime = eTime;
	}
	public List<SocketLog> getList() {
		return list;
	}
	public void setList(List<SocketLog> list) {
		this.list = list;
	}
	public String getPhoneSearch() {
		return phoneSearch;
	}
	public void setPhoneSearch(String phoneSearch) {
		this.phoneSearch = phoneSearch;
	}
	public String getUuidSearch() {
		return uuidSearch;
	}
	public void setUuidSearch(String uuidSearch) {
		this.uuidSearch = uuidSearch;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getWx() {
		return wx;
	}
	public void setWx(String wx) {
		this.wx = wx;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}


	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	
	public String getUseNameSearch() {
		return useNameSearch;
	}

	public void setUseNameSearch(String useNameSearch) {
		this.useNameSearch = useNameSearch;
	}


	/**
	 *列表
	 * 
	 * @return
	 */
	public String socketLogList() throws Exception {
		int currentPage = Integer.parseInt(getCurrentPage());
		int pageNum = Integer.parseInt(getPageNum());
		Map map = new HashMap();
		map.put("fTime", fTime);
		map.put("eTime", eTime);
		list = socketManager.getSocketLogList(currentPage, pageNum, map);
		int total = socketManager.getSocketLogListCount(map);
		setTotalNum(String.valueOf(total));
		setTotalPages(String.valueOf(getTotalPage(total)));
		setRows(list.size());
		return SUCCESS;
	}
	
	

}
