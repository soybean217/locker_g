/*
 * Copyright(C)2009 Suzhou Highguard network Co. Ltd.
 * All right reserved.
 */
package com.highguard.Wisdom.struts.actions;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
/**
 * @description 登陆Action类
 * @author 王从胜
 * @date 2009/11/21
 * @version Version 1.0
 */
@Controller
@Scope("prototype")
public class LogoutAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	private static final Class myClass = LogoutAction.class;
	private Map session;



	@Override
	public String execute() {
		

		return SUCCESS;
	}
}
