package com.highguard.Wisdom.mgmt.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.logging.WisdomLogger;
import com.highguard.Wisdom.mgmt.handler.ParamConfigHandler;
import com.highguard.Wisdom.struts.beans.ParanConfig;
import com.highguard.Wisdom.struts.common.Base64;
import com.highguard.Wisdom.struts.common.GetPathCommon;
import com.highguard.Wisdom.struts.constant.Constants;

public class ParamConfigXML {
	private static GetPathCommon common = new GetPathCommon();
	private static String outFile = null;
	private static ParamConfigXML instance = null;
	private static DocumentBuilder builder = null;
	private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private static Document document = null;
	private static Document sourceDocument = null;
	@SuppressWarnings("unchecked")
	private static final Class myClass = ParamConfigXML.class;
	private Map<String, String> map = new HashMap<String, String>();
	
	
	
	private final String CONFIG_FILENAME = "config.xml";
	
	private final String CONFIG_FILENAME2 = "configtemp.xml";
	
	
	/**
	 * 将构造方法私有化
	 */
	private ParamConfigXML() {
	}
	/**
	 * 获取ObjectXML的唯一实例
	 */
	public static synchronized ParamConfigXML getInstance() {
		// 如果instance为空,便创建一个新的FirewallConfigXML实例,否则返回已有实例
		if(instance == null) {
			instance = new ParamConfigXML();
		}
		return instance;
	}
	
	/**
	 * 用Xerces解析器来解析XML文档
	 * @param fileName 文件名
	 * @return TunnelRule 隧道规则对象
	 * @throws Exception
	 */
	public synchronized ParanConfig getParamConfig(int model) throws Exception {
		// 创建SAX解析器工厂对象
		SAXParserFactory saxfactory = SAXParserFactory.newInstance();
		// 使用解析器工厂创建解析器实例
		SAXParser saxParser = saxfactory.newSAXParser();
		// 创建SAX解析器要使用的事件侦听器对象
		ParamConfigHandler handler = new ParamConfigHandler();
		// 解析文件
		if(model==0){
			outFile = common.getXMLFileDir(CONFIG_FILENAME);
		}else{
			outFile = common.getXMLFileDir(CONFIG_FILENAME2);
		}
		
		saxParser.parse(new File(outFile), handler);
		ParanConfig paranConfig = handler.getParanConfig();

		if(builder == null) {
			try {
				builder = factory.newDocumentBuilder();
			} catch(ParserConfigurationException e) {
				throw new WisdomException(e);
			}
		}
		try {
			if(model==0){
				document = builder.parse(common.getXMLFileDir(CONFIG_FILENAME));
				sourceDocument = builder.parse(common.getXMLFileDir(CONFIG_FILENAME));
			}else{
				document = builder.parse(common.getXMLFileDir(CONFIG_FILENAME2));
				sourceDocument = builder.parse(common.getXMLFileDir(CONFIG_FILENAME2));
			}
			document.normalize();
		} catch(Exception e) {
			throw new WisdomException(e);
		}

		// lod end
	    WisdomLogger.logExitPublic(myClass,"getSfaCfgInfos()");
		return paranConfig;
	}
	
	
	
	
	/**
	 * 获取SFA配置文件
	 * @param SfaCfg
	 * @throws SRAException
	 */
	public synchronized Document getSfaCfgXML() {
		WisdomLogger.logEnterPublic(myClass,"getSfaCfgXML()");
		if(builder == null) {
			try {
				builder = factory.newDocumentBuilder();
			} catch(ParserConfigurationException e) {
				WisdomLogger.logError(e, "SfaCfgXML.getSfaCfgXML");
			}
		}
		try {
			sourceDocument = builder.parse(common.getXMLFileDir(CONFIG_FILENAME));
		} catch (Exception e) {
			WisdomLogger.logError(e, "SfaCfgXML.getSfaCfgXML");
		} 
		WisdomLogger.logExitPublic(myClass,"getSfaCfgXML()");
		return sourceDocument;
	}
	
