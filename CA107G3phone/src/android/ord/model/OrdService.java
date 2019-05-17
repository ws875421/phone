package android.ord.model;

import java.util.List;

public class OrdService {
	private OrdDAO_interface dao;

	public OrdService() {
		dao = new OrdDAO();
	}

	public boolean isOrd(String ord_no, String MEM_NO, String vendor_no, String VERIF_CODE) {
		return dao.isOrd(ord_no, MEM_NO, vendor_no, VERIF_CODE);
	}

	public List<OrdVO> getord(String MEM_NO) {
		return dao.getord(MEM_NO);
	}

}
