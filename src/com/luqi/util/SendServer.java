package com.luqi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
* @author coco
* @date 2020年11月16日 下午3:41:23
* 
*/
public class SendServer {
private int num = 0;
    public static void main(String[] args) {
    	String oldPath = "D:\\360安全浏览器下载\\xcgov";
        String newPath = "D:\\360安全浏览器下载\\a";
    	new SendServer().copyFolder(oldPath, newPath,"");;
	}
    public void process() {
        
		/* Calendar calendar = Calendar.getInstance();
		String dir = calendar.get(Calendar.YEAR) + "" + getTimeString(calendar.get(Calendar.MONTH) + "");*/

        String oldPath = "D:\\360安全浏览器下载\\xcgov";
        String newPath = "D:\\360安全浏览器下载\\test";
        
        try {

            while(true){
                System.out.println("复制 " + oldPath + " 目录开始");
                long t1 = System.currentTimeMillis();
                
                num = 0;
                copyFolder(oldPath, newPath,"");
                
                long t2 = System.currentTimeMillis();
                System.out.println("复制目录结束，用时：" + (t2-t1) + "ms,共复制：" + num + "文件");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void copyFolder(String oldPath, String newPath,String fff) {
        
        try {
            File mFile = new File(newPath);
            if(!mFile .exists()){
                (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
            }
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                	if(temp.getName().contains("150_150")) {
                		
                	
                    //String fileName = newPath + "/" + file[i];
                    String fileName = newPath + "/" + fff+temp.getName().substring(temp.getName().lastIndexOf("."));
                    File testFile = new File(fileName);
                    if (!testFile.exists()) {
                        FileInputStream input = new FileInputStream(temp);
                        FileOutputStream output = new FileOutputStream(fileName);
                        byte[] b = new byte[1024 * 5];
                        int len;
                        while ((len = input.read(b)) != -1) {
                            output.write(b, 0, len);
                        }
                        output.flush();
                        output.close();
                        input.close();
                        num++;
                    }
                	}
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath,file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }
    }
    
    private String getTimeString(String time){
        if(time.length()<2){
            return "0" + time;
        }
        else{
            return time;
        }
    }

}
