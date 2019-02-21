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
 * Servlet implementation class QueryApi
 */
public class QueryFiles extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QueryFiles.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryFiles() {
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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			
			// TODO: handle exception
		Hashtable<String, String> ht=new Hashtable<String, String>();
		String id=request.getParameter("ID");
		String  result = DBUtil.select("select * from APIPlatform_FILE where api_id = "+id);
		response.getWriter().print("{\"rows\":"+result+"}");
		} catch (Exception e) {
			logger.warn("query all the api files faild", e);
		}
	}

}
