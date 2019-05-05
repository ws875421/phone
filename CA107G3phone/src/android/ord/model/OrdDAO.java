package android.ord.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OrdDAO implements OrdDAO_interface{

	private static DataSource ds = null;

	// SQL
	private static final String GET_ONE_STMT = "SELECT * FROM ORD WHERE MEM_NO = ? AND VERIF_CODE = ?";
	

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA107G3");
		} catch (NamingException e) {

			e.printStackTrace();
		}
	}
	
	
	@Override
	public boolean isOrd(String MEM_NO, String VERIF_CODE) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		boolean isOrd = false;

		try {

			con = ds.getConnection();
			pstm = con.prepareStatement(GET_ONE_STMT);
			pstm.setString(1, MEM_NO);
			pstm.setString(2, VERIF_CODE);
			rs = pstm.executeQuery();
			isOrd = rs.next();
			return isOrd;
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
		return isOrd;
	}

}
