package com.highguard.Wisdom.struts.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.highguard.Wisdom.mgmt.hibernate.beans.Fruit;
import com.highguard.Wisdom.mgmt.hibernate.beans.ICPort;
import com.highguard.Wisdom.mgmt.hibernate.beans.InitStatus;
import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.hibernate.beans.LatticeStatus;
import com.highguard.Wisdom.mgmt.hibernate.beans.Latticestage;
import com.highguard.Wisdom.mgmt.hibernate.beans.SocketLog;
import com.highguard.Wisdom.mgmt.hibernate.beans.Trading;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.DeviceManager;
import com.highguard.Wisdom.mgmt.manager.FruitManager;
import com.highguard.Wisdom.mgmt.manager.IcportManager;
import com.highguard.Wisdom.mgmt.manager.SocketManager;
import com.highguard.Wisdom.mgmt.manager.TradingManager;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.common.GetPathCommon;
/**
 * 服务器 与 每个格子发生socket通信
 * @author Administrator
 *
 */
public class SocketToLattice  extends Thread {

	public final static HashMap<String, LockLattice> lock = new  HashMap<String, LockLattice>();

	public final String COLOR_HIGH ="4444";

	public final String COLOR_HIGH_LOW="3333";

	private boolean WORK_FLAG =true ;

