package mpc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库连接工具
 * @author Hu
 *
 */
public class DBUtil {
	
	public static String db_url = "jdbc:mysql://192.168.57.128:3306/hive?createDatabaseIfNotExist=true";
	public static String db_user = "hive";
	public static String db_pass = "hive";
	
	public static Connection getConn () {
		Connection conn = null;
		
		try {
			Class.forName("org.apache.hive.jdbc.HiveDriver");//加载驱动
			conn = DriverManager.getConnection(db_url, db_user, db_pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * 关闭连接
	 * @param state
	 * @param conn
	 */
	public static void close (Statement state, Connection conn) {
		if (state != null) {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close (ResultSet rs, Statement state, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (state != null) {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws SQLException {
		ClassService cs=new ClassService();
		EntityToString ets=new EntityToString();
		
		System.out.println(ets.getStringList(cs.list("data1", InfoNo2.class)));
		System.out.println(ets.getStringList(cs.list("data4", InfoNo3.class)));
		System.out.println(ets.getStringList(cs.list("data5", InfoNo4.class)));
		//System.out.println(ets.getStringList(cs.list("data", No1Info.class)));
	}
}
