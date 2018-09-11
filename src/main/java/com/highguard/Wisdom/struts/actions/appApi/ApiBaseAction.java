package com.highguard.Wisdom.struts.actions.appApi;

import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.highguard.Wisdom.struts.actions.BaseAction;
import com.highguard.Wisdom.struts.dto.UserInformation;
import com.highguard.Wisdom.struts.service.ValidationService;
import com.highguard.Wisdom.util.MatrixToImageWriter;
import com.highguard.Wisdom.util.StringUtil;
//import com.sun.xml.internal.ws.util.StringUtils;

public class ApiBaseAction  extends BaseAction{
	
	@Resource
	ValidationService validationService;
	
	public HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	
	public HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	
	
	public UserInformation getUserInfomation(int userId){
		return validationService.getUserInformationById("0",userId);
	}
	
	/**
	 * 输出文本流到前端页面 
	 * @param obj
	 */
	public void writeToFrontPage(Object obj){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		PrintWriter out;
		try{
			out=response.getWriter();
			out.print(obj.toString());
			out.flush();
			out.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 输出文本流到前端页面 
	 * @param obj
	 */
	public void writeXmlToFrontPage(Object obj){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml");
		PrintWriter out;
		try{
			out=response.getWriter();
			out.print(obj.toString());
			out.flush();
			out.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void encodeQrcode(String content){
		if(StringUtil.isEmpty(content)){
			return;
		}
		
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); //设置字符集编码类型
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300,hints);
			
			BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix); //toBufferedImage(bitMatrix);
			ImageIO.write(image, "png", getResponse().getOutputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Map<String,String> getParameters(){
		Map<String, String> map = new HashMap<String, String>();
		Enumeration parameterNames = getRequest().getParameterNames();
		while(parameterNames.hasMoreElements()){
			String paraName=parameterNames.nextElement().toString();
			map.put(paraName, getRequest().getParameter(paraName));
		}
		return map;
	}
	
}

