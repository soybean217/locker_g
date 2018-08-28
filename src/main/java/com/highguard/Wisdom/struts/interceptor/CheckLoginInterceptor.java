/*
 * Copyright(C)2009 Suzhou Highguard network Co. Ltd.
 * All right reserved.
 */
package com.highguard.Wisdom.struts.interceptor;

import java.util.Map;

import com.highguard.Wisdom.struts.actions.CheckUserAction;
import com.highguard.Wisdom.struts.actions.ControlAction;
import com.highguard.Wisdom.struts.actions.LoginAction;
import com.highguard.Wisdom.struts.actions.appApi.DeviceApiAction;
import com.highguard.Wisdom.struts.actions.appApi.FruitApiAction;
import com.highguard.Wisdom.struts.actions.appApi.OpenDoorACtion;
import com.highguard.Wisdom.struts.actions.appApi.TradingApiAction;
import com.highguard.Wisdom.struts.actions.appApi.ValidationAction;
import com.highguard.Wisdom.struts.actions.appApi.WalletApiAction;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * @description 用户登陆验证拦截器类
 * @author 王从胜
 * @date 2009/11/13
 * @version Version 1.0
 */
public class CheckLoginInterceptor extends AbstractInterceptor {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Map session = actionInvocation.getInvocationContext().getSession(); 
		Object action = actionInvocation.getAction();
		if (action instanceof LoginAction) {
			return actionInvocation.invoke();
		}
		if(action instanceof CheckUserAction){
			return actionInvocation.invoke();
		}
		if(action instanceof WalletApiAction){
			return actionInvocation.invoke();
		}
		if(action instanceof ValidationAction){
			return actionInvocation.invoke();
		}
		if(action instanceof DeviceApiAction){
			return actionInvocation.invoke();
		}
		if(action instanceof FruitApiAction){
			return actionInvocation.invoke();
		}
		if(action instanceof TradingApiAction){
			return actionInvocation.invoke();
		}
		if(action instanceof OpenDoorACtion){
			return actionInvocation.invoke();
		}
		if( action instanceof ControlAction ){
			return actionInvocation.invoke();
		}
		Object login = session.get("user");
		if (login != null ) {
			// 存在的情况下进行后续操作
			return actionInvocation.invoke();
		} else {
			session.remove("user");
			// 否则终止后续操作，返回LOGIN
			return Action.LOGIN;
		}
	}
}
