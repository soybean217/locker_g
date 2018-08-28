package com.highguard.Wisdom.struts.sfaClientUpdate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.highguard.Wisdom.logging.WisdomLogger;

public class ClientFileUtil {

	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final String TEMP_UPLOADNAME = "~TEMP_UPLOADNAME";
	private final String CONFIG_XMLNAME = "AutoUpdate";
	private String rootPath;
	
	
	public ClientFileUtil(String rootPath) {
		this.rootPath = rootPath;
	}

	
	/**
	 * 从压缩文件中读取文件信息列表
	 * @param zipFilePath
	 * @return
	 */
	public List<ClientFile> getUpdateFileInfoFromZipFile(String zipFilePath){
		File file =new File(zipFilePath);
		String zipDir = getTempUploadPath();
		try {
			if(file.getName().toLowerCase().endsWith("rar")){
				unRAR(file, zipDir);
			}else{
				//unZip(file, zipDir);
				antunZip(file, zipDir);
			}
			file.delete();
			return getUpdateFileInfoFromDisk(zipDir);
		} catch (Exception e) {
			file.delete();
			removeDir(new File(zipDir));
			WisdomLogger.logError(e, "getUpdateFileInfoFromZipFile");
			return null;
		}
	}

	
	
	
	/**
	 * 从配置文件中读取文件信息列表
	 * @param configFilePath
	 */
	@SuppressWarnings("unchecked")
	public UpdateFileInfo getUpdateFileInfoFromConfig() {
		UpdateFileInfo info =new UpdateFileInfo();
		Document document = getXmlDoc(getConfigFilePath());
		if (document == null) {
			return info;
		}
		try {
			Element root = document.getRootElement();
			Element updFileElm = root.element("updateFilesList");
			Element productElm = root.element("productVersion");
			Element descElm = root.element("versionDesc");
			Element restartElm = root.element("restart");
			List<Element> fileListElms = updFileElm.elements("file");
			if (fileListElms != null) {
				ClientFile cf = null;
				List<ClientFile> list_files = new ArrayList<ClientFile>();
				for (Element fileElm : fileListElms) {
					cf = new ClientFile();
					cf.setFileId(fileElm.attributeValue("id"));
					Element nameElm = fileElm.element("name");
					Element versionElm = fileElm.element("version");
					Element md5Elm = fileElm.element("md5");
					Element sizeElm = fileElm.element("size");
					Element dateElm = fileElm.element("modifyDate");
					if (nameElm != null) {
						cf.setFileName(nameElm.getStringValue());
					}
					if (versionElm != null) {
						cf.setVersion(versionElm.getStringValue());
					} 
					if (md5Elm != null) {
						cf.setMd5Value(md5Elm.getStringValue());
					}
					if (sizeElm != null) {
						cf.setFileSizeNotParse(Long.parseLong(sizeElm.getStringValue()));
					}
					if (dateElm != null) {
						cf.setLastModifyDate(dateElm.getStringValue());
					}
					list_files.add(cf);
				}
				info.setListClientFiles(list_files);
			}
			if (productElm != null) {
				info.setCurVersion(productElm.element("current").getText());
				info.setMaxVersion(productElm.element("max").getText());
				info.setMinVersion(productElm.element("min").getText());
				info.setUpdateDate(productElm.element("publishDate").getText());
			}
			if(descElm != null){
				List<Element> liElms = descElm.elements("li");
				List<String> desc = new ArrayList<String>();
				for (Element liElm : liElms) {
					desc.add(liElm.getText());
				}
				info.setUpdateDesc(desc);
			}
			if (restartElm != null) {
				info.setRestart(restartElm.element("flag").getText());
			}
		} catch (Exception e) {
			//配置文件读取失败，删除此配置文件，下次发布会重新再生成
			new File(getConfigFilePath()).delete();
		}
		return info;
	}

	
	/**
	 * 从磁盘目录中遍历文件，读取文件信息列表
	 * @param diskFileDirPath
	 * @return
	 */
	public List<ClientFile> getUpdateFileInfoFromDisk(String diskFileDirPath) {
		List<ClientFile> list_files = new ArrayList<ClientFile>();
		File rootFile = new File(diskFileDirPath);
		if(!rootFile.exists()){
			rootFile.mkdirs();
		}
		putFileToList(list_files, rootFile, "");
		return list_files;
	}
	
	
	
