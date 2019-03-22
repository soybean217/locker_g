package com.highguard.Wisdom.test;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;

import java.io.IOException;

public class sendsmsTecent {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String[] params = { "5678","5" };
			SmsSingleSender ssender = new SmsSingleSender(1400167573, "06b26763070fb808086a102baddf2c27");
			SmsSingleSenderResult result = ssender.sendWithParam("86", "18025314707", 240864, params, null, "", ""); // 签名参数未提供或者为空时，会使用默认签名发送短信
			System.out.println(result);
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
		}
	}

}
