package com.yisquare.servlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yisquare.tools.DBUtil;

public class DeleteScheBatch extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DeleteScheBatch.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Hashtable<String, String> ht = new Hashtable<String, String>();// 用来保存url信息
		String SCHE_ID = request.getParameter("SCHE_ID");
		logger.warn(SCHE_ID);
		try {
			if (SCHE_ID != "" && SCHE_ID != null && SCHE_ID.length() != 0) {
				ht.put("SCHE_ID", SCHE_ID);
				DBUtil.delete(ht,"SCHE_BATCH");
				logger.warn("Delete SCHE_BATCH where SCHE_ID = "+ SCHE_ID );
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn("Delete Rule", e);
		}
	}
	
}
