package android.wait_pos.controller;

import java.io.BufferedReader;
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

import android.vendor.model.VendorService;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class Wait_PosServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");

		Gson gson = new Gson();
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();

		System.out.println("action:" + action);

		Integer tbl_size = null; // action=insert : null

		if ("insertPhone".equals(action)) {

			String vendor_no = jsonObject.get("vendor_no").getAsString();
			String mem_no = jsonObject.get("mem_no").getAsString();
			Integer party_size = jsonObject.get("party_size").getAsInt();

			String result = null;

			Map<String, Map<Integer, Wait_Line>> wait_line_all = (Map) getServletContext()
					.getAttribute("wait_line_all");

			Map<Integer, Wait_Line> wait_line_vendor = (Map) wait_line_all.get(vendor_no);
			if (wait_line_vendor == null) {
//				out.println("目前不開放候位"); // message for member

				String jsonStr = gson.toJson("目前不開放候位");
				System.out.println(jsonStr);
				writeText(res, jsonStr);

				return;
			}

			switch (party_size) {
			case 1:
			case 2:
				tbl_size = 1;
				break;
			case 3:
			case 4:
				tbl_size = 2;
				break;
			case 5:
			case 6:
				tbl_size = 3;
				break;
			case 7:
			case 8:
				tbl_size = 4;
				break;
			case 9:
			case 10:
				tbl_size = 5;
				break;
			}

			Wait_Line wait_line = wait_line_vendor.get(tbl_size);
			if (wait_line == null) {
//				out.println("目前不開放候位"); // message for member

				String jsonStr = gson.toJson("目前不開放候位");
				System.out.println(jsonStr);
				writeText(res, jsonStr);

				return;
			}

			JsonObject jbMsg = new JsonObject();
			synchronized (wait_line) {
				if (!wait_line.getOpen_wait()) {
//					out.println("目前不開放候位"); // message for member

					String jsonStr = gson.toJson("目前不開放候位");
					System.out.println(jsonStr);
					writeText(res, jsonStr);

					return;
				} else {
					// check if the member has been in line
					Set<Integer> set = wait_line_vendor.keySet();
					Iterator<Integer> it = set.iterator();
					while (it.hasNext()) {
						if (wait_line_vendor.get(it.next()).getWait_line().keySet().contains(mem_no)) {
//							out.println("Don't take a number again!"); // message for member
							String jsonStr = gson.toJson("不得重複抽號");
							System.out.println(jsonStr);
							writeText(res, jsonStr);

							return;
						}
					}

					// put the member in line
					PersonInLine personInLine = new PersonInLine(mem_no, party_size, wait_line.getNumberPlate());
					wait_line.getWait_line().put(mem_no, personInLine);

					// message for member

					result = tbl_size + " 號桌新增候位 " + personInLine.getNumberPlate() + " 號"; // message for vendor

					
			        JsonObject jsonObject2 = new JsonObject();
			        jsonObject2.addProperty("號碼牌", wait_line.getWait_line().get(mem_no).getNumberPlate());
			        jsonObject2.addProperty("前面還有幾組人", wait_line.getWait_line().indexOf(mem_no));

			        String jsonOut = jsonObject2.toString();
					System.out.println(jsonOut);
					writeText(res, jsonOut);
					
				}

				// message for vendor
				jbMsg.addProperty("result", result);
				jbMsg.addProperty("number_now", wait_line.getNumber_now());
				String jsonStr = jbMsg.toString();
				// send to vendor
				SendToVendor.refreshLine("insert", wait_line, tbl_size, vendor_no, jsonStr, getServletContext());

			}

		}

	}

	private void writeText(HttpServletResponse res, String outText) throws IOException {
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		out.print(outText);
		out.close();
		System.out.println("outText: " + outText);
	}

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
