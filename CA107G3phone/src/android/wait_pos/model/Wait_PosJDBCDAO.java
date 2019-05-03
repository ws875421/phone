package android.wait_pos.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Wait_PosJDBCDAO implements Wait_PosDAO_interface {

	final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	final static String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	final static String USER = "CA107G3";
	final static String PASSWORD = "123456";

	// SQL
	private static final String INSERT_STMT = "INSERT INTO WAIT_POS VALUES ('WP'||LPAD(to_char(WAIT_POS_SEQ.NEXTVAL), 8, '0'),?,?,?,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE WAIT_POS SET WAIT_PEOPLE = ?, WAIT_REMIND = ?, WAIT_STATUS = ? WHERE (MEM_NO = ?) AND (VENDOR_NO = ?)";
	private static final String DELETE = "DELETE FROM WAIT_POS WHERE WAIT_NO =?";
	private static final String GET_ONE_STMT = "SELECT * FROM WAIT_POS WHERE WAIT_NO = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM WAIT_POS ";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Wait_PosJDBCDAO() {

	}

	@Override
	public int insert(Wait_PosVO posVO) {
		Connection con = null;
		PreparedStatement pstm = null;
		int rs = 0;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstm = con.prepareStatement(INSERT_STMT);

			pstm.setString(1, posVO.getMem_no());
			pstm.setString(2, posVO.getVendor_no());
			pstm.setInt(3, posVO.getWait_mun());
			pstm.setInt(4, posVO.getWait_people());
			pstm.setInt(5, posVO.getWait_preg());
			pstm.setTimestamp(6, posVO.getWait_gettime());
			pstm.setInt(7, posVO.getWait_remind());
			pstm.setInt(8, posVO.getWait_status());

			rs = pstm.executeUpdate();

			System.out.println("更新筆數 : " + rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public int update(Wait_PosVO posVO) {
		Connection con = null;
		PreparedStatement pstm = null;
		int rs = 0;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstm = con.prepareStatement(UPDATE);
			pstm.setInt(1, posVO.getWait_people());
			pstm.setInt(2, posVO.getWait_remind());
			pstm.setInt(3, posVO.getWait_status());
			pstm.setString(4, posVO.getMem_no());
			pstm.setString(5, posVO.getVendor_no());

			rs = pstm.executeUpdate();

			System.out.println("更新筆數 : " + rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public int delete(String posVO_no) {
		Connection con = null;
		PreparedStatement pstm = null;
		int rs = 0;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstm = con.prepareStatement(DELETE);
			pstm.setString(1, posVO_no);
			rs = pstm.executeUpdate();

			System.out.println("更新筆數 : " + rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public Wait_PosVO findByPrimaryKey(String posVO_no) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Wait_PosVO posVO = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstm = con.prepareStatement(GET_ONE_STMT);
			pstm.setString(1, posVO_no);
			rs = pstm.executeQuery();

			while (rs.next()) {
				posVO = new Wait_PosVO();
				posVO.setWait_no(posVO_no);
				posVO.setMem_no(rs.getString(2));
				posVO.setVendor_no(rs.getString(3));
				posVO.setWait_mun(rs.getInt(4));
				posVO.setWait_people(rs.getInt(5));
				posVO.setWait_gettime(rs.getTimestamp(6));
				posVO.setWait_remind(rs.getInt(7));
				posVO.setWait_status(rs.getInt(8));

			}
			System.out.println("查詢完畢");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return posVO;
	}

	@Override
	public List<Wait_PosVO> getAll() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Wait_PosVO posVO = null;
		List<Wait_PosVO> wlist = new ArrayList<>();

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstm = con.prepareStatement(GET_ALL_STMT);

			rs = pstm.executeQuery();

			while (rs.next()) {
				posVO = new Wait_PosVO();
				posVO.setWait_no(rs.getString(1));
				posVO.setMem_no(rs.getString(2));
				posVO.setVendor_no(rs.getString(3));
				posVO.setWait_mun(rs.getInt(4));
				posVO.setWait_people(rs.getInt(5));
				posVO.setWait_gettime(rs.getTimestamp(6));
				posVO.setWait_remind(rs.getInt(7));
				posVO.setWait_status(rs.getInt(8));
				wlist.add(posVO);
			}
			System.out.println("查詢完畢");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wlist;
	}

	// 測試
	public static void main(String[] args) {

		Wait_PosJDBCDAO wDAO = new Wait_PosJDBCDAO();
		
		// insert
		Wait_PosVO wait_posVO1 = new Wait_PosVO();
		wait_posVO1.setMem_no("M000001");
		wait_posVO1.setVendor_no("V000001");
		wait_posVO1.setWait_mun(new Integer(1));
		wait_posVO1.setWait_people(new Integer(2));
		wait_posVO1.setWait_preg(new Integer(0));
		wait_posVO1.setWait_gettime(new java.sql.Timestamp(System.currentTimeMillis()));
		wait_posVO1.setWait_remind(new Integer(3));
		wait_posVO1.setWait_status(new Integer(2));
		
		wDAO.insert(wait_posVO1);
		

		// 單筆查詢
//		System.out.println(wDAO.findByPrimaryKey("WP00000001"));

		// 多筆查詢
//		List<Wait_PosVo> wlist = wDAO.getAll();
//		for (Wait_PosVo w : wlist) {
//			System.out.println(w);
//		}

		// 刪除
//		wDAO.delete("WP00000005");

		// 更新
//		Wait_PosVo wp1 = new Wait_PosVo("M000004", "V000004", 5, 6, 3);
//		wDAO.update(wp1);

		// 兩個方法都可以
//		long t = System.currentTimeMillis();
//		java.sql.Timestamp sqltime = new java.sql.Timestamp(t);
//		System.out.println(sqltime);




		

	}
}
