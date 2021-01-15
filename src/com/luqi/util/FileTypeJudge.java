package com.luqi.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import javax.activation.MimetypesFileTypeMap;

/**
* @author coco
* @date 2020年2月27日 下午3:25:35
* 
*/
public class FileTypeJudge {
	/** 
     * Constructor 
     */  
    private FileTypeJudge() {  
    }  
  
    /** 
     * 将文件头转换成16进制字符串 
     *  
     * @param 原生byte 
     * @return 16进制字符串 
     */  
    private static String bytesToHexString(byte[] src) {  
  
        StringBuilder stringBuilder = new StringBuilder();  
        if (src == null || src.length <= 0) {  
            return null;  
        }  
        for (int i = 0; i < src.length; i++) {  
            int v = src[i] & 0xFF;  
            String hv = Integer.toHexString(v);  
            if (hv.length() < 2) {  
                stringBuilder.append(0);  
            }  
            stringBuilder.append(hv);  
        }  
        return stringBuilder.toString();  
    }  
  
    /** 
     * 得到文件头 
     *  
     * @param filePath 
     *            文件路径 
     * @return 文件头 
     * @throws IOException 
     */  
    private static String getFileContent(InputStream is) throws IOException {  
  
        byte[] b = new byte[28];  
  
        InputStream inputStream = null;  
  
        try {  
            is.read(b, 0, 28);  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
        } finally {  
            if (inputStream != null) {  
                try {  
                    inputStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw e;  
                }  
            }  
        }  
        return bytesToHexString(b);  
    }  
  
    /** 
     * 判断文件类型 
     *  
     * @param filePath 
     *            文件路径 
     * @return 文件类型 
     */  
    public static FileType getType(InputStream is) throws IOException {  
  
        String fileHead = getFileContent(is);  
        if (fileHead == null || fileHead.length() == 0) {  
            return null;  
        }  
        fileHead = fileHead.toUpperCase();  
        FileType[] fileTypes = FileType.values();  
  
        for (FileType type : fileTypes) {  
            if (fileHead.startsWith(type.getValue())) {  
                return type;  
            }  
        }  
  
        return null;  
    }  
    /**
     * 
     * @param value 表示文件类型
     * @return 1 表示图片,2 表示文档,3 表示视频,4 表示种子,5 表示音乐,7 表示其它
     * @return
     */
    public static String isFileType(FileType value) {  
    	String type = "file";// 其他  
        // 图片  
        FileType[] pics = { FileType.JPEG, FileType.PNG, FileType.GIF, FileType.TIFF, FileType.BMP, FileType.DWG, FileType.PSD };  
  
        FileType[] docs = { FileType.RTF, FileType.XML, FileType.HTML, FileType.CSS, FileType.JS, FileType.EML, FileType.DBX, FileType.PST, FileType.XLS_DOC, FileType.XLSX_DOCX, FileType.VSD,  
                FileType.MDB, FileType.WPS, FileType.WPD, FileType.EPS, FileType.PDF, FileType.QDF, FileType.PWL, FileType.ZIP, FileType.RAR, FileType.JSP, FileType.JAVA, FileType.CLASS,  
                FileType.JAR, FileType.MF, FileType.EXE, FileType.CHM, FileType.TORRENT };  
  
        FileType[] videos = { FileType.AVI, FileType.RAM, FileType.RM, FileType.MPG, FileType.MOV, FileType.ASF, FileType.MP4, FileType.FLV, FileType.MID };  
  
       // FileType[] tottents = { FileType.TORRENT };  
  
        FileType[] audios = { FileType.WAV, FileType.MP3 };  
  
      //  FileType[] others = {};  
  
        // 图片  
        for (FileType fileType : pics) {  
            if (fileType.equals(value)) {  
            	return "image/jpeg";  
            }  
        }  
        // 文档  
        for (FileType fileType : docs) {  
            if (fileType.equals(value)) {  
            	return  "file";
            }  
        }  
        // 视频  
        for (FileType fileType : videos) {  
            if (fileType.equals(value)) {  
            	return  "video/mp4";  
            }  
        }  
		/* // 种子  
		for (FileType fileType : tottents) {  
		    if (fileType.equals(value)) {  
		        type = 4;  
		    }  
		}  */
        // 音乐  
        for (FileType fileType : audios) {  
            if (fileType.equals(value)) {  
            	//return  "audio/"+value.toString().toLowerCase();  
            	return  "audio/amr";  
            }  
        }  
        return type;  
    }  
  
