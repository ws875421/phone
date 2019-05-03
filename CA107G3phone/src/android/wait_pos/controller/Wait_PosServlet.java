package android.wait_pos.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import org.apache.commons.collections4.map.ListOrderedMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
//import com.tables.model.TablesService;
//import com.tables.model.TablesVO;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class Wait_PosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		
		String action = req.getParameter("action");
		
		String vendor_no = req.getParameter("vendor_no");
		
		Integer tbl_size = null; // action=insert : null
		if (req.getParameter("tbl_size") != null)
			tbl_size = Integer.parseInt(req.getParameter("tbl_size"));
		
/* ============================= Vendor ================================= */		
// open_wait_fun
// call_member
// returnZero
// check_member
// clear_line		
		if ("open_wait_fun".equals(action)) {

				Boolean open_wait = Boolean.parseBoolean(req.getParameter("open_wait"));				
				
				Wait_Line wait_line = getWaitLine(vendor_no, tbl_size);

				synchronized(wait_line) {
					wait_line.setOpen_wait(open_wait);
					SendToVendor.openWaitFun(vendor_no, tbl_size, open_wait, getServletContext());
				}

			
		} // end of open_wait_fun
		
		if ("call_member".equals(action)) {

			Wait_Line wait_line = getWaitLine(vendor_no, tbl_size);
			
			ListOrderedMap<String, PersonInLine> wait_line_queue = wait_line.getWait_line();
			if (wait_line_queue.size() == 0) {
				out.println("目前沒有人可以叫號");
				return;
			}
			
			String mem_no = null;
			synchronized(wait_line) {
				
				// find the min person who is uncalled
				for (int i = 0;  i < wait_line_queue.size(); i++) {
					PersonInLine pil = wait_line_queue.getValue(i);
					if (!pil.getIsCall()) {
						mem_no = pil.getMem_no();
						break;
					}
				}
				if (mem_no == null) {
					out.println("目前所有人皆已叫號");
					return;
				}
				
				PersonInLine personInLine = wait_line.getWait_line().get(mem_no);
				
				Timer callMemTimer = new Timer();
				long delay = 30 * 1000; // ms
				long deadline = System.currentTimeMillis() + delay;
				callMemTimer.schedule(new CallMemTask(vendor_no, tbl_size, wait_line, mem_no, getServletContext()), new Date(deadline));				
				personInLine.setDeadline(deadline);
				personInLine.setCallMemTimer(callMemTimer);				
				personInLine.setIsCall(true);
				
				// send Deadline to vendor
				SendToVendor.setDeadline(vendor_no, tbl_size, mem_no, personInLine.getNumberPlate(), deadline, getServletContext());
													 
			} // End of synchronized(wait_line)

		
		} // end of call_member	
		
		if ("return_zero".equals(action)) {
	
			Wait_Line wait_line = getWaitLine(vendor_no, tbl_size);
			
			synchronized(wait_line) {
				wait_line.setNumber_now(1);
				SendToVendor.returnZero(vendor_no, tbl_size, getServletContext());
			}
			
			
		} // end of returnZero
		
		if ("clear_line".equals(action)) {
			
			Wait_Line wait_line = getWaitLine(vendor_no, tbl_size);
			
			synchronized(wait_line) {
				wait_line.getWait_line().clear();;
				SendToVendor.clearLine(vendor_no, tbl_size, getServletContext());
			}
			
			
		} // end of clear_line
		
		if ("check_member".equals(action)) {

			String mem_no = req.getParameter("mem_no");
	
			Wait_Line wait_line = getWaitLine(vendor_no, tbl_size);
			String result = null;
			PersonInLine personInLine = null;
			synchronized(wait_line) {
				personInLine = wait_line.getWait_line().remove(mem_no);
				if (personInLine == null) {
					out.println((tbl_size * 2) + " 人桌 " + personInLine.getNumberPlate() + " 號已取消, 驗證失敗");
					return;
				} 
				personInLine.getCallMemTimer().cancel();
				result = (tbl_size * 2) + " 人桌 " + personInLine.getNumberPlate() + " 號驗證成功";
				SendToVendor.refreshLine("check", wait_line,  tbl_size,  vendor_no, result, getServletContext());
			}
			

			// mem_no 移到桌位管理

			
		} // end of check_member
		
