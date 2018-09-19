package com.highguard.Wisdom.struts.listener;


import org.slf4j.MDC;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SocketListner implements ServletContextListener{

	private static SocketThered socket ;
	private static UdpThread udpThread ;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		socket.closeAllSocket();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if(null==socket){
			socket = new SocketThered();
		}
		MDC.put("userId", "0");
		socket.start();
		if (null==udpThread) {
			udpThread = new UdpThread();
			udpThread.start();
		}
	}


	public static SocketThered getSocketThered(){
		return socket;
	}
}
