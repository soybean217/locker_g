package com.highguard.Wisdom.struts.common;

import com.highguard.Wisdom.exception.SocketRuntimeException;
import com.highguard.Wisdom.mgmt.hibernate.beans.User;
import com.highguard.Wisdom.mgmt.manager.UserManager;
import com.highguard.Wisdom.mgmt.manager.WebSocketManager;
import com.highguard.Wisdom.struts.actions.wxapp.WebSocket;
import com.highguard.Wisdom.struts.listener.ApplicationUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.websocket.Session;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ws on 2017/7/15.
 */
public class WxApp {


    private static Logger logger = Logger.getLogger(WebSocket.class);
    private static GetPathCommon common = new GetPathCommon();

    public static JSONObject getSessionKey(String code) throws Exception{
        String appId = common.getCommonDir("appid");
        String appSecret = common.getCommonDir("appsecret");

        String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",appId,appSecret,code);

        //调用api换取openid和session_key
        return HttpRequestUtils.httpGet(url);
    }

    /**
     * 解密用户敏感数据获取用户信息
     * @param sessionKey 数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv 加密算法的初始向量
     * @return
     */
    public static JSONObject getUserInfo(String encryptedData,String sessionKey,String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decoder(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decoder(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decoder(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.fromObject(result);
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error(StringEscapeUtils.escapeSql(e.getMessage()));
        } catch (NoSuchPaddingException e) {
            logger.error(StringEscapeUtils.escapeSql(e.getMessage()));
        } catch (InvalidParameterSpecException e) {
            logger.error(StringEscapeUtils.escapeSql(e.getMessage()));
        } catch (IllegalBlockSizeException e) {
            logger.error(StringEscapeUtils.escapeSql(e.getMessage()));
        } catch (BadPaddingException e) {
            logger.error(StringEscapeUtils.escapeSql(e.getMessage()));
        } catch (UnsupportedEncodingException e) {
            logger.error(StringEscapeUtils.escapeSql(e.getMessage()));
        } catch (InvalidKeyException e) {
            logger.error(StringEscapeUtils.escapeSql(e.getMessage()));
        } catch (InvalidAlgorithmParameterException e) {
            logger.error(StringEscapeUtils.escapeSql(e.getMessage()));
        } catch (NoSuchProviderException e) {
            logger.error(StringEscapeUtils.escapeSql(e.getMessage()));
        }
        return null;
    }


    /**
     * 保存微信用户信息，并返回系统用户
     * @param userInfo
     * @return
     */
    public static User saveUserInfo(JSONObject userInfo){
        UserManager userManager = (UserManager) ApplicationUtil.act.getBean("userManager");
        User user = userManager.getUserByOpenid(userInfo.getString("openId"));

        if( user == null ) user = new User();
        user.setAvatar(userInfo.getString("avatarUrl"));
        user.setOpenid(userInfo.getString("openId"));
        user.setName(userInfo.getString("nickName"));
        user.setSex(userInfo.getString("gender").equals("1") ? "男" : "女");
        user.setAddress(userInfo.getString("province") + " " + userInfo.getString("city"));

        if( user.getId() == null  ) {
            user.setStatus("1");
            user.setBalance("0");
            userManager.saveUser(user);
            try {
                if(!StringUtils.isEmpty(common.getCommonDir("checkbalance"))){
                    user.setCheckbalance(common.getCommonDir("checkbalance"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            userManager.updateUser(user);
        }
        return user;
    }

    public static void sendMessage(Session session, Object object, String type) {
        if( session == null ){
            return;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("type", type);
        data.put("data", object);
        try {
            session.getBasicRemote().sendText(JSONObject.fromObject(data).toString());
            logger.debug("Websocket response:" + StringEscapeUtils.escapeSql(JSONObject.fromObject(data).toString()));
        } catch (IOException e) {
            logger.error(StringEscapeUtils.escapeSql(e.getMessage()));
        }
    }

    public static void printd(String message, String category, User user){
        WebSocketManager manager = WebSocketManager.newInstance();
        Session console = manager.getDebugSession();

        try {
            if( console != null && console.isOpen() )
                console.getBasicRemote().sendText(String.format("%s: <%s> %s", category, user == null ? "":user.getName(), message));
        } catch (IOException e) {
            logger.error(StringEscapeUtils.escapeSql(e.getMessage()));
        }
    }

    public static void sendErrorMessage(Session session, String message, int code){
        Map<String, String> map = new HashMap<>();
        map.put("errCode", String.valueOf(code));
        map.put("errMsg", message);
        sendMessage(session, map, "error");
    }

    public static void sendErrorMessage(Session session,Throwable e){
        int code = SocketRuntimeException.DEFAULT_ERROR_CODE;

        if( e instanceof SocketRuntimeException ){
            code = ((SocketRuntimeException)e).getErrorCode();
        }
        String message = e.getMessage();
        sendErrorMessage(session, message, code);
    }
}
