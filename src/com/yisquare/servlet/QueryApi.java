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
import com.yisquare.tools.Util;

/**
 * Servlet implementation class QueryApi
 */
public class QueryApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(QueryApi.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryApi() {
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
		try{
			Hashtable<String, String> ht=new Hashtable<String, String>();
			String id=request.getParameter("ID");
			String name=request.getParameter("NAME");
			String keayword=request.getParameter("KEYWORD");
			String creator=request.getParameter("CREATOR");
		    String bongSystem=request.getParameter("BELONG_SYSTEM");
		    String projectName=request.getParameter("PROJECT_NAME");
		    String leader=request.getParameter("LEADER");
		    String fileId=request.getParameter("FILE_ID");
		    String type = request.getParameter("TYPE_ID");
		    String createTime1=request.getParameter("CREATE_TIME1");
		    String createTime2=request.getParameter("CREATE_TIME2");
		    String lastChangeTime=request.getParameter("LAST_CHANGETIME");
		    String message=request.getParameter("MESSAGE");
		    
			if (id!=""&&id!=null&&id.length()!=0) {
				ht.put("ID", id);
			}
			if (name!=""&&name!=null&&name.length()!=0) {
				ht.put("NAME", name);
			}
			if (keayword!=""&&keayword!=null&&keayword.length()!=0) {
				ht.put("KEYWORD", keayword);
			}
			if (creator!=""&&creator!=null&&creator.length()!=0) {
				ht.put("CREATOR", creator);
			}
			if (bongSystem!=""&&bongSystem!=null&&bongSystem.length()!=0) {
				ht.put("BELONG_SYSTEM", bongSystem);
			}
			if (projectName!=""&&projectName!=null&&projectName.length()!=0) {
				ht.put("PROJECT_NAME", projectName);
			}
			if (leader!=""&&leader!=null&&leader.length()!=0) {
				ht.put("LEADER", leader);
			}
			if (fileId!=""&&fileId!=null&&fileId.length()!=0) {
				ht.put("FILE_ID", fileId);
			}
			
			if (lastChangeTime!=""&&lastChangeTime!=null&&lastChangeTime.length()!=0) {
				ht.put("LAST_CHANGETIME", lastChangeTime);
			}
			if (message!=""&&message!=null&&message.length()!=0) {
				ht.put("MESSAGE", message);
			}
			if (type!=""&&type!=null&&type.length()!=0) {
				ht.put("TYPE_ID", type);
			}
			response.getWriter().print("{\"rows\":"+DBUtil.select(DBUtil.getQuerySql(ht, "APIPlatform_API", createTime1, createTime2))+"}");
		   } catch (Exception e) {
			   e.printStackTrace();
		   logger.error("Exception:"+LogCreate.getException(e));
		   }
	}

}
