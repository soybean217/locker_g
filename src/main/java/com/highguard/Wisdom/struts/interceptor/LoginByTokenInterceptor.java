///*
// * Copyright(C)2009 Suzhou Highguard network Co. Ltd.
// * All right reserved.
// */
//package com.highguard.Wisdom.struts.interceptor;
//
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.struts2.StrutsStatics;
//
//import com.highguard.Wisdom.mgmt.dao.UserDao;
//import com.highguard.Wisdom.mgmt.dao.UserInfoDao;
//import com.highguard.Wisdom.mgmt.manager.UserInfoManager;
//import com.highguard.Wisdom.mgmt.manager.UserManager;
//import com.highguard.Wisdom.struts.actions.appApi.AppLoginAction;
//import com.highguard.Wisdom.struts.listener.ApplicationUtil;
//import com.highguard.Wisdom.util.StringUtil;
//import com.opensymphony.xwork2.Action;
//import com.opensymphony.xwork2.ActionContext;
//import com.opensymphony.xwork2.ActionInvocation;
//import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
///**
// * @description 用户登陆验证拦截器类
// * @version Version 1.0
// */
//public class LoginByTokenInterceptor extends AbstractInterceptor {
//	
//	private static final long serialVersionUID = 1L;
//	UserManager userManager;
//	UserInfoManager userInfoManager;
//	
//
//	@Override
//	public void init() {
////		userManager = (UserManager) ApplicationUtil.getAct().getBean("userInfoManager"); 
////		userInfoManager = (UserInfoManager) ApplicationUtil.getAct().getBean("userManager");
//	}
//
//	@Override
//	public String intercept(ActionInvocation actionInvocation) throws Exception {
//		Map<String, Object> session = actionInvocation.getInvocationContext().getSession(); 
//		Object action = actionInvocation.getAction();
//		if (action instanceof AppLoginAction) {
//			return actionInvocation.invoke();
//		}
//		ActionContext actionContext = actionInvocation.getInvocationContext();   
//        HttpServletRequest request= (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);   
//        String token = request.getParameter("token");
//        String currentToken = null==session.get("token")?"":session.get("token").toString();
//		if (!StringUtil.isEmpty(token) && currentToken.equals(token)) {
//			// 登录状态
//			return actionInvocation.invoke();
//		} else {
//			if(StringUtil.isEmpty(token)){
//				return Action.NONE;
//			}else{
//				return Action.LOGIN;
//			}
//		}
//	}
//}
