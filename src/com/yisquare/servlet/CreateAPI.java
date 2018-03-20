package com.yisquare.servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.yisquare.tools.DBUtil;
import com.yisquare.tools.LogCreate;
import com.yisquare.tools.Util;

public class CreateAPI extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CreateAPI.class);
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        fileControl(req, resp);
    }

    /**
     * 上传文件的处理
     * @throws UnsupportedEncodingException 
     */
    private void fileControl(HttpServletRequest request, HttpServletResponse response) throws ServletException, UnsupportedEncodingException {
    	Hashtable<String, String> ht=new Hashtable<String, String>();//用来保存api数据
		Hashtable<String, String> htFile=new Hashtable<String, String>();//用来保存files数据
		String timestamp = Calendar.getInstance().getTimeInMillis()+""+new Random().nextInt(1000);
		String timestamp1 = Calendar.getInstance().getTimeInMillis()+""+new Random().nextInt(100);
		String nowStr = Util.getNowFormat();
		String creator = "";
        // 在解析请求之前先判断请求类型是否为文件上传类型
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//        System.out.println("用户输入的文件名是："+isMultipart);
        FileItemFactory factory = new DiskFileItemFactory();// 文件上传处理工厂
        ServletFileUpload upload = new ServletFileUpload(factory);// 创建文件上传处理器
        // 开始解析请求信息
        List items = null;
        try {
            items = upload.parseRequest(request);
        }
        catch (FileUploadException e) {
            e.printStackTrace();
        }
        Iterator iter = items.iterator();// 对所有请求信息进行判断
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            // 字符串格式
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                String remk = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");
                System.out.println(fieldName+""+remk);
                if(!"textfield".equals(fieldName)){
                	ht.put(fieldName, remk);
                }
                if("CREATOR".equals(fieldName)){
                	creator = remk;
                }
            }
            // 文件格式
            else {
                String fileName = item.getName();
                int index = fileName.lastIndexOf("\\");
                fileName = fileName.substring(index + 1);
                //注意在存储文件的时候，为了避免中文不能访问或者乱码，文件会被重新命名，命名方式为时间戳+文件后缀
                String targetFileName = timestamp1+"."+fileName.substring(fileName.lastIndexOf(".")+1);
                request.setAttribute("realFileName", fileName);
                // 将文件写入
                String basePath = this.getServletContext().getRealPath("/uploadFile");
//                String basePathLinux = "/opt/webMethods/webMethods912/profiles/MWS_default/configuration/org.eclipse.osgi/uploadFile";
//                String basePath = Util.isOSLinux()?basePathLinux:basePathWindows;
                File file1 = new File(basePath);
                // 创建文件夹
                if (!file1.exists()) {
                	file1.mkdirs();
                }
                File file = new File(basePath, targetFileName);
                try {//将文件信息写入数据库
                	htFile.put("CREATOR", creator);
                	htFile.put("NAME", fileName);
                	htFile.put("CREATE_TIME", nowStr);
                	htFile.put("URL", "/APIPlatform/DownLoadServlet?fileName="+targetFileName);
                	htFile.put("API_ID", timestamp);
                	 htFile.put("NOTE", targetFileName);
                	if(fileName.length()!=0){
                		item.write(file);
                		DBUtil.insert(htFile,"APIPlatform_FILE");
                		LogCreate.insertLog("Add new file <font color=\"#006F97\">"+fileName+"</font> successfully!", creator, Util.getNowFormat(), "SUCCESS", "CREATE FILE");
                	}
                }
                catch (Exception e) {
                	logger.warn("upload file error:",e);
                }
            }
        }
        try {//将api信息写入数据库
        	ht.put("CREATE_TIME", "to_date('"+nowStr+"','yyyy-mm-dd hh24:mi:ss')");
        	ht.put("LAST_CHANGETIME", nowStr);
        	ht.put("FILE_ID", timestamp);
        	DBUtil.insert(ht,"APIPlatform_API");
        	String result = DBUtil.select("SELECT ID FROM APIPlatform_API WHERE FILE_ID = "+timestamp);//通过timestamp获取API ID
        	int apiId = Util.getIdNum(result);
        	LogCreate.insertLog("Create new API successfully! API Id is "+apiId, creator, nowStr, "SUCCESS", "CREATE API");
        	logger.warn("the insert api result is"+result);
        	response.sendRedirect("pages/APIDetail.html?API_ID="+apiId+"&FILE_ID="+timestamp);//跳转到刚刚详情页面，并把刚刚的api_Id和file_id（timestamp）作为传参
        }
        catch (Exception e) {
        	try {
				response.sendRedirect("pages/APIApply.html?state=0");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}//跳转到刚刚详情页面，并把刚刚的api_Id和file_id（timestamp）作为传参
            
        	LogCreate.insertLog(e, creator, nowStr, "WARNING", "CREATE API");
        	logger.warn("reigst new API faild",e);
        }
    }
}