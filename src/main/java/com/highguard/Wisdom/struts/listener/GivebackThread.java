package com.highguard.Wisdom.struts.listener;

import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.hibernate.beans.Order;
import com.highguard.Wisdom.mgmt.manager.TradingOrderManager;
import com.highguard.Wisdom.mgmt.manager.WebSocketManager;
import com.highguard.Wisdom.struts.common.GetPathCommon;
import com.highguard.Wisdom.struts.common.WxApp;
import net.sf.json.JSONObject;

import javax.websocket.Session;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by ws on 2017/7/22.
 */
public class GivebackThread extends Thread {

    private Order order;

    private TradingOrderManager tradingOrderManager;

    private WebSocketManager webSocketManager;

    private MainSocketThread thread;

    private Lattice mLattice;
    GetPathCommon common = null;

    public GivebackThread(Lattice lattice, Order order, TradingOrderManager orderManager, WebSocketManager socketManager, MainSocketThread thread){
        mLattice = lattice;
        this.order = order;
        tradingOrderManager = orderManager;
        webSocketManager = socketManager;
        this.thread = thread;
        common = new GetPathCommon();
    }


    @Override
    public void run() {
        //隔10秒读取
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ReadICCardCallback readICCardCallback = new ReadICCardCallback(mLattice) {
            @Override
            public void exists(Lattice lattice) {
                //杆存在则执行还杆流程
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                order.setGivebacktime(sdf.format(new Date()));


                double totalPrice = tradingOrderManager.caculate(order);

                order.setTotalprice(String.valueOf(totalPrice));
                order.setState("1");
                tradingOrderManager.addOrder(order);


                if (webSocketManager.contains(order.getUser())) {
                    Session session = webSocketManager.get(order.getUser());
                    if (session != null && session.isOpen()) {
                        WxApp.sendMessage(session, order, "pay");
                        webSocketManager.removeSocket(order.getUser());
                    }
                }
            }

            @Override
            public void notExists(Lattice lattice) {
                //不存在则什么都不动
            }
        };
        thread.addCallback(readICCardCallback);
        readICCardCallback.read();
    }
}
