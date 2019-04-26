//package android.vendor.model;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.sql.DataSource;
//
//public class VendorJDBCDAO implements VendorDAO_interface {
//
//	final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
//	final static String URL = "jdbc:oracle:thin:@localhost:1521:XE";
//	final static String USER = "CA107G3";
//	final static String PASSWORD = "123456";
//	private static DataSource ds = null;
//
//	// SQL
//	private static final String INSERT_STMT = "INSERT INTO VENDOR VALUES ('V'||LPAD(to_char(VENDOR_SEQ.NEXTVAL), 6, '0'),?,?,?,?,?,?,?,?,?,?,?,0,0,0,?,?,?,?,NULL,NULL,?)";
//	private static final String CREAT_STMT = "INSERT INTO VENDOR VALUES ('V'||LPAD(to_char(VENDOR_SEQ.NEXTVAL), 6, '0'),?,?,?,?,?,?,?,?,?,0,?,0,0,0,0,0,0,0,?,NULL,0,0,'','')";
////	private static final String UPDATE = "UPDATE VENDOR SET V_PWD = ?, V_TEL = ?, V_N_CODE = ?, V_AD_CODE = ?, V_ADDRESS1 = ?, V_ADDRESS2 = ?, V_ADDRESS3 = ?, V_WALLET = ?, V_NAME = ?, V_START_TIME = ?, V_END_TIME = ?, V_DAY = ?, V_TURN_TIME = ?, V_STATUS = ? WHERE V_ACCOUNT=?";
//	private static final String UPDATE = "UPDATE VENDOR SET v_type = ?, v_start_time = ?, v_end_time = ?, v_day = ?, v_tables = ?, v_text = ? WHERE vendor_no=?";
//	private static final String UPDATE_PIC = "UPDATE VENDOR SET v_pic = ? WHERE vendor_no=?";
//	private static final String UPDATE_AD = "UPDATE VENDOR SET v_ad = ? WHERE vendor_no=?";
//	private static final String DELETE = "DELETE FROM Vendor WHERE vendor_no =?";
//	private static final String GET_ONE_STMT = "SELECT * FROM Vendor WHERE v_account = ?";
//	private static final String GET_ACC_STMT = "SELECT * FROM Vendor WHERE v_account = ?";
//	private static final String GET_ALL_STMT = "SELECT * FROM Vendor ";
//	//Android
//	private static final String FIND_BY_ID_PASWD = "SELECT * FROM Vendor WHERE v_account = ? AND v_pwd = ?";
//	
//	
//	static {
//		try {
//			Context ctx = new InitialContext();
//			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA107G3");
//		} catch (NamingException e) {
//
//			e.printStackTrace();
//		}
//	}
//
//	
//	@Override
//	public boolean isVendor(String v_account, String v_pwd) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		boolean isVendor = false;
//
//		try {
//			
//
////			try {
////				Class.forName("oracle.jdbc.driver.OracleDriver");
////			} catch (ClassNotFoundException e) {
////				e.printStackTrace();
////			}
////			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","CA107G3", "123456");
//
//			con = ds.getConnection();
//			pstm = con.prepareStatement(FIND_BY_ID_PASWD);
//			pstm.setString(1, v_account);
//			pstm.setString(2, v_pwd);
//			rs = pstm.executeQuery();
//			isVendor = rs.next();
//			return isVendor;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//
//					e.printStackTrace();
//				}
//			}
//
//			if (pstm != null) {
//				try {
//					pstm.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//		return isVendor;
//	}
//	
//	
//	@Override
//	public int insert(VendorVO vendorVO) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		int rs = 0;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			System.out.println("連線成功!");
//			pstm = con.prepareStatement(INSERT_STMT);
//			pstm.setString(1, vendorVO.getV_account());
//			pstm.setString(2, vendorVO.getV_pwd());
//			pstm.setString(3, vendorVO.getV_mail());
//			pstm.setString(4, vendorVO.getV_tel());
//			pstm.setString(5, vendorVO.getV_n_code());
//			pstm.setString(6, vendorVO.getV_ad_code());
//			pstm.setString(7, vendorVO.getV_address1());
//			pstm.setString(8, vendorVO.getV_address2());
//			pstm.setString(9, vendorVO.getV_address3());
//			pstm.setString(10, vendorVO.getV_wallet());
//			pstm.setString(11, vendorVO.getV_name());
//			pstm.setString(12, vendorVO.getV_start_time());
//			pstm.setString(13, vendorVO.getV_end_time());
//			pstm.setString(14, vendorVO.getV_day());
//			pstm.setString(15, vendorVO.getV_tables());
//			pstm.setString(16, vendorVO.getV_status());
//
//			rs = pstm.executeUpdate();
//			System.out.println("成功筆數 : " + rs);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return rs;
//	}
//
//	@Override
//	public int create(VendorVO vendorVO) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		int rs = 0;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			System.out.println("連線成功!");
//			pstm = con.prepareStatement(CREAT_STMT);
//			pstm.setString(1, vendorVO.getV_account());
//			pstm.setString(2, vendorVO.getV_pwd());
//			pstm.setString(3, vendorVO.getV_mail());
//			pstm.setString(4, vendorVO.getV_tel());
//			pstm.setString(5, vendorVO.getV_n_code());
//			pstm.setString(6, vendorVO.getV_ad_code());
//			pstm.setString(7, vendorVO.getV_address1());
//			pstm.setString(8, vendorVO.getV_address2());
//			pstm.setString(9, vendorVO.getV_address3());
////			pstm.setString(10, vendorVO.getV_wallet());
//			pstm.setString(10, vendorVO.getV_name());
////			pstm.setBytes(11, vendorVO.getV_pic());
////			pstm.setString(13, vendorVO.getV_end_time());
////			pstm.setString(14, vendorVO.getV_day());
////			pstm.setString(15, vendorVO.getV_turn_time());
////			pstm.setString(16, vendorVO.getV_status());
//
//			rs = pstm.executeUpdate();
//			System.out.println("成功筆數 : " + rs);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return rs;
//	}
//
//	@Override
//	public int update(VendorVO vendorVO) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		int rs = 0;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			System.out.println("連線成功!");
//			pstm = con.prepareStatement(UPDATE);
//
//			pstm.setString(1, vendorVO.getV_type());
//			pstm.setString(2, vendorVO.getV_start_time());
//			pstm.setString(3, vendorVO.getV_end_time());
//			pstm.setString(4, vendorVO.getV_day());
//			pstm.setString(5, vendorVO.getV_tables());
//			pstm.setString(6, vendorVO.getV_text());
//			pstm.setString(7, vendorVO.getVendor_no());
//
//			System.out.println(vendorVO.getVendor_no());
//
//			rs = pstm.executeUpdate();
//			System.out.println("成功筆數 : " + rs);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return rs;
//	}
//
//	@Override
//	public int delete(String vendor_no) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		int rs = 0;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			System.out.println("連線成功!");
//			pstm = con.prepareStatement(DELETE);
//			pstm.setString(1, vendor_no);
//			rs = pstm.executeUpdate();
//			System.out.println("成功筆數 : " + rs);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return rs;
//	}
//
//	@Override
//	public VendorVO findByPK(String v_account) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		VendorVO vendor = null;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			pstm = con.prepareStatement(GET_ONE_STMT);
//			pstm.setString(1, v_account);
//			rs = pstm.executeQuery();
//
//			while (rs.next()) {
//				vendor = new VendorVO();
//
//				vendor.setVendor_no(rs.getString(1));
//				vendor.setV_account(rs.getString(2));
//				vendor.setV_pwd(rs.getString(3));
//				vendor.setV_mail(rs.getString(4));
//				vendor.setV_tel(rs.getString(5));
//				vendor.setV_n_code(rs.getString(6));
//				vendor.setV_ad_code(rs.getString(7));
//				vendor.setV_address1(rs.getString(8));
//				vendor.setV_address2(rs.getString(9));
//				vendor.setV_address3(rs.getString(10));
//				vendor.setV_wallet(rs.getString(11));
//				vendor.setV_name(rs.getString(12));
//				vendor.setV_w_no(rs.getInt(13));
//				vendor.setV_n_no(rs.getInt(14));
//				vendor.setV_alt_no(rs.getInt(15));
//				vendor.setV_start_time(rs.getString(16));
//				vendor.setV_end_time(rs.getString(17));
//				vendor.setV_day(rs.getString(18));
//				vendor.setV_tables(rs.getString(19));
////				vendor.setV_pic(rs.getBytes(20));
////				vendor.setV_ad(rs.getBytes(21));
//				vendor.setV_status(rs.getString(22));
//				vendor.setV_wait_status(rs.getString(23));
//				vendor.setV_type(rs.getString(24));
//				vendor.setV_text(rs.getString(25));
//
//			}
//			System.out.println("查詢完畢");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (pstm != null) {
//				try {
//					pstm.close();
//				} catch (SQLException se) {
//					se.printStackTrace();
//				}
//			}
//		}
//		if (con != null) {
//			try {
//				con.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return vendor;
//	}
//
//	@Override
//	public VendorVO findByAcc(String v_account) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		VendorVO vendor = null;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			pstm = con.prepareStatement(GET_ACC_STMT);
//			pstm.setString(1, v_account);
//			rs = pstm.executeQuery();
//
//			while (rs.next()) {
//				vendor = new VendorVO();
//
//				vendor.setVendor_no(rs.getString(1));
//				vendor.setV_account(rs.getString(2));
//				vendor.setV_pwd(rs.getString(3));
//				vendor.setV_mail(rs.getString(4));
//				vendor.setV_tel(rs.getString(5));
//				vendor.setV_n_code(rs.getString(6));
//				vendor.setV_ad_code(rs.getString(7));
//				vendor.setV_address1(rs.getString(8));
//				vendor.setV_address2(rs.getString(9));
//				vendor.setV_address3(rs.getString(10));
//				vendor.setV_wallet(rs.getString(11));
//				vendor.setV_name(rs.getString(12));
//				vendor.setV_w_no(rs.getInt(13));
//				vendor.setV_n_no(rs.getInt(14));
//				vendor.setV_alt_no(rs.getInt(15));
//				vendor.setV_start_time(rs.getString(16));
//				vendor.setV_end_time(rs.getString(17));
//				vendor.setV_day(rs.getString(18));
//				vendor.setV_tables(rs.getString(19));
////				vendor.setV_pic(rs.getBytes(20));
////				vendor.setV_ad(rs.getBytes(21));
//				vendor.setV_status(rs.getString(22));
//				vendor.setV_wait_status(rs.getString(23));
//				vendor.setV_type(rs.getString(24));
//				vendor.setV_text(rs.getString(25));
//
//			}
//			System.out.println("查詢完畢");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (pstm != null) {
//				try {
//					pstm.close();
//				} catch (SQLException se) {
//					se.printStackTrace();
//				}
//			}
//		}
//		if (con != null) {
//			try {
//				con.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return vendor;
//	}
//
//	@Override
//	public int updatePic(VendorVO vendorVO) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		int rs = 0;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			System.out.println("連線成功!更新一張圖");
//			pstm = con.prepareStatement(UPDATE_PIC);
//
////			pstm.setBytes(1, vendorVO.getV_pic());
//			pstm.setString(2, vendorVO.getVendor_no());
//
//			System.out.println(vendorVO.getVendor_no());
//
//			rs = pstm.executeUpdate();
//			System.out.println("成功筆數 : " + rs);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return rs;
//	}
//
//	@Override
//	public int updateAd(VendorVO vendorVO) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//
//		int rs = 0;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			System.out.println("連線成功!");
//			pstm = con.prepareStatement(UPDATE_AD);
//
////			pstm.setBytes(1, vendorVO.getV_ad());
//			pstm.setString(2, vendorVO.getVendor_no());
//
//			rs = pstm.executeUpdate();
//			System.out.println("成功筆數 : " + rs);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return rs;
//	}
//
//	@Override
//	public List<VendorVO> getAll() {
//
//		List<VendorVO> vlist = new ArrayList<>();
//		Connection con = null;
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		VendorVO vendor = null;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			pstm = con.prepareStatement(GET_ALL_STMT);
//			rs = pstm.executeQuery();
//
//			while (rs.next()) {
//				vendor = new VendorVO();
//
//				vendor.setVendor_no(rs.getString(1));
//				vendor.setV_account(rs.getString(2));
//				vendor.setV_pwd(rs.getString(3));
//				vendor.setV_mail(rs.getString(4));
//				vendor.setV_tel(rs.getString(5));
//				vendor.setV_n_code(rs.getString(6));
//				vendor.setV_ad_code(rs.getString(7));
//				vendor.setV_address1(rs.getString(8));
//				vendor.setV_address2(rs.getString(9));
//				vendor.setV_address3(rs.getString(10));
//				vendor.setV_wallet(rs.getString(11));
//				vendor.setV_name(rs.getString(12));
//				vendor.setV_w_no(rs.getInt(13));
//				vendor.setV_n_no(rs.getInt(14));
//				vendor.setV_alt_no(rs.getInt(15));
//				vendor.setV_start_time(rs.getString(16));
//				vendor.setV_end_time(rs.getString(17));
//				vendor.setV_day(rs.getString(18));
//				vendor.setV_tables(rs.getString(19));
//				// vendor.setV_pic();
//				// vendor.setV_ad();
//				vendor.setV_status(rs.getString(22));
//				// 裝入集合
//				vlist.add(vendor);
//			}
//			System.out.println("查詢完畢");
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (pstm != null) {
//				try {
//					pstm.close();
//				} catch (SQLException se) {
//					se.printStackTrace();
//				}
//			}
//		}
//		if (con != null) {
//			try {
//				con.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return vlist;
//	}
//
//	public static void main(String[] args) {
//		VendorDAO_interface vDAO = new VendorJDBCDAO();
//
//		// 新增
//		VendorVO vVO = new VendorVO();
//
//		vVO.setV_account("xxxzzzcc");
//		vVO.setV_pwd("000000");
//		vVO.setV_mail("yourmom@gmail.com");
//		vVO.setV_tel("11223344");
//		vVO.setV_n_code("02");
//		vVO.setV_ad_code("300");
//		vVO.setV_address1("台北市");
//		vVO.setV_address2("信義區");
//		vVO.setV_address3("中正路10號");
//		vVO.setV_name("墨賞鐵板燒");
////		vVO.setV_pic(null);
//
////		vVO.setVendor_no("V000003");
////		rm.setMenu_name("宇宙大燒賣");
////		rm.setMenu_price("2050");
//// 		rm.setMenu_pic(null);
//// 		rm.setMenu_stat(1);		
//// 		rm.setMenu_text("居然包了一整頭豬在裡面啊");		
//
//		vDAO.create(vVO);
//
//		// 單筆查詢
//		// System.out.println(vDAO.findByPrimaryKey("V000005"));
//
//		// 查詢全部
////		 List<VendorVO> vlist = new ArrayList<>();
////		 vlist = vDAO.getAll();
////		 for (VendorVO v : vlist) {
////		 System.out.println(v);
////		 }
//
//		// 新增
//		// VendorVo v2 = new VendorVo("leg0543088", "leg0543088",
//		// "leg0543088@gmail.com", "2165788", "08", "881", "高雄市8",
//		// "前金區8", "新田路2078號", "159843152381", "辣癮食尚麻辣火鍋8", "1300", "1700", "0101010",
//		// "6", "4");
//		//
//		// vDAO.insert(v2);
//
//		// 刪除
//		// vDAO.delete("V000006");
//
//		// 更新
////		VendorVO v3 = new VendorVO("leg0543088", "leg0543089", "2165789", "09", "666", "高雄9市", "前金9區", "新田路278號",
////				"15984312381", "火鍋8", "1309", "1709", "0101011", "3", "5");
////		vDAO.update(v3);
//	}
//
//	@Override
//	public void detail(VendorVO vendorVO) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void check(VendorVO vendorVO) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void waitOnOff(VendorVO vendorVO) {
//		// TODO Auto-generated method stub
//
//	}
//
//	
//
//}
