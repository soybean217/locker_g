package com.highguard.Wisdom.struts.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.highguard.Wisdom.logging.WisdomLogger;
import com.highguard.Wisdom.mgmt.hibernate.beans.Trading;
import com.highguard.Wisdom.mgmt.manager.DeviceManager;
import com.highguard.Wisdom.mgmt.manager.TradingManager;
import com.highguard.Wisdom.struts.beans.ReadContext;
import com.highguard.Wisdom.struts.common.GetPathCommon;

/**
 * 读取设备读到的东西
 * 
 * @author Administrator
 *
 */
public class TakeOffLockThered extends Thread {
	public static Map<String, ReadContext> map = new HashMap<String, ReadContext>();// 用于存放处理后的数据
	public static Map<String, String> readmap = new HashMap<String, String>();// 用于存放设备传来的数据
	private String LatticeId;
	private List<Object[]> tradingList = new ArrayList<Object[]>();
	private GetPathCommon commno = new GetPathCommon();
	public static Map<String, String> socketMap = new HashMap();

	public TakeOffLockThered( String LatticeId,
			List<Object[]> tradingList) {
		this.LatticeId = LatticeId;
		this.tradingList = tradingList;
	}

	public void run() {
		DeviceManager deviceManager = (DeviceManager) ApplicationUtil.act
				.getBean("deviceManager");
		TradingManager tradingManager = (TradingManager) ApplicationUtil.act
				.getBean("tradingManager");

		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		String content = "SPTP-1234-" + LatticeId + "-1234-7FFF";
		if (LatticeId == null || "".equals(LatticeId)) {
			WisdomLogger.logInfo("error", " 这个设备没有找到需要通信的IP ");
			LatticeId = "127.0.0.1";
			// return SUCCESS;
		}

		try {
			String port = commno.getCommonDir("sendport");
			// 客户端socket指定服务器的地址和端口号
			socket = new Socket(LatticeId, Integer.parseInt(port));
			// System.out.println("Socket=" + socket);

			br = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())));
			pw.println(content);
			pw.flush();
			WisdomLogger.logInfo("read", " send : " + content);
			String str = "";// br.readLine();
			WisdomLogger.logInfo("read", " ACCEPTread : " + str);
			if (str.endsWith("ERROR")) {

			} else {
				WisdomLogger.logInfo("latticeId", "  : " + LatticeId);
				WisdomLogger.logInfo("未取走商品的大小  tradingList", " size : "
						+ tradingList.size());
				deviceManager.changLatticeStatus(LatticeId, "1");// 改变格子状态
				for (int i = 0; i < tradingList.size(); i++) {
					Trading t = tradingManager.getTrading(Integer
							.parseInt(String.valueOf(tradingList.get(i)[0])));
					t.setTakeway("1");
					tradingManager.updateTrading(t);
				}
			}

			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				pw.close();
				socket.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
