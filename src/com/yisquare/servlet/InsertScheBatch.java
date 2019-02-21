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

public class InsertScheBatch extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(InsertScheBatch.class);

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		Hashtable<String, String> ht = new Hashtable<String, String>();// 用来保存api数据
		String MAIL_SUBJECT = request.getParameter("MAIL_SUBJECT");
		String ACTIVE_FLAG = request.getParameter("ACTIVE_FLAG");
		logger.warn(MAIL_SUBJECT);
		logger.warn(ACTIVE_FLAG);
		try {// 将 Rule 信息写入数据库
			ht.put("MAIL_SUBJECT", MAIL_SUBJECT);
			ht.put("ACTIVE_FLAG", ACTIVE_FLAG);
			DBUtil.insert(ht, "SCHE_BATCH");
		} catch (Exception e) {
			logger.error("Exception:" + LogCreate.getException(e));
		}
	}

}
