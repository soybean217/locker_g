package com.highguard.Wisdom.struts.actions.wxapp;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ws on 2017/7/16.
 */
public class WebSocketSessionListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        ((HttpServletRequest)servletRequestEvent.getServletRequest()).getSession();
    }
}
