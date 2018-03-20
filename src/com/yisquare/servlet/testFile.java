package com.yisquare.servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class testFile extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        fileControl(req, resp);
    }

    /**
     * 上传文件的处理
     * @throws UnsupportedEncodingException 
     */
    private void fileControl(HttpServletRequest request, HttpServletResponse response) throws ServletException, UnsupportedEncodingException {
    	request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
        // 在解析请求之前先判断请求类型是否为文件上传类型
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        System.out.println("用户输入的文件名是："+isMultipart);
        // 文件上传处理工厂
        FileItemFactory factory = new DiskFileItemFactory();

        // 创建文件上传处理器
        ServletFileUpload upload = new ServletFileUpload(factory);

        // 开始解析请求信息
        List items = null;
        try {
            items = upload.parseRequest(request);
        }
        catch (FileUploadException e) {
            e.printStackTrace();
        }

        // 对所有请求信息进行判断
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            // 信息为普通的格式
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                String value = item.getString();
                String remk = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");
                System.out.println(fieldName+""+remk);
            }
            // 信息为文件格式
            else {
                String fileName = item.getName();
                int index = fileName.lastIndexOf("\\");
                fileName = fileName.substring(index + 1);
                request.setAttribute("realFileName", fileName);

                // 将文件写入
//                String path = req.getContextPath();
//                String directory = "uploadFile";
//                String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path + "/" + directory;
                String basePath = request.getSession().getServletContext().getRealPath("/uploadFile");
                File file = new File(basePath, fileName);
                file.setWritable(true,false);
                try {
                    item.write(file);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        try {
        	response.getWriter().print("上传成功");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}