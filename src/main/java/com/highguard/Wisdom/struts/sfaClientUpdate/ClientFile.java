package com.highguard.Wisdom.struts.sfaClientUpdate;

public class ClientFile {

	private String fileId; //文件表示
	private String fileName;//文件名称
	private String filePath; //文件路径
	private String md5Value;//Md5值
	private String version;//文件版本
	private long fileSize;//文件长度
	private String lastModifyDate;//修改日期
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMd5Value() {
		return md5Value;
	}
	public void setMd5Value(String md5Value) {
		this.md5Value = md5Value;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	public long getFileSize() {
		return fileSize;
	}
	
	
	public void setFileSize(long fileSize) {
		long size = 0;
		if (fileSize != 0L) {
			size = fileSize / 1024;
			if (fileSize % 1024 != 0) {
				size += 1;
			}
		}
		this.fileSize = size;
	}
	
	public void setFileSizeNotParse(long fileSize){
		this.fileSize = fileSize;
	}
	
	public String getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
	
	
}
