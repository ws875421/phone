package com.ord.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OrdDAO implements Ord_interface {

	private static DataSource ds = null;

	// SQL
	private static final String GET_ORD = "SELECT * FROM ORD WHERE VENDOR_NO = ?";

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA107G3");
		} catch (NamingException e) {

			e.printStackTrace();
		}
	}

	@Override
	public List<OrdVO> getListbyVendor(String vendor_no) {

		List<OrdVO> ordlist = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		OrdVO ordVO = null;

		try {

			con = ds.getConnection();
			pstm = con.prepareStatement(GET_ORD);
			pstm.setString(1, vendor_no);
			rs = pstm.executeQuery();

			while (rs.next()) {
				// ordVO 也稱為 Domain objects
				ordVO = new OrdVO();
				ordVO.setOrd_no(rs.getString("ord_no"));
				ordVO.setMem_no(rs.getString("mem_no"));
				ordVO.setVendor_no(rs.getString("vendor_no"));
				ordVO.setTbl_no(rs.getString("tbl_no"));
				ordVO.setParty_size(rs.getInt("party_size"));
				ordVO.setShare_mem_no1(rs.getString("share_mem_no1"));
				ordVO.setShare_mem_no2(rs.getString("share_mem_no2"));
				ordVO.setShare_amount(rs.getInt("share_amount"));
				ordVO.setOrd_time(rs.getTimestamp("ord_time"));
				ordVO.setBooking_date(rs.getDate("booking_date"));
				ordVO.setBooking_time(rs.getString("booking_time"));
				ordVO.setNotes(rs.getString("notes"));
				ordVO.setTotal(rs.getInt("total"));
				ordVO.setArrival_time(rs.getString("arrival_time"));
				ordVO.setFinish_time(rs.getString("finish_time"));
				ordVO.setVerif_code(rs.getString("verif_code"));
				ordVO.setStatus(rs.getInt("status"));
				ordlist.add(ordVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return ordlist;
	}

	public static void main(String[] args) {

		System.out.println(new OrdDAO().getListbyVendor("V000001"));

	}
}
