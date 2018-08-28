package com.highguard.Wisdom.struts.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 获取 Spring的 ApplicationContext 对象
 * @author Administrator
 *
 */
public class ApplicationUtil {

	
	public static ApplicationContext act = null;
	
	static {
		act = new ClassPathXmlApplicationContext("beans.xml");
	}

	public static ApplicationContext getAct() {
		return act;
	}

	public static void setAct(ApplicationContext act) {
		ApplicationUtil.act = act;
	}
	
	
	
	
	
}
