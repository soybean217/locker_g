package com.highguard.Wisdom.struts.listener;

import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.manager.DeviceManager;
import com.highguard.Wisdom.struts.actions.wxapp.WebSocket;
import com.highguard.Wisdom.struts.common.WxApp;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import org.apache.log4j.Logger;

/**
 * Created by ws on 2017/7/3.
 */
public class CallbackWeightDemo extends MainThreadCallback {
    DeviceManager deviceManager;
    private Logger logger = Logger.getLogger(CallbackWeightDemo.class);
    public static BigDecimal lastWeight= new BigDecimal(0);

    public CallbackWeightDemo(Lattice lattice) {
        super(lattice);
        deviceManager = (DeviceManager)ApplicationUtil.act.getBean("deviceManager");
    }

    @Override
    boolean match(String[] tokens) {
        if(  tokens[0].equals("WEIGHT") ){
            return true;
        }
        return false;
    }

    @Override
    boolean callback(String[] tokens) {
        if (tokens.length>=4&&tokens[3].startsWith("70")) {
        	BigDecimal currentWeight =new BigDecimal(tokens[3].substring(2, 8)) ;
        	if (lastWeight.compareTo(currentWeight)!=0) {
        			for (Session session : WebSocket.allSession) {
        				logger.debug(session.toString());
        				Map<String, String> map = new HashMap<>();
        		        map.put("lastWeight", currentWeight.toString());
        		        WxApp.sendMessage(session, map, "msg");
        			}
        		lastWeight = currentWeight;
        	}
        }
        logger.debug(lastWeight);
        return false;
    }
}
