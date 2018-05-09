package com.yisquare.servlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yisquare.tools.DBUtil;

public class UpdateRule extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UpdateRule.class);

	public UpdateRule() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ruleId = request.getParameter("RULE_ID");
		String contentLogging = request.getParameter("CONTENT_LOGGING");
		String activeFlag = request.getParameter("ACTIVE_FLAG");

		if (ruleId != "" && ruleId != null && ruleId.length() != 0) {
			if (contentLogging != "" && contentLogging != null
					&& contentLogging.length() != 0) {
				String sql = "UPDATE MONITOR_CONFIG SET CONTENT_LOGGING = '"
						+ contentLogging + "'";
				DBUtil.update(sql);
			}
			if (activeFlag != "" && activeFlag != null
					&& activeFlag.length() != 0) {
				String sql = "UPDATE MONITOR_CONFIG SET CONTENT_LOGGING = '"
						+ activeFlag + "'";
				DBUtil.update(sql);
			}
		}

	}
}
