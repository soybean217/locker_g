package com.highguard.Wisdom.struts.actions.wxapp;

import com.highguard.Wisdom.exception.SocketRuntimeException;
import com.highguard.Wisdom.exception.WebSocketRuntimeException;
import com.highguard.Wisdom.mgmt.hibernate.beans.Lattice;
import com.highguard.Wisdom.mgmt.hibernate.beans.Order;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.DeviceManager;
import com.highguard.Wisdom.mgmt.manager.TradingOrderManager;
import com.highguard.Wisdom.mgmt.manager.WebSocketManager;
import com.highguard.Wisdom.struts.common.GetPathCommon;
import com.highguard.Wisdom.struts.common.HttpRequestUtils;
import com.highguard.Wisdom.struts.common.WxApp;
import com.highguard.Wisdom.struts.listener.*;
import com.highguard.Wisdom.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.condition.Http;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by ws on 2017/7/2.
 */

@ServerEndpoint(value = "/wxapp/websocket", configurator = HttpSessionConfigurator.class)
public class WebSocket {


    //websocket
    private Session mSession;
    private HttpSession mHttpSession;

    //设备对应的thread
    private MainSocketThread mThread;

    private WebSocketManager mSessionManager;
    public static Vector<Session> allSession = new Vector<Session>();

    private User mUser;

    private Session mDebugSession;

    public final static String TOKEN_CLIENT_SCAN = "scan";
    public final static String TOKEN_CLIENT_STATUS = "status";
    public final static String TOKEN_CLIENT_QUERY_ORDER = "queryOrder";


    private static Logger logger = Logger.getLogger(WebSocket.class);

    GetPathCommon common = null;

    private TradingOrderManager tradingOrderManager = (TradingOrderManager) ApplicationUtil.act.getBean("tradingOrderManager");
    private DeviceManager deviceManager = (DeviceManager) ApplicationUtil.act.getBean("deviceManager");
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws Exception{
        mHttpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        mSession = session;
        mSessionManager = WebSocketManager.newInstance();
        
        allSession.add(session);

        if( mHttpSession != null ){
            mUser = (User) mHttpSession.getAttribute("user");
            if( mUser != null ) {
            	logger.debug(mUser.toString());
            	mSessionManager.setSocket(mUser, mSession);
            }
        }

        common = new GetPathCommon();
        WxApp.printd("new connenction" ,"WEBSOCKET", mUser);
        logger.debug("websocket in");
        
        Map<String, String> map = new HashMap<>();
        map.put("lastWeight", CallbackWeightDemo.lastWeight.toString());
        WxApp.sendMessage(mSession, map, "msg");
    }

    @OnClose
    public void onClose(Session client, CloseReason reason) throws Exception{
    	allSession.remove(client);
        WxApp.printd("connenction closed", "WEBSOCKET", mUser);
        mSessionManager.removeSocket(mUser);
    }

    @OnMessage
    public void onMessage(String message, Session session){
        mSession = session;


        if( StringUtil.isEmpty(message) ){
            return;
        }

        String price = "1.5";

        WxApp.printd("[recived]"+message, "WEBSOCKET", mUser);
        try{
            JSONObject request = JSONObject.fromObject(message);

            JSONObject data = request.getJSONObject("data");

            switch (request.getString("type")){
                case TOKEN_CLIENT_SCAN:
                    checkCurrentUser2();
                    JSONObject lattice = data.getJSONObject("lattice");
                    String action = data.getString("action");
                    mThread = SocketThered.findByLatticeId(lattice.getInt("id"));
                    if(mThread == null){
                        throw new WebSocketRuntimeException(SocketRuntimeException.DEVICEID_NOTFOUND, "抱歉，设备未在线", mSession);
                    }
                    checkLatticeExists(lattice);
                    mSessionManager.setSocket(mUser, mSession);
                    doScan(data, action);
                    break;
                case TOKEN_CLIENT_QUERY_ORDER:
                    checkCurrentUser2();
                    sendMessage(tradingOrderManager.getLastNotPayOrder(mUser), "queryOrder");
                    break;
                case TOKEN_CLIENT_STATUS:
                    checkCurrentUser2();
                    checkUserOrder();
                    break;
                default:
                    sendMessage(price, ".");
                    break;
            }
        } catch (SocketRuntimeException ex){
            WxApp.printd("[exception]"+ex.getMessage(), "WEBSOCKET", mUser);
            WxApp.sendErrorMessage(mSession, ex);
        }

    }

