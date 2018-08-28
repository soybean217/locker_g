package com.highguard.Wisdom.mgmt.manager;


import com.highguard.Wisdom.exception.SocketRuntimeException;
import com.highguard.Wisdom.exception.WebSocketRuntimeException;
import com.highguard.Wisdom.exception.WisdomException;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.struts.actions.wxapp.WebSocket;
import com.highguard.Wisdom.struts.listener.ApplicationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by ws on 2017/7/2.
 */
@Service
@Transactional(rollbackFor = { Exception.class, WisdomException.class })
public class WebSocketManager {

    private Map<User, Session> sockets;


    public WebSocketManager(){
        sockets = new Hashtable<>();
    }


    public void setSocket(User key, Session session){
        if( sockets.containsKey(key) ){
            Session s = sockets.get(key);
            if( s != null && s.isOpen() && s != session ){
                throw new WebSocketRuntimeException(SocketRuntimeException.DEVICEID_ISUSED,"设备已被其他人使用", session);
            }
        }
        sockets.put(key, session);
    }


    public void removeSocket(User key){
        sockets.remove(key);
    }

    public boolean contains(User key){
        return sockets.containsKey(key);
    }

    public Session get(User key){
        return sockets.get(key);
    }

    public Session getDebugSession(){
        UserManager userManager = (UserManager) ApplicationUtil.act.getBean("userManager");
        User admin = userManager.getUserById(1);
        if( admin != null )
            return get(admin);
        return null;
    }

    public static WebSocketManager newInstance(){
        return (WebSocketManager) ApplicationUtil.act.getBean("webSocketManager");
    }

}
