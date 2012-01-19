package com.pos.sql;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JOptionPane;

public class GetConn {
	private Connection conn = null;
	private ResultSet rs = null;
	private Statement stmt = null;
	private Properties props;
	private String url, user, password, db;

	public GetConn() { // 構造方法
		props = new Properties();
		try {
			props.load(new FileInputStream("Settings/db.properties"));
			url = props.getProperty("url");
			user = props.getProperty("user");
			db = props.getProperty("db");
			password = props.getProperty("password");
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Can't Load db.properties");
			e1.printStackTrace();
		}
	}

	// 獲取資料庫連接方法
	public Connection getConnection() {
		try {
			String myurl = url + db;
			conn = DriverManager.getConnection(myurl, user, password);
			return conn;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 功能：執行查詢語句
	 */
	public ResultSet executeQuery(String sql) {
		try {
			conn = getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex);
			System.err.println(ex.getMessage());
		}
		return rs;
	}

	/*
	 * 功能:執行更新操作
	 */
	public int executeUpdate(String sql) {
		int result = 0;
		try {
			conn = getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			result = stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			result = 0;
		}
		try {
			stmt.close();
		} catch (SQLException ex1) {
		}
		return result;
	}

	/*
	 * 功能:關閉資料庫的連接
	 */
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

}
