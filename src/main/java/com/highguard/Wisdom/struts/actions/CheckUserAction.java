package com.highguard.Wisdom.struts.actions;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Blob;
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

import com.highguard.Wisdom.logging.WisdomLogger;
import com.highguard.Wisdom.mgmt.hibernate.beans.Trading;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.DeviceManager;
import com.highguard.Wisdom.mgmt.manager.TradingManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.common.GetPathCommon;
import com.highguard.Wisdom.struts.listener.SocketThered;
import com.highguard.Wisdom.struts.listener.TakeOffLockThered;

@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
public class CheckUserAction extends BaseAction {

	@Resource
	UserManager userManager;
	@Resource
	TradingManager tradingManager;
	@Resource
	DeviceManager deviceManager;
	private String username;
	private String wx;
	private List<Trading> tList = new ArrayList<Trading>();
	private GetPathCommon commno = new GetPathCommon(); 
	private String ids;
	private String code;
	private String fruit;
	 public String getCode() {
		return code;
	}
	public String getFruit() {
		return fruit;
	}
	public void setFruit(String fruit) {
		this.fruit = fruit;
	}
	public void setCode(String code) {
		this.code = code;
	}



	private String deviceId;
	private String LatticeId;
    public String getLatticeId() {
		return LatticeId;
	}
	public void setLatticeId(String latticeId) {
		LatticeId = latticeId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public List<Trading> gettList() {
		return tList;
	}
	public void settList(List<Trading> tList) {
		this.tList = tList;
	}



	private List<Object[]> tradingList = new ArrayList<Object[]>();
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getWx() {
		return wx;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setWx(String wx) {
		this.wx = wx;
	}



	public String checkUser(){
		
		if(userManager.getUserByNameAndWx(username, wx)==0){
			//setResultMes("<font color='red'>不存在用户</font>");
			return ERROR;
		}
		fruit="";
		Map map = new HashMap();
		map.put("wx", wx);
		map.put("takeway", "0");
		map.put("LatticeId", LatticeId);
		tradingList = tradingManager.getTradingList(-1, -1, map);
		for (int i = 0; i < tradingList.size(); i++) {
//			Trading t = new Trading();
//			t.setId(Integer.parseInt(String.valueOf(tradingList.get(i)[0])));
//			t.setUsername(tradingList.get(i)[1]+"");
//			t.setWx(tradingList.get(i)[2]+"");
//			t.setBalance(tradingList.get(i)[3]+"");
//			t.setConsumption(tradingList.get(i)[4]+"");
//			t.setTakeway(tradingList.get(i)[5]+"");
//			t.setCreatetime( (Date)tradingList.get(i)[6]);
//			t.setFruitname(tradingList.get(i)[7]+"");
//			t.setFruitnum(tradingList.get(i)[8]+"");
//			t.setDevice(tradingList.get(i)[9]+"");
//			tList.add(t);
			fruit +=tradingList.get(i)[7]+"X"+tradingList.get(i)[8]+" ";
		}
		
		return SUCCESS;
	}

	public String  sendSocket(){
//		   Socket socket = null;  
//	       BufferedReader br = null;  
//	       PrintWriter pw = null; 
	       Map map = new HashMap();
	       map.put("wx", wx);
		   map.put("takeway", "0");
	       map.put("LatticeId", LatticeId);
	       tradingList = tradingManager.getTradingList(-1, -1, map);
	        String content="SPTP-1234-"+LatticeId+"-1234-7FFF";  
		      if(LatticeId==null||"".equals(LatticeId)){
		    	  code="400";
		    	  WisdomLogger.logInfo("error", " 这个设备没有找到需要通信的IP ");
		    	  LatticeId="127.0.0.1";
		    	//  return SUCCESS;
		      }else{
		    	  code="200";
		    	  
		      }
		      TakeOffLockThered take = new TakeOffLockThered(LatticeId, tradingList);//开锁线程
		      take.start();
//	        try {
//	        	String port = commno.getCommonDir("sendport");
//	            //客户端socket指定服务器的地址和端口号   
//	            socket = new Socket(ip,Integer.parseInt( port));  
//	           // System.out.println("Socket=" + socket);  
//	          
//	            br = new BufferedReader(new InputStreamReader(  
//	                    socket.getInputStream()));  
//	            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(  
//	                    socket.getOutputStream())));  
//	                pw.println(content);  
//	                pw.flush();
//	                WisdomLogger.logInfo("read", " send : "+ content);
//	                String str =br.readLine();  
//	                System.out.println("ACCEPTread : "+ str);
//	                WisdomLogger.logInfo("read", " ACCEPTread : "+ str);
//	           if(str.endsWith("ERROR")){
//	        	   code="400";
//	           }else{
//	        	   WisdomLogger.logInfo("latticeId", "  : "+ LatticeId);
//	        	   WisdomLogger.logInfo("未取走商品的大小  tradingList", " size : "+ tradingList.size());
//	            deviceManager.changLatticeStatus(LatticeId, "1");//改变格子状态
//	        	  for (int i = 0; i < tradingList.size(); i++) {
//	        		  Trading t = tradingManager.getTrading(Integer.parseInt(String.valueOf(tradingList.get(i)[0])));
//	        		  t.setTakeway("1");
//	        		  tradingManager.updateTrading(t);
//				}
//	        	   code="200";
//	           }
//	           
//	            
//	            pw.flush();  
//	        } catch (Exception e) {  
//	            e.printStackTrace();  
//	        } finally {  
//	            try {  
//	                br.close();  
//	                pw.close();  
//	                socket.close();  
//	            } catch (Exception e) {  
//	                // TODO Auto-generated catch block   
//	                e.printStackTrace(); 
//	                code="400";
//	            }  
//	        }
	        
	        return SUCCESS;
	    }  


}
