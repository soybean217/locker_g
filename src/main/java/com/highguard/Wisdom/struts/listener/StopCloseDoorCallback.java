package com.highguard.Wisdom.struts.listener;

import com.highguard.Wisdom.logging.WisdomLogger;
import com.highguard.Wisdom.mgmt.hibernate.beans.ICPort;
import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.hibernate.beans.Order;
import com.highguard.Wisdom.mgmt.manager.IcportManager;
import com.highguard.Wisdom.mgmt.manager.TradingOrderManager;
import com.highguard.Wisdom.mgmt.manager.WebSocketManager;
import com.highguard.Wisdom.struts.actions.wxapp.WebSocket;
import com.highguard.Wisdom.struts.common.GetPathCommon;
import com.highguard.Wisdom.struts.common.WxApp;
import com.highguard.Wisdom.util.CommandUtils;
//import com.sun.jna.platform.win32.Sspi;
//import net.sf.ehcache.transaction.Command;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ws on 2017/7/9.
 */
public class StopCloseDoorCallback extends MainThreadCallback {

    GetPathCommon common = null;
    CommandUtils.DeviceStatus mStatus;
    public StopCloseDoorCallback(Lattice lattice) {
        super(lattice);
        common = new GetPathCommon();
        onece = true;
    }

    @Override
    boolean match(String[] tokens) {
        mStatus = CommandUtils.readStatus(mLattice, tokens);
        return mStatus != null && mStatus.toy == 1;
    }

    @Override
    boolean callback(String[] tokens) {
        thread.sendMessage("stop");
        TradingOrderManager tradingOrderManager = (TradingOrderManager) ApplicationUtil.act.getBean("tradingOrderManager");

        ICPort icPort = mLattice.getIcport();
        Order order = tradingOrderManager.getOrderByIcport(icPort);
        if (order != null) {
            GivebackThread givebackThread = new GivebackThread(mLattice, order, tradingOrderManager, webSocketManager, thread);
            givebackThread.start();
        }
        return true;
    }

}
