package com.pos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;

import com.pos.bean.Product;
import com.pos.bean.User;
import com.pos.sql.GetConn;

public class InsertProductDao {
	GetConn getConn = new GetConn();
	private Connection conn = getConn.getConnection();

	// 增加Product
	public boolean setProductInfoToDBbean(Product productinfo) {
		boolean blnrec = true;
		String strSql = "insert into product (package,description,lot_no,serial_no,qty,iv_min,iv_avg,iv_max,d_min,d_avg,d_max,vf_min,vf_avg,vf_max,wd,iv,created_at,updated_at,is_shipped,created_by,updated_by,input_no,input_date,in_po,in_lot,repo_id,stor_id,ship_id,output_no,output_date,customer_id,customer_order_no)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		System.out.println(strSql);
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(strSql);
			pstmt.setString(1, productinfo.getPackage());
			pstmt.setString(2, productinfo.getDescription());
			pstmt.setString(3, productinfo.getLotNo());
			pstmt.setString(4, productinfo.getSerialNo());
			pstmt.setInt(5, productinfo.getQty());
			pstmt.setFloat(6, productinfo.getIvMin());
			pstmt.setFloat(7, productinfo.getIvAvg());
			pstmt.setFloat(8, productinfo.getIvMax());
			pstmt.setFloat(9, productinfo.getdMin());
			pstmt.setFloat(10, productinfo.getdAvg());
			pstmt.setFloat(11, productinfo.getdMax());
			pstmt.setFloat(12, productinfo.getVfMin());
			pstmt.setFloat(13, productinfo.getVfAvg());
			pstmt.setFloat(14, productinfo.getVfMax());
			pstmt.setString(15, productinfo.getWd());
			pstmt.setString(16, productinfo.getIv());
			pstmt.setTimestamp(17, productinfo.getCreated_at());
			pstmt.setTimestamp(18, productinfo.getUpdated_at());
			pstmt.setBoolean(19, productinfo.isShipped());
			pstmt.setInt(20, productinfo.getCreatedBy());
			pstmt.setInt(21, productinfo.getUpdatedBy());
			pstmt.setString(22, productinfo.getInputNo());
			pstmt.setDate(23, productinfo.getInputDate());
			pstmt.setString(24, productinfo.getInPo());
			pstmt.setString(25, productinfo.getInLot());
			pstmt.setInt(26, productinfo.getRepoId());
			pstmt.setInt(27, productinfo.getStorId());
			pstmt.setInt(28, productinfo.getShipId());
			pstmt.setString(29, productinfo.getOutputNo());
			pstmt.setDate(30, productinfo.getOutputDate());
			pstmt.setInt(31, productinfo.getCustomerId());
			pstmt.setString(32, productinfo.getCustomerOrderNo());
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
	
	public boolean setProductIsShipped(Product productinfo) {
		boolean blnrec = true;
		String strSql = "update product set is_shipped = ?, updated_at=?, updated_by=?, output_no=?, output_date=?, customer_id=?, customer_order_no=?";
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(strSql);
			pstmt.setBoolean(1, productinfo.isShipped());
			pstmt.setTimestamp(2, productinfo.getUpdated_at());
			pstmt.setInt(3, productinfo.getUpdatedBy());
			pstmt.setString(4, productinfo.getOutputNo());
			pstmt.setDate(5, productinfo.getOutputDate());
			pstmt.setInt(6, productinfo.getCustomerId());
			pstmt.setString(7, productinfo.getCustomerOrderNo());
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
		Product product = new Product();
		product.setPackage("1111");
		product.setCreated_at(currentTimestamp);
		product.setCreatedBy(1);
		product.setUpdated_at(currentTimestamp);
		product.setdAvg((float)11.00);
		InsertProductDao id = new InsertProductDao();
		boolean bool = id.setProductInfoToDBbean(product);
		System.out.println(bool);
	}
}
