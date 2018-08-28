package com.highguard.Wisdom.struts.actions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.Device;
import com.highguard.Wisdom.mgmt.hibernate.beans.Fruit;
import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.hibernate.beans.Recharge;
import com.highguard.Wisdom.mgmt.hibernate.beans.Recharge;
import com.highguard.Wisdom.mgmt.hibernate.beans.Trading;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.DeviceManager;
import com.highguard.Wisdom.mgmt.manager.FruitManager;
import com.highguard.Wisdom.mgmt.manager.RechargeManager;
import com.highguard.Wisdom.mgmt.manager.RechargeManager;
import com.highguard.Wisdom.mgmt.manager.TradingManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.common.GetPathCommon;
import com.highguard.Wisdom.struts.common.MD5Digest;

@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
public class TradingAction extends BaseAction {

	@Resource
	TradingManager tradingManager;
	@Resource
	UserManager userManager;
	@Resource
	FruitManager fruitManager;
	@Resource
	DeviceManager deviceManager;
	private int id;
	private String ids;
	private GetPathCommon commno = new GetPathCommon();
	private Map<Integer, String> usermap = new LinkedHashMap<Integer, String>();
	private Map<String, String> statusmap = new HashMap<String, String>();
	private Map<Integer, String> devicemap = new HashMap<Integer, String>();
	private List<Trading> tradList = new ArrayList<Trading>();
	private List<Object[]> tradingList = new ArrayList<Object[]>();
	private Map<String, String> fruitmap = new HashMap<String, String>();
	private GetPathCommon common = new GetPathCommon();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static DecimalFormat    df   = new DecimalFormat("######0.00");   
	private String wx;
	private String deviceId;
	private Date fTime;
	private Date eTime;
	private Integer fruitid;
	private String LatticeId;
	private String status;
    private String username;
    private String usetime;

	public String getUsetime() {
		return usetime;
	}

	public void setUsetime(String usetime) {
		this.usetime = usetime;
	}

	public List<Trading> getTradList() {
		return tradList;
	}

