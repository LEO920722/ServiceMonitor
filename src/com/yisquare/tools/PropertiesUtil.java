package com.yisquare.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;

import org.apache.log4j.Logger;
import com.yisquare.servlet.DeleteApi;
import com.yisquare.servlet.Log4jListener;

public class PropertiesUtil {
	private static Logger logger = Logger.getLogger(DeleteApi.class);
	static String path = Log4jListener.path;
	public static String getDBInfo() throws IOException{
		 logger.warn("读取的路径是："+path);
		 FileInputStream fileInputStream = new FileInputStream(path);
         InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
         StringBuffer sb = new StringBuffer();
         String text = null;
         while((text = bufferedReader.readLine()) != null){
             sb.append(text);
         }
         bufferedReader.close();
         return sb.toString();
	}
	public static void setDBInfo(String ip) throws IOException{
		/* 写入Txt文件 */
		System.out.println("设置的内容是："+ip);
		File writename = new File(path); // 相对路径，如果没有则要建立一个新的output。txt文件
		BufferedWriter out = new BufferedWriter(new FileWriter(writename));
		
		out.write(ip); // \r\n即为换行
		out.flush(); // 把缓存区内容压入文件
		out.close(); // 最后记得关闭文件
	}
}
