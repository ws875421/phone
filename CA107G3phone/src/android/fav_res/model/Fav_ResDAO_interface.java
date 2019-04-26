package android.fav_res.model;

import java.util.List;

public interface Fav_ResDAO_interface {

	public int insert(Fav_ResVo fav_ResVo);
	
	public List<Fav_ResVo> find(String mem_no, String vendor_no);

	public int delete(String mem_no, String vendor_no);

	public List<Fav_ResVo> GETALLVNAME(String mem_no);
	
	public int update(Fav_ResVo fav_ResVo);

	public List<Fav_ResVo> findByMem_No(String mem_no);

	public List<Fav_ResVo> getAll();
}