	/**
	 * 创建新的配置文件
	 * @param configFilePath
	 * @param list_files
	 */
	@SuppressWarnings("deprecation")
	public void creatNewConfigFile(List<ClientFile> list_files,String version,String [] desc){
		//Document document = getXmlDoc(configFilePath);
		Document document = DocumentHelper.createDocument();
		Element rootElm = document.addElement("config");
		
		Element proVersionElm = addElement(rootElm, "productVersion");
		addElement(proVersionElm, "current", version);
		addElement(proVersionElm, "min", "0.0");
		addElement(proVersionElm, "max", version);
		addElement(proVersionElm, "publishDate", new Date().toLocaleString());
		
		Element descElm = addElement(rootElm, "versionDesc");
		if(desc!=null){
			for(String d:desc){
				addElement(descElm, "li",d);
			}
		}
		
		Element fileListElm = addElement(rootElm, "updateFilesList");
		for (ClientFile cf : list_files) {
			Element fileElm = addElement(fileListElm, "file");
			fileElm.addAttribute("id", cf.getFileId());
			addElement(fileElm, "name", cf.getFilePath());
			addElement(fileElm, "version", cf.getVersion());
			addElement(fileElm, "md5", cf.getMd5Value());
			addElement(fileElm, "size", cf.getFileSize()+"");
			addElement(fileElm, "modifyDate", cf.getLastModifyDate());
		}
		try {
			writeXml(document, getConfigFilePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 编辑版本区间
	 * @param minVersion
	 * @param maxVersion
	 */
	public void editVersion(String minVersion,String maxVersion){
		Document document = getXmlDoc(getConfigFilePath());
		if (document == null) {
			return;
		}
		Element rootElm = document.getRootElement();
		Element productElm = rootElm.element("productVersion");
		if(productElm!=null){ 
			Element minElm = productElm.element("min");
			Element maxElm = productElm.element("max");
			minElm.setText(minVersion);
			maxElm.setText(maxVersion);
		}
		try {
			writeXml(document, getConfigFilePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 更新配置文件
	 * @param list_files
	 * @param version
	 * @param desc
	 */
	@SuppressWarnings("deprecation")
	public void updateConfigFile(List<ClientFile> list_files,String version,String [] desc,String isRestart){
		Document document = getXmlDoc(getConfigFilePath());
		if (document == null) {
			creatNewConfigFile(list_files, version, desc);
			return;
		}
		Element rootElm = document.getRootElement();
		Element fileListElm = rootElm.element("updateFilesList");
		Element productElm = rootElm.element("productVersion");
		Element descElm = rootElm.element("versionDesc");
		Element restartElm = rootElm.element("restart");
		if (list_files != null && list_files.size() > 0) {
			if (fileListElm != null) {
				rootElm.remove(fileListElm);
			}
			Element newFileListElm = addElement(rootElm, "updateFilesList");
			for (ClientFile cf : list_files) {
				Element fileElm = addElement(newFileListElm, "file");
				fileElm.addAttribute("id", cf.getFileId());
				addElement(fileElm, "name", cf.getFilePath());
				addElement(fileElm, "version", cf.getVersion());
				addElement(fileElm, "md5", cf.getMd5Value());
				addElement(fileElm, "size", cf.getFileSize()+"");
				addElement(fileElm, "modifyDate", cf.getLastModifyDate());
			}
		}
		if (desc != null && desc.length > 0) {
			if (descElm != null) {
				rootElm.remove(descElm);
			}
			Element newDescElm = addElement(rootElm, "versionDesc");
			for (String d : desc) {
				addElement(newDescElm, "li", d);
			}
		}
		//版本更新说明
		if (version != null && !"".equals(version)) {
			if (productElm == null) {
				productElm = addElement(rootElm, "productVersion");
				productElm.addElement("current");
				productElm.addElement("publishDate");
			}
			Element curElm = productElm.element("current");
			Element dateElm = productElm.element("publishDate");
			curElm.setText(version);
			dateElm.setText(new Date().toLocaleString());
		}
		
		if (isRestart != null && !"".equals(isRestart.trim())) {
			if (restartElm == null) {
				restartElm = addElement(rootElm,"restart");
				restartElm.addElement("flag");
			}
			Element flagElm = restartElm.element("flag");
			flagElm.setText(isRestart);
		}
		
		try {
			writeXml(document, getConfigFilePath());
		} catch (IOException e) {
			WisdomLogger.logCatch(ClientFileUtil.class, e, "write xml ");
		}
		
	}
	
	
	
	
	
	
	
	/**
	 * 清空上传的临时文件
	 */
	public void resetTempUploadFiles(){
		File tempDir = new File(rootPath, TEMP_UPLOADNAME);
		if (!tempDir.exists()) {
			tempDir.mkdirs();
		}
		removeDir(tempDir);
	}
	
	
	/**
	 * 获取 临时的上传文件目录
	 * @return
	 */
	public String getTempUploadPath(){
		File tempDir = new File(rootPath, TEMP_UPLOADNAME);
		if (!tempDir.exists()) {
			tempDir.mkdirs();
		}
		return tempDir.getAbsolutePath();
	}
	
	/**
	 * 获取更新配置文件路径
	 * @return
	 */
	public String getConfigFilePath(){
		File configFile = new File(rootPath, CONFIG_XMLNAME);
		return configFile.getAbsolutePath();
	}
	
	
	
	/**
	 * 发布更新
	 * @param version 新版本号
	 * @param list_desc 更新说明
	 * @param isRestart 是否需要重启
	 */
	public void publishFiles(String version,String [] desc,String isRestart){
		//移除旧文件
		File oldFiles =new File(rootPath);
		removeDir(oldFiles);
		
	    //将临时文件目录拷贝至发布目录下
		copyFolder( new File(rootPath, TEMP_UPLOADNAME), rootPath);
		
		//重置临时目录
		resetTempUploadFiles();
		
		List<ClientFile> list_files = getUpdateFileInfoFromDisk(rootPath);
		updateConfigFile(list_files,version,desc,isRestart);
	}
	
	
	 // 复制文件夹
	private void copyFolder(File f, String f2Str) {
		new File(f2Str).mkdir();
		for (File ff : f.listFiles()) {
			if (ff.isDirectory()) {
				copyFolder(ff, f2Str + File.separator + ff.getName());
			} else {
				copyFile(ff, new File(f2Str, ff.getName()));
			}
		}
	}

	//复制文件
	private void copyFile(File oldFile, File newFile) {
		RandomAccessFile rafOld = null;
		RandomAccessFile rafNew = null;
		try {
			rafOld = new RandomAccessFile(oldFile, "r");
			rafNew = new RandomAccessFile(newFile, "rw");
			byte[] bytes = new byte[1024 * 63];
			int len = 0;
			while ((len = rafOld.read(bytes)) != -1) {
				rafNew.write(bytes, 0, len);
			}
		} catch (Exception e) {
			
		} finally {
			try {
				if (rafOld != null)
					rafOld.close();
				if (rafNew != null)
					rafNew.close();
			} catch (IOException e) {

			}
		}
	}

	
	
	/**
	 * 删除文件夹
	 * @param dirFile
	 */
	private void removeDir(File dirFile) {
		File[] files = dirFile.listFiles();
		for (File file : files) {
			if(TEMP_UPLOADNAME.equals(file.getName())){
				continue;
			}
			if(CONFIG_XMLNAME.equals(file.getName())){
				continue;
			}
			if (file.isFile()) {
				file.delete();
			} else {
				removeDir(file);
				file.delete();
			}
		}
	}

	
	
	
	
	
	/**
	 * 解压ZIP格式文件
	 * @param file
	 * @param descDir
	 * @throws IOException 
	 * @throws IOException
	 */
	private void unZip(File file, String descDir) throws IOException  {
		ZipFile zipFile = null;
		ZipInputStream zipInputStream = null;
		try {
			zipFile = new ZipFile(file);
			zipInputStream = new ZipInputStream(new FileInputStream(file));
			ZipEntry z = null;
			InputStream in = null;
			OutputStream out = null;
			while ((z = zipInputStream.getNextEntry()) != null) {
				File f = new File(descDir, z.getName());
				if (z.isDirectory()) {
					f.mkdirs();
					continue;
				}
				f.getParentFile().mkdirs();
				try {
					in = zipFile.getInputStream(z);
					out = new FileOutputStream(f);
					byte[] buf1 = new byte[1024 * 63];
					int len;
					while ((len = in.read(buf1)) > 0) {
						out.write(buf1, 0, len);
					}
					in.close();
					out.close();
				} finally{
					if (in != null) {
						in.close();
					}
					if (out != null) {
						out.close();
					}
				}
			}
		}finally{
			if(zipInputStream!=null){
				zipInputStream.close();
			}
			if(zipFile!=null){
				zipFile.close();
			}
		}
	}
	
	
	private void antunZip(File file, String descDir) throws IOException  {
		org.apache.tools.zip.ZipFile zipFile = null;
		try {
			zipFile = new org.apache.tools.zip.ZipFile(file);
			Enumeration<?> entries = zipFile.getEntries();
			org.apache.tools.zip.ZipEntry z = null;
			InputStream in = null;
			OutputStream out = null;
			while(entries.hasMoreElements()){
				z = (org.apache.tools.zip.ZipEntry) entries.nextElement();
				File f = new File(descDir, z.getName());
				if (z.isDirectory()) {
					f.mkdirs();
					continue;
				}
				f.getParentFile().mkdirs();
				try {
					in = zipFile.getInputStream(z);
					out = new FileOutputStream(f);
					byte[] buf1 = new byte[1024 * 63];
					int len;
					while ((len = in.read(buf1)) > 0) {
						out.write(buf1, 0, len);
					}
					in.close();
					out.close();
				} finally{
					if (in != null) {
						in.close();
					}
					if (out != null) {
						out.close();
					}
				}
			}
		}finally{
			if(zipFile!=null){
				zipFile.close();
			}
		}
	}
	
	
	
	/**
	 * 解压RAR文件
	 * @param rarFile
	 * @param descDir
	 * @throws IOException
	 */
	private void unRAR(File rarFile, String descDir) throws Exception {
		String unrarCmd = "C:\\Program Files\\WinRAR\\UnRar x ";
		new File(descDir).mkdirs();
		unrarCmd += rarFile + " " + descDir;
		Runtime rt = Runtime.getRuntime();
		rt.exec(unrarCmd);
		//Process p = 
		
	}
	
	
	
	/**
	 * 保存配置文件
	 * @param document
	 * @param path
	 * @throws IOException
	 */
	private void writeXml(Document document, String path) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GB2312"); // 指定XML编码
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(path), format);
		xmlWriter.write(document);
		xmlWriter.close();
	}
	
	/**
	 * 创建节点
	 * @param parentEle
	 * @param eleName
	 * @param textValue
	 * @return
	 */
	private Element addElement(Element parentEle, String eleName,
			String textValue) {
		Element ele = parentEle.addElement(eleName);
		ele.setText(textValue == null ? "" : textValue);
		return ele;
	}
	
	/**
	 * 创建节点
	 * @param parentEle
	 * @param eleName
	 * @return
	 */
	private Element addElement(Element parentEle, String eleName) {
		return parentEle.addElement(eleName);
	}
	
	/**
	 * 读    获取XMl的文档对象
	 * @param path
	 * @return
	 * @throws DocumentException
	 */
	private Document getXmlDoc(String path){
		try {
			SAXReader reader = new SAXReader();
			Document  document = reader.read(new File(path));
			return document;
		} catch (DocumentException e) {
			return null;
		}
	}
	
	
	
	/**
	 * 循环遍历文件
	 * @param list_files
	 * @param file
	 * @param filePath
	 */
	private void putFileToList(List<ClientFile> list_files,File file,String filePath){
		if(file.isFile()){
			ClientFile cf = new ClientFile();
			cf.setFileId(UUID.randomUUID().toString().replaceAll("-", ""));
			cf.setFileSize(file.length());
			cf.setFileName(file.getName());
			cf.setFilePath(filePath + file.getName());
			cf.setLastModifyDate(sdf.format(new Date(file.lastModified())));
			cf.setVersion(getVersion(file));
			cf.setMd5Value(getMd5Value(file));
			
			list_files.add(cf);
		}else{
			File[] files = file.listFiles();
			for(File f: files){
				if(TEMP_UPLOADNAME.equals(f.getName())){
					continue; //此目录为上传的临时文件目录
				}
				if(CONFIG_XMLNAME.equals(f.getName())){
					continue; //此文件为配置文件
				}
				if(f.isFile()){
					ClientFile cf = new ClientFile();
					cf.setFileId(UUID.randomUUID().toString().replaceAll("-", ""));
					cf.setFileName(f.getName());
					cf.setFilePath(filePath + f.getName());
					cf.setFileSize(f.length());
					cf.setLastModifyDate(sdf.format(new Date(f.lastModified())));
					cf.setVersion(getVersion(f));
					cf.setMd5Value(getMd5Value(f));
					list_files.add(cf);
				}else{
					putFileToList(list_files, f, filePath+f.getName()+File.separator);
				}
			}
		}
	}
	
	
	
	
	
	/**
	 * 获取文件的MD5值
	 * @param file
	 * @return
	 */
	private String getMd5Value(File file) {
		String retmes =null;
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			FileInputStream fis=new FileInputStream(file);
			byte [] buffer=new byte[8192];
			int length=0;
			while( (length=fis.read(buffer))!=-1 ){
				md.update(buffer, 0, length);
			}
			retmes= bytesToString(md.digest());
		} catch (Exception e) {
		}
		return retmes;
	}
	
	
	/**
	 * bytes转换为16进制字符串
	 * @param data
	 * @return
	 */
	private static String bytesToString(byte [] data){
		char [] hexChar  ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		char [] tempChar=new char[data.length * 2];
		for(int i=0;i<data.length;i++){
			byte b=data[i];
			tempChar[i*2]=hexChar[b >>>4 & 0x0f];
			tempChar[i*2+1]=hexChar[b & 0x0f];
		}
		return new String(tempChar);
	}
	
	/**
	 * 获取文件的版本号
	 * @param file
	 * @return
	 */
	private String getVersion(File file) {
		String retStr = null;
		RandomAccessFile raf = null;
		byte[] buffer;
		String str;
		try {
			raf = new RandomAccessFile(file, "r");
			buffer = new byte[64];
			raf.read(buffer);
			str = "" + (char) buffer[0] + (char) buffer[1];
			int peoffset = unpack(new byte[] { buffer[60], buffer[61],
					buffer[62], buffer[63] });
			raf.seek(peoffset);
			buffer = new byte[24];
			raf.read(buffer);
			str = "" + (char) buffer[0] + (char) buffer[1];
			int noSections = unpack(new byte[] { buffer[6], buffer[7] });
			int optHdrSize = unpack(new byte[] { buffer[20], buffer[21] });
			raf.seek(raf.getFilePointer() + optHdrSize);
			for (int i = 0; i < noSections; i++) {
				buffer = new byte[40];
				raf.read(buffer);
				str = "" + (char) buffer[0] + (char) buffer[1]
						+ (char) buffer[2] + (char) buffer[3]
						+ (char) buffer[4];
				if (".rsrc".equals(str)) {
					break;
				}
			}
			int infoVirt = unpack(new byte[] { buffer[12], buffer[13],
					buffer[14], buffer[15] });
			int infoSize = unpack(new byte[] { buffer[16], buffer[17],
					buffer[18], buffer[19] });
			int infoOff = unpack(new byte[] { buffer[20], buffer[21],
					buffer[22], buffer[23] });
			raf.seek(infoOff);
			buffer = new byte[infoSize];
			raf.read(buffer);
			int numDirs = unpack(new byte[] { buffer[14], buffer[15] });
			int subOff = 0;
			for (int i = 0; i < numDirs; i++) {
				int type = unpack(new byte[] { buffer[i * 8 + 16],
						buffer[i * 8 + 17], buffer[i * 8 + 18],
						buffer[i * 8 + 19] });
				if (type == 16) {
					subOff = unpack(new byte[] { buffer[i * 8 + 20],
							buffer[i * 8 + 21], buffer[i * 8 + 22],
							buffer[i * 8 + 23] });
					break;
				}
			}
			subOff = subOff & 0x7fffffff;
			infoOff = unpack(new byte[] { buffer[subOff + 20],
					buffer[subOff + 21], buffer[subOff + 22],
					buffer[subOff + 23] });
			infoOff = infoOff & 0x7fffffff;
			infoOff = unpack(new byte[] { buffer[infoOff + 20],
					buffer[infoOff + 21], buffer[infoOff + 22],
					buffer[infoOff + 23] });
			int dataOff = unpack(new byte[] { buffer[infoOff],
					buffer[infoOff + 1], buffer[infoOff + 2],
					buffer[infoOff + 3] });
			dataOff = dataOff - infoVirt;
			int version1 = unpack(new byte[] { buffer[dataOff + 48],
					buffer[dataOff + 49] });
			int version2 = unpack(new byte[] { buffer[dataOff + 50],
					buffer[dataOff + 51] });
			int version3 = unpack(new byte[] { buffer[dataOff + 52],
					buffer[dataOff + 53] });
			int version4 = unpack(new byte[] { buffer[dataOff + 54],
					buffer[dataOff + 55] });
			retStr = version2 + "." + version1 + "." + version4 + "."
					+ version3;
		} catch (Exception e) {
			
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
				}
			}
		}
		return retStr;
	}

	/**
	 * 把byte数组解析成整数
	 * @param b
	 * @return
	 */
	private static int unpack(byte[] b) {
		int num = 0;
		for (int i = 0; i < b.length; i++) {
			num = 256 * num + (b[b.length - 1 - i] & 0xff);
		}
		return num;
	}

}
