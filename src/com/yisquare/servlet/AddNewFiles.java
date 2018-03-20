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

import oracle.net.aso.f;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.yisquare.tools.DBUtil;
import com.yisquare.tools.LogCreate;
import com.yisquare.tools.Util;
public class AddNewFiles extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(AddNewFiles.class);
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
		String timestamp1 = Calendar.getInstance().getTimeInMillis()+""+new Random().nextInt(100);
		String skipApiID = "";//在用户重新上传的文件的时候 需要跳转本页面，这个iD是api的id
		String uploadApiId = "";//这个id是用来唯一标识api的id，用来链接文件用的，值是注册api时候的时间戳
		Hashtable<String, String> htFile=new Hashtable<String, String>();//用来保存files数据
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
                System.out.println(remk);
                if("SKIP_API_ID".equals(fieldName)){
                	skipApiID = remk;
                }else{
                	if("API_ID".equals(fieldName)){
                		htFile.put("API_ID", remk);
                		uploadApiId = remk;
                	}else{
                		
                		htFile.put(fieldName, remk);
                	}
                }
            }
            // 信息为文件格式
            else {
                String fileName = item.getName();
                int index = fileName.lastIndexOf("\\");
                fileName = fileName.substring(index + 1);
              //注意在存储文件的时候，为了避免中文不能访问或者乱码，文件会被重新命名，命名方式为时间戳+文件后缀
                String targetFileName = timestamp1+"."+fileName.substring(fileName.lastIndexOf(".")+1);
                request.setAttribute("realFileName", fileName);
                // 将文件写入
//                String path = req.getContextPath();
//                String directory = "uploadFile";
//                String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path + "/" + directory;
                String basePath = this.getServletContext().getRealPath("/uploadFile");
//                String basePathLinux = "/opt/webMethods/webMethods912/profiles/MWS_default/configuration/org.eclipse.osgi/uploadFile";
//                String basePath = Util.isOSLinux()?basePathLinux:basePathWindows;
                File file1 = new File(basePath);
                // 创建文件夹
                if (!file1.exists()) {
                	file1.mkdirs();
                }
                File file = new File(basePath, targetFileName);
                try {
                	if(fileName.length()!=0){
                		item.write(file);
                		htFile.put("NAME", fileName);
                		htFile.put("URL", "/APIPlatform/DownLoadServlet?fileName="+targetFileName);
                		htFile.put("CREATE_TIME", Util.getNowFormat());
                		htFile.put("NOTE", targetFileName);
                		DBUtil.insert(htFile, "APIPlatform_FILE");
                		System.out.println(skipApiID+"=="+uploadApiId);
                		LogCreate.insertLog("Add new file <font color=\"#006F97\">"+fileName+"</font> successfully! API Id is "+skipApiID, "", Util.getNowFormat(), "SUCCESS", "CREATE FILE");
                		response.sendRedirect("pages/APIDetail.html?API_ID="+skipApiID+"&FILE_ID="+uploadApiId);//跳转到刚刚详情页面，并把刚刚的api_Id和file_id（timestamp）作为传参
                	}
                	else{
                		response.sendRedirect("pages/APIDetail.html?API_ID="+skipApiID+"&FILE_ID="+uploadApiId);//跳转到刚刚详情页面，并把刚刚的api_Id和file_id（timestamp）作为传参
                	}
                }
                catch (Exception e) {
                    e.printStackTrace();
                    logger.warn("add new File faild", e);
                    LogCreate.insertLog(e, "", Util.getNowFormat(), "WARNING", "CREATE FILE");
                    try {
						response.sendRedirect("pages/APIDetail.html?API_ID="+skipApiID+"&FILE_ID="+uploadApiId);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//跳转到刚刚详情页面，并把刚刚的api_Id和file_id（timestamp）作为传参
                }
            }
        }
    }
}