package com.highguard.Wisdom.util;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class FileAndFolderUtil {
	
	private static final Logger logger  = Logger.getLogger(FileAndFolderUtil.class);

	public List<File> getFilesFromFolder(String path){
		List<File> result = new ArrayList<File>();
		File file = new File(path);		//获取其file对象
		File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
		for(File f:fs){					//遍历File[]数组
			if(!f.isDirectory())		{
				result.add(f);
			}
		}
		return result;
	}
}
