package android.wait_pos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.websocket.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SendToVendor {
	
	// open wait function
	public static void openWaitFun(String vendor_no, Integer tbl_size, Boolean open_wait, ServletContext context) {
		JsonObject jbMsg = new JsonObject();
		jbMsg.addProperty("action", "openWaitFun");
		jbMsg.addProperty("tbl_size", tbl_size);
		jbMsg.addProperty("open_wait", open_wait);
		String jsonStr = jbMsg.toString();
		
		sendToVendor(vendor_no, jsonStr, context);
	} // End of openWaitFun()

	// call member
	public static void setDeadline(String vendor_no, Integer tbl_size, String mem_no, int numberPlate, long deadline, ServletContext context) {
		JsonObject jbMsg = new JsonObject();
		jbMsg.addProperty("action", "setDeadline");
		jbMsg.addProperty("tbl_size", tbl_size);
		jbMsg.addProperty("mem_no", mem_no);
		jbMsg.addProperty("numberPlate", numberPlate);
		jbMsg.addProperty("deadline", deadline);
		String jsonStr = jbMsg.toString();
		
		sendToVendor(vendor_no, jsonStr, context);
		
	} // End of setDeadline()

	// return zero
	public static void returnZero(String vendor_no, Integer tbl_size, ServletContext context) {
		JsonObject jbMsg = new JsonObject();
		jbMsg.addProperty("action", "returnZero");
		jbMsg.addProperty("tbl_size", tbl_size);
		String jsonStr = jbMsg.toString();
		
		sendToVendor(vendor_no, jsonStr, context);
	} // End of returnZero()
	
	public static void clearLine(String vendor_no, Integer tbl_size, ServletContext context) {
		JsonObject jbMsg = new JsonObject();
		jbMsg.addProperty("action", "clearLine");
		jbMsg.addProperty("tbl_size", tbl_size);
		String jsonStr = jbMsg.toString();
		
		sendToVendor(vendor_no, jsonStr, context);
	}  // End of clearLine()
	
	// refresh line
	public static void refreshLine(String event, Wait_Line wait_line, Integer tbl_size, String vendor_no, String result, ServletContext context) {
		List<PersonInLineV> pilList = new ArrayList<PersonInLineV>();
		for (int i = 0; i < wait_line.getWait_line().size(); i++) {
			pilList.add(new PersonInLineV(wait_line.getWait_line().getValue(i)));
		}
			
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonElement jeLine = gson.toJsonTree(pilList);
		
		JsonObject jbLine = new JsonObject();
		jbLine.addProperty("event", event);
		jbLine.addProperty("action", "refreshLine");
		jbLine.addProperty("result", result);
		jbLine.addProperty("tbl_size", tbl_size);
		jbLine.add("w_line", jeLine);
		String jsonStr = gson.toJson(jbLine);
		
		sendToVendor(vendor_no, jsonStr, context);
		
	} // End of refreshLine()
	
	
	// insert
//	public static void renewNumberNow(Wait_Line wait_line, String vendor_no, Integer tbl_size, ServletContext context) {
//		JsonObject jbMsg = new JsonObject();
//		jbMsg.addProperty("action", "renewNumberNow");
//		jbMsg.addProperty("tbl_size", tbl_size);
//		jbMsg.addProperty("number_now", wait_line.getNumber_now());
//		String jsonStr = jbMsg.toString();
//		
//		sendToVendor(vendor_no, jsonStr, context);
//	} // End of renewNumberNow()
	
	public static void sendToVendor(String vendor_no, String jsonStr, ServletContext context) {
		Set<Session> vendor_wait_session = (Set) ((Map) context.getAttribute("vendor_wait_sessions")).get(vendor_no);
		
		for (Session session : vendor_wait_session) {
			synchronized (session) {
				if (session.isOpen()) 
						session.getAsyncRemote().sendText(jsonStr);
					
			}
		}
		
				
	} // End of sendToVendor()
}
