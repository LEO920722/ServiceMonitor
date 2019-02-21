package com.yisquare.servlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yisquare.tools.DBUtil;

public class DeleteScheRouter extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DeleteScheRouter.class);

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Hashtable<String, String> ht = new Hashtable<String, String>();// 用来保存Rul信息
		String ID = request.getParameter("ID");
		String SCHE_ID = request.getParameter("SCHE_ID");
		String RULE_ID = request.getParameter("RULE_ID");
		//System.out.println(RULE_ID); 
		try {// 将 Rule 信息写入数据库
				ht.put("ID", ID);
				DBUtil.delete(ht,"SCHE_BATCH_FOR_ROUTER");
				logger.warn("Delete Rule where RULE_ID = "+RULE_ID + "and SCHE_ID = " + SCHE_ID);
			
		} catch (Exception e) {
			logger.warn("Delete Rule", e);
		}
	
	}
}
