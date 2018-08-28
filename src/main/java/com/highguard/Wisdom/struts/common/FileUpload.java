package com.highguard.Wisdom.struts.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.highguard.Wisdom.logging.WisdomLogger;

public class FileUpload {
	@SuppressWarnings("unchecked")
	private static final Class myClass = FileUpload.class;
	private static final int BUFFER_SIZE=16*1024; 
	private static GetPathCommon common = new GetPathCommon();
	private static final String FILE_SEPARATOR = "/";
	public static boolean upload(File inFile,String filePath,String tag,boolean flag) throws Exception{
	// log start
	WisdomLogger.logEnterPublic(myClass,"Upload()");
	InputStream in = null;   
	OutputStream out = null; 
	File upLoadFile = null;
	boolean returnFlag = false;
	try {
		if(flag){
			File decryptionFile = decryption(inFile,common.getCommonDir("RootDir"));
			String tagFlag = readLastLine(decryptionFile,"UTF-8").trim();
			if(tag == null || !(tag.equals(tagFlag))){
				return false;
			}
			File deleteTagFile = deleteTag(decryptionFile,tagFlag.length());
			upLoadFile = deleteTagFile;
		}else{
			upLoadFile = inFile;
		}
        in = new BufferedInputStream(new FileInputStream(upLoadFile), BUFFER_SIZE);   
        out = new BufferedOutputStream(new FileOutputStream(filePath),   
                BUFFER_SIZE);   
        byte[] buffer = new byte[BUFFER_SIZE];   
        int len = 0;   
        while ((len = in.read(buffer)) > 0) {   
            out.write(buffer, 0, len);   
        }
        returnFlag = true;
        
    } catch (Exception e) {
        return false;
    } finally {   
        if (null != in) {   
            try {   
                in.close();   
            } catch (IOException e) {   
                WisdomLogger.logError(e, "FileUpload");
                throw e;
            }   
        }   
        if (null != out) {   
            try {   
                out.close();   
            } catch (IOException e) {   
                WisdomLogger.logError(e, "FileUpload");
                throw e;
            }   
        }   
    }   
	// lod end
	WisdomLogger.logExitPublic(myClass,"Upload()");
	return returnFlag;
	}
	/**
	 * 文件解密
	 */
	public static File decryption(File inFile,String rootDir) throws Exception{
		// log start
		WisdomLogger.logEnterPublic(myClass,"decryption()");
		File returnFile = null;
		try{
			returnFile = new File(inFile.getName());
			// DES算法要求有一个可信任的随机数源
	         SecureRandom sr = new SecureRandom();
	        // 获得密匙数据
	         FileInputStream fi = new FileInputStream(new File(rootDir +"key.txt"));
	        byte rawKeyData[] = new byte[fi.available()];// = new byte[5];
	         fi.read(rawKeyData);
	         fi.close();
	        // 从原始密匙数据创建一个DESKeySpec对象
	         DESKeySpec dks = new DESKeySpec(rawKeyData);
	        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
	         SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(dks);
	        // Cipher对象实际完成解密操作
	         Cipher cipher = Cipher.getInstance("DES");
	        // 用密匙初始化Cipher对象
	         cipher.init(Cipher.DECRYPT_MODE, key, sr);
	        // 现在，获取数据并解密
	         FileInputStream fi2 = new FileInputStream(inFile);
	        byte encryptedData[] = new byte[fi2.available()];
	         fi2.read(encryptedData);
	         fi2.close();
	        // 正式执行解密操作
	        byte decryptedData[] = cipher.doFinal(encryptedData);
	        // 这时把数据还原成原有的类文件
	        // FileOutputStream fo = new FileOutputStream(new
	        // File("DigestPass.class"));
	        // fo.write(decryptedData);
	        // 用解密后的数据加载类并应用
	        // 用加密后的数据覆盖原文件
	        FileOutputStream fo = new FileOutputStream(returnFile);
	        fo.write(decryptedData);
	        fo.close();
		}catch(Exception e){
			throw e;
		}
		// lod end
		WisdomLogger.logExitPublic(myClass,"decryption()");
		return returnFile;
	}
	/**
	 * 删除标记
	 */
	public static File deleteTag(File file,int deleteLength) throws Exception{
		// log start
		WisdomLogger.logEnterPublic(myClass,"deleteTag()");
		RandomAccessFile raf = null;
		File returnFile = null;
		try{
			returnFile = file;
			raf = new RandomAccessFile(returnFile,"rw"); 
			long len = raf.length(); 
			raf.setLength(len - deleteLength); 
		}catch(Exception e){
			WisdomLogger.logError(e, "FileUpload");
			throw e;
		}finally{
			if(raf != null){
				raf.close(); 
			}
		}
	    WisdomLogger.logExitPublic(myClass,"deleteTag()");
	    return returnFile;
	}
	/**
	 * 读取文件最后一行
	 * @param file
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String readLastLine(File file, String charset) throws Exception {    
		  RandomAccessFile raf = null;   
		  try {   
		    raf = new RandomAccessFile(file, "rw");   
		    long len = raf.length();   
		    if (len == 0) {   
		      return "";   
		    } else {   
		      long pos = len - 1;   
		      while (pos > 0) {   
		        pos--;   
		        raf.seek(pos);   
		        if (raf.readByte() == '\n') {   
		          break;   
		        }   
		      }   
		      if (pos == 0) {   
		        raf.seek(0);   
		      }   
		      byte[] bytes = new byte[(int) (len - pos)];   
		      raf.read(bytes);   
		      if (charset == null) {   
		        return new String(bytes);   
		      } else {   
		        return new String(bytes, charset);   
		      }   
		    }   
		  } catch (Exception e) {   
			  WisdomLogger.logError(e, "FileUpload");
			  throw e;
		  } finally {   
		    if (raf != null) {   
		      try {   
		        raf.close();   
		      } catch (Exception e2) { 
		    	  WisdomLogger.logError(e2, "FileUpload");
		    	  throw e2;
		      }   
		    }   
		  }   
		}  
	
		
	public static boolean copy(String path,String destination) throws Exception {
		try{
			File oldfile = new File(path);
			if(oldfile.exists()){
				File[] files = oldfile.listFiles();
		        for(int i=0;i<files.length;i++){
					if(files[i].isFile()){
						copyFile(files[i], destination);
					}else if(oldfile.isDirectory()){
						copyFolder(files[i], destination);
					}
		        }
			}else if(!oldfile.exists()){
				return false;
			}
			return true;
		}catch(Exception e){
			WisdomLogger.logError(e, "FileUpload");
			return false;
		}
	}
	/**
	 * 复制单个文件
	 * @param oldPath String 原文件路径
	 * @param newPath String 复制后路径
	 */
	public static void copyFile(File oldFile, String newPath) throws Exception {
		try {
			int bytesum = 0;
			int byteread = 0;
			InputStream inStream = new FileInputStream(oldFile.getAbsolutePath()); //读入原文件
			if(newPath.endsWith(FILE_SEPARATOR)){
				newPath = newPath + oldFile.getName();
			}else{
				newPath = newPath + FILE_SEPARATOR + oldFile.getName();
			}
			FileOutputStream fs = new FileOutputStream(newPath);
			byte[] buffer = new byte[1444];
			while ( (byteread = inStream.read(buffer)) != -1) {	
			bytesum += byteread; //字节数 文件大小
			fs.write(buffer, 0, byteread);
			}
			fs.close();
			inStream.close();
		}catch (Exception e) {
			WisdomLogger.logError(e, "FileUpload");
			throw e;
		}
	}
	
