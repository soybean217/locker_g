package com.highguard.Wisdom.util;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpSession;

public class CommunityUtils {


    public static void writeUserMessage(HttpSession session, String message, int errorCode){
        JSONObject messageObj = new JSONObject();
        messageObj.put("status", 1);
        messageObj.put("message", message);
        messageObj.put("errorCode", errorCode);
        session.setAttribute("usermessage", messageObj);
    }
}
