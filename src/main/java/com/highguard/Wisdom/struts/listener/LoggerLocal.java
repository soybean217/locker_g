package com.highguard.Wisdom.struts.listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
 

/**
 * 采用文件流生成日志文件，直接命名日志名称即可
 * 
 * @author 张卫广
 * */
public class LoggerLocal {

	private String filePath = "";
	
	/** 日志名称 */
	private String LoggerName = "Logger.Log";

	private java.text.SimpleDateFormat sdf_yyyymmdd = new java.text.SimpleDateFormat("yyyyMMdd");

	/**
	 * 构造函数，需要传入生产的日志名称,如：Action.Log
	 * 
	 */
	public LoggerLocal(String loggername) {
		File file = new File("/var/log/bangbang/socket-log"+File.separator+loggername);
		if(  !file.exists() ){
			 file.mkdirs();
		}
		filePath = file.getAbsoluteFile().getPath();
		this.LoggerName = filePath+ File.separator+loggername;
	}


	public LoggerLocal() {
		this.LoggerName = "Logger";
	}


	/**
	 * 格式化日期输出
	 * */
	public static String getTimeString(Calendar dt) {
		String str = String.valueOf(dt.get(1)) + '-';
		int i = dt.get(2) + 1;
		if (i < 10)
			str = str + '0';
		str = str + String.valueOf(i) + '-';
		i = dt.get(5);
		if (i < 10)
			str = str + '0';
		str = str + String.valueOf(i) + ' ';
		i = dt.get(11);
		if (i < 10)
			str = str + '0';
		str = str + String.valueOf(i) + ':';
		i = dt.get(12);
		if (i < 10)
			str = str + '0';
		str = str + String.valueOf(i) + ':';
		i = dt.get(13);
		if (i < 10)
			str = str + '0';
		str = str + String.valueOf(i);
		return str;
	}

	/**
	 * 日志文件
	 * 
	 * @param out
	 *            是否输出到控制台,true 输出到控制台,false
	 * @param msg
	 *            消息
	 * */
	public void logError(String msg, Exception ex) {
		String str = getTimeString(Calendar.getInstance());
		str = str + "  " + msg + "\r\n";
		try {
			FileWriter  file = new FileWriter(LoggerName+"_"+sdf_yyyymmdd.format(new Date())+".log",true); 
			PrintWriter syse = new PrintWriter(file); 
			syse.println( msg );
			if( ex != null ){ 
				ex.printStackTrace( syse );
			}
			syse.flush();
			syse.close();
			file.flush();
			file.close();
		} catch (Exception exception) { exception.printStackTrace() ; }
	}
 
	
	
	/**
	 * 日志文件
	 * @param msg 消息
	 * */
	public void log(String msg, boolean is){
		String str = getTimeString(Calendar.getInstance());
		str = str + "  " + msg + "\r\n";
		if( is )
		try {
			FileOutputStream logfile = new FileOutputStream(LoggerName+"_"+sdf_yyyymmdd.format(new Date())+".Log", true);
			logfile.write(str.getBytes());
			logfile.close();
		} catch (Exception exception) { exception.printStackTrace() ; }
	}
}
