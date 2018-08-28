package com.highguard.Wisdom.struts.common;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.highguard.Wisdom.logging.WisdomLogger;

/**
 * 应用服务器的相关配置信息
 * 
 * @author mxz
 * 
 */
public class AppConfig {

	
	private static Class<AppConfig> myClass = AppConfig.class; 
	
	private static AppConfig instance = null;

	private static synchronized void syncInit(){
		if(instance == null){
			instance = new AppConfig();
		}
	}
	
	public static AppConfig getInstance() {
		if (instance == null) {
			syncInit();
		}
		return instance;
	}

	/**
	 * 重置配置
	 * @return
	 */
	public static AppConfig resetInstance() {
		instance = null;
		return getInstance();
	}
	
	private AppConfig() {
		initSystemConfig();
		initClientConfig();
		initcfg();
	}
	
	private boolean isPr;//是否启用 文印室
	private int appRel; //每个阶段申请者之间的关系    或[1] , 并[2]
	private int delModel; //流程申请中文件的删除方式： 0-立即删除，1-过期删除，2-延期删除
	private int delayHour; //延期小时数
	private String hguserName; //配置的  hguser 与配置的跟节点名称
	private String backEncoding ="unicode";//返回客户端字符串的编码方式 ，默认方式 unicode
	private int warnlogsize; //日志告警警戒数
	private int userRoleLimits;//用户角色权限限制   0-不限制;1-限制
	private String defaultUpdateType;//客户端默认更新客户端类型，集中管控/集中文印
	private double spaceWarnPer;//存储空间已用告警百分比
	private double cpuWarnPer;//CPU使用率告警百分比
	private double memWarnPer;//内存使用告警百分比
	
	
	/**
	 * 初始化  读取   systemConfig.xml 配置
	 */
	@SuppressWarnings("unchecked")
	private void initSystemConfig(){
		GetPathCommon common = new GetPathCommon();
		try {
			String filePath = common.getXMLFileDir("systemConfig.xml");
			Document document = getXmlDoc(filePath);
			Element rootElm = document.getRootElement();
			//客户端配置
			List<Element> configElms = rootElm.elements("config");
			if (configElms != null) {
//				for (Element cElm : configElms) {
//					String name = cElm.element("name").getTextTrim();
//					String value = cElm.element("value").getText();
//				}
			}
		} catch (Exception e) {
			WisdomLogger.logCatch(myClass, e, "initSystemConfig");
		}
	}
	
	
	/**
	 * 初始化  读取   config.xml 配置
	 */
	@SuppressWarnings("unchecked")
	private void initClientConfig(){
		GetPathCommon common = new GetPathCommon();
		try {
			String filePath = common.getXMLFileDir("config.xml");
			Document document = getXmlDoc(filePath);
			Element rootElm = document.getRootElement();
			//客户端配置
			List<Element> configElms = rootElm.elements("client");
			if (configElms != null) {
				for (Element cElm : configElms) {
					String name = cElm.element("key").getTextTrim();
					String value = cElm.element("value").getText();
					if("ispr".equalsIgnoreCase(name)){
						if(value!=null && "true".equalsIgnoreCase(value)){
							this.setPr(true);
						}
					}
				}
			}
			
			Element delRuleElm = rootElm.element("delrule");
			if(delRuleElm!=null){
				String mode = delRuleElm.element("mode").getStringValue();
				String day = delRuleElm.element("day").getStringValue();
				this.setDelModel(Integer.parseInt(mode));
				this.setDelayHour(Integer.parseInt(day) * 24);
			}
			
		} catch (Exception e) {
			WisdomLogger.logCatch(myClass, e, "initConfig");
		}
	}
	
