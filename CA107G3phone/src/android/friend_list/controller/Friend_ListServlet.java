package android.friend_list.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import android.friend_list.model.Friend_ListService;
import android.friend_list.model.Friend_ListVO;
import android.member.Util.ImageUtil;
import android.member.model.MemberService;
import android.member.model.MemberVo;

public class Friend_ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);
		Friend_ListService fdlistSvc = new Friend_ListService();

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();

		if (action.equals("getFriend")) {

			String mem_no = jsonObject.get("mem_no").getAsString();
			List<Friend_ListVO> flist = fdlistSvc.getfd(mem_no);
			String jsonStr = gson.toJson(flist);
			writeText(res, jsonStr);

		}

		if (action.equals("getName")) {

			String mem_no = jsonObject.get("mem_no").getAsString();
			String gName = fdlistSvc.getName(mem_no);
//			String jsonStr = gson.toJson(gName);
			writeText(res, gName);

		}

	}

	private void writeText(HttpServletResponse res, String outText) throws IOException {
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		out.print(outText);
		out.close();
		System.out.println("outText: " + outText);
	}

}
