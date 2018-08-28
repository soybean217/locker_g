/*
 * Copyright(C)2009 Suzhou Highguard network Co. Ltd.
 * All right reserved.
 */
package com.highguard.Wisdom.struts.constant;
/**
 * @description 常量类
 * @author 王从胜
 * @date 2009/10/27
 * @version Version 1.0
 */
public final class Constants {
	public final static String Key_Current_User = "hg-sw-uname"; 
	public final static String Key_Current_User_Group = "hg-sw-groups"; 
	public final static String Key_Current_User_DN = "hg-sw-dn"; 
	
	public final static String Unix_File_Separator = "/"; 
	public final static char Unix_File_Separator_Char = '/'; 
	public final static String Window_File_Separator = "\\"; 
	public final static char Window_File_Separator_Char = '\\'; 
	
	public static final String ENCODING_TYPE = "utf-8";
	
	public static final String ENCODING_GBK = "GBK";
	
	public static final String ID = "id";
	
	public static final String AND = "AND";
	
	public static final int Error_Code = 500;
	
	public static final int Success_Code = 200;
	
	public static final String SRARUNLOG = "SRARunLog";
	
	public static final String SRAAUDITLOG = "SRAAuditLog";
	
	public static final String CMD_DATE = "date";
	public static final String CMD_ZIC = "zic -l";
	public static final String CMD_NTPDATE = "ntpdate";
	public static final String CMD_HWCLOCK = "hwclock -w";
	
	/*********************收件箱邮件状态**********************/
	/**收件人已读、借阅已借出*/
	public static final int STATUS_READ = 1;
	//邮件已删除
	public static final int STATUS_REMOVE = 2;
	//保密管理员已读
	public static final int STATUS_ADM_READ = 4;
	//保密管理员同意借阅
	public static final int STATUS_ADM_AGREE_ENQUIRY = 8;
	
	/**保密管理员撤销借阅 ,借阅文件已归还*/
	public static final int STATUS_ADM_CANCEL_ENQUIRY = 16;
	//文件已传完
	public static final int STATUS_FILE_TRANSFER = 32;
 	//文件传输失败
	public static final int STATUS_FILE_TRANSFER_FAILED = 64;
	
	/**借阅已传完或文件已下载*/
	public static final int STATUS_FILE_SAVE = 128;
	
	//借阅传输或文件下载失败
	public static final int STATUS_FILE_SAVE_FAILED = 256;
	/**收件已过期*/
	public static final int STATUS_EXPIRED = 512;
	// 下载中
	public static final int STATUS_DOWNLOADING = 1024;
	
	/*********************申请审批文件状态**********************/
	/** 已审批 */
	public static final int STATUS_APPROVEAL = 1;
	
	/** 已拒绝/已退回 */
	public static final int STATUS_REJECTION = 2;
	
	/** 已撤销/已作废 */
	public static final int STATUS_CANCELATION = 4;
	
	/** 已外带/已归档 */
	public static final int STATUS_BRINGOUT = 8;
	
	/** 已回收 */
	public static final int STATUS_RECOVERATION = 16;
	
	/** 已导出 */
	public static final int STATUS_EXPORTION = 32;
	
	/** 审批过期 */
	public static final int STATUS_APEXPIRATION = 64;
	
	/** 操作过期 */
	public static final int STATUS_OPEXPIRATION = 128;
	
	/** 已删除 */
	public static final int STATUS_DELETED = 256;
	
	/** 已刻录 */
	public static final int STATUS_BURN = 512;

	/** 已打印 */
	public static final int STATUS_PRINTED = 1024;
	
	/** 已复印 */
	public static final int STATUS_COPYED = 2048;
	
	/*******************角色值************************/
	/**审批者角色*/
	public static final String APPROVER = "1";
	/**保密管理员角色*/
	public static final String SEC_ADMIN = "2";
	/**组管理员角色*/
	public static final String GROUP_ADMIN = "3";
	/**公告管理员角色*/
	public static final String ANNOUN_ADMIN = "4";
	/**日志管理员角色*/
	public static final String LOG_ADMIN = "5";
	/**文印管理员*/
	public static final String PRINTEDTEXT_ADMIN ="9";

	/**收发室管理员*/
	public static final String MailRoom_ADMIN ="10";

	/**刻录管理员*/
	public static final String BURNED_ADMIN = "28";

	
	/*******************告警类型*************************/
	
	/**告警日志类型： 日志审计空间已满*/
	public static final int EVENT_LOG = 11;
	
	/** 告警日志类型：插入非法设备*/
	public static final int ILLEGAL_EQUIP = 12;
	
	/** 告警日志类型：用户空间已满*/
	public static final int USERSPACE_FULLING = 13;
	
	
	/** 告警日志类型：系统状态*/
	public static final int SYSTEM_STATE = 20;
	
}
