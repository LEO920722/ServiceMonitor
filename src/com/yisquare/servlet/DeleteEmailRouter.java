package com.yisquare.servlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yisquare.tools.DBUtil;

public class DeleteEmailRouter extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DeleteEmailRouter.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Hashtable<String, String> ht = new Hashtable<String, String>();// 用来保存Rul信息
		String ID = request.getParameter("ID");
		logger.warn(ID);
		//System.out.println(RULE_ID); 
		try {// 将 Rule 信息写入数据库
			if (ID != "" && ID != null && ID.length() != 0) {
				ht.put("ID", ID);
				DBUtil.delete(ht,"MAIL_ROUTER");
				logger.warn("Delete MAIL_ROUTER where ID = "+ ID);
			}
		} catch (Exception e) {
			logger.warn("Delete Rule", e);
		}
	}

}
