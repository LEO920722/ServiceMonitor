package com.yisquare.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.yisquare.tools.LogCreate;
import com.yisquare.tools.PropertiesUtil;

public class DBConf {
	private static Logger logger = Logger.getLogger(DBConf.class);
	private static final String name = "oracle.jdbc.driver.OracleDriver";
	// private static String url =
	// "jdbc:oracle:thin:@10.143.181.60:1521:cnywmdev"; //当部署的时候请将ip改为相应的ip
	// private static String user = "CNINTWMDVCA";
	// private static String password = "wmdvca1607";
	private Connection conn = null;

	public Connection getConn() {
		try {
			Class.forName(name);
			String ipString = PropertiesUtil.getDBInfo();// 获取相关的db信息
			String ipArray[] = ipString.split("&");
			// 2018-06-22 Add url type for servicename
			if (ipArray[2].equals("") || ipArray[2] == null) {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@//"
						+ ipArray[0] + ":" + ipArray[1] + "/" + ipArray[3],
						ipArray[4], ipArray[5]);
			} else {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@"
						+ ipArray[0] + ":" + ipArray[1] + ":" + ipArray[2],
						ipArray[4], ipArray[5]);
			}
			// conn =
			// DriverManager.getConnection("jdbc:oracle:thin:@"+ipArray[0]+"/"+ipArray[1]+":"+ipArray[2],
			// ipArray[3], ipArray[4]);
			// conn = DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			logger.error("Exception:" + LogCreate.getException(e));
			e.printStackTrace();
		}
		return conn;
	}

	// 2018-06-22 Add entrance of serviceName
	public void getConnection(String ip, String port, String sid,
			String serviceName, String username, String password)
			throws SQLException, ClassNotFoundException {
		Class.forName(name);
		String url = null;
		if (sid.equals("") || sid == null) {
			url = "jdbc:oracle:thin:@//" + ip + ":" + port + "/" + serviceName;
		} else {
			url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid;
		}
		// Remove print console -- 20180612
		// System.out.println(string);
		DriverManager.getConnection(url, username, password);

	}
}