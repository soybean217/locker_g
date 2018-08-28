package com.highguard.Wisdom.struts.actions.appApi;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller; 
import net.sf.json.JSONObject;

import com.highguard.Wisdom.logging.WisdomLogger;
import com.highguard.Wisdom.mgmt.hibernate.beans.FeedBack;
import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.hibernate.beans.LatticeStatus;
import com.highguard.Wisdom.mgmt.hibernate.beans.Markpassword;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.hibernate.beans.UserInfo;
import com.highguard.Wisdom.mgmt.manager.DeviceManager;
import com.highguard.Wisdom.mgmt.manager.UserInfoManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.listener.SocketThered;
import com.highguard.Wisdom.util.JsonUtil;
@Controller
@Scope("prototype")
public class OpenDoorACtion extends ApiBaseAction  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	UserManager userManager;
	@Resource
	DeviceManager deviceManager;
	@Resource
	UserInfoManager userInfoManager;
	private Integer userId;
	private String latticeId;
	private String userType;
	private String lattice;
	private String phone;
	private String content;//反馈内容
	private PrintWriter out; 
	DecimalFormat    df   = new DecimalFormat("######0.00");   
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLattice() {
		return lattice;
	}

	public void setLattice(String lattice) {
		this.lattice = lattice;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLatticeId() {
		return latticeId;
	}

	public void setLatticeId(String latticeId) {
		this.latticeId = latticeId;
	}

	public void openDoor() {
		int ye =Integer.parseInt(userManager.getMarkpassword("2").getValue());//最小的开门额度
		WisdomLogger.logInfo("openDoor","userType:"+userType+" latticeId"+latticeId+" userId"+userId);
		if( userId != null && userId.equals("66666") ){
			JSONObject user = new JSONObject();
//			Iterator<String> keySocket = SocketThered.map.keySet().iterator();
//			while( keySocket.hasNext() ){
//				String key = keySocket.next();
//				user.put( key , key);
//			}
			writeToFrontPage(user);
			return;
		}
		if(userType==null||latticeId==null||userId==null){
			JSONObject jsonMsg = JsonUtil.JsonMsg(false, "补全参数", "");
			WisdomLogger.logInfo("openDoor","补全参数");
			jsonMsg.put("errCode", "101"); 
			writeToFrontPage(jsonMsg);
		}
		User user = null;
		if(userType.equals("0")){
			user = userManager.getUserById(userId); 
		}else{
			UserInfo userInfo = userInfoManager.getUserInfoById(userId); 
			user = userManager.getUserById(userInfo.getUserId()); 
		}
		if( user == null ){ 
			JSONObject jsonMsg = JsonUtil.JsonMsg(false, "没有这个用户", "");
			WisdomLogger.logInfo(OpenDoorACtion.class, "没有这个用户");
			jsonMsg.put("errCode", "700"); 
			writeToFrontPage(jsonMsg);
			WisdomLogger.logInfo("openDoor","没有这个用户");
			return ;
		}
		String smTime = user.getSmTime();
		if(smTime!=null&&!"".equals(smTime)){
			long time = Long.parseLong(smTime)/1000;
			long now = (new Date().getTime())/1000;
			if( now-time <=30 ){
				JSONObject jsonMsg = JsonUtil.JsonMsg(false, "30秒内不许多次扫码", "");
				WisdomLogger.logInfo(OpenDoorACtion.class, "30秒内不许多次扫码");
				jsonMsg.put("errCode", "700"); 
				writeToFrontPage(jsonMsg);
				WisdomLogger.logInfo("openDoor","30秒内不许多次扫码");
				return ;
			}
			user.setSmTime(new Date().getTime()+"");
			userManager.updateUser(user);
		}
		Map<String,String>  map = new HashMap<String, String>();
		map.put("lockid", latticeId);
		List<Lattice> list = deviceManager.getLatticeList(-1, -1, map);
		 
		if(list.size()<=0){
			JSONObject jsonMsg = JsonUtil.JsonMsg(false, "没有这个机器", "");
			jsonMsg.put("errCode", "600"); 
			WisdomLogger.logInfo("openDoor","没有这个机器");
			writeToFrontPage(jsonMsg);
			return ;
		}
		Lattice lattice = list.get(0);
		
		if(change(user.getBalance())>=(float)ye){
			Socket socket = SocketThered.map.get(lattice.getLockid()+"").getSocket();
			 
			if(socket==null){
				JSONObject jsonMsg = JsonUtil.JsonMsg(false, "机器未连接", "");
				jsonMsg.put("errCode", "300"); 
				WisdomLogger.logInfo("openDoor","机器没连接"+lattice.getLockid());
				writeToFrontPage(jsonMsg);
			}else{
				try {
					LatticeStatus lss = deviceManager.getLatticeStatusById(lattice.getLockid());
					if( lss == null) {
						lss = new LatticeStatus();
						lss.setLattice(latticeId);
					}
					lss.setCreatetime(new Date()); 
					lss.setReadstate("0");
					lss.setCard(user.getCard());
					
					deviceManager.updateLatticeStatus(lss);
					
					String RemoteIP = socket.getInetAddress().getHostName();
					String RemotePort = "" + socket.getLocalPort();

					out = new PrintWriter(socket.getOutputStream(), true);
					out.println("RDKG-1234-"+lattice.getLockid());//查询格子重量
					out.flush();  

					out.println("WSMG-1234-"+lattice+"-4444"+"-"+doubleToString(Double.valueOf(df.format(Double.valueOf((lattice.getPrice()))/2)))+"-XXXXXX-XXXXXX-XXXXXX");//	更改数码管显示内容
					out.flush(); 
					
					
					JSONObject jsonMsg = JsonUtil.JsonMsg(true, "发送开门指令", "");
					WisdomLogger.logInfo("openDoor","已经发送开门指令");
					jsonMsg.put("errCode", "200"); 
					writeToFrontPage(jsonMsg);
				} catch (IOException e) {
					 e.printStackTrace();
				}
			}

		}else{
			JSONObject jsonMsg = JsonUtil.JsonMsg(false, "余额不足 无法开门！", "");
			WisdomLogger.logInfo("openDoor","余额不足 无法开门");
			jsonMsg.put("errCode", "100"); 
			writeToFrontPage(jsonMsg);
		}
	}

	public void getUrll() {
		Markpassword  m  = userManager.getMarkpassword("3");
		String url =  (m==null?"":m.getValue());//获取URL 路径
		WisdomLogger.logInfo("getUrl","url:"+url);

		JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "");
		WisdomLogger.logInfo("url","url");
		jsonMsg.put("url", url); 
		writeToFrontPage(jsonMsg);
	}

	public void saveFeedBack() {
		if(userId==null||userType==null||phone==null){
			JSONObject jsonMsg = JsonUtil.JsonMsg(false, "", "userId 或者 userType 或者 phone为空");
			writeToFrontPage(jsonMsg);
			return;
		}
		if(content==null||"".equals(content)){
			JSONObject jsonMsg = JsonUtil.JsonMsg(false, "", "内容为空");
			writeToFrontPage(jsonMsg);
			return;
		}
		try {
			content   = new String( content.getBytes("ISO-8859-1") , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
		FeedBack f = new FeedBack();
		f.setContent(content);
		f.setCreatetime(new Date());
		f.setPhone(phone);
		f.setUserId(userId+"");
		f.setUserType(userType);
		userManager.saveFeedBack(f);
		JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "反馈成功");
		writeToFrontPage(jsonMsg);
	}




	public  float  change(String res) {
		float f = Float.parseFloat(res);
		return f;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
