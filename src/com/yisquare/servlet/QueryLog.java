package com.yisquare.servlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yisquare.tools.DBUtil;
import com.yisquare.tools.Util;

/**
 * Servlet implementation class QueryApi
 */
public class QueryLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryLog() {
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
		Hashtable<String, String> ht=new Hashtable<String, String>();
		String id=request.getParameter("ID");
		String class1 =request.getParameter("CLASS");
		String lever=request.getParameter("LEVER");
		String info=request.getParameter("INFO");
		String createTime1=request.getParameter("TIME1");
		String createTime2=request.getParameter("TIME2");
		if (id!=""&&id!=null&&id.length()!=0) {
			ht.put("ID", id);
		}
		if (class1!=""&&class1!=null&&class1.length()!=0) {
			ht.put("CLASS", class1);
		}
		if (lever!=""&&lever!=null&&lever.length()!=0) {
			ht.put("LEVER", lever);
		}
		if (info!=""&&info!=null&&info.length()!=0) {
			ht.put("INFO", info);
		}
		System.out.println(createTime1+createTime2);
		response.getWriter().print("{\"rows\":"+DBUtil.select(DBUtil.getQuerySql(ht, "APIPlatform_LOG", createTime1, createTime2))+"}");
		  
	}

}
