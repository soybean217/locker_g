package com.highguard.Wisdom.struts.actions;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.logging.WisdomLogger;
import com.highguard.Wisdom.mgmt.xml.ParamConfigXML;
import com.highguard.Wisdom.struts.beans.ParanConfig;
import com.highguard.Wisdom.struts.common.AppConfig;

@SuppressWarnings("unchecked")
@Controller
@Scope("prototype")
public class ParanconfigAction extends BaseAction {

	private static final long serialVersionUID = -1L;
	private static final Class myClass = ParanconfigAction.class;
	private Map session;

	private ParanConfig paranConfig = new ParanConfig();

	public ParanConfig getParanConfig() {
		return paranConfig;
	}

	public void setParanConfig(ParanConfig paranConfig) {
		this.paranConfig = paranConfig;
	}

	public String listparanCfgInfo() throws Exception {
		session = getSession();
		String user = (String) session.get("user");
		if (!"admin".equals(user))
			return INPUT;
		// log start
		WisdomLogger.logEnterPublic(myClass, "listparanCfgInfo");
		StringBuffer message = new StringBuffer("User:").append(user).append(
				"listed the sfaCfgInfo");
		try {
			paranConfig = ParamConfigXML.getInstance().getParamConfig(0);

		} catch (WisdomException e) {

			message.append(" failed");
			// 审计
			WisdomLogger.auditError(e, message.toString());
			// log
			WisdomLogger.logError(e, message.toString());
			this.addFieldError("listSfaCfgInfo", e.getMessage());
			return ERROR;
		} 
		// log end
		WisdomLogger.logExitPublic(myClass, "listSfaCfgInfo");
		return SUCCESS;
	}

	/**
	 * 修改sfa信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateParanConfig() throws Exception {

		session = getSession();
		String user = (String) session.get("user");
		if (!"admin".equals(user))
			return INPUT;
		// log start
		WisdomLogger.logEnterPublic(myClass, "updateParanConfig");
		StringBuffer message = new StringBuffer("用户:").append(user).append(
				"修改sfa配置信息");
		try {
			ParamConfigXML.getInstance().updateParanConfig(paranConfig, 0);
			message.append(" 成功");
			setResult(true);
			AppConfig.resetInstance();
		} catch (WisdomException e) {
			message.append(" 失败");
			// 审计
			WisdomLogger.auditError(e, message.toString());
			// log
			WisdomLogger.logError(e, message.toString());
			this.addFieldError("updateParanConfig", e.getMessage());
			setErrorMsg(e.getMessage());
			return ERROR;
		} finally {

		}
		// log end
		WisdomLogger.logExitPublic(myClass, "updateParanConfig");
		return SUCCESS;
	}

}
