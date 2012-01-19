package com.pos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pos.bean.MyMD5;
import com.pos.bean.User;
import com.pos.sql.GetConn;

public class FindUserDao {
	GetConn getConn = new GetConn();
	private Connection conn = getConn.getConnection();

	public void MyFindUserDao() {
	}

	public User getUser(User user) {
		String strSql = "select * from user where username=? and password =?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MyMD5 myMd5 = new MyMD5();
		try {
			pstmt = conn.prepareStatement(strSql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, myMd5.createPassWord(user.getPassword()));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setCreatedAt(rs.getTimestamp("created_at"));
				user.setLastLogin(rs.getTimestamp("last_login"));
				user.setAdmin(rs.getBoolean("is_admin"));
				user.setLogin(rs.getBoolean("is_login"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					rs.close();
					pstmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	// 依照id找user
	public User getUserById(User user) {
		String strSql = "select * from user where id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(strSql);
			pstmt.setInt(1, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setCreatedAt(rs.getTimestamp("created_at"));
				user.setLastLogin(rs.getTimestamp("last_login"));
				user.setAdmin(rs.getBoolean("is_admin"));
				user.setLogin(rs.getBoolean("is_login"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					rs.close();
					pstmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	// 依照username找user
		public User getUserByUsername(User user) {
			String strSql = "select * from user where username=?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				pstmt = conn.prepareStatement(strSql);
				pstmt.setString(1, user.getUsername());
				rs = pstmt.executeQuery();
				while (rs.next()) {
					user.setId(rs.getInt("id"));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setCreatedAt(rs.getTimestamp("created_at"));
					user.setLastLogin(rs.getTimestamp("last_login"));
					user.setAdmin(rs.getBoolean("is_admin"));
					user.setLogin(rs.getBoolean("is_login"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null) {
						rs.close();
						pstmt.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return user;
		}

	// 尋找最新的user
	public int getUserID() {
		String strSql = "select max(id)as id from user";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int i = 0;
		try {
			pstmt = conn.prepareStatement(strSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				i = rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					rs.close();
					pstmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return i;
	}
	
	// 尋找所有user
	public List<User> getAllUsers() {
		String strSql = "select * from user";
		Statement pstmt = null;
		ResultSet rs = null;
		List<User> lstList = new ArrayList<User>();
		try {
			pstmt = conn.createStatement();
			rs = pstmt.executeQuery(strSql);
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setCreatedAt(rs.getTimestamp("created_at"));
				user.setLastLogin(rs.getTimestamp("last_login"));
				user.setAdmin(rs.getBoolean("is_admin"));
				user.setLogin(rs.getBoolean("is_login"));
				lstList.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					rs.close();
					pstmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lstList;
	}
	
	// 刪除user
	public boolean delUserInfo(int lybId) {
		boolean blnrec = true;
		String strSql = "delete from user where id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(strSql);
			pstmt.setInt(1, lybId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			blnrec = false;
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return blnrec;
	}
	
	public static void main(String[] args) {
		FindUserDao findUser = new FindUserDao();
		int us = findUser.getUserID();
		System.out.println(us);
	}
}
