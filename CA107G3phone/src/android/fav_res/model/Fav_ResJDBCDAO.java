//package android.fav_res.model;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Fav_ResJDBCDAO implements Fav_ResDAO_interface {
//
//	final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
//	final static String URL = "jdbc:oracle:thin:@localhost:1521:XE";
//	final static String USER = "CA107G3";
//	final static String PASSWORD = "123456";
//
//	// SQL
//	private static final String INSERT_STMT = "INSERT INTO FAV_RES VALUES (?,?)";
//	private static final String UPDATE = "UPDATE FAV_RES SET VENDOR_NO = ? WHERE MEM_NO = ?";
//	private static final String DELETE = "DELETE FROM FAV_RES WHERE (MEM_NO =?) AND(VENDOR_NO=?)";
//	private static final String GET_ONE_STMT = "SELECT * FROM FAV_RES WHERE mem_no = ?";
//	private static final String GET_ALL_STMT = "SELECT * FROM FAV_RES ";
//
//	static {
//		try {
//			Class.forName(DRIVER);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public Fav_ResJDBCDAO() {
//	}
//
//	@Override
//	public int insert(Fav_ResVo fav_ResVo) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		int rs = 0;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			pstm = con.prepareStatement(INSERT_STMT);
//			pstm.setString(1, fav_ResVo.getMem_no());
//			pstm.setString(2, fav_ResVo.getVendor_no());
//
//			rs = pstm.executeUpdate();
//
//			System.out.println("更新筆數 : " + rs);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return rs;
//	}
//
//	@Override
//	public int update(Fav_ResVo fav_ResVo) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		int rs = 0;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			pstm = con.prepareStatement(UPDATE);
//			pstm.setString(1, fav_ResVo.getVendor_no());
//			pstm.setString(2, fav_ResVo.getMem_no());
//
//			rs = pstm.executeUpdate();
//
//			System.out.println("更新筆數 : " + rs);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return rs;
//	}
//
//	@Override
//	public int delete(String mem_no, String vendor_no) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		int rs = 0;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			pstm = con.prepareStatement(DELETE);
//			pstm.setString(1, mem_no);
//			pstm.setString(2, vendor_no);
//			rs = pstm.executeUpdate();
//
//			System.out.println("更新筆數 : " + rs);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return rs;
//	}
//
//	@Override
//	public List<Fav_ResVo> findByMem_No(String rep_res_no) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		Fav_ResVo fav_ResVo = null;
//		List<Fav_ResVo> flist = new ArrayList<>();
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			pstm = con.prepareStatement(GET_ONE_STMT);
//			pstm.setString(1, rep_res_no);
//			rs = pstm.executeQuery();
//
//			while (rs.next()) {
//				fav_ResVo = new Fav_ResVo();
//				fav_ResVo.setMem_no(rep_res_no);
//				fav_ResVo.setVendor_no(rs.getString(2));
//				flist.add(fav_ResVo);
//			}
//			System.out.println("查詢完畢");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return flist;
//	}
//
//	@Override
//	public List<Fav_ResVo> getAll() {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		Fav_ResVo fav_ResVo = null;
//		List<Fav_ResVo> flist = new ArrayList<>();
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			pstm = con.prepareStatement(GET_ALL_STMT);
//
//			rs = pstm.executeQuery();
//
//			while (rs.next()) {
//				fav_ResVo = new Fav_ResVo();
//				fav_ResVo.setMem_no(rs.getString(1));
//				fav_ResVo.setVendor_no(rs.getString(2));
//
//				flist.add(fav_ResVo);
//			}
//			System.out.println("查詢完畢");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return flist;
//	}
//
//	public static void main(String[] args) {
//
//		Fav_ResDAO_interface fDAO = new Fav_ResJDBCDAO();
//
//		// 查詢會員共有收藏那些廠商
//		List<Fav_ResVo> favlist = fDAO.findByMem_No("M000001");
//		for (Fav_ResVo f : favlist) {
//			System.out.println(f);
//		}
//
//		// 多筆查詢
////		List<Fav_ResVo> flist = fDAO.getAll();
////		for (Fav_ResVo f : flist) {
////			System.out.println(f);
////		}
//
//		// 刪除
////		fDAO.delete("M000005", "V000005");
//
//		// 更新 (應該不會有此功能)
////		Fav_ResVo f1 = new Fav_ResVo("M000004", "V000003");
////		fDAO.update(f1);
//
//		// 插入資料
////		Fav_ResVo f2 = new Fav_ResVo("M000001", "V000004");
////		fDAO.insert(f2);
//	}
//
//}
