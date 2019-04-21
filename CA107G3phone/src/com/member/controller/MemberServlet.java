package com.member.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.member.model.MemberService;
import com.member.model.MemberVo;

//@WebServlet("/MemberServlet")
@MultipartConfig
public class MemberServlet extends HttpServlet {
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
		MemberService memberDao = new MemberService();
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();
		System.out.println("---1---");
		if (action.equals("isMember")) {
			System.out.println("---2---");
			String mem_account = jsonObject.get("mem_account").getAsString();
			String mem_pwd = jsonObject.get("mem_pwd").getAsString();

			if (memberDao.isMember(mem_account, mem_pwd)) {
				MemberVo memebervo = memberDao.getOneMem_account(mem_account);
				String jsonStr = gson.toJson(memebervo);

				writeText(res, jsonStr);

			} else {
				writeText(res, String.valueOf(memberDao.isMember(mem_account, mem_pwd)));
			}

			System.out.println("---4---");

		}

		if (action.equals("getImage")) {
			OutputStream os = res.getOutputStream();
			String mem_no = jsonObject.get("mem_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			
		}

	}

	private void writeText(HttpServletResponse res, String outText) throws IOException {
		System.out.println("---3---");
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		out.print(outText);
		out.close();
		System.out.println("outText: " + outText);
	}
}
