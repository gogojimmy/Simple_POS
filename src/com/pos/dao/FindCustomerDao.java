package com.pos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pos.bean.Customer;
import com.pos.bean.User;
import com.pos.sql.GetConn;

public class FindCustomerDao {
	GetConn getConn = new GetConn();
	private Connection conn = getConn.getConnection();

	public FindCustomerDao() {
	}
	
	public List<Customer> getAllCustomers() {
		String sql = "select * from Customer";
		Statement pstmt = null;
		ResultSet rs = null;
		List<Customer> lstList = new ArrayList<Customer>();
		try {
			pstmt = conn.createStatement();
			rs = pstmt.executeQuery(sql);
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getInt("id"));
				customer.setName(rs.getString("name"));
				customer.setCreatedAt(rs.getTimestamp("created_at"));
				customer.setCreatedBy(rs.getInt("created_by"));
				lstList.add(customer);
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
	
	public Customer findCustomerByName(String name) {
		String sql = "select * from Customer where name=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Customer customer = new Customer();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				customer.setId(rs.getInt("id"));
				customer.setName(rs.getString("name"));
				customer.setCreatedBy(rs.getInt("created_by"));
				customer.setCreatedAt(rs.getTimestamp("created_at"));
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
		return customer;
	}
	
	public Customer getCustomerById(Customer customer) {
		String strSql = "select * from Customer where id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(strSql);
			pstmt.setInt(1, customer.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				customer.setId(rs.getInt("id"));
				customer.setName(rs.getString("name"));
				customer.setCreatedAt(rs.getTimestamp("created_at"));
				customer.setCreatedBy(rs.getInt("created_by"));
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
		return customer;
	}
	
	//刪除客戶
	public boolean delCustomerInfo(int lybId) {
		boolean blnrec = true;
		String strSql = "delete from Customer where id = ?";
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
}
