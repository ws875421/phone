package android.friend_list.model;

import java.util.List;
import java.util.Set;

public interface Friend_ListDAO_interface {

	public List<Friend_ListVO> getfd(String mem_no);
	
	public String getName(String mem_no);

}
