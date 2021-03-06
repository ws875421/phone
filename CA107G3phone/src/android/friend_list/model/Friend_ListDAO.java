package android.friend_list.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Friend_ListDAO implements Friend_ListDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA107G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	// select * from FRIEND_LIST right JOIN MEMBER on
	// FRIEND_LIST.FRIE_NO=MEMBER.MEM_NO where FRIEND_LIST.MEM_NO='M000001';
	 private static final String GET_ONE_STMT = "SELECT * FROM FRIEND_LIST WHERE MEM_NO =?";
//	private static final String GET_ONE_STMT = "select * from FRIEND_LIST right JOIN MEMBER on FRIEND_LIST.FRIE_NO=MEMBER.MEM_NO where FRIEND_LIST.MEM_NO=?";

//	 
//	private static final String GET_ONE_STMT1 = "SELECT MEM_NAME FROM MEMBER WHERE MEM_NO IN (SELECT  MEM_NO FROM FRIEND_LIST WHERE MEM_NO =?)";
	private static final String GET_NAME = "SELECT MEM_NAME FROM MEMBER WHERE MEM_NO =?";

//	@Override
//	public List<Friend_ListVO> getfd(String mem_no) {
//
//		Connection con = null;
//		PreparedStatement pstm = null;
//		PreparedStatement pstm1 = null;
//		ResultSet rs = null;
//		ResultSet rs1 = null;
//		Friend_ListVO fdvo = null;
//		List<Friend_ListVO> fdlist = new ArrayList<>();
//		String s = null;
//
//		try {
//			con = ds.getConnection();
//			pstm = con.prepareStatement(GET_ONE_STMT);
//			pstm1 = con.prepareStatement(GET_ONE_STMT1);
//
//			pstm.setString(1, mem_no);
//			rs = pstm.executeQuery();
//
//			pstm1.setString(1, mem_no);
//			rs1 = pstm1.executeQuery();
//
//			while (rs1.next()) {
//				s = rs1.getString(1);
//			}
//
//			while (rs.next()) {
//				fdvo = new Friend_ListVO();
//				fdvo.setMem_no(s);
//
////					fdvo.setFrie_no(rs.getString(2));
//				fdvo.setFrie_no(rs.getString(5));
//				fdvo.setFrie_code(rs.getInt(3));
//				fdlist.add(fdvo);
//			}
//
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstm != null) {
//				try {
//					pstm.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//
//		return fdlist;
//	}

	@Override
	public String getName(String mem_no) {

		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String getName = null;
		

		try {
			con = ds.getConnection();
			pstm = con.prepareStatement(GET_NAME);
			pstm.setString(1, mem_no);
			rs = pstm.executeQuery();
			while (rs.next()) {

				getName = rs.getString(1);
			}

		} catch (SQLException e) {

			e.printStackTrace();
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

		return getName;
	}

	@Override
	public List<Friend_ListVO> getfd(String mem_no) {

		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Friend_ListVO fdvo = null;
		List<Friend_ListVO> fdlist = new ArrayList<>();

		try {
			con = ds.getConnection();
			pstm = con.prepareStatement(GET_ONE_STMT);
			pstm.setString(1, mem_no);
			rs = pstm.executeQuery();

			while (rs.next()) {
				fdvo = new Friend_ListVO();
				fdvo.setMem_no(rs.getString(1));
				fdvo.setFrie_no(rs.getString(2));
//				fdvo.setFrie_no(rs.getString(5));
				fdvo.setFrie_code(rs.getInt(3));
				fdlist.add(fdvo);
			}

		} catch (SQLException e) {

			e.printStackTrace();
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

		return fdlist;
	}
}
