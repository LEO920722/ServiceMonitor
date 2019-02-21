package com.yisquare.servlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yisquare.tools.DBUtil;
import com.yisquare.tools.LogCreate;

public class InsertScheForRouter extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(InsertScheForRule.class);

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Hashtable<String, String> ht = new Hashtable<String, String>();// 用来保存api数据
		String SCHE_ID = request.getParameter("SCHE_ID");
		String Email_ID = request.getParameter("Email_ID");
		String SendFlag = request.getParameter("Send_Flag");
		String CCFlag = request.getParameter("CC_Flag");
		logger.warn(SCHE_ID);
		logger.warn(Email_ID);
		logger.warn(SendFlag);
		logger.warn(CCFlag);
		try {// 将 Rule 信息写入数据库
			if (SendFlag != "" && SendFlag != null && SendFlag.length() != 0) {
				ht.put("Send", SendFlag);
			}
			if (CCFlag != "" && CCFlag != null && CCFlag.length() != 0) {
				ht.put("CC", CCFlag);
			}
			ht.put("SCHE_ID", SCHE_ID);
			ht.put("MAIL_ROUTER_ID", Email_ID);
			DBUtil.insert(ht, "SCHE_BATCH_FOR_ROUTER");
		} catch (Exception e) {
			logger.error("Exception:" + LogCreate.getException(e));
		}
	
	}
}
