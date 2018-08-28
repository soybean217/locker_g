package com.highguard.Wisdom.struts.listener;

import com.highguard.Wisdom.exception.SocketRuntimeException;
import com.highguard.Wisdom.exception.WebSocketRuntimeException;
import com.highguard.Wisdom.mgmt.hibernate.beans.*;
import com.highguard.Wisdom.mgmt.manager.DeviceManager;
import com.highguard.Wisdom.mgmt.manager.IcportManager;
import com.highguard.Wisdom.mgmt.manager.TradingOrderManager;
import com.highguard.Wisdom.struts.actions.wxapp.WebSocket;
import com.highguard.Wisdom.struts.common.WxApp;
import com.highguard.Wisdom.util.CommandUtils;
import com.highguard.Wisdom.util.CommunityUtils;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ws on 2017/7/4.
 * 往设备发送开锁指令，每隔一段时间发送一次，知道收到反馈开锁成功，生成订单
 */
public class OpenLockCallback extends MainThreadCallback{

    private User mUser;
    private HttpSession mSession;
    private Thread openLockThread;
    private boolean opening = true;
    private CommandUtils.DeviceStatus mStatus;
    private Order mOrder;
    private boolean isGiveback = false;
    private String mAction;
    private JSONObject mData;

    private boolean onlyOpen = false;

    public OpenLockCallback(Lattice lattice, User user, HttpSession sesison){
        super(lattice);
        mUser = user;
        mSession = sesison;
        onece = true;
    }

    public void setOnlyOpen(boolean onlyOpen){
        this.onlyOpen = onlyOpen;
    }

    public void setData(JSONObject data){
        mData = data;
    }

    public void setOrder(Order order){
        mOrder = order;
    }

    public void setAction(String action){
        mAction = action;
    }

    @Override
    public boolean equals(Object obj) {

        if( obj instanceof OpenLockCallback ){
            OpenLockCallback callback = (OpenLockCallback)obj;
            return callback.getLattice().getId().equals(this.getLattice().getId()) && (callback.isGiveback == this.isGiveback);
        }

        return false;
    }

    public void setIsGiveback(boolean giveback){
        this.isGiveback = giveback;
    }

    public void openLock(){
        //循环发送开锁指令
        openLockThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(opening) {
                    //发送开锁指令
                    String command = CommandUtils.makeOpenlock(mLattice);
                    thread.sendMessage(command);
                    opening = false;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        openLockThread.start();
    }
    @Override
    boolean match(String[] tokens) {
        //门开的指令
        mStatus = CommandUtils.readStatus(mLattice, tokens);
        return mStatus != null;
    }

    @Override
    boolean callback(String[] tokens) {
        if( onlyOpen ){
            return true;
        }

        //寄存服务
        if( "save".equals(mAction) ){
            if( mStatus.lock == 1 ){
                //门开了，返回成功信息
                CommunityUtils.writeUserMessage(mSession, "开锁成功", 0);
                return false;
            }
            else if(mStatus.lock == 0 && mStatus.toy == 1){
                //门关上了，行李在，生成订单
                TradingOrderManager tradingOrderManager = (TradingOrderManager) ApplicationUtil.act.getBean("tradingOrderManager");
                Lattice lattice = mLattice;
                if (lattice == null) {
                    throw new WebSocketRuntimeException(SocketRuntimeException.DEVICEID_NOTFOUND, "找不到控制器ID", mSession);
                }

                Order order = new Order();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                order.setCreatetime(sdf.format(new Date()));
                order.setState("1");
                order.setDeviceid(lattice.getDeviceid());
                order.setOrderPlace(tokens[2]);
                order.setType("1");
                order.setUserid(mUser.getId());
                order.setUser(mUser);
                order.setDevice(lattice);
                order.setOrdersn(tradingOrderManager.getOrderIdByUUId());
                tradingOrderManager.addOrder(order);


                CommunityUtils.writeUserMessage(mSession, "寄存成功", 0);
                return true;
            }
        }
        else if( "takeback".equals(mAction) ){
            if( mStatus.lock == 1 ){
                //门开了，返回成功信息
                CommunityUtils.writeUserMessage(mSession, "开锁成功", 0);
                return false;
            }
            else if(mStatus.lock == 0){
                //门关了，并且拿走了行李，则更新订单状态
//                TradingOrderManager tradingOrderManager = (TradingOrderManager) ApplicationUtil.act.getBean("tradingOrderManager");
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                mOrder.setGivebacktime(sdf.format(new Date()));
//
//
//                double totalPrice = tradingOrderManager.caculate(mOrder);
//
//                mOrder.setTotalprice(String.valueOf(totalPrice));
//                mOrder.setState("1");
//                tradingOrderManager.addOrder(mOrder);
//                CommunityUtils.writeUserMessage(mSession, "取行李成功", 0);
                return true;
            }

        }
        else if("deliver".equals(mAction)){
            //寄送服务
            if( mStatus.lock == 1 ){
                //门开了，返回成功信息
                CommunityUtils.writeUserMessage(mSession, "开锁成功", 0);
                return true;
            }
            else if (mStatus.lock == 0 && mStatus.toy == 1 ) {
                //生成订单
//                TradingOrderManager tradingOrderManager = (TradingOrderManager) ApplicationUtil.act.getBean("tradingOrderManager");
//                Lattice lattice = mLattice;
//                if (lattice == null) {
//                    throw new WebSocketRuntimeException(SocketRuntimeException.DEVICEID_NOTFOUND, "找不到控制器ID", mSession);
//                }
//
//                Order order = new Order();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                order.setCreatetime(sdf.format(new Date()));
//                order.setState("1");
//                order.setDeviceid(lattice.getDeviceid());
//                order.setOrderPlace(tokens[2]);
//                order.setType("3");
//                order.setUserid(mUser.getId());
//                order.setUser(mUser);
//                order.setDevice(lattice);
//                order.setOrdersn(tradingOrderManager.getOrderIdByUUId());
//
//                order.setOrderPlace(mData.getString("area"));
//                order.setGivebacktime(mData.getString("time"));
//                order.setTotalprice(lattice.getPromotionprice());
//                tradingOrderManager.addOrder(order);
//
//
//                CommunityUtils.writeUserMessage(mSession, "寄送成功", 0);
//                return true;
            }
        }
        return false;
    }
}
