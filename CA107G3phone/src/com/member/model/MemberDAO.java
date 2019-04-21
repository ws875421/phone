package com.member.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberDAO implements MemberDAO_interface {

	private static DataSource ds = null;
	private static final String INSERT_STMT = "INSERT INTO MEMBER VALUES ('M'||LPAD(to_char(member_seq.NEXTVAL), 6, '0'),?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE MEMBER SET MEM_NAME = ?, MEM_PWD = ?, MEM_GENDER = ?, MEM_TEL = ?, MEM_STATUS = ? , MEM_BALANCE = ?, MEM_NICKNAME = ?, MEM_PIC=? WHERE MEM_ACCOUNT = ?";
	private static final String DELETE = "DELETE FROM MEMBER WHERE MEM_NO =?";
	private static final String GET_ONE_STMT = "SELECT * FROM MEMBER WHERE mem_account = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM MEMBER ";
	private static final String FIND_BY_ID_PASWD = "SELECT * FROM member WHERE mem_account = ? AND mem_pwd = ?";
	// 圖片
	private static final String FIND_IMG_BY_ISBN = "SELECT MEM_PIC FROM MEMBER WHERE MEM_NO =?";

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA107G3");
		} catch (NamingException e) {

			e.printStackTrace();
		}
	}

	public MemberDAO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMember(String mem_account, String mem_pwd) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		boolean isMember = false;

		try {
			con = ds.getConnection();

//			try {
//				Class.forName("oracle.jdbc.driver.OracleDriver");
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","CA107G3", "123456");

			pstm = con.prepareStatement(FIND_BY_ID_PASWD);
			pstm.setString(1, mem_account);
			pstm.setString(2, mem_pwd);
			rs = pstm.executeQuery();
			isMember = rs.next();
			return isMember;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return isMember;
	}

	@Override
	public byte[] getImage(String mem_no) {
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

			//pool
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(FIND_IMG_BY_ISBN);
			pstmt.setString(1, mem_no);
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
	public int insert(MemberVo memberVO) {
		Connection con = null;
		PreparedStatement pstm = null;
		int rs = 0;

		try {
			con = ds.getConnection();
//			System.out.println("連線成功DAO!");

			pstm = con.prepareStatement(INSERT_STMT);
			pstm.setString(1, memberVO.getMem_name());
			pstm.setString(2, memberVO.getMem_account());
			pstm.setString(3, memberVO.getMem_pwd());
			pstm.setString(4, memberVO.getMem_gender());
			pstm.setString(5, memberVO.getMem_mail());
			pstm.setString(6, memberVO.getMem_id());
			pstm.setString(7, memberVO.getMem_tel());
			pstm.setString(8, memberVO.getMem_status());
//			pstm.setBytes(9, memberVO.getMem_pic());
			pstm.setDouble(10, memberVO.getMem_balance());
			pstm.setString(11, memberVO.getMem_nickname());

			rs = pstm.executeUpdate();
//			System.out.println("成功筆數 : " + rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rs;
	}

	@Override
	public int update(MemberVo memberVO) {
		Connection con = null;
		PreparedStatement pstm = null;
		int rs = 0;

		try {
			con = ds.getConnection();
			pstm = con.prepareStatement(UPDATE);
			pstm.setString(1, memberVO.getMem_name());
			pstm.setString(2, memberVO.getMem_pwd());

			String sec = memberVO.getMem_gender();
			if (sec.equals("男")) {
				sec = "M";
			} else {
				sec = "F";
			}
			pstm.setString(3, sec);
			pstm.setString(4, memberVO.getMem_tel());
			pstm.setString(5, memberVO.getMem_status());
//			System.out.println(memberVO.getMem_balance());
			pstm.setDouble(6, memberVO.getMem_balance());
			pstm.setString(7, memberVO.getMem_nickname());
//			pstm.setBytes(8, memberVO.getMem_pic());
//			System.out.println("@" + memberVO.getMem_pic());
			pstm.setString(9, memberVO.getMem_account());

			rs = pstm.executeUpdate();

//			System.out.println("更新筆數 : " + rs);

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rs;
	}

	@Override
	public int delete(String mem_no) {

		Connection con = null;
		PreparedStatement pstm = null;
		int rs = 0;

		try {
			con = ds.getConnection();
			pstm = con.prepareStatement(DELETE);
			pstm.setString(1, mem_no);
			rs = pstm.executeUpdate();

//			System.out.println("更新筆數 : " + rs);

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rs;

	}

	@Override
	public MemberVo findByPrimaryKey(String mem_account) {

		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		MemberVo member = null;

		try {
			con = ds.getConnection();
			pstm = con.prepareStatement(GET_ONE_STMT);
			pstm.setString(1, mem_account);
			rs = pstm.executeQuery();

			while (rs.next()) {
				member = new MemberVo();
				member.setMem_no(rs.getString(1));
				member.setMem_name(rs.getString(2));
				member.setMem_account(rs.getString(3));
				member.setMem_pwd(rs.getString(4));
				member.setMem_gender(rs.getString(5));
				member.setMem_mail(rs.getString(6));
				member.setMem_id(rs.getString(7));
				member.setMem_tel(rs.getString(8));
				member.setMem_status(rs.getString(9));
//				member.setMem_pic(rs.getBytes(10));
				member.setMem_balance(rs.getDouble(11));
				member.setMem_nickname(rs.getString(12));
			}
//			System.out.println("查詢完畢");

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return member;
	}

	@Override
	public List<MemberVo> getAll() {
		List<MemberVo> mlist = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		MemberVo member = null;

		try {
			con = ds.getConnection();
			pstm = con.prepareStatement(GET_ALL_STMT);
			rs = pstm.executeQuery();

			while (rs.next()) {
				member = new MemberVo();
				member.setMem_no(rs.getString(1));
				member.setMem_name(rs.getString(2));
				member.setMem_account(rs.getString(3));
				member.setMem_pwd(rs.getString(4));
				member.setMem_gender(rs.getString(5));
				member.setMem_mail(rs.getString(6));
				member.setMem_id(rs.getString(7));
				member.setMem_tel(rs.getString(8));
				member.setMem_status(rs.getString(9));
//			  
				member.setMem_balance(rs.getDouble(11));
				member.setMem_nickname(rs.getString(12));
				// 裝入集合
				mlist.add(member);
			}
//			System.out.println("查詢完畢");

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return mlist;
	}

}
