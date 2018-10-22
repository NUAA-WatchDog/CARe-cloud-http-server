package cn.zjt.iot.oncar.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Mr Dk.
 * @since 2018.4.18
 * @version 2018.4.18
 */

public class DatabaseUtil {
	
	private static final DatabaseUtil databaseUtil = new DatabaseUtil();
	
	private String Db_username = "sa";
	private String Db_password = "zhangjt1997";
	private String Db_driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String Db_URL = "jdbc:sqlserver://localhost:1433;DatabaseName=oncar";
	
	private Connection conn = null;

	public static String heartRateTablePre = "heart_rate_";
	public static String temperatureTablePre = "temperature_";
	public static String weightTablePre = "weight_";

	public static final int INSERT_NOT_UNIQUE = -1;
	public static final int USER_NOT_EXIST = -2;
	public static final int UPDATE_USER_FAILURE = -3;
	public static final int DELETE_USER_FAILURE = -4;
	public static final int PASSWORD_INCORRECT = -5;
	public static final int LOGIN_SUCCESS = -6;
	
	private DatabaseUtil () {
		
	}
	
	public static DatabaseUtil getInstance() {
		return databaseUtil;
	}
	
	public Connection getConnection () {
		try {
			Class.forName(Db_driver);
			conn = DriverManager.getConnection(Db_URL, Db_username, Db_password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
