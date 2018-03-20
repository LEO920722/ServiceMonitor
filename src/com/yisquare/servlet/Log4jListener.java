package com.yisquare.servlet;


import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

 
public class Log4jListener implements ServletContextListener {
	public static final String log4jdirkey = "log4jdir";
	public static String path = "";
	public void contextDestroyed(ServletContextEvent servletcontextevent) {
		System.getProperties().remove(log4jdirkey);
	}

	public void contextInitialized(ServletContextEvent servletcontextevent) {
		String log4jdir = servletcontextevent.getServletContext().getRealPath("/");
		path = log4jdir+"/config.txt";
		File file1 = new File(path);
        // 创建文件夹 
        if (!file1.exists()) {
        	try {
				file1.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		System.setProperty(log4jdirkey, log4jdir);
	} 
}
