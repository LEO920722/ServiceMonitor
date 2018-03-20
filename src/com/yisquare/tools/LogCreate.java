package com.yisquare.tools;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class LogCreate {
	static Hashtable<String, String> htLog = new Hashtable<String,String>();//用来保存log信息
	public static void logCreate(String str,String path ) {
		// TODO Auto-generated constructor stub
		System.out.println("需要打印的是"+str);
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");//设置日期格式
			Writer writer = new FileWriter(path+"/log/log.html",true);//true表示 不覆盖
			BufferedWriter br = new BufferedWriter(writer);
			
			br.write("<font color='red'>"+df.format(new Date())+"</font></br>");
			br.write(str);
		    br.write("</br>");
		    br.write("</br>");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[]){
		//LogCreate("fdasfdsafsadfsafasd");
	}
	
	
	public static String getException(Exception e) {
		StackTraceElement[] ste = e.getStackTrace();
		StringBuffer sb = new StringBuffer();
		sb.append(e.toString() + System.getProperty("line.separator"));
		for (int i = 0; i < ste.length; i++) {
			sb.append(ste[i].toString() + System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	/***
	 * 
	 * @param ms
	 * @param creator
	 * @param time
	 * @param lever
	 * @param classname
	 */
	public static void insertLog(Object ms,String creator,String time,String lever,String classname){
		String info = "";
		if(ms instanceof Exception){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			((Throwable) ms).printStackTrace(new PrintStream(baos));  
			 info = baos.toString();
		}
		else if(ms instanceof String){
			 info = (String) ms;
		}
        htLog.put("INFO", info);
        htLog.put("LEVER", lever);
        htLog.put("CLASS",classname);
        htLog.put("TIME", "to_date('"+time+"','yyyy-mm-dd hh24:mi:ss')");
		try {
			 DBUtil.insert(htLog, "APIPlatform_LOG");
		} catch (Exception e1) {
			 // TODO Auto-generated catch block
			 e1.printStackTrace();
		}
	}
}
