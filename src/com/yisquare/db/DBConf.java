package com.yisquare.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.yisquare.tools.LogCreate;
import com.yisquare.tools.PropertiesUtil;

public class DBConf {
	private static Logger logger = Logger.getLogger(DBConf.class);
	private static String DBTYPE = null;
	
	// 2019-02-21 Add compatibility to mysql 
	private static final String Oracle = "oracle.jdbc.driver.OracleDriver";
	private static final String MySQL = "com.mysql.jdbc.Driver";

	private Connection conn = null;

	public Connection getConn() {
		try {
			String ipString = PropertiesUtil.getDBInfo();// 获取相关的db信息
			String ipArray[] = ipString.split("&");
			setDBType(ipArray[0]);
			
			// 2018-06-22 Add url type for servicename
			if(ipArray[0].equals("MySQL")){
				Class.forName(MySQL);
				conn = DriverManager.getConnection("jdbc:oracle:thin:@//"
						+ ipArray[1] + ":" + ipArray[2] + "/" + ipArray[4],
						ipArray[5], ipArray[6]);
			}else{
				Class.forName(Oracle);
				if (ipArray[3].equals("") || ipArray[3] == null) {
					conn = DriverManager.getConnection("jdbc:mysql://"
							+ ipArray[1] + ":" + ipArray[2] + "/" + ipArray[4],
							ipArray[5], ipArray[6]);
				} else {
					conn = DriverManager.getConnection("jdbc:oracle:thin:@"
							+ ipArray[1] + ":" + ipArray[2] + ":" + ipArray[3],
							ipArray[5], ipArray[6]);
				}
			}		
			
		} catch (Exception e) {
			logger.error("Exception:" + LogCreate.getException(e));
			e.printStackTrace();
		}
		return conn;
	}

	// 2018-06-22 Add entrance of serviceName
	public void getConnection(String dbType, String ip, String port, String sid,
			String serviceName, String username, String password)
			throws SQLException, ClassNotFoundException {
		String url = null;
		setDBType(dbType);
		if(dbType.endsWith("MySQL")){
			Class.forName(MySQL);
			url = "jdbc:mysql://" + ip + ":" + port + ":" + serviceName;
		}else{
			Class.forName(Oracle);
			if (sid.equals("") || sid == null) {
				url = "jdbc:oracle:thin:@//" + ip + ":" + port + "/" + serviceName;
			} else {
				url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid;
			}
		}		
		// Remove print console -- 20180612
		// System.out.println(string);
		DriverManager.getConnection(url, username, password);

	}

	public static String getDBType() {
		return DBTYPE;
	}

	public static void setDBType(String dbType) {
		DBTYPE = dbType;
	}
}