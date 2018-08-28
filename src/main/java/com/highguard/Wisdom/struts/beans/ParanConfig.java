package com.highguard.Wisdom.struts.beans;
/**
 * 跑步配置设置参数
 * @author Administrator
 * 
 */
public class ParanConfig {
    private String timeout ;//多少时间接收不到数据代表本次训练结束
    private String timeshow;//到达终点多少时间后显示成绩
    private String cleanshow;//多少时间后 清空屏幕
    private String delaytime;//有效读取数据的时间间隔
    private String range;//有效距离 （必须跑多少米 才算这次考核或者成绩有效）
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public String getTimeshow() {
		return timeshow;
	}
	public void setTimeshow(String timeshow) {
		this.timeshow = timeshow;
	}
	public String getCleanshow() {
		return cleanshow;
	}
	public void setCleanshow(String cleanshow) {
		this.cleanshow = cleanshow;
	}
	public String getDelaytime() {
		return delaytime;
	}
	public void setDelaytime(String delaytime) {
		this.delaytime = delaytime;
	}
}