	/**
	 * 初始化  读取   initcfg.xml 配置
	 */
	private void initcfg(){
		GetPathCommon common = new GetPathCommon();
		try {
			String filePath = common.getXMLFileDir("initcfg.xml");
			Document document = getXmlDoc(filePath);
			Element rootElm = document.getRootElement();
			
			setBackEncoding(getNodeValue(filePath, rootElm, "backencoding"));
			setUserRoleLimits(parseInt(getNodeValue(filePath, rootElm, "userRoleLimits")));
			setAppRel(parseInt(getNodeValue(filePath, rootElm, "apprel")));
			setWarnlogsize(parseInt(getNodeValue(filePath, rootElm, "warnlogsize")));
			setHguserName(getNodeValue(filePath, rootElm, "hguserDisplayName"));
			setDefaultUpdateType(getNodeValue(filePath, rootElm, "defaultUpdateType"));
			setSpaceWarnPer(parseDouble(getNodeValue(filePath, rootElm, "spaceWarnPer")));
			setCpuWarnPer(parseDouble(getNodeValue(filePath, rootElm, "cpuWarnPer")));
			setMemWarnPer(parseDouble(getNodeValue(filePath, rootElm, "memWarnPer")));
		} catch (Exception e) {
			WisdomLogger.logCatch(myClass, e, "initConfig");
		}
	}
	
	
	/**
	 * 获取节点的Value值
	 * @param path
	 * @param parent
	 * @param node
	 * @return
	 * @throws Exception
	 */
	private String getNodeValue(String path,Element parent,String node) throws Exception{
		String value = null;
		try {
			value = parent.element(node).getStringValue();
		} catch (Exception e) {
			WisdomLogger.logCatch(myClass, e, path+" "+node+" value is null or does not exist");
		}
		return value;
	}

	/**
	 * 转为 Int 类型
	 * @param str
	 * @return
	 */
	private int parseInt(String str) {
		int res = 0;
		if (str == null || "".equals(str.trim())) {
			return 0;
		} else {
			try {
				res = Integer.parseInt(str);
			} catch (NumberFormatException e) {
			}
		}
		return res;
	}
	
	
	/**
	 * 转为 double 类型
	 * 
	 * @param str
	 * @return
	 */
	private double parseDouble(String str) {
		double res = 0;
		if (str == null || "".equals(str.trim())) {
			return 0;
		} else {
			try {
				res = Double.parseDouble(str);
			} catch (NumberFormatException e) {
			}
		}
		return res;
	}
	
	/////////////////////////////////////////////////////
	////--- getter setter --////
	/////////////////////////////////////////////////////
	/**
	 * 读    获取XMl的文档对象
	 * @param path
	 * @return
	 * @throws DocumentException
	 */
	private Document getXmlDoc(String path) throws DocumentException{
		SAXReader reader = new SAXReader();
		Document  document = reader.read(new File(path));
		return document;
	}

	public boolean isPr() {
		return isPr;
	}

	public void setPr(boolean isPr) {
		this.isPr = isPr;
	}
	
	
	public int getAppRel() {
		if (appRel != 1) {
			appRel = 2;
		}
		return appRel;
	}

	public void setAppRel(int appRel) {
		this.appRel = appRel;
	}

	/**
	 * 流程申请中文件的删除方式： 0-立即删除，1-过期删除，2-延期删除
	 * @return
	 */
	public int getDelModel() {
		return delModel;
	}

	public void setDelModel(int delModel) {
		this.delModel = delModel;
	}

	public int getDelayHour() {
		// 延期情况
		if (delModel == 2) {
			return delayHour;
		}
		// 非延期情况
		return 0;
	}

	public void setDelayHour(int delayHour) {
		this.delayHour = delayHour;
	}

	public String getHguserName() {
		if (hguserName == null || "".equals(hguserName) || "null".equals(hguserName)) {
			return "hguser";
		}
		return hguserName;
	}

	public void setHguserName(String hguserName) {
		this.hguserName = hguserName;
	}


	public String getBackEncoding() {
		return backEncoding;
	}


	public void setBackEncoding(String backEncoding) {
		this.backEncoding = backEncoding;
	}

	public int getWarnlogsize() {
		return warnlogsize;
	}

	public void setWarnlogsize(int warnlogsize) {
		this.warnlogsize = warnlogsize;
	}

	public int getUserRoleLimits() {
		return userRoleLimits;
	}

	public void setUserRoleLimits(int userRoleLimits) {
		this.userRoleLimits = userRoleLimits;
	}

	public String getDefaultUpdateType() {
		return defaultUpdateType;
	}

	public void setDefaultUpdateType(String defaultUpdateType) {
		this.defaultUpdateType = defaultUpdateType;
	}

	public double getSpaceWarnPer() {
		return spaceWarnPer;
	}

	public void setSpaceWarnPer(double spaceWarnPer) {
		this.spaceWarnPer = spaceWarnPer;
	}

	public double getCpuWarnPer() {
		return cpuWarnPer;
	}

	public void setCpuWarnPer(double cpuWarnPer) {
		this.cpuWarnPer = cpuWarnPer;
	}

	public double getMemWarnPer() {
		return memWarnPer;
	}

	public void setMemWarnPer(double memWarnPer) {
		this.memWarnPer = memWarnPer;
	}

	

	
}
