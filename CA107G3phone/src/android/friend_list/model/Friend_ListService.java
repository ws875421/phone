package android.friend_list.model;

import java.util.List;
import java.util.Set;

public class Friend_ListService {

	private Friend_ListDAO_interface dao;

	public Friend_ListService() {
		dao = new Friend_ListDAO();
	}

	public List<Friend_ListVO> getfd(String mem_no) {

//		List fblist = dao.getfd(mem_no);

		return dao.getfd(mem_no);
	}

	public String getName(String mem_no) {
		return dao.getName(mem_no);
	}

}
