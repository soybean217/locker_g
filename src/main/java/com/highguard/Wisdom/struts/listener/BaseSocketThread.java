package com.highguard.Wisdom.struts.listener;

import com.highguard.Wisdom.exception.WebSocketRuntimeException;
import com.highguard.Wisdom.struts.common.WxApp;
import com.highguard.Wisdom.util.StringUtil;
import org.apache.log4j.*;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

/**
 * Created by ws on 2017/7/2.
 */
public abstract class BaseSocketThread extends Thread {

    protected Socket mSocket;
    protected BufferedReader in;
    protected PrintWriter out;
    protected String SPLITOR = "-";

    protected org.apache.log4j.Logger logger = Logger.getLogger(this.getClass());


    public BaseSocketThread(Socket socket){
        initSocket(socket);
    }


    protected void initSocket(Socket socket){
        if( socket != null ) {
            mSocket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void run() {
        String line = null;
        while (!mSocket.isClosed()) {
            try {
                line = in.readLine();
                onMessage(line);
            } catch (Exception e) {
                try {
                    mSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }


    public void sendMessage(String message){
        if( out != null ){
            out.println(message);
            out.flush();
            WxApp.printd("[send]"+message, "SOCKET", null);
        }
    }


    public Socket getSocket(){
        return mSocket;
    }


    abstract void onMessage(String message);
}
