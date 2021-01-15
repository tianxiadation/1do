	package com.luqi.timer;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luqi.common.model.Notice;
import com.luqi.util.HttpUtil;
import com.luqi.util.Md5Util;
import com.luqi.util.UrlUtil;
/**
 * 
 * @author 39805
 *	百姓爆料
 */
public class BxblTask implements Runnable {
	private String businessId;//工单id
	private int state;//工单状态
	private int source;
	String appKey="14676dff2c74425aa8ab23108627746e";
	String secret="75016842071c430ebead78d3093f9a43";
	@Override
	public void run() {
		if(source==12) {
			 long timestamp=new Date().getTime();

			   //sign=md5( md5(appkey + timestamp) + secret))   备注：签名后字符应为全小写
			   String  sign=Md5Util.getMd5( Md5Util.getMd5(appKey+timestamp)+secret);
			   String url=UrlUtil.bxbl+"/api/notification/onedoCallback?timestamp="+timestamp+"&sign="+sign+"&appKey="+appKey;
			   JSONObject json=new JSONObject();
			   json.put("businessId",businessId);
			   json.put("state",state);
			  // String result = null;
			   boolean flag=true;
			   int i=0;
			   while (flag) {
				   String result=HttpUtil.doPost1(url, json.toString());
				   System.out.println("sssssssssssssssssssss"+result);
						if(result!="") {
						JSONObject r=JSON.parseObject(result);
						if(r.getIntValue("code")==0) {
							   new Notice().setTest(json.toString()).setResult(result).save();
							   flag=false;
						}
						
					}
					if(flag) {
						try {
							Thread.sleep(10000L+i*10000);
							i++;
							if(i==100) {
								new Notice().setTest(json.toString()).setResult(result).save();
								flag=false;
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
		}
	  
	
		
	}

	public BxblTask(String businessId, int state, int source) {
		super();
		this.businessId = businessId;
		this.state = state;
		this.source = source;
	}
	
	
	
}
