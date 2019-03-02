package oop;
import java.sql.*;

public class MySQLModule {
	private static MySQLModule mysqldb = null;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/UChicagoRegSystem?autoReconnect=true&useSSL=false";
				
	// Database credentials
	static final String USERNAME = "root";
	static final String PASSWORD = "Yubinwei0305!";

	// Properties
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	@Deprecated
	public static MySQLModule getInstance() {
		if(mysqldb == null) mysqldb = new MySQLModule();
		return mysqldb;
	}
	
	@Deprecated
	public ResultSet query(String sql) {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			
			// Execute a query
			if(conn == null) return null;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
			closeQuery();
			return null;
		} catch(Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
			closeQuery();
		    return null;
		}
	}
	
	@Deprecated
	public void update(String sql) {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			
			// Execute a query
			if(conn == null) return;
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
			closeQuery();
		} catch(Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
			closeQuery();
		}
	}
	
	@Deprecated
	public void closeQuery() {
		try {
			if(rs != null) { rs.close(); rs = null; }
			if(stmt != null) { stmt.close(); stmt = null; }
			if(conn != null) { conn.close(); conn = null; }
		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		} catch(Exception e) {
		    //Handle errors for Class.forName
		    e.printStackTrace();
		}
	}
}
