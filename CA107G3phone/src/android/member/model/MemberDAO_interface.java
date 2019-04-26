package android.member.model;

import java.util.List;

public interface MemberDAO_interface {

	public int insert(MemberVo memberVO);

	public int update(MemberVo memberVO);

	public int delete(String mem_no);

	public MemberVo findByPrimaryKey(String mem_account);

	public List<MemberVo> getAll();

	public boolean isMember(String mem_account, String mem_pwd);

	public byte[] getImage(String mem_no);

}
