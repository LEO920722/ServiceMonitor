package com.yisquare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yisquare.tools.DBUtil;
import com.yisquare.tools.LogCreate;

public class InsertScheForRule extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(InsertScheForRule.class);

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Hashtable<String, String> ht = new Hashtable<String, String>();// 用来保存api数据
		String SCHE_ID = request.getParameter("SCHE_ID");
		String RULE_ID = request.getParameter("RULE_ID");
		logger.warn(SCHE_ID);
		logger.warn(RULE_ID);
		try {// 将 Rule 信息写入数据库
			ht.put("SCHE_ID", SCHE_ID);
			ht.put("RULE_ID", RULE_ID);
			DBUtil.insert(ht, "SCHE_BATCH_FOR_RULE");
		} catch (Exception e) {
			logger.error("Exception:" + LogCreate.getException(e));
		}
	
	}
}
