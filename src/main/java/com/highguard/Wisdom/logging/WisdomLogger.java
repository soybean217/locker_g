package com.highguard.Wisdom.logging;

import javax.servlet.http.HttpServletRequest;

import com.highguard.Wisdom.struts.actions.wxapp.WebSocket;
import com.highguard.Wisdom.struts.listener.MainSocketThread;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("unchecked")
public final class WisdomLogger
{
	
	private static Logger log2 = Logger.getLogger(MainSocketThread.class);
	
	/**
	 * 调试输出
	 * @param myClass
	 * @param message
	 */
	public static void logDebug(Class myClass, String message) {
		log2.debug(message);
	}
	
	
	/**
	 * 一般输出
	 * @param myClass
	 * @param message
	 */
	public static void logInfo(Class myClass, String message) {
		log2.info(" "+myClass.getName() + ": " + message);
	}
	
	
	/**
	 * 警告输出
	 * @param myClass
	 * @param message
	 */
	public static void logWarn(Class myClass, String message) {
		log2.warn(" "+myClass.getName() + ": " + message);
	}
	
	
	/**
	 * 错误输出
	 * @param myClass
	 * @param message
	 */
	public static void logError(Class myClass, String message) {
		log2.error(myClass.getName() + ": " + message);
	}
	
	
	/**
	 * 错误输出
	 * @param myClass
	 * @param message
	 */
	public static void logError(String className, String message) {
		log2.error( " " + message);
	}
	
	
	/**
	 * 致命输出
	 * @param myClass
	 * @param message
	 */
	public static void logFatal(Class myClass, String message) {
		log2.fatal(myClass.getName() + ": " + message);
	}
	
	
	public static void logEnterPublic(Class myClass,String method) {
		logInfo(myClass,"entry public method " + method);
	}
	
	public static void logExitPublic(Class myClass,String method) {
		logInfo(myClass, "exit  public method " + method);
	}
	
	public static void logEnterMethod(Class myClass,String method){
		logInfo(myClass, "entry method " + method);
	}
	
	public static void logExitMethod(Class myClass,String method){
		logInfo(myClass, "exit  method " + method);
	}
	
	public static void logError(Throwable cause,String message) {
		if (cause != null) {
			log2.error(message + "< Exception --" + cause.getMessage() + ">");
		} else {
			log2.error("< Error --" + message + ">");
		}
	}
	
	public static void logCatch(Class myClass, Exception e, String action) {
		logError(myClass, "caught exception while trying to " + action + ": " + e);
	}
	
	
	private static HttpServletRequest request = null;
	
	
	public static void auditError(Throwable cause,String message) {
		ActionContext ctx = ActionContext.getContext();
		String username = (String)ctx.getSession().get("user");
		request = ServletActionContext.getRequest();
		String ipAddress = getIpAddress(request);
		if(username != null) {
			log2.error(ipAddress + " " + username  + ": " + message);
		} else {
			log2.error(message + "< Exception --" + cause.getMessage() + ">");
		}
	}
	public static void logInfo(String clazz, String message) {
		log2.info(" "+clazz + ": " + message);
	}
	
	
	public static void auditInfo(String message) {
		ActionContext ctx = ActionContext.getContext();
		
		String username = (String)ctx.getSession().get("user");
		request = ServletActionContext.getRequest();
		
		String ipAddress = getIpAddress(request);
		if(username != null) {
			log2.info(ipAddress + " " + username + ": " + message);
		} else {
			log2.info(message);
		}
	}
	public static void logWarn(String clazz,String message) {
		ActionContext ctx = ActionContext.getContext();
		String username = (String)ctx.getSession().get("user");
		request = ServletActionContext.getRequest();
		String ipAddress = getIpAddress(request);
		if(username != null) {
			log2.warn(ipAddress + " " + username + ": " + message);
		} else {
			log2.warn(ipAddress + " " + message);
		}
	}
	/**
	 * @param request
	 * @return
	 */
	private static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}



