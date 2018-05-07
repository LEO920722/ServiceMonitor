package com.yisquare.servlet;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;  
import java.net.URLEncoder;  
  







import javax.servlet.ServletException;  
import javax.servlet.ServletOutputStream;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.swing.text.ChangedCharSetException;

import com.yisquare.tools.DBUtil;
import com.yisquare.tools.Util;

public class DownLoadServlet extends HttpServlet {  
  
      
    public DownLoadServlet() {  
        super();  
    }  
  
  
    public void destroy() {  
        super.destroy(); // Just puts "destroy" string in log  
        // Put your code here  
    }  
  
  
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        doPost(request,response);  
    }  
    public static String getFileName(String timeStr){
    	
    	
		return null;
    }
      
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
    	request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
        //处理请求  
        //读取要下载的文件  
    	String fileName_ = request.getParameter("fileName");
//    	String dbCountJson=DBUtil.select("select NAME from FILES where NOTE = '" + fileName_+"'");
//		String realName= Util.Regex(".*:\"(.*)\"}]", dbCountJson, 1);
    	String basePath = this.getServletContext().getRealPath("/uploadFile");
        File f = new File(basePath+"/"+fileName_);  
        if(f.exists()){  
            FileInputStream  fis = new FileInputStream(f);  
            String filename=URLEncoder.encode(f.getName(),"utf-8"); //解决中文文件名下载后乱码的问题  
            byte[] b = new byte[fis.available()];  
            fis.read(b);  
            response.setCharacterEncoding("utf-8");  
            response.setHeader("Content-Disposition","attachment; filename="+filename+"");  
            //获取响应报文输出流对象  
            ServletOutputStream  out =response.getOutputStream();  
            //输出  
            out.write(b);  
            out.flush();  
            out.close();  
        }     
          
    }  
  
    /** 
     * Initialization of the servlet. <br> 
     * 
     * @throws ServletException if an error occurs 
     */  
    public void init() throws ServletException {  
        // Put your code here  
    }  
    
}