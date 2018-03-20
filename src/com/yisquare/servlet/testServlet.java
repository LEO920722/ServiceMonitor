package com.yisquare.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.tomcat.util.http.fileupload.FileItem;

import com.yisquare.tools.DBUtil;

/**
 * Servlet implementation class test
 */
public class testServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public testServlet() {
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
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//判断表单是否二进制流提交数据
		  boolean isMultipart = ServletFileUpload.isMultipartContent(request);  
		        if(!isMultipart){  
		            throw new RuntimeException("请检查您的表单的enctype属性，确定是multipart/*");  
		        } 
		        DiskFileItemFactory dfif = new DiskFileItemFactory();  
		        ServletFileUpload parser = new ServletFileUpload(dfif);  

		        parser.setFileSizeMax(3*1024*1024);//设置单个文件上传的大小  
		        parser.setSizeMax(6*1024*1024);//多文件上传时总大小限制  
		        //文件根目录
		        String uploadPath = this.getServletContext().getRealPath("")  + File.separator;
                System.out.println(uploadPath);
		        //保存表单数据,以便在之后取出
		       Map<String,String> map = new HashMap<String, String>();

		        try {
		            List<?> items = parser.parseRequest(request);
		            Iterator iter = items.iterator();
		            if (items != null && items.size() > 0) {
		                // 迭代表单数据
		                while (iter.hasNext()) {  
		                    FileItem item = (FileItem) iter.next();  
		                    if (item.isFormField()) {  
		                        //如果是普通表单字段  
		                        String name = item.getFieldName();  //获得表单name字段名称
		                        String value = item.getString();  //获得表单name字段名称对应的值
		                        map.put(name, value);
//		                        log.info("name={},value={}",name,value);
		                    } else if (!item.isFormField()&&item.getName()!=null) {
		                        String fileName = new File(item.getName()).getName();
		                        if(fileName == null || "".equals(fileName)){
		                            continue;
		                        }
		                        String filePath = uploadPath  + fileName;
		                        map.put("filePath",filePath);
		                        File storeFile = new File(filePath);
		                        // 在控制台输出文件的上传路径
		                        System.out.println(filePath);
		                        // 如果文件不存在则保存到指定文件夹否则直接返回
		                        if(!storeFile.exists())
		                            item.write(storeFile);
		                        request.setAttribute("message",
		                            "文件上传成功!");
		                        continue;
		                    }
		                }
		            }
		        } catch (Exception e) {
		             request.setAttribute("message","错误信息: " + e.getMessage());
		        }
	}

}