	/**
	 * 回滚SFA配置文件
	 * @param SfaCfg
	 * @throws SRAException
	 */
	public synchronized void toSfaCfgXML(Document sourceDocument) {
		WisdomLogger.logEnterPublic(myClass,"toSfaCfgXML()");
		try {
			DomXML.domDocToFile(sourceDocument,common.getXMLFileDir(CONFIG_FILENAME),Constants.ENCODING_TYPE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			WisdomLogger.logError(e, "SfaCfgXML.toSfaCfgXML");
		}
		WisdomLogger.logExitPublic(myClass,"toSfaCfgXML()");
	}
	
	
	
	
	/**
	 * 更新SFA配置文件
	 * @param SfaCfg
	 * @throws SRAException
	 */
	public synchronized  void updateParanConfig(ParanConfig paranConfig,int model) throws WisdomException {
		WisdomLogger.logEnterPublic(myClass,"updateParanConfig()");
		
	
		
		map.put("timeout", paranConfig.getTimeout());
		map.put("timeshow", paranConfig.getTimeshow());
		map.put("cleanshow", paranConfig.getCleanshow());
		map.put("delaytime", paranConfig.getDelaytime());
        map.put("range", paranConfig.getRange());
		GetPathCommon common = new GetPathCommon();
		String filePath;
		
		if(builder == null) {
			try {
				builder = factory.newDocumentBuilder();
			} catch(ParserConfigurationException e) {
				throw new WisdomException(e);
			}
		}
		try {
			if(model==0){
				document = builder.parse(common.getXMLFileDir(CONFIG_FILENAME));
				sourceDocument = builder.parse(common.getXMLFileDir(CONFIG_FILENAME));
			}else{
				document = builder.parse(common.getXMLFileDir(CONFIG_FILENAME));
				sourceDocument = builder.parse(common.getXMLFileDir(CONFIG_FILENAME));
			}
			document.normalize();
		} catch(Exception e) {
			throw new WisdomException(e);
		}
		
		NodeList itemNodes = document.getElementsByTagName("client");
		Element eventsElement = null;
		NodeList eventsNodes = null;
		String tag = null;
		for (int i = 0; i < itemNodes.getLength(); i++) {
			eventsElement = (Element)itemNodes.item(i);
			if (eventsElement != null) {
				eventsNodes = eventsElement.getElementsByTagName("key");
				tag = eventsNodes.item(0).getFirstChild().getNodeValue();
				eventsNodes = eventsElement.getElementsByTagName("value");

				if (map.get(tag) != null) {
					Node node = eventsNodes.item(0).getFirstChild();
					if (node != null) {
						node.setNodeValue(map.get(tag));
					}else{
						eventsNodes.item(0).setTextContent(map.get(tag));
					}
				}
			}
		}
		
		itemNodes = document.getElementsByTagName("print");
		for (int i = 0; i < itemNodes.getLength(); i++) {
			eventsElement = (Element)itemNodes.item(i);
			if (eventsElement != null) {
				eventsNodes = eventsElement.getElementsByTagName("key");
				tag = eventsNodes.item(0).getFirstChild().getNodeValue();
				eventsNodes = eventsElement.getElementsByTagName("value");
				if (map.get(tag) != null) {
					eventsNodes.item(0).getFirstChild().setNodeValue((map.get(tag) != null && !"".equals(map.get(tag))) ? map.get(tag) : " ");
				}
			}
		}
		
		itemNodes = document.getElementsByTagName("cert");
		for (int i = 0; i < itemNodes.getLength(); i++) {
			eventsElement = (Element)itemNodes.item(i);
			if (eventsElement != null) {
				eventsNodes = eventsElement.getElementsByTagName("key");
				tag = eventsNodes.item(0).getFirstChild().getNodeValue();
				eventsNodes = eventsElement.getElementsByTagName("value");
				if (map.get(tag) != null) {
					eventsNodes.item(0).getFirstChild().setNodeValue(String.valueOf(map.get(tag)));
				}
			}
		}
		
		itemNodes = document.getElementsByTagName("filter");
		for (int i = 0; i < itemNodes.getLength(); i++) {
			eventsElement = (Element)itemNodes.item(i);
			if (eventsElement != null) {
				eventsNodes = eventsElement.getElementsByTagName("key");
				tag = eventsNodes.item(0).getFirstChild().getNodeValue();
				eventsNodes = eventsElement.getElementsByTagName("value");
				if (map.get(tag) != null) {
					eventsNodes.item(0).getFirstChild().setNodeValue(String.valueOf(map.get(tag)));
				}
			}
		}
		
		itemNodes = document.getElementsByTagName("hide");
		for (int i = 0; i < itemNodes.getLength(); i++) {
			eventsElement = (Element)itemNodes.item(i);
			if (eventsElement != null) {
				eventsNodes = eventsElement.getElementsByTagName("key");
				tag = eventsNodes.item(0).getFirstChild().getNodeValue();
				eventsNodes = eventsElement.getElementsByTagName("value");
				if (map.get(tag) != null) {
					eventsNodes.item(0).getFirstChild().setNodeValue(String.valueOf(map.get(tag)));
				}
			}
		}
		
		NodeList delRuleNodes = document.getElementsByTagName("delrule");
		eventsElement = (Element)delRuleNodes.item(0);
		if (eventsElement != null) {
			NodeList modeNodes = eventsElement.getElementsByTagName("mode");		
		}

		try {
			// 将修改后的内容写入到文件中
			if(model==0){
				DomXML.domDocToFile(document,common.getXMLFileDir(CONFIG_FILENAME),Constants.ENCODING_TYPE);
			}else {
				DomXML.domDocToFile(document,common.getXMLFileDir(CONFIG_FILENAME2),Constants.ENCODING_TYPE);
			}
		} catch(Exception e) {
			// 回滚
			try {
				if(model==0){
					DomXML.domDocToFile(sourceDocument,common.getXMLFileDir(CONFIG_FILENAME),Constants.ENCODING_TYPE);
				}else{
					DomXML.domDocToFile(sourceDocument,common.getXMLFileDir(CONFIG_FILENAME2),Constants.ENCODING_TYPE);
				}
			} catch(Exception se) {
				throw new WisdomException(e);
			}
			throw new WisdomException(e);
		}
		// lod end
	    WisdomLogger.logExitPublic(myClass,"updateSfaCfg()");
	}
	
	
	
	public synchronized String saveConfigAsString(ParanConfig paranConfig){
		try {
			updateParanConfig(paranConfig, 0);
			String path = common.getXMLFileDir(CONFIG_FILENAME2);
			StringBuffer sb = new StringBuffer();
			InputStreamReader input = new InputStreamReader(new FileInputStream(new File(path)),"utf-8");
			BufferedReader reader = new BufferedReader(input);
			String line = null;
			while( (line=reader.readLine())!=null){
				sb.append(line);
			}
			reader.close();
			
			String msg = sb.toString();
			String base64Str =Base64.encode(msg.getBytes());
			
			return base64Str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
		
	
}
