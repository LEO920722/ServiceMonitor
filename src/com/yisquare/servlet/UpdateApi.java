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
 * Servlet implementation class UpdateApi
 */
public class UpdateApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UpdateApi.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String apiId ="";
		try {
			Hashtable<String, String> ht=new Hashtable<String, String>();
			apiId=request.getParameter("ID");
			System.out.println(apiId);
			String name=request.getParameter("NAME");
			String keayword=request.getParameter("KEYWORD");
			String creator=request.getParameter("CREATOR");
			String bongSystem=request.getParameter("BELONG_SYSTEM");
			String projectName=request.getParameter("PROJECT_NAME");
			String leader=request.getParameter("LEADER");
			String fileId=request.getParameter("FILE_ID");
			String message=request.getParameter("MESSAGE");
			String type_Id=request.getParameter("TYPE_ID");
			if (apiId!=""&&apiId!=null&&apiId.length()!=0) {
				ht.put("ID", apiId);
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
			if (message!=""&&message!=null&&message.length()!=0) {
				ht.put("MESSAGE", message);
			}
			if (type_Id!=""&&type_Id!=null&&type_Id.length()!=0) {
				ht.put("TYPE_ID", type_Id);
			}
			ht.put("LAST_CHANGETIME", Util.getNowFormat());
			System.out.println(ht.toString());
			String  result = DBUtil.update(ht,"APIPlatform_API","ID");
			response.getWriter().print("{\"rows\":"+result+"}");
			
		} catch (Exception e) {
			logger.warn("Update the api faild",e);
			LogCreate.insertLog("Update new file successfully! API Id is "+apiId, "", Util.getNowFormat(), "<font color=\"#DAA520\">WARNING</font>", "UPDATE");
		}
	}

}
