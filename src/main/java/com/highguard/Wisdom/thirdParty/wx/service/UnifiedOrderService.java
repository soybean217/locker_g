package com.highguard.Wisdom.thirdParty.wx.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.highguard.Wisdom.thirdParty.wx.ConstantUrl;
import com.highguard.Wisdom.thirdParty.wx.beans.UnifiedOrder;
import com.highguard.Wisdom.thirdParty.wx.common.HttpsRequest;
import com.highguard.Wisdom.thirdParty.wx.common.XMLParser;

import net.sf.json.JSONObject;

public class UnifiedOrderService {
	public Map<String, Object> requestUnifiedOrder(UnifiedOrder unifiedOrder){
		try {
			HttpsRequest request=new HttpsRequest();
			String result= request.sendPost(ConstantUrl.WX_API_UNIFIEDORDER, unifiedOrder);
			Map<String, Object> mapFromXML = XMLParser.getMapFromXML(result);
			return mapFromXML;
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Map<String, Object> queryOrder(com.highguard.Wisdom.thirdParty.wx.beans.QueryOrder order){
		try {
			HttpsRequest request=new HttpsRequest();
			String result= request.sendPost(ConstantUrl.WX_ORDER_QUERY_URL, order);
			Map<String, Object> mapFromXML = XMLParser.getMapFromXML(result);
			return mapFromXML;
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
