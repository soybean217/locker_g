package com.highguard.Wisdom.struts.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.Fruit;
import com.highguard.Wisdom.mgmt.manager.FruitManager;

@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
public class FruitAction extends BaseAction {

	@Resource
	FruitManager fruitManager;
	private int id;
	private String ids;
	private String useNameSearch;
	private String cardNameSearch;
	private String itemSearch;
	private Map<String, String> userMap = new HashMap<String, String>();
	private Map<String, String> itemMap = new HashMap<String, String>();
    private List<Fruit> fruitList = new ArrayList<Fruit>();
    private Fruit fruit ;
    private String numSearch;// 编号
	private String categorySearch;// 品类
	private String productnameSearch;// 品名
	private String gradeSearch;// 等级
	private String priceSearch;// 价格
	private File upload;
	private String uploadContentType;    // 封装上传文件名的属性    
	private String uploadFileName;
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
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

	public List<Fruit> getFruitList() {
		return fruitList;
	}

	public void setFruitList(List<Fruit> fruitList) {
		this.fruitList = fruitList;
	}

	public Fruit getFruit() {
		return fruit;
	}

	public void setFruit(Fruit fruit) {
		this.fruit = fruit;
	}

	public String getProductnameSearch() {
		return productnameSearch;
	}

	public void setProductnameSearch(String productnameSearch) {
		this.productnameSearch = productnameSearch;
	}

	public int getId() {
		return id;
	}

	public FruitManager getFruitManager() {
		return fruitManager;
	}

	public void setFruitManager(FruitManager fruitManager) {
		this.fruitManager = fruitManager;
	}

	public String getNumSearch() {
		return numSearch;
	}

	public void setNumSearch(String numSearch) {
		this.numSearch = numSearch;
	}

	public String getCategorySearch() {
		return categorySearch;
	}

	public void setCategorySearch(String categorySearch) {
		this.categorySearch = categorySearch;
	}

	public String getGradeSearch() {
		return gradeSearch;
	}

	public void setGradeSearch(String gradeSearch) {
		this.gradeSearch = gradeSearch;
	}

	public String getPriceSearch() {
		return priceSearch;
	}

	public void setPriceSearch(String priceSearch) {
		this.priceSearch = priceSearch;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
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
	 *列表
	 * 
	 * @return
	 */
	public String fruitList() throws Exception {
		int currentPage = Integer.parseInt(getCurrentPage());
		int pageNum = Integer.parseInt(getPageNum());
		Map map = new HashMap();
		map.put("numSearch", numSearch);
		map.put("categorySearch", categorySearch);
		map.put("gradeSearch", gradeSearch);
		map.put("productnameSearch", productnameSearch);
		map.put("priceSearch", priceSearch);
		fruitList = fruitManager.getFruitList(currentPage, pageNum, map);
		
		int total = fruitManager.getFruitCount(map);
		setTotalNum(String.valueOf(total));
		setTotalPages(String.valueOf(getTotalPage(total)));
		setRows(fruitList.size());
		return SUCCESS;
	}
	
	public void copyFile(File oldfile, String newPath) { 
	       try { 
	           int bytesum = 0; 
	           int byteread = 0; 
	           if (oldfile.exists()) {                  //文件存在时 
	               InputStream inStream = new FileInputStream(oldfile);      //读入原文件 
	               FileOutputStream fs = new FileOutputStream(newPath); 
	               byte[] buffer = new byte[1444]; 
	               int length; 
	               while ( (byteread = inStream.read(buffer)) != -1) { 
	                   bytesum += byteread;            //字节数 文件大小 
	                   //System.out.println(bytesum); 
	                   fs.write(buffer, 0, byteread); 
	               } 
	               inStream.close(); 
	           } 
	       }  catch (Exception e) { 
	           e.printStackTrace();
	       } 
	   } 

	/**
	 * 准备添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toAddFruit() throws Exception {

		return SUCCESS;
	}

	/**
	 * 添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addFruit() throws Exception {
		 String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("") + File.separator + "fruitimage" + File.separator;
		 if(upload!=null){
			 uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("."));
			 String filename= System.currentTimeMillis()+uploadFileName;
			 path = path +filename;
			 
			 copyFile(upload, path);
			 fruit.setImageurl("fruitimage/"+filename);
		 }
		fruitManager.saveFruit(fruit);
		return SUCCESS;
	}

	/**
	 * 修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editFruit() throws Exception {
		 String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("") + File.separator + "fruitimage" + File.separator;
		 if(upload!=null){
			 uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("."));
			 String filename= System.currentTimeMillis()+uploadFileName;
			 path = path +filename;
			 
			 copyFile(upload, path);
			 fruit.setImageurl("fruitimage/"+filename);
		 }
		fruitManager.updateFruit(fruit);
		return SUCCESS;
	}

	public String toeditFruit() throws Exception {
	
		fruit = fruitManager.getFruitById(id);
		return SUCCESS;
	}

	public String deleteFruit() throws Exception {
		if(fruitManager.getFruitCountInLattice(ids)==0){
			fruitManager.delete(ids);
		}else{
			setResultMes("<font color='red' >存在水果被格子引用，无法删除</font>");
		}
		
		return SUCCESS;
	}
	


}
