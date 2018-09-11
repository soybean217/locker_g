/*
 * Copyright(C)2009 Suzhou Highguard network Co. Ltd.
 * All right reserved.
 */
package com.highguard.Wisdom.struts.actions;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.record.CellValueRecordInterface;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RecordFactory;
import org.apache.poi.hssf.record.RowRecord;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.UnicodeString;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts2.interceptor.SessionAware;

import com.highguard.Wisdom.logging.WisdomLogger;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @description Action基类
 * @author 王从胜
 * @date 2009/11/22
 * @version Version 1.0
 */
@SuppressWarnings({"unchecked","deprecation"})
public class BaseAction extends ActionSupport implements SessionAware {
	
	protected HttpServletRequest request;
	protected HttpServletResponse reponse;
	protected ServletContext servletContext;
	
	private static final long serialVersionUID = 1L;
	private String resultMes;
	private Map session;

	public Map getSession() {
		return session;
	}
	// 记录总条数
	private String totalNum = "0";
	// 当前页
	private String currentPage = "1";
	private int cp = 0;
	// 总页数
	private String totalPages = "0";
	// 请求地址
	private String url;
	// 跳转页数
	private String jumpPage = "1";
	// 每页显示条数
	private String pageNum = "10";
	private int pn = 10;
	private int rows = 0;
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setReponse(HttpServletResponse reponse) {
		this.reponse = reponse;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	// 隐藏变量
	private String hidVariable;
	
	/**
	 * 设置操作结果，用户界面显示
	 * @param flag true 操作成功
	 */
	public void setResult(boolean flag){
		if(flag){
			setResultMes("<span class='OKText'>保存成功</span>");
		}else{
			setResultMes("<span class='ERRText'>保存失败</span>");
		}
	}
	/**
	 * 设置操作结果，用户界面显示
	 * @param flag true 操作成功
	 */
	public void setErrorMsg(String msg){
		setResultMes("<span class='ERRText'>"+msg+"</span>");
	}
	
	public String getResultMes() {
		return resultMes;
	}

	public void setResultMes(String resultMes) {
		this.resultMes = resultMes;
	}

	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}

	public int getPn() {
		return pn;
	}

	public void setPn(int pn) {
		this.pn = pn;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getJumpPage() {
		return jumpPage;
	}

	public void setJumpPage(String jumpPage) {
		this.jumpPage = jumpPage;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getHidVariable() {
		return hidVariable;
	}

	public void setHidVariable(String hidVariable) {
		this.hidVariable = hidVariable;
	}

	public void setSession(Map session) {
		this.session = session;
	}
	
	public int getTotalPage(int totalNum) {
		int total = 0;
		int num = Integer.parseInt(pageNum);
		if(totalNum % num == 0) {
			total = totalNum / num;
		} else {
			total = totalNum / num + 1;
		}
		return total;
	}
	
//	public static boolean isWindowsOS() {
//		boolean isWindowsOS = false;
//		String osName = System.getProperty("os.name");
//		if (osName.toLowerCase().indexOf("windows")>-1) {
//			isWindowsOS = true;
//		}
//		return isWindowsOS;
//	}
	
	public static byte[] encrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(256, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] encodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(encodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			WisdomLogger.logError(e, "BaseAction.encrypt");
		}
		return null;
	}
	
	public static byte[] decrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(256, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] encodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(encodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(content);
			return result;
			
		} catch (Exception e) {
			WisdomLogger.logError(e, "BaseAction.decrypt");
		}
		return null;
	}
	
	public static void merge(InputStream[] inputs, OutputStream out) {  
	    if (inputs == null || inputs.length <= 1) {  
	      throw new IllegalArgumentException("no inputstreams or only one inputstreanm.");  
	    }  
	  
	    List<Record> rootRecords = getRecords(inputs[0]);  
	    Workbook workbook = Workbook.createWorkbook(rootRecords);  
	    List<Sheet> sheets = getSheets(workbook, rootRecords);  
	    if(sheets == null || sheets.size() == 0) {  
	      throw new IllegalArgumentException("the first excel document formart is error, it must bas one sheet at least.");  
	    }  
	    //以第一篇文档的最后一个sheet为根，以后的数据都追加在这个sheet后面  
	    Sheet rootSheet = sheets.get(sheets.size() - 1);   
	    int rootRows = getRowsOfSheet(rootSheet); //记录第一篇文档的行数，以后的行数在此基础上增加  
	    rootSheet.setLoc(rootSheet.getDimsLoc());  
	    Map<Integer, Integer> map = new HashMap(10000);  
	  
	    for (int i = 1; i < inputs.length; i++) { //从第二篇开始遍历  
	      List<Record> records = getRecords(inputs[i]);  
	      int rowsOfCurXls = 0;  
	      //遍历当前文档的每一个record  
	      for (Iterator itr = records.iterator(); itr.hasNext();) {  
	        Record record = (Record) itr.next();  
	        if (record.getSid() == RowRecord.sid) { //如果是RowRecord  
	          RowRecord rowRecord = (RowRecord) record;  
	          //调整行号  
	          rowRecord.setRowNumber(rootRows + rowRecord.getRowNumber());  
	          rootSheet.addRow(rowRecord); //追加Row  
	          rowsOfCurXls++; //记录当前文档的行数  
	        }  
	        //SST记录，SST保存xls文件中唯一的String，各个String都是对应着SST记录的索引  
	        else if (record.getSid() == SSTRecord.sid) {  
	          SSTRecord sstRecord = (SSTRecord) record;  
	          for (int j = 0; j < sstRecord.getNumUniqueStrings(); j++) {  
	            int index = workbook.addSSTString(sstRecord.getString(j));  
	            //记录原来的索引和现在的索引的对应关系  
	            map.put(Integer.valueOf(j), Integer.valueOf(index));  
	          }  
	        } else if (record.getSid() == LabelSSTRecord.sid) {  
	          LabelSSTRecord label = (LabelSSTRecord) record;  
	          //调整SST索引的对应关系  
	          label.setSSTIndex(map.get(Integer.valueOf(label.getSSTIndex())));  
	        }  
	        //追加ValueCell  
	        if (record instanceof CellValueRecordInterface) {  
	          CellValueRecordInterface cell = (CellValueRecordInterface) record;  
	          int cellRow = cell.getRow() + rootRows;  
	          cell.setRow(cellRow);  
	          rootSheet.addValueRecord(cellRow, cell);  
	        }  
	      }  
	      rootRows += rowsOfCurXls;  
	    }  
	    byte[] data = getBytes(workbook, sheets.toArray(new Sheet[0]));  
	    write(out, data);  
	  }  

