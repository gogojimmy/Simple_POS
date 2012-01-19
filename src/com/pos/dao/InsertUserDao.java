package com.pos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;

import javax.swing.JOptionPane;

import com.pos.bean.MyMD5;
import com.pos.bean.User;
import com.pos.sql.GetConn;

public class InsertUserDao {
	GetConn getConn = new GetConn();
	private Connection conn = getConn.getConnection();

	// 增加user
	public boolean setUserInfoToDBbean(User userinfo) {
		boolean blnrec = true;
		String strSql = "insert into user (password,username,created_at,last_login,is_admin,is_login)"
				+ " values(?,?,?,?,?,?)";
		System.out.println(strSql);
		PreparedStatement pstmt = null;
		MyMD5 md = new MyMD5();
		try {
			pstmt = conn.prepareStatement(strSql);
			pstmt.setString(1, md.createPassWord(userinfo.getPassword()));
			pstmt.setString(2, userinfo.getUsername());
			pstmt.setTimestamp(3, userinfo.getCreatedAt());
			pstmt.setTimestamp(4, userinfo.getLastLogin());
			pstmt.setBoolean(5, userinfo.isAdmin());
			pstmt.setBoolean(6, userinfo.isLogin());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			blnrec = false;
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

	// 修改user，判斷使用者是否已登入
	public boolean setUserIsLogin(User userinfo) {
		boolean blnrec = true;
		String strSql = "update user set is_login = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(strSql);
			pstmt.setBoolean(1, userinfo.isLogin());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			blnrec = false;
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

	// 修改user
	public boolean updateUser(User userinfo) {
		boolean blnrec = true;
		String strSql = "update user set username=?,password=?,is_admin=?,is_login=? "
				+ "where Id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(strSql);
			pstmt.setString(1, userinfo.getUsername());
			pstmt.setString(2, userinfo.getPassword());
			pstmt.setBoolean(3, userinfo.isAdmin());
			pstmt.setBoolean(4, userinfo.isLogin());
			pstmt.setInt(5, userinfo.getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			blnrec = false;
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

	// 修改user密碼
	public boolean updateUserPassWord(User userinfo) {
		boolean blnrec = true;
		String strSql = "update user set password=? " + "where id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(strSql);
			MyMD5 mymd5 = new MyMD5();
			pstmt.setString(1, mymd5.createPassWord(userinfo.getPassword()));
			pstmt.setInt(2, userinfo.getId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			blnrec = false;
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
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(
				now.getTime());
		User user = new User();
		user.setUsername("admin");
		user.setPassword("0000");
		user.setAdmin(true);
		user.setLogin(false);
		user.setCreatedAt(currentTimestamp);
		user.setLastLogin(currentTimestamp);
		boolean bool = false;
		try {
			InsertUserDao id = new InsertUserDao();
			bool = id.setUserInfoToDBbean(user);
			JOptionPane.showMessageDialog(null, bool);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, bool);
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
