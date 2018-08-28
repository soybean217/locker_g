package com.highguard.Wisdom.struts.listener;

import com.highguard.Wisdom.exception.WebSocketRuntimeException;
import com.highguard.Wisdom.logging.WisdomLogger;
import com.highguard.Wisdom.mgmt.hibernate.beans.Order;
import com.highguard.Wisdom.struts.common.WxApp;
import com.highguard.Wisdom.util.CommunityUtils;
import com.highguard.Wisdom.util.StringUtil;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by ws on 2017/7/2.
 */
public class MainSocketThread extends BaseSocketThread {
	
	private Logger logger = Logger.getLogger(MainSocketThread.class);

    private CopyOnWriteArrayList<MainThreadCallback> callbacks;

    private List<MainThreadCallback> deletes;

    public MainSocketThread(Socket socket) {
        super(socket);
        callbacks = new CopyOnWriteArrayList<>();
        deletes = new ArrayList<>();
    }


    public boolean addCallback(MainThreadCallback callback){
        if( callbacks.contains(callback) && callback.isOnece() ){
            callbacks.remove(callback);
        }
        if(!callbacks.contains(callback)) {
            //相同控制器的callback存在则退出
            callbacks.add(callback);
            callback.setThread(this);
            return true;
        }
        return false;
    }



    @Override
    void onMessage(String message) {
        String[] datas = message.trim().split(SPLITOR);

        if( datas.length == 0 ){
            return;
        }
        
        logger.debug("[SOCKET recived]"+message);

        WxApp.printd("[recived]"+message, "SOCKET", null);

        for(MainThreadCallback cb : callbacks){
            if( cb.match(datas) ){
                if( execute(cb, datas) ){
                    deletes.add(cb);
                }
            }
        }

        //删除已成功执行的callback
        for(MainThreadCallback cb : deletes){
            if( callbacks.contains(cb) && cb.isOnece() ){
                callbacks.remove(cb);
            }
        }
        deletes.clear();

    }


    private boolean execute(MainThreadCallback callback, String[] datas){
        try {
            return callback.callback(datas);
        } catch (WebSocketRuntimeException ex) {
            if( ex.getHttpSession() != null ){
                CommunityUtils.writeUserMessage(ex.getHttpSession(), ex.getMessage(), ex.getErrorCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return callback.isOnece();
    }
}
