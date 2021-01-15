package com.luqi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

/**
* @author coco
* @date 2019年10月21日 下午4:34:59
* 
*/
public class UploadFileUtil {
	public static void main(String[] args) {
		System.out.println(doPost("http://59.202.68.43:8080/1do/file/uploadFile",new File("C:\\temp\\test\\215996335751430144.jpeg")));

	}
	public static String doPost(String url,File file){
		long timestamp =new Date().getTime();
		HttpClient httpClient=new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		PostMethod post=new PostMethod(url);
		//post.addRequestHeader("Cookie", "LoginToken="+LoginToken+";UserName="+UserName);
		Part[] parts=new Part[1];
		/*
		 * parts[0]=new StringPart("isAutoSend", "false","utf8"); parts[1]=new
		 * StringPart("to", to,"utf8"); parts[2]=new StringPart("from", from,"utf8");
		 * parts[3]=new StringPart("info", "{\"nickName\":\""+nickName+"\"}","utf8");
		 * parts[4]=new StringPart("clientMsgId", timestamp+"","utf8"); parts[5]=new
		 * StringPart("id", "WU_FILE_0","utf8"); parts[6]=new StringPart("name",
		 * file.getName(),"utf8"); parts[7]=new StringPart("lastModifiedDate",
		 * ""+file.lastModified(),"utf8"); parts[8]=new StringPart("type", type,"utf8");
		 */
		//parts[9]=new StringPart("file", file.getName(),"utf8"); 
		//parts[1]=new StringPart("sex", sex, "utf8");
		try {
			parts[0]=new FilePart("file", file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	//	post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
		try {
			int statcode = httpClient.executeMethod(post);
			if(statcode==HttpStatus.SC_OK){
				BufferedReader br = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream()));
				String str=null;
				StringBuffer sb=new StringBuffer();
				while((str=br.readLine())!=null){
					sb.append(str);
				}
				br.close();
				
				return sb.toString();
			}
		
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			post.releaseConnection();
		}
		return null;
	}
}
