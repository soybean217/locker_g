package com.highguard.Wisdom.struts.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetPathCommon {
	private static final String FILE_SEPARATOR = "/";
	private static final String REPLACE_FILE_SEPARATOR = "\\"; 
	/**
	 * 获取配置根路径1�7
	 * @return rootDir
	 */
	public String getCommonDir(String str) throws Exception {
		String rootDir = null;
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		Properties p = new Properties();
		try {
			InputStream inputFile = new FileInputStream(path.replace("%20", " ") + "commons-dir.properties");
		    p.load(inputFile);      
		    inputFile.close(); 
			rootDir = p.getProperty(str);
			rootDir = rootDir.replace(REPLACE_FILE_SEPARATOR, FILE_SEPARATOR);
			File isFile = new File(rootDir);
			if(isFile.isDirectory()){
				if(!rootDir.endsWith(FILE_SEPARATOR)){
					rootDir = rootDir + FILE_SEPARATOR;
				}
			}
		} catch (FileNotFoundException ex) {      
		    throw ex;
		} catch (IOException ex) {
		    throw ex;
		} 
		return rootDir;
	}
	/**
	 * 获取数据库配置文件路径1�7
	 * @return xmlDir
	 */
	public String getSQLFileDir(String fileName) throws Exception {
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String xmlDir = path.replace("%20", " ").substring(0, path.replace("%20", " ").length() - 8) + "sql/" + fileName;
		xmlDir = xmlDir.replace(REPLACE_FILE_SEPARATOR, FILE_SEPARATOR);
		return xmlDir;
	}
	/**
	 * 获取XML配置文件路径1�7
	 * @return xmlDir
	 */
	public String getXMLFileDir(String fileName) throws Exception {
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String xmlDir = path.replace("%20", " ").substring(0, path.replace("%20", " ").length() - 8) + "xml/" + fileName;
		xmlDir = xmlDir.replace(REPLACE_FILE_SEPARATOR, FILE_SEPARATOR);
		return xmlDir;
	}
	/**
	 * 获取日志根路径1�7
	 * @return rootDir
	 */
	public String getLogDir(String str) throws Exception {
		String rootDir = null;
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		Properties p = new Properties();
		try {
			InputStream inputFile = new FileInputStream(path.replace("%20", " ") + "log4j.properties");      
		    p.load(inputFile);
		    inputFile.close(); 
		    if("audit".equals(str)){
		    	rootDir = p.getProperty("log4j.appender.SRAAuditLog.File");
		    }else{
		    	rootDir = p.getProperty("log4j.appender.SRARunLog.File");
		    }
			rootDir = rootDir.replace(REPLACE_FILE_SEPARATOR, FILE_SEPARATOR);
			int index = rootDir.lastIndexOf(FILE_SEPARATOR);
			rootDir = rootDir.substring(0, index);
			
			if(!rootDir.endsWith(FILE_SEPARATOR)){
				rootDir = rootDir + FILE_SEPARATOR;
			}
		} catch (FileNotFoundException ex) {      
		    throw ex;
		} catch (IOException ex) {
		    throw ex;
		} 
		return rootDir;
	}
}
