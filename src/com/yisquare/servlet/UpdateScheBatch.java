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

public class UpdateScheBatch extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UpdateScheBatch.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
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
		Hashtable<String, String> ht = new Hashtable<String, String>();// 用来保存api数据
		String SCHE_ID = request.getParameter("SCHE_ID");
		String ACTIVE_FLAG = request.getParameter("ACTIVE_FLAG");
		logger.warn(SCHE_ID);
		logger.warn(ACTIVE_FLAG);
		try {
			if (SCHE_ID != null && "".equals(SCHE_ID) && SCHE_ID.length() != 0) {
				ht.put("SCHE_ID", SCHE_ID);
				if (ACTIVE_FLAG != null && "".equals(ACTIVE_FLAG) && ACTIVE_FLAG.length() != 0) {
					ht.put("ACTIVE_FLAG", ACTIVE_FLAG);
				}
				//DBUtil.update(ht, "SCHE_BATCH", ACTIVE_FLAG);
				String selectSQL = "SELECT ID FROM SCHE_BATCH WHERE SCHE_ID = '"
						+ SCHE_ID + "'";
				logger.warn(selectSQL);
				String ID = DBUtil.select(selectSQL);
				logger.warn(ID);
				if (!ID.equals("[]") && ID != null && ID.length() != 0) {
					ID = ID.replace("[{", "").replace("}]", "")
							.replace("\"ID\":", "");
					DBUtil.update(ht, "SCHE_BATCH", ID);
				}
			}else {
				logger.warn("Can't get SCHE_BATCH");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception:" + LogCreate.getException(e));
		}
	}

}
