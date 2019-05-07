package android.wait_pos.controller;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener

public class RequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    }

    @Override
	public void requestInitialized(ServletRequestEvent servletRequestEvent) {

        ((HttpServletRequest)servletRequestEvent.getServletRequest()).getSession();
    }
}
