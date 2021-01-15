package com.luqi.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class CallUtil {
	/**   
	　* 描述：   
	　* 创建人：coco   
	　* 创建时间：2019年10月15日 上午11:02:46         
	 * @return 
	*/
	//public static void getString(String url1, String fileName,String attrUrl,String urlapi,String[] str,List<String> list,String to,int status,String[] tos) {
		public static boolean share( String fileName,String urlapi,String[] str,String type,List<String> to) {
		String result = null;
        String result1 = null;
        try {
            //String url1 = attrUrl + i.getPersistAttach();
            //String fileName = i.getPersistAttach().replace("img/", "");
           // downloadPicture(url1, fileName, attrUrl+"img/");
            File file = new File(UrlUtil.jdlj+fileName);
            String type1 = new MimetypesFileTypeMap().getContentType(file);
            String url = urlapi + "/Chat-Module/chat/upload";
           // result = doPost(url, imsession.getComment(), str[1], str[3], type, file, str[0], str[1]);
            result = doPost(url, "PQV8oo3jeeiDkLbY", str[1], str[3], type1, file, str[0], str[1]);
			/*
			 * if (result == null) { logger.error(i.getMsgID() + "-- File upload failed");
			 * Wx1callnamemap.updatewxnull(str);
			 * DbUtil.updateSendnumber(i.getMsgID());//失败次数加1，到8次后放弃查询
			 * 
			 * break; //continue; }
			 */
            long timestamp = new Date().getTime();
            String ss = getSendFile(to, type, result,fileName, str[3], file, timestamp);
            result1 = HttpUtil.doPost2(ss, urlapi + "/Chat-Module/chat/sendmsg", str[0], str[1]);
			/*
			 * if (result1 == null) { logger.error(i.getMsgID() +
			 * "-- file send not succeed"); Wx1callnamemap.updatewxnull(str);
			 * DbUtil.updateSendnumber(i.getMsgID());//失败次数加1，到8次后放弃查询
			 * 
			 * break; //continue; }
			 */
          
           // String msgid = getMsgid(result1);
            //i.setResultmsgId(Long.valueOf(msgid)).setClientMsgId(timestamp).setType(0).update();
           // file.delete();
           // Wx1callnamemap.updatewx(str);
            System.out.println("-- The file sent successfully");
            return false;
        } catch (Exception e) {
        	e.printStackTrace();
            //logger.error(i.getMsgID() + "-- file send not succeed");
           // Wx1callnamemap.updatewxnull(str);
            //DbUtil.updateSendnumber(i.getMsgID());//失败次数加1，到8次后放弃查询
        	
        }
        return true;
	}
	 //获得发送文件或图片参数
    public static String getSendFile(List<String> to,String type,String result, String fileName, String nickName, File file, long timestamp) {
    	//Immessage i, Imsession imsession,
        JSONObject paramJson = new JSONObject();
        
        //String[] to = {imsession.getComment()};
        paramJson.put("to", to);
       
        JSONObject content = new JSONObject();
		/*
		 * if (type == 1 || type == 5) { paramJson.put("type", "Image"); } else {
		 * paramJson.put("type", "File");//文件为File,图片为Image }
		 */
        paramJson.put("type", type);//文件为File,图片为Image

        paramJson.put("info", "{\"nickName\":\"" + nickName + "\"}");
        paramJson.put("clientMsgId", timestamp);
        String[] s = getString(result);

        content.put("fileName", fileName);
        content.put("fileSize", s[0]);
        content.put("fileUrl", s[1]);
        content.put("thumbnail", s[2]);
        content.put("thumbnailHeight", s[3]);
        content.put("thumbnailWidth", s[4]);
        paramJson.put("content", content.toString());
        return paramJson.toString();


        //{\"fileName\":\"8.jpg\",\"fileSize\":6737,\"fileUrl\":\"/ZjUNvmb\",\"thumbnail\":\"/ZjUNvma\",\"thumbnailHeight\":200,\"thumbnailWidth\":200}"

    }
    public static String[] getString(String result) {

        JSONObject json = JSON.parseObject(result);
        String Data = json.getString("Body");
        JSONObject json1 = JSON.parseObject(Data);
        String Data1 = json1.getString("response");
        JSONObject json2 = JSON.parseObject(Data1);
        String Data2 = json2.getString("Data");
        JSONObject json3 = JSON.parseObject(Data2);
        String[] s = {json3.getString("fileSize"), json3.getString("fileUrl"), json3.getString("thumbnail"), json3.getString("thumbnailHeight"), json3.getString("thumbnailWidth")};
        return s;

    }

    public static int getmp3Time(File file) {
        //File file = new File("C:\\music\\test2.mp3");
        int i = 0;
        try {
            MP3File f = (MP3File) AudioFileIO.read(file);
            MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
            System.out.println(audioHeader.getTrackLength());
            i = audioHeader.getTrackLength();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

	public static void downloadPicture(String urlList,String path,String url2) {  
        URL url = null;  
        String ss=path;
        try {  
        	path = URLEncoder.encode(path, "UTF-8").replace("+", "%20");
            url = new URL(url2+path);  
            DataInputStream dataInputStream = new DataInputStream(url.openStream());  
  
            FileOutputStream fileOutputStream = new FileOutputStream(new File(ss));  
            ByteArrayOutputStream output = new ByteArrayOutputStream();  
  
            byte[] buffer = new byte[1024];  
            int length;  
  
            while ((length = dataInputStream.read(buffer)) > 0) {  
                output.write(buffer, 0, length);  
            }  
            fileOutputStream.write(output.toByteArray());  
            dataInputStream.close();  
            fileOutputStream.close();  
            //System.out.println(i+"--Download successful");
        } catch (MalformedURLException e) {  
           // e.printStackTrace();  
        	System.out.println("Download failed");
        } catch (IOException e) {  
        	System.out.println("Download failed");
            e.printStackTrace();  
        }  
    }  
	public static String doPost(String url,String to,String from,String nickName,String type,File file,String LoginToken,String UserName){
		long timestamp =new Date().getTime();
		HttpClient httpClient=new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		PostMethod post=new PostMethod(url);
		post.addRequestHeader("Cookie", "LoginToken="+LoginToken+";UserName="+UserName);
		Part[] parts=new Part[10];
		parts[0]=new StringPart("isAutoSend", "false","utf8"); 
		parts[1]=new StringPart("to", to,"utf8"); 
		parts[2]=new StringPart("from", from,"utf8"); 
		parts[3]=new StringPart("info", "{\"nickName\":\""+nickName+"\"}","utf8"); 
		parts[4]=new StringPart("clientMsgId", timestamp+"","utf8"); 
		parts[5]=new StringPart("id", "WU_FILE_0","utf8"); 
		parts[6]=new StringPart("name", file.getName(),"utf8"); 
		parts[7]=new StringPart("lastModifiedDate", ""+file.lastModified(),"utf8"); 
		parts[8]=new StringPart("type", type,"utf8"); 
		//parts[9]=new StringPart("file", file.getName(),"utf8"); 
		//parts[1]=new StringPart("sex", sex, "utf8");
		try {
			parts[9]=new FilePart("file", file);
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
	 /*
	 2018年8月6日下午3:38:11 coco
	*/
	/*public void getsessionName() {
		JSONObject json1=JsonUtil.getJSONObject(getRequest());
		JSONObject json= new JSONObject();
		JSONObject json2= new JSONObject();
		JSONObject json3= new JSONObject();
		JSONObject json4= new JSONObject();
		json.put("appName", "launchr");
		json.put("appToken", "verify-code");
		json.put("userName", "NO6lZyJjYRCAKd9R");
		json.put("userToken", json1.getString("LoginToken"));
		json.put("userName", json1.getString("loginName"));
		json.put("sessionName", json1.getString("GROUPID"));
		json2.put("param", json);
		json3.put("body", json2);
		json4.put("async",false);
		json4.put("authToken","NhHCGqeKtkK0dFnznZxP9FxeTF8=");
		json4.put("companyCode","xcgov");
		json4.put("companyShowID","b5WJZ1bRLDCb7x2B");
		json4.put("language","zh-cn");
		json4.put("loginName","NO6lZyJjYRCAKd9R");
		json4.put("resourceUri","/Chat-Module/chat/session");
		json4.put("type","POST");
		json4.put("userName","boyd");
		json3.put("header",json4);
	   String str=HttpUtil.doPost1("http://xcgovapi.hzxc.gov.cn/Chat-Module/chat/session", json3.toString());
	   JSONObject json33 = JSON.parseObject(str);
		String Data1=json33.getString("Body");
		JSONObject json22 = JSON.parseObject(Data1);
		String Data2=json22.getString("response");
		JSONObject json11 = JSON.parseObject(Data2);
		String Data=json11.getString("Data");
		JSONObject json333 = JSON.parseObject(Data);
		String Data22=json333.getString("data");
		renderJson(Data22);
	}*/
}
