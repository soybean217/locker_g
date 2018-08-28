package com.highguard.Wisdom.struts.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.highguard.Wisdom.logging.WisdomLogger;


/**
 * Xml格式的参数  转换工具类
 * @author mxz
 *
 */
public class XmlParameterUtil {

	private static final Class<XmlParameterUtil> clazz = XmlParameterUtil.clazz;
	
	/**
	 * 转换XML格式字符串 ，嵌套nodeName参数的子节点为Map对象
	 * @param xmlText
	 * @param nodeName 默认为跟节点下的直接子节点。
	 * 通常可以为 /xx/yy 表示 取跟节点下的xx节点，xx节点下的yy节点，返回yy节点的直接子节点键值对
	 * @return
	 */
	public Map<String, String> handRequesParameterMap(String xmlText,String nodeName){
		Map<String, String> map = new HashMap<String, String>();
		try {
			//将字符串转化为XML
			Document document = DocumentHelper.parseText(xmlText);
			String[] nodes = nodeName.split("/");
			Element paraElm = document.getRootElement();
			Element nodeElm = paraElm;
			for(String node: nodes){
				if("".equals(node.trim())){
					continue;
				}
				nodeElm = paraElm.element(node);
				if (nodeElm != null) {
					paraElm = nodeElm;
				}
			}
			if (nodeElm != null) {
				map = putEml(nodeElm);
			}
		} catch (DocumentException e) {
			WisdomLogger.logError(e, "Parse Parameter_Text To XML,xmlText="+xmlText);
		}
		return map;
	}
	
	
	
	
	 /**
	 * 循环解析XML的每个节点，封装成Map对象
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> putEml(Element elm){
		Map<String, String> mapParas = new HashMap<String, String>();
		for (Iterator it = elm.elementIterator(); it.hasNext();) {
			Element nelm = (Element) it.next();
			mapParas.put(nelm.getName(),nelm.getStringValue());
		}
		return mapParas;
	}
	
	
	public int parseInt(String para) {
		if (para == null || "".equals(para.trim())) {
			return 0;
		}
		int num = 0;
		try {
			num = Integer.parseInt(para);
		} catch (NumberFormatException e) {
			WisdomLogger.logCatch(clazz, e, "parseInt,  para=" + para);
		}
		return num;
	}

}
