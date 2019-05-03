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

	private static final String GET_ONE_STMT = "SELECT * FROM FRIEND_LIST WHERE MEM_NO =?";

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
