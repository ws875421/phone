package android.fav_res.model;

import java.util.List;

public class Fav_ResService {

	private Fav_ResDAO dao;

	public Fav_ResService() {
		dao = new Fav_ResDAO();
	}

	public int addFav_Res(String mem_no, String vendor_no) {

		Fav_ResVo fav_ResVo = new Fav_ResVo();
		fav_ResVo.setMem_no(mem_no);
		fav_ResVo.setVendor_no(vendor_no);

		int i = dao.insert(fav_ResVo);
		return i;
	}

	public List<Fav_ResVo> findByMem_No(String mem_no) {

		List favlist = dao.findByMem_No(mem_no);
		return favlist;
	}

	public List<Fav_ResVo> find(String mem_no, String vendor_no) {

		List favlist = dao.find(mem_no, vendor_no);
		return favlist;
	}

	public List<Fav_ResVo> getallfav(String mem_no) {

		List favlist = dao.GETALLVNAME(mem_no);
		return favlist;
	}
	
	public int delete(String mem_no, String vendor_no) {
		int i = dao.delete(mem_no, vendor_no);
		return i;
	}

}