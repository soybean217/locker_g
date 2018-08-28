package com.highguard.Wisdom.mgmt.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.highguard.Wisdom.struts.beans.ParanConfig;
import com.highguard.Wisdom.struts.common.GetPathCommon;

public class ParamConfigHandler extends DefaultHandler {
	// 保存已经读到过但还没有关闭的标签
	Stack<String> tagsStack = new Stack<String>();
	ParanConfig paranConfig = null;
	private Map<String,String> map = new HashMap<String,String>();
	private String tempStr;
	
	/**
	 * 读到一个开始标签的时候调用此方法
	 * @param namespaceURI 名域
	 * @param localName 标签名
	 * @param qName 标签的修饰前缀
	 * @param atts 标签所包含的属性列表
	 * @exception SAXException
	 */
	public void startElement(String namespaceURI,String localName,String qName,Attributes atts)
			throws SAXException {
		tagsStack.push(qName);
	}
	
	/**
	 * 在遇到结束标签的时候调用这个方法
	 * @param namespaceURI 名域
	 * @param localName 标签名
	 * @param qName 标签的修饰前缀
	 * @exception SAXException
	 */
	public void endElement(String namespaceURI,String localName,String qName)
			throws SAXException {
		// 将最近读取的标签弹出
		String currenttag = (String)tagsStack.pop();
		// 最近读到的标签应该与即将关闭的标签一样
		if(!currenttag.equals(qName)) {
			throw new SAXException("XML format is incorrect,the label dose not match");
		}
	}
	
	/**
	 * 处理在XML文件中读到的字符串
	 * @param chs 字符串
	 * @param start 字符串起始位置
	 * @param length 字符串长度
	 * @exception SAXException
	 */
	public void characters(char[] chs,int start,int length) throws SAXException {
		// 从栈中得到当前节点的信息
		String tag  = (String)tagsStack.peek();
		String value = new String(chs,start,length);
		if ("key".equals(tag)) {
			tempStr = value;
		}else if ("value".equals(tag)) {
			map.put(tempStr, value != null ? value : "");
		}
	}	
	
	public ParanConfig getParanConfig() {
		paranConfig = new ParanConfig();
		
		paranConfig.setCleanshow(map.get("cleanshow"));
		paranConfig.setDelaytime(map.get("delaytime"));
		paranConfig.setTimeout(map.get("timeout"));
		paranConfig.setTimeshow(map.get("timeshow"));
		paranConfig.setRange(map.get("range"));
		return paranConfig;
	}
	
	
	private String readFileByLines(String filePath) throws Exception {
		File file = new File(filePath);
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = null;
		try {
			//reader = new BufferedReader(new FileReader(file));
			reader= new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			String tempStr = null;
			while((tempStr = reader.readLine()) != null) {
				sb.append(tempStr).append("\n");
			}
			reader.close();
		} catch(Exception e) {
			throw e;
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch(Exception e) {
					throw e;
				}
			}
		}
		return sb.toString();
	}
	
}
