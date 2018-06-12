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

public class InsertEmailRouter extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(InsertEmailRouter.class);
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		Hashtable<String, String> ht = new Hashtable<String, String>();// 用来保存api数据
		String NAME = request.getParameter("NAME");
		String ADDRESS = request.getParameter("ADDRESS");
		try {// 将 Rule 信息写入数据库
			ht.put("NAME", NAME);
			ht.put("ADDRESS", ADDRESS);
			DBUtil.insert(ht, "MAIL_ROUTER");
		} catch (Exception e) {
			logger.error("Exception:" + LogCreate.getException(e));
		}
	}

}
