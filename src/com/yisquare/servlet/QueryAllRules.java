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

public class QueryAllRules extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QueryAllRules.class);

	public QueryAllRules() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			Hashtable<String, String> ht = new Hashtable<String, String>();
			String SCHE_ID = request.getParameter("SCHE_ID");
			String createTime1 = request.getParameter("CREATE_TIME1");
			String createTime2 = request.getParameter("CREATE_TIME2");
			if (SCHE_ID == null || SCHE_ID == "") {
				String sql = DBUtil.getQuerySql(ht, "MONITOR_CONFIG",
						createTime1, createTime2).replace("order by id desc",
						"order by RULE_ID asc");
				response.getWriter().print(
						"{\"rows\":" + DBUtil.select(sql) + "}");
			} else {
				ht.put("SCHE_ID", SCHE_ID);
				String rs = DBUtil.select(DBUtil.getQuerySql(ht,
						"SCHE_BATCH_FOR_RULE", createTime1, createTime2));

				response.getWriter().print("{\"rows\":" + rs + "}");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception:" + LogCreate.getException(e));
		}
	}

}