    /**
     * 判断用户是否是还杆，根据用户主动发起和设备的各种状态
     * @param data
     */
    public void doScan(JSONObject data, String action){
        int latticeId = data.getJSONObject("lattice").getInt("lockid");
        Lattice lattice = deviceManager.getLatticeById(latticeId);
        //用户主动发起借杆
        if( action.equals("borrow") ){
            //检查用户是否有订单未完成
            checkUserOrder();
            //扫码借杆
            ReadICCardCallback callback = new ReadICCardCallback(lattice){
                @Override
                public void exists(Lattice lattice1) {
                    //用户借杆流程
                    borrow(lattice1);
                }

                @Override
                public void notExists(Lattice lattice1) {
                    WebSocketRuntimeException ex = new WebSocketRuntimeException(SocketRuntimeException.CARD_NOTEXISTS, "抱歉，请换其他杆位!", mSession);
                    WxApp.sendErrorMessage(mSession, ex);
                }
            };
            if( mThread.addCallback(callback) ){
                callback.read();
            }
        }
        //用户扫码还杆
        else{
            //查询杆状态,如果没杆提示用户重试其他设备
            ReadICCardCallback callback = new ReadICCardCallback(lattice){

                @Override
                public void exists(Lattice lattice1) {
                    //提示用户去别的地方还
                    WebSocketRuntimeException ex = new WebSocketRuntimeException(SocketRuntimeException.CARD_EXISTS, "抱歉，请换其他空杆位还杆!", mSession);
                    WxApp.sendErrorMessage(mSession, ex);
                }

                @Override
                public void notExists(Lattice lattice1) {
                    //扫码还杆流程
                    giveBack(lattice1);
                }
            };
            if( mThread.addCallback(callback) ){
                callback.read();
            }
            //取出还杆的订单号，并设置到线程里去
        }

    }

    /**
     * 用户借杆流程
     */
    void borrow(Lattice lattice){
        //发送开锁指令
        OpenLockCallback callback = new OpenLockCallback(lattice, mUser, null);
        if( mThread.addCallback(callback) ){
            callback.openLock();
        }
    }


    void checkUserOrder(){
        //验证当前用户是否有未完成的订单
        Order order = tradingOrderManager.getLastNotPayOrder(mUser);

        if( order != null && order.getState().equals("1") ){
            throw new WebSocketRuntimeException(SocketRuntimeException.LASTORDER_NOTPAY, "您上次订单尚未支付，请支付上次订单再使用", mSession);
        }
        if( order != null && order.getState().equals("0") ){
            throw new WebSocketRuntimeException(SocketRuntimeException.LASTORDER_NOTGIVEBACK, "您尚未还杆，请及时还杆", mSession);
        }
    }

    /**
     * 用户还杆流程
     */
    private void giveBack(Lattice lattice){

        OpenLockCallback openLockCallback = new OpenLockCallback(lattice, mUser, null);
        if(mThread.addCallback(openLockCallback)){
            openLockCallback.setIsGiveback(true);
            openLockCallback.openLock();
        }
        //提示用户将杆放入卡槽内
        sendMessage("请将杆放入卡槽内，并关门", "message");

    }


    /**
     * 检查设备ID是否可用
     * @param data
     */
    private void checkLatticeExists(JSONObject data){
        if( StringUtil.isEmpty(data.getString("lockid")) ){
            throw new WebSocketRuntimeException(SocketRuntimeException.DEVICEID_NOTFOUND, "找不到控制器ID", mSession);
        }
        DeviceManager deviceManager = (DeviceManager) ApplicationUtil.act.getBean("deviceManager");
        String id = data.getString("lockid");
        id = id.trim();
        Map<String,String> map = new HashMap<String,String>();
        map.put("lockid", id);

        if( deviceManager.getLatticeListCount(map) == 0 ){
            throw new WebSocketRuntimeException(SocketRuntimeException.DEVICEID_NOTFOUND, "找不到控制器ID", mSession);
        }
    }


    private void checkCurrentUser2(){
        if( mUser == null ){
            throw new WebSocketRuntimeException(SocketRuntimeException.INVALID_USER, "请先登录", mSession);
        }
        if( StringUtil.isEmpty(mUser.getTelephone()) ){
            throw new WebSocketRuntimeException(SocketRuntimeException.INVALID_USER_MOBILE, "请绑定您的手机号", mSession);
        }
    }


    @OnError
    public void onError(Session session, Throwable error){
        //关闭连接的时候不应该再发送任何东西，客户端可能会收不到,这里只做记录
        WxApp.printd("[error]"+ error.getMessage(), "WEBSOCKET", mUser);
        logger.error("[error]"+StringEscapeUtils.escapeSql(error.getMessage()), error);
        mSessionManager.removeSocket(mUser);
    }

    private void sendMessage(Object object, String type){
        String text = object == null ? "":object.toString();
        WxApp.printd("[send]"+ text, "WEBSOCKET", mUser);
        WxApp.sendMessage(mSession, object, type);
    }
    

}
