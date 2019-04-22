package com.vendor.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class VendorDAO implements VendorDAO_interface {

	private static DataSource ds = null;

	// SQL
	private static final String GET_ONE_STMT = "SELECT * FROM Vendor WHERE v_account = ?";
	private static final String FIND_BY_ID_PASWD = "SELECT * FROM Vendor WHERE v_account = ? AND v_pwd = ?";
	//圖片
	private static final String FIND_IMG_BY_ISBN = "SELECT V_PIC FROM Vendor WHERE vendor_no =?";

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA107G3");
		} catch (NamingException e) {

			e.printStackTrace();
		}
	}

	public VendorDAO() {
	}

	@Override
	public boolean isVendor(String v_account, String v_pwd) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		boolean isVendor = false;

		try {

			con = ds.getConnection();
			pstm = con.prepareStatement(FIND_BY_ID_PASWD);
			pstm.setString(1, v_account);
			pstm.setString(2, v_pwd);
			rs = pstm.executeQuery();
			isVendor = rs.next();
			return isVendor;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return isVendor;
	}

	@Override
	public VendorVO findByPK(String v_account) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		VendorVO vendor = null;

		try {

			con = ds.getConnection();
			pstm = con.prepareStatement(GET_ONE_STMT);
			pstm.setString(1, v_account);
			rs = pstm.executeQuery();

			while (rs.next()) {
				vendor = new VendorVO();
				vendor.setVendor_no(rs.getString(1));
				vendor.setV_account(rs.getString(2));
				vendor.setV_pwd(rs.getString(3));
				vendor.setV_mail(rs.getString(4));
				vendor.setV_tel(rs.getString(5));
				vendor.setV_n_code(rs.getString(6));
				vendor.setV_ad_code(rs.getString(7));
				vendor.setV_address1(rs.getString(8));
				vendor.setV_address2(rs.getString(9));
				vendor.setV_address3(rs.getString(10));
				vendor.setV_wallet(rs.getString(11));
				vendor.setV_name(rs.getString(12));
				vendor.setV_w_no(rs.getInt(13));
				vendor.setV_n_no(rs.getInt(14));
				vendor.setV_alt_no(rs.getInt(15));
				vendor.setV_start_time(rs.getString(16));
				vendor.setV_end_time(rs.getString(17));
				vendor.setV_day(rs.getString(18));
				vendor.setV_tables(rs.getString(19));
//				vendor.setV_pic(rs.getBytes(20));
//				vendor.setV_ad(rs.getBytes(21));
				vendor.setV_status(rs.getString(22));
				vendor.setV_wait_status(rs.getString(23));
				vendor.setV_type(rs.getString(24));
				vendor.setV_text(rs.getString(25));
			}
			System.out.println("DAO查詢完畢");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return vendor;
	}

	@Override
	public byte[] getImage(String vendor_no) {
		byte[] picture = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

//			try {
//				Class.forName("oracle.jdbc.driver.OracleDriver");
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "CA107G3", "123456");

			// pool
			con = ds.getConnection();

			pstmt = con.prepareStatement(FIND_IMG_BY_ISBN);
			pstmt.setString(1, vendor_no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				picture = rs.getBytes(1);
			}
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
			if (pstmt != null) {
				try {
					pstmt.close();
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
		return picture;

	}

	@Override
	public int insert(VendorVO vendorVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(VendorVO vendor) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String vendor_no) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int create(VendorVO vendorVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void detail(VendorVO vendorVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void check(VendorVO vendorVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void waitOnOff(VendorVO vendorVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public int updatePic(VendorVO vendorVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateAd(VendorVO vendorVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public VendorVO findByAcc(String v_account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VendorVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
