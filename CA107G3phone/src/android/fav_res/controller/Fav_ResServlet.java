package android.fav_res.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import android.fav_res.model.Fav_ResService;
import android.fav_res.model.Fav_ResVo;

//@WebServlet("/Fav_ResServlet")
public class Fav_ResServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		Fav_ResService fav_ResSvc = new Fav_ResService();

		String action = jsonObject.get("action").getAsString();
		String mem_no = jsonObject.get("mem_no").getAsString();
		String vendor_no = null;

		if (action.equals("addfav") && !(mem_no.isEmpty())) {

			vendor_no = jsonObject.get("vendor_no").getAsString();

			int rs = fav_ResSvc.addFav_Res(mem_no, vendor_no);
			String jsonStr = gson.toJson(rs);
			writeText(res, jsonStr);
		}

		if (action.equals("isFav") && !(mem_no.isEmpty())) {

			vendor_no = jsonObject.get("vendor_no").getAsString();

			List favlist = fav_ResSvc.find(mem_no, vendor_no);
			writeText(res, gson.toJson(favlist));

		}

		if (action.equals("del") && !(mem_no.isEmpty())) {

			vendor_no = jsonObject.get("vendor_no").getAsString();

			int i = fav_ResSvc.delete(mem_no, vendor_no);
			writeText(res, gson.toJson(i));

		}

		if (action.equals("getallfav") && !(mem_no.isEmpty())) {
			List favlist = fav_ResSvc.getallfav(mem_no);
			writeText(res, gson.toJson(favlist));
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