	public void setTradList(List<Trading> tradList) {
		this.tradList = tradList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, String> getStatusmap() {
		return statusmap;
	}

	public void setStatusmap(Map<String, String> statusmap) {
		this.statusmap = statusmap;
	}

	public String getLatticeId() {
		return LatticeId;
	}

	public void setLatticeId(String latticeId) {
		LatticeId = latticeId;
	}

	public Integer getFruitid() {
		return fruitid;
	}

	public void setFruitid(Integer fruitid) {
		this.fruitid = fruitid;
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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getWx() {
		return wx;
	}

	public Map<Integer, String> getDevicemap() {
		return devicemap;
	}

	public void setDevicemap(Map<Integer, String> devicemap) {
		this.devicemap = devicemap;
	}

	public void setWx(String wx) {
		this.wx = wx;
	}

	public Map<String, String> getFruitmap() {
		return fruitmap;
	}

	public void setFruitmap(Map<String, String> fruitmap) {
		this.fruitmap = fruitmap;
	}

	public Trading getTrading() {
		return trading;
	}

	public void setTrading(Trading trading) {
		this.trading = trading;
	}

	private Trading trading = new Trading();

	public Map<Integer, String> getUsermap() {
		return usermap;
	}

	public void setUsermap(Map<Integer, String> usermap) {
		this.usermap = usermap;
	}

	public int getId() {
		return id;
	}

	

	public void setId(int id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 列表
	 * 
	 * @return
	 */
	public String tradingList()  {
		List<Fruit> fruit = fruitManager.getAllFruitList();
		fruitmap.put("", "-- 请选择 --");
		for (int i = 0; i < fruit.size(); i++) {
			fruitmap.put(fruit.get(i).getId()+"", fruit.get(i).getProductname());
		}

		statusmap.put("", "-- 请选择 --");
		statusmap.put("1", "正常");
		statusmap.put("2", "异常");
		int currentPage = Integer.parseInt(getCurrentPage());
		int pageNum = Integer.parseInt(getPageNum());
		Map map = new HashMap();
		map.put("fruitid", fruitid);
		map.put("fTime", fTime);
		map.put("eTime", eTime);
		map.put("LatticeId", LatticeId);
		map.put("status", status);
		map.put("username", username);
		Map map1 = new HashMap();
		List<User> list = userManager.getUserList(map1);
		usermap.put(0, "--请选择--");
		for (int i = 0; i < list.size(); i++) {
			usermap.put(list.get(i).getId(), list.get(i).getName());
		}
		
		tradingList = tradingManager.getTradingList(currentPage, pageNum, map);
		for (int i = 0; i < tradingList.size(); i++) {
			Trading t = new Trading();
			t.setId(Integer.parseInt(String.valueOf(tradingList.get(i)[0])));
			t.setUsername(tradingList.get(i)[1] + "");
			t.setWx(tradingList.get(i)[2] + "");
			t.setBalance(tradingList.get(i)[3] + "");
			t.setConsumption(tradingList.get(i)[4] + "");
			t.setTakeway(tradingList.get(i)[5] + "");
			t.setCreatetime((Date) tradingList.get(i)[6]);
			t.setFruitname(tradingList.get(i)[7] + "");
			t.setFruitnum(tradingList.get(i)[8] + "");
			t.setDevice(tradingList.get(i)[9] + "");
			t.setLockid(tradingList.get(i)[10] + "");
			t.setStatus(tradingList.get(i)[11] + "");
			tradList.add(t);
		}
		int total = tradingManager.getTradingCount(map);
		setTotalNum(String.valueOf(total));
		setTotalPages(String.valueOf(getTotalPage(total)));
		setRows(tradList.size());
		return SUCCESS;
	}

	public String exportTrading(){
		
		Map map = new HashMap();
		map.put("fruitid", fruitid);
		map.put("fTime", fTime);
		map.put("eTime", eTime);
		map.put("LatticeId", LatticeId);
		map.put("status", status);
		tradingList = tradingManager.getTradingList(-1, -1, map);
		for (int i = 0; i < tradingList.size(); i++) {
			Trading t = new Trading();
			t.setId(Integer.parseInt(String.valueOf(tradingList.get(i)[0])));
			t.setUsername(tradingList.get(i)[1]+"");
			t.setWx(tradingList.get(i)[2]+"");
			t.setBalance(tradingList.get(i)[3]+"");
			t.setConsumption(tradingList.get(i)[4]+"");
			t.setTakeway(tradingList.get(i)[5]+"");
			t.setCreatetime( (Date)tradingList.get(i)[6]);
			t.setFruitname(tradingList.get(i)[7]+"");
			t.setFruitnum(tradingList.get(i)[8]+"");
			t.setDevice(tradingList.get(i)[9]+"");
			t.setLockid(tradingList.get(i)[10]+"");
			t.setStatus(tradingList.get(i)[11]+"");
			tradList.add(t);
		}
		
		
		 HSSFWorkbook wb = new HSSFWorkbook();  
		         // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet   
		 HSSFSheet sheet = wb.createSheet("购物清单");  
		         // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short   
		 HSSFRow row = sheet.createRow((int) 0);  
		         // 第四步，创建单元格，并设置值表头 设置表头居中   
		 HSSFCellStyle style = wb.createCellStyle();  
		 style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式   
		   
		 HSSFCell cell = row.createCell((short) 0);  
			cell.setCellValue("用户姓名");  
			 cell.setCellStyle(style);  
			cell = row.createCell((short) 1);  
			 cell.setCellValue("购买种类");  
			 cell.setCellStyle(style);  
			 cell = row.createCell((short) 2);  
			 cell.setCellValue("数量（KG）");  
			 cell.setCellStyle(style);  
			 cell = row.createCell((short) 3);  
			 cell.setCellValue("消费价格");  
		   cell.setCellStyle(style);  
		   cell = row.createCell((short) 4);  
			 cell.setCellValue("购买时间");  
		   cell.setCellStyle(style);  
		   sheet.setColumnWidth((short)0, (short)4000);
		   sheet.setColumnWidth((short)1, (short)4000);
		   sheet.setColumnWidth((short)2, (short)4000);
		   sheet.setColumnWidth((short)3, (short)4000);
		   sheet.setColumnWidth((short)4, (short)4000);
		   for (int i = 0; i < tradList.size(); i++) {
			   Trading t = tradList.get(i);
			   HSSFRow row1 = sheet.createRow((int) (i+1));  
			   HSSFCell cell1 = row1.createCell((short) 0);  
				cell1.setCellValue(t.getUsername());  
				 cell1.setCellStyle(style);  
				cell1 = row1.createCell((short) 1);  
				 cell1.setCellValue(t.getFruitname());  
				 cell1.setCellStyle(style);  
				 cell1 = row1.createCell((short) 2);  
				 cell1.setCellValue(t.getFruitnum());  
				 cell1.setCellStyle(style);  
				 cell1 = row1.createCell((short) 3);  
				 cell1.setCellValue(t.getConsumption());  
			   cell1.setCellStyle(style);  
			   cell1 = row1.createCell((short) 4);  
				 cell1.setCellValue(format.format(t.getCreatetime()));  
			   cell1.setCellStyle(style);  
		}
		   try  
		           {   
			           String path = common.getCommonDir("export")+"A.xls";
		               FileOutputStream fout = new FileOutputStream(path);  
		               wb.write(fout);  
		               fout.close();  
		               HttpServletResponse response = ServletActionContext.getResponse();
		               download(path, response);
		           }  
		           catch (Exception e)  
		           {  
		               e.printStackTrace();  
		           }  

		
		return this.NONE;
	}
	
	private void download(String path, HttpServletResponse response) {  
        try {  
            // path是指欲下载的文件的路径。   
            File file = new File(path);  
            // 取得文件名。   
            String filename = file.getName();  
            // 以流的形式下载文件。   
            InputStream fis = new BufferedInputStream(new FileInputStream(path));  
            byte[] buffer = new byte[fis.available()];  
            fis.read(buffer);  
            fis.close();  
            // 清空response   
            response.reset();  
            // 设置response的Header   
            response.addHeader("Content-Disposition", "attachment;filename="  
                    + new String(filename.getBytes()));  
            response.addHeader("Content-Length", "" + file.length());  
            OutputStream toClient = new BufferedOutputStream(  
                    response.getOutputStream());  
            response.setContentType("application/vnd.ms-excel;charset=gb2312");  
            toClient.write(buffer);  
            toClient.flush();  
            toClient.close();  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
    }  


	/**
	 * 准备添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toAddTrading() throws Exception {
		
		test1();
		
		Map<String, String> map1 = new HashMap<String, String>();
		List<User> list = userManager.getUserList(map1);
		for (int i = 0; i < list.size(); i++) {
			usermap.put(list.get(i).getId(), list.get(i).getName());
		}
		// List<Fruit> fruit = fruitManager.getAllFruitList();
		devicemap.put(null, "--请选择--");
		// for (int i = 0; i < fruit.size(); i++) {
		// fruitmap.put(fruit.get(i).getId(),
		// fruit.get(i).getCategory()+"("+fruit.get(i).getProductname()+")" +
		// " 单价"+fruit.get(i).getPrice()+"(元/公斤)");
		//
		// }
		Map map = new HashMap();
		List<Device> devicelist = deviceManager.getDeviceList(-1, -1, map);
		for (int i = 0; i < devicelist.size(); i++) {
			devicemap.put(devicelist.get(i).getId(), devicelist.get(i)
					.getName());
		}
		return SUCCESS;
	}



	/**
	 * 添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addTrading() throws Exception {
		User user = userManager.getUserById(trading.getUserid());
		Lattice l = deviceManager.getLatticeById(trading.getLatticeid());
		if ((l.getFruitid() + "").equals("")) {
			setResultMes("<font color='red'>格子未配置水果<font>");
			return ERROR;
		}
		Fruit fruit = fruitManager.getLocketFruitById(l.getFruitid());
		double ye = Double.valueOf(user.getBalance());// 用户余额
		double num = Double.valueOf(trading.getFruitnum());// 购买数量
		double price = Double.valueOf(l.getPrice());// 单价
		double xf = Double.valueOf(user.getConsumption());// 用户消费金额

		if (num > Double.valueOf(fruit.getWeight())) {
			setResultMes("<font color='red'>对不起，没那么多货了！<font>");
			return ERROR;
		}

		if (num * price > ye) {
			setResultMes("<font color='red'>余额不足，无法支付<font>");
			return ERROR;
		} else {
			ye = ye - num * price;
			xf = xf + num * price;
			user.setBalance(String.valueOf(ye));
			user.setConsumption(String.valueOf(xf));
			// fruit.setWeight(Double.valueOf(fruit.getWeight())-num+"");
			// fruitManager.updateFruit(fruit);
			l.setWeight(Double.valueOf(l.getWeight()) - num + "");
			//deviceManager.updateLattice(l);
			userManager.updateUser(user);
			trading.setBalance(String.valueOf(ye));
			trading.setWx(user.getWx());
			trading.setUsername(user.getName());
			trading.setConsumption(df.format(num * price) + "");
			if(num*price>30){
				return "";
			}
			if(user.getType()!=null&&user.getType().equals("1")){
				return "";
			}
			trading.setCreatetime(format.parse(usetime));
			trading.setStatus("1");
			trading.setTakeway("3");
			trading.setFruitid(fruit.getId());
			tradingManager.saveTrading(trading);
		}
		return SUCCESS;
	}
	
	public void test1() {
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			
			
			for (int i = 0; i < 600; i++) {
				//timeStart=timeStart+1000*60*5;
				long timeStart=sdf.parse("2016-"+getyue()+"-"+getday()+" "+gethour()+":"+getM()+":"+getM()+"").getTime();
				test(getuserid(),getlatticeid(),getdate(timeStart));
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public  static String getyue(){
//		String s = "10,11,12";
//		String[] b = s.split(",");
//		Random rand = new Random();
//		int num = rand.nextInt(3);
//		return b[num];
		return "01";
	}
	public  static String getday(){
		String s = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,16,15,17,18,19,20,21,22,23,24,25,26,27,28";
		String[] b = s.split(",");
		Random rand = new Random();
		int num = rand.nextInt(28);
		return b[num]; 
	}
	
	public  static String gethour(){
		String s = "8,9,10,11,12,13,14,16,15,17,18,19,20,21,22";
		String[] b = s.split(",");
		Random rand = new Random();
		int num = rand.nextInt(15);
		return b[num]; 
	}
	
	public  static String getM(){
		String s = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,16,15,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59";
		String[] b = s.split(",");
		Random rand = new Random();
		int num = rand.nextInt(59);
		return b[num]; 
	}
	
	public  static String getuserid(){
		String s = "12,13,14,16,15,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50";

		String[] b = s.split(",");
		Random rand = new Random();
		int num = rand.nextInt(35);
		return b[num]; 
	}
	
	
	public static String getlatticeid(){
		String s = "674,675,676,677,678,679,680,681,682,683,684,685,686,687,689,688,691,692,693,694,695,696,697,698,699,700,701,702,703,704,705,706,708";
		String[] b = s.split(",");
		Random rand = new Random();
		int num = rand.nextInt(32);
		return b[num]; 
	}
	
	public Date getdate(long time){
		Date date = new Date(time);
		return date;
	}
	
	
	
	public String test(String useid,String latticeid,Date date){
		User user = userManager.getUserById(Integer.parseInt(useid));
		if(user==null){
			return "";
		}
		Lattice l = deviceManager.getLatticeById(Integer.parseInt(latticeid));
		if ((l.getFruitid() + "").equals("")) {
			setResultMes("<font color='red'>格子未配置水果<font>");
			return ERROR;
		}
		Fruit fruit = fruitManager.getLocketFruitById(l.getFruitid());
		double ye = Double.valueOf("0");// 用户余额
		double num = Double.valueOf(getrandom());// 购买数量
		double price = Double.valueOf(l.getPrice());// 单价
		double xf = Double.valueOf("0");// 用户消费金额

	
			ye = ye - num * price;
			xf = xf + num * price;
//			user.setBalance(String.valueOf(ye));
//			user.setConsumption(String.valueOf(xf));
			// fruit.setWeight(Double.valueOf(fruit.getWeight())-num+"");
			// fruitManager.updateFruit(fruit);
			l.setWeight(Double.valueOf(l.getWeight()) - num + "");
			//deviceManager.updateLattice(l);
			//userManager.updateUser(user);
			trading.setBalance(String.valueOf("0"));
			trading.setWx(user.getWx());
			trading.setUsername(user.getName());
			trading.setConsumption(df.format(num * price) + "");
			trading.setCreatetime(date);
			if(num*price>30){
				return "";
			}
			if(user.getType()!=null&&user.getType().equals("1")){
				return "";
			}
			trading.setStatus("1");
			trading.setTakeway("3");
			trading.setFruitid(fruit.getId());
			trading.setUserid(user.getId());
			trading.setFruitnum(num+"");
			trading.setLatticeid(l.getId());			
			tradingManager.saveTrading(trading);

		return SUCCESS;
	}
	
	public static String getrandom(){
		return df.format(new Random().nextInt(3)+1+Math.random())+"";
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
		}
		
	}

	public String deleteTrading() throws Exception {
		tradingManager.delete(ids);
		return SUCCESS;
	}

}
