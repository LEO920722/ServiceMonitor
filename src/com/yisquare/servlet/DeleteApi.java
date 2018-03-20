package com.yisquare.servlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yisquare.tools.DBUtil;

/**
 * Servlet implementation class QueryTypes
 */
public class DeleteApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DeleteApi.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result = "";
		try {
			String id=request.getParameter("ID");
			Hashtable<String, String>ht = new Hashtable<String, String>();
			if (id!=""&&id!=null&&id.length()!=0) {
				ht.put("ID", id);
				result = DBUtil.delete(ht, "APIPlatform_API");
			}
			else{
				result ="{\"errorcode\":\"0\",\"errmsg\":\"error\"}";
			}
		}catch(Exception e){
			logger.warn("delete the api faild", e);
		}
		response.getWriter().print("{\"rows\":"+result+"}");
	}

}
