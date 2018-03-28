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
	 * 上传文件的处理
	 * 
	 * @throws UnsupportedEncodingException
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		Hashtable<String, String> ht = new Hashtable<String, String>();// 用来保存api数据
		String RULE_ID = request.getParameter("RULE_ID");
		String RULE_NAME = request.getParameter("RULE_NAME");
		String INTERFACE_NAME = request.getParameter("INTERFACE_NAME");
		String SERVICE_NAME = request.getParameter("SERVICE_NAME");
		String CONTENT_LOGGING = request.getParameter("CONTENT_LOGGING");
		String ACTIVE_FLAG = request.getParameter("ACTIVE_FLAG");
		try {// 将 Rule 信息写入数据库
			ht.put("RULE_ID", RULE_ID);
			ht.put("RULE_NAME", RULE_NAME);
			ht.put("INTERFACE_NAME", INTERFACE_NAME);
			ht.put("SERVICE_NAME", SERVICE_NAME);
			ht.put("CONTENT_LOGGING", CONTENT_LOGGING);
			ht.put("ACTIVE_FLAG", ACTIVE_FLAG);
			DBUtil.insert(ht, "MONITOR_CONFIG");
		} catch (Exception e) {
			logger.warn("Insert new Rule", e);
		}
	}

}
