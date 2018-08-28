package com.highguard.Wisdom.struts.actions;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.logging.WisdomLogger;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.common.Validator;
import com.highguard.Wisdom.struts.constant.Constants;
import com.opensymphony.xwork2.ActionContext;

/**
 * @description 登陆Action类
 * @author 王从胜
 * @date 2009/11/21
 * @version Version 1.0
 */
@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
public class LoginAction extends BaseAction {
	@Resource
	UserManager userManager;
	private static final long serialVersionUID = 1L;
	private static final Class myClass = LoginAction.class;


	
	/*@Override
	public String execute() {
		// log start
		if(username.equals("admin")&&password.equals("password")){
			ActionContext ctx = ActionContext.getContext();
			ctx.getSession().put("user", username);
			return SUCCESS;	
		}else{
			this.addFieldError("login", getText("账户名或者密码错误"));
			return INPUT;
		}
		
	}*/
	
	@Override
	public String execute() {
		// log start
		ActionContext ctx = ActionContext.getContext();
		if(username.equals("admin")){
			if(password.equals("password")){
				ctx.getSession().put("user", username);
				ctx.getSession().put("deptid", Integer.parseInt("1"));
				ctx.getSession().put("group", Integer.parseInt("60"));

				return SUCCESS;	
			}else{
				this.addFieldError("login", getText("账户名或者密码错误"));
				return INPUT;
			}
		}else if(username.equals("secadmin")){
			if(password.equals("password123456")){
				ctx.getSession().put("user", username);
				ctx.getSession().put("deptid", Integer.parseInt("1"));
				ctx.getSession().put("group", Integer.parseInt("60"));

				return SUCCESS;	
			}else{
				this.addFieldError("login", getText("账户名或者密码错误"));
				return INPUT;
			}
			
		}else{
//			List<User> users = userManager.getUserByUserName(username);
//			if(users != null && users.size() > 0){
//				for(User u : users){
//					if(StringUtils.isNotBlank(password) && password.equals(u.getPassword())){
//						if(null != u.getDeptid()){
//							ctx.getSession().put("deptid", u.getDeptid());
//						}
//						if(null != u.getUsergroup()){
//							ctx.getSession().put("group", u.getUsergroup());
//						}
//						ctx.getSession().put("user", username);
//						return SUCCESS;
//					}
//				}
//			}
//			this.addFieldError("login", getText("账户名或者密码错误"));
			return INPUT;
		}
		
	}
	
	@Override
	public void validate() {
		if(Validator.isNull(username)) {
			this.addFieldError("username.null", getText("validator.login.username.null"));
		}
		if(Validator.isNull(password)) {
			this.addFieldError("password.null", getText("validator.login.password.null"));
		}
		if(this.hasFieldErrors()) {
			WisdomLogger.logWarn(Constants.SRAAUDITLOG, username + ": Trying to login the system as [" + username + "] is failed.");
		}
	}
	
	// 用户名
	private String username;
	// 密码
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}