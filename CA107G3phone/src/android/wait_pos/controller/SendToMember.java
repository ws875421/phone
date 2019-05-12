package android.wait_pos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.websocket.Session;

import org.apache.commons.collections4.map.ListOrderedMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SendToMember {
	
	// open wait function
	public static void openWaitFun(String vendor_no,  String jsonStr, Boolean open_wait, ServletContext context) {
//		JsonObject jbMsg = new JsonObject();
//		jbMsg.addProperty("action", "openWaitFun");
//		jbMsg.addProperty("tbl_size", tbl_size);
//		jbMsg.addProperty("open_wait", open_wait);
//		String jsonStr = jbMsg.toString();
		
		sendToMember(vendor_no, jsonStr, context);
	} // End of openWaitFun()

	// call member
	public static void beCalled(String vendor_no,  String mem_no, String jsonStr, ServletContext context) {
//		JsonObject jbMsg = new JsonObject();
//		jbMsg.addProperty("event", "beCalled");
//		jbMsg.addProperty("action", "setDeadline");
//		jbMsg.addProperty("vendor_no", vendor_no);
//		jbMsg.addProperty("tbl_size", tbl_size);
//		jbMsg.addProperty("deadline", deadline);
//		String jsonStr = jbMsg.toString();
		
		sendToMember(mem_no, jsonStr, context);
		
	} // End of setDeadline()

	
	// clearLine
	public static void beCanceled(String vendor_no, String jsonStr, List<String> memList, ServletContext context) {		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
//		JsonObject joMsg = new JsonObject();
//		joMsg.addProperty("event", "cancelByVendor");
//		joMsg.addProperty("action", "changeSatusToCancel");
//		joMsg.addProperty("vendor_no", vendor_no);
//		joMsg.addProperty("tbl_size", tbl_size);
//		String jsonStr =joMsg.toString();
//		System.out.println(memList);
		for(int i = 0; i < memList.size(); i++) {	
			sendToMember(memList.get(i), jsonStr, context);
			
		}
	}  // End of beCanceled()
	
	// passed and removed
		public static void beCanceledCall(String vendor_no, Integer tbl_size, String mem_no, ServletContext context) {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				JsonObject joMsg = new JsonObject();
				joMsg.addProperty("event", "passedAndRemoved");
				joMsg.addProperty("action", "changeSatusToCancel");
				joMsg.addProperty("vendor_no", vendor_no);
				joMsg.addProperty("tbl_size", tbl_size);
				String jsonStr =joMsg.toString();
				
				sendToMember(mem_no, jsonStr, context);
				
		}  // End of beCanceledCall()
		
	// cancel
	public static void renewGpBeforeCancel(String event, Wait_Line wait_line, String jsonStr, String vendor_no, Integer pilIdx, ServletContext context) {
		ListOrderedMap<String, PersonInLine> waitLine = wait_line.getWait_line();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		for(int i = pilIdx; i < waitLine.size(); i++) {
			JsonObject joMsg = new JsonObject();
			joMsg.addProperty("event", event);
			joMsg.addProperty("action", "renewGpBefore");
			joMsg.addProperty("vendor_no", vendor_no);
//			joMsg.addProperty("tbl_size", tbl_size);
			joMsg.addProperty("gp_before", i);
//			String jsonStr =joMsg.toString();
			System.out.println("####");
			sendToMember(waitLine.get(i), jsonStr, context);
			
		}

	} // End of renewGpBeforeCancel()
	
	// check member
	public static void renewGpBeforeCheck(String event, Wait_Line wait_line, String jsonStr, String vendor_no, ServletContext context) {
		ListOrderedMap<String, PersonInLine> waitLine = wait_line.getWait_line();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		for(int i = 0; i < waitLine.size(); i++) {
			JsonObject joMsg = new JsonObject();
			joMsg.addProperty("event", event);
			joMsg.addProperty("action", "renewGpBefore");
			joMsg.addProperty("vendor_no", vendor_no);
//			joMsg.addProperty("tbl_size", tbl_size);
			joMsg.addProperty("gp_before", i);
//			String jsonStr = joMsg.toString();
			
			sendToMember(waitLine.get(i), jsonStr, context);
			System.out.println(waitLine.get(i));
		}

	} // End of renewGpBeforeCheck()
	
	public static void beChecked(String vendor_no, String jsonStr, String mem_no, ServletContext context) {
//		JsonObject jbMsg = new JsonObject();
//		jbMsg.addProperty("event", "beChecked");
//		jbMsg.addProperty("action", "beChecked");
//		jbMsg.addProperty("vendor_no", vendor_no);
//		jbMsg.addProperty("tbl_size", tbl_size);
//		String jsonStr = jbMsg.toString();
		
		sendToMember(mem_no, jsonStr, context);
		
	} // End of setDeadline()
	
	// call member
	public static void renewGpBeforeCall(String event, Wait_Line wait_line, String jsonStr, String vendor_no, Integer pilIdx, ServletContext context) {
		ListOrderedMap<String, PersonInLine> waitLine = wait_line.getWait_line();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		for(int i = 0; i <= pilIdx; i++) {
			JsonObject joMsg = new JsonObject();
			if (i == pilIdx) {
				joMsg.addProperty("event", "you" + event);
			} else {
				joMsg.addProperty("event", event);
			}			
			joMsg.addProperty("action", "renewGpBefore");
			joMsg.addProperty("vendor_no", vendor_no);
//			joMsg.addProperty("tbl_size", tbl_size);
			joMsg.addProperty("gp_before", i);
//			String jsonStr =joMsg.toString();
			
			sendToMember(waitLine.get(i), jsonStr, context);			
		}

	} // End of renewGpBeforeCall()	
	
	public static void sendToMember(String mem_no, String jsonStr, ServletContext context) {
		Set<Session> memSessionSet = (Set) ((Map) context.getAttribute("member_sessions")).get(mem_no);
		
		if (memSessionSet != null) {
			for (Session session : memSessionSet) {
				synchronized (session) {
					if (session.isOpen()) {
						session.getAsyncRemote().sendText(jsonStr);
						System.out.println(jsonStr);
					}
							
						
				}
			}
		}
		
		
				
	} // End of sendToMember()
}
