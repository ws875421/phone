package android.wait_pos.model;

import java.util.List;

public interface Wait_PosDAO_interface {

	public int insert(Wait_PosVO posVo);

	public int update(Wait_PosVO posVo);

	public int delete(String posVo_no);

	public Wait_PosVO findByPrimaryKey(String posVo_no);

	public List<Wait_PosVO> getAll();

}
