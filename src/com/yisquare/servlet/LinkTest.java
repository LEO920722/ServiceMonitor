package com.yisquare.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yisquare.db.DBConf;
import com.yisquare.tools.PropertiesUtil;

/**
 * Servlet implementation class LinkTest
 * 作用：
 * 不传参数：查询db连接信息，0表示连接失败，1表示连接成功
 * 穿参数： 表示首次用来天连接数据信息
 */
public class LinkTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LinkTest.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LinkTest() {
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
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub 
		PrintWriter writer= response.getWriter();
		String ipStr=request.getParameter("IP");
		//System.out.println("传入进来的ipStr是："+ipStr);
		if (ipStr!=""&&ipStr!=null&&ipStr.length()!=0) {//当用户输入参数后 将访问该模块
			//首先测试连接是否正常
			try {
				String [] array = ipStr.split("-");
				new DBConf().getConnection(array[0], array[1], array[2], array[3], array[4],array[5]);
				PropertiesUtil.setDBInfo(ipStr);//注意这里ip是所有信息拼接起来的
				writer.print("2");//连接成功
			} catch (Exception e) {//连接数据库失败，前端表示为
				// TODO: handle exception
				e.printStackTrace();
				logger.warn("connect to the db faild");
				writer.print("1");//弹出框抖动
			}
		}
		else{//第一次进入 没有输入任何参数
			String string = null;
			try {
				string = PropertiesUtil.getDBInfo();
				System.out.println("_"+string+"_");
				if(string == null||string.length()==0){//没有查到本地信息
					writer.print("0");//弹出框
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.warn("write the dbinfo faild");
			}
		}
		
	}

}
