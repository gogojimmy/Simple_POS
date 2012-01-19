package com.pos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;

import com.pos.bean.Customer;
import com.pos.bean.MyMD5;
import com.pos.bean.User;
import com.pos.sql.GetConn;

public class InsertCustomerDao {
	GetConn getConn = new GetConn();
	private Connection conn = getConn.getConnection();

	public boolean setCustomerInfoToDBbean(Customer customerinfo) {
		boolean blnrec = true;
		String strSql = "insert into customer (name,created_at,created_by) values(?,?,?)";
		System.out.println(strSql);
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(strSql);
			pstmt.setString(1, customerinfo.getName());
			pstmt.setTimestamp(2, customerinfo.getCreatedAt());
			pstmt.setInt(3, customerinfo.getCreatedBy());
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
	
	// 修改客戶名稱
	public boolean updatecCustomerName(Customer customerinfo) {
		boolean blnrec = true;
		String strSql = "update Customer set name=? " + "where id = ?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(strSql);
			pstmt.setString(1,customerinfo.getName());
			pstmt.setInt(2, customerinfo.getId());
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
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		Customer customer = new Customer();
		customer.setName("Customer");
		customer.setCreatedAt(currentTimestamp);
		customer.setCreatedBy(23);
		InsertCustomerDao id = new InsertCustomerDao();
		boolean bool = id.setCustomerInfoToDBbean(customer);
		System.out.println(bool);
	}
}
