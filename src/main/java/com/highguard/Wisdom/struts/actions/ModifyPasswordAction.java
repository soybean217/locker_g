package com.highguard.Wisdom.struts.actions;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.Markpassword;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.struts.common.MD5Digest;

@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
public class ModifyPasswordAction extends BaseAction {
	@Resource
	UserManager userManager;
	private static final long serialVersionUID = 1L;
	private static final Class myClass = ModifyPasswordAction.class;

	private String oldPassword = null;
	private String newPassword = null;
	private String reNewPassword = null;

    private String je  ;//最低剩余金额
	public String getJe() {
		return je;
	}

	public void setJe(String je) {
		this.je = je;
	}

	public String toModifyPassword() throws Exception {
		
		return super.execute();
	}
	
	public String modifyPassword(){
		Markpassword m = userManager.getMarkpassword("1");
		if(m==null){
			m = new Markpassword();
			m.setType("1");
			m.setValue(MD5Digest.encodeByMD5(newPassword));
		}else{
			if(MD5Digest.encodeByMD5(newPassword).equals(m.getValue())){
				m.setValue(MD5Digest.encodeByMD5(newPassword));
			}else{
				setResultMes("<font color='red'>密码错误 </font>");
				return SUCCESS;
			}
		}
		setResultMes("<font color='red'>修改成功 </font>");
		userManager.updateMarkPassword(m);
		return SUCCESS;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getReNewPassword() {
		return reNewPassword;
	}

	public void setReNewPassword(String reNewPassword) {
		this.reNewPassword = reNewPassword;
	}
	
	public String toEditJE(){//跳转到最低剩余金额修改界面
		
		Markpassword m = userManager.getMarkpassword("2");//
		if(m!=null){
			je = m.getValue();	
		}
		return SUCCESS;
	}
	
	public String editJE(){//跳转到最低剩余金额修改界面
		
		Markpassword m = userManager.getMarkpassword("2");//
		if(m==null){
			m = new Markpassword();
			m.setType("2");
			m.setValue(je);
		}else{
		    m.setValue(je);	
		}
		setResultMes("<font color='red'>修改成功 </font>");
		userManager.updateMarkPassword(m);
		return SUCCESS;
	}
	
public String toEditURL(){//跳转到最低剩余金额修改界面
		
		Markpassword m = userManager.getMarkpassword("3");//
		if(m!=null){
			je = m.getValue();	
		}
		return SUCCESS;
	}
	
	public String editURL(){//跳转到最低剩余金额修改界面
		
		Markpassword m = userManager.getMarkpassword("3");//
		if(m==null){
			m = new Markpassword();
			m.setType("3");
			m.setValue(je);
		}else{
		    m.setValue(je);	
		}
		setResultMes("<font color='red'>修改成功 </font>");
		userManager.updateMarkPassword(m);
		return SUCCESS;
	}
}