	/**
	 * 复制整个文件夹内容
	 * @param oldPath String 原文件路径
	 * @param newPath String 复制后路径
	 */
	public static void copyFolder(File oldPath, String newPath) throws Exception {
		try {
			if(!newPath.endsWith(FILE_SEPARATOR)){
				newPath = newPath + FILE_SEPARATOR + oldPath.getName();
			}else{
				newPath = newPath + oldPath.getName();
			}
			(new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
			String[] file=oldPath.list();
			File temp=null;
			for (int i = 0; i < file.length; i++) {
				if(oldPath.getAbsolutePath().endsWith(FILE_SEPARATOR)){
					temp=new File(oldPath.getAbsolutePath()+file[i]);
				}else{
					temp=new File(oldPath.getAbsolutePath()+FILE_SEPARATOR+file[i]);
				}
				if(temp.isFile()){
					FileInputStream input = new FileInputStream(temp);
					if(!newPath.endsWith(FILE_SEPARATOR)){
						newPath = newPath + FILE_SEPARATOR;
					}
					FileOutputStream output = new FileOutputStream(newPath + temp.getName());
					byte[] b = new byte[1024 * 5];
					int len;
					while ( (len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if(temp.isDirectory()){
					//如果是子文件夹
					String oldFilePath = oldPath.getAbsolutePath();
					if(!oldFilePath.endsWith(FILE_SEPARATOR)){
						oldFilePath = oldFilePath + FILE_SEPARATOR;
					}
					copyFolder(new File(oldFilePath + file[i]),newPath);
				}
			}
		}catch (Exception e) {
			WisdomLogger.logError(e, "FileUpload");
			throw e;
		}
	}
	/**
	 * 删除目录
	 * @param path String 原目录路径
	 */
	public static void removeFolder(String path) throws Exception {
		//path是一个文件路径
		try{
			File deleteDir = new File(path);
			String[] files = deleteDir.list();
			File temp=null;
			for (int i = 0; i < files.length; i++) {
				if(path.endsWith(FILE_SEPARATOR)){
					temp=new File(path+files[i]);
				}else{
					temp=new File(path+FILE_SEPARATOR+files[i]);
				}
				if(temp.isFile()){
					temp.delete();
				}
				if(temp.isDirectory()){
					//如果是子文件夹
					if(!path.endsWith(FILE_SEPARATOR)){
						path = path + FILE_SEPARATOR;
					}
					removeFolder(path + files[i]);
				}
			}
			deleteDir.delete();
		}catch(Exception e){
			WisdomLogger.logError(e, "FileUpload");
			throw e;
		}
	}
}
