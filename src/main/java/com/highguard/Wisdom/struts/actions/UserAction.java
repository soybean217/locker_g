package com.highguard.Wisdom.struts.actions;

import java.io.BufferedInputStream;
import java.io.File;
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
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.FeedBack;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.UserManager;

@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction {

	@Resource
	UserManager userManager;
	private int id;
	private String ids;
	private String useNameSearch;
	private String cardNameSearch;
	private String itemSearch;
	private Map<String, String> userMap = new HashMap<String, String>();
	private Map<String, String> itemMap = new HashMap<String, String>();
    private List<User> userList = new ArrayList<User>();
	private HttpServletResponse response;
	private File upload;
	private String uploadContentType;    // 封装上传文件名的属性    
	private String uploadFileName;
	private File otherupload;
	private String otheruploadContentType;    // 封装上传文件名的属性    
	private String otheruploadFileName;
	private User user;
	private String type;//类型 查询
	private String username;
	private String wx;
	private String uuidSearch;
	private String phoneSearch;
	private List<FeedBack> feedBackList = new ArrayList<FeedBack>();
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
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
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
	public User getUser() {
		return user;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}



	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public File getOtherupload() {
		return otherupload;
	}
	public void setOtherupload(File otherupload) {
		this.otherupload = otherupload;
	}
	public Map<String, String> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<String, String> itemMap) {
		this.itemMap = itemMap;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}



	public String getOtheruploadContentType() {
		return otheruploadContentType;
	}
	public void setOtheruploadContentType(String otheruploadContentType) {
		this.otheruploadContentType = otheruploadContentType;
	}
	public String getOtheruploadFileName() {
		return otheruploadFileName;
	}
	public void setOtheruploadFileName(String otheruploadFileName) {
		this.otheruploadFileName = otheruploadFileName;
	}
	public String getUseNameSearch() {
		return useNameSearch;
	}

	public void setUseNameSearch(String useNameSearch) {
		this.useNameSearch = useNameSearch;
	}

	public String getCardNameSearch() {
		return cardNameSearch;
	}

	public void setCardNameSearch(String cardNameSearch) {
		this.cardNameSearch = cardNameSearch;
	}

	public String getItemSearch() {
		return itemSearch;
	}

	public void setItemSearch(String itemSearch) {
		this.itemSearch = itemSearch;
	}

	public Map<String, String> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, String> userMap) {
		this.userMap = userMap;
	}

	/**
	 *人员列表
	 * 
	 * @return
	 */
	public String userList() throws Exception {
		int currentPage = Integer.parseInt(getCurrentPage());
		int pageNum = Integer.parseInt(getPageNum());
		Map map = new HashMap();
		map.put("useNameSearch", useNameSearch);
		map.put("cardNameSearch", cardNameSearch);
		map.put("uuidSearch", uuidSearch);
		map.put("itemSearch", itemSearch);
		map.put("phoneSearch", phoneSearch);
		userList = userManager.getUserList(currentPage, pageNum, map);
		int total = userManager.getUserCount(map);
		setTotalNum(String.valueOf(total));
		setTotalPages(String.valueOf(getTotalPage(total)));
		setRows(userList.size());
		return SUCCESS;
	}
	
	public String getye(String a) { 
		double d = Double.parseDouble(a);
		DecimalFormat df = new DecimalFormat("######0.0");
		return df.format(d);
	}
	
	public String feedBackList() throws Exception {
		int currentPage = Integer.parseInt(getCurrentPage());
		int pageNum = Integer.parseInt(getPageNum());
		Map map = new HashMap();
	
		feedBackList = userManager.getFeedBackList(currentPage, pageNum, map);
		
		int total = userManager.getFeedBackListCount(map);
		setTotalNum(String.valueOf(total));
		setTotalPages(String.valueOf(getTotalPage(total)));
		setRows(userList.size());
		return SUCCESS;
	}


	public List<FeedBack> getFeedBackList() {
		return feedBackList;
	}
	public void setFeedBackList(List<FeedBack> feedBackList) {
		this.feedBackList = feedBackList;
	}
	/**
	 * 准备添加用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toAddUser() throws Exception {
		user  = new User();
        user.setUuid(System.currentTimeMillis()+"");
		return SUCCESS;
	}

	/**
	 * 添加用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addUser() throws Exception {
		if(userManager.getUserByWx( user.getTelephone())!=null){
			setResultMes("<font color='red'>手机号重复</font>");
			return ERROR;
		}
		
		if (null != upload) {
			FileInputStream str = new FileInputStream(upload);
			Blob photo = Hibernate.createBlob(str);
		    user.setSfzphoto(photo);
		}
		if (null != otherupload) {
			FileInputStream str = new FileInputStream(otherupload);
			Blob photo = Hibernate.createBlob(str);
			   user.setOtherphoto(photo);
		}
		user.setBalance("0");
		user.setConsumption("0");
		userManager.saveUser(user);
		return SUCCESS;
	}

	/**
	 * 修改用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editUser() throws Exception {
	  User olduser = userManager.getUserByWx(user.getTelephone());
	  if(olduser!=null){
		  if(!olduser.getId().toString().equals(user.getId().toString())){
			  setResultMes("<font color='red'>手机号重复</font>");
				return ERROR;
		  }
	  }
		
		
	  User	inuser = userManager.getUserById(user.getId());
		if (null != upload) {
			FileInputStream str = new FileInputStream(upload);
			Blob photo = Hibernate.createBlob(str);
		    user.setSfzphoto(photo);
		}else{
			user.setSfzphoto(inuser.getSfzphoto());
		}
		if (null != otherupload) {
			FileInputStream str = new FileInputStream(otherupload);
			Blob photo = Hibernate.createBlob(str);
			   user.setOtherphoto(photo);
		}else{
			user.setOtherphoto(inuser.getOtherphoto());
		}
		userManager.updateUser(user);
		return SUCCESS;
	}

	public String toeditUser() throws Exception {
		user = userManager.getUserById(id);
		if(user.getUuid()==null||"".equals(user.getUuid())){
			user.setUuid(System.currentTimeMillis()+"");
		}
		return SUCCESS;
	}

	public String deleteUser() throws Exception {
		userManager.delete(ids);
		return SUCCESS;
	}
	
	/**
	 * 显示照片
	 * 
	 * @return
	 * @throws Exception
	 */
	public String showImage() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		BufferedInputStream ins;
		OutputStream ops;
		byte[] bt = null;
		user = userManager.getUserById(id);
		Blob blob =  null;
		if(type.equals("1")){
			blob = user.getSfzphoto();
		}else{
			blob = user.getOtherphoto();
		}
		if (null != blob) {
			InputStream is = blob.getBinaryStream();
			ins = new BufferedInputStream(is);
			int size = (int) blob.length();
			bt = new byte[size];
			ins.read(bt, 0, size);
			response.getOutputStream().write(bt);
			ops = response.getOutputStream();
			ops.flush();
			ops.close();
			ins.close();
		}
		return null;
	}
	
	
	public String checkUser(){
		
		if(userManager.getUserByNameAndWx(username, wx)==0){
			setResultMes("<font color='red'>不存在用户</font>");
			return ERROR;
		}
		
		return SUCCESS;
	}
	


}
