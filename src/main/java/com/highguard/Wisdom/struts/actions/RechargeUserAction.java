package com.highguard.Wisdom.struts.actions;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.FeedBack;
import com.highguard.Wisdom.mgmt.hibernate.beans.RechargeUser;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.RechargeUserManager;

@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
public class RechargeUserAction extends BaseAction {

	@Resource
	RechargeUserManager rechargeUserManager;
	private String ids;
	private String id;
	private HttpServletResponse response;
	private List<RechargeUser> list = new ArrayList<RechargeUser>();
	private RechargeUser user = new RechargeUser();
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RechargeUser getUser() {
		return user;
	}

	public void setUser(RechargeUser user) {
		this.user = user;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<RechargeUser> getList() {
		return list;
	}

	public void setList(List<RechargeUser> list) {
		this.list = list;
	}

	/**
	 *人员列表
	 * 
	 * @return
	 */
	public String rechargeUserList() throws Exception {
		int currentPage = Integer.parseInt(getCurrentPage());
		int pageNum = Integer.parseInt(getPageNum());
		Map map = new HashMap();
		list = rechargeUserManager.getRechargeUserList(currentPage, pageNum, map);
		
		int total = rechargeUserManager.getRechargeUserListCount(map);
		setTotalNum(String.valueOf(total));
		setTotalPages(String.valueOf(getTotalPage(total)));
		setRows(list.size());
		return SUCCESS;
	}
	
	
	
	/**
	 * 准备添加用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toAddRechargeUser() throws Exception {
		
		return SUCCESS;
	}

	/**
	 * 添加用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addRechargeUser() throws Exception {
		user.setCreatetime(new Date());
		rechargeUserManager.saveRechargeUser(user);
		return SUCCESS;
	}

	/**
	 * 修改用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editRechargeUser() throws Exception {
		rechargeUserManager.saveRechargeUser(user);
		return SUCCESS;
	}

	public String toediteRechargeUser() throws Exception {
		user = rechargeUserManager.getRechargeUserById(Integer.parseInt(id));
		return SUCCESS;
	}

	public String deleteRechargeUser() throws Exception {
		rechargeUserManager.deleteUser(ids);
		return SUCCESS;
	}
	

}
