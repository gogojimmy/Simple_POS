package com.pos.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;
import com.pos.bean.RepoStorShip;
import com.pos.bean.User;
import com.pos.sql.GetConn;

public class FindRepoStorShipDao {
	GetConn getConn = new GetConn();
	private Connection conn = getConn.getConnection();
	
	public FindRepoStorShipDao(){
	}
	
	// 傳回該Type所有的值
	public List<RepoStorShip> findAll(int type) {
		String strSql = "select * from repo_stor_ship where type=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RepoStorShip> lstList = new ArrayList<RepoStorShip>();
		try {
			pstmt = conn.prepareStatement(strSql);
			pstmt.setInt(1, type);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				RepoStorShip rss = new RepoStorShip();
				rss.setId(rs.getInt("id"));
				rss.setName(rs.getString("name"));
				rss.setType(rs.getInt("type"));
				lstList.add(rss);
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
	
	public RepoStorShip getRssById(RepoStorShip rss) {
		String strSql = "select * from repo_stor_ship where id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(strSql);
			pstmt.setInt(1, rss.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				rss.setId(rs.getInt("id"));
				rss.setName(rs.getString("name"));
				rss.setType(rs.getInt("type"));
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
		return rss;
	}
}
