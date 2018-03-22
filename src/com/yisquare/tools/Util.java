package com.yisquare.tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.json.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Util {
	public static String pathString = "";

	/**
	 * 根据sql语句来获取查询的记录数
	 * 
	 * @param sql
	 * @return
	 */
	public static int getIdNum(String idJson) {
		String dbCount = Util.Regex("ID\":(.*)}]", idJson, 1);
		return Integer.parseInt(dbCount);
	}

	/**
	 * 正则表达式 根据pat规则 来获取str中我们需要的内容获取的是group（i）
	 * 
	 * @param pat
	 * @param str
	 * @param i
	 * @return
	 */
	public static String Regex(String pat, String str, int i) {
		Pattern pattern = Pattern.compile(pat);// 编译我的规则
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			return matcher.group(i);
		}
		return null;
	}

	public static String getNowFormat() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		return df.format(new Date());
	}

	// public static void main(String as[]){
	// // System.out.println(getIdNum("[{\"ID\":41}]"));
	// // System.out.println(getNowFormat());
	// System.out.println(isOSLinux());
	//
	// }

	public static boolean isOSLinux() {
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if (os != null && os.toLowerCase().indexOf("linux") > -1) {
			return true;
		} else {
			return false;
		}
	}

	public static String isOSLinux(ServletContext servletContext) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void writeDBInfo(String str, String path) {
		// TODO Auto-generated constructor stub
		System.out.println("需要打印的是" + str);
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");// 设置日期格式
			Writer writer = new FileWriter(path + "/log/log.html", true);// true表示
																			// 不覆盖
			BufferedWriter br = new BufferedWriter(writer);

			br.write("<font color='red'>" + df.format(new Date())
					+ "</font></br>");
			br.write(str);
			br.write("</br>");
			br.write("</br>");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getInfo(String key) {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("ipConfig.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return p.getProperty("ip");
	}

	public void setInfo(String key, String value) {
		try {
			FileWriter writer = new FileWriter("ipConfig.properties");
			Properties p = new Properties();
			p.setProperty(key, value);
			p.store(writer, "新增信息");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String as[]) {
		// Util util = new Util();
		// util.setInfo("ip", "123");

		String json = "[{\"SERVICE_ID\":10},{\"SERVICE_ID\":9},{\"SERVICE_ID\":9},{\"SERVICE_ID\":9}]";

	}

	public static String[] getServiceIDList(String json) {

		JsonParser parser = new JsonParser();
		JsonArray jsonArray = parser.parse(json).getAsJsonArray();
		int size = jsonArray.size(), i = 0;
		String[] list = new String[size];
		Gson gson = new Gson();
		for (JsonElement serviceID : jsonArray) {
			ServiceIDBean sBean = gson.fromJson(serviceID, ServiceIDBean.class);
			list[i] = sBean.getServiceID();
			i++;
		}
		return list;
	}
}
