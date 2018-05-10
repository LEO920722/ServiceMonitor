package com.yisquare.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.yisquare.tools.DBUtil;

public class InsertRule extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(InsertRule.class);

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * 
	 * 
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Hashtable<String, String> ht = new Hashtable<String, String>();// 用来保存Rul信息
		String RULE_ID = request.getParameter("RULE_ID");
		String RULE_NAME = request.getParameter("RULE_NAME");
		String INTERFACE_NAME = request.getParameter("INTERFACE_NAME");
		String SERVICE_NAME = request.getParameter("SERVICE_NAME");
		String CONTENT_LOGGING = request.getParameter("CONTENT_LOGGING");
		String ACTIVE_FLAG = request.getParameter("ACTIVE_FLAG");

		SERVICE_NAME = SERVICE_NAME.replace("=", ":");// Replace "=" with "="
														// due to init.js has
														// replaced this two
														// char

		try {// 将 Rule 信息写入数据库
			if (RULE_ID != "" && RULE_ID != null && RULE_ID.length() != 0) {
				ht.put("RULE_ID", RULE_ID);
				if (RULE_NAME != "" && RULE_NAME != null
						&& RULE_NAME.length() != 0) {
					ht.put("RULE_NAME", RULE_NAME);
				}
				if (INTERFACE_NAME != "" && INTERFACE_NAME != null
						&& INTERFACE_NAME.length() != 0) {
					ht.put("INTERFACE_NAME", INTERFACE_NAME);
				}
				if (SERVICE_NAME != "" && SERVICE_NAME != null
						&& SERVICE_NAME.length() != 0) {
					ht.put("SERVICE_NAME", SERVICE_NAME);
				}
				if (CONTENT_LOGGING != "" && CONTENT_LOGGING != null
						&& CONTENT_LOGGING.length() != 0) {
					ht.put("CONTENT_LOGGING", CONTENT_LOGGING);
				}
				if (ACTIVE_FLAG != "" && ACTIVE_FLAG != null
						&& ACTIVE_FLAG.length() != 0) {
					ht.put("ACTIVE_FLAG", ACTIVE_FLAG);
				}
				String selectSQL = "SELECT ID FROM MONITOR_CONFIG WHERE RULE_ID = '"
						+ RULE_ID + "'";
				String ID = DBUtil.select(selectSQL);
				if (!ID.equals("[]") && ID != null && ID.length() != 0) {
					ID = ID.replace("[{", "").replace("}]", "")
							.replace("\"ID\":", "");
					DBUtil.update(ht, "MONITOR_CONFIG", ID);
				} else {
					DBUtil.insert(ht, "MONITOR_CONFIG");
				}
			}
		} catch (Exception e) {
			logger.warn("Insert new Rule", e);
		}
	}

}
