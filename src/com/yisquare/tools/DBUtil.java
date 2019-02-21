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
	 *            閺屻儴顕梥ql鐠囶厼褰�
	 * @return json閺嶇厧绱￠惃鍕波閺嬫粓娉�
	 */
	public static String select(String sql) {
		Connection con = new DBConf().getConn();
		String json;
		try {
			json = new JSONValidatingWriter().write(new QueryRunner().query(
					con, sql, new MapListHandler()));
			logger.warn("Queryjson"+json);
			con.close();
			return json;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param sql
	 *            閺屻儴顕梥ql鐠囶厼褰�
	 * @return json閺嶇厧绱￠惃鍕波閺嬫粓娉�
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
		// 婵″倹鐏夐弰顖氼嚠oracle 閺佺増宓佹惔鎾圭箻鐞涘本褰冮崗銉︽闂傚瓨鎼锋担婊堟付鐟曚椒绗呴棃锟�2濮濓拷
		sql = sql.replaceAll("'to_date", "to_date");
		sql = sql.replaceAll("'yyyy-mm-dd hh24:mi:ss'\\)'",
				"'yyyy-mm-dd hh24:mi:ss'\\)");
		// Remove print console -- 20180612
		//System.out.println(sql);
		try {
			stat = con.createStatement();// 婵縿鍊栭弻鐔封枖閺囩偛鐏＄�点倛娅ｉ弫銈嗙鎼淬垹鈷旈悶娑樼焸濞笺倝骞�娑撶硾L閻犲浂鍘艰ぐ鐐虹嵁閹壆绠查柛銉у仜閻ｇ娀骞嶉敓鑺ユ櫢闁瑰瓨鍔楃划銊╁几濠婂懏鐣遍悗鐢殿攰閿燂拷?
			list.put("errorcode", stat.executeUpdate(sql) + "");// 闁瑰灚鎸稿畵鍐磼閹惧浜�
			list.put("errmsg", "ok");
			con.commit();
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
			// Remove print console -- 20180612
			//System.out.println("閺屻儴顕楅惃鍕嚔閸欍儲妲搁敍锟�" + sql);
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
	 *            sql鐠囶厼褰�
	 * @return list缂佹挻鐏夐敓锟�?
	 * @throws SQLException
	 */
	public static List<Object[]> selectList(String sql) {
		try {
			return new QueryRunner().query(new DBConf().getConn(), sql,
					new ArrayListHandler());// 閹跺﹦绮ㄩ弸婊堟肠娑擃厾娈戝В蹇庣鐞涘本鏆熼幑顕�鍏樻潪顒佸灇閿燂拷?閿熸枻鎷烽弫鎵矋閿涘苯鍟�鐎涙ɑ鏂侀崚鐧搃st閿燂拷?
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
		after_sql += " ID = '" + id + "'";//Change into harcode id = ?
		midle_sql = midle_sql.substring(0, midle_sql.length() - 3);
		sql = before_sql + midle_sql + after_sql;
		// Remove print console -- 20180612
		//System.out.println(sql);
		logger.warn("Update sql is--->>" + sql);//
		// Remove print console -- 20180612
		//System.out.println("Update sql is--->>" + sql);
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
	 * 閸楁洜瀚惃鍓坧date鐠囶厼褰�
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
		// Remove print console -- 20180612
		//System.out.println("delete sql:" + sql);

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
	 * 閺嶈宓佹导鐘虹箻閺夈儳娈慼t閺夈儴鍤滈崝銊ф晸閹存仩ql鐠囶厼褰�
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
		Connection con = new DBConf().getConn();// 閸掓稑缂撴稉锟芥稉顏呮殶閹诡喖绨辨潻鐐村复
		PreparedStatement pre = null;// 閸掓稑缂撴０鍕椽鐠囨垼顕㈤崣銉ヮ嚠鐠炩槄绱濇稉锟介懜顒勫厴閺勵垳鏁ゆ潻娆庨嚋閼板奔绗夐悽鈯縯atement
		ResultSet result = null;// 閸掓稑缂撴稉锟芥稉顏嗙波閺嬫粓娉︾�电钖�
		try {

			pre = con.prepareStatement(sql);// 鐎圭偘绶ラ崠鏍暕缂傛牞鐦х拠顓炲綖
			result = pre.executeQuery();// 閹笛嗩攽閺屻儴顕楅敍灞炬暈閹板繑瀚崣铚傝厬娑撳秹娓剁憰浣稿晙閸旂姴寮弫锟�
			ResultSetMetaData metaData = result.getMetaData();
			int columnCount = metaData.getColumnCount();
			String str = "";
			while (result.next()) {
				// 瑜版挾绮ㄩ弸婊堟肠娑撳秳璐熺粚鐑樻
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
				// 闁劒绔寸亸鍡曠瑐闂堛垻娈戦崙鐘遍嚋鐎电钖勯崗鎶芥４閿涘苯娲滄稉杞扮瑝閸忔娊妫撮惃鍕樈娴兼艾濂栭崫宥嗭拷褑鍏橀妴浣歌嫙娑撴柨宕伴悽銊ㄧカ濠э拷
				// 濞夈劍鍓伴崗鎶芥４閻ㄥ嫰銆庢惔蹇ョ礉閺堬拷閸氬簼濞囬悽銊ф畱閺堬拷閸忓牆鍙ч梻锟�
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				// Remove print console -- 20180612
				//System.out.println("閺佺増宓佹惔鎾圭箾閹恒儱鍑￠崗鎶芥４閿涳拷");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/***
	 * 鏉╂瑦妲搁弽瑙勫祦鐎圭偤妾惃鍕付濮瑰倽顔曠拋鈥冲毉閺夈儳娈戦弻銉嚄閿涘瞼鏁辨禍搴℃躬oracle娑擃厽鐓￠崙鐑樻降閻ㄥ嫭妞傞梻瀛樻Цobject
	 * 閸掆晝鏁ら幋鎴滄粦閼奉亜绻侀惃鍑濨Uil闂呭彞浜掔憴锝嗙�介敍锟�
	 * 
	 * @param ht
	 * @param tablename
	 * @param create_time1
	 * @param create_time2
	 * @return
	 */
	public static String getQuerySql(Hashtable<String, String> ht,
			String tablename, String create_time1, String create_time2) {
		String sql = DBUtil.getSqlContent(ht, tablename);// 閼惧嘲褰囬崚婵囶劄閻ㄥ墕ql
		String columnStr = "";
		String timeFieldName = "";// 娑撳秴鎮撻惃鍕�冩稉顓熸闂傚娈戠�涙顔岄崥宥勭瑝娑擄拷閺嶏拷 閹碉拷娴犮儴顩︾拋鍓х枂
		// 娑撹桨绨＄亸鍝綼te缁鐎风憴锝嗙�介崙鐑樻降閿涘苯鐨�*閸欓攱宕查幋锟� 鐎涙顔岄妴锟�
		switch (tablename) {
		case ("APIPlatform_API"): {
			columnStr = "ID,NAME,TYPE_ID,KEYWORD,CREATOR,BELONG_SYSTEM,PROJECT_NAME,LEADER,FILE_ID,to_char(CREATE_TIME,'yyyy-MM-dd HH24:mi:ss') as CREATE_TIME,LAST_CHANGETIME,MESSAGE";
			timeFieldName = "CREATE_TIME";
			break;
		}
		case ("TRANSACTION_MONITOR"):{
			columnStr = "BUSINESS_ID,RULE_ID,BUSINESS_STATUS,BUSINESS_TYPE,REFERENCE_ID,to_char(CREATE_TIMESTAMP,'yyyy-MM-dd HH24:mi:ss') as CREATE_TIME,to_char(MODIFIED_TIMESTAMP,'yyyy-MM-dd HH24:mi:ss') as LAST_CHANGETIME";
			// Change time field into MODIFIED_TIMESTAMP -- 20180611
			timeFieldName = "MODIFIED_TIMESTAMP";
			if(ht.containsKey("SELECT_TYPE")){
				String oldBuzId = "BUSINESS_ID='"+ht.get("BUSINESS_ID")+"'";
				String newBuzId = "BUSINESS_ID LIKE '%"+ht.get("BUSINESS_ID")+"%'";
				sql = sql.replace(oldBuzId, newBuzId).replace("and SELECT_TYPE='LIKE'", "");
			}
			break;
		}
		case ("SERVICE_MONITOR"): {
			columnStr = "ID,BUSINESS_ID,RULE_ID,SERVERID,SERVICENAME,SERVICE_STATUS,ERRORMESSAGE,to_char(MODIFIED_TIMESTAMP,'yyyy-MM-dd HH24:mi:ss') as LAST_CHANGETIME";
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
		case ("MAIL_ROUTER"): {
			columnStr = "ID,NAME,ADDRESS";
			timeFieldName = "CREATE_TIMESTAMP";
			break;
		}
		case ("SCHE_BATCH_FOR_ROUTER"): {
			columnStr = "ID,SCHE_ID,MAIL_ROUTER_ID,SEND,CC";
			timeFieldName = "CREATE_TIMESTAMP";
			break;
		}
		case ("SCHE_BATCH_FOR_RULE"): {
			columnStr = "SCHE_ID,RULE_ID";
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
		// 閸欘亣顩﹂悽銊﹀煕闁瀚ㄦ禍鍡曠娑擃亝妫╅張鐕傜礄閺冪姾顔戝锟芥慨瀣箷閺勵垳绮ㄩ弶鐔告闂傝揪绱� 娓氬灝绱戞慨瀣⒔鐞涳拷
		if ((create_time1 != null && create_time1.length() != 0)
				|| create_time2 != null && create_time2.length() != 0) {
			if (create_time1 == "" || create_time1 == null
					|| create_time1.length() == 0) {// 瀵拷婵妞傞梻缈犺礋缁屽搫姘ㄧ拋鍓х枂瀵拷婵妞傞梻缈犺礋瀵板牊妫惃鍕闂傦拷
				create_time1 = "2001-01-01 00:00:00";
			}
			if (create_time2 == "" || create_time2 == null
					|| create_time2.length() == 0) {// 缂佹挻娼弮鍫曟？娑撹櫣鈹栫亸杈啎缂冾喕璐熻ぐ鎾冲閻ㄥ嫭妞傞梻锟�
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 鐠佸墽鐤嗛弮銉︽埂閺嶇厧绱�
				create_time2 = df.format(new Date());
			}
			if (!sql.contains("where")) {// 婵″倹鐏塻ql鐠囶厼褰炴稉顓濈瑝閸栧懎鎯坵here鐎涙顔岄敍鍫濆祮濞屸剝婀侀崗鏈电铂閻ㄥ嫬鍨介弬顓熸蒋娴犺绱氱亸鍗炲娑撳here
				sql += " where " + timeFieldName + " BETWEEN TO_DATE('"
						+ create_time1
						+ "', 'YYYY-MM-DD HH24:MI:SS') AND TO_DATE('"
						+ create_time2 + "', 'YYYY-MM-DD HH24:MI:SS')";
			} else {// 閸氾箑鍨崝鐘辩瑐and
				sql += " and " + timeFieldName + " BETWEEN TO_DATE('"
						+ create_time1
						+ "', 'YYYY-MM-DD HH24:MI:SS') AND TO_DATE('"
						+ create_time2 + "', 'YYYY-MM-DD HH24:MI:SS')";
			}
		} else {// 閻€劍鍩涘▽鈩冩箒闁瀚ㄩ弮銉︽埂鐏忥拷 姒涙顓婚弻銉嚄娴犲﹤銇夐惃锟�
			if (!sql.contains("where")) {// 婵″倹鐏塻ql鐠囶厼褰炴稉顓濈瑝閸栧懎鎯坵here鐎涙顔岄敍鍫濆祮濞屸剝婀侀崗鏈电铂閻ㄥ嫬鍨介弬顓熸蒋娴犺绱氱亸鍗炲娑撳here
				sql += " where to_char(" + timeFieldName
						+ ",'yyyy-mm-dd')<=to_char(sysdate,'yyyy-mm-dd')";
			}
		}
		
		// 2019-02-21 Add compatibility to mysql
		//Remove order by id desc and add rows constraint in 10000 -- 20180611
		String dbType = new DBConf().getDBType();
		if(dbType.equals("MySQL")){
			sql += " limit 10000";
		}else{
			sql += " and rownum <= 10000";
		}
		logger.warn(sql);
		return sql;
	}

	public static void main(String[] args) throws Exception {
		// Hashtable<String, String> ht = new Hashtable<String, String>();
		// String str=select(ht,"BillInfo");
		// str="\"rows\":"+str;
		// System.out.println(str);
		// ht.put("g_id", "1");select(ht, "customer").equals("[]")
		// ht.put("c_name", "瀵版劘鍚�");
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
		// ht.put("User_Type","鐎瑰爼鏆�");
		// ht.put("User_Name", "閸掓繀鑵戞稉澶屽疆");
		// ht.put("User_Class","濞屾瑧鐓舵稉澶夎厬");
		// ht.put("User_Tel", "15629899999");
		// for(int i=0;i<100;i++){
		// try {
		// // ht.put("Record_Id", Util.getMaxId()+1+"");
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// DBUtil.insert(ht, "record");//鐏忓棙鏆熼幑顔肩摠閸忋儲鏆熼幑顔肩氨
		// }
		//
		// String jsonStr=DBUtil.select(ht, "record");
		// System.out.println(jsonStr);
		// JSONArray jsonArray=new JSONArray(jsonStr);
		// jsonArray.getJSONObject(0).get("Record_Id");
		// System.out.println(DBUtil.select("select * from api where id = "+"'1'"));
		// Hashtable<String, String> ht = new Hashtable<String, String>();

		// 閺屻儴顕楅惃鍒紃acle閻ㄥ嫭妫╅張鐔荤箻鐞涘本鐗稿蹇撳
		// Hashtable<String, String> ht = new Hashtable<String, String>();
		// String
		// str=select("select to_char(end,'yyyy-MM-dd HH24:mi:ss')as time from some ");
		// System.out.println(str);

		// 鐏忓棙妫╅張鐔奉槱閻炲棗鎮楅幓鎺戝弳Oracle閻ㄥ嫭鏆熼幑锟�
		// 娴滃弶绁撮崣顖滄暏閻ㄥ墕ql閿涙nsert into some(BEGIN,NAME,END)
		// values(to_date('2018-02-09 04:22:42','yyyy-mm-dd
		// hh24:mi:ss'),'Bill',to_date('2018-02-09 04:22:42','yyyy-mm-dd
		// hh24:mi:ss'))

		// SimpleDateFormat df = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//鐠佸墽鐤嗛弮銉︽埂閺嶇厧绱�
		// Hashtable<String, String> ht1 = new Hashtable<String, String>();
		// ht1.put("NAME", "Bill1");
		// ht1.put("BEGIN", "to_date('"+df.format(new
		// Date())+"','yyyy-mm-dd hh24:mi:ss')");
		// ht1.put("END", "to_date('"+df.format(new
		// Date())+"','yyyy-mm-dd hh24:mi:ss')");
		// String str2=insert(ht1, "some");
		// System.out.println(str2);

		// 鐏忓棙妫╅張鐔奉槱閻炲棗鎮楅弻銉嚄Oracle閻ㄥ嫭鏆熼幑锟�
		// 娴滃弶绁撮崣顖滄暏閻ㄥ墕ql閿涙nsert into some(BEGIN,NAME,END)
		// values(to_date('2018-02-09 04:22:42','yyyy-mm-dd
		// hh24:mi:ss'),'Bill',to_date('2018-02-09 04:22:42','yyyy-mm-dd
		// hh24:mi:ss'))

		// SimpleDateFormat df = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//鐠佸墽鐤嗛弮銉︽埂閺嶇厧绱�
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
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//鐠佸墽鐤嗛弮銉︽埂閺嶇厧绱�
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
