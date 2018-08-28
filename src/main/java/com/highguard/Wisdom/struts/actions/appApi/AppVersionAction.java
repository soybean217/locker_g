package com.highguard.Wisdom.struts.actions.appApi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.highguard.Wisdom.mgmt.hibernate.beans.AppVersion;
import com.highguard.Wisdom.mgmt.manager.AppVersionManager;
import com.highguard.Wisdom.util.JsonUtil;
import com.highguard.Wisdom.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class AppVersionAction extends ApiBaseAction{
	@Resource
	AppVersionManager appVersionManager;
	
	 public void checkVersion(){
		 JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "");
		 String appVersion=getRequest().getParameter("appVersion");
		 if(null== appVersion || appVersion.equals("")){
			 jsonMsg=JsonUtil.JsonMsg(false, "", "未知的版本号！");
		 }else{
			 // 查询当前app版本
			 AppVersion maxVersion = appVersionManager.findMaxVersion();
			 String currVersion="" ;
			 if(null!=maxVersion){
				 currVersion=maxVersion.getAppVersion();
			 }
			 if(!currVersion.equals("") && appVersion.compareTo(currVersion)>0){
				 //不是最新版本
				 jsonMsg.put("msg", "有新的版本啦！");
				 jsonMsg.put("newVersion", currVersion);
				 jsonMsg.put("url", "");
			 }else{
				 jsonMsg.put("msg", "您的app是最新版本！");
			 }
		 }
		 writeToFrontPage(jsonMsg);
	 }
	 
	 public void upgrade(){
		String appVersion=getRequest().getParameter("appVersion");
		JSONObject jsonMsg = JsonUtil.JsonMsg(true, "", "");
		if(StringUtil.isEmpty(appVersion)){
			jsonMsg=JsonUtil.JsonMsg(false, "", "缺少版本号！");
		}else{
			// 查询当前app版本
			String currVersion= "";
			AppVersion maxVersion = appVersionManager.findMaxVersion();
			if(null!=maxVersion){
				currVersion=maxVersion.getAppVersion();
			}
			if(appVersion.compareTo(currVersion)>0){
				String path=getRequest().getSession().getServletContext().getRealPath(File.separator);
				outputFile(path+"download/app/app"+currVersion+".apk");
			}else{
				jsonMsg=JsonUtil.JsonMsg(false, "", "您的app是当前的最新版本！");
				writeToFrontPage(jsonMsg);
			}
		}
	 }
	 
	 public void outputFile(String path) {
	        try {
	        	HttpServletResponse response=getResponse();
	            // path是指欲下载的文件的路径。
	            File file = new File(path);
	            // 取得文件名。
	            String filename = file.getName();
	            // 取得文件的后缀名。
	            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

	            // 以流的形式下载文件。
	            InputStream fis = new BufferedInputStream(new FileInputStream(path));
	            byte[] buffer = new byte[fis.available()];
	            fis.read(buffer);
	            fis.close();
	            // 清空response
	            response.reset();
	            // 设置response的Header
	            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
	            response.addHeader("Content-Length", "" + file.length());
	            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	            response.setContentType("application/octet-stream");
	            toClient.write(buffer);
	            toClient.flush();
	            toClient.close();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	 
	 public static void main(String[] args) {
		String  s1="1.1";
		String  s2="1.1";

	}
}
