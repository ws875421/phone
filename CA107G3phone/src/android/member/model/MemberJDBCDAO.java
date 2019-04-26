//package android.member.model;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MemberJDBCDAO implements MemberDAO_interface {
//
//	final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
//	final static String URL = "jdbc:oracle:thin:@localhost:1521:XE";
//	final static String USER = "CA107G3";
//	final static String PASSWORD = "123456";
//
//	// SQL
//	private static final String INSERT_STMT = "INSERT INTO MEMBER VALUES ('M'||LPAD(to_char(member_seq.NEXTVAL), 6, '0'),?,?,?,?,?,?,?,?,?,?,?)";
//	private static final String UPDATE = "UPDATE MEMBER SET MEM_NAME = ?, MEM_PWD = ?, MEM_GENDER = ?, MEM_TEL = ?, MEM_STATUS = ?,MEM_PIC = ? , MEM_BALANCE = ?, MEM_NICKNAME = ? WHERE MEM_ACCOUNT = ?";
//	private static final String DELETE = "DELETE FROM MEMBER WHERE MEM_NO =?";
//	private static final String GET_ONE_STMT = "SELECT * FROM MEMBER WHERE MEM_NO = ?";
//	private static final String GET_ALL_STMT = "SELECT * FROM MEMBER ";
//
//	static {
//		try {
//			Class.forName(DRIVER);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public MemberJDBCDAO() {
//	}
//
//	@Override
//	public int insert(MemberVo memberVO) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		int rs = 0;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
////			System.out.println("連線成功!");
//			pstm = con.prepareStatement(INSERT_STMT);
//			pstm.setString(1, memberVO.getMem_name());
//			pstm.setString(2, memberVO.getMem_account());
//			pstm.setString(3, memberVO.getMem_pwd());
//			pstm.setString(4, memberVO.getMem_gender());
//			pstm.setString(5, memberVO.getMem_mail());
//			pstm.setString(6, memberVO.getMem_id());
//			pstm.setString(7, memberVO.getMem_tel());
//			pstm.setString(8, memberVO.getMem_status());
//			pstm.setBytes(9, memberVO.getMem_pic());
//			pstm.setDouble(10, memberVO.getMem_balance());
//			pstm.setString(11, memberVO.getMem_nickname());
//
//			rs = pstm.executeUpdate();
////			System.out.println("成功筆數 : " + rs);
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
//	public int update(MemberVo memberVO) {
//		Connection con = null;
//		PreparedStatement pstm = null;
//		int rs = 0;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			pstm = con.prepareStatement(UPDATE);
//			pstm.setString(1, memberVO.getMem_name());
//			pstm.setString(2, memberVO.getMem_pwd());
//			pstm.setString(3, memberVO.getMem_gender());
//			pstm.setString(4, memberVO.getMem_tel());
//			pstm.setString(5, memberVO.getMem_status());
//			pstm.setDouble(6, memberVO.getMem_balance());
//			pstm.setString(7, memberVO.getMem_nickname());
//			pstm.setString(8, memberVO.getMem_account());
//
//			rs = pstm.executeUpdate();
//
////			System.out.println("更新筆數 : " + rs);
//
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}
//
//		return rs;
//	}
//
//	@Override
//	public int delete(String mem_no) {
//
//		Connection con = null;
//		PreparedStatement pstm = null;
//		int rs = 0;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			pstm = con.prepareStatement(DELETE);
//			pstm.setString(1, mem_no);
//			rs = pstm.executeUpdate();
//
////			System.out.println("更新筆數 : " + rs);
//
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}
//
//		return rs;
//
//	}
//
//	@Override
//	public MemberVo findByPrimaryKey(String mem_no) {
//
//		Connection con = null;
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		MemberVo member = null;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			pstm = con.prepareStatement(GET_ONE_STMT);
//			pstm.setString(1, mem_no);
//			rs = pstm.executeQuery();
//
//			while (rs.next()) {
//				member = new MemberVo();
//				member.setMem_no(mem_no);
//				member.setMem_name(rs.getString(2));
//				member.setMem_account(rs.getString(3));
//				member.setMem_pwd(rs.getString(4));
//				member.setMem_gender(rs.getString(5));
//				member.setMem_mail(rs.getString(6));
//				member.setMem_id(rs.getString(7));
//				member.setMem_tel(rs.getString(8));
//				member.setMem_status(rs.getString(9));
//				// member.setMem_pic();
//				member.setMem_balance(rs.getDouble(11));
//				member.setMem_nickname(rs.getString(12));
//			}
////			System.out.println("查詢完畢");
//
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}
//
//		return member;
//	}
//
//	@Override
//	public List<MemberVo> getAll() {
//		List<MemberVo> mlist = new ArrayList<>();
//		Connection con = null;
//		PreparedStatement pstm = null;
//		ResultSet rs = null;
//		MemberVo member = null;
//
//		try {
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			pstm = con.prepareStatement(GET_ALL_STMT);
//			rs = pstm.executeQuery();
//
//			while (rs.next()) {
//				member = new MemberVo();
//				member.setMem_no(rs.getString(1));
//				member.setMem_name(rs.getString(2));
//				member.setMem_account(rs.getString(3));
//				member.setMem_pwd(rs.getString(4));
//				member.setMem_gender(rs.getString(5));
//				member.setMem_mail(rs.getString(6));
//				member.setMem_id(rs.getString(7));
//				member.setMem_tel(rs.getString(8));
//				member.setMem_status(rs.getString(9));
//				member.setMem_balance(rs.getDouble(11));
//				member.setMem_nickname(rs.getString(12));
//				// 裝入集合
//				mlist.add(member);
//			}
////			System.out.println("查詢完畢");
//
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}
//
//		return mlist;
//	}
//
////######################################################################################
//	public static void main(String[] args) {
//
//		MemberDAO_interface memberDAO = new MemberJDBCDAO();
//
//		// 新增
////		String pic_path = "C:\\picFrom";
////		String pic_pic = "2835-2.gif";
////		byte[] b = null;
////		FileInputStream in = null;
////		ByteArrayOutputStream baos = null;
////
////		File pic = new File(pic_path, pic_pic);
////		try {
////			in = new FileInputStream(pic);
////			baos = new ByteArrayOutputStream();
////			b = new byte[in.available()];
////			int c;
////			while ((c = in.read(b)) != -1) {
////				baos.write(b, 0, c);
////			}
////			b = baos.toByteArray();
////
////		} catch (FileNotFoundException e) {
////			e.printStackTrace();
////		} catch (IOException e) {
////			e.printStackTrace();
////		} finally {
////			if (baos != null) {
////				try {
////					baos.close();
////				} catch (IOException e) {
////					e.printStackTrace();
////				}
////			}
////			if (in != null) {
////				try {
////					in.close();
////				} catch (IOException e) {
////					e.printStackTrace();
////				}
////			}
////		}
//
////		byte[] b = null;
////		try {
////			b = getPictureByteArray("C:\\picFrom\\2835-2.gif");
////		} catch (IOException e) {catch block
////			e.printStackTrace();
////		}
////
////		MemberVo mb1 = new MemberVo("王妍熙2", "zld2502448", "zld2502448", "F", "zld2502448@gmail.com", "H110368617",
////				"0916417676", "1", b, 899.0, "小狐狸2");
////		memberDAO.insert(mb1);
////		
//
//		// 頭貼
////		File pic = new File("");
//
//		// 查詢PK
//		System.out.println(memberDAO.findByPrimaryKey("M000001"));
//
//		// 查詢多筆
////		List<MemberVo> allList = new ArrayList<>();
////		allList = memberDAO.getAll();
////		for (MemberVo mall : allList) {
////			System.out.println(mall);
////		}
//
//		//
//		// 更新 12
////		Byte[] b = new Byte[0];
////		MemberVo mb2_up = new MemberVo("王妍熙6", "val5080809", "zld2502888", "M", "0932145698", "3", b, 700d, "小三");
////		System.out.println(mb2_up);
////		memberDAO.update(mb2_up);
//
//		// 刪除
////		memberDAO.delete("M000007");
//
//	}
//
////讀圖片給位置檔名
////	public static byte[] getPictureByteArray(String path) throws IOException {
////		File file = new File(path);
////		FileInputStream fis = new FileInputStream(file);
////		ByteArrayOutputStream baos = new ByteArrayOutputStream();
////		byte[] buffer = new byte[8192];
////		int i;
////		while ((i = fis.read(buffer)) != -1) {
////			baos.write(buffer, 0, i);
////		}
////		baos.close();
////		fis.close();
////
////		return baos.toByteArray();
////	}
//}
