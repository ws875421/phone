package android.wait_pos.controller;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class ServletAwareConfig extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
//        config.getUserProperties().put("httpSession", httpSession);   
        
		if (httpSession != null) {
			System.out.println("获取到session id:" + httpSession.getId());
			config.getUserProperties().put("httpSession", httpSession);
		} else {
			System.out.println("modifyHandshake 获取到null session");
		}
        
    }

}
