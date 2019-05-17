package com.ord.model;

import java.util.List;

public class OrdService {

	private Ord_interface dao;

	public OrdService() {
		dao = new OrdDAO();
	}

	public List<OrdVO> getListbyVendor(String vendor_no) {

		return dao.getListbyVendor(vendor_no);
	}

}
