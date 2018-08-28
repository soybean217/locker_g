package com.highguard.Wisdom.struts.listener;

import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.manager.WebSocketManager;
import org.apache.log4j.*;
import org.apache.log4j.Logger;

import java.net.Socket;

/**
 * Created by ws on 2017/7/3.
 */
public abstract class MainThreadCallback {

    protected MainSocketThread thread;
    protected Lattice mLattice;
    protected boolean onece = false;
    protected org.apache.log4j.Logger logger = Logger.getLogger(this.getClass());
    protected WebSocketManager webSocketManager;



    public boolean isOnece(){
        return onece;
    }
    public void setOnece(boolean onece){
        this.onece = onece;
    }

    public MainThreadCallback(Lattice lattice){
        mLattice = lattice;
        webSocketManager = WebSocketManager.newInstance();
    }

    public void setThread(MainSocketThread thread){
        this.thread = thread;
    }

    public MainSocketThread getThread(){
        return this.thread;
    }

    public Lattice getLattice(){
        return mLattice;
    }

    abstract boolean match(String[] tokens);
    abstract boolean callback(String[] tokens);
}
