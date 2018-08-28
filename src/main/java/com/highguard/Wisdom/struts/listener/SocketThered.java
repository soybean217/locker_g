package com.highguard.Wisdom.struts.listener;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.highguard.Wisdom.exception.SocketRuntimeException;
import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.manager.DeviceManager;
import com.highguard.Wisdom.struts.common.GetPathCommon;
import org.apache.log4j.Logger;

/**
 * 读取设备读到的东西
 * @author Administrator
 *
 */
public class SocketThered extends Thread {
	public static Map<Integer,MainSocketThread> map = new HashMap<Integer, MainSocketThread>();//用于存放 socket
	private ServerSocket ss;
	private GetPathCommon commno = new GetPathCommon(); 
	private WorkThread worker = new WorkThread();
	private boolean WORKFLAGING = true;
	private Logger logger = Logger.getLogger(SocketThered.class);
	private DeviceManager deviceManager;


	public static int LATTICE_NOTONLINE= 405;
	/**
	 * 超时操作
	 */
	public static Map<String,Long> timeout = new HashMap<String,Long>();


	public void closeAllSocket(){
		WORKFLAGING = false;
		Iterator<Integer> key = map.keySet().iterator();
		while( key.hasNext() ){
			Integer k = key.next();
			Socket socket = map.get(k).getSocket();
			if( socket != null && !socket.isClosed() ){
				try {
					socket.close();
					//更新设备状态
//                    setLatticeNotonline(k);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		map.clear();

	}


	private class  WorkThread extends Thread{
		public void run(){
			while( WORKFLAGING ){
				Iterator<Integer> key = map.keySet().iterator();
				//System.out.println();System.out.println("WorkThread."+socket_num+",map.size="+map.size() ); 
				while( key.hasNext() ){
					Integer k = key.next();
					Socket socket = map.get(k).getSocket();
					if( socket != null ){
						try {
							if( socket.isClosed() ){
								map.remove( k );
//								setLatticeNotonline(k);
								continue;
							}
							PrintWriter out = new PrintWriter( socket.getOutputStream(), true);
							if( out != null && !socket.isClosed() ){
								out.println("R-ID-1234");
								out.flush();  
							}
						} catch (IOException e) {
							e.printStackTrace();
						} 
					}
				}
				try {
					Thread.sleep( 1000 * 30 ); // 3分钟下发依次心跳信号
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void run() {
		try {
		    deviceManager = (DeviceManager) ApplicationUtil.act.getBean("deviceManager");
			ss = new ServerSocket(5310);
//			worker.start();
			while ( WORKFLAGING ) {
				Socket socket = ss.accept();
				MainSocketThread main = new MainSocketThread(socket);
				main.addCallback(new DeviceInitCallback(null));
				main.addCallback(new CallbackWeightDemo(null));
//				main.addCallback(new IcPortInitCallback(null));
//				main.addCallback(new StopCloseDoorCallback(null));
				main.start();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			try {
				ss.close();
			} catch (IOException e1) {
				logger.error(e.getMessage(), e);
			}
		}finally{
			if( null != ss ){
				try {
					ss.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public void setLatticeOnLine(int latticeId, MainSocketThread mainSocketThread){
		map.put(latticeId, mainSocketThread);
		Lattice lattice = deviceManager.getLatticeById(latticeId);
		if( lattice != null ){
			lattice.setStatus("在线");
			deviceManager.updateLattice(lattice);
		}
	}

	public void setLatticeNotonline(String latticeId){
		map.remove(latticeId);
		Lattice lattice = deviceManager.getLatticeBylockid(latticeId);
		if( lattice != null ){
			lattice.setStatus("离线");
			deviceManager.updateLattice(lattice);
		}
	}

	public static MainSocketThread findByLatticeId(int latticeId, boolean throwException){
		MainSocketThread main = map.get(latticeId);
		if( throwException ){
			if( main == null ){
				throw new SocketRuntimeException(SocketThered.LATTICE_NOTONLINE, "设备未在线");
			}
		}
		return main;
	}

	public static MainSocketThread findByLatticeId(int latticeId){
		return findByLatticeId(latticeId, true);
	}
}
