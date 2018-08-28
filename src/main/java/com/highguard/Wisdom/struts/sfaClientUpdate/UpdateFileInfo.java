package com.highguard.Wisdom.struts.sfaClientUpdate;

import java.util.List;

public class UpdateFileInfo {

	private String curVersion;
	private String maxVersion;
	private String minVersion;
	private String restart;
	private List<ClientFile> listClientFiles;
	private List<String> updateDesc;
	private String updateDate;
	
	
	public String getCurVersion() {
		return curVersion;
	}
	public void setCurVersion(String curVersion) {
		this.curVersion = curVersion;
	}
	public String getMaxVersion() {
		return maxVersion;
	}
	public void setMaxVersion(String maxVersion) {
		this.maxVersion = maxVersion;
	}
	public String getMinVersion() {
		return minVersion;
	}
	public void setMinVersion(String minVersion) {
		this.minVersion = minVersion;
	}
	public List<ClientFile> getListClientFiles() {
		return listClientFiles;
	}
	public void setListClientFiles(List<ClientFile> listClientFiles) {
		this.listClientFiles = listClientFiles;
	}
	public List<String> getUpdateDesc() {
		return updateDesc;
	}
	public void setUpdateDesc(List<String> updateDesc) {
		this.updateDesc = updateDesc;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getRestart() {
		return restart;
	}
	public void setRestart(String restart) {
		this.restart = restart;
	}
	
	
}
