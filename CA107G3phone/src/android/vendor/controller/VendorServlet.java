package android.vendor.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import android.member.Util.ImageUtil;
import android.vendor.model.*;

//@WebServlet("/VendorServlet")
//@MultipartConfig
public class VendorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		System.out.println("doPost");
		
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);

		VendorService vendorSvc = new VendorService();

		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();
		
		if (action.equals("isVendor")) {
			String v_account = jsonObject.get("v_account").getAsString();
			String v_pwd = jsonObject.get("v_pwd").getAsString();

			if (vendorSvc.isVendor(v_account, v_pwd)) {
				VendorVO vendorVO = vendorSvc.getOneV_account(v_account);
				String jsonStr = gson.toJson(vendorVO);

				writeText(res, jsonStr);

			} else {
				writeText(res, String.valueOf(vendorSvc.isVendor(v_account, v_pwd)));
			}
		}
		if (action.equals("getAll")) {
			List<VendorVO> vlist = vendorSvc.getAll();
			writeText(res, gson.toJson(vlist));
		}
		if (action.equals("getImage") && (!(jsonObject.get("vendor_no").getAsString()).isEmpty())) {
			OutputStream os = res.getOutputStream();
			String vendor_no = jsonObject.get("vendor_no").getAsString();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = vendorSvc.getImage(vendor_no);
			if (image != null) {
				// 縮圖 in server side
				image = ImageUtil.shrink(image, imageSize);
				res.setContentType("image/jpeg");
				res.setContentLength(image.length);
			}
			os.write(image);
		} 

		else {
			writeText(res, "");
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
