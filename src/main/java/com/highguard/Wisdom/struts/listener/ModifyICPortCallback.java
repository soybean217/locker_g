package com.highguard.Wisdom.struts.listener;

import com.highguard.Wisdom.mgmt.hibernate.beans.ICPort;
import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.manager.IcportManager;
import com.highguard.Wisdom.thirdParty.wx.common.MD5;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ws on 2017/7/9.
 */
public class ModifyICPortCallback extends MainThreadCallback {
    private String[] mTokens;
    private String mCard;
    public ModifyICPortCallback(Lattice lattice, String[] tokens) {
        super(lattice);
        mTokens = tokens;
        mCard = MD5.MD5Encode(mTokens[1]+mTokens[4]+new Date().toString()).toUpperCase();
    }

    public void send(){
        try {
            thread.sendMessage("ICID-1234-"+mTokens[1]+"-1234-"+mCard);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    boolean match(String[] tokens) {
        if( tokens[0].equals("ICID") && tokens[1].equals("OK") ){
            return true;
        }
        return false;
    }

    @Override
    boolean callback(String[] tokens) {
        IcportManager icportManager = (IcportManager)ApplicationUtil.act.getBean("icportManager");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ICPort port = new ICPort();
        port.setCreatetime(sdf.format(new Date()));
        port.setIccard(mCard);
//        port.setLattice(mTokens[1]);
        port.setNum(0);
        icportManager.saveICPort(port);
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof ModifyICPortCallback ){
            return ((ModifyICPortCallback)obj).mCard == this.mCard;
        }
        return false;
    }
}