	static List<Record> getRecords(InputStream input) {
	    try {  
	      POIFSFileSystem poifs = new POIFSFileSystem(input);  
	      InputStream stream = poifs.getRoot().createDocumentInputStream("Workbook");  
	      return RecordFactory.createRecords(stream);  
	    } catch (IOException e) {  
	      WisdomLogger.logError(e, "BaseAction.getRecords");
	    }  
	    return Collections.EMPTY_LIST;  
	  }
	
	static void write(OutputStream out, byte[] data) {  
	    POIFSFileSystem fs = new POIFSFileSystem();  
	    // Write out the Workbook stream  
	    try {  
	      fs.createDocument(new ByteArrayInputStream(data), "Workbook");  
	      fs.writeFilesystem(out);  
	      out.flush();  
	    } catch (IOException e) {  
	    	WisdomLogger.logError(e, "BaseAction.write");
	    } finally {  
	      try {  
	        out.close();  
	      } catch (IOException e) {  
	    	  WisdomLogger.logError(e, "BaseAction.write");
	      }  
	    }  
	  }
	
	static List<Sheet> getSheets(Workbook workbook, List records) {  
	    int recOffset = workbook.getNumRecords();  
	    int sheetNum = 0;  
	    // convert all LabelRecord records to LabelSSTRecord  
	    convertLabelRecords(records, recOffset, workbook);  
	    List<Sheet> sheets = new ArrayList();  
	    while (recOffset < records.size()) {  
	      Sheet sh = Sheet.createSheet(records, sheetNum++, recOffset);  
	  
	      recOffset = sh.getEofLoc() + 1;  
	      if (recOffset == 1) {  
	        break;  
	      }  
	      sheets.add(sh);  
	    }  
	    return sheets;  
	  }
	  
	static int getRows(List<Record> records) {  
	    int row = 0;  
	    for (Iterator itr = records.iterator(); itr.hasNext();) {  
	      Record record = (Record) itr.next();  
	      if (record.getSid() == RowRecord.sid) {  
	        row++;  
	      }  
	    }  
	    return row;  
	  }
	
	static int getRowsOfSheet(Sheet sheet) {  
	    int rows = 0;  
	    sheet.setLoc(0);  
	    while(sheet.getNextRow() != null) {  
	      rows++;  
	    }  
	    return rows;  
	  }

	static void convertLabelRecords(List records, int offset, Workbook workbook) {  
		  
	    for (int k = offset; k < records.size(); k++) {  
	      Record rec = (Record) records.get(k);  
	  
	      if (rec.getSid() == LabelRecord.sid) {  
	        LabelRecord oldrec = (LabelRecord) rec;  
	  
	        records.remove(k);  
	        LabelSSTRecord newrec = new LabelSSTRecord();  
	        int stringid = workbook.addSSTString(new UnicodeString(oldrec.getValue()));  
	  
	        newrec.setRow(oldrec.getRow());  
	        newrec.setColumn(oldrec.getColumn());  
	        newrec.setXFIndex(oldrec.getXFIndex());  
	        newrec.setSSTIndex(stringid);  
	        records.add(k, newrec);  
	      }  
	    }  
	  }

	public static byte[] getBytes(Workbook workbook, Sheet[] sheets) {  
	    // HSSFSheet[] sheets = getSheets();  
	    int nSheets = sheets.length;  
	    // before getting the workbook size we must tell the sheets that  
	    // serialization is about to occur.  
	    for (int i = 0; i < nSheets; i++) {  
	      sheets[i].preSerialize();  
	    }
	    int totalsize = workbook.getSize();  
	    
	    // pre-calculate all the sheet sizes and set BOF indexes  
	    int[] estimatedSheetSizes = new int[nSheets];  
	    for (int k = 0; k < nSheets; k++) {  
	      workbook.setSheetBof(k, totalsize);  
	      int sheetSize = sheets[k].getSize();  
	      estimatedSheetSizes[k] = sheetSize;  
	      totalsize += sheetSize;  
	    }  
	  
	    byte[] retval = new byte[totalsize];  
	    int pos = workbook.serialize(0, retval);  
	  
	    for (int k = 0; k < nSheets; k++) {  
	      int serializedSize = sheets[k].serialize(pos, retval);  
	      if (serializedSize != estimatedSheetSizes[k]) {  
	            throw new IllegalStateException("Actual serialized sheet size (" + serializedSize  
	            + ") differs from pre-calculated size (" + estimatedSheetSizes[k] + ") for sheet (" + k  
	            + ")");  
	      }  
	      pos += serializedSize;  
	    }  
	    return retval;  
	}
	
	public static File[] getDirFiles(String path) {
		File dir = new File(path);
		return dir.listFiles();
	}
	
	public static void deleteDir(String path) {
		File[] files = getDirFiles(path);
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
	}
	

}