/* ============================= Member ================================= */		
// insert
// cancel
		
		if ("insert".equals(action)) {
			
			String mem_no = req.getParameter("mem_no");

			String result = null;
			
			Map<String, Map<Integer, Wait_Line>> wait_line_all = (Map) getServletContext().getAttribute("wait_line_all");
			
			Map<Integer, Wait_Line> wait_line_vendor = (Map) wait_line_all.get(vendor_no);				
			if (wait_line_vendor == null) {
				out.println("目前不開放候位"); // message for member
				return;
			}
			
			Integer party_size = Integer.parseInt(req.getParameter("party_size"));
			switch (party_size) {
				case 1: case 2: tbl_size = 1; break;
				case 3: case 4: tbl_size = 2; break;
				case 5: case 6: tbl_size = 3; break;				
				case 7:	case 8:	tbl_size = 4; break;				
				case 9:	case 10: tbl_size = 5; break;			
			}
			
			Wait_Line wait_line = wait_line_vendor.get(tbl_size);
			if (wait_line == null) {
				out.println("目前不開放候位"); // message for member
				return;				
			}
			
			JsonObject jbMsg = new JsonObject();
			synchronized(wait_line) {
				if (!wait_line.getOpen_wait()) {
					out.println("目前不開放候位"); // message for member
					return;					
				} else {
					System.out.println("mem_no:"+mem_no);
					System.out.println("wait_line_vendor:"+wait_line_vendor);
					System.out.println("party_size:"+party_size);
					
					// check if the member has been in line
					Set<Integer> set = wait_line_vendor.keySet();
					Iterator<Integer> it = set.iterator();
					while(it.hasNext()) {
						if (wait_line_vendor.get(it.next()).getWait_line().keySet().contains(mem_no) ) {
							out.println("Don't take a number again!"); // message for member
							return;
						}
					}
					
					// put the member in line
					PersonInLine personInLine = new PersonInLine(mem_no, party_size, wait_line.getNumberPlate());
					wait_line.getWait_line().put(mem_no, personInLine);
					out.println("隊伍現況 " + wait_line.getWait_line() + "<br>"); // message for member
					out.println("號碼牌 " + wait_line.getWait_line().get(mem_no).getNumberPlate() + "<br>"); // message for member
					out.println("前面還有幾組人 " + wait_line.getWait_line().indexOf(mem_no) + "<br>"); // message for member
					result = tbl_size + " 號桌新增候位 " + personInLine.getNumberPlate() + " 號";  // message for vendor
					
					System.out.println("隊伍現況 " + wait_line.getWait_line());
					System.out.println("號碼牌 " + wait_line.getWait_line().get(mem_no).getNumberPlate());
					System.out.println("前面還有幾組人 " + wait_line.getWait_line().indexOf(mem_no));
					
					
				}
				
				// message for vendor
				jbMsg.addProperty("result", result);
				jbMsg.addProperty("number_now", wait_line.getNumber_now());
				String jsonStr = jbMsg.toString();
				// send to vendor
				SendToVendor.refreshLine("insert",  wait_line,  tbl_size,  vendor_no, jsonStr, getServletContext());
//				SendToVendor.renewNumberNow( wait_line, vendor_no, tbl_size, getServletContext());
			} // End of synchronized(wait_line)
			

			
		} // end of insert
		
		
		if ("cancel".equals(action)) {
			
			String mem_no = req.getParameter("mem_no");
						
			String result = null;
			
			Map<String, Map<Integer, Wait_Line>> wait_line_all = (Map) getServletContext().getAttribute("wait_line_all");
			
			Map<Integer, Wait_Line> wait_line_vendor = (Map) wait_line_all.get(vendor_no);				


			Wait_Line wait_line = wait_line_vendor.get(tbl_size);
			
			PersonInLine personInLine;
			synchronized(wait_line) {
				personInLine = wait_line.getWait_line().remove(mem_no);
				if (personInLine != null) {
					if (personInLine.getCallMemTimer() != null) personInLine.getCallMemTimer().cancel(); // 叫號中取消
					result = tbl_size + " 號桌 " + personInLine.getNumberPlate() + " 號已取消";  // message for vendor
					// send to vendor
					SendToVendor.refreshLine("cancel", wait_line,  tbl_size,  vendor_no, result, getServletContext());
				} else {
					// multithread 已經被別人取消了 同帳號多個登入 or 已驗證
				}
				
				out.println("取消候位成功");  // message for member
				
			}
			
			
			
		} // end of cancel
		
		

	} // end of doPost
	
	@Override
	public void init() throws ServletException {
		ServletContext context = getServletContext();
		context.setAttribute("wait_line_all", new HashMap<String, Map<Integer, Wait_Line>>());
		context.setAttribute("vendor_wait_sessions", new ConcurrentHashMap<String, Set<Session>>());
	}
	
	public Wait_Line getWaitLine(String vendor_no, Integer tbl_size) {
		Map<String, Map<Integer, Wait_Line>> wait_line_all = (Map) getServletContext().getAttribute("wait_line_all");
		
		Map<Integer, Wait_Line> wait_line_vendor = (Map) wait_line_all.get(vendor_no);				
		if (wait_line_vendor == null) {
			wait_line_vendor = new HashMap<Integer, Wait_Line>();
			wait_line_all.put(vendor_no, wait_line_vendor);					
		}
		
		Wait_Line wait_line = wait_line_vendor.get(tbl_size);
		if (wait_line == null) {
			wait_line = new Wait_Line();
			wait_line_vendor.put(tbl_size, wait_line);					
		}
		
		return wait_line;
	} // End of getWaitLine	

} // end of class
