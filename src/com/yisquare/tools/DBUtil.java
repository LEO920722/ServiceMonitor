package com.yisquare.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;
import org.stringtree.json.JSONValidatingWriter;

import com.google.gson.Gson;
import com.yisquare.db.DBConf;

public class DBUtil {

	private static Statement stat = null;
	private static String sql = null;
	private static Hashtable<String, String> list = new Hashtable<String, String>();
	private static Gson gson = new Gson();
	private static Logger logger = Logger.getLogger(DBUtil.class);

	/**
	 * @param sql
	 *            鏌ヨsql璇彞
	 * @return json鏍煎紡鐨勭粨鏋滈泦
	 */
	public static String select(String sql) {
		Connection con = new DBConf().getConn();
		String json;
		try {
			json = new JSONValidatingWriter().write(new QueryRunner().query(
					con, sql, new MapListHandler()));
			con.close();
			System.out.println("鏌ヨ鐨勮鍙ユ槸锛�" + sql + ";鏈鏌ヨ鐨勭粨鏋滄槸锛�" + json);
			return json;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param sql
	 *            鏌ヨsql璇彞
	 * @return json鏍煎紡鐨勭粨鏋滈泦
	 */
	public static String updateorInsert(String sql) {
		Connection con = new DBConf().getConn();
		String json;
		try {
			json = new JSONValidatingWriter().write(new QueryRunner().update(
					con, sql, new MapListHandler()));
			con.close();
			return json;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String insert(Hashtable<?, ?> ht, String tablename)
			throws Exception {
		Connection con = new DBConf().getConn();
		String before_sql, after_sql;
		before_sql = "insert into " + tablename + "(";
		after_sql = " values('";
		int i = 1;
		for (Iterator<?> iter = ht.keySet().iterator(); iter.hasNext(); i = 0) {
			String key = (String) iter.next();
			Object value = ht.get(key);
			if (i != 1) {
				before_sql += ",";
				after_sql += "','";
			}
			before_sql += key;
			after_sql += value;
		}
		sql = before_sql + ")" + after_sql + "')";
		logger.warn("Insert sql is--->>" + sql);
		// 濡傛灉鏄oracle 鏁版嵁搴撹繘琛屾彃鍏ユ椂闂存搷浣滈渶瑕佷笅闈�2姝�
		sql = sql.replaceAll("'to_date", "to_date");
		sql = sql.replaceAll("'yyyy-mm-dd hh24:mi:ss'\\)'",
				"'yyyy-mm-dd hh24:mi:ss'\\)");
		System.out.println(sql);
		try {
			stat = con.createStatement();// 濮濄倖鏌熷▔鏇炲灡瀵よ櫣鏁ゆ禍搴㈠⒔鐞涘矂娼ら幀涓糛L鐠囶厼褰為獮鎯扮箲閸ョ偛鐣犻幍锟芥晸閹存劗绮ㄩ弸婊呮畱鐎电锟�?
			list.put("errorcode", stat.executeUpdate(sql) + "");// 閹垫挸宓冪紒鎾寸亯
			list.put("errmsg", "ok");
			con.close();
			return gson.toJson(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			/*
			 * list.put("errorcode", "-1"); list.put("errmsg", e.toString());
			 * 
			 * e.printStackTrace(); return gson.toJson(list);
			 */

			throw e;
		}

	}

	public static String select(Hashtable ht, String tablename) {
		Connection con = new DBConf().getConn();
		String sql;
		sql = "select * from " + tablename;
		int i = 1;
		for (Iterator<?> iter = ht.keySet().iterator(); iter.hasNext(); i = 0) {
			String key = (String) iter.next();
			Object value = ht.get(key);
			if (i == 1) {
				sql += " where ";
			} else {
				sql += " and ";
			}
			sql += key + "='" + value + "'";
		}
		// sql += " order by id desc";
		try {
			System.out.println("鏌ヨ鐨勮鍙ユ槸锛�" + sql);
			logger.warn("Query sql is--->>" + sql);//
			String json = new JSONValidatingWriter().write(new QueryRunner()
					.query(con, sql, new MapListHandler()));
			logger.warn("Query sql is--->>" + json);//
			con.close();
			return json;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author
	 * @param sql
	 *            sql璇彞
	 * @return list缁撴灉锟�?
	 * @throws SQLException
	 */
	public static List<Object[]> selectList(String sql) {
		try {
			return new QueryRunner().query(new DBConf().getConn(), sql,
					new ArrayListHandler());// 鎶婄粨鏋滈泦涓殑姣忎竴琛屾暟鎹兘杞垚锟�?锟斤拷鏁扮粍锛屽啀瀛樻斁鍒癓ist锟�?
		} catch (SQLException e) {
			return null;
		}
	}

	public static String update(Hashtable<String, String> ht, String tablename,
			String id) {
		Connection con = new DBConf().getConn();
		String before_sql, after_sql, sql, midle_sql;
		before_sql = "update " + tablename + " set ";
		after_sql = " where ";
		midle_sql = "";
		for (Iterator<String> iter = ht.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Object value = ht.get(key);

			if (key.equals(id)) {
				after_sql += key + "='" + value + "'";
			} else {
				midle_sql += key + "='" + value + "' , ";
			}
		}
		midle_sql = midle_sql.substring(0, midle_sql.length() - 3);
		sql = before_sql + midle_sql + after_sql;
		logger.warn("Update sql is--->>" + sql);//
		System.out.println("Update sql is--->>" + sql);
		try {
			stat = con.createStatement();
			list.put("errorcode", stat.executeUpdate(sql) + "");
			list.put("errmsg", "ok");
			con.close();
			return gson.toJson(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			list.put("errorcode", "-1");
			list.put("errmsg", e.toString());
			return gson.toJson(list);
		}

	}

	/**
	 * 鍗曠嫭鐨剈pdate璇彞
	 * 
	 * @param sql
	 * @return
	 */
	public static String update(String sql) {
		try {

			stat = new DBConf().getConn().createStatement();
			list.put("errorcode", stat.executeUpdate(sql) + "");
			list.put("errmsg", "ok");
			return gson.toJson(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			list.put("errorcode", "-1");
			list.put("errmsg", e.toString());
			return gson.toJson(list);
		}

	}

	public static String delete(Hashtable<?, ?> ht, String tablename) {
		String sql;
		sql = "delete from " + tablename + " where ";
		int i = 1;
		for (Iterator<?> iter = ht.keySet().iterator(); iter.hasNext(); i = 0) {
			String key = (String) iter.next();
			Object value = ht.get(key);

			if (i != 1) {
				sql += " and ";
			}
			sql += key + "='" + value + "'";
		}
		System.out.println("delete sql:" + sql);

		try {
			stat = new DBConf().getConn().createStatement();
			list.put("errorcode", stat.executeUpdate(sql) + "");
			list.put("errmsg", "ok");
			return gson.toJson(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			list.put("errorcode", "-1");
			list.put("errmsg", e.toString());
			return gson.toJson(list);
		}
	}

	/**
	 * 鏍规嵁浼犺繘鏉ョ殑ht鏉ヨ嚜鍔ㄧ敓鎴恠ql璇彞
	 * 
	 * @param ht
	 * @param tablename
	 * @return
	 */
	public static String getSqlContent(Hashtable ht, String tablename) {
		String sql;
		sql = "select * from " + tablename;
		int i = 1;
		for (Iterator<?> iter = ht.keySet().iterator(); iter.hasNext(); i = 0) {
			String key = (String) iter.next();
			Object value = ht.get(key);
			if (i == 1) {
				sql += " where ";
			} else {
				sql += " and ";
			}
			sql += key + "='" + value + "'";
		}
		// System.out.println(sql);
		return sql;

	}

	public static String selectByMyFuc(String sql) {
		Connection con = new DBConf().getConn();// 鍒涘缓涓�涓暟鎹簱杩炴帴
		PreparedStatement pre = null;// 鍒涘缓棰勭紪璇戣鍙ュ璞★紝涓�鑸兘鏄敤杩欎釜鑰屼笉鐢⊿tatement
		ResultSet result = null;// 鍒涘缓涓�涓粨鏋滈泦瀵硅薄
		try {

			pre = con.prepareStatement(sql);// 瀹炰緥鍖栭缂栬瘧璇彞
			result = pre.executeQuery();// 鎵ц鏌ヨ锛屾敞鎰忔嫭鍙蜂腑涓嶉渶瑕佸啀鍔犲弬鏁�
			ResultSetMetaData metaData = result.getMetaData();
			int columnCount = metaData.getColumnCount();
			String str = "";
			while (result.next()) {
				// 褰撶粨鏋滈泦涓嶄负绌烘椂
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnLabel(i);
					String value = result.getString(columnName);
					str += "'" + columnName + "'" + "_'" + value + "'-";
				}
			}
			str = str.substring(0, str.length() - 1);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 閫愪竴灏嗕笂闈㈢殑鍑犱釜瀵硅薄鍏抽棴锛屽洜涓轰笉鍏抽棴鐨勮瘽浼氬奖鍝嶆�ц兘銆佸苟涓斿崰鐢ㄨ祫婧�
				// 娉ㄦ剰鍏抽棴鐨勯『搴忥紝鏈�鍚庝娇鐢ㄧ殑鏈�鍏堝叧闂�
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				System.out.println("鏁版嵁搴撹繛鎺ュ凡鍏抽棴锛�");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/***
	 * 杩欐槸鏍规嵁瀹為檯鐨勯渶姹傝璁″嚭鏉ョ殑鏌ヨ锛岀敱浜庡湪oracle涓煡鍑烘潵鐨勬椂闂存槸object
	 * 鍒╃敤鎴戜滑鑷繁鐨凞BUil闅句互瑙ｆ瀽锛�
	 * 
	 * @param ht
	 * @param tablename
	 * @param create_time1
	 * @param create_time2
	 * @return
	 */
	public static String getQuerySql(Hashtable<String, String> ht,
			String tablename, String create_time1, String create_time2) {
		String sql = DBUtil.getSqlContent(ht, tablename);// 鑾峰彇鍒濇鐨剆ql
		String columnStr = "";
		String timeFieldName = "";// 涓嶅悓鐨勮〃涓椂闂寸殑瀛楁鍚嶄笉涓�鏍� 鎵�浠ヨ璁剧疆
		// 涓轰簡灏哾ate绫诲瀷瑙ｆ瀽鍑烘潵锛屽皢*鍙锋崲鎴� 瀛楁銆�
		switch (tablename) {
		case ("APIPlatform_API"): {
			columnStr = "ID,NAME,TYPE_ID,KEYWORD,CREATOR,BELONG_SYSTEM,PROJECT_NAME,LEADER,FILE_ID,to_char(CREATE_TIME,'yyyy-MM-dd HH24:mi:ss') as CREATE_TIME,LAST_CHANGETIME,MESSAGE";
			timeFieldName = "CREATE_TIME";
			break;
		}
		case ("SERVICE_MONITOR"): {
			columnStr = "ID,BUSINESS_ID,RULE_ID,SERVICENAME,SERVICE_STATUS,ERRORMESSAGE,to_char(MODIFIED_TIMESTAMP,'yyyy-MM-dd HH24:mi:ss') as LAST_CHANGETIME";
			timeFieldName = "MODIFIED_TIMESTAMP";
			break;
		}
		case ("EMAIL_BATCH"): {
			columnStr = "SERVICE_ID";
			timeFieldName = "CREATE_TIMESTAMP";
			break;
		}
		case ("SCHE_BATCH"): {
			columnStr = "SCHE_ID,MAIL_SUBJECT,ACTIVE_FLAG,to_char(LAST_RUN_TIMESTAMP,'yyyy-mm-dd hh24:mi:ss') AS LAST_RUN_TIMESTAMP";
			timeFieldName = "LAST_RUN_TIMESTAMP";
			break;
		}
		case ("MONITOR_CONFIG"): {
			columnStr = "RULE_ID,RULE_NAME,INTERFACE_NAME,SERVICE_NAME,CONTENT_LOGGING,ACTIVE_FLAG";
			timeFieldName = "CREATE_TIMESTAMP";
			break;
		}
		default: {
			columnStr = "ID,to_char(TIME,'yyyy-MM-dd HH24:mi:ss') as TIME,INFO,LEVER,CLASS";
			timeFieldName = "TIME";
			break;
		}
		}
		sql = sql.replaceAll("\\*", columnStr);
		// 鍙鐢ㄦ埛閫夋嫨浜嗕竴涓棩鏈燂紙鏃犺寮�濮嬭繕鏄粨鏉熸椂闂达級 渚垮紑濮嬫墽琛�
		if ((create_time1 != null && create_time1.length() != 0)
				|| create_time2 != null && create_time2.length() != 0) {
			if (create_time1 == "" || create_time1 == null
					|| create_time1.length() == 0) {// 寮�濮嬫椂闂翠负绌哄氨璁剧疆寮�濮嬫椂闂翠负寰堟棭鐨勬椂闂�
				create_time1 = "2018-01-01 00:00:00";
			}
			if (create_time2 == "" || create_time2 == null
					|| create_time2.length() == 0) {// 缁撴潫鏃堕棿涓虹┖灏辫缃负褰撳墠鐨勬椂闂�
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 璁剧疆鏃ユ湡鏍煎紡
				create_time2 = df.format(new Date());
			}
			if (!sql.contains("where")) {// 濡傛灉sql璇彞涓笉鍖呭惈where瀛楁锛堝嵆娌℃湁鍏朵粬鐨勫垽鏂潯浠讹級灏卞姞涓妛here
				sql += " where " + timeFieldName + " BETWEEN TO_DATE('"
						+ create_time1
						+ "', 'YYYY-MM-DD HH24:MI:SS') AND TO_DATE('"
						+ create_time2 + "', 'YYYY-MM-DD HH24:MI:SS')";
			} else {// 鍚﹀垯鍔犱笂and
				sql += " and " + timeFieldName + " BETWEEN TO_DATE('"
						+ create_time1
						+ "', 'YYYY-MM-DD HH24:MI:SS') AND TO_DATE('"
						+ create_time2 + "', 'YYYY-MM-DD HH24:MI:SS')";
			}
		} else {// 鐢ㄦ埛娌℃湁閫夋嫨鏃ユ湡灏� 榛樿鏌ヨ浠婂ぉ鐨�
			if (!sql.contains("where")) {// 濡傛灉sql璇彞涓笉鍖呭惈where瀛楁锛堝嵆娌℃湁鍏朵粬鐨勫垽鏂潯浠讹級灏卞姞涓妛here
				sql += " where to_char(" + timeFieldName
						+ ",'yyyy-mm-dd')<to_char(sysdate,'yyyy-mm-dd')";
			}
		}
		sql += " order by id desc";
		return sql;
	}

	public static void main(String[] args) throws Exception {
		// Hashtable<String, String> ht = new Hashtable<String, String>();
		// String str=select(ht,"BillInfo");
		// str="\"rows\":"+str;
		// System.out.println(str);
		// ht.put("g_id", "1");select(ht, "customer").equals("[]")
		// ht.put("c_name", "寰愯吘");
		// ht.put("c_pwd", "xuteng");
		// ht.remove("c_pwd");
		// System.out.println(select(ht, "goods"));
		// ht.put("openid", "oYJktwA8lh6a7fAknSbJfpwtL0cs");
		// delete(ht,"userinfo");
		/*
		 * List<UserInfo> ps = select(ht, UserInfo[].class); for (int i = 0; i <
		 * ps.size(); i++) { UserInfo p = ps.get(i);
		 * System.out.println("userinfo:" + p.getOpenid() + p.getCity() +
		 * p.getCountry() + p.getGroupid() + p.getHeadimgurl() + p.getLanguage()
		 * + p.getNickname() + p.getProvince() + p.getRemark() + p.getSex() +
		 * p.getSubscribe_time()+p.getPassword());
		 * 
		 * }
		 */
		// //ht.put("Record_Id", "1");
		// // String jsonStr=DBUtil.select("select max(Record_Id) from record");
		// // JSONArray jsonArray=new JSONArray(jsonStr);
		// //
		// System.out.println(jsonArray.getJSONObject(0).get("max(Record_Id)"));
		// //
		// // System.out.println(jsonStr);
		// // JSONArray jsonArray=new JSONArray(jsonStr);
		// // System.out.println(jsonArray.getJSONObject(0).get("Record_Id"));
		// //
		// ht.put("User_Type","瀹堕暱");
		// ht.put("User_Name", "鍒濅腑涓夌彮");
		// ht.put("User_Class","娌欑煶涓変腑");
		// ht.put("User_Tel", "15629899999");
		// for(int i=0;i<100;i++){
		// try {
		// // ht.put("Record_Id", Util.getMaxId()+1+"");
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// DBUtil.insert(ht, "record");//灏嗘暟鎹瓨鍏ユ暟鎹簱
		// }
		//
		// String jsonStr=DBUtil.select(ht, "record");
		// System.out.println(jsonStr);
		// JSONArray jsonArray=new JSONArray(jsonStr);
		// jsonArray.getJSONObject(0).get("Record_Id");
		// System.out.println(DBUtil.select("select * from api where id = "+"'1'"));
		// Hashtable<String, String> ht = new Hashtable<String, String>();

		// 鏌ヨ鐨刼racle鐨勬棩鏈熻繘琛屾牸寮忓寲
		// Hashtable<String, String> ht = new Hashtable<String, String>();
		// String
		// str=select("select to_char(end,'yyyy-MM-dd HH24:mi:ss')as time from some ");
		// System.out.println(str);

		// 灏嗘棩鏈熷鐞嗗悗鎻掑叆Oracle鐨勬暟鎹�
		// 浜叉祴鍙敤鐨剆ql锛歩nsert into some(BEGIN,NAME,END)
		// values(to_date('2018-02-09 04:22:42','yyyy-mm-dd
		// hh24:mi:ss'),'Bill',to_date('2018-02-09 04:22:42','yyyy-mm-dd
		// hh24:mi:ss'))

		// SimpleDateFormat df = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//璁剧疆鏃ユ湡鏍煎紡
		// Hashtable<String, String> ht1 = new Hashtable<String, String>();
		// ht1.put("NAME", "Bill1");
		// ht1.put("BEGIN", "to_date('"+df.format(new
		// Date())+"','yyyy-mm-dd hh24:mi:ss')");
		// ht1.put("END", "to_date('"+df.format(new
		// Date())+"','yyyy-mm-dd hh24:mi:ss')");
		// String str2=insert(ht1, "some");
		// System.out.println(str2);

		// 灏嗘棩鏈熷鐞嗗悗鏌ヨOracle鐨勬暟鎹�
		// 浜叉祴鍙敤鐨剆ql锛歩nsert into some(BEGIN,NAME,END)
		// values(to_date('2018-02-09 04:22:42','yyyy-mm-dd
		// hh24:mi:ss'),'Bill',to_date('2018-02-09 04:22:42','yyyy-mm-dd
		// hh24:mi:ss'))

		// SimpleDateFormat df = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//璁剧疆鏃ユ湡鏍煎紡
		// Hashtable<String, String> ht1 = new Hashtable<String, String>();
		// String sql=getSqlContent(ht1, "API");
		// String columnStr =
		// "ID,NAME,TYPE_ID,KEYWORD,CREATOR,BELONG_SYSTEM,PROJECT_NAME,LEADER,FILE_ID,to_char(CREATE_TIME,'yyyy-MM-dd HH24:mi:ss') as CREATE_TIME,LAST_CHANGETIME,MESSAGE";
		// sql = sql.replaceAll("\\*", columnStr);
		// if(!sql.contains("where")){
		// sql+=" where CREATE_TIME BETWEEN TO_DATE('2018-02-08 12:30:11', 'YYYY-MM-DD HH24:MI:SS') AND TO_DATE('2018-02-28 13:00:11', 'YYYY-MM-DD HH24:MI:SS')";
		// }
		// else{
		// sql+="and CREATE_TIME BETWEEN TO_DATE('2018-02-08 12:30:11', 'YYYY-MM-DD HH24:MI:SS') AND TO_DATE('2018-02-28 13:00:11', 'YYYY-MM-DD HH24:MI:SS')";
		// }
		// sql += " order by id desc";
		// System.out.println(sql);
		// System.out.println(select(sql));

		// SimpleDateFormat df = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//璁剧疆鏃ユ湡鏍煎紡
		// Hashtable<String, String> ht = new Hashtable<String, String>();
		// ht.put("CREATE_TIME", "to_date('"+df.format(new
		// Date())+"','yyyy-mm-dd hh24:mi:ss')");
		// ht.put("FILE_ID", "1");
		// DBUtil.insert(ht,"API");
		// System.out
		// .print(DBUtil
		// .select("select SERVICE_ID from EMAIL_BATCH where BATCH_ID = 6"));

		// String realName = Util
		// .Regex(".*:\"(.*)\"}]",
		// DBUtil.select("select NAME from FILES where NOTE = '151987473112312.png'"),
		// 1);
		// System.out.println(realName);
	}

}
