/*
 * Copyright(C)2009 Suzhou Highguard network Co. Ltd.
 * All right reserved.
 */
package com.highguard.Wisdom.mgmt.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.highguard.Wisdom.logging.WisdomLogger;
/**
 * @description DOM操作XML文档共同类
 * @author 王从胜
 * @date 2009/11/03
 * @version Version 1.0
 */
public class DomXML {	
	@SuppressWarnings("unchecked")
	private static final Class myClass = DomXML.class;
	/**
	 * 使用JAXP将DOM对象写到XML文件里
	 * @param document DOM的文档对象
	 * @param fileName 写入的XML文档路径
	 * @param encoding XML文档的编码方式
	 * @return String 输出文件的路径
	 * @throws TransformerException
	 */
	public static String domDocToFile(Document document,String fileName,String encoding)
			throws TransformerException {	
	// log start
	WisdomLogger.logEnterPublic(myClass,"domDocToFile()");
	// 创建TransformerFactory对象
	TransformerFactory factory = TransformerFactory.newInstance();
	// 创建Transformer对象
	Transformer transformer = factory.newTransformer();
	// 设置输出字符编码属性
	transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
	// 获取Transformer对象的输出属性
	Properties properties = transformer.getOutputProperties();
	// 设置输出为XML格式
	properties.setProperty(OutputKeys.METHOD, "xml");
	transformer.setOutputProperties(properties);
	
	// 创建DOMSource对象
	DOMSource source = new DOMSource(document);
	// 创建XSLT引擎的输出对象
	File file = new File(fileName);
	StreamResult result = new StreamResult(file);	
	// 执行DOM文档到XML文件的转换
	transformer.transform(source, result);
	try {
		writeFileTrimLine(file);
	} catch(Exception e) {
		throw new TransformerException(e);
	}
	// lod end
	WisdomLogger.logExitPublic(myClass,"domDocToFile()");
	// 输出文件的路径
	return file.getAbsolutePath();
	}
	/**
	 * 去除多余的空行
	 * @param file
	 */
	private static void writeFileTrimLine(File file) throws Exception {
		// log start
		WisdomLogger.logEnterPublic(myClass,"writeFileTrimLine()");
		BufferedReader reader = null;
		PrintWriter writer = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			StringBuffer str = new StringBuffer("");
			String tmpString = null;
			while((tmpString = reader.readLine()) != null) {
				if(tmpString.length() > 0) {
					str.append(tmpString);
					str.append("\r\n");
				}
			}
			reader.close();			
			writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			writer.print(str);
			writer.flush();
			writer.close();
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
			if(writer != null) {
				writer.close();
			}			
		}
		// lod end
		WisdomLogger.logExitPublic(myClass,"writeFileTrimLine()");	
	}	
}
