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

public class QueryScheDetailForRouter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QueryScheDetailForRouter.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			Hashtable<String, String> ht = new Hashtable<String, String>();
			String createTime1 = request.getParameter("CREATE_TIME1");
			String createTime2 = request.getParameter("CREATE_TIME2");
			String SCHE_ID = request.getParameter("SCHE_ID");
			ht.put("SCHE_ID", SCHE_ID);
			 String rs = DBUtil.select(DBUtil.getQuerySql(ht,
					"SCHE_BATCH_FOR_ROUTER", createTime1, createTime2));
			 logger.warn(rs);
			response.getWriter().print("{\"rows\":" + rs + "}");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception:" + LogCreate.getException(e));
		
		}
	}
}
