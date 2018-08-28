package com.highguard.Wisdom.struts.actions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.Recharge;
import com.highguard.Wisdom.mgmt.hibernate.beans.RechargeUser;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.RechargeManager;
import com.highguard.Wisdom.mgmt.manager.RechargeUserManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.common.GetPathCommon;
import com.highguard.Wisdom.struts.common.MD5Digest;

@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
public class RechargeAction extends BaseAction {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Resource
	RechargeManager rechargeManager;
	@Resource
	UserManager userManager;
	@Resource
	RechargeUserManager rechargeUserManager;
	private int id;
	private String ids;
    private List<Recharge> rechargeList = new ArrayList<Recharge>();
    private Recharge recharge;
    private GetPathCommon commno = new GetPathCommon(); 
    private String password;
	private Map<Integer, String> usermap = new HashMap<Integer, String>();
    private String num;
    private String name;
    private String phone;
    private Date fTime;
	private Date eTime;
	private String price;
	private GetPathCommon common = new GetPathCommon();
	private int userid;
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Recharge> getRechargeList() {
		return rechargeList;
	}

	public void setRechargeList(List<Recharge> rechargeList) {
		this.rechargeList = rechargeList;
	}

	public Recharge getRecharge() {
		return recharge;
	}

	public void setRecharge(Recharge recharge) {
		this.recharge = recharge;
	}

	public String getPassword() {
		return password;
	}

	public Map<Integer, String> getUsermap() {
		return usermap;
	}

	public void setUsermap(Map<Integer, String> usermap) {
		this.usermap = usermap;
	}

	public void setPassword(String password) {
		this.password = password;
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
	 * 跳转到输入密码
	 * @return
	 */
	public String rechargePassword(){
		return SUCCESS;
	}

	public String validatePasword(){
		try {
		//	String validatePasword = commno.getCommonDir("validatePasword")a;
			Map<String,String> map = new HashMap<String, String>();
			map.put("name", name);
			List<RechargeUser> list = rechargeUserManager.getRechargeUserList(1, Integer.MAX_VALUE, map);
			if(list.size()==0){
				 this.addFieldError("login", getText("暂无这个用户"));
				return INPUT;
			}
			String validatePasword =list.get(0).getPassword();
			if(!validatePasword.equals(password)){
				this.addFieldError("login", getText("密码错误"));
				return INPUT;
			}
			userid = list.get(0).getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 *列表
	 * 
	 * @return
	 */
	public String rechargeList() throws Exception {
		int currentPage = Integer.parseInt(getCurrentPage());
		int pageNum = Integer.parseInt(getPageNum());
		Map map = new HashMap();
		map.put("name", name);
		map.put("num", num);
		map.put("fTime", fTime);
		map.put("eTime", eTime);
		map.put("price", price);
		rechargeList = rechargeManager.getRechargeList(currentPage, pageNum, map);
		
		int total = rechargeManager.getRechargeCount(map);
		setTotalNum(String.valueOf(total));
		setTotalPages(String.valueOf(getTotalPage(total)));
		setRows(rechargeList.size());
		return SUCCESS;
	}
	
	public String rechargeExport() throws Exception{
		Map map = new HashMap();
		map.put("name", name);
		map.put("num", num);
		map.put("fTime", fTime);
		map.put("eTime", eTime);
		map.put("price", price);
		rechargeList = rechargeManager.getRechargeList(-1, -1, map);
		
		 HSSFWorkbook wb = new HSSFWorkbook();  
         // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet   
 HSSFSheet sheet = wb.createSheet("充值清单");  
         // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short   
 HSSFRow row = sheet.createRow((int) 0);  
         // 第四步，创建单元格，并设置值表头 设置表头居中   
 HSSFCellStyle style = wb.createCellStyle();  
 style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式   
 sheet.setColumnWidth((short)0, (short)4000);
 sheet.setColumnWidth((short)1, (short)4000);
 sheet.setColumnWidth((short)2, (short)4000);
 sheet.setColumnWidth((short)3, (short)4000);
 sheet.setColumnWidth((short)4, (short)4000);
 HSSFCell cell = row.createCell((short) 0);  
	cell.setCellValue("用户ID");  
	 cell.setCellStyle(style);  
	cell = row.createCell((short) 1);  
	 cell.setCellValue("姓名");  
	 cell.setCellStyle(style);  
	 cell = row.createCell((short) 2);  
	 cell.setCellValue("充值金额");  
	 cell.setCellStyle(style);  
	 cell = row.createCell((short) 3);  
	 cell.setCellValue("充值时间");  
   cell.setCellStyle(style);  
   cell = row.createCell((short) 4);  
	 cell.setCellValue("备注");  
   cell.setCellStyle(style);  
   
   for (int i = 0; i < rechargeList.size(); i++) {
	   Recharge t = rechargeList.get(i);
	   HSSFRow row1 = sheet.createRow((int) (i+1));  
	   HSSFCell cell1 = row1.createCell((short) 0);  
		cell1.setCellValue(t.getUuid());  
		 cell1.setCellStyle(style);  
		cell1 = row1.createCell((short) 1);  
		 cell1.setCellValue(t.getUsername());  
		 cell1.setCellStyle(style);  
		 cell1 = row1.createCell((short) 2);  
		 cell1.setCellValue(t.getPrice());  
		 cell1.setCellStyle(style);  
		 cell1 = row1.createCell((short) 3);  
		 cell1.setCellValue(format.format(t.getCreatetime()));  
	   cell1.setCellStyle(style);  
	   cell1 = row1.createCell((short) 4);  
		 cell1.setCellValue(t.getComment());  
	   cell1.setCellStyle(style);  
}
   String path = common.getCommonDir("export")+"A1.xls";
   FileOutputStream fout = new FileOutputStream(path);  
   wb.write(fout);  
   fout.close();  
   HttpServletResponse response = ServletActionContext.getResponse();
   download(path, response);
   return null;
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
	public String toAddRecharge() throws Exception {
		Map<String,String> map = new HashMap<String, String>();
		map.put("phone", phone);
		List<User> list = userManager.getUserList(map);
		for (int i = 0; i < list.size(); i++) {
			usermap.put(list.get(i).getId(), list.get(i).getName());
		}
		return SUCCESS;
	}

	/**
	 * 添加
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addRecharge() throws Exception {
		Map map = new HashMap();
		map.put("price", recharge.getPrice());
		//if(rechargeManager.getRechargeCount(map)>0){
		//	setResultMes("<font color='red'>存在重复金额<font>");
		//}else{
		RechargeUser reUser = rechargeUserManager.getRechargeUserById(userid);
			User user = userManager.getUserById(recharge.getUserid());
			recharge.setUsername(user.getName());
			recharge.setCreatetime(new Date());
			recharge.setUuid(user.getUuid());
			double price = Double.valueOf(recharge.getPrice())+ Double.valueOf(user.getBalance());
			user.setBalance(price+"");
			
			rechargeManager.saveRecharge(recharge);	
			userManager.updateUser(user);
	//	}
		return SUCCESS;
	}


	public String deleteRecharge() throws Exception {
		rechargeManager.delete(ids);
		return SUCCESS;
	}
	


}
