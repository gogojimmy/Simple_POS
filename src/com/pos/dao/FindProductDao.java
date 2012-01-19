package com.pos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pos.bean.Product;
import com.pos.sql.GetConn;

public class FindProductDao {
	GetConn getConn = new GetConn();
	private Connection conn = getConn.getConnection();
	
	public FindProductDao() {
	}
	
	public List<Product> getProductByPackage(String _package) {
		String sql = "select * from product where package=? and is_shipped=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> lstList = new ArrayList<Product>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, _package);
			pstmt.setBoolean(2, false);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setPackage(rs.getString("package"));
				product.setDescription(rs.getString("description"));
				product.setLotNo(rs.getString("lot_no"));
				product.setSerialNo(rs.getString("serial_no"));
				product.setQty(rs.getInt("qty"));
				product.setIvMin(rs.getFloat("iv_min"));
				product.setIvAvg(rs.getFloat("iv_avg"));
				product.setIvMax(rs.getFloat("iv_max"));
				product.setdMin(rs.getFloat("d_min"));
				product.setdAvg(rs.getFloat("d_avg"));
				product.setdMax(rs.getFloat("d_max"));
				product.setVfMin(rs.getFloat("vf_min"));
				product.setVfAvg(rs.getFloat("vf_avg"));
				product.setVfMax(rs.getFloat("vf_max"));
				product.setWd(rs.getString("wd"));
				product.setIv(rs.getString("iv"));
				product.setCreated_at(rs.getTimestamp("created_at"));
				product.setUpdated_at(rs.getTimestamp("updated_at"));
				product.setShipped(rs.getBoolean("is_shipped"));
				product.setCreatedBy(rs.getInt("created_by"));
				product.setUpdatedBy(rs.getInt("updated_by"));
				product.setInputNo(rs.getString("input_no"));
				product.setInputDate(rs.getDate("input_date"));
				product.setInPo(rs.getString("in_po"));
				product.setInLot(rs.getString("in_lot"));
				product.setRepoId(rs.getInt("repo_id"));
				product.setStorId(rs.getInt("stor_id"));
				product.setShipId(rs.getInt("ship_id"));
				lstList.add(product);
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
	
	public List<Product> getProductBySerialNo(String serialNo) {
		String sql = "select * from product where serial_no=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> lstList = new ArrayList<Product>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, serialNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setPackage(rs.getString("package"));
				product.setDescription(rs.getString("description"));
				product.setLotNo(rs.getString("lot_no"));
				product.setSerialNo(rs.getString("serial_no"));
				product.setQty(rs.getInt("qty"));
				product.setIvMin(rs.getFloat("iv_min"));
				product.setIvAvg(rs.getFloat("iv_avg"));
				product.setIvMax(rs.getFloat("iv_max"));
				product.setdMin(rs.getFloat("d_min"));
				product.setdAvg(rs.getFloat("d_avg"));
				product.setdMax(rs.getFloat("d_max"));
				product.setVfMin(rs.getFloat("vf_min"));
				product.setVfAvg(rs.getFloat("vf_avg"));
				product.setVfMax(rs.getFloat("vf_max"));
				product.setWd(rs.getString("wd"));
				product.setIv(rs.getString("iv"));
				product.setCreated_at(rs.getTimestamp("created_at"));
				product.setUpdated_at(rs.getTimestamp("updated_at"));
				product.setShipped(rs.getBoolean("is_shipped"));
				product.setCreatedBy(rs.getInt("created_by"));
				product.setUpdatedBy(rs.getInt("updated_by"));
				product.setInputNo(rs.getString("input_no"));
				product.setInputDate(rs.getDate("input_date"));
				product.setInPo(rs.getString("in_po"));
				product.setInLot(rs.getString("in_lot"));
				product.setRepoId(rs.getInt("repo_id"));
				product.setStorId(rs.getInt("stor_id"));
				product.setShipId(rs.getInt("ship_id"));
				product.setOutputDate(rs.getDate("output_date"));
				product.setCustomerId(rs.getInt("customer_id"));
				product.setCustomerOrderNo(rs.getString("customer_order_no"));
				lstList.add(product);
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
	
	public List<Product> getStocks() {
		String sql = "select * from product where qty>0 and is_shipped = false";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> lstList = new ArrayList<Product>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setPackage(rs.getString("package"));
				product.setDescription(rs.getString("description"));
				product.setLotNo(rs.getString("lot_no"));
				product.setSerialNo(rs.getString("serial_no"));
				product.setQty(rs.getInt("qty"));
				product.setIvMin(rs.getFloat("iv_min"));
				product.setIvAvg(rs.getFloat("iv_avg"));
				product.setIvMax(rs.getFloat("iv_max"));
				product.setdMin(rs.getFloat("d_min"));
				product.setdAvg(rs.getFloat("d_avg"));
				product.setdMax(rs.getFloat("d_max"));
				product.setVfMin(rs.getFloat("vf_min"));
				product.setVfAvg(rs.getFloat("vf_avg"));
				product.setVfMax(rs.getFloat("vf_max"));
				product.setWd(rs.getString("wd"));
				product.setIv(rs.getString("iv"));
				product.setCreated_at(rs.getTimestamp("created_at"));
				product.setUpdated_at(rs.getTimestamp("updated_at"));
				product.setShipped(rs.getBoolean("is_shipped"));
				product.setCreatedBy(rs.getInt("created_by"));
				product.setUpdatedBy(rs.getInt("updated_by"));
				product.setInputNo(rs.getString("input_no"));
				product.setInputDate(rs.getDate("input_date"));
				product.setInPo(rs.getString("in_po"));
				product.setInLot(rs.getString("in_lot"));
				product.setRepoId(rs.getInt("repo_id"));
				product.setStorId(rs.getInt("stor_id"));
				product.setShipId(rs.getInt("ship_id"));
				product.setOutputDate(rs.getDate("output_date"));
				product.setCustomerId(rs.getInt("customer_id"));
				product.setCustomerOrderNo(rs.getString("customer_order_no"));
				lstList.add(product);
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
	
	public List<Product> getProductTurnoverRate() {
		String sql = "select * from product where qty>0";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> lstList = new ArrayList<Product>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setPackage(rs.getString("package"));
				product.setDescription(rs.getString("description"));
				product.setLotNo(rs.getString("lot_no"));
				product.setSerialNo(rs.getString("serial_no"));
				product.setQty(rs.getInt("qty"));
				product.setIvMin(rs.getFloat("iv_min"));
				product.setIvAvg(rs.getFloat("iv_avg"));
				product.setIvMax(rs.getFloat("iv_max"));
				product.setdMin(rs.getFloat("d_min"));
				product.setdAvg(rs.getFloat("d_avg"));
				product.setdMax(rs.getFloat("d_max"));
				product.setVfMin(rs.getFloat("vf_min"));
				product.setVfAvg(rs.getFloat("vf_avg"));
				product.setVfMax(rs.getFloat("vf_max"));
				product.setWd(rs.getString("wd"));
				product.setIv(rs.getString("iv"));
				product.setCreated_at(rs.getTimestamp("created_at"));
				product.setUpdated_at(rs.getTimestamp("updated_at"));
				product.setShipped(rs.getBoolean("is_shipped"));
				product.setCreatedBy(rs.getInt("created_by"));
				product.setUpdatedBy(rs.getInt("updated_by"));
				product.setInputNo(rs.getString("input_no"));
				product.setInputDate(rs.getDate("input_date"));
				product.setInPo(rs.getString("in_po"));
				product.setInLot(rs.getString("in_lot"));
				product.setRepoId(rs.getInt("repo_id"));
				product.setStorId(rs.getInt("stor_id"));
				product.setShipId(rs.getInt("ship_id"));
				product.setOutputDate(rs.getDate("output_date"));
				product.setCustomerId(rs.getInt("customer_id"));
				product.setCustomerOrderNo(rs.getString("customer_order_no"));
				lstList.add(product);
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
	
	public List<Product> getProductByCustomerId(int customerId) {
		String sql = "select * from product where customer_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> lstList = new ArrayList<Product>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, customerId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setPackage(rs.getString("package"));
				product.setDescription(rs.getString("description"));
				product.setLotNo(rs.getString("lot_no"));
				product.setSerialNo(rs.getString("serial_no"));
				product.setQty(rs.getInt("qty"));
				product.setIvMin(rs.getFloat("iv_min"));
				product.setIvAvg(rs.getFloat("iv_avg"));
				product.setIvMax(rs.getFloat("iv_max"));
				product.setdMin(rs.getFloat("d_min"));
				product.setdAvg(rs.getFloat("d_avg"));
				product.setdMax(rs.getFloat("d_max"));
				product.setVfMin(rs.getFloat("vf_min"));
				product.setVfAvg(rs.getFloat("vf_avg"));
				product.setVfMax(rs.getFloat("vf_max"));
				product.setWd(rs.getString("wd"));
				product.setIv(rs.getString("iv"));
				product.setCreated_at(rs.getTimestamp("created_at"));
				product.setUpdated_at(rs.getTimestamp("updated_at"));
				product.setShipped(rs.getBoolean("is_shipped"));
				product.setCreatedBy(rs.getInt("created_by"));
				product.setUpdatedBy(rs.getInt("updated_by"));
				product.setInputNo(rs.getString("input_no"));
				product.setInputDate(rs.getDate("input_date"));
				product.setInPo(rs.getString("in_po"));
				product.setInLot(rs.getString("in_lot"));
				product.setRepoId(rs.getInt("repo_id"));
				product.setStorId(rs.getInt("stor_id"));
				product.setShipId(rs.getInt("ship_id"));
				product.setOutputDate(rs.getDate("output_date"));
				product.setCustomerId(rs.getInt("customer_id"));
				product.setCustomerOrderNo(rs.getString("customer_order_no"));
				lstList.add(product);
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
	
	public List<Product> getProductInRepo() {
		String sql = "select * from product where qty>0 and is_shipped=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> lstList = new ArrayList<Product>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setBoolean(1, true);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setPackage(rs.getString("package"));
				product.setDescription(rs.getString("description"));
				product.setLotNo(rs.getString("lot_no"));
				product.setSerialNo(rs.getString("serial_no"));
				product.setQty(rs.getInt("qty"));
				product.setIvMin(rs.getFloat("iv_min"));
				product.setIvAvg(rs.getFloat("iv_avg"));
				product.setIvMax(rs.getFloat("iv_max"));
				product.setdMin(rs.getFloat("d_min"));
				product.setdAvg(rs.getFloat("d_avg"));
				product.setdMax(rs.getFloat("d_max"));
				product.setVfMin(rs.getFloat("vf_min"));
				product.setVfAvg(rs.getFloat("vf_avg"));
				product.setVfMax(rs.getFloat("vf_max"));
				product.setWd(rs.getString("wd"));
				product.setIv(rs.getString("iv"));
				product.setCreated_at(rs.getTimestamp("created_at"));
				product.setUpdated_at(rs.getTimestamp("updated_at"));
				product.setShipped(rs.getBoolean("is_shipped"));
				product.setCreatedBy(rs.getInt("created_by"));
				product.setUpdatedBy(rs.getInt("updated_by"));
				product.setInputNo(rs.getString("input_no"));
				product.setInputDate(rs.getDate("input_date"));
				product.setInPo(rs.getString("in_po"));
				product.setInLot(rs.getString("in_lot"));
				product.setRepoId(rs.getInt("repo_id"));
				product.setStorId(rs.getInt("stor_id"));
				product.setShipId(rs.getInt("ship_id"));
				product.setOutputDate(rs.getDate("output_date"));
				product.setCustomerId(rs.getInt("customer_id"));
				product.setCustomerOrderNo(rs.getString("customer_order_no"));
				lstList.add(product);
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
}
