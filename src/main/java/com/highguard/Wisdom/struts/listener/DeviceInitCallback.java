package com.highguard.Wisdom.struts.listener;

import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.manager.DeviceManager;

import java.util.List;

/**
 * Created by ws on 2017/7/3.
 */
public class DeviceInitCallback extends MainThreadCallback {
    DeviceManager deviceManager;

    public DeviceInitCallback(Lattice lattice) {
        super(lattice);
        deviceManager = (DeviceManager)ApplicationUtil.act.getBean("deviceManager");
    }

    @Override
    boolean match(String[] tokens) {
        if( tokens.length == 3 && tokens[0].equals("R") ){
            return true;
        }
        return false;
    }

    @Override
    boolean callback(String[] tokens) {
        List<Lattice> lattices = deviceManager.getLatticeListByLockid(tokens[2]);
        SocketThered socketThered = SocketListner.getSocketThered();

        for (Lattice lattice:lattices ) {
            socketThered.setLatticeOnLine(lattice.getId(), thread);
        }
        return false;
    }
}