	public void sclose(){
		if( socket != null ){
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		WORK_FLAG  = false;
	}



	/**
	 * 应答码10次为开锁锁定
	 * @author king
	 *
	 */
	private class LockLattice{
		private String latticeid ;
		private int    once;
		private long   locktime;
		private int    sendOk;  
		@Override
		public String toString() {
			return "LockLattice [latticeid=" + latticeid + ", once=" + once + ", locktime=" + locktime + "]";
		}
	}



	/**
	 * 充值信息
	 * @author king
	 *
	 */
	private class PayInfo{
		private int once;
		private String money;
		private Date payTime;
		@Override
		public String toString() {
			return "PayInfo [once=" + once + ", money=" + money + ", payTime=" + payTime + "]";
		}
	}

	private java.text.SimpleDateFormat sdf= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	Socket socket = null; // 保存与本线程相关的Socket对象  
	private String newCard ;
	private BufferedReader in;
	private PrintWriter out;
	private String lattice;//设备地址
	private String card;   //用户的ic卡号
	private LoggerLocal logger; 
	public  GetPathCommon common = new GetPathCommon();	
	private int threadNumber = 0;
	// 用户重置信息
	private final static HashMap<String,PayInfo> payMoney = new HashMap<String,PayInfo>();

	public SocketToLattice(Socket socket, LoggerLocal log, int threadNumber_ ) { // 构造函数  
		this.logger = log;
		this.socket = socket; // 初始化socket变量  
		this.threadNumber = threadNumber_;
	}  

	public HashMap<String,String> moneyTicket = new HashMap<String,String>();{
		moneyTicket.put("006.004.000.145","006.004.000.145"); 
	}

	public void run() {
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		try { 
			// 线程主体   
			UserManager userManager = (UserManager) ApplicationUtil.act.getBean("userManager");
			DeviceManager deviceManager = (DeviceManager) ApplicationUtil.act.getBean("deviceManager");
			TradingManager tradingManager = (TradingManager) ApplicationUtil.act.getBean("tradingManager");
			IcportManager icportManager = (IcportManager) ApplicationUtil.act.getBean("icportManager");
			SocketManager socketManager = (SocketManager) ApplicationUtil.act.getBean("socketManager");
			FruitManager fruitManager  = (FruitManager) ApplicationUtil.act.getBean("fruitManager");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);  
			this.setName("SOCKENAME-"+threadNumber+" SOCKET");
			while( WORK_FLAG ){
				String line = in.readLine();
				if( line == null ){ 
					socket.close();
					//  防止有没有重置完成的命令
					if( lattice != null ){
						if( moneyTicket.containsKey( lattice ) && card != null){
							logger.log("操作日志|"+lattice+"|"+threadNumber+"|投币机上行null关闭之|"+line, true );
							payMoney(card,userManager, socketManager,lattice, out);
						}
					}  
					break;
				} 
				if( logger !=  null && line != null && !line.trim().equals("null")){
					if( line.indexOf("R-ID") ==-1 ){
						logger.log("",true);
						if( lattice == null  ){
							logger.log("终端输入|"+threadNumber+"|"+line, true );
						}else{
							logger.log("终端输入|"+lattice+"|"+threadNumber+"|"+line, true );
						}
					}
				}  
				if( null != line && !line.trim().equals("null")){
					String [] noticeCommand = line.split(" ");
					if( noticeCommand != null ){
						if( noticeCommand.length == 2 && noticeCommand[0].toUpperCase().startsWith("OK") && noticeCommand[1].toUpperCase().startsWith("SPTP")){
							// OK SPTP-006.004.000.050-7000FF9B00
							String [] command = noticeCommand[1].split("-");
							if(  command.length == 3 ){
								if( command[2].startsWith("7000FF") && !moneyTicket.containsKey(command[1]) ){  // 需要重复发送10此开门指令
									logger.log("操作日志|"+lattice+"|"+threadNumber+"|收到7000FF应答码，发送3此开锁指令",true);
									for (int j = 0; j < 3; j++) {
										out.println("SPTP-1234-"+command[1]+"-1234-7FFF");//	
										out.flush();
										try {
											Thread.sleep(1000);
										} catch (InterruptedException e) {
											e.printStackTrace();
										} 
									}
								} 
							}
						}
					}

					String[] accept = line.split("-"); 

					// 用户刷卡上报，刷卡是触发  
					if( accept.length == 5 && accept[3].startsWith("7310") ){//说明是刷卡上报
						saveLog(socketManager, "", "", accept[1].trim(), "刷卡上报 ："+line, "接收");
						out.println("stop");
						latticeFlag(accept[1].trim(), deviceManager);//修改格子标示状态
						String icid = accept[4];//获取IC卡号
						card = icid;

						//IC卡管理
						if( icid.equals("000102030405060708090A0B0C0D0E0F") || icid.startsWith("0000000000000000") ){//说明是新卡 需要修改卡号
							logger.log("操作指令|"+lattice+"|"+threadNumber+"|读到的是新卡 需要修改卡号 -->读到的是新卡 需要修改卡号---->"+icid, true);
							String newcard = getRandomString();
							logger.log("操作指令|"+lattice+"|"+threadNumber+"|读到的是新卡 需要修改卡号 -->读到的是新卡 需要修改卡号---->"+newcard, true);
							Thread.sleep(1000);
							newCard = newcard;
							out.println("ICID-1234-"+accept[1].trim()+"-1234"+"-"+newcard);//
							logger.log("下发指令|"+lattice+"|"+threadNumber+"|读到的是新卡 需要修改卡号 -->读到的是新卡 需要修改卡号---->ICID-1234-"+accept[1].trim()+"-1234"+"-"+newcard,true);
							continue;
						}

						// 如果是投币箱指令，默认下发开启验钞机命令！
						if( accept[1] != null ){
							if( moneyTicket.containsKey( accept[1] ) ){
								logger.log("日志记录|投币机|"+lattice+"|"+card,true);
								if( card != null && card.length() > 5){
									if( moneyTicket.get( accept[1] ).equals( card )){  // 第二次刷卡
										logger.log("日志记录|投币机|"+lattice+"|"+threadNumber+"|"+card+"|二次刷卡，完成充值投币，关闭投币机！",true);
										payMoney(card,userManager, socketManager,accept[1], out);
										moneyTicket.put( accept[1], "");
										continue;
									}
								}
								if( card != null && card.length() > 0 ){
									User user1 = userManager.getUserByCard( card );
									logger.log("日志记录|投币机|"+lattice+"|"+threadNumber+"|"+card+"|判断用户是否是空白用户，如果是发送办卡指令给视频坐席，user="+user1,true);
									if( user1 == null ){
										logger.log("日志记录|投币机|"+lattice+"|"+threadNumber+"|"+card+"|判断用户是否是空白用户，如果是发送办卡指令给视频坐席，用户不存在！发给坐席",true);
										openCard(card, accept[1]);
										continue;
									}
								}
								out.println("SPTP-1234-"+accept[1]+"-1234-7FFF");//
								logger.log("下发指令|"+lattice+"|"+threadNumber+"|下发开启验钞机命令---->SPTP-1234-"+accept[1]+"-1234-7FFF",true);
								out.flush();
								if( card != null && card.length() > 5 ){
									moneyTicket.put(accept[1], card);
								}
								continue;
							}
						}

						// 用户立马刷新旁边的柜子,执行立即充值
						if( payMoney.containsKey( card ) && moneyTicket.containsKey( accept[1]) ){
							logger.log("操作指令|"+lattice+"|"+threadNumber+"|当前用户card:"+card+"尚未完成充值，直接刷了旁边的柜子|payMoney="+payMoney,true);
							payMoney(card,userManager, socketManager,accept[1], out);
							moneyTicket.put( accept[1], "");
						}

						// READ-006.004.000.140-000-7310000600-01020304050671446202965205ABCDEF
						Map<String,String> map = new HashMap<String,String>();
						map.put("lockid", accept[1].trim());
						map.put("date", new Date().getTime()+"");//目前时间
						if( deviceManager.getLatticeListCount(map)==0 ){//说明不存在这个设备
							logger.log("操作命令|"+lattice+"|"+threadNumber+"|lockid="+accept[1]+",不存在！！！！",true );
							lattice = accept[1];
							deviceManager.saveLatticeStatus(lattice,accept[4].trim());//保存设备连接状态,记录格子的连接时间，连接状态，这个没有异议
						}else{
							lattice=accept[1];
							deviceManager.saveLatticeStatus(lattice,accept[4].trim());//保存设备连接状态
							User user = isUser(icid,userManager); // 获取持卡用户信息
							logger.log("操作命令|"+lattice+"|"+threadNumber+"|icid="+icid+"|user="+user,true );
							if( null != user){//存在用户
								//设定的报警价格，也就是10元
								int ye =Integer.parseInt(userManager.getMarkpassword("2").getValue());
								logger.log("操作命令|"+lattice+"|"+threadNumber+"|检查用户的余额："+change(user.getBalance())+",数据库设定的最低额度="+ye,true );
								if( change(user.getBalance()) >= (float)ye ){
									//用户刷卡后 就显示单价
									Lattice lat = deviceManager.getLatticeBylockid(lattice);

									boolean isPromotion = false; 
									// 开启了促销价格
									if( lat.getPromotiontime() != null && lat.getPromotiontime().length() >10  ){
										logger.log("日志记录|"+lattice+"|"+threadNumber+"|当前格子已经开启了疯狂的促销，开启促销时间："+lat.getPromotiontime(),true  );
										if( millisecond(lat.getPromotiontime()) > 300 && lat.getPromotionprice() != null  ){
											isPromotion = true;
											logger.log("日志记录|"+lattice+"|"+threadNumber+"|当前格子已经开启了疯狂的促销，当前促销价格："+lat.getPromotionprice() ,true );
										}
									} 
									LatticeStatus lss = deviceManager.getLatticeStatusById(lattice);
									lss.setReadstate("0");
									lss.setCard(card);
									deviceManager.updateLatticeStatus(lss);

									String price = lat.getPrice();
									if( isPromotion ){
										price = lat.getPromotionprice();
										logger.log("日志记录|"+lattice+"|"+threadNumber+"|当前格子将以促销价格显示："+price,true  );
									}
									// 发送当前个字的单价和用户账户金额
									// WSMG-1234-123.334.44.55-公斤价/2-XXXXX0-XXXXX0-XXXXX120.0

									// 2017/02/18 促销价格调整 ，改为了： 单价-促销价格-购买重量-消费金额，去掉了卡内余额
									{
										String Command  = "";
										if( isPromotion ){
											Command = "WSMG-1234-"+lattice+"-"+COLOR_HIGH+"-XXXXXX-"+doubleToString(Double.valueOf(df.format(Double.valueOf((price))/2)))+"-XXXXXX-XXXXXX";
										}else{
											Command = "WSMG-1234-"+lattice+"-"+COLOR_HIGH+"-"+doubleToString(Double.valueOf(df.format(Double.valueOf((price))/2)))+"-XXXXXX-XXXXXX-XXXXXX";
										}
										//Command = "WSMG-1234-"+lattice+"-"+COLOR_HIGH+"-"+doubleToString(Double.valueOf(df.format(Double.valueOf((price))/2)))+"-XXXXX0-XXXXX0-"+doubleToString(Double.parseDouble(user.getBalance()));
										saveLog(socketManager, user.getName(), user.getTelephone(), lattice, " 刷卡后显示单价"+ Command, "发送");
										logger.log("下发指令|"+lattice+"|"+threadNumber+"|刷卡后显示单价---->"+Command,true );
									}
									// 查询格子重量
									out.println("RDKG-1234-"+accept[1]);//
									out.flush();

									logger.log("下发指令|"+lattice+"|"+threadNumber+"|开门查询格子重量---->RDKG-1234-"+accept[1],true );
									saveLog(socketManager, user.getName(), user.getTelephone(), lattice, " 开门查询格子重量："+"RDKG-1234-"+accept[1], "发送");
								} 
							} 
						}
					}


					/**
					 * 客户端建立连接，获取当前格子的价格等，服务端缓存Socket信息，key的格子ID
					 */
					if( accept.length == 3 && accept[1].startsWith("ID") ){
						lattice = accept[2].toString();

						// 每次有心跳上行的时候，自动清理定时器
						SocketThered.timeout.put(lattice, System.currentTimeMillis());
						// 
						//if( !SocketThered.map.containsKey( lattice )  ){
//						SocketThered.map.put(lattice, socket);
						//}
						String lattce = accept[2];//格子编号 
						InitStatus init = deviceManager.getInitStatusByLattice(lattce);
						if( init == null){// 如果不存在 initstatus数据 新建
							init = new InitStatus();
							init.setCreatetime("0");
							init.setLattice(lattce);
							init.setStatus("1");
							deviceManager.saveInitStatus(init);
						}else{
							long now = new Date().getTime();
							long createtime = Long.parseLong(init.getCreatetime());
							if( now >= createtime && init.getStatus().equals("0") ){//没有发送 并且 时间到了
								Lattice tice = deviceManager.getLatticeBylockid(lattice);
								double price = Double.valueOf( tice.getPrice());      //单价 
								boolean isPromotion = false; 
								// 开启了促销价格
								if( tice.getPromotiontime() != null && tice.getPromotiontime().length() >10  ){
									logger.log("日志记录|A"+lattice+"|"+threadNumber+"|当前格子已经开启了疯狂的促销，开启促销时间："+tice.getPromotiontime() ,true);
									if( millisecond(tice.getPromotiontime()) > 300 && tice.getPromotionprice() != null  ){
										isPromotion = true;
										logger.log("日志记录|A"+lattice+"|"+threadNumber+"|当前格子已经开启了疯狂的促销，当前促销价格："+tice.getPromotionprice(),true );
									}
								} 

								double price1 = Double.parseDouble(tice.getPrice());
								if( isPromotion ){
									price1 = Double.parseDouble(tice.getPromotionprice());
									logger.log("日志记录|"+lattice+"|"+threadNumber+"|A当前格子将以促销价格显示："+price ,true);
								}

								// 下发显示价格
								{
									String Command = "WSMG-1234-"+lattice+"-"+COLOR_HIGH+"-"+doubleToString(price1/2)+"-XXXXXX-XXXXXX-XXXXXX";
									if( isPromotion ){
										Command = "WSMG-1234-"+lattice+"-"+COLOR_HIGH+"-XXXXXX-"+doubleToString(price1/2)+"-XXXXXX-XXXXXX";
									}
									logger.log("下发指令|"+lattice+"|"+threadNumber+"|此次启用促销价格显示---->"+Command,true);
									out.println(Command);//	更改数码管显示内容
									out.flush();
								}
								init.setStatus("1");
								deviceManager.updateInitStatus(init);
							}
						}
					}

					/**
					 * 称重上报数据
					 */
					if( accept.length == 2 && accept[0].startsWith("RDKG") && lattice != null ){
						logger.log("日志记录|"+lattice+"|获得格子的称重上报数据！！！！>"+ line ,true);
						latticeFlag(lattice, deviceManager);//修改格子标示状态
						out.println("stop");  // Stop是让终端不在上报数据
						out.flush();
						LatticeStatus lss = deviceManager.getLatticeStatusById(lattice);
						String card = lss.getCard(); 
						//开锁时候    获取格子的重量
						// 格子的状态  （ -1：刷卡上报 0 刷卡上报成功 然后获得重量  1 关门获得重量  3 是 关门以后）
						String readState = lss.getReadstate();
						if( readState != null && readState.equals("0")){  // 刷卡上报成功
							if( isNum(accept[1]) == false ){
								out.println("WSMG-1234-"+lattice+"-1234"+"--------");//	更改数码管显示内容
								logger.log("下发指令|"+lattice+"|"+threadNumber+"|读到乱码 错误提示---->"+"WSMG-1234-"+lattice+"-1234"+"--------",true);
								continue;
							}
							saveLog(socketManager, "", "", lattice, "开锁获取重量 "+line, "接受");
							double weight =	Double.valueOf(accept[1])/1000;//格子的重量 存入数据库 
							deviceManager.updateLatticeWeight(weight+"", lattice);
							Latticestage stage = new Latticestage();
							stage.setCreatetime(new Date());
							stage.setLattice(lattice);
							stage.setWeight(weight+"");
							deviceManager.saveLatticestage(stage);
							// 开锁指令
							out.println("SPTP-1234-"+lattice+"-1234-7FFF");
							out.flush();//发送开锁命令
							saveLog(socketManager, "", "", lattice,  "发送开锁命令"+"SPTP-1234-"+lattice+"-1234-7FFF", "发送");
							logger.log("下发指令|"+lattice+"|"+threadNumber+"|开锁指令"+"SPTP-1234-"+lattice+"-1234-7FFF",true);
						}else if( readState != null && readState.equals("1")){ // 关锁后   获取格子的重量
							logger.log("日志记录|"+lattice+"|"+threadNumber+"|说明关锁后 再次获得格子重量"+accept[1]+" 格子为"+lattice+" 改变状态为3",false);
							lss.setReadstate("3");
							lss.setCard("");
							deviceManager.updateLatticeStatus(lss);
							if( isNum(accept[1]) == false ){
								out.println("stop-WSMG-1234-"+lattice+"-1234"+"--------");//	更改数码管显示内容
								logger.log("下发指令|"+lattice+"|"+threadNumber+"|开锁指令---->"+"SPTP-1234-"+lattice+"-1234-7FFF",true);
								continue;
							}
							//获取格子的信息  内有水果的价格
							Lattice tice = deviceManager.getLatticeBylockid(lattice);
							double oldweight = Double.valueOf(df.format(Double.valueOf(tice.getWeight())));
							double weight    = Double.valueOf(df.format(Double.valueOf(accept[1])/1000));  // 格子的重量 存入数据库 
							Latticestage stage = new Latticestage();
							stage.setCreatetime(new Date());
							stage.setLattice(lattice);
							stage.setWeight(weight+"");
							Trading trading = new Trading();
							int  isPromotion = 0;
							int  beginPromotion = 0 ;
							User user = userManager.getUserByCard(card);//获取这个IC卡用户的信息
							logger.log("日志记录|"+lattice+"|"+threadNumber+"|关门后重量变化>"+"原始重量"+oldweight+"目前重量"+weight ,true);
							saveLog(socketManager, "", "", lattice, "关锁后 再次获得格子重量 "+line+" 即 " + "原始重量"+oldweight+"目前重量"+weight, "接收");
							// 管理员往里面放入水果了
							if( oldweight <= weight ){//数据库存在的最后一次重量 小于 关闭锁以后的重量 （有人往里放入水果了或者是其他东西）
								double price =  Double.valueOf(tice.getPrice());//单价
								// 让机子显示最新价格
								sendToLatticeTenth(lattice, out, "WSMG-1234-"+lattice+"-"+COLOR_HIGH+"-"+doubleToString(price/2)+"-XXXXXX-XXXXXX-XXXXXX", deviceManager);
								logger.log("日志记录|"+lattice+"|"+threadNumber+"|无重量变化 或者往里放东西---->"+"WSMG-1234-"+lattice+"-"+COLOR_HIGH+"-"+doubleToString(price/2)+"-XXXXXX-XXXXXX-XXXXXX",true);
							}else if( oldweight > weight ){//有人买水果了


								Lattice lat = deviceManager.getLatticeBylockid(lattice);
								boolean isPromotion1 = false; 
								// 开启了促销价格
								if( lat != null ) {
									if( lat.getPromotiontime() != null && lat.getPromotiontime().length() >10  ){
										logger.log("日志记录|"+lattice+"|"+threadNumber+"|A当前格子已经开启了疯狂的促销，开启促销时间："+lat.getPromotiontime() ,true);
										if( millisecond(lat.getPromotiontime()) > 300 && lat.getPromotionprice() != null  ){
											isPromotion1 = true;
											logger.log("日志记录|"+lattice+"|"+threadNumber+"|A当前格子已经开启了疯狂的促销，当前促销价格："+lat.getPromotionprice(),true );
										}
									} 
								}

								double ye    =  Double.valueOf(df.format(Double.valueOf(user.getBalance())));//用户余额
								double num   =  oldweight - weight;//购买数量
								double price =  Double.valueOf(tice.getPrice());//单价
								if( isPromotion1 && tice.getPromotionprice() != null && tice.getPromotionprice().length() > 0){
									price = Double.valueOf(tice.getPromotionprice());//促销单价
									logger.log("日志记录|"+lattice+"|"+threadNumber+"|A当前格子已经开启了疯狂的促销，当前促销价格："+price+",此用户此次消费按照促销价格扣费",true);
								}
								double xf    =  Double.valueOf(user.getConsumption());//用户消费金额

								logger.log("日志记录|"+lattice+"|"+threadNumber+"| 1>当前用户账户余额：Double.valueOf(df.format(Double.valueOf(user.getBalance())))="+weight,true);
								logger.log("日志记录|"+lattice+"|"+threadNumber+"| 1>拿取数量： oldweight - weight="+num+", oldweight="+oldweight+",weight="+weight,true);
								logger.log("日志记录|"+lattice+"|"+threadNumber+"| 1>物品单价：Double.valueOf(tice.getPrice())="+price,true);
								logger.log("日志记录|"+lattice+"|"+threadNumber+"| 1>消费总额：Double.valueOf(user.getConsumption())="+xf,true);
								ye = Double.valueOf(df.format(ye - num*price));  // 余额
								xf = Double.valueOf(df.format(xf + num*price)); // 总共消费多少 
								logger.log("日志记录|"+lattice+"|"+threadNumber+"| 1>本次消费后余额：Double.valueOf(df.format(ye - num*price))="+ye,true);
								logger.log("日志记录|"+lattice+"|"+threadNumber+"| 1>本次消费金额：num*price="+(num*price),true);
								logger.log("日志记录|"+lattice+"|"+threadNumber+"| 1>当前账户余额：="+ye+",原来格子的重量：="+oldweight+",当前格子重量：="+weight+",价格="+price+",用户历史消费总额="+xf ,true);
								// 开启了促销
								logger.log("日志记录|"+lattice+"|"+threadNumber+"|检查促销设置|"+tice ,true);
								if( tice != null && tice.getIspromotion() != null  && tice.getIspromotion() == 1 ){
									logger.log("日志记录|"+lattice+"|"+threadNumber+"|此箱子开启了促销---->"+ tice.getLockid()+" | "+tice.getPrice() ,true );
									if( tice.getPromotionprice() != null && tice.getPromotionweight() != null ){
										logger.log("日志记录|"+lattice+"|"+threadNumber+"|此箱子开启了促销---->促销价格："+ tice.getPromotionprice()+" 元|促销重量："+tice.getPromotionweight() +" 斤",true );
										double promotionPrice =  Double.valueOf( tice.getPromotionprice() );    // 促销价格
										double promotionWeight=  Double.valueOf( tice.getPromotionweight() );   // 促销重量
										logger.log("日志记录|"+lattice+"|"+threadNumber+"|此箱子开启了促销---->开启促销时间："+ tice.getPromotiontime() ,true);
										logger.log("日志记录|"+lattice+"|"+threadNumber+"|此箱子开启了促销---->开启促销时间和现在相差分钟数："+ millisecond(tice.getPromotiontime()) ,true);
										// 必须原始重量低于促销重量才开始促销！！
										if( oldweight <= promotionWeight && millisecond(tice.getPromotiontime()) > 300 ){
											price = promotionPrice;   
											isPromotion = 1;
											logger.log("日志记录|"+lattice+"|"+threadNumber+"|此箱子开启了促销---->当前结算价格为："+price,true );
										}
										if( weight <= promotionWeight ){
											beginPromotion = 1;
										} 
									}
								}
								if( user != null && user.getTelephone() != null && user.getTelephone().length() == 11 && ye <= 10  ){
									logger.log("日志记录|"+lattice+"|"+threadNumber+"|余额不足："+ user.toString()+", 账户余额小于10元--启动短信通知",true );
									String  msg ="尊敬的用户："+ user.getName() +",系统检测您账户余额不足10元，为了更好的服务，请尽早充值，感谢您的使用！" ;
									logger.log("日志记录|"+lattice+"|"+threadNumber+"|余额不足："+user.getName()+",tel="+ user.getTelephone()+",balance="+user.getBalance(),true );
									logger.log("日志记录|"+lattice+"|"+threadNumber+"|余额不足："+user.getName()+",msg="+ msg,true );
									logger.log("日志记录|"+lattice+"|"+threadNumber+"|余额不足："+user.getName()+",tel="+ user.getTelephone()+",发送标志："+sendMsg(user.getTelephone(),msg),true );
								} 
								if( tice != null && user.getType() != null && user.getType().equals("2") ){ // 说明是普普通通用户才扣钱
									user.setBalance(String.valueOf(ye));
									user.setConsumption(String.valueOf(xf));
									user.setLastdevice( tice.getDeviceid()+"" );
									user.setLastlattice( tice.getId()+"" );
									user.setLastpaytime( sdf.format( new Date())  );
									userManager.updateUser(user);
								}
								String fruitName = "未知";
								if( fruitManager != null ){
									Fruit fruit = fruitManager.getFruitById( tice.getFruitid() );
									if( fruit != null ){
										fruitName = fruit.getProductname();
									}
								} 
								logger.log("日志记录|"+lattice+"|"+threadNumber+"|A消费提醒：user="+user,true );
								if( user != null ){
									logger.log("日志记录|"+lattice+"|"+threadNumber+"|A消费提醒：user.getTelephone()="+user.getTelephone(),true );
									logger.log("日志记录|"+lattice+"|"+threadNumber+"|A消费提醒：Math.abs(num*price)="+Math.abs(num*price),true );
								}
								if(     user != null && 
										user.getTelephone() != null && 
										user.getTelephone().length() == 11  && 
										Math.abs(num*price) > 0.1  ){
									String Paymsg= "尊敬的用户："+ user.getName()+",此次您购买的水果（"+fruitName+"）共计消费:"+df.format(Math.abs(num*price))+"元，您的账户余额："+ df.format(ye)+"元，"+(isPromotion==1?"此次您购买的水果为促销价，原价为："+tice.getPrice()+"元,促销价为:"+ tice.getPromotionprice()+"元":"")+",欢迎您的使用！";
									//saveMsgRecord(user.getId()+"", user.getTelephone(),Paymsg,"XFTX");// 记入短信发送内容

									logger.log("日志记录|"+lattice+"|"+threadNumber+"|A消费提醒：num="+num+" 斤,price="+price+" 元",true );
									logger.log("日志记录|"+lattice+"|"+threadNumber+"|A消费提醒："+Paymsg,true  ); 
									logger.log("日志记录|"+lattice+"|"+threadNumber+"|A消费提醒："+user.getName()+",tel="+ user.getTelephone()+", 发送标志："+sendMsg(user.getTelephone(),Paymsg),true );
								} 
								trading.setBalance(String.valueOf(ye));
								trading.setWx(user.getWx());
								trading.setUsername(user.getName());
								trading.setConsumption(df.format(num*price)+"");
								trading.setCreatetime(new Date());
								trading.setStatus("1");//买水果
								trading.setTakeway("0");
								trading.setFruitid(tice.getFruitid());
								trading.setDeviceid(tice.getDeviceid());
								trading.setLatticeid(tice.getId());
								trading.setFruitnum(df.format(num)+"");
								trading.setUserid(user.getId());
								tradingManager.saveTrading(trading);
								// WSMG-1234-198.390.338.399-1234-价格/2-数量*2-数量*价格-用户余额


								// 2017/02/18 促销价格调整 ，改为了： 单价-促销价格-购买重量-消费金额，去掉了卡内余额
								{
									String command = "WSMG-1234-"+lattice+"-4444"+"-"+doubleToString(price/2)+"-XXXXXX-"+doubleToString1(num*2)+"-"+doubleToString1(num*price);
									if( isPromotion1 ){
										command = "WSMG-1234-"+lattice+"-4444"+"-XXXXXX-"+doubleToString(price/2)+"-"+doubleToString1(num*2)+"-"+doubleToString1(num*price);
									}
									// String command = "WSMG-1234-"+lattice+"-4444"+"-"+doubleToString(price/2)+"-"+doubleToString1(num*2)+"-"+doubleToString1(num*price)+"-"+doubleToString(ye);
									sendToLatticeTenth(lattice, out,command, deviceManager);
									logger.log("下发指令|"+lattice+"|"+threadNumber+"|更改数码管显示内容："+ command,true );
									saveLog(socketManager, "", "", lattice, "关门显示 "+"WSMG-1234-"+lattice+"-4444"+"-"+command, "发送");
								}
								//10秒钟后 再次显示 单价-0-0-0
								InitStatus init = deviceManager.getInitStatusByLattice(lattice);
								init.setCreatetime((new Date().getTime()+10000)+"");
								init.setStatus("0");
								deviceManager.updateInitStatus(init);
								Thread.sleep(5000); 

								init.setStatus("1");
								deviceManager.updateInitStatus(init);
								{
									String command = "WSMG-1234-"+lattice+"-"+COLOR_HIGH+"-"+doubleToString(price/2)+"-XXXXXX-XXXXXX-XXXXXX";
									if( isPromotion1 ){
										command  = "WSMG-1234-"+lattice+"-"+COLOR_HIGH+"-XXXXXX-"+doubleToString(price/2)+"-XXXXXX-XXXXXX";
									}
									sendToLatticeTenth(lattice, out, command , deviceManager);
									saveLog(socketManager, "", "", lattice, "5秒后更改数码管显示内容 "+command, "发送");
									logger.log("下发指令|"+lattice+"|"+threadNumber+"|5秒后更改数码管显示内容---->"+command ,true );
								}
							}
							tice.setDate(new Date().getTime()+Integer.parseInt(common.getCommonDir("date"))*1000+"");
							tice.setWeight(oldweight-weight+"");
							if( beginPromotion == 1 ){
								tice.setPromotiontime( sdf.format( new Date()) );  // 开启促销时间
							}else{
								tice.setPromotiontime( ""); // 关闭促销时间
							}
							deviceManager.updateLattice(tice);
							deviceManager.updateLatticeWeight(Double.valueOf(accept[1])/1000+"", lattice);
							deviceManager.saveLatticestage(stage);
						}
					}

					//关闭锁 命令
					if(accept.length == 4&&accept[3].startsWith("7000")){//说明 锁关了 再去查询格子的重量
						logger.log( "操作日志|"+lattice+"|7000|"+line,true );
						// READ-006.004.000.140-000-7000010400

						LockLattice  lLattice = lock.get(accept[1]);
						if( lLattice != null && lLattice.once >= 10 ) {
							if( lLattice.sendOk == 0 ){
								logger.log( "操作日志|"+lattice+"|"+threadNumber+"|收到7000FF应答码，当前重复开锁次数已经达到10次="+lLattice.once+",机器自动锁定:",true );
								//String Paymsg= "尊敬管理员用户："+accept[1]+",已经超过10 次未能打开门，此门已经自动锁定，请注意解锁！";
								//saveMsgRecord("", "18611303489", Paymsg,"BJTX");// 记入短信发送内容
								//saveMsgRecord("", "15811052988", Paymsg,"BJTX");// 记入短信发送内容
								//logger.log( "操作日志|"+lattice+"|"+threadNumber+"|报警短信发送完毕="+lLattice.once+",锁定价格",true );
								lLattice.sendOk = 1;
							}
						}

						if( accept[3].startsWith("7000FF") && !moneyTicket.containsKey(accept[1])){  // 需要重复发送10此开门指令
							LockLattice  lockLattice = lock.get(accept[1]);
							if( lockLattice == null){
								lockLattice = new LockLattice();
								lockLattice.latticeid = accept[1];
								lockLattice.locktime  = System.currentTimeMillis();
								lockLattice.once = 1; 
								lock.put(accept[1] , lockLattice);
							}else{
								lockLattice.locktime = System.currentTimeMillis();
								lockLattice.once = lockLattice.once+1; 
							}

							if( lLattice != null && lLattice.once < 10 ) {
								logger.log( "操作日志|"+lattice+"|"+threadNumber+"|收到7000FF应答码，发送10此开锁指令，lLattice.once="+lLattice.once,true );
								for (int j = 0; j < 3; j++) {
									out.println("SPTP-1234-"+accept[1]+"-1234-7FFF");//	
									out.flush();
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									} 
								}
							}
							continue;
						} 

						out.println("stop");
						LatticeStatus lss = deviceManager.getLatticeStatusById(accept[1]);
						logger.log("日志记录|"+lattice+"|"+threadNumber+"|接受关门指令---->"+"关门 "+line,true );
						saveLog(socketManager, "", "", accept[1], "关门 "+line, "接受");
						if(lss.getReadstate()!=null&&lss.getReadstate().equals("3")){
							logger.log("日志记录|"+lattice+"|"+threadNumber+"|门已经关了，不需要再关门>"+"关门 "+line,true );
						}else{
							latticeFlag(accept[1], deviceManager);//修改格子标示状态
							Thread.sleep(3000);
							logger.log("日志记录|"+lattice+"|"+threadNumber+"|关锁后 3秒再次查询格子重量",true );
							lss.setReadstate("1");
							deviceManager.updateLatticeStatus(lss);
							lattice = accept[1];
							out.println("stop-RDKG-1234-"+accept[1]);//查询格子重量
							out.flush();
							saveLog(socketManager, "", "", accept[1], "关锁后 3秒再 查询重量 "+"stop-RDKG-1234-"+accept[1], "发送");
							logger.log("下发指令|"+lattice+"|"+threadNumber+"|关锁后 3秒再 查询重量---->stop-RDKG-1234-"+accept[1],true );
						}
					}

					// 投币机接受投币消息
					if( accept.length == 4 && accept[3].startsWith("700") && moneyTicket.containsKey(accept[1])){
						saveLog(socketManager, "", "", lattice, "收到充值上报 "+line, "接收");
						out.println("STOP");//
						out.flush();
						LatticeStatus lss = deviceManager.getLatticeStatusById(accept[1].trim());
						if( card == null ){
							card = lss.getCard();
						}
						if( accept[2].equals("000") ){ 
							saveLog(socketManager, "", "", lattice, "超过60秒没投币 "+line, "接收");
							logger.log("日志记录|"+lattice+"|"+threadNumber+"|超过60秒没投币！！！",true );
							// 下发关闭投币功能
							saveLog(socketManager, "", "", lattice, "发送3次闭锁指令 "+"SPTP-1234-"+accept[1]+"-1234-BFFF", "下发");
							if( payMoney.containsKey( card ) ){
								payMoney(card,userManager, socketManager,accept[1], out);
							}
							closeMoneyBox( accept[1], out );
							logger.log("下发指令|"+lattice+"|"+threadNumber+"|关闭验钞机---->SPTP-1234-"+accept[1]+"-1234-BFFF",true );
							continue;
						}else{
							if( payMoney.containsKey( card ) ){
								PayInfo payInfo = payMoney.get( card );
								payInfo.once = payInfo.once+1;
								payInfo.money = tranMoney(accept[2]);
								payInfo.payTime = new Date();
							}else{
								PayInfo payInfo = new PayInfo();
								payInfo.once = 1;
								payInfo.money = tranMoney(accept[2]);
								payInfo.payTime = new Date();
								payMoney.put( card , payInfo);
							} 
							saveLog(socketManager, "", "", lattice, "接收到投币金额： "+payMoney, "接收");
							logger.log("充值指令|"+lattice+"|"+threadNumber+"|投币金额>"+payMoney+"|card="+card ,true );
							saveLog(socketManager, "", "", lattice, "充值卡号： "+card, "接收");
						} 
					}

					// 别人拿了东西，立即会触发重量变化，所以地方不能做计费处理，重量变化上报
					if( accept.length == 4 && accept[3].startsWith("75")){
						out.println("stop");
						out.flush();
						latticeFlag(accept[1].trim(), deviceManager);//修改格子标示状态
						LatticeStatus lss = deviceManager.getLatticeStatusById(accept[1].trim());
						if( lss.getReadstate().equals("3") ){//此时已经关门了 就不再显示重量变化上报了   
							logger.log("日志记录|"+lattice+"|"+threadNumber+"|门都关了 就不显示重量上报了",true );
							continue;
						} 
						saveLog(socketManager, "", "", accept[1].trim(), "重量变化上报 "+line, "接受");
						//判断设备号是否存在 不存在自动创建 
						Map<String,String>map = new HashMap<String,String>();
						map.put("lockid", accept[1].trim());
						Lattice tice = deviceManager.getLatticeBylockid(accept[1].trim());//获取格子的信息  内有水果的价格
						LatticeStatus stage = deviceManager.getLatticeStatusById(accept[1].trim());
						if( stage.getCard()==null || "".equals(stage.getCard())){
							continue;
						}
						if( tice == null || tice.getWeight() == null || tice.getWeight().equals("") ){
							continue;
						}


						Lattice lat = deviceManager.getLatticeBylockid(lattice);
						boolean isPromotion1 = false; 
						// 开启了促销价格
						if( lat != null ) {
							if( lat.getPromotiontime() != null && lat.getPromotiontime().length() >10  ){
								logger.log("日志记录|"+lattice+"|"+threadNumber+"|A当前格子已经开启了疯狂的促销，开启促销时间："+lat.getPromotiontime(),true  );
								if( millisecond(lat.getPromotiontime()) > 300 && lat.getPromotionprice() != null  ){
									isPromotion1 = true;
									logger.log("日志记录|"+lattice+"|"+threadNumber+"|A当前格子已经开启了疯狂的促销，当前促销价格："+lat.getPromotionprice(),true  );
								}
							} 
						}


						User user = userManager.getUserByCard(stage.getCard());
						double weight =Double.valueOf(df.format(Double.valueOf(accept[3].substring(4))/1000));//格子的重量 存入数据库 
						double ye = Double.valueOf(df.format(Double.valueOf(user.getBalance())));//用户余额
						double num =Double.valueOf(tice.getWeight())- weight;//购买数量
						double price =  Double.valueOf(tice.getPrice());//单价
						if( isPromotion1 && tice.getPromotionprice() != null && tice.getPromotionprice().length() > 0){
							price = Double.valueOf(tice.getPromotionprice());//促销单价
							logger.log("日志记录|"+lattice+"|"+threadNumber+"|B当前格子已经开启了疯狂的促销，当前促销价格："+price+",此用户此次消费按照促销价格扣费",true  );
						}
						double xf =  Double.valueOf(user.getConsumption());//用户消费金额
						ye = Double.valueOf(df.format(Double.valueOf(df.format(ye-num*price))));//余额
						xf = num*price;//总共消费多少  
						logger.log("日志记录|"+lattice+"|"+threadNumber+"|上报重量：Double.valueOf(df.format(Double.valueOf(accept[3].substring(4))/1000))="+weight,true );
						logger.log("日志记录|"+lattice+"|"+threadNumber+"|当前余额：Double.valueOf(df.format(Double.valueOf(user.getBalance())))="+ye,true );
						logger.log("日志记录|"+lattice+"|"+threadNumber+"|拿取数量：Double.valueOf(tice.getWeight())- weight)="+num,true );
						logger.log("日志记录|"+lattice+"|"+threadNumber+"|物品单价：Double.valueOf(tice.getPrice())="+price,true );
						logger.log("日志记录|"+lattice+"|"+threadNumber+"|消费总额：Double.valueOf(user.getConsumption())="+xf,true );
						logger.log("日志记录|"+lattice+"|"+threadNumber+"|本次消费后余额：Double.valueOf(df.format(Double.valueOf(df.format(ye-num*price))))="+ye,true );
						logger.log("日志记录|"+lattice+"|"+threadNumber+"|本次消费金额：num*price="+xf,true );
						{
							String command = "WSMG-1234-"+accept[1].trim()+"-"+COLOR_HIGH+"-"+doubleToString(price/2)+"-XXXXXX"+"-"+doubleToString1(num*2)+"-"+doubleToString1(xf);
							if( isPromotion1 ){
								command = "WSMG-1234-"+accept[1].trim()+"-"+COLOR_HIGH+"-"+"-XXXXXX-"+doubleToString(price/2)+"-"+doubleToString1(num*2)+"-"+doubleToString1(xf);
							}
							sendToLatticeTenth(accept[1].trim(), out, command , deviceManager);
							saveLog(socketManager, user.getName(), user.getBalance(), accept[1].trim(),"重量变化上报更改数码管显示内容"+command, "发送");
							logger.log("下发指令|"+lattice+"|"+threadNumber+"|更改数码管显示内容-->"+command,true );
						}
					}
					//客户端给我返回OK
					if( accept.length == 2 && accept[1].startsWith("OK") ){

						if( accept[0].toUpperCase().startsWith("ICID")){
							logger.log("操作指令|"+lattice+"|"+threadNumber+"|修改原始卡成功--原始卡:"+card+",新卡:"+newCard,true );
							ICPort icport = new ICPort();
//							icport.setCreatetime(  new Date() );
							icport.setIccard( newCard );
							icport.setNum(0);
//							icport.setLattice(lattice);
							icportManager.saveICPort( icport );
							continue;
						}
						LatticeStatus lss = deviceManager.getLatticeStatusById(lattice);
						lss.setFlag("1");
						deviceManager.updateLatticeStatus(lss);
					}
				}
			}
		} catch (Exception e) {  
			e.printStackTrace();
		}finally  { 
			try { socket.close(); } catch (IOException e) { e.printStackTrace(); }
		} 
	}  



	public synchronized void payMoney( String card, UserManager userManager, SocketManager socketManager,String latticeid,PrintWriter out){
		if(  card != null ) {
			User user = userManager.getUserByCard(card);
			logger.log("充值指令|"+lattice+"|"+ threadNumber +"|user:"+user,true );
			logger.log("充值指令|"+lattice+"|"+ threadNumber +"|userid:"+user.getId()+",money="+payMoney,true );

			PayInfo payInfo = payMoney.get( card );
			logger.log("充值指令|"+lattice+"|"+ threadNumber +"|payInfo:"+payInfo,true );
			if( payInfo != null ){
				logger.log("充值指令|"+lattice+"|"+ threadNumber +"|充值信息:充值金额："+payInfo.money,true );
				logger.log("充值指令|"+lattice+"|"+ threadNumber +"|充值信息:投币次数："+payInfo.once,true );
				logger.log("充值指令|"+lattice+"|"+ threadNumber +"|充值信息:最后投币时间："+payInfo.payTime,true );

				boolean payStatus = payMoneyUrl( user.getId()+"" ,payInfo.money );
				logger.log("充值指令|"+lattice+"|充值是否成功:"+payStatus,true );
				if( payStatus ){
					payMoney.remove( card );
				} 
				user = userManager.getUserByCard(card);
				logger.log("充值指令|"+lattice+"|"+ threadNumber +"|再次显示用户金额|user:"+user,true );
			}
			saveLog(socketManager, "", "", lattice, "发送2次闭锁指令： "+"SPTP-1234-"+latticeid+"-1234-BFFF", "指令");
			closeMoneyBox( latticeid,out );
		}
	}




	public void closeMoneyBox( String latticeid ,PrintWriter out){
		for( int i = 0 ; i < 2 ;i ++ ){
			out.println("SPTP-1234-"+latticeid+"-1234-BFFF");//
			out.flush();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.log("下发指令|"+lattice+"|"+ threadNumber +"|关闭验钞机---->SPTP-1234-"+latticeid+"-1234-BFFF",true );
	}





	/**
	 * 循环发送信息给设备
	 * @param latticeid 格子 ID
	 * @param out 输出流
	 * @param content 输出内容
	 */
	public void sendToLatticeTenth(String latticeid,PrintWriter  out ,String content,DeviceManager deviceManager){
		for (int j = 0; j < 10; j++) {
			out.println(content);//	
			out.flush();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			LatticeStatus lss = deviceManager.getLatticeStatusById(latticeid);
			String flag = lss.getFlag();
			if(flag!=null&&"1".equals(flag)){
				break;
			}
		}
	}

	/**
	 * 收到发送指令前 先把状态改成0    （晚上要把 收到 ID-命令 新增 LatticeStatus）
	 * @param latticeid
	 * @param deviceManager
	 */
	public void latticeFlag(String latticeid,DeviceManager deviceManager){
		LatticeStatus lss = deviceManager.getLatticeStatusById(latticeid);
		if(lss==null){
			lss = new LatticeStatus();
			lss.setFlag("0");
			lss.setCreatetime(new Date()); 
			lss.setLattice(latticeid);
			lss.setCard(card);
		}
		deviceManager.updateLatticeStatus(lss);
	}


	/**
	 * 根据ic卡的ID 判断 是否存在这个用户
	 * @param icid
	 * @return
	 */
	public User isUser(String icid,UserManager userManager){
		Map<String,String> map = new HashMap<String,String>();
		map.put("card", icid);
		List<User> list = userManager.getUserList(-1, -1, map);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}



	/**
	 * 将double 转换成 xxx8.00格式
	 * @param a
	 * @return
	 */
	public static String doubleToString(double a){
		String res="";
		DecimalFormat    df   = new DecimalFormat("######0.0");   
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

	/**
	 * 将double 转换成 xxx8.00格式
	 * @param a
	 * @return
	 */
	public static String doubleToString1(double a){
		String res="";
		DecimalFormat    df   = new DecimalFormat("######0.00");   
		a = Double.valueOf(df.format(a));
		if( a <= 0.01d && a > 0){
			a = 0;
		}
		res = a+"";
		String last= res.substring( res.indexOf(".")+1, res.length());
		if( last.length() == 1 ){
			res= a+"0";
		}
		if(res.length() < 7 ){
			int b = 7-res.length();
			for (int i = 0; i < b; i++) {
				res="X"+res;
			}
		}
		return res;
	}


	public static void main( String []args ){
	}



	public  float  change(String res) {
		float f = Float.parseFloat(res);
		return f;
	}

	/**
	 * 判断一个字符串是否为数字
	 */
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}



	/**
	 * 获取随机32位字符串   
	 * @return
	 */
	public String getRandomString(){
		return "0102030405067"+System.currentTimeMillis()+"ABCDEF" ;
	}


	final HttpClient client = new HttpClient();

	/**
	 * 充值接口
	 * @return
	 */
	public boolean payMoneyUrl(String u ,String m ){
		PostMethod method = new PostMethod( "http://www.rockcapital.cn/wx/api/recharge.html" ); 
		client.getParams().setContentCharset("UTF-8");
		String SubmitResult = null;
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] data = {new NameValuePair("u",  u), new NameValuePair("m", m)};
		method.setRequestBody(data);	
		try {
			client.executeMethod(method);	
			SubmitResult =method.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    
		return SubmitResult == null? false : (SubmitResult.indexOf("true")>-1?true:false);
	}




	/**
	 * 充值接口
	 */
	public boolean saveMsgRecord(String u ,String m, String msg ,String type  ){
		PostMethod method = new PostMethod( "http://www.rockcapital.cn/wx/api/msg.html" ); 
		client.getParams().setContentCharset("UTF-8");
		String SubmitResult = null;
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] data = {
				new NameValuePair("u",  u), 
				new NameValuePair("msg",  msg),
				new NameValuePair("m", m),
				new NameValuePair("type", type),
		};
		method.setRequestBody(data);	
		try {
			client.executeMethod(method);	
			SubmitResult =method.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    
		return SubmitResult == null? false : (SubmitResult.indexOf("true")>-1?true:false);
	}



	/**
	 * 开卡接口
	 */
	public boolean openCard(String cardnumber,String boxnumber ){
		PostMethod method = new PostMethod( "http://www.rockcapital.cn/wx/api/bcard.html" ); 
		client.getParams().setContentCharset("UTF-8");
		String SubmitResult = null;
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] data = {
				new NameValuePair("cardnumber",  cardnumber), 
				new NameValuePair("boxnumber",  boxnumber)
		};
		method.setRequestBody(data);	
		try {
			client.executeMethod(method);	
			SubmitResult =method.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    
		return SubmitResult == null? false : (SubmitResult.indexOf("true")>-1?true:false);
	}




	/**
	 * 蓝创短信发送通道 
	 * @param mobile
	 * @param msg
	 * @return
	 */
	public int sendMsg(String mobile ,String msg ){
		PostMethod method = new PostMethod( "http://222.73.117.158/msg/HttpBatchSendSM" ); 
		client.getParams().setContentCharset("UTF-8");
		String SubmitResult = null;
		String result[] = null;
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] data = {
				new NameValuePair("account", "BJSHWJ1"),
				new NameValuePair("pswd",    "Tch884203"),
				new NameValuePair("mobile",  mobile),
				new NameValuePair("product", "5429212946"),
				new NameValuePair("msg",     msg)};
		method.setRequestBody(data);	
		try {
			client.executeMethod(method);	
			SubmitResult =method.getResponseBodyAsString();
			result = SubmitResult.split(",");
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    
		return Integer.parseInt(result[1]);
	}



	protected String tranMoney( String model ){
		String money = "0";
		switch( model ){
		case "001" : money="50"; break;
		case "002" : money="100"; break;
		case "003" : money="150"; break;
		case "004" : money="200"; break;
		case "005" : money="250"; break;
		case "006" : money="300"; break;
		case "007" : money="350"; break;
		case "008" : money="400"; break;
		case "009" : money="450"; break;
		case "010" : money="550"; break;
		default:break;
		}
		return money ;
	}



	public void saveLog(SocketManager socketManager,String user,String phone,String lattice,String action,String type){
		SocketLog log = new SocketLog();
		log.setAction(action);
		log.setLattice(lattice);
		log.setPhone(phone);
		log.setType(type);
		log.setUser(user);
		log.setCreatetime(new Date());
		socketManager.saveSocketLog(log);
	}


	/**
	 * 比较2个时间相差时间数
	 * @param date
	 * @return
	 */
	public long millisecond(String date){
		if( date == null || "".equals(date)){
			return 0;
		}
		long oldtime = 0;
		try {
			oldtime = sdf.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long nowtime = new Date().getTime();
		return (nowtime-oldtime)/1000;
	}
}
