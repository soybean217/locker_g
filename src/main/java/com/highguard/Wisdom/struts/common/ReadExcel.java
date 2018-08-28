package com.highguard.Wisdom.struts.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 读取excel的公共类
 * 
 * @author michael
 *
 */
public class ReadExcel {

	/**
	 * 去读excel2003
	 * 
	 * @param path      文件路径
	 * @param sheetSize 第几个sheet
	 * @return
	 */
	public static List readExcel2003(String path, int sheetSize) {
		List list = new ArrayList();
		try {
			InputStream ip = new FileInputStream(path);
			POIFSFileSystem poifs = new POIFSFileSystem(ip);
			HSSFWorkbook wb = new HSSFWorkbook(poifs);
			HSSFSheet sheet = wb.getSheetAt(sheetSize);
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();
				List rowList = new ArrayList();
				Iterator cells = row.cellIterator();
				while (cells.hasNext()) {
					HSSFCell hc = (HSSFCell) cells.next();
					switch (hc.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC:
						rowList.add(hc.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_STRING:
						rowList.add(hc.getStringCellValue());
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						rowList.add(hc.getBooleanCellValue());
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						rowList.add(hc.getCellFormula());
						break;
					default:
						rowList.add("");
						break;
					}
				}
				list.add(rowList);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return list;
	}

	/**
	 * 加密
	 * 
	 * @param str
	 * @return
	 */
	public static String encrypt(String str) {
		if (str == null || "".equalsIgnoreCase(str)) {
			return "";
		}
		Encoder encoder = java.util.Base64.getEncoder();
		String encode = encoder.encodeToString(str.getBytes());
// 	   return new BASE64Encoder().encode(str.getBytes());
		return encode;
	}

	/**
	 * BASE64 解密
	 * 
	 * @param str
	 * @return
	 */
	public static String desencrypt(String str) {
		if (null == str || "".equals(str)) {
			return "";
		}
		try {
			Decoder decoder = java.util.Base64.getDecoder();
			byte[] buffer = decoder.decode(str);
//			return new String(new BASE64Decoder().decodeBuffer(str));
			return new String(buffer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
