package com.highguard.Wisdom.struts.common;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	private static GetPathCommon common = new GetPathCommon();

	  public static final String driver = "com.mysql.jdbc.Driver";
//	  public static final String url = "jdbc:mysql://127.0.0.1:3306/fileapprove?useUnicode=true&characterEncoding=UTF-8";
//	  public static final String user = "appserver";
//	  public static final String password = "secret";
	  /**
	   * 获取数据库的连接
	   * @return
	   */
	  public static Connection getConnction(){
		 
		  Connection conn = null;
		  try {
			Class.forName(driver);
			String url = common.getCommonDir("url");
			String user = common.getCommonDir("user");
			String password = common.getCommonDir("password");
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	  }
	  /**
	   * 关闭数据库连接
	   * @param con
	   * @param rs
	   * @param st
	   */
	  public static void close(Connection con ,ResultSet rs,Statement st){
		  try {
			  if(rs!=null){
				  rs.close();
			  }
			  if(st!=null){
				  st.close();
			  }
			  if(con!=null){
				  con.close();
			  }
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
	  }
	  /**
	   * 获取所有的表名
	   * @return
	   */
	  public static String[] getAllTbles(){
		  String [] tables = {};
		  Connection con = JDBCUtil.getConnction();
		  try {
			Statement st = con.createStatement();
			DatabaseMetaData data = con.getMetaData();
			ResultSet rs = data.getTables(null, null, null, null);
			int a = 0;
			String table = "";
			
			while (rs.next())
			{
				table +=rs.getString("TABLE_NAME")+",";
				a++;
			}
			JDBCUtil.close(con, rs, st);
			table = table.substring(0, table.length()-1);
			tables = table.split(",");
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return tables;
	  }

}