    public static void main(String args[]) throws Exception {  
		/*	BufferedInputStream bis = null;
			HttpURLConnection urlconnection = null;
			URL url = null;         
			        url = new URL("http://xcgovimhttp.hzxc.gov.cn/launchr//uiyi6fe");
			    urlconnection = (HttpURLConnection) url.openConnection();
			    urlconnection.connect();
			bis = new BufferedInputStream(urlconnection.getInputStream());
			    
			System.out.println("file type:"+HttpURLConnection.guessContentTypeFromStream(bis));*/
		String fileName =new Date().getTime()+"WeChat";     //为下载的文件命名
		String filePath = "d:/work/";      //保存目录
		saveUrlAs("http://xcgovimhttp.hzxc.gov.cn/launchr//uiyi6fe", filePath , fileName,"GET");  
		File file=new  File("d:\\work\\"+fileName);
		 System.out.println(FileTypeJudge.isFileType(FileTypeJudge.getType(new FileInputStream(new File("d:\\work\\"+fileName)))));
		 file.delete();
  
    	// System.out.println(getContentType("http://xcgovimhttp.hzxc.gov.cn/launchr//uiyi6fe"));
    }  
    public static String getFileType(String url) {  
    try {
    	String fileName =new Date().getTime()+"WeChat";     //为下载的文件命名
		String filePath = UrlUtil.jdlj;      //保存目录
		saveUrlAs(url, filePath , fileName,"GET");  

			return FileTypeJudge.isFileType(FileTypeJudge.getType(new FileInputStream(new File(UrlUtil.jdlj+fileName))));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   return "file";
    
    }
    /**  
     * @从制定URL下载文件并保存到指定目录
     * @param filePath 文件将要保存的目录  
     * @param method 请求方法，包括POST和GET  
     * @param url 请求的路径  
     * @return  
     */  
      
    public static int saveUrlAs(String url,String filePath,String fileName,String method){  
         //System.out.println("fileName---->"+filePath);  
         //创建不同的文件夹目录  
         File file=new File(filePath);  
         //判断文件夹是否存在  
         if (!file.exists())  
        {  
            //如果文件夹不存在，则创建新的的文件夹  
             file.mkdirs();  
        }  
         FileOutputStream fileOut = null;  
         HttpURLConnection conn = null;  
         InputStream inputStream = null;  
         try  
        {  
             // 建立链接  
             URL httpUrl=new URL(url);  
             conn=(HttpURLConnection) httpUrl.openConnection();  
             //以Post方式提交表单，默认get方式  
             conn.setRequestMethod(method);  
             conn.setDoInput(true);    
             conn.setDoOutput(true);  
             // post方式不能使用缓存   
             conn.setUseCaches(false);  
             //连接指定的资源   
             conn.connect();  
             //获取网络输入流  
             inputStream=conn.getInputStream();  
             BufferedInputStream bis = new BufferedInputStream(inputStream);  
             //判断文件的保存路径后面是否以/结尾  
             if (!filePath.endsWith("/")) {  
      
                 filePath += "/";  
      
                 }  
             //写入到文件（注意文件保存路径的后面一定要加上文件的名称）  
             fileOut = new FileOutputStream(filePath+fileName);  
             BufferedOutputStream bos = new BufferedOutputStream(fileOut);  
               
             byte[] buf = new byte[4096];  
             int length = bis.read(buf);  
             //保存文件  
             while(length != -1)  
             {  
                 bos.write(buf, 0, length);  
                 length = bis.read(buf);  
             }  
             bos.close();  
             bis.close();  
             conn.disconnect();  
             return 1;  
        } catch (Exception e)  
        {

            e.printStackTrace();
             System.out.println("抛出异常！！");
             return 2;
        }  
           
        // return 2;
           
     }  
    public static String get302(String url) {  
        try {  
            
            System.out.println("访问地址:" + url);  
            URL serverUrl = new URL(url);  
            HttpURLConnection conn = (HttpURLConnection) serverUrl  
                    .openConnection();  
            conn.setRequestMethod("GET");  
            // 必须设置false，否则会自动redirect到Location的地址  
            conn.setInstanceFollowRedirects(false);  

            conn.addRequestProperty("Accept-Charset", "UTF-8;");  
            conn.addRequestProperty("User-Agent",  
                    "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");  
            conn.addRequestProperty("Referer", "http://zuidaima.com/");  
            conn.connect();  
            String location = conn.getHeaderField("Location");  

            serverUrl = new URL(location);  
            conn = (HttpURLConnection) serverUrl.openConnection();  
            conn.setRequestMethod("GET");  

            conn.addRequestProperty("Accept-Charset", "UTF-8;");  
            conn.addRequestProperty("User-Agent",  
                    "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.8) Firefox/3.6.8");  
            conn.addRequestProperty("Referer", "http://zuidaima.com/");  
            conn.connect();  
            System.out.println("跳转地址:" + location);  
           // Photo.savePhoto(location, false);
            
            return location;
        } catch (Exception e) {  
        	//Photo.savePhoto(url, true);
           // e.printStackTrace();
        }
    	return url;  
     }  

}
