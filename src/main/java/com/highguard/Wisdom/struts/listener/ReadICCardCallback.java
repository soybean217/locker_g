package com.highguard.Wisdom.struts.listener;

import com.highguard.Wisdom.exception.SocketRuntimeException;
import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.hibernate.beans.Order;
import com.highguard.Wisdom.mgmt.manager.TradingOrderManager;
import com.highguard.Wisdom.util.CommandUtils;
import com.highguard.Wisdom.util.StringUtil;

import java.net.Socket;

/**
 * Created by ws on 2017/7/2.
 */
public abstract class ReadICCardCallback extends MainThreadCallback{

    private CommandUtils.DeviceStatus mStatus;

    public ReadICCardCallback(Lattice lattice){
        super(lattice);
        this.onece = true;
    }

    public void read(){
        String command = CommandUtils.makeQueryStatus(mLattice);
        thread.sendMessage(command);
    }

    public abstract void exists(Lattice lattice);
    public abstract void notExists(Lattice lattice);

    public boolean match(String[] tokens){
        mStatus = CommandUtils.readStatus(mLattice, tokens);
        return mStatus != null;
    }

    public boolean callback(String[] tokens){
        TradingOrderManager manager = (TradingOrderManager)ApplicationUtil.act.getBean("tradingOrderManager");
        Order order = manager.getOrderByUserAndLattice(null, mLattice);
        //判断是否有杆
        if( order != null ){
            exists(mLattice);
        }
        else{
            notExists(mLattice);
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof ReadICCardCallback ){
            return ((ReadICCardCallback)obj).getLattice().getId().equals(this.getLattice().getId());
        }
        return false;
    }
}
