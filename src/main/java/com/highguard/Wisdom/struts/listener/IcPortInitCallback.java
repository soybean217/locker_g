package com.highguard.Wisdom.struts.listener;

import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.manager.IcportManager;
import com.highguard.Wisdom.thirdParty.wx.common.MD5;

/**
 * Created by ws on 2017/7/9.
 */
public class IcPortInitCallback extends MainThreadCallback {
    private String defaultCard = "000102030405060708090A0B0C0D0E0F";
    private String defaultCard2 = "00000000000000000000000000000000";

    public IcPortInitCallback(Lattice lattice) {
        super(lattice);
    }

    @Override
    boolean match(String[] tokens) {
        if( tokens.length == 5 && tokens[0].equals("RDIC") && tokens[3].startsWith("7310") && (tokens[4].equals(defaultCard) || tokens[4].equals(defaultCard2)) ){
            return true;
        }
        return false;
    }

    @Override
    boolean callback(String[] tokens) {
        ModifyICPortCallback callback = new ModifyICPortCallback(mLattice, tokens);
        thread.addCallback(callback);
        callback.send();
        return false;
    }
}
