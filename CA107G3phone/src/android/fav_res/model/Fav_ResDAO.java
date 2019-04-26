package android.fav_res.model;

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

public class Fav_ResDAO implements Fav_ResDAO_interface {

	// SQL
	private static final String INSERT_STMT = "INSERT INTO FAV_RES VALUES (?,?)";
	private static final String UPDATE = "UPDATE FAV_RES SET VENDOR_NO = ? WHERE MEM_NO = ?";
	private static final String DELETE = "DELETE FROM FAV_RES WHERE (MEM_NO =?) AND(VENDOR_NO=?)";
	private static final String GET_ONE_STMT = "SELECT * FROM FAV_RES WHERE mem_no = ?";
	private static final String GET_ONE = "SELECT * FROM FAV_RES WHERE mem_no = ? AND VENDOR_NO = ? ";
	private static final String GET_ALL_STMT = "SELECT * FROM FAV_RES ";
	// SELECT V_NAME FROM VENDOR WHERE VENDOR_NO IN (SELECT VENDOR_NO FROM FAV_RES
	// WHERE MEM_NO = 'M000001');
	private static final String GETALLVNAME = "SELECT V_NAME FROM VENDOR WHERE VENDOR_NO IN (SELECT VENDOR_NO FROM FAV_RES WHERE MEM_NO = ?)";

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA107G3");
		} catch (NamingException e) {

			e.printStackTrace();
		}
	}

	public Fav_ResDAO() {
		// TODO Auto-generated constructor stub
	}

	public int insert(Fav_ResVo fav_ResVo) {
		Connection con = null;
		PreparedStatement pstm = null;
		int rs = 0;

		try {
//			System.out.println("DAO"+fav_ResVo);
			con = ds.getConnection();
			pstm = con.prepareStatement(INSERT_STMT);
			pstm.setString(1, fav_ResVo.getMem_no());
			pstm.setString(2, fav_ResVo.getVendor_no());

			rs = pstm.executeUpdate();

			System.out.println("更新筆數 : " + rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

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
		return rs;
	}

	@Override
	public List<Fav_ResVo> find(String mem_no, String vendor_no) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Fav_ResVo fav_ResVo = null;
		List<Fav_ResVo> flist = new ArrayList<>();

		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
			con = ds.getConnection();
			pstm = con.prepareStatement(GET_ONE);
			pstm.setString(1, mem_no);
			pstm.setString(2, vendor_no);
			rs = pstm.executeQuery();

			while (rs.next()) {
				fav_ResVo = new Fav_ResVo();
				fav_ResVo.setMem_no(mem_no);
				fav_ResVo.setVendor_no(rs.getString(2));
				flist.add(fav_ResVo);
			}
			System.out.println("查詢完畢");
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

		return flist;
	}

	@Override
	public List<Fav_ResVo> findByMem_No(String mem_no) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Fav_ResVo fav_ResVo = null;
		List<Fav_ResVo> flist = new ArrayList<>();

		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
			con = ds.getConnection();
			pstm = con.prepareStatement(GET_ONE_STMT);
			pstm.setString(1, mem_no);
			rs = pstm.executeQuery();

			while (rs.next()) {
				fav_ResVo = new Fav_ResVo();
				fav_ResVo.setMem_no(mem_no);
				fav_ResVo.setVendor_no(rs.getString(2));
				flist.add(fav_ResVo);
			}
			System.out.println("查詢完畢");
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

		return flist;
	}

	@Override
	public int delete(String mem_no, String vendor_no) {
		Connection con = null;
		PreparedStatement pstm = null;
		int rs = 0;

		try {
			con = ds.getConnection();
			pstm = con.prepareStatement(DELETE);
			pstm.setString(1, mem_no);
			pstm.setString(2, vendor_no);
			rs = pstm.executeUpdate();

			System.out.println("更新筆數 : " + rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

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
		return rs;
	}

	public List<Fav_ResVo> GETALLVNAME(String mem_no) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Fav_ResVo fav_ResVo = null;
		List<Fav_ResVo> flist = new ArrayList<>();

		try {
			con = ds.getConnection();
			pstm = con.prepareStatement(GETALLVNAME);
			pstm.setString(1, mem_no);
			rs = pstm.executeQuery();

			while (rs.next()) {
				fav_ResVo = new Fav_ResVo();
				fav_ResVo.setMem_no(mem_no);
				fav_ResVo.setVendor_no(rs.getString(1));
				flist.add(fav_ResVo);
			}
			System.out.println("查詢完畢");
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

		return flist;
	}

	@Override
	public int update(Fav_ResVo fav_ResVo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Fav_ResVo> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
