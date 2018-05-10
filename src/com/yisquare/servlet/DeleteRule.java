package com.yisquare.servlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yisquare.tools.DBUtil;

public class DeleteRule extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DeleteRule.class);

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Hashtable<String, String> ht = new Hashtable<String, String>();// 用来保存Rul信息
		String RULE_ID = request.getParameter("RULE_ID");
		System.out.println(RULE_ID); 
		try {// 将 Rule 信息写入数据库
			if (RULE_ID != "" && RULE_ID != null && RULE_ID.length() != 0) {
				ht.put("RULE_ID", RULE_ID);
				DBUtil.delete(ht,"MONITOR_CONFIG");
				logger.warn("Delete Rule where RULE_ID = "+RULE_ID);
			}
		} catch (Exception e) {
			logger.warn("Delete Rule", e);
		}
	}

}
