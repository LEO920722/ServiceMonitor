package com.yisquare.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yisquare.tools.DBUtil;
import com.yisquare.tools.LogCreate;

public class QueryServiceTransaction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(QueryServiceTransaction.class);
	static String path = Log4jListener.path;

	public QueryServiceTransaction() {
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
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Hashtable<String, String> ht = new Hashtable<String, String>();
			String selectType = request.getParameter("SELECT_TYPE");
			String businessId = request.getParameter("BUSINESS_ID");
			String ruleId = request.getParameter("RULE_ID");
			String businessStatus = request.getParameter("BUSINESS_STATUS");
			String businessType = request.getParameter("BUSINESS_TYPE");
			String referenceId = request.getParameter("REFERENCE_ID");
			String createTime1 = request.getParameter("CREATE_TIME1");
			String createTimestamp1 = request.getParameter("CREATE_TIMESTAMP1")
					.replace('=', ':');
			String createTime2 = request.getParameter("CREATE_TIME2");
			String createTimestamp2 = request.getParameter("CREATE_TIMESTAMP2")
					.replace('=', ':');

			if (businessId != "" && businessId != null
					&& businessId.length() != 0) {
				ht.put("BUSINESS_ID", businessId);
				if (selectType != "" && selectType != null
						&& selectType.length() != 0 && !selectType.equals("=")) {
					ht.put("SELECT_TYPE", selectType);
				}
			}
			if (ruleId != "" && ruleId != null && ruleId.length() != 0) {
				ht.put("RULE_ID", ruleId);
			}
			if (ruleId != "" && ruleId != null && ruleId.length() != 0) {
				ht.put("RULE_ID", ruleId);
			}
			if (businessStatus != "" && businessStatus != null
					&& businessStatus.length() != 0) {
				if (businessStatus.equals("Error")) {
					ht.put("BUSINESS_STATUS", "E");
				} else if (businessStatus.equals("Success")) {
					ht.put("BUSINESS_STATUS", "S");
				}
			}
			if (businessType != "" && businessType != null
					&& businessType.length() != 0) {
				ht.put("BUSINESS_TYPE", businessType);
			}
			if (referenceId != "" && referenceId != null
					&& referenceId.length() != 0) {
				ht.put("REFERENCE_ID", referenceId);
			}

			// Add function to select data in time field -- 20180611
			// Change condition from "==" into ".eqauls()"
			if (createTime1 == null || createTime1.equals("")) {
				if (createTime2 == null || createTime2.equals("")) {
					createTime1 = sdf.format(d) + " " + createTimestamp1
							+ ":00";
					createTime2 = sdf.format(d) + " " + createTimestamp2
							+ ":59";
				} else {
					createTime1 = null;
					createTime2 += " " + createTimestamp2 + ":59";
				}
			} else {
				if (createTime2 == null || createTime2.equals("")) {
					createTime1 += " " + createTimestamp1 + ":00";
					createTime2 = null;
				} else {
					createTime1 += " " + createTimestamp1 + ":00";
					createTime2 += " " + createTimestamp2 + ":59";
				}

			}

			// Add function to change selected data order by MODIFIED_TIMESTAMP -- 20180611
			response.getWriter().print(
					"{\"rows\":"
							+ DBUtil.select(DBUtil.getQuerySql(ht,
									"TRANSACTION_MONITOR", createTime1,
									createTime2).replace("10000",
									"10000 order by MODIFIED_TIMESTAMP desc"))
							+ "}");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception:" + LogCreate.getException(e));
		}
	}
}
