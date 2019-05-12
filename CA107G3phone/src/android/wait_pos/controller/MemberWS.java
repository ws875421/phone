package android.wait_pos.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value="/MemberWS/{mem_no}")
public class MemberWS implements ServletContextListener {
	public static ServletContext context;
	
	
	@OnOpen
	public void onOpen(@PathParam("mem_no") String mem_no, Session memSession) throws IOException {
		
		String text = String.format("連線#MemberWS Session ID = %s, connected; userName = %s", memSession.getId(), mem_no);
		System.out.println(text);
		// 設定成500KB為了配合Android bundle傳送大小
		int maxBufferSize = 500 * 1024;
		memSession.setMaxTextMessageBufferSize(maxBufferSize);
		memSession.setMaxBinaryMessageBufferSize(maxBufferSize);
        Map<String, Set<Session>> member_sessions = (Map)context.getAttribute("member_sessions");
        Set<Session> memSessionSet = (Set) member_sessions.get(mem_no);
        if (memSessionSet == null) {
        	memSessionSet = Collections.synchronizedSet(new HashSet<Session>());
        	member_sessions.put(mem_no, memSessionSet);
        }
        memSessionSet.add(memSession);
	} // end of onOpen

	@OnMessage
	public void onMessage(Session userSession, String message) {
		System.out.println("Message received: " + message);
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String text = String.format("session ID = %s, disconnected; close code = %d; reason phrase = %s",
				userSession.getId(), reason.getCloseCode().getCode(), reason.getReasonPhrase());
		System.out.println(text);
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}
	
	
	public void contextInitialized(ServletContextEvent event) {
		context = event.getServletContext();
	}

	public void contextDestroyed(ServletContextEvent event) {
		// do nothing
		System.out.println("ServletContextListener通知: contextDestroyed....");
	}
}

