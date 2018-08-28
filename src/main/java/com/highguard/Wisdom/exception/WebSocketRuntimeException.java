package com.highguard.Wisdom.exception;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * Created by ws on 2017/7/2.
 */
public class WebSocketRuntimeException extends SocketRuntimeException{

    private Session mSession;
    private HttpSession mHttpSession;
    public WebSocketRuntimeException(int errorCode, String message, Session session) {
        super(errorCode, message);
        mSession = session;
    }
    public WebSocketRuntimeException(int errorCode, String message, HttpSession session) {
        super(errorCode, message);
        mHttpSession = session;
    }
    public WebSocketRuntimeException(int errorCode, String message) {
        super(errorCode, message);
    }

    public Session getSession(){
        return mSession;
    }

    public HttpSession getHttpSession() {
        return mHttpSession;
    }
}
