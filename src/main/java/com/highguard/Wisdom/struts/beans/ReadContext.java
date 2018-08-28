package com.highguard.Wisdom.struts.beans;

import java.util.Date;
/**
 * 用于存放读取到的数据
 * @author huangqm
 *
 */
public class ReadContext {

	private String cardid;//设备ID
	private Date createTime;// 读取到的时间

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
