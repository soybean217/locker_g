package com.highguard.Wisdom.struts.servlet;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import com.highguard.Wisdom.logging.WisdomLogger;

public class ProxoolListener implements ServletContextListener {
	private static final String XML_FILE_PROPERTY = "xmlFile";
	private static final String ContextConfigLocation_PROPERTY = "contextConfigLocation";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		ProxoolFacade.shutdown();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		ServletContext context = contextEvent.getServletContext();// 对应servlet的init方法中
																  // servletConfig.getServletCobntext()
		String appDir = contextEvent.getServletContext().getRealPath("/");
		
		Enumeration names = context.getInitParameterNames();
		while(names.hasMoreElements()) {
			String name = (String)names.nextElement();
			String value = context.getInitParameter(name);
			
			if(name.equals(XML_FILE_PROPERTY)) {
				try {
					File file = new File(value);
					if(file.isAbsolute()) {
						JAXPConfigurator.configure(value, false);
					} else {
						JAXPConfigurator.configure(appDir + File.separator + value, false);
					}
				} catch (ProxoolException e) {
					WisdomLogger.logError(e, "ProxoolListener");
				}
			} else if (!name.equals(ContextConfigLocation_PROPERTY)) {
				try {
					throw new Exception("web.xml file is not right because not has xmlFile param");
				} catch (Exception e) {
					WisdomLogger.logError(e, "ProxoolListener");
				}
			}
		}
	}
}
