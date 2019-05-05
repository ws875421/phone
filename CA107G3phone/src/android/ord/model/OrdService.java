package android.ord.model;

import java.util.List;

public class OrdService {
	private OrdDAO_interface dao;

	public OrdService() {
		dao = new OrdDAO();
	}

	public boolean isOrd(String MEM_NO, String VERIF_CODE) {
		return dao.isOrd(MEM_NO, VERIF_CODE);
	}
	
}
