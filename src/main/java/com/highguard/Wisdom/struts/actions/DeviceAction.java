package com.highguard.Wisdom.struts.actions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
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

import com.highguard.Wisdom.mgmt.hibernate.beans.Device;
import com.highguard.Wisdom.mgmt.hibernate.beans.Fruit;
import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.hibernate.beans.LatticeStatus;
import com.highguard.Wisdom.mgmt.manager.DeviceManager;
import com.highguard.Wisdom.mgmt.manager.FruitManager;
import com.highguard.Wisdom.struts.common.GetPathCommon;
import com.highguard.Wisdom.struts.listener.SocketThered;

@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
public class DeviceAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	FruitManager fruitManager;
	@Resource
	DeviceManager deviceManager; 
	GetPathCommon common = new GetPathCommon();
	private int id;
	private int latticeid;
	private int deviceId;
	private int type;

	private String ids;
	private String useNameSearch;
	private String cardNameSearch;
	private String itemSearch;
	private Map<Integer, String> fruitmap = new HashMap<Integer, String>();
	private Map<String, String> itemMap = new HashMap<String, String>();
    private List<Fruit> fruitList = new ArrayList<Fruit>();
    private List<Device> deviceList = new ArrayList<Device>();
    private List<Lattice> latticeList = new ArrayList<Lattice>();
    private List<LatticeStatus> latticeStatus = new ArrayList<LatticeStatus>();
    private Device device ;
    private Lattice lattice;
    private String numSearch;// 编号
	private String localSearch;// 位置
	private String cellnumSearch;// 格子数量
	private String gradeSearch;// 等级
	private String priceSearch;// 价格
	private String cellnum;
	private HttpServletResponse response;
	private File upload;
	private String uploadContentType;    // 封装上传文件名的属性    
	private String uploadFileName;
	
	DecimalFormat    df   = new DecimalFormat("######0.00");  
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	public String getCellnum() {
		return cellnum;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}


	public List<LatticeStatus> getLatticeStatus() {
		return latticeStatus;
	}
	public void setLatticeStatus(List<LatticeStatus> latticeStatus) {
		this.latticeStatus = latticeStatus;
	}
	public DeviceManager getDeviceManager() {
		return deviceManager;
	}

	public void setDeviceManager(DeviceManager deviceManager) {
		this.deviceManager = deviceManager;
	}

	public String getCellnumSearch() {
		return cellnumSearch;
	}

	public void setCellnumSearch(String cellnumSearch) {
		this.cellnumSearch = cellnumSearch;
	}

	public int getLatticeid() {
		return latticeid;
	}

	public void setLatticeid(int latticeid) {
		this.latticeid = latticeid;
	}

	public void setCellnum(String cellnum) {
		this.cellnum = cellnum;
	}

	public List<Fruit> getFruitList() {
		return fruitList;
	}

	public void setFruitList(List<Fruit> fruitList) {
		this.fruitList = fruitList;
	}





	public int getId() {
		return id;
	}



	public Device getDevice() {
		return device;
	}

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
	public void setDevice(Device device) {
		this.device = device;
	}

	public List<Device> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}

	public List<Lattice> getLatticeList() {
		return latticeList;
	}

	public void setLatticeList(List<Lattice> latticeList) {
		this.latticeList = latticeList;
	}


	public String getLocalSearch() {
		return localSearch;
	}

	public void setLocalSearch(String localSearch) {
		this.localSearch = localSearch;
	}

	public String getNumSearch() {
		return numSearch;
	}

	public void setNumSearch(String numSearch) {
		this.numSearch = numSearch;
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

	public Lattice getLattice() {
		return lattice;
	}

	public void setLattice(Lattice lattice) {
		this.lattice = lattice;
	}

	public Map<Integer, String> getFruitmap() {
		return fruitmap;
	}

	public void setFruitmap(Map<Integer, String> fruitmap) {
		this.fruitmap = fruitmap;
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



	/**
	 *列表
	 * 
	 * @return
	 */
	public String deviceList() throws Exception {
		int currentPage = Integer.parseInt(getCurrentPage());
		int pageNum = Integer.parseInt(getPageNum());
		Map map = new HashMap();
		map.put("numSearch", numSearch);
		map.put("localSearch", localSearch);
		map.put("gradeSearch", gradeSearch);
		map.put("cellnumSearch", cellnumSearch);
		deviceList = deviceManager.getDeviceList(currentPage, pageNum, map);
		int total = deviceManager.getDeviceListCount(map);
		setTotalNum(String.valueOf(total));
		setTotalPages(String.valueOf(getTotalPage(total)));
		setRows(deviceList.size());
		return SUCCESS;
	}

	/**
	 * 准备添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toAddDevice() throws Exception {

		return SUCCESS;
	}

	/**
	 * 添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addDevice() throws Exception {
		if (null != upload) {
			FileInputStream str = new FileInputStream(upload);
			Blob photo = Hibernate.createBlob(str);
			device.setWximage(photo);
		}
		device.setStatus("0");
		deviceManager.saveDevice(device);
	//	int num = Integer.parseInt(device.getCellnum());
	//	for (int i = 0; i < num; i++) {
			Lattice la = new Lattice();
//			la.setDeviceid(device.getId());
//			la.setStatus("0");
//			deviceManager.saveLattice(la);
	//	}
		return SUCCESS;
	}

	/**
	 * 修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editDevice() throws Exception {
		
		//Device	de = deviceManager.getDeviceById(device.getId());
		if (null != upload) {
			FileInputStream str = new FileInputStream(upload);
			Blob photo = Hibernate.createBlob(str);
			device.setWximage(photo);
		}
		
		deviceManager.updateDevice(device);
//		int oldcell = Integer.parseInt(cellnum);
//		int newcell = Integer.parseInt(device.getCellnum());
//		for (int i = 0; i < newcell-oldcell; i++) {
//			Lattice la = new Lattice();
//			la.setDeviceid(device.getId());
//			la.setStatus("0");
//			deviceManager.saveLattice(la);
//		}
		return SUCCESS;
	}

	public String toeditDevice() throws Exception {
	
		device = deviceManager.getDeviceById(id);
		return SUCCESS;
	}

	public String deleteDevice() throws Exception {
		deviceManager.deleteDeviceAndLattice(ids);
		return SUCCESS;
	}
	
	/**
	 *列表
	 * 
	 * @return
	 */
	public String latticeList() throws Exception {
		int currentPage = Integer.parseInt(getCurrentPage());
		int pageNum = Integer.parseInt(getPageNum());
		Map map = new HashMap();
		//latticeList = deviceManager.getLatticeListByDeviceId(id);
		List<Object[]> list = deviceManager.getLatticeListByDeviceId(id);
		List<Fruit> fruitList = fruitManager.getAllFruitList();
		for (int i = 0; i < list.size(); i++) {
			Lattice f = new Lattice();
			f.setId(Integer.parseInt(list.get(i)[0]+""));
			f.setLockid(list.get(i)[1]+"");
			f.setQrcode(list.get(i)[2]+"");
			f.setFruit(list.get(i)[3]+"");
			f.setPrice(list.get(i)[4]+"");
			f.setWeight(list.get(i)[5]+"");
			f.setStatus(list.get(i)[8]+"");
			f.setType(list.get(i)[9]!=null?Integer.valueOf(list.get(i)[9].toString()):0);
			latticeList.add(f);
		}
		int total = deviceManager.getLatticeListCount(map);
		setTotalNum(String.valueOf(total));
		setTotalPages(String.valueOf(getTotalPage(total)));
		setRows(latticeList.size());
		return SUCCESS;
	}

	

	/**
	 * 修改
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editLattice() throws Exception {
		lattice.setDate(new Date().getTime()+Integer.parseInt(common.getCommonDir("date"))*1000+"");
		deviceManager.updateLattice(lattice);
		LatticeStatus lss = deviceManager.getLatticeStatusById(lattice.getLockid());
		return SUCCESS;
	}
	
	
	/**
	 * 将double 转换成 xxx8.00格式
	 * @param a
	 * @return
	 */
	public String doubleToString(double a){
		String res="";
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		a = Double.valueOf(df.format(a));
		res=a+"";
		if(res.length()<7){
			int b = 7-res.length();
			for (int i = 0; i < b; i++) {
				res="X"+res;
			}
		}
		return res;
	}
	
	
	

	public String toeditLattice() throws Exception {
		List<Fruit> fruitList = fruitManager.getAllFruitList();
		for (int i = 0; i < fruitList.size(); i++) {
			fruitmap.put(fruitList.get(i).getId(), fruitList.get(i).getCategory()+"("+fruitList.get(i).getProductname()+")" + " 单价"+fruitList.get(i).getPrice()+"(元/公斤)");
		}
	if(latticeid>0){//修改
		lattice = deviceManager.getLatticeById(latticeid);	
		}else{//新增
			lattice = new Lattice();
			lattice.setDeviceid(deviceId);
		}
		
		return SUCCESS;
	}

	public String deleteLattice() throws Exception {
		int num1 =deviceManager.deleteLattice(ids);
		//device = deviceManager.getDeviceById(id);
		//int num = Integer.parseInt(device.getCellnum());
		//device.setCellnum((num-num1)+"");
		//deviceManager.updateDevice(device);
		return SUCCESS;
	}
	

		/**
		 * 显示照片
		 * 
		 * @return
		 * @throws Exception
		 */
		public String showWxImage() throws Exception {
			HttpServletResponse response = ServletActionContext.getResponse();
			BufferedInputStream ins;
			OutputStream ops;
			byte[] bt = null;
			device = deviceManager.getDeviceById(id);
			Blob blob =  device.getWximage();
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
		
		public String latticeAjax() throws IOException {   
	        HttpServletResponse response = ServletActionContext.getResponse();  
	        response.setContentType("text/html;charset=utf-8");
	        PrintWriter writer = response.getWriter();   
	        String res = "<select name='trading.latticeid'>";
	        List<Object[]> list = deviceManager.getLatticeListByDeviceId(id);
	        for (int i = 0; i < list.size(); i++) {
				res+="<option value='"+Integer.parseInt(list.get(i)[0]+"")+"' onclick='getValue(this)'>电子锁ID:"+list.get(i)[1]+" 品名:"+list.get(i)[3]+"</option>";
			}
	        res +="</select>";
	        writer.print(res);   
	        writer.flush();   
	        writer.close();  
	        return null;
	    }  
		
		
		//------  查询socket链接 设备的链接状态 ----
		
		public String latticeStatusList() throws Exception {
			int currentPage = Integer.parseInt(getCurrentPage());
			int pageNum = Integer.parseInt(getPageNum());
			Map map = new HashMap();
			map.put("lattice", useNameSearch);
			latticeStatus = deviceManager.getLatticeStatus(currentPage, pageNum, map);
			int total = deviceManager.getLatticeStatusCount(map);
			setTotalNum(String.valueOf(total));
			setTotalPages(String.valueOf(getTotalPage(total)));
			setRows(latticeStatus.size());
			return SUCCESS;
		}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
