package com.luqi.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.luqi.util.FileUtil;
import com.luqi.util.IDUtil;
import com.luqi.util.MsgUtil;
import com.luqi.util.UrlUtil;

/**
* @author coco
* @date 2019年10月12日 上午10:10:40
* 
*/
public class FileController extends Controller {
	/**   
	　* 描述：  附件上传 
	　* 创建人：coco   
	　* 创建时间：2019年10月12日 上午10:19:09         
	*/
	public void uploadFile() {
		List<UploadFile> uploadFiles=this.getFiles();
		List<String> urls=new ArrayList<String>();
		for (UploadFile uploadFile : uploadFiles) {
			
			//String fileName=IDUtil.getUid()+uploadFile.getOriginalFileName();
			String fileName=IDUtil.getUid()+uploadFile.getFileName().substring(uploadFile.getFileName().lastIndexOf("."));
			urls.add(UrlUtil.attrUrl+fileName);
			File file=uploadFile.getFile();				
			File t=new File(UrlUtil.jdlj+fileName);
			
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!t.getParentFile().exists()) {
				t.getParentFile().mkdirs();
			}
			try {
				t.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileUtil.fileChannelCopy(file, t);
			file.delete();
		}

		renderJson(MsgUtil.successMsg(urls));
	}
}
