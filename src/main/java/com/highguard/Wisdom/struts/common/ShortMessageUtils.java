package com.highguard.Wisdom.struts.common;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by ws on 2017/7/21.
 */
public class ShortMessageUtils {

    private static String CorpID = "tclkj01968";
    private static String Pwd = "3390long@";


    /**
     * 随机生成6位随机验证码
     * 方法说明
     * @Discription:扩展说明
     * @return
     * @return String
     * @Author: feizi
     * @Date: 2015年4月17日 下午7:19:02
     * @ModifyUser：feizi
     * @ModifyDate: 2015年4月17日 下午7:19:02
     */
    public static String createRandomVcode(){
        //验证码
        StringBuilder vcode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            vcode.append((int) (Math.random() * 9));
        }
        return vcode.toString();
    }

    /**
     * http get请求 发送方法 其他方法同理 返回值>0 提交成功
     *
     * @param Mobile
     *            手机号码
     * @param Content
     *            发送内容
     * @param send_time
     *            定时发送的时间；可以为空，为空时为及时发送
     * @return 返回值
     * @throws MalformedURLException
     * @throws UnsupportedEncodingException
     */
    public static int sendSMSGet(String Mobile, String Content, String send_time)
            throws MalformedURLException, UnsupportedEncodingException {
        URL url = null;
        String send_content = URLEncoder.encode(
                Content.replaceAll("<br/>", " "), "GBK");// 发送内容
        url = new URL("http://inolink.com/WS/BatchSend2.aspx?CorpID="
                + CorpID + "&Pwd=" + Pwd + "&Mobile=" + Mobile + "&Content="
                + send_content + "&Cell=&SendTime=" + send_time);
        BufferedReader in = null;
        int inputLine = 0;
        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            inputLine = new Integer(in.readLine()).intValue();
        } catch (Exception e) {
            inputLine = -2;
        }
        return inputLine;
    }
    /**
     * Hppt POST请求发送方法 返回值>0 为 提交成功
     *
     * @param Mobile
     *            电话号码
     * @param Content
     *            发送内容
     * @param send_time
     *            定时发送时间，为空时，为及时发送
     * @return
     * @throws MalformedURLException
     * @throws UnsupportedEncodingException
     */
    public static int sendSMSPost(String Mobile, String Content,
                                  String send_time) throws MalformedURLException,
            UnsupportedEncodingException {

        String inputLine = "";
        int value = -2;
        DataOutputStream out = null;
        InputStream in = null;

        String send_content = URLEncoder.encode(
                Content.replaceAll("<br/>", " "), "GBK");// 发送内容

        String strUrl = "http://sdk2.028lk.com:9880/sdk2/BatchSend2.aspx";
        String param = "CorpID=" + CorpID + "&Pwd=" + Pwd + "&Mobile=" + Mobile
                + "&Content=" + send_content + "&Cell=&SendTime=" + send_time;

        try {

            inputLine = sendPost(strUrl, param);


            value = new Integer(inputLine).intValue();

        } catch (Exception e) {

            value = -2;

        }


        return value;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
